package info.bitrich.xchangestream.deribit.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import info.bitrich.xchangestream.deribit.dto.response.DeribitUserChangeNotification.UserChangeData;
import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import lombok.extern.jackson.Jacksonized;
import org.knowm.xchange.deribit.v2.config.converter.StringToPositionTypeConverter;
import org.knowm.xchange.deribit.v2.dto.Kind;
import org.knowm.xchange.dto.account.OpenPosition;

@Data
@SuperBuilder(toBuilder = true)
@Jacksonized
public class DeribitUserChangeNotification extends DeribitWsNotification<UserChangeData> {

  @Data
  @Builder
  @Jacksonized
  public static class UserChangeData {
    @JsonProperty("positions")
    private List<DeribitPosition> positions;
  }

  @Data
  @Builder
  @Jacksonized
  public static class DeribitPosition {
    /** Average price of trades that built this position */
    @JsonProperty("average_price")
    private BigDecimal averagePrice;

    /** Only for options, average price in USD */
    @JsonProperty("average_price_usd")
    private BigDecimal averagePriceUSD;

    /** Delta parameter */
    private BigDecimal delta;

    /** direction, buy or sell */
    @JsonProperty("direction")
    @JsonDeserialize(converter = StringToPositionTypeConverter.class)
    private OpenPosition.Type positionType;

    /** Floating profit or loss */
    @JsonProperty("floating_profit_loss")
    private BigDecimal floatingProfitLoss;

    /** Only for options, floating profit or loss in USD */
    @JsonProperty("floating_profit_loss_usd")
    private BigDecimal floatingProfitLossUSD;

    @JsonProperty("gamma")
    private BigDecimal gamma;

    /** Current index price */
    @JsonProperty("index_price")
    private BigDecimal indexPrice;

    /** Initial margin */
    @JsonProperty("initial_margin")
    private BigDecimal initialMargin;

    /** Unique instrument identifier */
    @JsonProperty("instrument_name")
    private String instrumentName;

    @JsonProperty("interest_value")
    private BigDecimal interestValue;

    /** Instrument kind, "future" or "option" */
    @JsonProperty("kind")
    private Kind kind;

    @JsonProperty("leverage")
    private Integer leverage;

    /** Maintenance margin */
    @JsonProperty("maintenance_margin")
    private BigDecimal maintenanceMargin;

    @JsonProperty("realized_funding")
    private BigDecimal realizedFunding;

    /** Realized profit or loss */
    @JsonProperty("realized_profit_loss")
    private BigDecimal realizedProfitLoss;

    /** Last settlement price for position's instrument 0 if instrument wasn't settled yet */
    @JsonProperty("settlement_price")
    private BigDecimal settlementPrice;

    /**
     * Position size for futures size in quote currency (e.g. USD), for options size is in base
     * currency (e.g. BTC)
     */
    @JsonProperty("size")
    private BigDecimal size;

    /** Only for futures, position size in base currency */
    @JsonProperty("size_currency")
    private BigDecimal sizeCurrency;

    @JsonProperty("theta")
    private BigDecimal theta;

    /** Profit or loss from position */
    @JsonProperty("total_profit_loss")
    private BigDecimal totalProfitLoss;

    @JsonProperty("vega")
    private BigDecimal vega;
  }
}
