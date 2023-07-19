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
        "price",
        "upc",
        "shipping",
        "description",
        "manufacturer",
        "model",
        "url",
        "image",
        "createdAt",
        "updatedAt",
        "categories"
})
@Generated("jsonschema2pojo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Product {

    @EqualsAndHashCode.Include
    @JsonProperty("id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("type")
    private String type;
    @JsonProperty("price")
    private Float price;
    @JsonProperty("upc")
    private String upc;
    @JsonProperty("shipping")
    private Integer shipping;
    @JsonProperty("description")
    private String description;
    @JsonProperty("manufacturer")
    private String manufacturer;
    @JsonProperty("model")
    private String model;
    @JsonProperty("url")
    private String url;
    @JsonProperty("image")
    private String image;
    @JsonProperty("createdAt")
    private String createdAt;
    @JsonProperty("updatedAt")
    private String updatedAt;
    @JsonProperty("categories")
    private List<Category> categories;
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