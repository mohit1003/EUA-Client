package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ParamTO {

	@JsonProperty(value = "transaction_id")
	private String transactionId;

	@JsonProperty(value = "transaction_status")
	private String transactionStatus;

	private String amount;

	private String currency;

	private String mode;

	private String vpa;

	@JsonProperty(value = "additionalProperties")
	private String additionalProperties;
}
