package info.bitrich.xchangestream.binancefutures.dto;

import static com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_COMMENTS;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction.BinanceWebSocketTypes.ACCOUNT_UPDATE;
import static info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesPosition.guessContract;
import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesAccountUpdateRaw;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesAccountUpdateTransaction;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesBalance;
import info.bitrich.xchangestream.binancefuture.dto.BinanceFuturesPosition;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import junit.framework.TestCase;
import org.junit.Test;
import org.knowm.xchange.currency.Currency;


public class AccountUpdateBinanceWebSocketTransactionTest extends TestCase {

  private final ObjectMapper mapper = new ObjectMapper()
      .enable(ALLOW_COMMENTS)
      .disable(FAIL_ON_UNKNOWN_PROPERTIES);

  @Test
  public void testMapping() throws IOException {
    InputStream stream = this.getClass().getResourceAsStream("testAccountUpdate.json");

    BinanceFuturesAccountUpdateTransaction binanceFuturesAccountUpdateTransaction =
        mapper.readValue(stream, BinanceFuturesAccountUpdateTransaction.class);

    assertThat(binanceFuturesAccountUpdateTransaction).isNotNull();
    assertThat(binanceFuturesAccountUpdateTransaction.eventType).isEqualTo(ACCOUNT_UPDATE);
    assertThat(binanceFuturesAccountUpdateTransaction.getEventTime().getTime())
        .isEqualTo(1564745798939L);

    BinanceFuturesAccountUpdateRaw accountUpdate =
        new BinanceFuturesAccountUpdateRaw(binanceFuturesAccountUpdateTransaction);

    assertThat(accountUpdate.getTransaction()).isEqualTo(1564745798938L);
    assertThat(accountUpdate.getBalances()).hasSize(2);
    assertThat(accountUpdate.getPositions()).hasSize(3);


    BinanceFuturesBalance usdtBalance = accountUpdate
        .getBalances()
        .stream()
        .filter(it -> it.getCurrency().equals(new Currency("USDT")))
        .findAny()
        .orElse(null);

    assertThat(usdtBalance).isNotNull();
    assertThat(usdtBalance.getWalletBalance()).isEqualTo(new BigDecimal("122624.12345678"));
    assertThat(usdtBalance.getCrossWalletBalance()).isEqualTo(new BigDecimal("100.12345678"));
    assertThat(usdtBalance.getBalanceChange()).isEqualTo(new BigDecimal("50.12345678"));


    BinanceFuturesBalance busdBalance = accountUpdate
        .getBalances()
        .stream()
        .filter(it -> it.getCurrency().equals(new Currency("BUSD")))
        .findAny()
        .orElse(null);

    assertThat(busdBalance).isNotNull();
    assertThat(busdBalance.getWalletBalance()).isEqualTo(new BigDecimal("1.00000000"));
    assertThat(busdBalance.getCrossWalletBalance()).isEqualTo(new BigDecimal("0.00000000"));
    assertThat(busdBalance.getBalanceChange()).isEqualTo(new BigDecimal("-49.12345678"));


    BinanceFuturesPosition bothPosition = accountUpdate
        .getPositions()
        .stream()
        .filter(it -> it.getPositionSide().equals("BOTH"))
        .findAny()
        .orElse(null);

    assertThat(bothPosition).isNotNull();
    assertThat(bothPosition.getFuturesContract()).isEqualTo(guessContract("BTCUSDT"));
    assertThat(bothPosition.getPositionAmount()).isEqualTo(new BigDecimal("0"));
    assertThat(bothPosition.getEntryPrice()).isEqualTo(new BigDecimal("0.00000"));
    assertThat(bothPosition.getAccumulatedRealized()).isEqualTo(new BigDecimal("200"));
    assertThat(bothPosition.getUnrealizedPnl()).isEqualTo(new BigDecimal("0"));
    assertThat(bothPosition.getMarginType()).isEqualTo("isolated");
    assertThat(bothPosition.getIsolatedWallet()).isEqualTo(new BigDecimal("0.00000000"));


    BinanceFuturesPosition longPosition = accountUpdate
        .getPositions()
        .stream()
        .filter(it -> it.getPositionSide().equals("LONG"))
        .findAny()
        .orElse(null);

    assertThat(longPosition).isNotNull();
    assertThat(longPosition.getFuturesContract()).isEqualTo(guessContract("BTCUSDT"));
    assertThat(longPosition.getPositionAmount()).isEqualTo(new BigDecimal("20"));
    assertThat(longPosition.getEntryPrice()).isEqualTo(new BigDecimal("6563.66500"));
    assertThat(longPosition.getAccumulatedRealized()).isEqualTo(new BigDecimal("0"));
    assertThat(longPosition.getUnrealizedPnl()).isEqualTo(new BigDecimal("2850.21200"));
    assertThat(longPosition.getMarginType()).isEqualTo("isolated");
    assertThat(longPosition.getIsolatedWallet()).isEqualTo(new BigDecimal("13200.70726908"));


    BinanceFuturesPosition shortPosition = accountUpdate
        .getPositions()
        .stream()
        .filter(it -> it.getPositionSide().equals("SHORT"))
        .findAny()
        .orElse(null);

    assertThat(shortPosition).isNotNull();
    assertThat(shortPosition.getFuturesContract()).isEqualTo(guessContract("BTCUSDT"));
    assertThat(shortPosition.getPositionAmount()).isEqualTo(new BigDecimal("-10"));
    assertThat(shortPosition.getEntryPrice()).isEqualTo(new BigDecimal("6563.86000"));
    assertThat(shortPosition.getAccumulatedRealized()).isEqualTo(new BigDecimal("-45.04000000"));
    assertThat(shortPosition.getUnrealizedPnl()).isEqualTo(new BigDecimal("-1423.15600"));
    assertThat(shortPosition.getMarginType()).isEqualTo("isolated");
    assertThat(shortPosition.getIsolatedWallet()).isEqualTo(new BigDecimal("6570.42511771"));
  }
}
