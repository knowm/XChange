package org.knowm.xchange.okex.v5.dto.trade;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByCurrencyPair;
import org.knowm.xchange.service.trade.params.CancelOrderByIdParams;

/** Author: Max Gao (gaamox@tutanota.com) Created: 10-06-2021 */
public class OkexTradeParams {
  public static class OkexCancelOrderParams
      implements CancelOrderByIdParams, CancelOrderByCurrencyPair {
    public final CurrencyPair currencyPair;
    public final String orderId;

    public OkexCancelOrderParams(CurrencyPair currencyPair, String orderId) {
      this.currencyPair = currencyPair;
      this.orderId = orderId;
    }

    @Override
    public String getOrderId() {
      return orderId;
    }

    @Override
    public CurrencyPair getCurrencyPair() {
      return currencyPair;
    }
  }
}
