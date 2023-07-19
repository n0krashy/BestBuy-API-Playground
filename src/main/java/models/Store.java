package models;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.annotation.processing.Generated;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "type",
        "address",
        "address2",
        "city",
        "state",
        "zip",
        "lat",
        "lng",
        "hours",
        "createdAt",
        "updatedAt",
        "services"
})
@Generated("jsonschema2pojo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Store {

    @EqualsAndHashCode.Include
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("address")
    private String address;
    @JsonProperty("address2")
    private String address2;
    @JsonProperty("city")
    private String city;
    @JsonProperty("state")
    private String state;
    @JsonProperty("zip")
    private String zip;
    @JsonProperty("lat")
    private Float lat;
    @JsonProperty("lng")
    private Float lng;
    @JsonProperty("hours")
    private String hours;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("services")
    private List<Service> services;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}