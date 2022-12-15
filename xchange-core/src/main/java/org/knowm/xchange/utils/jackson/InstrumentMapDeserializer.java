package org.knowm.xchange.utils.jackson;

import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;

public class InstrumentMapDeserializer extends KeyDeserializer {

    @Override
    public Object deserializeKey(String s, DeserializationContext deserializationContext) {
        long count = s.chars().filter(ch -> ch == '/').count();
        // CurrencyPair (Base/Counter) i.e. BTC/USD
        if (count == 1) return new CurrencyPair(s);
        // Futures/Swaps (Base/Counter/Prompt) i.e. BTC/USD/200925
        if (count == 2) return new FuturesContract(s);
        // Options (Base/Counter/Prompt/StrikePrice/Put?Call) i.e. BTC/USD/200925/8956.67/P
        if (count == 4) return new OptionsContract(s);
        else return null;
    }
}
