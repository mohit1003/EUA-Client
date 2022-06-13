package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 * Describes a page in a search result
 */
@Data
@JsonInclude(Include.NON_NULL)
public class PageTO {

    private String id;

    @JsonProperty(value = "next_id")
    private String nextId;
}
