package info.bitrich.xchangestream.binancefuture.dto;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;

public class BinanceFuturesPosition {

  public static final List<String> QUOTE_CURRENCIES = Arrays.asList("USDT", "USDC", "BTC", "DAI");

  private final FuturesContract contract;
  private final BigDecimal positionAmount;
  private final BigDecimal entryPrice;
  private final BigDecimal accumulatedRealized;
  private final BigDecimal unrealizedPnl;
  private final String marginType;
  private final BigDecimal isolatedWallet;
  private final String positionSide;

  public BinanceFuturesPosition(BinanceFuturesWebsocketPositionsTransaction position) {
    this.contract = guessContract(position.getSymbol());
    this.positionAmount = position.getPositionAmount();
    this.entryPrice = position.getEntryPrice();
    this.accumulatedRealized = position.getAccumulatedRealized();
    this.unrealizedPnl = position.getUnrealizedPnl();
    this.marginType = position.getMarginType();
    this.isolatedWallet = position.getIsolatedWallet();
    this.positionSide = position.getPositionSide();
  }

  public static FuturesContract guessContract(String symbol) {
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new FuturesContract(new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex)), "PERPETUAL");
      }
    }
    int splitIndex = symbol.length() - 3;
    return new FuturesContract(new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex)), "PERPETUAL");
  }

  public BigDecimal getIsolatedWallet() {
    return isolatedWallet;
  }

  public FuturesContract getFuturesContract() {
    return contract;
  }

  public BigDecimal getPositionAmount() {
    return positionAmount;
  }

  public BigDecimal getEntryPrice() {
    return entryPrice;
  }

  public BigDecimal getAccumulatedRealized() {
    return accumulatedRealized;
  }

  public BigDecimal getUnrealizedPnl() {
    return unrealizedPnl;
  }

  public String getMarginType() {
    return marginType;
  }

  public String getPositionSide() {
    return positionSide;
  }

  @Override
  public String toString() {
    return "BinanceFuturesPositions{" +
        "contract=" + contract +
        ", positionAmount=" + positionAmount +
        ", entryPrice=" + entryPrice +
        ", accumulatedRealized=" + accumulatedRealized +
        ", unrealizedPnl=" + unrealizedPnl +
        ", marginType='" + marginType + '\'' +
        ", isolatedWallet=" + isolatedWallet +
        ", positionSide='" + positionSide + '\'' +
        '}';
  }
}
