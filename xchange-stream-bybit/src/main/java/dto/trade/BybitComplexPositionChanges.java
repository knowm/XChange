package dto.trade;

import java.math.BigDecimal;
import lombok.Getter;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.dto.account.OpenPosition;
import org.knowm.xchange.instrument.Instrument;

@Getter
public class BybitComplexPositionChanges extends OpenPosition {
  private BigDecimal positionValue;
  private BigDecimal leverage;
  private BigDecimal takeProfit;
  private  BigDecimal stopLoss;
  private BigDecimal curRealisedPnl;
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

}
