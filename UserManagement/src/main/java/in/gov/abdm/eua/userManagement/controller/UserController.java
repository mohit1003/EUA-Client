package in.gov.abdm.eua.userManagement.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import in.gov.abdm.eua.userManagement.dto.dhp.ErrorResponseDTO;
import in.gov.abdm.eua.userManagement.dto.phr.RegistrationByMobileOrEmailRequest;
import in.gov.abdm.eua.userManagement.dto.phr.UserDTO;
import in.gov.abdm.eua.userManagement.service.impl.UserServiceImpl;
import in.gov.abdm.uhi.common.dto.Ack;
import in.gov.abdm.uhi.common.dto.MessageAck;
import in.gov.abdm.uhi.common.dto.Response;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Login and Registration using HealthId number", description = "These APIs are intended to be used for user registration and login to EUA using ABHA number and PHR address. These APIs are using PHR's APIs internally")
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    final
    ObjectMapper objectMapper;

    final
    UserServiceImpl userService;


    public UserController(ObjectMapper objectMapper, UserServiceImpl userService) {
        this.objectMapper = objectMapper;
        this.userService = userService;
    }

    @PostMapping("/saveUser")
    public ResponseEntity<RegistrationByMobileOrEmailRequest> saveUser(@RequestBody String userDetails) {
        try {
            RegistrationByMobileOrEmailRequest userDTO = objectMapper.readValue(userDetails, RegistrationByMobileOrEmailRequest.class);
            userService.saveUser(userDTO);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorSchemaReady("Error Saving userData", "500"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(getErrorSchemaReady("Success", "200"));
    }

    @GetMapping("/getUser/{abhaAddress}")
    public ResponseEntity<RegistrationByMobileOrEmailRequest> getUser(@PathVariable(name = "abhaAddress") String abhaAddress) {

        RegistrationByMobileOrEmailRequest userDetails;
        try {
            userDetails = userService.getUserByAbhaAddress(abhaAddress);

        } catch (Exception e) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(getErrorSchemaReady("Error getting userData", "500"));
        }
       return ResponseEntity.status(HttpStatus.OK).body(userDetails);
    }

    private RegistrationByMobileOrEmailRequest getErrorSchemaReady(String message, String code) {
        RegistrationByMobileOrEmailRequest byMobileOrEmailRequest = new RegistrationByMobileOrEmailRequest();
        ErrorResponseDTO errorResponseDTO = new ErrorResponseDTO();
        errorResponseDTO.setMessage(message);
        errorResponseDTO.setCode(code);
        errorResponseDTO.setPath("UserService.userController");
        byMobileOrEmailRequest.setResponse(errorResponseDTO);
        return byMobileOrEmailRequest;
    }
}
