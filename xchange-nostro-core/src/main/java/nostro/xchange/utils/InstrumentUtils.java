package nostro.xchange.utils;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.Derivative;
import org.knowm.xchange.instrument.Instrument;

public class InstrumentUtils {
    public static CurrencyPair getCurrencyPair(Instrument instrument) {
        if (instrument instanceof CurrencyPair)
            return (CurrencyPair) instrument;
        if (instrument instanceof Derivative)
            return ((Derivative) instrument).getCurrencyPair();
        return null;
    }
}
