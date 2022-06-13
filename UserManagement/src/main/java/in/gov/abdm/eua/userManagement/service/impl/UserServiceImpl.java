package in.gov.abdm.eua.userManagement.service.impl;

import in.gov.abdm.eua.userManagement.dto.phr.*;
import in.gov.abdm.eua.userManagement.dto.phr.registration.HidResponse;
import in.gov.abdm.eua.userManagement.exceptions.PhrException400;
import in.gov.abdm.eua.userManagement.exceptions.PhrException500;
import in.gov.abdm.eua.userManagement.model.Address;
import in.gov.abdm.eua.userManagement.model.User;
import in.gov.abdm.eua.userManagement.model.UserAbhaAddress;
import in.gov.abdm.eua.userManagement.model.UserRefreshToken;
import in.gov.abdm.eua.userManagement.repository.AddressRepo;
import in.gov.abdm.eua.userManagement.repository.UserAbhaAddressRepo;
import in.gov.abdm.eua.userManagement.repository.UserRefreshTokenRepo;
import in.gov.abdm.eua.userManagement.repository.UserRepo;
import in.gov.abdm.eua.userManagement.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Value("${abha.base.url}")
    private String abhaBaseUrl;

    @Value("${abha.getUserProfile.url}")
    private String getUserProfileUrl;

    private UserRepo userRepo;
    private AddressRepo addressRepo;
    private ModelMapper modelMapper;
    private WebClient webClient;
    private UserAbhaAddressRepo abhaAddressRepo;
    private UserRefreshTokenRepo refreshTokenRepo;
    private UserDTO userDTO;

    public UserServiceImpl(UserRepo userRepo, AddressRepo addressRepo, ModelMapper modelMapper, WebClient webClient, UserAbhaAddressRepo abhaAddressRepo, UserRefreshTokenRepo refreshTokenRepo) {
        this.userRepo = userRepo;
        this.addressRepo = addressRepo;
        this.modelMapper = modelMapper;
        this.webClient = webClient;
        this.abhaAddressRepo = abhaAddressRepo;
        this.refreshTokenRepo = refreshTokenRepo;
        userDTO = new UserDTO();
    }

    @Override
    public UserDTO getUserDTO() {
        return userDTO;
    }

    @Override
    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }


    @Override
    public void getUserProfile(String auth, String xtoken, @Valid OtpRequestForXToken request) {
        LOGGER.info("Inside function getUserProfile() ");
        Mono<UserDTO> userDTO = null;
        if (xtoken != null && auth != null) {
            LinkPhrAddressToAbhaNumber addressToAbhaNumber = new LinkPhrAddressToAbhaNumber();
            addressToAbhaNumber.setPhrAddress(request.getMappedPhrAddress());
            addressToAbhaNumber.setPreferred(request.getPreferred());
            try {
                Mono<PhrAddressAbhaNumberLinkageStatus> linkedStatus = getPhrAddressAbhaNumberLinkageStatusMono(auth, xtoken, addressToAbhaNumber);

                linkedStatus.subscribe(isLinked -> {
                    if (isLinked.getStatus()) {
                        this.webClient.get().uri(abhaBaseUrl + getUserProfileUrl)
                                .headers(httpHeaders -> {
                                    httpHeaders.add("Authorization", auth);
                                    String xtokenBearer = "Bearer " + xtoken;
                                    httpHeaders.add("X-Token", xtokenBearer);
                                })
                                .retrieve()
                                .onStatus(HttpStatus::is4xxClientError,
                                        response -> response.bodyToMono(LoginPostVerificationRequestResponse.class)
                                                .flatMap(error -> Mono.error(new PhrException500(error.getError().getErrorString()))))
                                .onStatus(HttpStatus::is5xxServerError,
                                        response -> response.bodyToMono(LoginPostVerificationRequestResponse.class)
                                                .flatMap(error -> Mono.error(new PhrException500(error.getError().getErrorString()))))
                                .bodyToMono(UserDTO.class)
                                .subscribe(res -> {
                                    if (res != null) {
                                        saveUserToDb(res);
                                    }
                                });
                    } else {
                        throw new PhrException500("Error mapping phrAddress to Abha Number. Cannot store user to DB");
                    }
                });

            } catch (PhrException400 | PhrException500 e) {
                LOGGER.error("Cannot store user details in DB ");
                LOGGER.error(e.getLocalizedMessage());
            }
        }
    }

    private Mono<PhrAddressAbhaNumberLinkageStatus> getPhrAddressAbhaNumberLinkageStatusMono(String auth, String xtoken, LinkPhrAddressToAbhaNumber addressToAbhaNumber) {
        return this.webClient.post().uri(abhaBaseUrl + "/v2/account/phr-linked")
                .headers(httpHeaders -> {
                    httpHeaders.add("Authorization", auth);
                    String xtokenBearer = "Bearer " + xtoken;
                    httpHeaders.add("X-Token", xtokenBearer);
                })
                .body(BodyInserters.fromValue(addressToAbhaNumber))
                .retrieve()
                .bodyToMono(PhrAddressAbhaNumberLinkageStatus.class);
    }

    //    @Async TODO: Make this async
    @Override
    public void saveUserToDb(UserDTO userDTO) {
        Address address = modelMapper.map(userDTO, Address.class);

        Set<UserAbhaAddress> abhaAddressSet = new HashSet<>();

        userDTO.getPhrAddress().forEach(phrAddress -> {
            UserAbhaAddress userAbhaAddress = new UserAbhaAddress();
            userAbhaAddress.setUser(address.getUser());
            userAbhaAddress.setPhrAddress(phrAddress);
            abhaAddressSet.add(userAbhaAddress);
            userAbhaAddress = null;
        });

        if (abhaAddressSet.isEmpty()) {
            LOGGER.error("No abha address found");
            throw new PhrException500("No abha address found");
        }
        address.getUser().setUser_abhaAddresses(abhaAddressSet);
        addressRepo.save(address);
    }


    @Override
    public void saveUserRefreshToken(String refreshToken, @Valid LoginPostVerificationRequest otpDTO) {
        List<UserAbhaAddress> abhaAddressList = abhaAddressRepo.findByPhrAddress(otpDTO.getPatientId().toString());
        Long userId = null;
        if (!abhaAddressList.isEmpty()) {
            userId = abhaAddressList.get(0).getUser().getUserId();
        } else {
            LOGGER.error("No abha address found");
            throw new PhrException500("No abha address found");
        }
        User user = abhaAddressList.get(0).getUser();
        UserRefreshToken userRefreshToken = new UserRefreshToken();
        userRefreshToken.setRefreshToken(refreshToken);
        userRefreshToken.setUser(user);

        refreshTokenRepo.save(userRefreshToken);


    }

    public String getUserRefreshTokenFromAbhaNumber(String healthIdNo) {
        UserRefreshToken byUserHealthIdNumber = refreshTokenRepo.getByUserHealthIdNumber(healthIdNo);
        if (byUserHealthIdNumber == null) {
            LOGGER.error("HealthIdNumber/Abhanumber not found");
            throw new PhrException500("HealthIdNumber/Abhanumber not found");
        }
        LOGGER.info("Refreshtoken with user retrieved is " + byUserHealthIdNumber.getRefreshToken());

        return byUserHealthIdNumber.getRefreshToken();
    }

    public void saveNewPhrAddress(String healthIdNumber, String newPhrAddress) {
        LOGGER.info("Inside method to save new PHR adress after /registration/hid/create/phrAddress");

        User user = userRepo.findByHealthIdNumber(healthIdNumber);
        if (null != user) {
            Set<UserAbhaAddress> user_abhaAddresses = user.getUser_abhaAddresses();
            UserAbhaAddress userAbhaAddressNew = new UserAbhaAddress();
            userAbhaAddressNew.setPhrAddress(newPhrAddress);
            userAbhaAddressNew.setUser(user);
            user_abhaAddresses.add(userAbhaAddressNew);

            user.setUser_abhaAddresses(user_abhaAddresses);

            LOGGER.info("User to be saved " + user);

            userRepo.save(user);
            userAbhaAddressNew = null;
            user_abhaAddresses = null;
            LOGGER.info("User Phr number saved successfully");
        } else {
            LOGGER.error("User with abha number " + healthIdNumber + " not found");
            throw new PhrException500("User with abha number " + healthIdNumber + " not found");
        }
    }

    public void mapHidresponseToUserDto(HidResponse res) {
        UserDTO request = getUserDTOFromRegistrationByMobileOrEmailRequest(res);
        request.setName(null);
        saveUserToDb(request);
    }

    private UserDTO getUserDTOFromRegistrationByMobileOrEmailRequest(HidResponse userData) {
        LOGGER.info("Mapping user data from /registration/hid/confirm-init HidResponse to userDto");

        UserDTO userDTO;
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        TypeMap<HidResponse, UserDTO> mapperMobileEmail = this.modelMapper.createTypeMap(HidResponse.class, UserDTO.class);

        mapperMobileEmail.addMapping(src -> src.getName().getFirstName(), UserDTO::setFirstName);
        mapperMobileEmail.addMapping(src -> src.getName().getMiddleName(), UserDTO::setMiddleName);
        mapperMobileEmail.addMapping(src -> src.getName().getLastName(), UserDTO::setLastName);

        mapperMobileEmail.addMapping(src -> src.getDateOfBirth().getDate(), UserDTO::setDayOfBirth);
        mapperMobileEmail.addMapping(src -> src.getDateOfBirth().getMonth(), UserDTO::setMonthOfBirth);
        mapperMobileEmail.addMapping(src -> src.getDateOfBirth().getYear(), UserDTO::setYearOfBirth);

        userDTO = this.modelMapper.map(userData, UserDTO.class);

        LOGGER.info("Mapped userDto is " + userDTO);

        return userDTO;

    }


}
