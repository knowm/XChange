package org.knowm.xchange.bibox.dto.trade;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

/** @author odrotleff */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"count", "page", "items"})
public class BiboxOrders {

  @JsonProperty("count")
  private Integer count;

  @JsonProperty("page")
  private Integer page;

  @JsonProperty("items")
  private List<BiboxOrder> items = new ArrayList<BiboxOrder>();

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** @return The count */
  @JsonProperty("count")
  public Integer getCount() {
    return count;
  }

  /** @param count The count */
  @JsonProperty("count")
  public void setCount(Integer count) {
    this.count = count;
  }

  /** @return The page */
  @JsonProperty("page")
  public Integer getPage() {
    return page;
  }

  /** @param page The page */
  @JsonProperty("page")
  public void setPage(Integer page) {
    this.page = page;
  }

  /** @return The items */
  @JsonProperty("items")
  public List<BiboxOrder> getItems() {
    return items;
  }

  /** @param items The items */
  @JsonProperty("items")
  public void setItems(List<BiboxOrder> items) {
    this.items = items;
  }

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
