package org.xchange.bitz.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.service.marketdata.MarketDataService;
import org.knowm.xchange.service.trade.TradeService;
import org.xchange.bitz.BitZExchange;
import org.xchange.bitz.dto.marketdata.BitZPublicOrder;
import org.xchange.bitz.service.BitZTradeServiceRaw;

public class BitzTradeAddIntegration {
  
  //TODO: Replace Raw Test
  @Test
  public void tradeAddRawTest() throws Exception {

    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(BitZExchange.class.getName());
    BitZTradeServiceRaw tradeService = (BitZTradeServiceRaw) exchange.getTradeService();
    
    // Verify Not Null Values
    assertThat(tradeService).isNotNull();

    // Test Placing Order
    // tradeService.placeBitZLimitOrder(CurrencyPair.LTC_BTC, new BitZPublicOrder(BigDecimal.ONE, BigDecimal.ONE), new Date(), true);
  }

}

