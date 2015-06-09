package com.xeiam.xchange.ripple.service.polling;

import java.io.IOException;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.ripple.RippleExchange;
import com.xeiam.xchange.ripple.dto.RippleAmount;
import com.xeiam.xchange.ripple.dto.RippleException;
import com.xeiam.xchange.ripple.dto.trade.RippleAccountOrders;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderCancelRequest;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderCancelResponse;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryRequestBody;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryRequest;
import com.xeiam.xchange.ripple.dto.trade.RippleOrderEntryResponse;

public class RippleTradeServiceRaw extends RippleBasePollingService {

  public RippleTradeServiceRaw(final Exchange exchange) {
    super(exchange);
  }

  public String placeOrder(final LimitOrder order, final boolean validate) throws RippleException, IOException {
    final RippleOrderEntryRequest entry = new RippleOrderEntryRequest();
    entry.setSecret(exchange.getExchangeSpecification().getSecretKey());

    final RippleOrderEntryRequestBody request = entry.getOrder();

    final RippleAmount baseAmount;
    final RippleAmount counterAmount;
    if (order.getType() == OrderType.BID) {
      request.setType("buy");
      // buying: we receive base and pay with counter, taker receives counter and pays with base
      counterAmount = request.getTakerGets();
      baseAmount = request.getTakerPays();
    } else {
      request.setType("sell");
      // selling: we receive counter and pay with base, taker receives base and pays with counter 
      baseAmount = request.getTakerGets();
      counterAmount = request.getTakerPays();
    }

    baseAmount.setCurrency(order.getCurrencyPair().baseSymbol);
    baseAmount.setValue(order.getTradableAmount());
    if (baseAmount.getCurrency().equals(Currencies.XRP) == false) {
      // not XRP - need a counterparty for this currency
      final Object counterparty = order.getAdditionalData(RippleExchange.DATA_BASE_COUNTERPARTY);
      if (counterparty == null) {
        throw new ExchangeException(String.format("additional data field %s must be populated for currency %s",
            RippleExchange.DATA_BASE_COUNTERPARTY, baseAmount.getCurrency()));
      }
      baseAmount.setCounterparty(counterparty.toString());
    }

    counterAmount.setCurrency(order.getCurrencyPair().counterSymbol);
    counterAmount.setValue(order.getTradableAmount().multiply(order.getLimitPrice()));
    if (counterAmount.getCurrency().equals(Currencies.XRP) == false) {
      // not XRP - need a counterparty for this currency
      final Object counterparty = order.getAdditionalData(RippleExchange.DATA_COUNTER_COUNTERPARTY);
      if (counterparty == null) {
        throw new ExchangeException(String.format("additional data field %s must be populated for currency %s",
            RippleExchange.DATA_COUNTER_COUNTERPARTY, counterAmount.getCurrency()));
      }
      counterAmount.setCounterparty(counterparty.toString());
    }

    final RippleOrderEntryResponse response = rippleAuthenticated.orderEntry(exchange.getExchangeSpecification().getApiKey(), validate,
        entry);
    return Long.toString(response.getOrder().getSequence());
  }

  public boolean cancelOrder(final String orderId, final boolean validate) throws RippleException, IOException {
    final RippleOrderCancelRequest cancel = new RippleOrderCancelRequest();
    cancel.setSecret(exchange.getExchangeSpecification().getSecretKey());

    final RippleOrderCancelResponse response = rippleAuthenticated.orderCancel(exchange.getExchangeSpecification().getApiKey(),
        Long.valueOf(orderId), validate, cancel);
    return response.isSuccess();
  }

  public RippleAccountOrders openAccountOrders() throws RippleException, IOException {
    return ripplePublic.openAccountOrders(exchange.getExchangeSpecification().getApiKey());
  }

}
