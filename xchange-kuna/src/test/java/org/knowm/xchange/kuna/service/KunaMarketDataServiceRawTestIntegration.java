package org.knowm.xchange.kuna.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.currency.CurrencyPair.BCH_UAH;
import static org.knowm.xchange.currency.CurrencyPair.BTC_UAH;
import static org.knowm.xchange.currency.CurrencyPair.ETH_UAH;
import static org.knowm.xchange.kuna.util.KunaUtils.toPairString;

import java.io.IOException;
import java.time.Instant;
import java.util.Map;
import org.assertj.core.data.Offset;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.kuna.BaseKunaTest;
import org.knowm.xchange.kuna.dto.KunaAskBid;
import org.knowm.xchange.kuna.dto.KunaTimeTicker;
import org.knowm.xchange.kuna.dto.KunaTrade;

public class KunaMarketDataServiceRawTestIntegration extends BaseKunaTest {

  public static final long DEFAULT_OFFSET = 5l;
  private static KunaMarketDataServiceRaw marketDataServiceRaw;

  @BeforeClass
  public static void init() {
    marketDataServiceRaw = (KunaMarketDataServiceRaw) getExchange().getMarketDataService();
  }

  @Test
  public void test_btcuah_ticker() throws IOException {
    KunaTimeTicker kunaTimeTicker = marketDataServiceRaw.getKunaTicker(BTC_UAH);

    assertThat(kunaTimeTicker.getTimestamp())
        .isCloseTo(Instant.now().getEpochSecond(), Offset.offset(DEFAULT_OFFSET));
    assertThat(kunaTimeTicker.getTicker()).isNotNull();
    assertThat(kunaTimeTicker.getTicker()).hasNoNullFieldsOrProperties();
  }

  @Test
  public void test_ethuah_ticker() throws IOException {
    KunaTimeTicker kunaTimeTicker = marketDataServiceRaw.getKunaTicker(ETH_UAH);

    assertThat(kunaTimeTicker.getTimestamp())
        .isCloseTo(Instant.now().getEpochSecond(), Offset.offset(DEFAULT_OFFSET));
    assertThat(kunaTimeTicker.getTicker()).isNotNull();
    assertThat(kunaTimeTicker.getTicker()).hasNoNullFieldsOrProperties();
  }

  @Test
  public void test_available_tickers() throws IOException {
    Map<String, KunaTimeTicker> availableTickers = marketDataServiceRaw.getKunaTickers();

    assertThat(availableTickers).isNotNull().isNotEmpty();
    assertThat(availableTickers)
        .containsKeys(toPairString(BTC_UAH), toPairString(ETH_UAH), toPairString(BCH_UAH));
  }

  @Test
  public void test_order_book() throws IOException {
    KunaAskBid kunaAskBid = marketDataServiceRaw.getKunaOrderBook(BTC_UAH);

    assertThat(kunaAskBid).isNotNull();
    assertThat(kunaAskBid.getAsks()).isNotEmpty();
    assertThat(kunaAskBid.getBids()).isNotEmpty();
  }

  @Test
  public void test_kuna_trades_history() throws IOException {
    KunaTrade[] trades = marketDataServiceRaw.getKunaTradesHistory(BTC_UAH);

    assertThat(trades).isNotNull().isNotEmpty();
    assertThat(trades[0]).hasNoNullFieldsOrPropertiesExcept("side");
  }
}
