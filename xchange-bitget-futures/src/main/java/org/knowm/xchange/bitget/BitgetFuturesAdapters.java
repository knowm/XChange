package org.knowm.xchange.bitget;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitget.dto.marketdata.BitgetContractDto;
import org.knowm.xchange.bitget.dto.marketdata.BitgetFuturesTickerDto;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.dto.marketdata.Ticker;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class BitgetFuturesAdapters {

  public String toString(Instrument instrument) {
    return instrument == null
        ? null
        : instrument.getBase().toString() + instrument.getCounter().toString();
  }

  public String toString(Currency currency) {
    return Optional.ofNullable(currency).map(Currency::getCurrencyCode).orElse(null);
  }

  public InstrumentMetaData toInstrumentMetaData(BitgetContractDto bitgetContractDto) {
    InstrumentMetaData.InstrumentMetaDataBuilder builder =
        InstrumentMetaData.builder()
            .tradingFee(bitgetContractDto.getTakerFeeRate())
            .minimumAmount(bitgetContractDto.getMinTradeAssetAmount())
            .priceScale(bitgetContractDto.getPricePrecision())
            .volumeScale(bitgetContractDto.getAssetAmountPrecision())
            .amountStepSize(bitgetContractDto.getAssetAmountStepSize())
            .marketOrderEnabled(
                bitgetContractDto.getSymbolStatus() == BitgetContractDto.SymbolStatus.NORMAL);

    // set price step
    if (bitgetContractDto.getPriceEndStep() != null
        && bitgetContractDto.getPriceEndStep() > 0
        && bitgetContractDto.getPricePrecision() != null) {
      builder.priceStepSize(
          BigDecimal.ONE
              .scaleByPowerOfTen(-bitgetContractDto.getPricePrecision())
              .multiply(BigDecimal.valueOf(bitgetContractDto.getPriceEndStep())));
    }

    // set min quote amount for USDT
    if (bitgetContractDto
        .getFuturesContract()
        .getCurrencyPair()
        .getCounter()
        .equals(Currency.USDT)) {
      builder.counterMinimumAmount(bitgetContractDto.getMinTradeUSDT());
    }

    return builder.build();
  }

  public Ticker toTicker(BitgetFuturesTickerDto bitgetFuturesTickerDto) {
    if (bitgetFuturesTickerDto.getInstrument() == null) {
      return null;
    }
    return new Ticker.Builder()
        .instrument(bitgetFuturesTickerDto.getInstrument())
        .open(bitgetFuturesTickerDto.getOpen24h())
        .last(bitgetFuturesTickerDto.getLastPrice())
        .bid(bitgetFuturesTickerDto.getBestBidPrice())
        .ask(bitgetFuturesTickerDto.getBestAskPrice())
        .high(bitgetFuturesTickerDto.getHigh24h())
        .low(bitgetFuturesTickerDto.getLow24h())
        .volume(bitgetFuturesTickerDto.getAssetVolume24h())
        .quoteVolume(bitgetFuturesTickerDto.getQuoteVolume24h())
        .timestamp(toDate(bitgetFuturesTickerDto.getTimestamp()))
        .bidSize(bitgetFuturesTickerDto.getBestBidSize())
        .askSize(bitgetFuturesTickerDto.getBestAskSize())
        .percentageChange(bitgetFuturesTickerDto.getChange24h())
        .build();
  }

  public Date toDate(Instant instant) {
    return Optional.ofNullable(instant).map(Date::from).orElse(null);
  }
}
