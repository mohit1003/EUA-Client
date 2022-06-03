package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
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
