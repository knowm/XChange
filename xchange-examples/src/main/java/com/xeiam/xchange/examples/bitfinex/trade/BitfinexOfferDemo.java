package com.xeiam.xchange.examples.bitfinex.trade;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitfinex.v1.BitfinexOrderType;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexCreditResponse;
import com.xeiam.xchange.bitfinex.v1.dto.trade.BitfinexOfferStatusResponse;
import com.xeiam.xchange.bitfinex.v1.service.polling.BitfinexTradeServiceRaw;
import com.xeiam.xchange.dto.Order.OrderType;
import com.xeiam.xchange.dto.trade.FixedRateLoanOrder;
import com.xeiam.xchange.dto.trade.FloatingRateLoanOrder;
import com.xeiam.xchange.examples.bitfinex.BitfinexDemoUtils;

public class BitfinexOfferDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();

    raw(bfx);
  }

  private static void raw(Exchange bfx) throws IOException {

    BitfinexTradeServiceRaw tradeService = (BitfinexTradeServiceRaw) bfx.getPollingTradeService();

    BitfinexOfferStatusResponse fixedRateResponse = tradeService.placeBitfinexFixedRateLoanOrder(
        new FixedRateLoanOrder(OrderType.BID, "USD", new BigDecimal("0.01"), 2, "", null, new BigDecimal("0.01")), BitfinexOrderType.LIMIT);
    System.out.println("Fixed rate order response: " + fixedRateResponse);

    BitfinexOfferStatusResponse floatingRateResponse = tradeService.placeBitfinexFloatingRateLoanOrder(
        new FloatingRateLoanOrder(OrderType.BID, "USD", new BigDecimal("0.01"), 2, "", null, BigDecimal.ZERO), BitfinexOrderType.MARKET);
    System.out.println("Floating rate order response: " + floatingRateResponse);

    BitfinexCreditResponse[] activeCredits = tradeService.getBitfinexActiveCredits();
    System.out.println("Active credits: " + Arrays.toString(activeCredits));

    BitfinexOfferStatusResponse[] openOffers = tradeService.getBitfinexOpenOffers();
    System.out.println("Open offers response: " + Arrays.toString(openOffers));

    for (BitfinexOfferStatusResponse offer : openOffers) {
      BitfinexOfferStatusResponse cancelResponse = tradeService.cancelBitfinexOffer(Integer.toString(offer.getId()));
      System.out.println("Cancel offer response: " + cancelResponse);
    }
  }

}
