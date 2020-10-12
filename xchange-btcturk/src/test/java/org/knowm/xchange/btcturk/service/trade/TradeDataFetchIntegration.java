package org.knowm.xchange.btcturk.service.trade;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.btcturk.BTCTurkExchange;
import org.knowm.xchange.btcturk.dto.BTCTurkOrderTypes;
import org.knowm.xchange.btcturk.dto.account.BTCTurkUserTransactions;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkExchangeResult;
import org.knowm.xchange.btcturk.dto.trade.BTCTurkOpenOrders;
import org.knowm.xchange.btcturk.service.BTCTurkDemoUtilsTest;
import org.knowm.xchange.btcturk.service.BTCTurkTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.trade.TradeService;

/** @author mertguner */
public class TradeDataFetchIntegration {

  private Exchange btcTurk;
  private BTCTurkTradeService btcTurkTradeService;
  private TradeService tradeService;

  @Before
  public void InitExchange() throws IOException {
    if (BTCTurkDemoUtilsTest.BTCTURK_APIKEY.isEmpty())
      btcTurk = ExchangeFactory.INSTANCE.createExchange(BTCTurkExchange.class);
    else {
      ExchangeSpecification exSpec = new BTCTurkExchange().getDefaultExchangeSpecification();
      exSpec.setApiKey(BTCTurkDemoUtilsTest.BTCTURK_APIKEY);
      exSpec.setSecretKey(BTCTurkDemoUtilsTest.BTCTURK_SECRETKEY);
      btcTurk = ExchangeFactory.INSTANCE.createExchange(exSpec);
    }

    tradeService = btcTurk.getTradeService();
    btcTurkTradeService = (BTCTurkTradeService) tradeService;
  }

  @Test
  public void Tests() throws IOException, InterruptedException {

    if (!BTCTurkDemoUtilsTest.BTCTURK_APIKEY.isEmpty()) {
      // PlaceOrderAndOpenOrders Test
      Thread.sleep(1000);
      List<BTCTurkOpenOrders> openOrders =
          btcTurkTradeService.getBTCTurkOpenOrders(CurrencyPair.ETH_TRY);

      Boolean result = false;
      if (openOrders.isEmpty()) {
        Thread.sleep(1000);
        BTCTurkExchangeResult exchangeResult =
            btcTurkTradeService.placeLimitOrder(
                new BigDecimal("0.01"),
                new BigDecimal(713),
                CurrencyPair.ETH_TRY,
                BTCTurkOrderTypes.Sell);
        Thread.sleep(1000);
        result = btcTurkTradeService.cancelOrder(exchangeResult.getId());
      } else {
        result = btcTurkTradeService.cancelOrder(openOrders.get(0).getId());
      }
      assertThat(result).isEqualTo(true);

      // UserTransactions Test
      Thread.sleep(1000);
      List<BTCTurkUserTransactions> userTransactions =
          btcTurkTradeService.getBTCTurkUserTransactions();
      assertThat(userTransactions.size()).isEqualTo(25);
    } else assertThat(tradeService).isNotEqualTo(null);
  }
}
