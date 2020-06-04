package org.knowm.xchange.huobi.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.dto.trade.MarketOrder;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.HuobiUtils;
import org.knowm.xchange.huobi.dto.trade.HuobiCreateOrderRequest;
import org.knowm.xchange.huobi.dto.trade.HuobiOrder;
import org.knowm.xchange.huobi.dto.trade.results.HuobiCancelOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrderInfoResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrderResult;
import org.knowm.xchange.huobi.dto.trade.results.HuobiOrdersResult;
import org.knowm.xchange.service.trade.params.CurrencyPairParam;

class HuobiTradeServiceRaw extends HuobiBaseService {
  HuobiTradeServiceRaw(Exchange exchange) {
    super(exchange);
  }

  // https://huobiapi.github.io/docs/spot/v1/en/#search-past-orders
  HuobiOrder[] getHuobiTradeHistory(CurrencyPairParam params) throws IOException {
    String tradeStates = "partial-filled,partial-canceled,filled";
    HuobiOrdersResult result =
        huobi.getOrders(
            HuobiUtils.createHuobiCurrencyPair(params.getCurrencyPair()),
            tradeStates,
            null, // System.currentTimeMillis() - 48 * 60 * 60_000L,
            null,
            null,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  HuobiOrder[] getHuobiOpenOrders(CurrencyPairParam params) throws IOException {
    String states = "pre-submitted,submitted,partial-filled";
    HuobiOrdersResult result =
        huobi.getOpenOrders(
            HuobiUtils.createHuobiCurrencyPair(params.getCurrencyPair()),
            states,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  String cancelHuobiOrder(String orderId) throws IOException {
    HuobiCancelOrderResult result =
        huobi.cancelOrder(
            orderId,
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  String placeHuobiLimitOrder(LimitOrder limitOrder) throws IOException {
    String type;
    if (limitOrder.getType() == OrderType.BID) {
      type = "buy-limit";
    } else if (limitOrder.getType() == OrderType.ASK) {
      type = "sell-limit";
    } else {
      throw new ExchangeException("Unsupported order type.");
    }
    if (limitOrder.hasFlag(HuobiTradeService.FOK)) type = type + "-fok";
    if (limitOrder.hasFlag(HuobiTradeService.IOC)) type = type + "-ioc";

    HuobiOrderResult result =
        huobi.placeLimitOrder(
            new HuobiCreateOrderRequest(
                getAccountId(),
                limitOrder.getOriginalAmount().toString(),
                limitOrder.getLimitPrice().toString(),
                HuobiUtils.createHuobiCurrencyPair(limitOrder.getCurrencyPair()),
                type,
                limitOrder.getUserReference(),
                null,
                null),
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);

    return checkResult(result);
  }

  String placeHuobiMarketOrder(MarketOrder order) throws IOException {
    String type;
    if (order.getType() == OrderType.BID) {
      type = "buy-market";
    } else if (order.getType() == OrderType.ASK) {
      type = "sell-market";
    } else {
      throw new ExchangeException("Unsupported order type.");
    }

    HuobiOrderResult result =
        huobi.placeMarketOrder(
            new HuobiCreateOrderRequest(
                getAccountId(),
                order.getOriginalAmount().toString(),
                null,
                HuobiUtils.createHuobiCurrencyPair(order.getCurrencyPair()),
                type,
                order.getUserReference(),
                null,
                null),
            exchange.getExchangeSpecification().getApiKey(),
            HuobiDigest.HMAC_SHA_256,
            2,
            HuobiUtils.createUTCDate(exchange.getNonceFactory()),
            signatureCreator);
    return checkResult(result);
  }

  List<HuobiOrder> getHuobiOrder(String... orderIds) throws IOException {
    List<HuobiOrder> orders = new ArrayList<>();
    for (String orderId : orderIds) {
      HuobiOrderInfoResult orderInfoResult =
          huobi.getOrder(
              orderId,
              exchange.getExchangeSpecification().getApiKey(),
              HuobiDigest.HMAC_SHA_256,
              2,
              HuobiUtils.createUTCDate(exchange.getNonceFactory()),
              signatureCreator);
      orders.add(checkResult(orderInfoResult));
    }
    return orders;
  }

  private String getAccountId() throws IOException {
    return String.valueOf(
        ((HuobiAccountServiceRaw) exchange.getAccountService()).getAccounts()[0].getId());
  }
}
