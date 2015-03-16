package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.BitbayAuthentiacated;
import com.xeiam.xchange.bitbay.dto.BitbayBaseResponse;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.bitbay.dto.trade.BitbayOrder;
import com.xeiam.xchange.bitbay.dto.trade.BitbayTradeResponse;
import com.xeiam.xchange.bitbay.service.BitbayDigest;
import com.xeiam.xchange.dto.trade.LimitOrder;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author yarkh
 */
public class BitbayTradeServiceRaw extends BitbayBasePollingService {

    protected BitbayAuthentiacated bitbay;
    protected final ParamsDigest signatureCreator;
    protected final String apiKey;

  protected BitbayTradeServiceRaw(Exchange exchange) {

    super(exchange);
    this.bitbay = RestProxyFactory.createProxy(BitbayAuthentiacated.class, exchange.getExchangeSpecification().getSslUri());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.signatureCreator = BitbayDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

    protected List<BitbayOrder> getOrders() throws IOException {
        return bitbay.orders(apiKey, signatureCreator, new Date().getTime() / 1000);
    }


    protected BitbayTradeResponse placeBitbayOrder(LimitOrder order) throws IOException {
        return bitbay.trade(apiKey, signatureCreator, new Date().getTime() / 1000,
                order.getType().toString().toLowerCase(), order.getCurrencyPair().baseSymbol, order.getCurrencyPair().counterSymbol,
                order.getTradableAmount(), order.getTradableAmount().multiply(order.getLimitPrice()), order.getLimitPrice());
    }

    protected BitbayBaseResponse cancelBitbayOrder(String orderId) throws IOException {
        return bitbay.cancel(apiKey, signatureCreator, new Date().getTime() / 1000, orderId);
    }
}
