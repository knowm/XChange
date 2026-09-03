package info.bitrich.xchangestream.gateio.dto.response;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.gateio.config.Config;
import info.bitrich.xchangestream.gateio.dto.response.balance.GateioMultipleSpotBalanceNotification;
import info.bitrich.xchangestream.gateio.dto.response.funding.GateioMultipleTickerAndFundingNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookNotification;
import info.bitrich.xchangestream.gateio.dto.response.orderbook.GateioOrderBookV2FuturesNotification;
import info.bitrich.xchangestream.gateio.dto.response.ticker.GateioTickerNotification;
import info.bitrich.xchangestream.gateio.dto.response.trade.GateioTradeNotification;
import info.bitrich.xchangestream.gateio.dto.response.usertrade.GateioMultipleUserTradeNotification;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.derivative.FuturesContract;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class GateioWsNotificationTest {

  ObjectMapper objectMapper = Config.getInstance().getObjectMapper();

  @Test
  void deserialize_trades() throws Exception {
    GateioWsNotification notification = readNotification("spot.trades.update.json");
    assertThat(notification).isInstanceOf(GateioTradeNotification.class);
    assertThat(notification.getTime().toEpochMilli()).isEqualTo(1606292218000L);
    assertThat(notification.getTimeMs().toEpochMilli()).isEqualTo(1606292218231L);
  }

  @Test
  void deserialize_ticker() throws Exception {
    GateioWsNotification notification = readNotification("spot.ticker.update.json");
    assertThat(notification).isInstanceOf(GateioTickerNotification.class);
    assertThat(notification.getTime()).isEqualTo(Instant.parse("2023-08-09T22:36:06Z"));
    assertThat(notification.getTimeMs()).isEqualTo(Instant.parse("2023-08-09T22:36:06.926Z"));
  }

  @Test
  void deserialize_orderbook() throws Exception {
    GateioWsNotification notification = readNotification("spot.order_book.update.json");
    assertThat(notification).isInstanceOf(GateioOrderBookNotification.class);
    assertThat(notification.getTime()).isEqualTo(Instant.parse("2023-08-11T12:32:31Z"));
    assertThat(notification.getTimeMs()).isEqualTo(Instant.parse("2023-08-11T12:32:31.420Z"));
  }

  @Test
  void deserialize_orderbookV2_futures() throws Exception {
    GateioWsNotification notification = readNotification("futures.order_bookV2.update.json");
    assertThat(notification).isInstanceOf(GateioOrderBookV2FuturesNotification.class);
    assertThat(notification.getTimeMs()).isEqualTo(Instant.parse("2025-04-03T09:37:07.020Z"));
    assertThat(((GateioOrderBookV2FuturesNotification) notification).getResult().getTimestamp()).isEqualTo(Instant.parse("2025-04-03T09:37:07.017Z"));
    assertThat(((GateioOrderBookV2FuturesNotification) notification).getResult().getBids().size()).isEqualTo(5);
    assertThat(((GateioOrderBookV2FuturesNotification) notification).getResult().getBids().get(0).getPrice()).isEqualTo("83702.2");
    assertThat(((GateioOrderBookV2FuturesNotification) notification).getResult().getBids().get(0).getSize()).isEqualTo("62");
    assertThat(((GateioOrderBookV2FuturesNotification) notification).getResult().getInstrument()).isEqualTo(new FuturesContract("BTC/USDT/PERP"));
  }

  @Test
  void deserialize_usertrades() throws Exception {
    GateioWsNotification notification = readNotification("spot.usertrades.update.json");
    assertThat(notification).isInstanceOf(GateioMultipleUserTradeNotification.class);
    assertThat(notification.getTime()).isEqualTo(Instant.parse("2023-08-10T18:29:19Z"));
    assertThat(notification.getTimeMs()).isEqualTo(Instant.parse("2023-08-10T18:29:19.338Z"));
  }

  @Test
  void deserialize_balances() throws Exception {
    GateioWsNotification notification = readNotification("spot.balance.update.json");
    assertThat(notification).isInstanceOf(GateioMultipleSpotBalanceNotification.class);
    assertThat(notification.getTime()).isEqualTo(Instant.parse("2023-08-10T22:41:13Z"));
    assertThat(notification.getTimeMs()).isEqualTo(Instant.parse("2023-08-10T22:41:13.893Z"));
    assertThat(((GateioMultipleSpotBalanceNotification) notification).getResult().get(0).getTime()).isEqualTo(Instant.parse("2023-08-10T22:41:13Z"));
    assertThat(((GateioMultipleSpotBalanceNotification) notification).getResult().get(0).getTimeMs()).isEqualTo(Instant.parse("2023-08-10T22:41:13.890Z"));
  }

  @Test
  void deserialize_funding() throws Exception {
    GateioWsNotification notification = readNotification("futures.funding.update.json");
    assertThat(notification).isInstanceOf(GateioMultipleTickerAndFundingNotification.class);
    assertThat(notification.getTime()).isEqualTo(Instant.parse("2018-11-08T06:38:06Z"));
    assertThat(notification.getTimeMs()).isEqualTo(Instant.parse("2018-11-08T06:38:06.123Z"));
    assertThat(((GateioMultipleTickerAndFundingNotification) notification).getResult().get(0).getFundingRate().equals(new BigDecimal("-0.000114"))).isTrue();
  }



  private GateioWsNotification readNotification(String resourceName) throws IOException {
    return objectMapper.readValue(
        getClass().getClassLoader().getResourceAsStream(resourceName), GateioWsNotification.class);
  }
}
