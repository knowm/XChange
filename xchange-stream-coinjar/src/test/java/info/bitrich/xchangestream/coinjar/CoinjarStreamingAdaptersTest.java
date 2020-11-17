package info.bitrich.xchangestream.coinjar;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.coinjar.dto.CoinjarWebSocketUserTradeEvent;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Date;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.UserTrade;

public class CoinjarStreamingAdaptersTest {

  @Test
  public void testAdaptTopicToCurrencyPair() {
    String topic = "book:BTCAUD";
    assertThat(CoinjarStreamingAdapters.adaptTopicToCurrencyPair(topic))
        .isEqualTo(CurrencyPair.BTC_AUD);
  }

  @Test
  public void testAdaptTopicToCurrencyPairFourChars() {
    String topic = "book:USDC-AUD";
    assertThat(CoinjarStreamingAdapters.adaptTopicToCurrencyPair(topic))
        .isEqualTo(new CurrencyPair(Currency.USDC, Currency.AUD));
  }

  @Test
  public void testAdaptUserTrade() {
    CoinjarWebSocketUserTradeEvent.Payload.Fill fill =
        new CoinjarWebSocketUserTradeEvent.Payload.Fill(
            "93.82",
            "2018-08-28T05:16:37.405702Z",
            9130900,
            "0.01000000",
            "buy",
            "BTCAUD",
            "9382.00000000",
            280117631,
            "taker");
    CoinjarWebSocketUserTradeEvent.Payload payload =
        new CoinjarWebSocketUserTradeEvent.Payload(fill);
    CoinjarWebSocketUserTradeEvent event =
        new CoinjarWebSocketUserTradeEvent("private", "private:fill", null, payload);
    UserTrade userTrade = CoinjarStreamingAdapters.adaptUserTrade(event);

    UserTrade expected =
        new UserTrade.Builder()
            .id("9130900")
            .orderId("280117631")
            .currencyPair(CurrencyPair.BTC_AUD)
            .originalAmount(new BigDecimal("0.01000000"))
            .timestamp(Date.from(ZonedDateTime.parse("2018-08-28T05:16:37.405702Z").toInstant()))
            .price(new BigDecimal("9382.00000000"))
            .type(Order.OrderType.BID)
            .build();
    assertThat(userTrade).isEqualTo(expected);
  }
}
