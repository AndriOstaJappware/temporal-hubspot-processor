package jsontopojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
        "next",
        "prev"
})
@Getter
@Setter
public class Paging {
    @JsonProperty("next")
    private Next next;
    @JsonProperty("prev")
    private String prev;
}





