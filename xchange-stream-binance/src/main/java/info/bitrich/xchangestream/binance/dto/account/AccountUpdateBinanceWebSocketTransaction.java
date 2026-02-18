package info.bitrich.xchangestream.binance.dto.account;

import static org.knowm.xchange.binance.BinanceAdapters.adaptSymbol;

import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.BaseBinanceWebSocketTransaction;
import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.ToString;
import org.knowm.xchange.dto.account.OpenPosition;

@Getter
@ToString
public class AccountUpdateBinanceWebSocketTransaction extends BaseBinanceWebSocketTransaction {

  private final Long transactionTime;
  private final String accountAlias;
  private final AccountUpdate accountUpdate;

  public AccountUpdateBinanceWebSocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("T") Long transactionTime,
      @JsonProperty("i") String accountAlias,
      @JsonProperty("a") AccountUpdate accountUpdate) {
    super(eventType, eventTime);
    this.transactionTime = transactionTime;
    this.accountAlias = accountAlias;
    this.accountUpdate = accountUpdate;
  }

  @Getter
  @ToString
  public static class AccountUpdate {

    private final AccountUpdateBinanceEventReason eventReasonType;
    private final List<Balance> balances;
    private final List<Position> positions;

    public AccountUpdate(
        @JsonProperty("m") AccountUpdateBinanceEventReason eventReasonType,
        @JsonProperty("B") List<Balance> balances,
        @JsonProperty("P") List<Position> positions) {
      this.eventReasonType = eventReasonType;
      this.balances = balances;
      this.positions = positions;
    }
  }

  @Getter
  @ToString
  public static class Balance {

    private final String asset;
    private final BigDecimal walletBalance;
    private final BigDecimal crossWalletBalance;
    private final BigDecimal balanceChange;

    public Balance(
        @JsonProperty("a") String asset,
        @JsonProperty("wb") BigDecimal walletBalance,
        @JsonProperty("cw") BigDecimal crossWalletBalance,
        @JsonProperty("bc") BigDecimal balanceChange) {
      this.asset = asset;
      this.walletBalance = walletBalance;
      this.crossWalletBalance = crossWalletBalance;
      this.balanceChange = balanceChange;
    }
  }

  @Getter
  @ToString
  public static class Position {

    private final String symbol;
    private final BigDecimal positionAmount;
    private final BigDecimal entryPrice;
    private final BigDecimal breakEvenPrice;
    private final BigDecimal accumulatedRealized;
    private final BigDecimal unrealizedPnl;
    private final String marginType;
    private final BigDecimal isolatedWallet;
    private final String positionSide;

    public Position(
        @JsonProperty("s") String symbol,
        @JsonProperty("pa") BigDecimal positionAmount,
        @JsonProperty("ep") BigDecimal entryPrice,
        @JsonProperty("bep") BigDecimal breakEvenPrice,
        @JsonProperty("cr") BigDecimal accumulatedRealized,
        @JsonProperty("up") BigDecimal unrealizedPnl,
        @JsonProperty("mt") String marginType,
        @JsonProperty("iw") BigDecimal isolatedWallet,
        @JsonProperty("ps") String positionSide) {
      this.symbol = symbol;
      this.positionAmount = positionAmount;
      this.entryPrice = entryPrice;
      this.breakEvenPrice = breakEvenPrice;
      this.accumulatedRealized = accumulatedRealized;
      this.unrealizedPnl = unrealizedPnl;
      this.marginType = marginType;
      this.isolatedWallet = isolatedWallet;
      this.positionSide = positionSide;
    }

    public OpenPosition toOpenPosition(boolean isFuture) {
      return OpenPosition.builder()
          .instrument(adaptSymbol(symbol, isFuture))
          .price(entryPrice)
          .size(positionAmount.abs())
          .type(
              positionAmount.compareTo(BigDecimal.ZERO) >= 0
                  ? OpenPosition.Type.LONG
                  : OpenPosition.Type.SHORT)
          .unRealisedPnl(unrealizedPnl)
          .build();
    }
  }
}
