package in.gov.abdm.uhi.EUABookingService.beans;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a DHP message context
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
@NotNull
public class ContextTO {

	private String domain;

	private String country;

	private String city;

	private String action;

	@JsonProperty("core_version")
	private String coreVersion;

	@JsonProperty("consumer_id")
	private String consumerId;

	@JsonProperty("consumer_uri")
	@NotBlank
	private String consumerUri;

	@JsonProperty("provider_id")
	private String providerId;

	@JsonProperty("provider_uri")
	private String providerUri;

	@JsonProperty("transaction_id")
	private String transactionId;

	@JsonProperty("message_id")
	private String messageId;

	private String timestamp;

	private String key;

	private String ttl;
}
