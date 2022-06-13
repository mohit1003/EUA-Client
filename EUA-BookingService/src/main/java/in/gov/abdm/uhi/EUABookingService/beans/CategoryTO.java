package in.gov.abdm.uhi.EUABookingService.beans;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a category
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown=true)
public class CategoryTO {

	private String id;

	@JsonProperty(value = "parent_category_id")
	private String parentCategoryId;

	private DescriptorTO descriptor;

	private TimeTO time;

	private Map<String, String> tags;
}
