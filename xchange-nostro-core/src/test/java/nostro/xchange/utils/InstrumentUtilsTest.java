package nostro.xchange.utils;

import org.junit.Test;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.derivative.OptionsContract;

import static org.assertj.core.api.Assertions.assertThat;

public class InstrumentUtilsTest {

    @Test
    public void testGetCurrencyPairFromFutures() {
        CurrencyPair currencyPair = CurrencyPair.ADA_BNB;
        FuturesContract instrument = new FuturesContract(currencyPair, null);
        assertThat(InstrumentUtils.getCurrencyPair(instrument)).isEqualTo(currencyPair);
    }

    @Test
    public void testGetCurrencyPairFromOptions() {
        CurrencyPair currencyPair = CurrencyPair.ETH_USD;
        OptionsContract instrument = new OptionsContract("ETH/USD/210719/34000/C");
        assertThat(InstrumentUtils.getCurrencyPair(instrument)).isEqualTo(currencyPair);
    }

    @Test
    public void testGetCurrencyPairFromPair() {
        CurrencyPair currencyPair = CurrencyPair.ADA_BNB;
        assertThat(InstrumentUtils.getCurrencyPair(currencyPair)).isEqualTo(currencyPair);
    }
}