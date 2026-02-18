package info.bitrich.xchangestream.bitfinex.dto;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.bitfinex.config.converter.StringToCurrencyPairConverter;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.OpenPosition.Type;

@Data
@Builder
@Jacksonized
@JsonFormat(shape = JsonFormat.Shape.ARRAY)
public class BitfinexWebSocketPosition {

  /** Pair (tBTCUSD, …). */
  @JsonDeserialize(converter = StringToCurrencyPairConverter.class)
  private CurrencyPair currencyPair;

  /** Status (ACTIVE, CLOSED). */
  private PositionStatus status;

  /**
   * Size of the position. Positive values means a long position, negative values means a short
   * position.
   */
  private BigDecimal amount;

  /** The price at which you entered your position. */
  private BigDecimal basePrice;

  /** The amount of funding being used for this position. */
  private BigDecimal marginFunding;

  /** 0 for daily, 1 for term. */
  private FundingType marginFundingType;

  /** Profit & Loss */
  private BigDecimal pl;

  /** Profit & Loss Percentage */
  private BigDecimal plPercent;

  /** Liquidation price */
  private BigDecimal priceLiq;

  /** Beta value */
  private BigDecimal leverage;

  private Object placeHolder0;

  /** Position ID */
  private String positionId;

  /** Millisecond timestamp of creation */
  private Instant createdAt;

  /** Millisecond timestamp of update */
  private Instant updatedAt;

  private Object placeHolder1;

  /** Identifies the type of position, 0 = Margin position, 1 = Derivatives position */
  private PositionType positionType;

  private Object placeHolder2;

  /** The amount of collateral applied to the open position */
  private BigDecimal collateral;

  /** The minimum amount of collateral required for the position */
  private BigDecimal collateralMin;

  /** Additional meta information about the position */
  private Object meta;

  @JsonIgnore
  public Type getType() {
    if (amount.signum() >= 0) {
      return Type.LONG;
    } else {
      return Type.SHORT;
    }
  }

  public enum FundingType {
    @JsonProperty("0")
    DAILY,

    @JsonProperty("1")
    TERM
  }

  public enum PositionType {
    @JsonProperty("0")
    MARGIN,

    @JsonProperty("1")
    DERIVATIVES
  }

  public enum PositionStatus {
    @JsonProperty("ACTIVE")
    ACTIVE,

    @JsonProperty("CLOSED")
    CLOSED,

    @JsonEnumDefaultValue
    UNKNOWN
  }
}
