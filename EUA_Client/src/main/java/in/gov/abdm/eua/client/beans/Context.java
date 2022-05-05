package in.gov.abdm.eua.client.beans;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Context {
    private String domain;
    private String country;
    private String city;
    private String action;
    private String core_version;
    private String consumer_id;
    private String consumer_uri;
    private String provider_id;
    private String provider_uri;
    private String transaction_id;
    private String message_id;
    private String timestamp;
    private String key;
    private String ttl;


}
