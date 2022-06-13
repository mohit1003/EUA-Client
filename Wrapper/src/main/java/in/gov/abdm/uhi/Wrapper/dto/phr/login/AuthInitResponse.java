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

package in.gov.abdm.uhi.Wrapper.dto.phr.login;

import com.fasterxml.jackson.annotation.JsonInclude;

import in.gov.abdm.uhi.Wrapper.dto.phr.ServiceResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * AuthInitResponse
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthInitResponse extends ServiceResponse {
    private String transactionId;

    private String requesterId;

    private String authMode;

    private String error;

}
