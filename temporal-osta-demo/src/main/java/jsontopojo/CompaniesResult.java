package jsontopojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.util.Date;
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "id",
        "properties",
        "createdAt",
        "updatedAt",
        "archived"
})
@Getter
@Setter
public class CompaniesResult {
    @JsonProperty("id")
    private String id;
    @JsonProperty("properties")
    private CompanyProperties companyProperties;
    @JsonProperty("createdAt")
    private Date createdAt;
    @JsonProperty("updatedAt")
    private Date updatedAt;
    @JsonProperty("archived")
    private Boolean archived;
}
