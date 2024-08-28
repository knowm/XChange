package org.knowm.xchange.bitget;

import java.util.HashMap;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.knowm.xchange.bitget.dto.BitgetSymbolDto;
import org.knowm.xchange.bitget.dto.BitgetSymbolDto.Status;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.InstrumentMetaData;
import org.knowm.xchange.instrument.Instrument;

@UtilityClass
public class BitgetAdapters {

  private final Map<String, CurrencyPair> SYMBOL_TO_CURRENCY_PAIR = new HashMap<>();


  public String toString(Instrument instrument) {
    return instrument == null ? null : instrument.getBase().toString() + instrument.getCounter().toString();
  }


  public void putSymbolMapping(String symbol, CurrencyPair currencyPair) {
    SYMBOL_TO_CURRENCY_PAIR.put(symbol, currencyPair);
  }


  public InstrumentMetaData toInstrumentMetaData(BitgetSymbolDto bitgetSymbolDto) {
    InstrumentMetaData.Builder builder = new InstrumentMetaData.Builder()
        .tradingFee(bitgetSymbolDto.getTakerFeeRate())
        .minimumAmount(bitgetSymbolDto.getMinTradeAmount())
        .maximumAmount(bitgetSymbolDto.getMaxTradeAmount())
        .volumeScale(bitgetSymbolDto.getQuantityPrecision())
        .priceScale(bitgetSymbolDto.getPricePrecision())
        .marketOrderEnabled(bitgetSymbolDto.getStatus() == Status.ONLINE);

    // set min quote amount for USDT
    if (bitgetSymbolDto.getCurrencyPair().getCounter().equals(Currency.USDT)) {
      builder.counterMinimumAmount(bitgetSymbolDto.getMinTradeUSDT());
    }

    return builder.build();
  }

}
