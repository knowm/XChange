package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketAuth;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrderResponse;
import com.xeiam.xchange.bitmarket.dto.trade.BitMarketOrdersResponse;
import com.xeiam.xchange.bitmarket.service.BitMarketDigest;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author yarkh
 */
public class BitMarketTradeServiceRaw extends BitMarketBasePollingService {

  protected BitMarketAuth bitMarketAuth;
  protected final ParamsDigest signatureCreator;
  protected final String apiKey;

  protected BitMarketTradeServiceRaw(Exchange exchange) {

      super(exchange);
      this.bitMarketAuth = RestProxyFactory.createProxy(BitMarketAuth.class, exchange.getExchangeSpecification().getSslUri());
      this.apiKey = exchange.getExchangeSpecification().getApiKey();
      this.signatureCreator = BitMarketDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected BitMarketBaseResponse<Map<String, BitMarketOrdersResponse>> getBitMarketOrders(String market) throws IOException {
    return bitMarketAuth.orders(market, new Date().getTime() / 1000, apiKey, signatureCreator);
  }

    protected BitMarketBaseResponse<BitMarketOrderResponse> sendBitMarketOrder(LimitOrder limitOrder) throws IOException {

      String type = limitOrder.getType() == Order.OrderType.BID? "buy" : "sell";

      return bitMarketAuth.trade(limitOrder.getCurrencyPair().baseSymbol + limitOrder.getCurrencyPair().counterSymbol,
          type, limitOrder.getTradableAmount(), limitOrder.getLimitPrice(),
          new Date().getTime()/1000, apiKey, signatureCreator);
    }

  protected BitMarketBaseResponse<BitMarketOrderResponse> cancelBitMarketOrder(String id) throws IOException {
    return bitMarketAuth.cancel(id, new Date().getTime() / 1000, apiKey, signatureCreator);
  }

}
