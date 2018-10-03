package org.knowm.xchange.examples.bitfinex.trade;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bitfinex.v1.dto.trade.BitfinexFundingTradeResponse;
import org.knowm.xchange.bitfinex.v1.service.BitfinexTradeServiceRaw;
import org.knowm.xchange.examples.bitfinex.BitfinexDemoUtils;

public class BitfinexTradeDemo {

  public static void main(String[] args) throws IOException {

    Exchange bfx = BitfinexDemoUtils.createExchange();

    raw(bfx);
  }

  private static void raw(Exchange bfx) throws IOException {

    /*
    BitfinexTradeServiceRaw tradeService = (BitfinexTradeServiceRaw) bfx.getTradeService();
    LimitOrder limitOrder = new LimitOrder.Builder(OrderType.BID, CurrencyPair.BTC_USD).limitPrice(new BigDecimal("481.69"))
        .originalAmount(new BigDecimal("0.001")).build();
    tradeService.placeBitfinexLimitOrder(limitOrder, BitfinexOrderType.LIMIT);
    */

    BitfinexTradeServiceRaw tradeService = (BitfinexTradeServiceRaw) bfx.getTradeService();

    Date tenDaysAgo =
        Date.from(LocalDate.now().minusDays(10).atStartOfDay(ZoneId.systemDefault()).toInstant());
    BitfinexFundingTradeResponse[] fundingTradeResponses =
        tradeService.getBitfinexFundingHistory("USD", tenDaysAgo, 2000);
  }
}
