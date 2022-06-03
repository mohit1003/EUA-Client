package in.gov.abdm.uhi.EUABookingService.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

/**
 * Describes count or amount of an item
 */
@Data
@JsonInclude(Include.NON_NULL)
public class ItemQuantityTO {

	private ItemQuantityCountTO allocated;

	private ItemQuantityCountTO available;

	private ItemQuantityCountTO maximum;

	private ItemQuantityCountTO minimum;

	private ItemQuantityCountTO selected;
}
