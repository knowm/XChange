package info.bitrich.xchangestream.bybit;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.bybit.dto.BybitResponse;
import info.bitrich.xchangestream.bybit.dto.marketdata.BybitOrderbook;
import info.bitrich.xchangestream.bybit.dto.trade.*;
import info.bitrich.xchangestream.service.netty.StreamingObjectMapperHelper;
import org.junit.Test;
import org.knowm.xchange.bybit.dto.marketdata.tickers.linear.BybitLinearInverseTicker;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.marketdata.Trades;
import org.knowm.xchange.dto.trade.MarketOrder;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.Locale;

import static info.bitrich.xchangestream.bybit.BybitStreamAdapters.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.knowm.xchange.bybit.dto.BybitCategory.OPTION;
import static org.knowm.xchange.bybit.dto.trade.BybitOrderType.MARKET;
import static org.knowm.xchange.bybit.dto.trade.details.BybitTimeInForce.IOC;
import static org.knowm.xchange.dto.Order.OrderStatus.FILLED;

public class BybitStreamAdaptersTest {

  ObjectMapper mapper = StreamingObjectMapperHelper.getObjectMapper();

  @Test
  public void adaptOrderBookTest() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("orderBookSnapshotResponse.json5"));
    BybitOrderbook bybitOrderbookSnapshot = mapper.treeToValue(jsonNode, BybitOrderbook.class);

    OrderBook orderBook =
        adaptOrderBook(bybitOrderbookSnapshot, new FuturesContract("BTC/USDT/PERP"));
    assertThat(orderBook.getTimeStamp().getTime()).isEqualTo(1672304484976L);

    assertThat(orderBook.getBids().get(0).getLimitPrice()).isEqualTo(new BigDecimal("16493.50"));
    assertThat(orderBook.getBids().get(0).getOriginalAmount()).isEqualTo(new BigDecimal("0.006"));

    assertThat(orderBook.getAsks().get(0).getLimitPrice()).isEqualTo(new BigDecimal("16611.00"));
    assertThat(orderBook.getAsks().get(0).getOriginalAmount()).isEqualTo(new BigDecimal("0.029"));
  }

  @Test
  public void adaptTradesTest() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("tradeResponse.json5"));
    List<BybitTrade> bybitTradeList =
        mapper.treeToValue(
            jsonNode.get("data"),
            mapper.getTypeFactory().constructCollectionType(List.class, BybitTrade.class));
    Trades trades = adaptTrades(bybitTradeList, new FuturesContract("BTC/USDT/PERP"));
    assertThat(trades.getTrades().get(0).getId()).isEqualTo("20f43950-d8dd-5b31-9112-a178eb6023af");
    assertThat(trades.getTrades().get(0).getTimestamp().getTime()).isEqualTo(1672304486865L);
    assertThat(trades.getTrades().get(0).getInstrument())
        .isEqualTo(new FuturesContract("BTC/USDT/PERP"));
    assertThat(trades.getTrades().get(0).getType()).isEqualTo(OrderType.BID);
    assertThat(trades.getTrades().get(0).getOriginalAmount()).isEqualTo(new BigDecimal("0.001"));
    assertThat(trades.getTrades().get(0).getPrice()).isEqualTo(new BigDecimal("16578.50"));
  }

  @Test
  public void adaptOrdersChangesTest() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("orderChangeResponse.json5"));
    BybitOrderChangesResponse bybitOrderChangesResponse =
        mapper.treeToValue(jsonNode, BybitOrderChangesResponse.class);
    DateTimeFormatter dateParser =
        new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddLLLyy")
            .toFormatter(Locale.US);
    Order order = adaptOrdersChanges(bybitOrderChangesResponse.getData()).get(0);
    assertThat(order.getInstrument()).isEqualTo(new OptionsContract("ETH/USDC/221230/1400/C"));
    assertThat(order.getId()).isEqualTo("5cf98598-39a7-459e-97bf-76ca765ee020");
    assertThat(order.getType()).isEqualTo(OrderType.ASK);
    assertThat(order instanceof MarketOrder).isEqualTo(true);
    assert order instanceof MarketOrder;
    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("75"));
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("1"));
    // here it's updated time, because Order don't have other field
    assertThat(order.getTimestamp().getTime()).isEqualTo(1672364262457L);
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("1"));
    assertThat(order.getFee()).isEqualTo(new BigDecimal("0.358635"));
    assertThat(order.getStatus()).isEqualTo(FILLED);
    assertThat(order.getLeverage()).isEqualTo(null);
  }

  @Test
  public void adaptPositionChanges() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("positionChangeResponse.json5"));
    BybitPositionChangesResponse bybitPositionChangesResponse =
        mapper.treeToValue(jsonNode, BybitPositionChangesResponse.class);
    List<OpenPosition> openPositions =
        BybitStreamAdapters.adaptPositionChanges(bybitPositionChangesResponse.getData())
            .getOpenPositions();
    assertThat(openPositions.size()).isEqualTo(1);
    assertThat(openPositions.get(0).getInstrument())
        .isEqualTo(new FuturesContract("BTC/USDT/PERP"));
    assertThat(openPositions.get(0).getLiquidationPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(openPositions.get(0).getPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(openPositions.get(0).getSize()).isEqualTo(BigDecimal.ZERO);
    assertThat(openPositions.get(0).getType()).isEqualTo(null);
    assertThat(openPositions.get(0).getUnRealisedPnl()).isEqualTo(BigDecimal.ZERO);
  }

  @Test
  public void adaptComplexPositionChanges() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("positionChangeResponse.json5"));
    BybitPositionChangesResponse bybitPositionChangesResponse =
        mapper.treeToValue(jsonNode, BybitPositionChangesResponse.class);
    List<BybitComplexPositionChanges> bybitPositionChanges =
        BybitStreamAdapters.adaptComplexPositionChanges(bybitPositionChangesResponse.getData());
    assertThat(bybitPositionChanges.size()).isEqualTo(1);
    assertThat(bybitPositionChanges.get(0).getInstrument())
        .isEqualTo(new FuturesContract("BTC/USDT/PERP"));
    assertThat(bybitPositionChanges.get(0).getLiquidationPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getSize()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getType()).isEqualTo(null);
    assertThat(bybitPositionChanges.get(0).getUnRealisedPnl()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getPositionIdx()).isEqualTo(2);
    assertThat(bybitPositionChanges.get(0).getTradeMode()).isEqualTo(0);
    assertThat(bybitPositionChanges.get(0).getRiskId()).isEqualTo(1);
    assertThat(bybitPositionChanges.get(0).getRiskLimitValue()).isEqualTo("2000000");
    assertThat(bybitPositionChanges.get(0).getLeverage()).isEqualTo(new BigDecimal("10"));
    assertThat(bybitPositionChanges.get(0).getPositionValue()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getPositionBalance()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getMarkPrice()).isEqualTo(new BigDecimal("28184.5"));
    assertThat(bybitPositionChanges.get(0).getPositionIM()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getPositionMM()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getTakeProfit()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getStopLoss()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getTrailingStop()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getCurRealisedPnl()).isEqualTo(new BigDecimal("1.26"));
    assertThat(bybitPositionChanges.get(0).getCumRealisedPnl())
        .isEqualTo(new BigDecimal("-25.06579337"));
    assertThat(bybitPositionChanges.get(0).getSessionAvgPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getCreatedTime().getTime()).isEqualTo(1694402496913L);
    assertThat(bybitPositionChanges.get(0).getUpdatedTime().getTime()).isEqualTo(1697682317038L);
    assertThat(bybitPositionChanges.get(0).getLiquidationPrice()).isEqualTo(BigDecimal.ZERO);
    assertThat(bybitPositionChanges.get(0).getBustPrice() == null).isTrue();
    assertThat(bybitPositionChanges.get(0).getPositionStatus()).isEqualTo("Normal");
    assertThat(bybitPositionChanges.get(0).getAdlRankIndicator()).isEqualTo(0);
    assertThat(bybitPositionChanges.get(0).getAutoAddMargin()).isEqualTo(0);
    assertThat(bybitPositionChanges.get(0).getLeverageSysUpdatedTime()).isEmpty();
    assertThat(bybitPositionChanges.get(0).getMmrSysUpdatedTime()).isEmpty();
    assertThat(bybitPositionChanges.get(0).getSeq()).isEqualTo(8327597863L);
    assertThat(bybitPositionChanges.get(0).isReduceOnly()).isEqualTo(false);
  }

  @Test
  public void adaptComplexOrdersChangesTest() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader().getResourceAsStream("orderChangeResponse.json5"));
    BybitOrderChangesResponse bybitOrderChangesResponse =
        mapper.treeToValue(jsonNode, BybitOrderChangesResponse.class);
    DateTimeFormatter dateParser =
        new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("ddLLLyy")
            .toFormatter(Locale.US);
    BybitComplexOrderChanges order =
        adaptComplexOrdersChanges(bybitOrderChangesResponse.getData()).get(0);
    assertThat(order.getInstrument()).isEqualTo(new OptionsContract("ETH/USDC/221230/1400/C"));
    assertThat(order.getId()).isEqualTo("5cf98598-39a7-459e-97bf-76ca765ee020");
    assertThat(order.getType()).isEqualTo(OrderType.ASK);

    assertThat(order.getAveragePrice()).isEqualTo(new BigDecimal("75"));
    assertThat(order.getOriginalAmount()).isEqualTo(new BigDecimal("1"));
    // here it's create time
    assertThat(order.getTimestamp().getTime()).isEqualTo(1672364262444L);
    assertThat(order.getCumulativeAmount()).isEqualTo(new BigDecimal("1"));
    assertThat(order.getFee()).isEqualTo(new BigDecimal("0.358635"));
    assertThat(order.getStatus()).isEqualTo(FILLED);
    assertThat(order.getLeverage()).isEqualTo(null);
    assertThat(order.getCategory()).isEqualTo(OPTION);
    assertThat(order.getBlockTradeId()).isEmpty();
    assertThat(order.getPositionIdx()).isEqualTo(0);
    assertThat(order.getCreateType()).isEqualTo(null);
    assertThat(order.getCancelType()).isEqualTo("UNKNOWN");
    assertThat(order.getRejectReason()).isEqualTo("EC_NoError");
    assertThat(order.getLeavesQty() == null).isTrue();
    assertThat(order.getLeavesValue() == null).isTrue();
    assertThat(order.getCumExecValue()).isEqualTo(new BigDecimal("75"));
    assertThat(order.getFeeCurrency()).isEmpty();
    assertThat(order.getTimeInForce()).isEqualTo(IOC);
    assertThat(order.getOrderType()).isEqualTo(MARKET);
    assertThat(order.getStopOrderType()).isEmpty();
    assertThat(order.getOcoTriggerBy()).isEqualTo(null);
    assertThat(order.getOrderIv()).isEmpty();
    assertThat(order.getMarketUnit()).isEmpty();
    assertThat(order.getTriggerPrice() == null).isTrue();
    assertThat(order.getTakeProfit() == null).isTrue();
    assertThat(order.getStopLoss() == null).isTrue();
    assertThat(order.getTpslMode()).isEmpty();
    assertThat(order.getTpLimitPrice() == null).isTrue();
    assertThat(order.getSlLimitPrice() == null).isTrue();
    assertThat(order.getTpTriggerBy()).isEmpty();
    assertThat(order.getSlTriggerBy()).isEmpty();
    assertThat(order.getTriggerDirection()).isEqualTo(0);
    assertThat(order.getTriggerBy()).isEmpty();
    assertThat(order.getLastPriceOnCreated()).isEmpty();
    assertThat(order.isReduceOnly()).isEqualTo(false);
    assertThat(order.isCloseOnTrigger()).isEqualTo(false);
    assertThat(order.getPlaceType()).isEqualTo("price");
    assertThat(order.getSmpType()).isEqualTo("None");
    assertThat(order.getSmpGroup()).isEqualTo(0);
    assertThat(order.getSmpOrderId()).isEmpty();
    assertThat(order.getUpdatedTime().getTime()).isEqualTo(1672364262457L);
  }

  @Test
  public void adaptTickerSnapshotTest() throws Exception {
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("tickerSnapshotResponse.json5"));
    BybitResponse<BybitLinearInverseTicker> bybitResponse =
        mapper.treeToValue(jsonNode, new TypeReference<>() {});

    assertThat(bybitResponse.getType()).isEqualTo("snapshot");
    assertThat(bybitResponse.getTopic()).isEqualTo("tickers.BTCUSDT");

    BybitLinearInverseTicker data = bybitResponse.getData();
    assertThat(data.getSymbol()).isEqualTo("BTCUSDT");
    assertThat(data.getFundingRate()).isEqualTo(new BigDecimal("0.0001"));
    assertThat(data.getOpenInterest()).isEqualTo(new BigDecimal("50000.000"));
    assertThat(data.getFundingIntervalHour()).isEqualTo(8);

    Ticker ticker = adaptTicker(data);
    assertThat(ticker.getInstrument()).isEqualTo(new FuturesContract("BTC/USDT/PERP"));
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("70123.50"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("70123.00"));
    assertThat(ticker.getBidSize()).isEqualTo(new BigDecimal("2.300"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("70124.00"));
    assertThat(ticker.getAskSize()).isEqualTo(new BigDecimal("1.500"));
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("70500.00"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("69200.00"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("12345.670"));
    assertThat(ticker.getQuoteVolume()).isEqualTo(new BigDecimal("865432100.00"));
    // price24hPcnt 0.00896 → percentageChange 0.896 (×100)
    assertThat(ticker.getPercentageChange()).isEqualByComparingTo(new BigDecimal("0.896"));
    assertThat(ticker.getTimestamp()).isNotNull();
  }

  @Test
  public void adaptTickerDeltaDeserializationTest() throws Exception {
    // Verify delta messages deserialize correctly with partial fields
    JsonNode jsonNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("tickerDeltaResponse.json5"));
    BybitResponse<BybitLinearInverseTicker> bybitResponse =
        mapper.treeToValue(jsonNode, new TypeReference<>() {});

    assertThat(bybitResponse.getType()).isEqualTo("delta");

    BybitLinearInverseTicker delta = bybitResponse.getData();
    assertThat(delta.getSymbol()).isEqualTo("BTCUSDT");
    assertThat(delta.getLastPrice()).isEqualTo(new BigDecimal("70150.00"));
    assertThat(delta.getBid1Price()).isEqualTo(new BigDecimal("70149.50"));
    assertThat(delta.getBid1Size()).isEqualTo(new BigDecimal("3.100"));
    assertThat(delta.getAsk1Price()).isEqualTo(new BigDecimal("70150.50"));
    assertThat(delta.getAsk1Size()).isEqualTo(new BigDecimal("1.200"));
    // Fields not in delta should be null
    assertThat(delta.getHighPrice24h()).isNull();
    assertThat(delta.getLowPrice24h()).isNull();
    assertThat(delta.getVolume24h()).isNull();
    assertThat(delta.getFundingRate()).isNull();
    assertThat(delta.getOpenInterest()).isNull();
  }

  @Test
  public void adaptTickerDeltaMergeTest() throws Exception {
    // Load snapshot
    JsonNode snapshotNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("tickerSnapshotResponse.json5"));
    BybitResponse<BybitLinearInverseTicker> snapshotResponse =
        mapper.treeToValue(snapshotNode, new TypeReference<>() {});
    BybitLinearInverseTicker snapshot = snapshotResponse.getData();

    // Load delta
    JsonNode deltaNode =
        mapper.readTree(
            ClassLoader.getSystemClassLoader()
                .getResourceAsStream("tickerDeltaResponse.json5"));
    BybitResponse<BybitLinearInverseTicker> deltaResponse =
        mapper.treeToValue(deltaNode, new TypeReference<>() {});
    BybitLinearInverseTicker delta = deltaResponse.getData();

    // Simulate merge: delta fields override snapshot, rest preserved
    BybitLinearInverseTicker merged = BybitLinearInverseTicker.builder()
        .symbol(snapshot.getSymbol())
        .lastPrice(delta.getLastPrice() != null ? delta.getLastPrice() : snapshot.getLastPrice())
        .bid1Price(delta.getBid1Price() != null ? delta.getBid1Price() : snapshot.getBid1Price())
        .bid1Size(delta.getBid1Size() != null ? delta.getBid1Size() : snapshot.getBid1Size())
        .ask1Price(delta.getAsk1Price() != null ? delta.getAsk1Price() : snapshot.getAsk1Price())
        .ask1Size(delta.getAsk1Size() != null ? delta.getAsk1Size() : snapshot.getAsk1Size())
        .highPrice24h(delta.getHighPrice24h() != null ? delta.getHighPrice24h() : snapshot.getHighPrice24h())
        .lowPrice24h(delta.getLowPrice24h() != null ? delta.getLowPrice24h() : snapshot.getLowPrice24h())
        .volume24h(delta.getVolume24h() != null ? delta.getVolume24h() : snapshot.getVolume24h())
        .turnover24h(delta.getTurnover24h() != null ? delta.getTurnover24h() : snapshot.getTurnover24h())
        .price24hPcnt(delta.getPrice24hPcnt() != null ? delta.getPrice24hPcnt() : snapshot.getPrice24hPcnt())
        .indexPrice(snapshot.getIndexPrice())
        .markPrice(snapshot.getMarkPrice())
        .openInterest(snapshot.getOpenInterest())
        .openInterestValue(snapshot.getOpenInterestValue())
        .fundingRate(snapshot.getFundingRate())
        .nextFundingTime(snapshot.getNextFundingTime())
        .fundingIntervalHour(snapshot.getFundingIntervalHour())
        .build();

    Ticker ticker = adaptTicker(merged);

    // Delta fields should be updated
    assertThat(ticker.getLast()).isEqualTo(new BigDecimal("70150.00"));
    assertThat(ticker.getBid()).isEqualTo(new BigDecimal("70149.50"));
    assertThat(ticker.getBidSize()).isEqualTo(new BigDecimal("3.100"));
    assertThat(ticker.getAsk()).isEqualTo(new BigDecimal("70150.50"));
    assertThat(ticker.getAskSize()).isEqualTo(new BigDecimal("1.200"));

    // Snapshot fields should be preserved
    assertThat(ticker.getHigh()).isEqualTo(new BigDecimal("70500.00"));
    assertThat(ticker.getLow()).isEqualTo(new BigDecimal("69200.00"));
    assertThat(ticker.getVolume()).isEqualTo(new BigDecimal("12345.670"));
    assertThat(ticker.getQuoteVolume()).isEqualTo(new BigDecimal("865432100.00"));
  }
}
