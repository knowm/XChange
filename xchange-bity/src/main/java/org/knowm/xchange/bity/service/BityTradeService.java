package org.knowm.xchange.bity.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bity.BityAdapters;
import org.knowm.xchange.bity.dto.BityResponse;
import org.knowm.xchange.bity.dto.account.BityOrder;
import org.knowm.xchange.dto.trade.UserTrades;
import org.knowm.xchange.service.trade.TradeService;
import org.knowm.xchange.service.trade.params.TradeHistoryParamLimit;
import org.knowm.xchange.service.trade.params.TradeHistoryParamOffset;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public final class BityTradeService extends BityTradeServiceRaw implements TradeService {

  /**
   * Constructor
   *
   * @param exchange
   */
  public BityTradeService(Exchange exchange) {
    super(exchange);
  }

  public static class BityHistoryParams
      implements TradeHistoryParams, TradeHistoryParamLimit, TradeHistoryParamOffset {

    private Integer limit;

    private Long offset;

    @Override
    public Integer getLimit() {
      return limit;
    }

    @Override
    public void setLimit(Integer limit) {
      this.limit = limit;
    }

    @Override
    public Long getOffset() {
      return offset;
    }

    @Override
    public void setOffset(Long offset) {
      this.offset = offset;
    }
  }

  @Override
  public UserTrades getTradeHistory(TradeHistoryParams params) {

    Integer limit = null;
    Long offset = null;

    if (params instanceof TradeHistoryParamLimit) {
      limit = ((TradeHistoryParamLimit) params).getLimit();
    }

    if (params instanceof TradeHistoryParamOffset) {
      offset = ((TradeHistoryParamOffset) params).getOffset();
    }

    // Integer offset, final Integer limit, Integer orderBy
    final BityResponse<BityOrder> orders = super.getBityOrders(offset, limit, "timestamp_created");
    return BityAdapters.adaptTrades(orders.getObjects());
  }

  @Override
  public TradeHistoryParams createTradeHistoryParams() {
    BityHistoryParams params = new BityHistoryParams();
    params.setLimit(50);
    params.setOffset(0L);
    return params;
  }
}
