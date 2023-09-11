package org.knowm.xchange.bybit.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo.InstrumentStatus;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentsInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo.ContractType;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.option.BybitOptionInstrumentInfo.OptionType;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo.MarginTrading;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.BybitTickers;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.option.BybitOptionTicker;
import org.knowm.xchange.bybit.dto.marketdata.tickers.spot.BybitSpotTicker;

public class BybitMarketDataServiceRawTest extends BaseWiremockTest {

  private BybitMarketDataServiceRaw marketDataServiceRaw;

  @Before
  public void setUp() throws Exception {
    Exchange bybitExchange = createExchange();
    marketDataServiceRaw = (BybitMarketDataServiceRaw) bybitExchange.getMarketDataService();
  }

  @Test
  public void testGetLinearInverseInstrumentsInfo() throws Exception {
    initInstrumentsInfoStub("/getInstrumentLinear.json5");

    BybitInstrumentsInfo<BybitInstrumentInfo> instrumentsInfo =
        marketDataServiceRaw.getInstrumentsInfo(BybitCategory.LINEAR).getResult();

    assertThat(instrumentsInfo.getList()).hasSize(1);

    BybitLinearInverseInstrumentInfo actualInstrumentInfo =
        (BybitLinearInverseInstrumentInfo) instrumentsInfo.getList().get(0);

    assertThat(actualInstrumentInfo.getSymbol()).isEqualTo("BTCUSDT");
    assertThat(actualInstrumentInfo.getContractType()).isEqualTo(ContractType.LINEAR_PERPETUAL);
    assertThat(actualInstrumentInfo.getStatus()).isEqualTo(InstrumentStatus.TRADING);
    assertThat(actualInstrumentInfo.getBaseCoin()).isEqualTo("BTC");
    assertThat(actualInstrumentInfo.getQuoteCoin()).isEqualTo("USDT");
    assertThat(actualInstrumentInfo.getLaunchTime()).isEqualTo(new Date(1585526400000L));
    assertThat(actualInstrumentInfo.getDeliveryTime()).isEqualTo(new Date(0L));
    assertThat(actualInstrumentInfo.getDeliveryFeeRate()).isNull();
    assertThat(actualInstrumentInfo.getPriceScale()).isEqualTo(2);
    assertThat(actualInstrumentInfo.getLeverageFilter().getMinLeverage()).isEqualTo(1);
    assertThat(actualInstrumentInfo.getLeverageFilter().getMaxLeverage())
        .isEqualTo(new BigDecimal("100.00"));
    assertThat(actualInstrumentInfo.getLeverageFilter().getLeverageStep())
        .isEqualTo(new BigDecimal("0.01"));
    assertThat(actualInstrumentInfo.getPriceFilter().getTickSize())
        .isEqualTo(new BigDecimal("0.50"));
    assertThat(actualInstrumentInfo.getPriceFilter().getMinPrice())
        .isEqualTo(new BigDecimal("0.50"));
    assertThat(actualInstrumentInfo.getPriceFilter().getMaxPrice())
        .isEqualTo(new BigDecimal("999999.00"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .isEqualTo(new BigDecimal("100.000"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
        .isEqualTo(new BigDecimal("0.001"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getQtyStep())
        .isEqualTo(new BigDecimal("0.001"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getPostOnlyMaxOrderQty())
        .isEqualTo(new BigDecimal("1000.000"));
    assertThat(actualInstrumentInfo.isUnifiedMarginTrade()).isTrue();
    assertThat(actualInstrumentInfo.getFundingInterval()).isEqualTo(480);
    assertThat(actualInstrumentInfo.getSettleCoin()).isEqualTo("USDT");
    assertThat(actualInstrumentInfo.getCopyTrading()).isNull();
  }

  @Test
  public void testGetOptionInstrumentsInfo() throws Exception {
    initInstrumentsInfoStub("/getInstrumentOption.json5");

    BybitInstrumentsInfo<BybitInstrumentInfo> instrumentsInfo =
        marketDataServiceRaw.getInstrumentsInfo(BybitCategory.OPTION).getResult();

    assertThat(instrumentsInfo.getList()).hasSize(1);

    BybitOptionInstrumentInfo actualInstrumentInfo =
        (BybitOptionInstrumentInfo) instrumentsInfo.getList().get(0);

    assertThat(actualInstrumentInfo.getSymbol()).isEqualTo("ETH-3JAN23-1250-P");
    assertThat(actualInstrumentInfo.getOptionsType()).isEqualTo(OptionType.PUT);
    assertThat(actualInstrumentInfo.getStatus()).isEqualTo(InstrumentStatus.TRADING);
    assertThat(actualInstrumentInfo.getBaseCoin()).isEqualTo("ETH");
    assertThat(actualInstrumentInfo.getQuoteCoin()).isEqualTo("USD");
    assertThat(actualInstrumentInfo.getLaunchTime()).isEqualTo(new Date(1672560000000L));
    assertThat(actualInstrumentInfo.getDeliveryTime()).isEqualTo(new Date(1672732800000L));
    assertThat(actualInstrumentInfo.getDeliveryFeeRate()).isEqualTo(new BigDecimal("0.00015"));
    assertThat(actualInstrumentInfo.getPriceFilter().getTickSize())
        .isEqualTo(new BigDecimal("0.1"));
    assertThat(actualInstrumentInfo.getPriceFilter().getMinPrice())
        .isEqualTo(new BigDecimal("0.1"));
    assertThat(actualInstrumentInfo.getPriceFilter().getMaxPrice())
        .isEqualTo(new BigDecimal("10000000"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .isEqualTo(new BigDecimal("1500"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
        .isEqualTo(new BigDecimal("0.1"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getQtyStep())
        .isEqualTo(new BigDecimal("0.1"));
    assertThat(actualInstrumentInfo.getSettleCoin()).isEqualTo("USDC");
  }

  @Test
  public void testGetSpotInstrumentsInfo() throws Exception {
    initInstrumentsInfoStub("/getInstrumentSpot.json5");

    BybitInstrumentsInfo<BybitInstrumentInfo> instrumentsInfo =
        marketDataServiceRaw.getInstrumentsInfo(BybitCategory.SPOT).getResult();

    assertThat(instrumentsInfo.getList()).hasSize(1);

    BybitSpotInstrumentInfo actualInstrumentInfo =
        (BybitSpotInstrumentInfo) instrumentsInfo.getList().get(0);

    assertThat(actualInstrumentInfo.getSymbol()).isEqualTo("BTCUSDT");
    assertThat(actualInstrumentInfo.getStatus()).isEqualTo(InstrumentStatus.TRADING);
    assertThat(actualInstrumentInfo.getMarginTrading()).isEqualTo(MarginTrading.BOTH);
    assertThat(actualInstrumentInfo.getBaseCoin()).isEqualTo("BTC");
    assertThat(actualInstrumentInfo.getQuoteCoin()).isEqualTo("USDT");
    assertThat(actualInstrumentInfo.getPriceFilter().getTickSize())
        .isEqualTo(new BigDecimal("0.01"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .isEqualTo(new BigDecimal("71.73956243"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMaxOrderAmt())
        .isEqualTo(new BigDecimal("2000000"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMinOrderQty())
        .isEqualTo(new BigDecimal("0.000048"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getMinOrderAmt())
        .isEqualTo(new BigDecimal("1"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getBasePrecision())
        .isEqualTo(new BigDecimal("0.000001"));
    assertThat(actualInstrumentInfo.getLotSizeFilter().getQuotePrecision())
        .isEqualTo(new BigDecimal("0.00000001"));
  }

  @Test
  public void testGetLinearInverseTicker() throws Exception {
    initTickerStub("/getTickerInverse.json5");

    BybitTickers<BybitTicker> bybitTickers =
        marketDataServiceRaw.getTicker24h(BybitCategory.INVERSE, "BTCUSD").getResult();

    assertThat(bybitTickers.getList()).hasSize(1);

    BybitLinearInverseTicker actualTicker =
        (BybitLinearInverseTicker) bybitTickers.getList().get(0);

    assertThat(actualTicker.getSymbol()).isEqualTo("BTCUSD");
    assertThat(actualTicker.getLastPrice()).isEqualTo(new BigDecimal("16597.00"));
    assertThat(actualTicker.getIndexPrice()).isEqualTo(new BigDecimal("16598.54"));
    assertThat(actualTicker.getMarkPrice()).isEqualTo(new BigDecimal("16596.00"));
    assertThat(actualTicker.getPrevPrice24h()).isEqualTo(new BigDecimal("16464.50"));
    assertThat(actualTicker.getPrice24hPcnt()).isEqualTo(new BigDecimal("0.008047"));
    assertThat(actualTicker.getHighPrice24h()).isEqualTo(new BigDecimal("30912.50"));
    assertThat(actualTicker.getLowPrice24h()).isEqualTo(new BigDecimal("15700.00"));
    assertThat(actualTicker.getPrevPrice1h()).isEqualTo(new BigDecimal("16595.50"));
    assertThat(actualTicker.getOpenInterest()).isEqualTo(new BigDecimal("373504107"));
    assertThat(actualTicker.getOpenInterestValue()).isEqualTo(new BigDecimal("22505.67"));
    assertThat(actualTicker.getTurnover24h()).isEqualTo(new BigDecimal("2352.94950046"));
    assertThat(actualTicker.getVolume24h()).isEqualTo(new BigDecimal("49337318"));
    assertThat(actualTicker.getFundingRate()).isEqualTo(new BigDecimal("-0.001034"));
    assertThat(actualTicker.getNextFundingTime()).isEqualTo(new Date(1672387200000L));
    assertThat(actualTicker.getPredictedDeliveryPrice()).isNull();
    assertThat(actualTicker.getBasisRate()).isNull();
    assertThat(actualTicker.getDeliveryFeeRate()).isNull();
    assertThat(actualTicker.getDeliveryTime()).isEqualTo(new Date(0L));
    assertThat(actualTicker.getAsk1Size()).isEqualTo(new BigDecimal("1"));
    assertThat(actualTicker.getBid1Price()).isEqualTo(new BigDecimal("16596.00"));
    assertThat(actualTicker.getAsk1Price()).isEqualTo(new BigDecimal("16597.50"));
    assertThat(actualTicker.getBid1Size()).isEqualTo(new BigDecimal("1"));
    assertThat(actualTicker.getBasis()).isNull();
  }

  @Test
  public void testGetOptionTicker() throws Exception {
    initTickerStub("/getTickerOption.json5");

    BybitTickers<BybitTicker> bybitTickers =
        marketDataServiceRaw.getTicker24h(BybitCategory.OPTION, "BTC-30DEC22-18000-C").getResult();

    assertThat(bybitTickers.getList()).hasSize(1);

    BybitOptionTicker actualTicker = (BybitOptionTicker) bybitTickers.getList().get(0);

    assertThat(actualTicker.getSymbol()).isEqualTo("BTC-30DEC22-18000-C");
    assertThat(actualTicker.getBid1Price()).isEqualTo(new BigDecimal("0"));
    assertThat(actualTicker.getBid1Size()).isEqualTo(new BigDecimal("0"));
    assertThat(actualTicker.getBid1Iv()).isEqualTo(new BigDecimal("0"));
    assertThat(actualTicker.getAsk1Price()).isEqualTo(new BigDecimal("435"));
    assertThat(actualTicker.getAsk1Size()).isEqualTo(new BigDecimal("0.66"));
    assertThat(actualTicker.getAsk1Iv()).isEqualTo(new BigDecimal("5"));
    assertThat(actualTicker.getLastPrice()).isEqualTo(new BigDecimal("435"));
    assertThat(actualTicker.getHighPrice24h()).isEqualTo(new BigDecimal("435"));
    assertThat(actualTicker.getLowPrice24h()).isEqualTo(new BigDecimal("165"));
    assertThat(actualTicker.getMarkPrice()).isEqualTo(new BigDecimal("0.00000009"));
    assertThat(actualTicker.getIndexPrice()).isEqualTo(new BigDecimal("16600.55"));
    assertThat(actualTicker.getMarkIv()).isEqualTo(new BigDecimal("0.7567"));
    assertThat(actualTicker.getUnderlyingPrice()).isEqualTo(new BigDecimal("16590.42"));
    assertThat(actualTicker.getOpenInterest()).isEqualTo(new BigDecimal("6.3"));
    assertThat(actualTicker.getTurnover24h()).isEqualTo(new BigDecimal("2482.73"));
    assertThat(actualTicker.getVolume24h()).isEqualTo(new BigDecimal("0.15"));
    assertThat(actualTicker.getTotalVolume()).isEqualTo(new BigDecimal("99"));
    assertThat(actualTicker.getTotalTurnover()).isEqualTo(new BigDecimal("1967653"));
    assertThat(actualTicker.getDelta()).isEqualTo(new BigDecimal("0.00000001"));
    assertThat(actualTicker.getGamma()).isEqualTo(new BigDecimal("0.00000001"));
    assertThat(actualTicker.getVega()).isEqualTo(new BigDecimal("0.00000004"));
    assertThat(actualTicker.getTheta()).isEqualTo(new BigDecimal("-0.00000152"));
    assertThat(actualTicker.getPredictedDeliveryPrice()).isEqualTo(new BigDecimal("0"));
    assertThat(actualTicker.getChange24h()).isEqualTo(new BigDecimal("86"));
  }

  @Test
  public void testGetSpotTicker() throws Exception {
    initTickerStub("/getTickerSpot.json5");

    BybitTickers<BybitTicker> bybitTickers =
        marketDataServiceRaw.getTicker24h(BybitCategory.SPOT, "BTCUSDT").getResult();

    assertThat(bybitTickers.getList()).hasSize(1);

    BybitSpotTicker actualTicker = (BybitSpotTicker) bybitTickers.getList().get(0);

    assertThat(actualTicker.getSymbol()).isEqualTo("BTCUSDT");
    assertThat(actualTicker.getBid1Price()).isEqualTo(new BigDecimal("20517.96"));
    assertThat(actualTicker.getBid1Size()).isEqualTo(new BigDecimal("2"));
    assertThat(actualTicker.getAsk1Price()).isEqualTo(new BigDecimal("20527.77"));
    assertThat(actualTicker.getAsk1Size()).isEqualTo(new BigDecimal("1.862172"));
    assertThat(actualTicker.getLastPrice()).isEqualTo(new BigDecimal("20533.13"));
    assertThat(actualTicker.getPrevPrice24h()).isEqualTo(new BigDecimal("20393.48"));
    assertThat(actualTicker.getPrice24hPcnt()).isEqualTo(new BigDecimal("0.0068"));
    assertThat(actualTicker.getHighPrice24h()).isEqualTo(new BigDecimal("21128.12"));
    assertThat(actualTicker.getLowPrice24h()).isEqualTo(new BigDecimal("20318.89"));
    assertThat(actualTicker.getTurnover24h()).isEqualTo(new BigDecimal("243765620.65899866"));
    assertThat(actualTicker.getVolume24h()).isEqualTo(new BigDecimal("11801.27771"));
    assertThat(actualTicker.getUsdIndexPrice()).isEqualTo(new BigDecimal("20784.12009279"));
  }
}
