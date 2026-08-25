package info.bitrich.xchangestream.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import info.bitrich.xchangestream.cryptocom.dto.CryptoComUserTradeUpdate;
import java.math.BigDecimal;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.account.CryptoComBalance;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order.OrderType;
import org.knowm.xchange.dto.account.Balance;
import org.knowm.xchange.dto.trade.UserTrade;

public class CryptoComStreamingAdaptersTest {

  @Test
  public void testAdaptUserTrade() {
    CryptoComUserTradeUpdate update = new CryptoComUserTradeUpdate();
    update.setTradeId("38246881");
    update.setOrderId("18342311");
    update.setInstrumentName("BTC_USDT");
    update.setSide("BUY");
    update.setPrice("50000.0");
    update.setQuantity("0.005000");
    update.setFee("0.000005");
    update.setFeeCurrency("BTC");
    update.setCreateTime(1785085695512L);

    UserTrade trade = CryptoComStreamingAdapters.adaptUserTrade(update);

    assertThat(trade.getId()).isEqualTo("38246881");
    assertThat(trade.getOrderId()).isEqualTo("18342311");
    assertThat(trade.getInstrument()).isEqualTo(CurrencyPair.BTC_USDT);
    assertThat(trade.getType()).isEqualTo(OrderType.BID);
    assertThat(trade.getPrice()).isEqualByComparingTo(new BigDecimal("50000.0"));
    assertThat(trade.getOriginalAmount()).isEqualByComparingTo(new BigDecimal("0.005000"));
    assertThat(trade.getFeeAmount()).isEqualByComparingTo(new BigDecimal("0.000005"));
    assertThat(trade.getFeeCurrency()).isEqualTo(Currency.BTC);
    assertThat(trade.getTimestamp()).isEqualTo(new Date(1785085695512L));
  }

  @Test
  public void testAdaptUserTradeWithNullFeeCurrency() {
    CryptoComUserTradeUpdate update = new CryptoComUserTradeUpdate();
    update.setInstrumentName("BTC_USDT");
    update.setSide("SELL");

    UserTrade trade = CryptoComStreamingAdapters.adaptUserTrade(update);

    assertThat(trade.getType()).isEqualTo(OrderType.ASK);
    assertThat(trade.getFeeCurrency()).isNull();
    assertThat(trade.getTimestamp()).isNull();
  }

  @Test
  public void testAdaptBalance() {
    CryptoComBalance.PositionBalance position = new CryptoComBalance.PositionBalance();
    position.setInstrumentName("BTC");
    position.setQuantity("0.01500000");

    Balance balance = CryptoComStreamingAdapters.adaptBalance(position);

    assertThat(balance.getCurrency()).isEqualTo(Currency.BTC);
    assertThat(balance.getTotal()).isEqualByComparingTo(new BigDecimal("0.01500000"));
  }
}
