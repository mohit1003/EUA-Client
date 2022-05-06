package in.gov.abdm.uhi.common.dto;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a category
 */
@Data
@JsonInclude(Include.NON_NULL)
public class CategoryTO {

	private String id;

	@JsonProperty(value = "parent_category_id")
	private String parentCategoryId;

	private DescriptorTO descriptor;

	private TimeTO time;

	private Map<String, String> tags;
}
