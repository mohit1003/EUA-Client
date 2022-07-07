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

package in.gov.abdm.eua.userManagement.dto.phr.registration;


import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * HidResponseName
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class HidName {
    @NotNull(message = "Firstname cannot be null")
    @JsonAlias("first")
    private String firstName;

    @NotNull(message = "middleName cannot be null")
    @JsonAlias("middle")
    private String middleName;

    @NotNull(message = "LastName cannot be null")
    @JsonAlias("last")
    private String lastName;

}
