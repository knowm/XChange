package info.bitrich.xchangestream.binance.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.dto.marketdata.FundingRate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class FundingRateWebsocketTransaction extends ProductBinanceWebSocketTransaction {

  private final BigDecimal markPrice;
  private final BigDecimal indexPrice;
  private final BigDecimal estimatedSettlePrice;
  private final BigDecimal fundingRate;
  private final Date nextFundingTime;

  public FundingRateWebsocketTransaction(
      @JsonProperty("e") String eventType,
      @JsonProperty("E") String eventTime,
      @JsonProperty("s") String symbol,
      @JsonProperty("p") BigDecimal markPrice,
      @JsonProperty("i") BigDecimal indexPrice,
      @JsonProperty("P") BigDecimal estimatedSettlePrice,
      @JsonProperty("r") BigDecimal fundingRate,
      @JsonProperty("T") Date nextFundingTime) {
    super(eventType, eventTime, symbol);
    this.markPrice = markPrice;
    this.indexPrice = indexPrice;
    this.estimatedSettlePrice = estimatedSettlePrice;
    this.fundingRate = fundingRate;
    this.nextFundingTime = nextFundingTime;
  }

  public FundingRate toFundingRate() {
    return new FundingRate.Builder()
        .fundingRate8h(fundingRate)
        .fundingRate1h(
            fundingRate.divide(BigDecimal.valueOf(8), fundingRate.scale(), RoundingMode.HALF_EVEN))
        .fundingRateDate(nextFundingTime)
        .instrument(BinanceAdapters.adaptSymbol(symbol, true))
        .build();
  }
}
