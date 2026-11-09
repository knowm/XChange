package org.knowm.xchange.blockchain.service.marketdata;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.knowm.xchange.blockchain.service.utils.BlockchainConstants.*;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.service.BlockchainBaseTest;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.exceptions.InternalServerException;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.service.marketdata.MarketDataService;

class MarketDataServiceTest extends BlockchainBaseTest {
  private MarketDataService service;

  @BeforeEach
  void init() {
    BlockchainExchange exchange = createExchange();
    service = exchange.getMarketDataService();
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getOrderBookSuccess() throws Exception {
    stubGet(ORDERBOOK_JSON, 200, URL_ORDERBOOOK_L3);
    OrderBook response = service.getOrderBook(CurrencyPair.BTC_USD);
    assertThat(response).isNotNull();
    assertThat(response.getAsks().get(0).getLimitPrice()).isNotNull().isPositive();
    assertThat(response.getBids().get(0).getLimitPrice()).isNotNull().isPositive();
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getOrderBookFailure() {
    stubGet(ORDERBOOK_FAILURE_JSON, 500, URL_ORDERBOOOK_L3);
    Throwable exception = catchThrowable(() -> service.getOrderBook(CurrencyPair.BTC_USD));
    assertThat(exception).isInstanceOf(InternalServerException.class).hasMessage(STATUS_CODE_500);
  }

  @Test
  @Timeout(
      value = 2000,
      unit = TimeUnit.MILLISECONDS,
      threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
  void getOrderBookByInstrumentSuccess() throws Exception {
    stubGet(ORDERBOOK_JSON, 200, URL_ORDERBOOOK_L3);
    Instrument instrument = CurrencyPair.BTC_USD;
    OrderBook response = service.getOrderBook(instrument);
    assertThat(response).isNotNull();
    assertThat(response.getAsks().get(0).getLimitPrice()).isNotNull().isPositive();
    assertThat(response.getBids().get(0).getLimitPrice()).isNotNull().isPositive();
  }
}
