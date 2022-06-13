package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * Describes count or amount of an item
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class ItemQuantityTO {

	private ItemQuantityCountTO allocated;

	private ItemQuantityCountTO available;

	private ItemQuantityCountTO maximum;

	private ItemQuantityCountTO minimum;

	private ItemQuantityCountTO selected;
}
