package org.knowm.xchange.coinbene.dto.trading;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import org.knowm.xchange.coinbene.dto.CoinbeneResponse;

public class CoinbeneOrders {
  private final List<CoinbeneLimitOrder> orders;
  private final Integer totalCount;
  private final Integer pageSize;
  private final Integer page;

  public CoinbeneOrders(
      @JsonProperty("result") List<CoinbeneLimitOrder> orders,
      @JsonProperty("totalcount") Integer totalCount,
      @JsonProperty("pagesize") Integer pageSize,
      @JsonProperty("page") Integer page) {
    this.orders = orders;
    this.totalCount = totalCount;
    this.pageSize = pageSize;
    this.page = page;
  }

  public List<CoinbeneLimitOrder> getOrders() {
    return orders;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public Integer getPage() {
    return page;
  }

  public static class Container extends CoinbeneResponse {
    private final CoinbeneOrders orders;

    Container(@JsonProperty("orders") CoinbeneOrders orders) {
      this.orders = orders;
    }

    public CoinbeneOrders getOrders() {
      return orders;
    }
  }
}
