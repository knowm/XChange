package org.knowm.xchange.bybit.mappers;

import java.io.IOException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.feerates.BybitFeeRate;
import org.knowm.xchange.bybit.dto.marketdata.instruments.BybitInstrumentInfo;
import org.knowm.xchange.bybit.dto.marketdata.instruments.linear.BybitLinearInverseInstrumentInfo;
import org.knowm.xchange.bybit.service.BybitAccountService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MarketDataMapper {

  public static CurrencyPair symbolToCurrencyPair(BybitInstrumentInfo instrumentInfo) {
    return new CurrencyPair(instrumentInfo.getBaseCoin(), instrumentInfo.getQuoteCoin());
  }

  public static InstrumentMetaData symbolToCurrencyPairMetaData(
      BybitLinearInverseInstrumentInfo instrumentInfo, BybitAccountService accountService)
      throws IOException {

    BybitFeeRate feeRate =
        accountService.getFeeRate(BybitCategory.LINEAR, instrumentInfo.getSymbol());

    return new InstrumentMetaData.Builder()
        .tradingFee(feeRate.getTakerFeeRate().max(feeRate.getMakerFeeRate()))
        .minimumAmount(instrumentInfo.getLotSizeFilter().getMinOrderQty())
        .maximumAmount(instrumentInfo.getLotSizeFilter().getMaxOrderQty())
        // e.g. 0.0010 -> 3
        .volumeScale(
            Math.max(
                instrumentInfo.getLotSizeFilter().getQtyStep().stripTrailingZeros().scale(), 0))
        .priceScale(instrumentInfo.getPriceScale())
        .counterMinimumAmount(instrumentInfo.getPriceFilter().getMinPrice())
        .counterMaximumAmount(instrumentInfo.getPriceFilter().getMaxPrice())
        .priceScale(instrumentInfo.getPriceScale())
        .amountStepSize(instrumentInfo.getLotSizeFilter().getQtyStep())
        .build();
  }
}
