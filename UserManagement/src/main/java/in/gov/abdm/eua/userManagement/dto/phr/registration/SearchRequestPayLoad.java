/*
 * Patient Health Record Service
 * Create your PHR ID to track your health records in one place.
 *
 * OpenAPI spec version: V.1.0
 *
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

<<<<<<< HEAD
<<<<<<< HEAD:UserManagement/src/main/java/in/gov/abdm/eua/userManagement/dto/phr/registration/SearchRequestPayLoad.java
package in.gov.abdm.eua.userManagement.dto.phr.registration;
=======
package in.gov.abdm.eua.service.dto.phr.registration;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a:EUAclient/src/main/java/in/gov/abdm/eua/service/dto/phr/registration/SearchRequestPayLoad.java
=======
package in.gov.abdm.eua.userManagement.dto.phr.registration;
>>>>>>> 9dbb9f3cbb1548b28a5c1ef4dea8ca3f1144235a

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * SearchRequestPayLoad
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchRequestPayLoad {
    @NotNull(message = "healthIdNumber cannot be null")
    private Object healthIdNumber;

}
