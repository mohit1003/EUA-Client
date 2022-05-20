package in.gov.abdm.eua.client.controller;

import in.gov.abdm.eua.client.dto.phr.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class PhrLoginAndRegistrationUsingMobileEmailControllerTest {

    @Mock
    WebClient webClient;
    @Mock
    WebClient.RequestBodyUriSpec requestBodyUriSpec;
    @Mock
    WebClient.RequestHeadersSpec requestHeadersSpec;
    @Mock
    WebClient.UriSpec uriSpec;
    @Mock
    WebClient.RequestBodySpec requestBodySpec;
    @Mock
    WebClient.ResponseSpec responseSpec;
    @InjectMocks
    PhrLoginAndRegistrationUsingMobileEmailController phrLoginAndRegistrationUsingMobileEmailController;
    GenerateOTPRequest otpDTO;

    @Autowired
    private MockMvc mockMvc;



    @BeforeEach
    public void setUp(){

      MockitoAnnotations.openMocks(this);
        otpDTO = new GenerateOTPRequest("boHpUHCCMad3BmRuFetZ+Xz31igXr6kneFDO4IZY0UpUVm3ep8RmW+lO9PZju1pnmVX7GPV5PuindHhEiypCAXugqaX9bgKHbsZrSWQYae4tcLLwJ0qCpjG8AzRCQBFXZDvxH/+T9ebVNiksIicU7wsEBv7q+3Gxv9Z3gXd+qvx/o3fxV8UXGXRirMt7bNYVLyZocFWu3pqPI7lZDZg2y8bjexNzV3B7xXCLgzB9Iqdqib5gBsogZoONnNGJ9+JeFlTyq+r3szO4xuYhGEleSahyvcB3s4nsjEow0HOG9FCQ4so3E0CX6XXf++rUU82cji+5eX6Pa/61XOUmGjhWiA==", "MOBILE_OTP");
        WebClient restClient = WebClient.create();

        when(webClient.post()).thenReturn(requestBodyUriSpec);
//        when(webClient.get()).thenReturn();
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.header(any(),any())).thenReturn(requestBodySpec);

        when(requestHeadersSpec.header(any(),any())).thenReturn(requestHeadersSpec);



        when(requestBodySpec.accept(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.body(any())).thenReturn(requestHeadersSpec);
        when(requestBodyUriSpec.retrieve()).thenReturn(responseSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(ArgumentMatchers.<Class<String>>notNull()))
                .thenReturn(Mono.just("resp"));

    }

    // Test cases for generateOtp begins --------
    @Test
    @Description(" To test that when provided empty/null request should return 400 bad-request error")
    public void whenCalledGenerateOtpMethodOtpDtoValueShouldNotBeNull()  {
        otpDTO = null;
        TransactionResponse otpGenerateResponse = new TransactionResponse();
        otpGenerateResponse.setError("Request cannot be Null");
        otpGenerateResponse.setCode("400");
        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.generateOtp(otpDTO)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(otpGenerateResponse));
    }

    @Test
    @Description(" To test that when provided empty/null mobile number should return 400 bad-request error")
    public void whenCalledGenerateOtpMethodMobileValueShouldNotBeNull() {
        otpDTO.setValue(null);
        TransactionResponse otpGenerateResponse = new TransactionResponse();
        otpGenerateResponse.setError("Mobile number cannot be Null");
        otpGenerateResponse.setCode("400");
        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.generateOtp(otpDTO)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(otpGenerateResponse));
    }

    @Test
    @Description(" To test that when provided empty/null AuthMode number should return 400 bad-request error")
    public void whenCalledGenerateOtpMethodAuthModeValueShouldNotBeNull() {
        otpDTO.setAuthMode(null);
        TransactionResponse otpGenerateResponse = new TransactionResponse();
        otpGenerateResponse.setError("AuthMode cannot be Null");
        otpGenerateResponse.setCode("400");
        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.generateOtp(otpDTO)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(otpGenerateResponse));
    }

    // Test cases for generateOtp ends --------
    // Test cases for validateOtp() begins --------

    @Test
    @Description("To test when provided null request should return 400 bad-request error")
    public void whenCalledvalidateOtpRequestShouldNotBeNull() {
        VerifyOTPRequest verifyOTPRequest = null;
        TransactionWithPHRResponse response = new TransactionWithPHRResponse();
        response.setError("Request cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.validateOtp(verifyOTPRequest)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null OTP should return 400 bad-request error")
    public void whenCalledValidateOtpShouldNotBeNull() {
        VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
        verifyOTPRequest.setValue(null);
        verifyOTPRequest.setSessionId("348573ueu47464u");
        TransactionWithPHRResponse response = new TransactionWithPHRResponse();
        response.setError("OTP number cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.validateOtp(verifyOTPRequest)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null SessionId should return 400 bad-request error")
    public void whenCalledValidateSessionIdShouldNotBeNull() {
        VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
        verifyOTPRequest.setValue("boHpUHCCMad3BmRuFetZ+Xz31igXr6kneFDO4IZY0UpUVm3ep8RmW+lO9PZju1pnmVX7GPV5PuindHhEiypCAXugqaX9bgKHbsZrSWQYae4tcLLwJ0qCpjG8AzRCQBFXZDvxH/+T9ebVNiksIicU7wsEBv7q+3Gxv9Z3gXd+qvx/o3fxV8UXGXRirMt7bNYVLyZocFWu3pqPI7lZDZg2y8bjexNzV3B7xXCLgzB9Iqdqib5gBsogZoONnNGJ9+JeFlTyq+r3szO4xuYhGEleSahyvcB3s4nsjEow0HOG9FCQ4so3E0CX6XXf++rUU82cji+5eX6Pa/61XOUmGjhWiA==");
        verifyOTPRequest.setSessionId(null);
        TransactionWithPHRResponse response = new TransactionWithPHRResponse();
        response.setError("SessionId cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.validateOtp(verifyOTPRequest)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

// Test cases for validateOtp() ends --------
// Test cases for resendOtp() begins --------

    @Test
    @Description("To test when provided null request should return 400 bad-request error")
    public void whenCalledResendOtpRequestShouldNotBeNull() {
        ResendOTPRequest request = null;
        SuccessResponse response = new SuccessResponse();
        response.setError("Request cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.resendOtp(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));

    }

    @Test
    @Description("To test when provided null SessionId should return 400 bad-request error")
    public void whenCalledResendOtpSessionIdShouldNotBeNull() {
        ResendOTPRequest request = new ResendOTPRequest(null);
        SuccessResponse response = new SuccessResponse();
        response.setError("SessionId cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.resendOtp(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));

    }
// Test cases for resendOtp() ends --------
// Test cases for registerPhr() begins --------
    @Test
    @Description("To test when provided null request should return 400 bad-request error")
    public void whenCalledRegisterPhrRequestShouldNotBeNull() {
        CreatePHRRequest verifyOTPRequest = null;
        JwtResponse response = new JwtResponse();
        response.setError("Request cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerPhr(verifyOTPRequest)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null sessionId should return 400 bad-request error")
    public void whenCalledRegisterPhrSessionIdShouldNotBeNull() {
        CreatePHRRequest verifyOTPRequest = new CreatePHRRequest();
        verifyOTPRequest.setPassword("scsdc");
        verifyOTPRequest.setPhrAddress("sdcsdcsdc");
        verifyOTPRequest.setIsAlreadyExistedPHR(true);
        JwtResponse response = new JwtResponse();
        response.setError("sessionId cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerPhr(verifyOTPRequest)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null phrAddress should return 400 bad-request error")
    public void whenCalledRegisterPhrPhrAddressShouldNotBeNull() {
        CreatePHRRequest verifyOTPRequest = new CreatePHRRequest();
        verifyOTPRequest.setPassword("scsdc");
        verifyOTPRequest.setSessionId("sdcsdcsdc");
        verifyOTPRequest.setIsAlreadyExistedPHR(true);
        JwtResponse response = new JwtResponse();
        response.setError("phrAddress cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerPhr(verifyOTPRequest)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    // Test cases for registerPhr() ends --------
    // Test cases for registerNewPhr() begins --------

    @Test
    @Description("To test when provided null request should return 400 bad-request error")
    public void whenCalledregisterNewPhrRequestShouldNotBeNull() {
        TransactionResponse response = new TransactionResponse();
        response.setError("Request cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(null)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null sessionId should return 400 bad-request error")
    public void whenCalledregisterNewPhrSessionIdShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest(null,new RegistrationByMobileOrEmailRequest.NamePhrRegistration("","",""),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),"anyString()","anyString()","anyString()","anyString()","anyString()","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("SessionId cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null name should return 400 bad-request error")
    public void whenCalledRegisterNewPhrNameShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("acsd",null,new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),"anyString()","anyString()","anyString()","anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("Name cannot be null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null DateOfBirth should return 400 bad-request error")
    public void whenCalledRegisterNewPhrDOBShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration("", "", ""),null,"anyString()","anyString()","anyString()","anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("Date of birth object cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null First Name/ Last name/ middle name should return 400 bad-request error")
    public void whenCalledRegisterNewPhrNameFLMShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration(null,null, null),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),"anyString()","anyString()","anyString()","anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("Invalid First/Middle/Last name. Null provided");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null Date/Month/ year should return 400 bad-request error")
    public void whenCalledRegisterNewPhrDOB_DMYShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration("", "", ""),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr(null,null,null),"anyString()","anyString()","anyString()","anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("Invalid Date of birth (Date/Month/Year). Null provided");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null gender should return 400 bad-request error")
    public void whenCalledRegisterNewPhrGenderShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration("","",""),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),null,"anyString()","anyString()","anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("Gender cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null stateCode should return 400 bad-request error")
    public void whenCalledRegisterNewPhrStateCodeShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration("","",""),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),"anyString()",null,"anyString()","anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("StateCode cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null DistrictCode should return 400 bad-request error")
    public void whenCalledRegisterNewPhrDistrictCodeShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration("","",""),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),"anyString()","anyString()",null,"anyString()","anyString","anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("DistrictCode cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }

    @Test
    @Description("To test when provided null Mobile should return 400 bad-request error")
    public void whenCalledRegisterNewPhrMobileShouldNotBeNull() {
        RegistrationByMobileOrEmailRequest request = new RegistrationByMobileOrEmailRequest("any()",new RegistrationByMobileOrEmailRequest.NamePhrRegistration("","",""),new RegistrationByMobileOrEmailRequest.DateOfBirthRegistrationPhr("","",""),"anyString()","anyString()","anyString()","anyString()",null,"anyString()","anyString()","anyString()");

        TransactionResponse response = new TransactionResponse();
        response.setError("DistrictCode cannot be Null");
        response.setCode("400");

        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.registerNewPhr(request)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response));
    }
    // Test cases for registerNewPhr() ends --------

//    @Test
//    @Description("To test get states method")
//    public void testGetStates() {
//        Mockito.when(phrLoginAndRegistrationController.getAllStates()).thenReturn(any());
//        Assertions.assertThat(phrLoginAndRegistrationController.getAllStates()).isNotNull();
//    }


    // Test cases for login auth-init() begins

    @Test
    @Description("To test that given null request should return 400 bad request")
    public void givenNullRequestShouldReturn400Error() {
        LoginViaMobileEmailRequestResponse verifyErrorDetails = new LoginViaMobileEmailRequestResponse();
        verifyErrorDetails.setError("Request cannot be null");
        verifyErrorDetails.setCode("400");
        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.generateOtpForLogin(null, null, null)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyErrorDetails));
    }


    @Test
    @Description("To test that given null value should return 400 bad request")
    public void givenNullValueShouldReturn400Error() throws Exception {
        String request = """
                {
                  "value": null,
                  "purpose": "CM_ACCESS",
                  "authMode": "MOBILE_OTP",
                  "requester": {
                    "type": "PHR",
                    "id": "IN0400XX"
                  }
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-init").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null purpose should return 400 bad request")
    public void givenNullPurposeShouldReturn400Error() throws Exception {
        String request = """
                {
                  "value": "yJ2hY5bc2g3P2pQyca/ER6VYQ8TGMj/VN42h9xkh/3jAwJQtZEspnhrtEKqwFXt1+8budi64CPlUEzbkwUsCotIOMm8idfSX+SQyb8VlqLxxIkAzGvmXjWrbQUNEUWnnJjzkIjweNmj8GJ2u0uRdrAGpBc1vMoMz5XD2SGfFttvmziTtucq5w2dOoAPOni4Bl7sfii3Qyo8Szl1/tXNnZbDZi8HH9Cpajno4pFiu6mQDVTkkyDHTqyo7Bv3IFpdNYiRDAZ1yh1cBOfufMy1gSZQetCwETFxdsOgw7JvKL/gEN+RAFKZF2oUriCsAkYYbxW1cfrqa/YRXUw0ho+n4Jw==",
                  "purpose": null,
                  "authMode": "MOBILE_OTP",
                  "requester": {
                    "type": "PHR",
                    "id": "IN0400XX"
                  }
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-init")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null AuthMode should return 400 bad request")
    public void givenNullAuthModeShouldReturn400Error() throws Exception {
        String request = """
                {
                  "value": "yJ2hY5bc2g3P2pQyca/ER6VYQ8TGMj/VN42h9xkh/3jAwJQtZEspnhrtEKqwFXt1+8budi64CPlUEzbkwUsCotIOMm8idfSX+SQyb8VlqLxxIkAzGvmXjWrbQUNEUWnnJjzkIjweNmj8GJ2u0uRdrAGpBc1vMoMz5XD2SGfFttvmziTtucq5w2dOoAPOni4Bl7sfii3Qyo8Szl1/tXNnZbDZi8HH9Cpajno4pFiu6mQDVTkkyDHTqyo7Bv3IFpdNYiRDAZ1yh1cBOfufMy1gSZQetCwETFxdsOgw7JvKL/gEN+RAFKZF2oUriCsAkYYbxW1cfrqa/YRXUw0ho+n4Jw==",
                  "purpose": "CM_ACCESS",
                  "authMode": null,
                  "requester": {
                    "type": "PHR",
                    "id": "IN0400XX"
                  }
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-init").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null Requester Type should return 400 bad request")
    public void givenNullRequesterTypeShouldReturn400Error() throws Exception {
        String request = """
                {
                  "value": "yJ2hY5bc2g3P2pQyca/ER6VYQ8TGMj/VN42h9xkh/3jAwJQtZEspnhrtEKqwFXt1+8budi64CPlUEzbkwUsCotIOMm8idfSX+SQyb8VlqLxxIkAzGvmXjWrbQUNEUWnnJjzkIjweNmj8GJ2u0uRdrAGpBc1vMoMz5XD2SGfFttvmziTtucq5w2dOoAPOni4Bl7sfii3Qyo8Szl1/tXNnZbDZi8HH9Cpajno4pFiu6mQDVTkkyDHTqyo7Bv3IFpdNYiRDAZ1yh1cBOfufMy1gSZQetCwETFxdsOgw7JvKL/gEN+RAFKZF2oUriCsAkYYbxW1cfrqa/YRXUw0ho+n4Jw==",
                  "purpose": "CM_ACCESS",
                  "authMode": "MOBILE_OTP",
                  "requester": {
                    "type": null,
                    "id": "IN0400XX"
                  }
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-init").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null Requester Id should return 400 bad request")
    public void givenNullRequesterIdShouldReturn400Error() throws Exception {
        String request = """
                {
                  "value": "yJ2hY5bc2g3P2pQyca/ER6VYQ8TGMj/VN42h9xkh/3jAwJQtZEspnhrtEKqwFXt1+8budi64CPlUEzbkwUsCotIOMm8idfSX+SQyb8VlqLxxIkAzGvmXjWrbQUNEUWnnJjzkIjweNmj8GJ2u0uRdrAGpBc1vMoMz5XD2SGfFttvmziTtucq5w2dOoAPOni4Bl7sfii3Qyo8Szl1/tXNnZbDZi8HH9Cpajno4pFiu6mQDVTkkyDHTqyo7Bv3IFpdNYiRDAZ1yh1cBOfufMy1gSZQetCwETFxdsOgw7JvKL/gEN+RAFKZF2oUriCsAkYYbxW1cfrqa/YRXUw0ho+n4Jw==",
                  "purpose": "CM_ACCESS",
                  "authMode": "MOBILE_OTP",
                  "requester": {
                    "type": "PHR",
                    "id": null
                  }
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-init").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null Requester should return 400 bad request")
    public void givenNullRequesterShouldReturn400Error() throws Exception {
        String request = """
                {
                  "value": "yJ2hY5bc2g3P2pQyca/ER6VYQ8TGMj/VN42h9xkh/3jAwJQtZEspnhrtEKqwFXt1+8budi64CPlUEzbkwUsCotIOMm8idfSX+SQyb8VlqLxxIkAzGvmXjWrbQUNEUWnnJjzkIjweNmj8GJ2u0uRdrAGpBc1vMoMz5XD2SGfFttvmziTtucq5w2dOoAPOni4Bl7sfii3Qyo8Szl1/tXNnZbDZi8HH9Cpajno4pFiu6mQDVTkkyDHTqyo7Bv3IFpdNYiRDAZ1yh1cBOfufMy1gSZQetCwETFxdsOgw7JvKL/gEN+RAFKZF2oUriCsAkYYbxW1cfrqa/YRXUw0ho+n4Jw==",
                  "purpose": "CM_ACCESS",
                  "authMode": "MOBILE_OTP",
                  "requester": null
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-init").contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Test cases for login auth-init() begins

    @Test
    @Description("To test that given null Requester should return 400 bad request")
    public void givenNullRequestForAuthInitShouldReturn400Error() throws Exception {
        LoginViaMobileEmailRequestResponse verifyErrorDetails = new LoginViaMobileEmailRequestResponse();
        verifyErrorDetails.setError("Request cannot be null");
        verifyErrorDetails.setCode("400");
        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.generateOtpForLogin(null, null, null)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyErrorDetails));

    }

    @Test
    @Description("To test that given null transactionId should return 400 bad request")
    public void givenNullTransactionIdShouldReturn400Error() throws Exception {
        String request = """
                {
                  "transactionId": null,
                  "authcode": "tSCaVUjHwHiMVCokz7u3ogfop5r7ON5GmVY4rJNaQhoVAMlZl5lDqbb4vobfFMsQ1zO404gkWqPqLoDCdavx+JJ5pxprDpRo+PbeV44q51xr5OoNW2ITy9x6WM81KF9o7OnIU3FOGg09jqcJ/By3S8ICWxzJDKVwCJPehHtjhSFiy+mdWEjKkBTrEWJRTy3ZOkij+fskm+JjLoJlIF0TmA94Jb/avX0/LrnacpWEYWAHd0R/8/HIeITVNwG5hnsuRyIcIKKy7bEuYul8wJDD8RPBhL/gIAV4c5zDCb518o1MJGQtNg8Yf/zcROdaynWrBHIh2tacPrxmLHiZHD+BHQ==",
                  "requesterId": "IN0410XX"
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/pre-Verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null authcode should return 400 bad request")
    public void givenNullAuthcodeShouldReturn400Error() throws Exception {
        String request = """
                {
                  "transactionId": "a825f76b-0696-40f3-864c-5a3a5b389a83",
                  "authcode": null,
                  "requesterId": "IN0410XX"
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/pre-Verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null requesterId should return 400 bad request")
    public void givenNullRequesterIdForAuthInit_ShouldReturn400Error() throws Exception {
        String request = """
                {
                  "transactionId": "a825f76b-0696-40f3-864c-5a3a5b389a83",
                  "authcode": "tSCaVUjHwHiMVCokz7u3ogfop5r7ON5GmVY4rJNaQhoVAMlZl5lDqbb4vobfFMsQ1zO404gkWqPqLoDCdavx+JJ5pxprDpRo+PbeV44q51xr5OoNW2ITy9x6WM81KF9o7OnIU3FOGg09jqcJ/By3S8ICWxzJDKVwCJPehHtjhSFiy+mdWEjKkBTrEWJRTy3ZOkij+fskm+JjLoJlIF0TmA94Jb/avX0/LrnacpWEYWAHd0R/8/HIeITVNwG5hnsuRyIcIKKy7bEuYul8wJDD8RPBhL/gIAV4c5zDCb518o1MJGQtNg8Yf/zcROdaynWrBHIh2tacPrxmLHiZHD+BHQ==",
                  "requesterId": null
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/pre-Verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    // Test cases for login auth-init() ends

    //Tests cases for login validateUserToken() begins
    @Test
    @Description("To test that given null Request should return 400 bad request")
    public void givenNullRequestForPreVerifyShouldReturn400Error() throws Exception {
        LoginPostVerificationRequestResponse verifyErrorDetails = new LoginPostVerificationRequestResponse();
        verifyErrorDetails.setError("Request cannot be null");
        verifyErrorDetails.setCode("400");
        Assertions.assertThat(phrLoginAndRegistrationUsingMobileEmailController.validateUserToken(null, null, null)).isEqualTo(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(verifyErrorDetails));
    }

    @Test
    @Description("To test that given null transactionId should return 400 bad request")
    public void givenNullTransactionIdForAuthInit_ShouldReturn400Error() throws Exception {
        String request = """
                {
                  "transactionId": null,
                  "authcode": "tSCaVUjHwHiMVCokz7u3ogfop5r7ON5GmVY4rJNaQhoVAMlZl5lDqbb4vobfFMsQ1zO404gkWqPqLoDCdavx+JJ5pxprDpRo+PbeV44q51xr5OoNW2ITy9x6WM81KF9o7OnIU3FOGg09jqcJ/By3S8ICWxzJDKVwCJPehHtjhSFiy+mdWEjKkBTrEWJRTy3ZOkij+fskm+JjLoJlIF0TmA94Jb/avX0/LrnacpWEYWAHd0R/8/HIeITVNwG5hnsuRyIcIKKy7bEuYul8wJDD8RPBhL/gIAV4c5zDCb518o1MJGQtNg8Yf/zcROdaynWrBHIh2tacPrxmLHiZHD+BHQ==",
                  "requesterId": "IN0401XX"
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @Description("To test that given null authcode should return 400 bad request")
    public void givenNullAuthcodeForAuthInit_ShouldReturn400Error() throws Exception {
        String request = """
                {
                  "transactionId": "dsvdvdsvdsvsdefcszc",
                  "authcode": null,
                  "requesterId": "IN0401XX"
                }
                """;

        mockMvc = MockMvcBuilders
                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/login/mobileEmail/auth-confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    //Tests cases for login validateUserToken() ends
    //TC for /states begins

//    @Test
//    @Description("To test that given null authcode should return 400 bad request")
//    public void whenCalledStatesApi_ShouldReturnAllStates() throws Exception {
//
//        mockMvc = MockMvcBuilders
//                .standaloneSetup(phrLoginAndRegistrationUsingMobileEmailController).build();
//        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/states"))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//    }


    //TC for /states ends



}