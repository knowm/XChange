package info.bitrich.xchangestream.binancefuture.dto;

import static info.bitrich.xchangestream.binancefuture.BinanceFuturesAdapters.guessContract;

import java.math.BigDecimal;
import org.knowm.xchange.derivative.FuturesContract;

public class BinanceFuturesPosition {

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
