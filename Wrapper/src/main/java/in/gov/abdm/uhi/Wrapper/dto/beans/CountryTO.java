package in.gov.abdm.uhi.Wrapper.dto.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

/**
 * Describes a country.
 */
@Data
@JsonInclude(Include.NON_NULL)
public class CountryTO {

    private String name;

    private String code;
}
