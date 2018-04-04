package org.knowm.xchange.kucoin.dto.trading;

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

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({"total", "datas", "limit", "page"})
public class KucoinDealtOrdersInfo {

  @JsonProperty("total")
  private Integer total;

  @JsonProperty("datas")
  private List<KucoinDealtOrder> dealtOrders = new ArrayList<KucoinDealtOrder>();

  @JsonProperty("limit")
  private Integer limit;

  @JsonProperty("page")
  private Integer page;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinDealtOrdersInfo() {}

  /**
   * @param total
   * @param dealtOrders
   * @param limit
   * @param page
   */
  public KucoinDealtOrdersInfo(
      Integer total, List<KucoinDealtOrder> dealtOrders, Integer limit, Integer page) {
    super();
    this.total = total;
    this.dealtOrders = dealtOrders;
    this.limit = limit;
    this.page = page;
  }

  /** @return The total */
  @JsonProperty("total")
  public Integer getTotal() {
    return total;
  }

  /** @param total The total */
  @JsonProperty("total")
  public void setTotal(Integer total) {
    this.total = total;
  }

  /** @return The dealtOrders */
  @JsonProperty("datas")
  public List<KucoinDealtOrder> getDealtOrders() {
    return dealtOrders;
  }

  /** @param dealtOrders The dealtOrders */
  @JsonProperty("datas")
  public void setDealtOrders(List<KucoinDealtOrder> dealtOrders) {
    this.dealtOrders = dealtOrders;
  }

  /** @return The limit */
  @JsonProperty("limit")
  public Integer getLimit() {
    return limit;
  }

  /** @param limit The limit */
  @JsonProperty("limit")
  public void setLimit(Integer limit) {
    this.limit = limit;
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

  @JsonAnyGetter
  public Map<String, Object> getAdditionalProperties() {
    return this.additionalProperties;
  }

  @JsonAnySetter
  public void setAdditionalProperty(String name, Object value) {
    this.additionalProperties.put(name, value);
  }
}
