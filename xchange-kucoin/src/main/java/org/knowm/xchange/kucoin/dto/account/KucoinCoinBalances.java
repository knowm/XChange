package org.knowm.xchange.kucoin.dto.account;

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
@JsonPropertyOrder({"total", "datas", "currPageNo", "limit", "pageNos"})
public class KucoinCoinBalances {

  @JsonProperty("total")
  private Integer total;

  @JsonProperty("datas")
  private List<KucoinCoinBalance> balances = new ArrayList<KucoinCoinBalance>();

  @JsonProperty("currPageNo")
  private Integer currPageNo;

  @JsonProperty("limit")
  private Integer limit;

  @JsonProperty("pageNos")
  private Integer pageNos;

  @JsonIgnore private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /** No args constructor for use in serialization */
  public KucoinCoinBalances() {}

  /**
   * @param total
   * @param datas
   * @param currPageNo
   * @param limit
   * @param pageNos
   */
  public KucoinCoinBalances(
      Integer total,
      List<KucoinCoinBalance> datas,
      Integer currPageNo,
      Integer limit,
      Integer pageNos) {
    super();
    this.total = total;
    this.balances = datas;
    this.currPageNo = currPageNo;
    this.limit = limit;
    this.pageNos = pageNos;
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

  /** @return The balances */
  @JsonProperty("datas")
  public List<KucoinCoinBalance> getBalances() {
    return balances;
  }

  /** @param balances The balances */
  @JsonProperty("datas")
  public void setBalances(List<KucoinCoinBalance> balances) {
    this.balances = balances;
  }

  /** @return The currPageNo */
  @JsonProperty("currPageNo")
  public Integer getCurrPageNo() {
    return currPageNo;
  }

  /** @param currPageNo The currPageNo */
  @JsonProperty("currPageNo")
  public void setCurrPageNo(Integer currPageNo) {
    this.currPageNo = currPageNo;
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

  /** @return The pageNos */
  @JsonProperty("pageNos")
  public Integer getPageNos() {
    return pageNos;
  }

  /** @param pageNos The pageNos */
  @JsonProperty("pageNos")
  public void setPageNos(Integer pageNos) {
    this.pageNos = pageNos;
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
