package info.bitrich.xchangestream.binance.dto.market;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import info.bitrich.xchangestream.binance.dto.ProductBinanceWebSocketTransaction;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import lombok.Getter;
import org.knowm.xchange.binance.BinanceAdapters;
import org.knowm.xchange.dto.marketdata.FundingRate;
import org.knowm.xchange.dto.marketdata.FundingRate.FundingRateInterval;

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

  public FundingRate toFundingRate(int fundingRateInterval) {
    FundingRateInterval rateInterval = FundingRateInterval.H8;
    BigDecimal fundingRate1h = BigDecimal.ZERO;
    switch (fundingRateInterval) {
      case 1:
        {
          rateInterval = FundingRateInterval.H1;
          fundingRate1h = fundingRate;
          break;
        }
      case 2:
        {
          rateInterval = FundingRateInterval.H2;
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(2), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
      case 4:
        {
          rateInterval = FundingRateInterval.H4;
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(4), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
      case 6:
        {
          rateInterval = FundingRateInterval.H6;
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(6), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
      case 8:
        {
          fundingRate1h =
              fundingRate.divide(
                  BigDecimal.valueOf(8), fundingRate.scale(), RoundingMode.HALF_EVEN);
          break;
        }
    }
    return new FundingRate.Builder()
        .fundingRateInterval(rateInterval)
        .fundingRate(fundingRate)
        .fundingRate1h(fundingRate1h)
        .fundingRateDate(nextFundingTime)
        .instrument(BinanceAdapters.adaptSymbol(symbol, true))
        .build();
  }
}
