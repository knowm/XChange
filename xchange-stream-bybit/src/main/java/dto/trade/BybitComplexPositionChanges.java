package dto.trade;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.instrument.Instrument;

@Getter
@Setter
@ToString
public class BybitComplexPositionChanges extends OpenPosition {
  private int positionIdx;
  private int tradeMode;
  private int riskId;
  private String riskLimitValue;
  private BigDecimal markPrice;
  private BigDecimal positionBalance;
  private int autoAddMargin;
  private BigDecimal positionMM;
  private BigDecimal positionIM;
  private BigDecimal bustPrice;
  private BigDecimal positionValue;
  private BigDecimal leverage;
  private BigDecimal takeProfit;
  private BigDecimal stopLoss;
  private BigDecimal trailingStop;
  private BigDecimal curRealisedPnl;
  private BigDecimal sessionAvgPrice; //USDC contract session avg price
  private String positionStatus;
  private int adlRankIndicator;
  private boolean isReduceOnly;
  private String mmrSysUpdatedTime;
  private String leverageSysUpdatedTime;
  private long createdTime;
  private long updatedTime;
  private long seq;

  public BybitComplexPositionChanges(Instrument instrument, Type type, BigDecimal size,
      BigDecimal liquidationPrice, BigDecimal unRealisedPnl,
      BigDecimal positionValue, BigDecimal entryPrice, BigDecimal leverage, BigDecimal takeProfit,
      BigDecimal stopLoss, BigDecimal curRealisedPnl, long createdTime, long updatedTime,
      long seq) {
    super(instrument, type, size, entryPrice, liquidationPrice, unRealisedPnl);
    this.positionValue = positionValue;
    this.leverage = leverage;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.curRealisedPnl = curRealisedPnl;
    this.createdTime = createdTime;
    this.updatedTime = updatedTime;
    this.seq = seq;
  }

  public BybitComplexPositionChanges(Instrument instrument, Type type, BigDecimal size,
      BigDecimal price, BigDecimal liquidationPrice,
      BigDecimal unRealisedPnl) {
    super(instrument, type, size, price, liquidationPrice, unRealisedPnl);
  }

  public BybitComplexPositionChanges(BybitComplexPositionChanges changes) {
    super(changes.getInstrument(), changes.getType(), changes.getSize(), changes.getPrice(),
        changes.getLiquidationPrice(), changes.getUnRealisedPnl());
    this.positionValue = changes.positionValue;
    this.leverage = changes.leverage;
    this.takeProfit = changes.takeProfit;
    this.stopLoss = changes.stopLoss;
    this.curRealisedPnl = changes.curRealisedPnl;
    this.createdTime = changes.createdTime;
    this.updatedTime = changes.updatedTime;
    this.seq = changes.seq;
  }
  public BybitComplexPositionChanges(Instrument instrument, Type type, BigDecimal size,
      BigDecimal price, BigDecimal liquidationPrice, BigDecimal unRealisedPnl, int positionIdx,
      int tradeMode, int riskId, String riskLimitValue, BigDecimal markPrice,
      BigDecimal positionBalance, int autoAddMargin, BigDecimal positionMM, BigDecimal positionIM,
      BigDecimal bustPrice, BigDecimal positionValue, BigDecimal leverage,
      BigDecimal takeProfit, BigDecimal stopLoss, BigDecimal trailingStop, BigDecimal curRealisedPnl,
      BigDecimal sessionAvgPrice, String positionStatus, int adlRankIndicator, boolean isReduceOnly,
      String mmrSysUpdatedTime, String leverageSysUpdatedTime, long createdTime, long updatedTime,
      long seq) {
    super(instrument, type, size, price, liquidationPrice, unRealisedPnl);
    this.positionIdx = positionIdx;
    this.tradeMode = tradeMode;
    this.riskId = riskId;
    this.riskLimitValue = riskLimitValue;
    this.markPrice = markPrice;
    this.positionBalance = positionBalance;
    this.autoAddMargin = autoAddMargin;
    this.positionMM = positionMM;
    this.positionIM = positionIM;
    this.bustPrice = bustPrice;
    this.positionValue = positionValue;
    this.leverage = leverage;
    this.takeProfit = takeProfit;
    this.stopLoss = stopLoss;
    this.trailingStop = trailingStop;
    this.curRealisedPnl = curRealisedPnl;
    this.sessionAvgPrice = sessionAvgPrice;
    this.positionStatus = positionStatus;
    this.adlRankIndicator = adlRankIndicator;
    this.isReduceOnly = isReduceOnly;
    this.mmrSysUpdatedTime = mmrSysUpdatedTime;
    this.leverageSysUpdatedTime = leverageSysUpdatedTime;
    this.createdTime = createdTime;
    this.updatedTime = updatedTime;
    this.seq = seq;
  }
}
