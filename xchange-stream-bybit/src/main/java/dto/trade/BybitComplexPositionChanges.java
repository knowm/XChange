package dto.trade;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.dto.account.OpenPosition;

@Data
@SuperBuilder
@Jacksonized
@AllArgsConstructor
@NoArgsConstructor
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
  private BigDecimal cumRealisedPnl;
  private BigDecimal sessionAvgPrice; // USDC contract session avg price
  private String positionStatus;
  private int adlRankIndicator;
  private boolean isReduceOnly;
  private String mmrSysUpdatedTime;
  private String leverageSysUpdatedTime;
  private Date createdTime;
  private Date updatedTime;
  private long seq;

  public BigDecimal getPositionValue() {
    if (getSize().compareTo(BigDecimal.ZERO) != 0 && getPrice().compareTo(BigDecimal.ZERO) != 0) {
      return getSize().multiply(getPrice());
    }
    return BigDecimal.ZERO;
  }
}
