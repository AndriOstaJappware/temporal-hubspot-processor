package jsontopojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "results",
        "paging",
})
@Getter
@Setter
public class Companies {
    @JsonProperty("results")
    private List<CompaniesResult> comapnyResults = new ArrayList<CompaniesResult>();
    @JsonProperty("paging")
    private Paging paging;
}
