package org.knowm.xchange.bybit.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.spot.BybitSpotInstrumentInfo;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MarketDataMapper {

  public static CurrencyPair symbolToCurrencyPair(BybitInstrumentInfo instrumentInfo) {
    return new CurrencyPair(instrumentInfo.getBaseCoin(), instrumentInfo.getQuoteCoin());
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(
      BybitSpotInstrumentInfo instrumentInfo) {

    return new InstrumentMetaData.Builder()
        .minimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderQty())
        .counterMinimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderAmt())
        .counterMaximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderAmt())
        .priceScale(instrumentInfo.getPriceFilter().getTickSize().scale())
        .volumeScale(instrumentInfo.getLotSizeFilter().getBasePrecision().scale())
        .amountStepSize(instrumentInfo.getLotSizeFilter().getBasePrecision())
        .priceStepSize(instrumentInfo.getPriceFilter().getTickSize())
        .build();
  }
}
