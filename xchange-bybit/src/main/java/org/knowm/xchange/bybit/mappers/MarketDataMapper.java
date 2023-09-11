package org.knowm.xchange.bybit.mappers;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MarketDataMapper {

  public static CurrencyPair symbolToCurrencyPair(BybitInstrumentInfo instrumentInfo) {
    return new CurrencyPair(instrumentInfo.getBaseCoin(), instrumentInfo.getQuoteCoin());
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(
      BybitLinearInverseInstrumentInfo spotInstrumentInfo) {
    return new InstrumentMetaData.Builder()
        .tradingFee(BigDecimal.ZERO) // todo: it is a private api call
        .minimumAmount(spotInstrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(spotInstrumentInfo.getLotSizeFilter().getMaxOrderQty())
        // e.g. 0.0010 -> 3
        .volumeScale(
            Math.max(
                spotInstrumentInfo.getLotSizeFilter().getQtyStep().stripTrailingZeros().scale(), 0))
        .priceScale(spotInstrumentInfo.getPriceScale())
        .counterMinimumAmount(spotInstrumentInfo.getPriceFilter().getMinPrice())
        .counterMaximumAmount(spotInstrumentInfo.getPriceFilter().getMaxPrice())
        .priceScale(spotInstrumentInfo.getPriceScale())
        .amountStepSize(spotInstrumentInfo.getLotSizeFilter().getQtyStep())
        .build();
  }
}
