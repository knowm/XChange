package org.knowm.xchange.hitbtc.v2.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.CurrencyMetaData;
import org.knowm.xchange.dto.meta.CurrencyPairMetaData;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;

public class HitbtcAdapters {


  public static CurrencyPair adaptSymbol(String symbolString) {

    return CurrencyPairDeserializer.getCurrencyPairFromString(symbolString);
  }

  public static CurrencyPair adaptSymbol(HitbtcSymbol hitbtcSymbol) {

    return new CurrencyPair(hitbtcSymbol.getBaseCurrency(), hitbtcSymbol.getFeeCurrency());
  }

  public static ExchangeMetaData adaptToExchangeMetaData(List<HitbtcSymbol> symbols, Map<Currency, CurrencyMetaData> currencies) {

    Map<CurrencyPair, CurrencyPairMetaData> currencyPairs = new HashMap<>();
    if (symbols != null) {
      for (HitbtcSymbol symbol : symbols) {
        CurrencyPair pair = adaptSymbol(symbol);

        // Todo fix scale? Verify this is correct
//        CurrencyPairMetaData meta = new CurrencyPairMetaData(symbol.getTakeLiquidityRate(), symbol.getLot(), null, symbol.getStep().scale());
        CurrencyPairMetaData meta = new CurrencyPairMetaData(symbol.getTakeLiquidityRate(), symbol.getTickSize(), null, symbol
            .getQuantityIncrement().scale());


        currencyPairs.put(pair, meta);
      }
    }

    return new ExchangeMetaData(currencyPairs, currencies, null, null, null);
  }

}
