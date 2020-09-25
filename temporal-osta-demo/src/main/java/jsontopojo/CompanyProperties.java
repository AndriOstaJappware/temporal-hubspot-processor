package jsontopojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.math.BigInteger;
import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "createdate",
        "domain",
        "hs_lastmodifieddate",
        "hs_object_id",
        "name"
})
@Getter
@Setter
public class CompanyProperties {
    @JsonProperty("createdate")
    private Date createdate;
    @JsonProperty("domain")
    private String domain;
    @JsonProperty("hs_lastmodifieddate")
    private Date hs_lastmodifieddate;
    @JsonProperty("hs_object_id")
    private BigInteger hs_object_id;
    @JsonProperty("name")
    private String name;
}
