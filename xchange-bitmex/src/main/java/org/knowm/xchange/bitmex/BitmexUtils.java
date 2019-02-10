package org.knowm.xchange.bitmex;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author timmolter */
public class BitmexUtils {

  protected static final HashBiMap<String, Currency> assetsMap = HashBiMap.create();
  private static Map<String, CurrencyPair> assetPairMap = new HashMap<String, CurrencyPair>();
  protected static BiMap<String, BitmexContract> bitmexContracts = HashBiMap.create();
  protected static BiMap<Currency, String> bitmexCurrencies = HashBiMap.create();

  /** Private Constructor */
  private BitmexUtils() {}

  public static void setBitmexAssetPairs(List<BitmexTicker> tickers) {

    for (BitmexTicker ticker : tickers) {
      String quote = ticker.getQuoteCurrency();
      String base = ticker.getRootSymbol();
      Currency baseCurrencyCode = Currency.getInstance(base);
      Currency quoteCurrencyCode = Currency.getInstance(quote);

      CurrencyPair pair = new CurrencyPair(base, quote);
      assetPairMap.put(ticker.getSymbol(), pair);
      assetsMap.put(quote, quoteCurrencyCode);
      assetsMap.put(base, baseCurrencyCode);
    }
  }

  public static String createBitmexContract(BitmexContract contract) {

    return bitmexContracts.inverse().get(contract);
  }

  public static CurrencyPair translateBitmexCurrencyPair(String currencyPairIn) {

    CurrencyPair pair = assetPairMap.get(currencyPairIn);
    if (pair == null) {
      // bitmex can give short pairs back from open orders ?
      if (currencyPairIn.length() == 6) {
        Currency base = Currency.getInstance(currencyPairIn.substring(0, 3));
        if (base.getCommonlyUsedCurrency() != null) {
          base = base.getCommonlyUsedCurrency();
        }
        Currency counter = Currency.getInstance(currencyPairIn.substring(3, 6));
        if (counter.getCommonlyUsedCurrency() != null) {
          counter = counter.getCommonlyUsedCurrency();
        }
        pair = new CurrencyPair(base, counter);
      } else if (currencyPairIn.length() == 7) {
        Currency base = Currency.getInstance(currencyPairIn.substring(0, 4));
        if (base.getCommonlyUsedCurrency() != null) {
          base = base.getCommonlyUsedCurrency();
        }
        Currency counter = Currency.getInstance(currencyPairIn.substring(4, 7));
        if (counter.getCommonlyUsedCurrency() != null) {
          counter = counter.getCommonlyUsedCurrency();
        }
        pair = new CurrencyPair(base, counter);
      }
    }
    return pair;
  }

  public static String getBitmexCurrencyCode(Currency currency) {

    if (currency.getIso4217Currency() != null) {
      currency = currency.getIso4217Currency();
    }
    String bitmexCode = assetsMap.inverse().get(currency);
    if (bitmexCode == null) {
      throw new ExchangeException("Bitmex does not support the currency code " + currency);
    }
    return bitmexCode;
  }

  public static String translateCurrency(Currency currencyIn) {

    String currencyOut = bitmexCurrencies.get(currencyIn);

    if (currencyOut == null) {
      throw new ExchangeException("Bitmex does not support the currency code " + currencyIn);
    }
    return currencyOut;
  }

  public static Currency translateBitmexCurrency(String currencyIn) {

    Currency currencyOut = bitmexCurrencies.inverse().get(currencyIn);

    if (currencyOut == null) {
      throw new ExchangeException("Bitmex does not support the currency code " + currencyIn);
    }
    return currencyOut;
  }

  public static String translateBitmexContract(BitmexContract contractIn) {

    String contractOut = bitmexContracts.inverse().get(contractIn);

    if (contractOut == null) {
      throw new ExchangeException("Bitmex does not support the contact " + contractIn);
    }
    return contractOut;
  }

  public static Currency translateBitmexCurrencyCode(String currencyIn) {

    Currency currencyOut = assetsMap.get(currencyIn);
    if (currencyOut == null) {
      throw new ExchangeException("Bitmex does not support the currency code " + currencyIn);
    }
    return currencyOut.getCommonlyUsedCurrency();
  }

  public class CustomBitmexContractSerializer extends JsonSerializer<BitmexContract> {

    @Override
    public void serialize(
        BitmexContract contract, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {

      jsonGenerator.writeString(contract.toString());
    }
  }
}
