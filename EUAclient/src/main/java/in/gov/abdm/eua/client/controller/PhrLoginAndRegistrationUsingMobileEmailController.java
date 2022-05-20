package in.gov.abdm.eua.client.controller;

import in.gov.abdm.eua.client.dto.phr.StatesEntityDTO;
import in.gov.abdm.eua.client.dto.phr.*;
import in.gov.abdm.eua.client.dto.phr.login.LoginViaMobileEmailRequestInit;
import in.gov.abdm.eua.client.exceptions.PhrException400;
import in.gov.abdm.eua.client.exceptions.PhrException500;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("api/v1/")
public class PhrLoginAndRegistrationUsingMobileEmailController {
    private static final Logger LOGGER = LoggerFactory.getLogger(PhrLoginAndRegistrationUsingMobileEmailController.class);
    private final WebClient webClient;

    @Value("${abdm.phr.base.url}")
    private String baseUrlPhr;
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

    @Value("${abdm.phr.login.mobileEmail.authInit.url}")
    private String loginMobileEmailAuthInitUrl;
    @Value("${abdm.phr.login.mobileEmail.authConfirm.url}")
    private String loginMobileEmailAuthConfirmUrl;
    @Value("${abdm.phr.login.mobileEmail.preVerify.url}")
    private String loginMobileEmailPreVerifyUrl;





    public PhrLoginAndRegistrationUsingMobileEmailController(WebClient webClient){
        this.webClient = webClient;

    }

    @PostMapping("/registration/mobileEmail/generate/otp")
    public ResponseEntity<TransactionResponse> generateOtp(@RequestBody GenerateOTPRequest otpDTO) {
        LOGGER.info("Inside/registration/mobileEmail/generate/otp API ");

        ResponseEntity<TransactionResponse> BAD_REQUEST = applyValidationsForGenerateOtp(otpDTO);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        TransactionResponse sessionIdMono;
        try{
         sessionIdMono = this.webClient.post().uri(baseUrlPhr + generateOtpUrl).body(Mono.just(otpDTO), GenerateOTPRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                 .onStatus(HttpStatus::is5xxServerError,
                         response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(TransactionResponse.class)
                .block();
        }
        catch (PhrException400 e) {
            sessionIdMono = new TransactionResponse();
            return returnServerError400(sessionIdMono, e);
        }
        catch (PhrException500 e) {
            sessionIdMono = new TransactionResponse();
            return returnServerError500(sessionIdMono, e);
        }

       return ResponseEntity.status(HttpStatus.OK).body(sessionIdMono);
    }


    @PostMapping("/registration/mobileEmail/validate/otp")
    public ResponseEntity<TransactionWithPHRResponse> validateOtp(@RequestBody VerifyOTPRequest otpDTO) {
        LOGGER.info("Inside /registration/mobileEmail/validate/otp API ");

        ResponseEntity<TransactionWithPHRResponse> BAD_REQUEST = applyValidationsForValidateOtp(otpDTO);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        TransactionWithPHRResponse verifyDetails;
        try{
        verifyDetails = this.webClient.post().uri(baseUrlPhr + validateOtpUrl).body(Mono.just(otpDTO), VerifyOTPRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(TransactionWithPHRResponse.class)
                .block();
        }
        catch (PhrException400 e) {
            verifyDetails = new TransactionWithPHRResponse();
            return returnServerError400(verifyDetails, e);
        }
        catch (PhrException500 e) {
            verifyDetails = new TransactionWithPHRResponse();
            return returnServerError500(verifyDetails, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }

    @GetMapping("/states")
    public ResponseEntity<?> getAllStates() {
        LOGGER.info("Inside /states API ");

        Set<StatesEntityDTO> statesEntityDTO;
        try {
             statesEntityDTO = webClient.get().uri(baseUrlPhr + "/states")
                    .retrieve()
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new ServiceException(error))))
                    .bodyToMono(Set.class)
                    .block();

        }
        catch (PhrException500 e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ServiceResponse(e.getLocalizedMessage(),"500", "PhrLoginAndRegistrationController"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(statesEntityDTO);
    }

    @GetMapping("/{id}/districts")
    public ResponseEntity<?> getAllDistricts(@PathVariable String id) {
        LOGGER.info("Inside \"/{id}/districts API ");

        String url = baseUrlPhr+"/"+id+"/districts";
        Set<DistrictsEntityDTO> statesEntityDTO = null;
        try {
            statesEntityDTO = webClient.get().uri(url)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new ServiceException(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(Set.class)
                    .block();

        }catch (ServiceException e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ServiceResponse(e.getLocalizedMessage(),"400", "PhrLoginAndRegistrationController"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(statesEntityDTO);
    }

    @PostMapping("/registration/mobileEmail/create/phrAddress/suggestion")
    public ResponseEntity<SuggestionsDTO> getSuggestions(@RequestBody ResendOTPRequest suggestionsRequest) {
        LOGGER.info("Inside /registration/mobileEmail/create/phrAddress/suggestion API ");

        SuggestionsDTO suggestions;
        try {
            suggestions = webClient.post().uri(baseUrlPhr + "/v1/apps/create/phrAddress/suggestion")
                    .body(Mono.just(suggestionsRequest), ResendOTPRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(SuggestionsDTO.class)
                    .block();
        }
        catch (PhrException400 e) {
            suggestions = new SuggestionsDTO();
            return returnServerError400(suggestions, e);
        }
        catch (PhrException500 e) {
            suggestions = new SuggestionsDTO();
            return returnServerError500(suggestions, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(suggestions);
    }

    @GetMapping("/registration/mobileEmail/phrAddress/isExist")
    public ResponseEntity<IsPhrAddressExistsDTO> isExists(@RequestParam String phrAddress){

        LOGGER.info("Inside /registration/mobileEmail/phrAddress/isExist API ");

        IsPhrAddressExistsDTO phrAddressExistsResponse;
        String queryParam = "?phrAddress="+phrAddress;
        String url = baseUrlPhr+"/v1/apps/phrAddress/isExist"+queryParam;
        try {
            phrAddressExistsResponse = webClient.get()
                    .uri(url)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(IsPhrAddressExistsDTO.class)
                    .block();
        } catch (PhrException400 e) {
            phrAddressExistsResponse = new IsPhrAddressExistsDTO();
            return returnServerError400(phrAddressExistsResponse, e);
        }
        catch (PhrException500 e) {
            phrAddressExistsResponse = new IsPhrAddressExistsDTO();
            return returnServerError500(phrAddressExistsResponse, e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(phrAddressExistsResponse);
    }


    @PostMapping("/registration/mobileEmail/resend/otp")
    public ResponseEntity<SuccessResponse> resendOtp(@RequestBody ResendOTPRequest otpDTO) {

        LOGGER.info("Inside /registration/mobileEmail/resend/otp API ");

        ResponseEntity<SuccessResponse> BAD_REQUEST = applyValiationsForResendOtp(otpDTO);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        SuccessResponse verifyDetails;
        try{
        verifyDetails = this.webClient.post().uri(baseUrlPhr + resendOtpUrl).body(Mono.just(otpDTO), ResendOTPRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(SuccessResponse.class)
                .block();
    }
        catch (PhrException400 e) {
            verifyDetails = new SuccessResponse();
            return returnServerError400(verifyDetails, e);
        }
        catch (PhrException500 e) {
            verifyDetails = new SuccessResponse();
            return returnServerError500(verifyDetails, e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    @PostMapping("/registration/mobileEmail/details")
    public ResponseEntity<TransactionResponse> registerNewPhr(@RequestBody RegistrationByMobileOrEmailRequest otpDTO) {

        LOGGER.info("Inside /registration/mobileEmail/details API ");


        TransactionResponse verifyDetails;

        ResponseEntity<TransactionResponse> BAD_REQUEST = applyValidationsForRegisterNewPhr(otpDTO);
        if (BAD_REQUEST != null) return BAD_REQUEST;

        try {
            verifyDetails = this.webClient.post().uri(baseUrlPhr + registerUrl).body(Mono.just(otpDTO), RegistrationByMobileOrEmailRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(TransactionResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            verifyDetails = new TransactionResponse();
            return returnServerError400(verifyDetails, e);
        }
        catch (PhrException500 e) {
            verifyDetails = new TransactionResponse();
            return returnServerError500(verifyDetails, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);

    }

    @PostMapping("/registration/mobileEmail/create/phrAddress")
    public ResponseEntity<JwtResponse> registerPhr(@RequestBody CreatePHRRequest otpDTO) {

        LOGGER.info("Inside /registration/mobileEmail/create/phrAddress API ");


        ResponseEntity<JwtResponse> BAD_REQUEST = applyValiationsForRegisterPhr(otpDTO);
        if (BAD_REQUEST != null) return BAD_REQUEST;
        JwtResponse verifyDetails;
        try {
            verifyDetails = this.webClient.post().uri(baseUrlPhr + registerAdditional).body(Mono.just(otpDTO), CreatePHRRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(JwtResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            verifyDetails = new JwtResponse();
            return returnServerError400(verifyDetails, e);
        }
        catch (PhrException500 e) {
            verifyDetails = new JwtResponse();
            return returnServerError500(verifyDetails, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    @PostMapping("/login/mobileEmail/auth-confirm")
    public ResponseEntity<LoginPostVerificationRequestResponse> validateUserToken(@RequestBody @Valid LoginPostVerificationRequest otpDTO, @RequestHeader("Authorization") String auth,  Errors errors) {

        LOGGER.info("Inside /login/mobileEmail/auth-confirm API ");


        if(otpDTO == null) {
            LoginPostVerificationRequestResponse verifyErrorDetails = new LoginPostVerificationRequestResponse();
            verifyErrorDetails.setError("Request cannot be null");
            verifyErrorDetails.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyErrorDetails);
        }
        LoginPostVerificationRequestResponse verifyDetails;

        if(errors.hasErrors()) {
            verifyDetails = new LoginPostVerificationRequestResponse();
            verifyDetails.setError(errors.getAllErrors().toString());
            verifyDetails.setCode("400");
            verifyDetails.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyDetails);
        }

        verifyDetails = this.webClient.post().uri(baseUrlPhr + loginMobileEmailAuthConfirmUrl)
                .header("Authorization", auth)
                .body(Mono.just(otpDTO), LoginPostVerificationRequest.class)
                .retrieve()
                .onStatus(HttpStatus::is4xxClientError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                .onStatus(HttpStatus::is5xxServerError,
                        response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                .bodyToMono(LoginPostVerificationRequestResponse.class)
                .block();

        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);

    }

    @PostMapping(value = "/login/mobileEmail/auth-init", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LoginViaMobileEmailRequestResponse> generateOtpForLogin(@Valid @RequestBody  LoginViaMobileEmailRequestInit otpDTO, @RequestHeader("Authorization") String auth,Errors errors) {

        LOGGER.info("Inside /login/mobileEmail/auth-init API ");


        if(otpDTO == null) {
            LoginViaMobileEmailRequestResponse verifyErrorDetails = new LoginViaMobileEmailRequestResponse();
            verifyErrorDetails.setError("Request cannot be null");
            verifyErrorDetails.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyErrorDetails);
        }
        LoginViaMobileEmailRequestResponse verifyDetails;
        if(errors.hasErrors()) {
            verifyDetails = new LoginViaMobileEmailRequestResponse();
            verifyDetails.setError(errors.getAllErrors().toString());
            verifyDetails.setCode("400");
            verifyDetails.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyDetails);
        }
        try {
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
                    .block();

        }catch (PhrException400 e) {
            verifyDetails = new LoginViaMobileEmailRequestResponse();
            return returnServerError400(verifyDetails, e);
        }
        catch (PhrException500 e) {
            verifyDetails = new LoginViaMobileEmailRequestResponse();
            return returnServerError500(verifyDetails, e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }

    @PostMapping("/login/mobileEmail/pre-Verify")
    public ResponseEntity<LoginPreVerificationResponse> verifyUserOtp(@RequestBody @Valid LoginPreVerificationRequest otpDTO, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside /login/mobileEmail/pre-Verify API ");


        if(otpDTO == null) {
            LoginPreVerificationResponse verifyErrorDetails = new LoginPreVerificationResponse();
            verifyErrorDetails.setError("Request cannot be null");
            verifyErrorDetails.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyErrorDetails);
        }
        LoginPreVerificationResponse verifyDetails;
        if(errors.hasErrors()) {
            verifyDetails = new LoginPreVerificationResponse();
            verifyDetails.setError(errors.getAllErrors().toString());
            verifyDetails.setCode("400");
            verifyDetails.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyDetails);
        }

        try {
            verifyDetails = this.webClient.post().uri(baseUrlPhr + loginMobileEmailPreVerifyUrl).body(Mono.just(otpDTO), LoginPreVerificationRequest.class)
                    .header("Authorization", auth)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(LoginPreVerificationResponse.class)
                    .block();

        }catch (PhrException400 e) {
            verifyDetails = new LoginPreVerificationResponse();
            return returnServerError400(verifyDetails, e);
        }
        catch (PhrException500 e) {
            verifyDetails = new LoginPreVerificationResponse();
            return returnServerError500(verifyDetails, e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(verifyDetails);
    }


    private <T extends ServiceResponse> ResponseEntity<T> returnServerError400(T verifyDetails, ServiceException e) {
        LOGGER.error(e.getLocalizedMessage());
        verifyDetails = (T) new TransactionResponse();
        verifyDetails.setError(e.getLocalizedMessage());
        verifyDetails.setCode("400");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyDetails);
    }

    private <T extends ServiceResponse> ResponseEntity<T> returnServerError500(T verifyDetails, ServiceException e) {
        LOGGER.error(e.getLocalizedMessage());
        verifyDetails = (T) new TransactionResponse();
        verifyDetails.setError(e.getLocalizedMessage());
        verifyDetails.setCode("500");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(verifyDetails);
    }


    private ResponseEntity<TransactionResponse> applyValidationsForGenerateOtp(GenerateOTPRequest otpDTO) {
        if(otpDTO == null) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Request cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getValue()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Mobile number cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getAuthMode()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("AuthMode cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return null;
    }

    private ResponseEntity<TransactionWithPHRResponse> applyValidationsForValidateOtp(VerifyOTPRequest otpDTO) {
        if(otpDTO == null) {
            TransactionWithPHRResponse errorResponse = new TransactionWithPHRResponse();
            errorResponse.setError("Request cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getValue()) {
            TransactionWithPHRResponse errorResponse = new TransactionWithPHRResponse();
            errorResponse.setError("OTP number cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getSessionId()) {
            TransactionWithPHRResponse errorResponse = new TransactionWithPHRResponse();
            errorResponse.setError("sessionId cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return null;
    }

    private ResponseEntity<SuccessResponse> applyValiationsForResendOtp(ResendOTPRequest otpDTO) {
        if(otpDTO == null) {
            SuccessResponse errorResponse = new SuccessResponse();
            errorResponse.setError("Request cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        if(null == otpDTO.getSessionId()) {
            SuccessResponse errorResponse = new SuccessResponse();
            errorResponse.setError("SessionId cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return null;
    }

    private ResponseEntity<JwtResponse> applyValiationsForRegisterPhr(CreatePHRRequest otpDTO) {
        if(otpDTO == null) {
            JwtResponse errorResponse = new JwtResponse();
            errorResponse.setError("Request cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getSessionId()) {
            JwtResponse errorResponse = new JwtResponse();
            errorResponse.setError("sessionId cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getPhrAddress()) {
            JwtResponse errorResponse = new JwtResponse();
            errorResponse.setError("PhrAddress cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return null;
    }

    private ResponseEntity<TransactionResponse> applyValidationsForRegisterNewPhr(RegistrationByMobileOrEmailRequest otpDTO) {
        if(otpDTO == null) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Request cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getSessionId()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("SessionId object cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getName()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Name object cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getName().getFirst() || null == otpDTO.getName().getMiddle() || null == otpDTO.getName().getLast()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Invalid First/Middle/Last name. Null provided");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getDateOfBirth()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Date of birth object cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getDateOfBirth().getDate() || null == otpDTO.getDateOfBirth().getMonth() || null == otpDTO.getDateOfBirth().getYear()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Invalid Date of birth (Date/Month/Year). Null provided");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        if(null == otpDTO.getGender()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Gender cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getStateCode()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("StateCode cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        if(null == otpDTO.getDistrictCode()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("DistrictCode cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        if(null == otpDTO.getMobile()) {
            TransactionResponse errorResponse = new TransactionResponse();
            errorResponse.setError("Mobile cannot be Null");
            errorResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
        return null;
    }




}
