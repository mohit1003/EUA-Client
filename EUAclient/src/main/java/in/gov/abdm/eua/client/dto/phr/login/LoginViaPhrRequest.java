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

package in.gov.abdm.eua.client.dto.phr.login;

import in.gov.abdm.eua.client.dto.phr.Requester;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * LoginViaPhrRequest
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginViaPhrRequest {
  @NotBlank(message = "patientID cannot be null/blank")
  @Email(message = "Invalid patientId")
  private String patientId;
  @NotBlank(message = "purpose cannot be null/blank")
  private String purpose;
  @NotBlank(message = "authMode cannot be null/blank")
  private String authMode;

  @NotNull(message = "requester cannot be null")
  @Valid
  private Requester requester;

}
