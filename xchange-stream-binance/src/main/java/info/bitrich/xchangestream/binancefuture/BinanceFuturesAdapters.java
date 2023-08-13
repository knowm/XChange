package info.bitrich.xchangestream.binancefuture;

import java.util.Arrays;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;

public class BinanceFuturesAdapters {

  public static final List<String> QUOTE_CURRENCIES = Arrays.asList("USDT", "USDC", "BTC", "DAI");

  public static FuturesContract guessContract(String symbol) {
    for (String quoteCurrency : QUOTE_CURRENCIES) {
      if (symbol.endsWith(quoteCurrency)) {
        int splitIndex = symbol.lastIndexOf(quoteCurrency);
        return new FuturesContract(new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex)), "PERP");
      }
    }
    int splitIndex = symbol.length() - 3;
    return new FuturesContract(new CurrencyPair(symbol.substring(0, splitIndex), symbol.substring(splitIndex)), "PERP");
  }
}
