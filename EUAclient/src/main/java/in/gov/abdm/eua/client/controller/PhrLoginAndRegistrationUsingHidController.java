package in.gov.abdm.eua.client.controller;

import in.gov.abdm.eua.client.dto.phr.CreatePHRRequest;
import in.gov.abdm.eua.client.dto.phr.*;
import in.gov.abdm.eua.client.dto.phr.login.*;
import in.gov.abdm.eua.client.dto.phr.registration.*;
import in.gov.abdm.eua.client.exceptions.PhrException400;
import in.gov.abdm.eua.client.exceptions.PhrException500;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("api/v1/")
public class PhrLoginAndRegistrationUsingHidController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PhrLoginAndRegistrationUsingHidController.class);


    private final WebClient webClient;

    @Value("${abdm.phr.base.url}")
    private String baseUrlPhr;
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


    public PhrLoginAndRegistrationUsingHidController(WebClient webClient) {
        this.webClient = webClient;
    }


    @PostMapping("/registration/hid/search/auth-mode")
    public ResponseEntity<SearchResponsePayLoad> findUserByHealthId(@Valid @RequestBody SearchRequestPayLoad searchRequestPayLoad,  Errors errors) {

        LOGGER.info("Inside /registration/hid/search/auth-mode API ");

        if(searchRequestPayLoad == null) {
            SearchResponsePayLoad responsePayLoad = new SearchResponsePayLoad();
            responsePayLoad.setError("Request cannot be null");
            responsePayLoad.setCode("400");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }
        SearchResponsePayLoad searchResponsePayLoad;

        if(errors.hasErrors()) {
            searchResponsePayLoad = new SearchResponsePayLoad();
            searchResponsePayLoad.setError(errors.getAllErrors().toString());
            searchResponsePayLoad.setCode("400");
            searchResponsePayLoad.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(searchResponsePayLoad);
        }

        try{
            searchResponsePayLoad = this.webClient.post().uri(baseUrlPhr + registrationByHidAuthModeUrl).body(Mono.just(searchRequestPayLoad), SearchRequestPayLoad.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(SearchResponsePayLoad.class)
                    .block();
        }
        catch (PhrException400 e) {
            searchResponsePayLoad = new SearchResponsePayLoad();
            return returnServerError400(searchResponsePayLoad, e);
        }
        catch (PhrException500 e) {
            searchResponsePayLoad = new SearchResponsePayLoad();
            return returnServerError500(searchResponsePayLoad, e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(searchResponsePayLoad);
    }

    @PostMapping("/registration/hid/auth-init")
    public ResponseEntity<TransactionResponse> generateTransactionOtp(@Valid @RequestBody LoginViaMobileEmailRequestRegistration loginViaMobileEmailRequest, Errors errors) {

        LOGGER.info("Inside /registration/hid/auth-init API ");

        if(loginViaMobileEmailRequest == null) {
            TransactionResponse responsePayLoad = new TransactionResponse();
            responsePayLoad.setError("Request cannot be null");
            responsePayLoad.setCode("400");
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }
        TransactionResponse transactionResponse;
        if(errors.hasErrors()) {
            transactionResponse = new TransactionResponse();
            transactionResponse.setError(errors.getAllErrors().toString());
            transactionResponse.setCode("400");
            transactionResponse.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transactionResponse);
        }

//        if(loginViaMobileEmailRequest.getValue())

        try{
            transactionResponse = this.webClient.post().uri(baseUrlPhr + registrationByHidAuthInitUrl).body(Mono.just(loginViaMobileEmailRequest), LoginViaMobileEmailRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(TransactionResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            transactionResponse = new TransactionResponse();
            return returnServerError400(transactionResponse, e);
        }
        catch (PhrException500 e) {
            transactionResponse = new TransactionResponse();
            return returnServerError500(transactionResponse, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(transactionResponse);
    }


    @PostMapping("/registration/hid/confirm-init")
    public ResponseEntity<HidResponse> verifyUserOtp(@Valid @RequestBody LoginRequestPayload loginRequestPayload, Errors errors) {

        LOGGER.info("Inside /registration/hid/confirm-init API ");


        HidResponse hidResponse;

        if(loginRequestPayload == null) {
            hidResponse = new HidResponse();
            hidResponse.setError("Request cannot be null");
            hidResponse.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hidResponse);
        }
        if(errors.hasErrors()) {
            hidResponse = new HidResponse();
            hidResponse.setError(errors.getAllErrors().toString());
            hidResponse.setCode("400");
            hidResponse.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(hidResponse);
        }
        try{
            hidResponse = this.webClient.post().uri(baseUrlPhr + registrationByHidConfirmInitUrl).body(Mono.just(loginRequestPayload), LoginRequestPayload.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(HidResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            hidResponse = new HidResponse();
            return returnServerError400(hidResponse, e);
        }
        catch (PhrException500 e) {
            hidResponse = new HidResponse();
            return returnServerError500(hidResponse, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(hidResponse);
    }

    @PostMapping("/registration/hid/create/phrAddress")
    public ResponseEntity<JwtResponseHid> createPhrAddress(@Valid @RequestBody CreatePHRRequest createPHRRequest, Errors errors) {

        LOGGER.info("Inside /registration/hid/create/phrAddress API ");


        JwtResponseHid jwtResponse;

        if(createPHRRequest == null) {
            JwtResponseHid response= new JwtResponseHid();
            response.setError("Request cannot be null");
            response.setCode("400");
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        if(errors.hasErrors()) {
            jwtResponse = new JwtResponseHid();
            jwtResponse.setError(errors.getAllErrors().toString());
            jwtResponse.setCode("400");
            jwtResponse.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(jwtResponse);
        }
        try{
            jwtResponse = this.webClient.post().uri(baseUrlPhr + registrationByHidCreatePhrUrl).body(Mono.just(createPHRRequest), CreatePHRRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(JwtResponseHid.class)
                    .block();
        }
        catch (PhrException400 e) {
            jwtResponse = new JwtResponseHid();
            return returnServerError400(jwtResponse, e);
        }
        catch (PhrException500 e) {
            jwtResponse = new JwtResponseHid();
            return returnServerError500(jwtResponse, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(jwtResponse);
    }

    @PostMapping("/login/hid/auth-init")
    public ResponseEntity<LoginViaMobileEmailRequestResponse> generateOtpForHidLogin(@Valid @RequestBody LoginViaMobileEmailRequest viaMobileEmailRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside /login/hid/auth-init API ");


        LoginViaMobileEmailRequestResponse emailRequestResponse;
        if(viaMobileEmailRequest == null) {
            LoginViaMobileEmailRequestResponse response = new LoginViaMobileEmailRequestResponse();
            response.setError("Request cannot be null");
            response.setCode("400");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        if(errors.hasErrors()) {
            emailRequestResponse = new LoginViaMobileEmailRequestResponse();
            emailRequestResponse.setCode("400");
            emailRequestResponse.setError(errors.getAllErrors().toString());
            emailRequestResponse.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(emailRequestResponse);
        }
        try{
            String uri = baseUrlPhr + loginByHidAuthInitUrl;
            emailRequestResponse = this.webClient.post()
                    .uri(uri).body(Mono.just(viaMobileEmailRequest), LoginViaMobileEmailRequest.class)
                    .header("Authorization", auth)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(LoginViaMobileEmailRequestResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            emailRequestResponse = new LoginViaMobileEmailRequestResponse();
            return returnServerError400(emailRequestResponse, e);
        }
        catch (PhrException500 e) {
            emailRequestResponse = new LoginViaMobileEmailRequestResponse();
            return returnServerError500(emailRequestResponse, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(emailRequestResponse);
    }

    @PostMapping(value = "/login/hid/search/auth-mode")
    public ResponseEntity<SearchResponsePayLoad> searchUserByHealthIdForLogin(@Valid @RequestBody SearchByHealthIdNumberRequest byHealthIdNumberRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside /login/hid/search/auth-mode API ");

        SearchResponsePayLoad responsePayLoad;
        if(byHealthIdNumberRequest == null) {
            responsePayLoad = new SearchResponsePayLoad();
            responsePayLoad.setCode("400");
            responsePayLoad.setError("Request cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        if(errors.hasErrors()) {
            responsePayLoad = new SearchResponsePayLoad();
            responsePayLoad.setCode("400");
            responsePayLoad.setError(errors.getAllErrors().toString());
            responsePayLoad.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        if(!byHealthIdNumberRequest.getHealthIdNumber().toString().matches("[\\d]{14}")) {
            responsePayLoad = new SearchResponsePayLoad();
            responsePayLoad.setCode("400");
            responsePayLoad.setError("Invalid healthIdNumber.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }
        try{
            responsePayLoad = this.webClient.post()
                    .uri(baseUrlPhr + loginByHidAuthModeUrl).body(Mono.just(byHealthIdNumberRequest), SearchByHealthIdNumberRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(SearchResponsePayLoad.class)
                    .block();
        }
        catch (PhrException400 e) {
            responsePayLoad = new SearchResponsePayLoad();
            return returnServerError400(responsePayLoad, e);
        }
        catch (PhrException500 e) {
            responsePayLoad = new SearchResponsePayLoad();
            return returnServerError500(responsePayLoad, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }

    @PostMapping("/login/phrAddress/auth-init")
    public ResponseEntity<AuthInitResponse> generateOtpPhrLogin(@Valid @RequestBody LoginViaPhrRequest loginViaPhrRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside /login/phrAddress/auth-init API ");

        AuthInitResponse responsePayLoad;

        if(loginViaPhrRequest == null) {
            responsePayLoad = new AuthInitResponse();
            responsePayLoad.setCode("400");
            responsePayLoad.setError("Request cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        if(errors.hasErrors()) {
            responsePayLoad = new AuthInitResponse();
            responsePayLoad.setCode("400");
            responsePayLoad.setError(errors.getAllErrors().toString());
            responsePayLoad.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        try{
            responsePayLoad = this.webClient.post().uri(baseUrlPhr + loginByPhrAuthInitUrl)
                    .header("Authorization", auth)
                    .body(Mono.just(loginViaPhrRequest), LoginViaPhrRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(AuthInitResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            responsePayLoad = new AuthInitResponse();
            return returnServerError400(responsePayLoad, e);
        }
        catch (PhrException500 e) {
            responsePayLoad = new AuthInitResponse();
            return returnServerError500(responsePayLoad, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }

    @PostMapping("/login/phrAddress/auth-confirm")
    public ResponseEntity<AuthConfirmResponse> verifyOtpPhrLogin(@Valid @RequestBody VerifyPasswordOtpLoginRequest passwordOtpLoginRequest, @RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside /login/phrAddress/auth-confirm API ");

        AuthConfirmResponse responsePayLoad;

        if(passwordOtpLoginRequest == null) {
            responsePayLoad = new AuthConfirmResponse();
            responsePayLoad.setCode("400");
            responsePayLoad.setError("Request cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        if(errors.hasErrors()) {
            responsePayLoad = new AuthConfirmResponse();
            responsePayLoad.setCode("400");
            responsePayLoad.setError(errors.getAllErrors().toString());
            responsePayLoad.setPath(errors.getNestedPath());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        try{
            responsePayLoad = this.webClient.post().uri(baseUrlPhr + loginByPhrAuthConfirmUrl)
                    .header("Authorization", auth)
                    .body(Mono.just(passwordOtpLoginRequest), VerifyPasswordOtpLoginRequest.class)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(AuthConfirmResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            responsePayLoad = new AuthConfirmResponse();
            return returnServerError400(responsePayLoad, e);
        }
        catch (PhrException500 e) {
            responsePayLoad = new AuthConfirmResponse();
            return returnServerError500(responsePayLoad, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }



    @GetMapping("/login/phrAddress/search/auth-mode")
    public ResponseEntity<SearchPhrAuthResponse> findUserByHealthIdForPhrLogin(@RequestParam(name = "phrAddress") String phrAddress) {

        LOGGER.info("Inside /login/phrAddress/search/auth-mode API ");

        SearchPhrAuthResponse responsePayLoad;

        if(phrAddress.isBlank()) {
            responsePayLoad = new SearchPhrAuthResponse();
            responsePayLoad.setCode("400");
            responsePayLoad.setError("Mandatory request parameter phrAddress cannot be null");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        if(!phrAddress.matches("[a-z0-9]+@[a-z]+")) {
            responsePayLoad = new SearchPhrAuthResponse();
            responsePayLoad.setCode("400");
            responsePayLoad.setError("Mandatory request parameter phrAddress is invalid");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsePayLoad);
        }

        try{
            String uri = baseUrlPhr + loginByPhrAuthModeUrl + "?phrAddress=" + phrAddress;
            responsePayLoad = this.webClient.get().uri(uri)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(SearchPhrAuthResponse.class)
                    .block();
        }
        catch (PhrException400 e) {
            responsePayLoad = new SearchPhrAuthResponse();
            return returnServerError400(responsePayLoad, e);
        }
        catch (PhrException500 e) {
            responsePayLoad = new SearchPhrAuthResponse();
            return returnServerError500(responsePayLoad, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }

    @PostMapping("/resend/login/otp")
    public ResponseEntity<LoginResendOtp> loginResendOtp(@Valid @RequestBody LoginResendOtp loginResendOtp,@RequestHeader("Authorization") String auth, Errors errors) {

        LOGGER.info("Inside /resend/login/otp API ");

        LoginResendOtp responsePayLoad;

        try{

            String uri = baseUrlPhr + loginResendOtpUrl;
            responsePayLoad = this.webClient.post()
                    .uri(uri)
                    .body(Mono.just(loginResendOtp), LoginResendOtp.class)
                    .header("Authorization", auth)
                    .retrieve()
                    .onStatus(HttpStatus::is4xxClientError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException400(error))))
                    .onStatus(HttpStatus::is5xxServerError,
                            response -> response.bodyToMono(String.class).flatMap(error -> Mono.error(new PhrException500(error))))
                    .bodyToMono(LoginResendOtp.class)
                    .block();
        }
        catch (PhrException400 e) {
            responsePayLoad = new LoginResendOtp();
            return returnServerError400(responsePayLoad, e);
        }
        catch (PhrException500 e) {
            responsePayLoad = new LoginResendOtp();
            return returnServerError500(responsePayLoad, e);
        }
        return ResponseEntity.status(HttpStatus.OK).body(responsePayLoad);
    }





    private <T extends ServiceResponse> ResponseEntity<T> returnServerError400(T verifyDetails, ServiceException e) {
        LOGGER.error(e.getLocalizedMessage());
        verifyDetails = (T) new TransactionResponse();
        verifyDetails.setError(e.getLocalizedMessage());
        verifyDetails.setCode("4XX");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyDetails);
    }

    private <T extends ServiceResponse> ResponseEntity<T> returnServerError500(T verifyDetails, ServiceException e) {
        LOGGER.error(e.getLocalizedMessage());
        verifyDetails = (T) new TransactionResponse();
        verifyDetails.setError(e.getLocalizedMessage());
        verifyDetails.setCode("500");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(verifyDetails);
    }


    
}
