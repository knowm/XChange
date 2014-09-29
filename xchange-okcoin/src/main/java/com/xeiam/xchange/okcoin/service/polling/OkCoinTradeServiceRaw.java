package com.xeiam.xchange.okcoin.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinOrderResult;
import com.xeiam.xchange.okcoin.dto.trade.OkCoinTradeResult;

public class OkCoinTradeServiceRaw extends OKCoinBaseTradePollingService {

  protected OkCoinTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public OkCoinTradeResult trade(String symbol, String type, String rate, String amount) throws IOException {
    OkCoinTradeResult tradeResult = okCoin.trade(partner, symbol, type, rate, amount, signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinTradeResult cancelOrder(long orderId, String symbol) throws IOException {
    OkCoinTradeResult tradeResult = okCoin.cancelOrder(partner, orderId, symbol, signatureCreator);
    return returnOrThrow(tradeResult);
  }

  public OkCoinOrderResult getOrder(long orderId, String symbol) throws IOException {
    OkCoinOrderResult orderResult = okCoin.getOrder(partner, orderId, symbol, signatureCreator);
    return returnOrThrow(orderResult);
  }
}
