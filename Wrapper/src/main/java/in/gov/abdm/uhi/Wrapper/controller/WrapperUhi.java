package in.gov.abdm.uhi.Wrapper.controller;

import java.util.Set;

import javax.validation.Valid;
import in.gov.abdm.uhi.Wrapper.dto.dhp.Error;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;

import in.gov.abdm.uhi.Wrapper.constants.ConstantsUtils;
import in.gov.abdm.uhi.Wrapper.dto.dhp.Ack;
import in.gov.abdm.uhi.Wrapper.dto.dhp.AckResponse;
import in.gov.abdm.uhi.Wrapper.dto.dhp.EuaRequestBody;
import in.gov.abdm.uhi.Wrapper.dto.dhp.EuaRequestBodyStatus;
import in.gov.abdm.uhi.Wrapper.dto.dhp.MessageResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.CreatePHRRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.GenerateOTPRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.IsPhrAddressExistsDTO;
import in.gov.abdm.uhi.Wrapper.dto.phr.JwtResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginPostVerificationRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginPostVerificationRequestResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginPreVerificationRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginPreVerificationResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginResendOtp;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginViaMobileEmailRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.LoginViaMobileEmailRequestResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.RegistrationByMobileOrEmailRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.ResendOTPRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.SearchResponsePayLoad;
import in.gov.abdm.uhi.Wrapper.dto.phr.ServiceResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.SuccessResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.SuggestionsDTO;
import in.gov.abdm.uhi.Wrapper.dto.phr.TransactionResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.TransactionWithPHRResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.VerifyOTPRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.AuthConfirmResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.AuthInitResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.LoginViaMobileEmailRequestInit;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.LoginViaPhrRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.SearchByHealthIdNumberRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.SearchPhrAuthResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.login.VerifyPasswordOtpLoginRequest;
import in.gov.abdm.uhi.Wrapper.dto.phr.registration.HidResponse;
import in.gov.abdm.uhi.Wrapper.dto.phr.registration.JwtResponseHid;
import in.gov.abdm.uhi.Wrapper.dto.phr.registration.LoginRequestPayload;
import in.gov.abdm.uhi.Wrapper.dto.phr.registration.LoginViaMobileEmailRequestRegistration;
import in.gov.abdm.uhi.Wrapper.dto.phr.registration.SearchRequestPayLoad;
import in.gov.abdm.uhi.Wrapper.exceptions.PhrException400;
import in.gov.abdm.uhi.Wrapper.exceptions.PhrException500;
import reactor.core.publisher.Mono;


@RequestMapping("/api/v1/wrapper/apps")
@RestController
public class WrapperUhi {

    private static final Logger LOGGER = LoggerFactory.getLogger(WrapperUhi.class);

    private final WebClient webClient;

    @Value("${abdm.phr.base.url}")
    private String baseUrlPhr;
    @Value("${abdm.eua.url}")
    private String euaUrl;

    @Value("${abdm.phr.generateOtp.url}")
    private String generateOtpUrl;
    @Value("${abdm.phr.resendOtp.url}")
    private String resendOtpUrl;
    @Value("${abdm.phr.validateOtp.url}")
    private String validateOtpUrl;
    @Value("${abdm.phr.register.url}")
    private String registerUrl;
    @Value("${abdm.phr.registerAdditional.url}")
    private String registerAdditional;
    @Value("${abdm.gateway.url}")
    private String gatewayUrl;
    @Value("${abdm.uhi.bookingService}")
    private String bookingService;
    @Value("${abdm.phr.login.mobileEmail.authInit.url}")
    private String loginMobileEmailAuthInitUrl;
    @Value("${abdm.phr.login.mobileEmail.authConfirm.url}")
    private String loginMobileEmailAuthConfirmUrl;
    @Value("${abdm.phr.login.mobileEmail.preVerify.url}")
    private String loginMobileEmailPreVerifyUrl;
    @Value("${abdm.phr.registration.hid.confirmInit.url}")
    private String registrationByHidConfirmInitUrl;
    @Value("${abdm.phr.registration.hid.createPhr.url}")
    private String registrationByHidCreatePhrUrl;
    @Value("${abdm.phr.registration.hid.authInit.url}")
    private String registrationByHidAuthInitUrl;
    @Value("${abdm.phr.registration.hid.authMode.url}")
    private String registrationByHidAuthModeUrl;
    @Value("${abdm.phr.login.hid.authInit.url}")
    private String loginByHidAuthInitUrl;
    @Value("${abdm.phr.login.hid.authMode.url}")
    private String loginByHidAuthModeUrl;
    @Value("${abdm.phr.login.phrAdress.authInit.url}")
    private String loginByPhrAuthInitUrl;
    @Value("${abdm.phr.login.phrAdress.authConfirm.url}")
    private String loginByPhrAuthConfirmUrl;
    @Value("${abdm.phr.login.phrAdress.authMode.url}")
    private String loginByPhrAuthModeUrl;
    @Value("${abdm.phr.login.resend.otp.url}")
    private String loginResendOtpUrl;


    public WrapperUhi(WebClient webClient) {
        this.webClient = webClient;
    }

    @PostMapping("/registration/mobileEmail/generate/otp")
    public ResponseEntity<Mono<TransactionResponse>> generateOtp(@RequestBody GenerateOTPRequest otpDTO) {
        LOGGER.info("Inside wrapper /registration/mobileEmail/generate/otp API ");


        Mono<TransactionResponse> sessionIdMono;

        sessionIdMono = this.webClient
                .post()
                .uri(baseUrlPhr + generateOtpUrl)
                .body(BodyInserters.fromValue(otpDTO))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(TransactionResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(sessionIdMono);
    }


    @PostMapping("/registration/mobileEmail/validate/otp")
    public ResponseEntity<Mono<TransactionWithPHRResponse>> validateOtp(@RequestBody VerifyOTPRequest otpDTO) {
        LOGGER.info("Inside wrapper /registration/mobileEmail/validate/otp API ");

        Mono<TransactionWithPHRResponse> verifyDetails;

        verifyDetails = this.webClient.post().uri(baseUrlPhr + validateOtpUrl).body(Mono.just(otpDTO), VerifyOTPRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(TransactionWithPHRResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    @GetMapping("/states")
    public ResponseEntity<Mono<?>> getAllStates() {
        LOGGER.info("Inside wrapper /states API ");

        Mono<Set> statesEntityDTO;

        statesEntityDTO = webClient.get().uri(baseUrlPhr + "/states")
                .retrieve()
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new ServiceException(error))))
                .bodyToMono(Set.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(statesEntityDTO);
    }

    @GetMapping("/{id}/districts")
    public ResponseEntity<Mono<?>> getAllDistricts(@PathVariable String id) {
        LOGGER.info("Inside wrapper  \"/{id}/districts API ");

        String url = baseUrlPhr + "/" + id + "/districts";
        Mono<Set> statesEntityDTO;

        statesEntityDTO = webClient.get().uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new ServiceException(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(Set.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(statesEntityDTO);
    }

    @PostMapping("/registration/mobileEmail/create/phrAddress/suggestion")
    public ResponseEntity<Mono<SuggestionsDTO>> getSuggestions(@Valid @RequestBody ResendOTPRequest suggestionsRequest, Errors errors) {
        LOGGER.info("Inside wrapper /registration/mobileEmail/create/phrAddress/suggestion API ");

        Mono<SuggestionsDTO> suggestions;

        suggestions = webClient.post().uri(baseUrlPhr + "/v1/apps/create/phrAddress/suggestion")
                .body(Mono.just(suggestionsRequest), ResendOTPRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(SuggestionsDTO.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(suggestions);
    }

    @GetMapping("/registration/mobileEmail/phrAddress/isExist")
    public ResponseEntity<Mono<IsPhrAddressExistsDTO>> isExists(@RequestParam String phrAddress) {

        LOGGER.info("Inside wrapper /registration/mobileEmail/phrAddress/isExist API ");

        Mono<IsPhrAddressExistsDTO> phrAddressExistsResponse;
        String queryParam = "?phrAddress=" + phrAddress;
        String url = baseUrlPhr + "/v1/apps/phrAddress/isExist" + queryParam;

        phrAddressExistsResponse = webClient.get()
                .uri(url)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(IsPhrAddressExistsDTO.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(phrAddressExistsResponse);
    }


    @PostMapping("/registration/mobileEmail/resend/otp")
    public ResponseEntity<Mono<SuccessResponse>> resendOtp(@RequestBody ResendOTPRequest otpDTO) {

        LOGGER.info("Inside wrapper /registration/mobileEmail/resend/otp API ");

        Mono<SuccessResponse> verifyDetails;

        verifyDetails = this.webClient.post().uri(baseUrlPhr + resendOtpUrl).body(Mono.just(otpDTO), ResendOTPRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(SuccessResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    @PostMapping("/registration/mobileEmail/details")
    public ResponseEntity<Mono<TransactionResponse>> registerNewPhr(@RequestBody RegistrationByMobileOrEmailRequest otpDTO) {

        LOGGER.info("Inside wrapper /registration/mobileEmail/details API ");


        Mono<TransactionResponse> verifyDetails;


        verifyDetails = this.webClient.post().uri(baseUrlPhr + registerUrl).body(Mono.just(otpDTO), RegistrationByMobileOrEmailRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(TransactionResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);

    }

    @PostMapping("/registration/mobileEmail/create/phrAddress")
    public ResponseEntity<Mono<JwtResponse>> registerPhr(@RequestBody CreatePHRRequest otpDTO) {

        LOGGER.info("Inside wrapper /registration/mobileEmail/create/phrAddress API ");

        Mono<JwtResponse> verifyDetails;

        verifyDetails = this.webClient.post().uri(baseUrlPhr + registerAdditional).body(Mono.just(otpDTO), CreatePHRRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(JwtResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    @PostMapping("/login/mobileEmail/auth-confirm")
    public ResponseEntity<Mono<LoginPostVerificationRequestResponse>> validateUserToken(@RequestBody @Valid LoginPostVerificationRequest otpDTO, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/mobileEmail/auth-confirm API ");

        Mono<LoginPostVerificationRequestResponse> verifyDetails;

        verifyDetails = this.webClient.post().uri(baseUrlPhr + loginMobileEmailAuthConfirmUrl)
                .header("Authorization", auth)
                .body(Mono.just(otpDTO), LoginPostVerificationRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(LoginPostVerificationRequestResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);

    }


    @PostMapping(value = "/login/mobileEmail/auth-init", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Mono<LoginViaMobileEmailRequestResponse>> generateOtpForLogin(@Valid @RequestBody LoginViaMobileEmailRequestInit otpDTO, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/mobileEmail/auth-init API ");

        Mono<LoginViaMobileEmailRequestResponse> verifyDetails;


        verifyDetails = this.webClient.post()
                .uri(baseUrlPhr + loginMobileEmailAuthInitUrl)
                .header("Authorization", auth)
                .body(Mono.just(otpDTO), LoginViaMobileEmailRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(LoginViaMobileEmailRequestResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }

    @PostMapping("/login/mobileEmail/pre-Verify")
    public ResponseEntity<Mono<LoginPreVerificationResponse>> verifyUserOtp(@RequestBody @Valid LoginPreVerificationRequest otpDTO, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/mobileEmail/pre-Verify API ");

        Mono<LoginPreVerificationResponse> verifyDetails;

        verifyDetails = this.webClient.post().uri(baseUrlPhr + loginMobileEmailPreVerifyUrl).body(Mono.just(otpDTO), LoginPreVerificationRequest.class)
                .header("Authorization", auth)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(LoginPreVerificationResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    @PostMapping("/registration/hid/search/auth-mode")
    public ResponseEntity<Mono<SearchResponsePayLoad>> findUserByHealthId(@Valid @RequestBody SearchRequestPayLoad searchRequestPayLoad, Errors errors) {

        LOGGER.info("Inside wrapper /registration/hid/search/auth-mode API ");

        Mono<SearchResponsePayLoad> searchResponsePayLoad = null;

        searchResponsePayLoad = this.webClient.post().uri(baseUrlPhr + registrationByHidAuthModeUrl).body(BodyInserters.fromValue(searchRequestPayLoad))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(SearchResponsePayLoad.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(searchResponsePayLoad);
    }

    @PostMapping("/registration/hid/auth-init")
    public ResponseEntity<Mono<TransactionResponse>> generateTransactionOtp(@Valid @RequestBody LoginViaMobileEmailRequestRegistration loginViaMobileEmailRequest, Errors errors) {

        LOGGER.info("Inside wrapper /registration/hid/auth-init API ");

        Mono<TransactionResponse> transactionResponse = null;

        transactionResponse = this.webClient.post().uri(baseUrlPhr + registrationByHidAuthInitUrl).body(BodyInserters.fromValue(loginViaMobileEmailRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(TransactionResponse.class)
                .onErrorResume(this::getErrorSchemaReady);


        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }


    @PostMapping("/registration/hid/confirm-init")
    public ResponseEntity<Mono<HidResponse>> verifyUserOtp(@Valid @RequestBody LoginRequestPayload loginRequestPayload, Errors errors) {

        LOGGER.info("Inside wrapper /registration/hid/confirm-init API ");


        Mono<HidResponse> hidResponse = null;

        hidResponse = this.webClient.post().uri(baseUrlPhr + registrationByHidConfirmInitUrl).body(BodyInserters.fromValue(loginRequestPayload))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(HidResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(hidResponse);
    }

    @PostMapping("/registration/hid/create/phrAddress")
    public ResponseEntity<Mono<JwtResponseHid>> createPhrAddress(@Valid @RequestBody CreatePHRRequest createPHRRequest, Errors errors) {

        LOGGER.info("Inside wrapper /registration/hid/create/phrAddress API ");


        Mono<JwtResponseHid> jwtResponse = null;


        jwtResponse = this.webClient.post().uri(baseUrlPhr + registrationByHidCreatePhrUrl).body(BodyInserters.fromValue(createPHRRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(JwtResponseHid.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/login/hid/auth-init")
    public ResponseEntity<Mono<LoginViaMobileEmailRequestResponse>> generateOtpForHidLogin(@Valid @RequestBody LoginViaMobileEmailRequest viaMobileEmailRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/hid/auth-init API ");


        Mono<LoginViaMobileEmailRequestResponse> emailRequestResponse;
        String uri = baseUrlPhr + loginByHidAuthInitUrl;
        emailRequestResponse = this.webClient.post()
                .uri(uri).body(BodyInserters.fromValue(viaMobileEmailRequest))
                .header("Authorization", auth)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(LoginViaMobileEmailRequestResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(emailRequestResponse);
    }

    @PostMapping(value = "/login/hid/search/auth-mode")
    public ResponseEntity<Mono<SearchResponsePayLoad>> searchUserByHealthIdForLogin(@Valid @RequestBody SearchByHealthIdNumberRequest byHealthIdNumberRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/hid/search/auth-mode API ");

        Mono<SearchResponsePayLoad> responsePayLoad = null;
        responsePayLoad = this.webClient.post()
                .uri(baseUrlPhr + loginByHidAuthModeUrl).body(BodyInserters.fromValue(byHealthIdNumberRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(SearchResponsePayLoad.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }


    @PostMapping("/login/phrAddress/auth-init")
    public ResponseEntity<Mono<AuthInitResponse>> generateOtpPhrLogin(@Valid @RequestBody LoginViaPhrRequest loginViaPhrRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/phrAddress/auth-init API ");

        Mono<AuthInitResponse> responsePayLoad;

        responsePayLoad = this.webClient.post().uri(baseUrlPhr + loginByPhrAuthInitUrl)
                .header("Authorization", auth)
                .body(BodyInserters.fromValue(loginViaPhrRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(AuthInitResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }

    @PostMapping("/login/phrAddress/auth-confirm")
    public ResponseEntity<Mono<AuthConfirmResponse>> verifyOtpPhrLogin(@Valid @RequestBody VerifyPasswordOtpLoginRequest passwordOtpLoginRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /login/phrAddress/auth-confirm API ");

        Mono<AuthConfirmResponse> responsePayLoad;

        responsePayLoad = this.webClient.post().uri(baseUrlPhr + loginByPhrAuthConfirmUrl)
                .header("Authorization", auth)
                .body(BodyInserters.fromValue(passwordOtpLoginRequest))
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(AuthConfirmResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }


    @GetMapping("/login/phrAddress/search/auth-mode")
    public ResponseEntity<Mono<SearchPhrAuthResponse>> findUserByHealthIdForPhrLogin(@RequestParam(name = "phrAddress") String phrAddress) {

        LOGGER.info("Inside wrapper /login/phrAddress/search/auth-mode API ");

        Mono<SearchPhrAuthResponse> responsePayLoad;

        String uri = baseUrlPhr + loginByPhrAuthModeUrl + "?phrAddress=" + phrAddress;
        responsePayLoad = this.webClient.get().uri(uri)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(SearchPhrAuthResponse.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }

    @PostMapping("/resend/login/otp")
    public ResponseEntity<Mono<LoginResendOtp>> loginResendOtp(@Valid @RequestBody LoginResendOtp loginResendOtp, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside wrapper /resend/login/otp API ");

        Mono<LoginResendOtp> responsePayLoad;


        String uri = baseUrlPhr + loginResendOtpUrl;
        responsePayLoad = this.webClient.post()
                .uri(uri)
                .body(BodyInserters.fromValue(loginResendOtp))
                .header("Authorization", auth)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(LoginResendOtp.class)
                .onErrorResume(this::getErrorSchemaReady);

        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }

//    @PostMapping(ConstantsUtils.ON_SEARCH_ENDPOINT)
//    public ResponseEntity<Mono<AckResponse>> onSearch(@RequestBody EuaRequestBody onSearchRequest) throws JsonProcessingException {
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(euaUrl + "/on_search").body(BodyInserters.fromValue(onSearchRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//
//
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//    }
//
//    @PostMapping("/on_select")
//    public ResponseEntity<Mono<AckResponse>> onSelect(@RequestBody EuaRequestBody onSelectRequest) throws JsonProcessingException {
//
//
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(euaUrl+"/on_select").body(BodyInserters.fromValue(onSelectRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//
//    }
//
//    @PostMapping("/on_init")
//    public ResponseEntity<Mono<AckResponse>> onInit(@RequestBody EuaRequestBodyStatus onInitRequest) throws JsonProcessingException {
//        try {
//
//            Mono<AckResponse> ackResponseMono = webClient.post().uri(euaUrl+"/on_init").body(BodyInserters.fromValue(onInitRequest))
//                    .retrieve()
//                    .bodyToMono(AckResponse.class);
//
//            String ackResponseMono1 = webClient.post().uri(bookingService+"/on_init").body(BodyInserters.fromValue(onInitRequest))
//                    .retrieve()
//                    .onStatus(HttpStatus::is4xxClientError,
//                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                    .onStatus(HttpStatus::is5xxServerError,
//                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                    .bodyToMono(String.class)
//                    .block();
//
//
//            AckResponse a = new AckResponse();
//            assert ackResponseMono1 != null;
//            if (ackResponseMono1.equals("true")) {
//
//                return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//
//            } else {
//                a.getMessage().getAck().setStatus("NACK");
//                a.getError().setMessage("Failed to store orders in database");
//                ackResponseMono = Mono.just(a);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ackResponseMono);
//            }
//        } catch (Exception e) {
//
//
//
//            LOGGER.error(e.toString());
//            AckResponse a=new AckResponse();
//            a.getMessage().getAck().setStatus("NACK");
//            a.getError().setMessage(e.getMessage());
//            Mono<AckResponse> ackResponseMono=Mono.just(a);
//            return new ResponseEntity<>(ackResponseMono, HttpStatus.INTERNAL_SERVER_ERROR);
//
//
//
//        }
//    }
//
//
//    @PostMapping("/on_confirm")
//    public ResponseEntity<Mono<AckResponse>> onConfirm(@RequestBody EuaRequestBodyStatus onConfirmRequest) throws JsonProcessingException {
//        try {
//            Mono<AckResponse> ackResponseMono = webClient.post().uri(euaUrl+"/on_confirm").body(BodyInserters.fromValue(onConfirmRequest))
//                    .retrieve()
//                    .onStatus(HttpStatus::is4xxClientError,
//                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                    .onStatus(HttpStatus::is5xxServerError,
//                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                    .bodyToMono(AckResponse.class);
//
//
//            String ackResponseMono1 = webClient.post().uri(bookingService+"/on_init").body(BodyInserters.fromValue(onConfirmRequest))
//                    .retrieve()
//                    .onStatus(HttpStatus::is4xxClientError,
//                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                    .onStatus(HttpStatus::is5xxServerError,
//                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                    .bodyToMono(String.class)
//                    .block();
//
//
//            AckResponse a = new AckResponse();
//            if (ackResponseMono1 != null && ackResponseMono1.equals("true")) {
//                return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//
//            } else {
//                a.getMessage().getAck().setStatus("NACK");
//                a.getError().setMessage("Failed to store orders in database");
//                ackResponseMono = Mono.just(a);
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ackResponseMono);
//            }
//        } catch (Exception e) {
//
//
//            LOGGER.error(e.toString());
//            AckResponse a = new AckResponse();
//            a.getMessage().getAck().setStatus("NACK");
//            a.getError().setMessage(e.getMessage());
//            Mono<AckResponse> ackResponseMono = Mono.just(a);
//            return new ResponseEntity<>(ackResponseMono, HttpStatus.INTERNAL_SERVER_ERROR);
//
//
//        }
//
//
//    }
//
//    @PostMapping("/on_status")
//    public ResponseEntity<Mono<AckResponse>> onStatus(@RequestBody EuaRequestBodyStatus onStatusRequest) throws JsonProcessingException {
//
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(euaUrl+"/on_status").body(BodyInserters.fromValue(onStatusRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//
//    }
//
//
//    @PostMapping(ConstantsUtils.SEARCH_ENDPOINT)
//    public ResponseEntity<Mono<AckResponse>> search(@RequestBody EuaRequestBody searchRequest) throws JsonProcessingException {
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(gatewayUrl + "/search").body(BodyInserters.fromValue(searchRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//
//    }
//
//    @PostMapping("/select")
//    public ResponseEntity<Mono<AckResponse>> select(@RequestBody EuaRequestBody selectRequest) throws JsonProcessingException {
//
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(selectRequest.getContext().getProviderUri()+"/"+selectRequest.getContext().getAction()).body(BodyInserters.fromValue(selectRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//    }
//
//    @PostMapping("/init")
//    public ResponseEntity<Mono<AckResponse>> init(@RequestBody EuaRequestBody initRequest) throws JsonProcessingException {
//
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(initRequest.getContext().getProviderUri()+"/"+initRequest.getContext().getAction()).body(BodyInserters.fromValue(initRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//    }
//
//    @PostMapping("/confirm")
//    public ResponseEntity<Mono<AckResponse>> confirm(@RequestBody EuaRequestBody confirmRequest) throws JsonProcessingException {
//
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(confirmRequest.getContext().getProviderUri()+"/"+confirmRequest.getContext().getAction()).body(BodyInserters.fromValue(confirmRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//    }
//
//    @PostMapping("/status")
//    public ResponseEntity<Mono<AckResponse>> status(@RequestBody EuaRequestBody statusRequest) throws JsonProcessingException {
//        Mono<AckResponse> ackResponseMono = webClient.post().uri(statusRequest.getContext().getProviderUri()+"/"+statusRequest.getContext().getAction()).body(BodyInserters.fromValue(statusRequest))
//                .retrieve()
//                .onStatus(HttpStatus::is4xxClientError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
//                .onStatus(HttpStatus::is5xxServerError,
//                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
//                .bodyToMono(AckResponse.class);
//        return ResponseEntity.status(HttpStatus.OK).body(ackResponseMono);
//
//    }

    private <T extends ServiceResponse> Mono<T> getErrorSchemaReady(Throwable error) {
        LOGGER.error("WrapperUhiController::error::onErrorResume::" + error.getMessage());
        Mono<TransactionWithPHRResponse> errorMono = Mono.just(new TransactionWithPHRResponse());
        errorMono.subscribe(err -> {
            err.setError(error.getLocalizedMessage());
            err.setCode("500");
            err.setPath("/api/v1/wrapper/apps");
        });
        return (Mono<T>) errorMono;
    }
}
