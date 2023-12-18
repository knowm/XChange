package org.knowm.xchange.bybit.mappers;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bybit.dto.marketdata.BybitSymbol;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MarketDataMapper {

  public static CurrencyPair symbolToCurrencyPair(BybitSymbol symbol) {
    return new CurrencyPair(symbol.getBaseCurrency(), symbol.getQuoteCurrency());
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(BybitSymbol bybitSymbol) {
    return new InstrumentMetaData.Builder()
        // workaround - get maximum of maker and taker fees
        .tradingFee(bybitSymbol.getTakerFee().max(bybitSymbol.getMakerFee()))
        .minimumAmount(bybitSymbol.getLotSizeFilter().getMinTradingQty())
        .maximumAmount(bybitSymbol.getLotSizeFilter().getMaxTradingQty())
        // e.g. 0.0010 -> 3
        .volumeScale(
            Math.max(bybitSymbol.getLotSizeFilter().getQtyStep().stripTrailingZeros().scale(), 0))
        .priceScale(bybitSymbol.getPriceScale())
        .counterMinimumAmount(bybitSymbol.getPriceFilter().getMinPrice())
        .counterMaximumAmount(bybitSymbol.getPriceFilter().getMaxPrice())
        .priceScale(bybitSymbol.getPriceScale())
        .amountStepSize(bybitSymbol.getLotSizeFilter().getQtyStep())
        .build();
  }
}
