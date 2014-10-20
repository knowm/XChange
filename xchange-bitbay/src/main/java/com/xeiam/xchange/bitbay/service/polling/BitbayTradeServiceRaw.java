package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.BitbayAuthentiacated;
import com.xeiam.xchange.bitbay.dto.BitbayBaseResponse;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.bitbay.dto.trade.BitbayOrder;
import com.xeiam.xchange.bitbay.dto.trade.BitbayTradeResponse;
import com.xeiam.xchange.dto.trade.LimitOrder;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author yarkh
 */
public class BitbayTradeServiceRaw extends BitbayBasePollingService<BitbayAuthentiacated> {


  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitbayTradeServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitbayAuthentiacated.class, exchangeSpecification);
  }

    protected List<BitbayOrder> getOrders() throws IOException {
        return bitbay.orders(apiKey, signatureCreator, new Date().getTime());
    }


    protected BitbayTradeResponse placeBitbayOrder(LimitOrder order) throws IOException {
        return bitbay.trade(apiKey, signatureCreator, new Date().getTime(),
                order.getType().toString().toLowerCase(), order.getCurrencyPair().baseSymbol, order.getCurrencyPair().counterSymbol,
                order.getTradableAmount(), order.getTradableAmount().multiply(order.getLimitPrice()), order.getLimitPrice());
    }

    protected BitbayBaseResponse cancelBitbayOrder(String orderId) throws IOException {
        return bitbay.cancel(apiKey, signatureCreator, new Date().getTime(), orderId);
    }
}
