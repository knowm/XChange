package org.knowm.xchange.okex;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.dto.trade.LimitOrder;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.dto.OkexResponse;
import org.knowm.xchange.okex.dto.marketdata.OkexCandleStick;
import org.knowm.xchange.okex.service.OkexMarketDataService;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class OkexPublicDataTest {

  Exchange exchange;
  private final Instrument currencyPair = new CurrencyPair("BTC/USDT");
  private final Instrument instrument = new FuturesContract("BTC/USDT/SWAP");
  @Before
  public void setUp(){
    exchange = ExchangeFactory.INSTANCE.createExchange(OkexExchange.class);
  }

  @Test
  public void checkInstrumentMetaData(){
    InstrumentMetaData spotMetaData = exchange.getExchangeMetaData().getInstruments().get(currencyPair);
    InstrumentMetaData swapMetaData = exchange.getExchangeMetaData().getInstruments().get(instrument);

    assertThat(spotMetaData.getMinimumAmount()).isGreaterThan(BigDecimal.ZERO);
    assertThat(spotMetaData.getPriceScale()).isGreaterThanOrEqualTo(0);
    assertThat(spotMetaData.getVolumeScale()).isGreaterThanOrEqualTo(0);

    assertThat(swapMetaData.getMinimumAmount()).isGreaterThan(BigDecimal.ZERO);
    assertThat(swapMetaData.getPriceScale()).isGreaterThanOrEqualTo(0);
    assertThat(swapMetaData.getVolumeScale()).isGreaterThanOrEqualTo(0);
  }

  @Test
  public void checkOrderBook() throws IOException {
    LimitOrder spotOrder = exchange.getMarketDataService().getOrderBook(currencyPair).getBids().get(0);
    LimitOrder swapOrder = exchange.getMarketDataService().getOrderBook(instrument).getBids().get(0);

    assertThat(spotOrder.getInstrument()).isEqualTo(currencyPair);
    assertThat(swapOrder.getInstrument()).isEqualTo(instrument);
  }

  @Test
  public void checkTicker() throws IOException {
    Ticker spotTicker = exchange.getMarketDataService().getTicker(currencyPair);
    Ticker swapTicker = exchange.getMarketDataService().getTicker(instrument);

    assertThat(spotTicker.getInstrument()).isEqualTo(currencyPair);
    assertThat(swapTicker.getInstrument()).isEqualTo(instrument);
  }

  @Test
  public void checkTrades() throws IOException {
    Trades spotTrades = exchange.getMarketDataService().getTrades(currencyPair);
    Trades swapTrades = exchange.getMarketDataService().getTrades(instrument);

    assertThat(spotTrades.getTrades().get(0).getInstrument()).isEqualTo(currencyPair);
    assertThat(swapTrades.getTrades().get(0).getInstrument()).isEqualTo(instrument);
    assertThat(swapTrades.getTrades().get(0).getTimestamp()).isBeforeOrEqualTo(swapTrades.getTrades().get(5).getTimestamp());
  }

  @Test
  public void testCandleHist() throws IOException {
    ((OkexMarketDataService) exchange.getMarketDataService()).getOkexOrderbook("BTC-USDT");
    OkexResponse<List<OkexCandleStick>> barHistDtos =
        ((OkexMarketDataService) exchange.getMarketDataService())
            .getHistoryCandle("BTC-USDT", null, null, null, null);
    Assert.assertTrue(Objects.nonNull(barHistDtos) && !barHistDtos.getData().isEmpty());
  }

  @Test
  public void testInstrumentOkexConvertions(){
    assertThat(OkexAdapters.adaptOkexInstrumentId("BTC-USDT-SWAP")).isEqualTo(new FuturesContract("BTC/USDT/SWAP"));
    assertThat(OkexAdapters.adaptInstrument(new FuturesContract("BTC/USDT/SWAP"))).isEqualTo("BTC-USDT-SWAP");
    assertThat(OkexAdapters.adaptOkexInstrumentId("BTC-USDT")).isEqualTo(new CurrencyPair("BTC/USDT"));
    assertThat(OkexAdapters.adaptInstrument(new CurrencyPair("BTC/USDT"))).isEqualTo("BTC-USDT");
  }
}
