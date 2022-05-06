package in.gov.abdm.uhi.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes the ACK response
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class AckTO {

	/**
	 * Describe the status of the ACK response. If schema validation passes, status
	 * is ACK else it is NACK
	 */
	private String status;
}
