package info.bitrich.xchangestream.bitmex;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.knowm.xchange.bitmex.BitmexContract;
import org.knowm.xchange.bitmex.BitmexPrompt;
import org.knowm.xchange.bitmex.dto.account.BitmexTicker;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;


public class BitmexUtils {
    protected static final HashBiMap<String, Currency> assetsMap = HashBiMap.create();
    protected static Map<String, CurrencyPair> assetPairMap = new HashMap();
    protected static BiMap<String, BitmexContract> bitmexContracts = HashBiMap.create();
    protected static BiMap<Currency, String> bitmexCurrencies = HashBiMap.create();

    private BitmexUtils() {

    }

    public static void initBitmexContracts() {
        Request request = Request.Get("https://www.bitmex.com/api/v1/instrument/activeIntervals");
        try {
            Content content = request.execute().returnContent();
            ObjectMapper mapper = new ObjectMapper();
            JsonNode node = mapper.readValue(content.toString(), JsonNode.class);
            JsonNode intervals = node.get("intervals");
            JsonNode symbols = node.get("symbols");
            for (int i = 0; i < symbols.size(); i++) {
                String symbol = symbols.get(i).textValue();
                String interval = intervals.get(i).textValue();
                String[] parts = interval.split(":");
                bitmexContracts.put(symbol, new BitmexContract(new CurrencyPair(new Currency(parts[0]), Currency.USD), BitmexPrompt.valueOf(parts[1].toUpperCase())));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setBitmexAssetPairs(List<BitmexTicker> tickers) {
        Iterator var1 = tickers.iterator();

        while(var1.hasNext()) {
            BitmexTicker ticker = (BitmexTicker)var1.next();
            String quote = ticker.getQuoteCurrency();
            String base = ticker.getRootSymbol();
            Currency baseCurrencyCode = Currency.getInstance(base);
            Currency quoteCurrencyCode = Currency.getInstance(quote);
            CurrencyPair pair = new CurrencyPair(base, quote);
            if (!assetPairMap.containsKey(ticker.getSymbol()) && !assetPairMap.containsValue(pair)) {
                assetPairMap.put(ticker.getSymbol(), pair);
            }

            if (!assetsMap.containsKey(quote) && !assetsMap.containsValue(quoteCurrencyCode)) {
                assetsMap.put(quote, quoteCurrencyCode);
            }

            if (!assetsMap.containsKey(base) && !assetsMap.containsValue(baseCurrencyCode)) {
                assetsMap.put(base, baseCurrencyCode);
            }
        }

    }

    public static String createBitmexContract(BitmexContract contract) {
        return (String)bitmexContracts.inverse().get(contract);
    }

    public static CurrencyPair translateBitmexCurrencyPair(String currencyPairIn) {
        CurrencyPair pair = (CurrencyPair)assetPairMap.get(currencyPairIn);
        if (pair == null) {
            Currency base;
            Currency counter;
            if (currencyPairIn.length() == 6) {
                base = Currency.getInstance(currencyPairIn.substring(0, 3));
                if (base.getCommonlyUsedCurrency() != null) {
                    base = base.getCommonlyUsedCurrency();
                }

                counter = Currency.getInstance(currencyPairIn.substring(3, 6));
                if (counter.getCommonlyUsedCurrency() != null) {
                    counter = counter.getCommonlyUsedCurrency();
                }

                pair = new CurrencyPair(base, counter);
            } else if (currencyPairIn.length() == 7) {
                base = Currency.getInstance(currencyPairIn.substring(0, 4));
                if (base.getCommonlyUsedCurrency() != null) {
                    base = base.getCommonlyUsedCurrency();
                }

                counter = Currency.getInstance(currencyPairIn.substring(4, 7));
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

        String bitmexCode = (String)assetsMap.inverse().get(currency);
        if (bitmexCode == null) {
            throw new ExchangeException("Bitmex does not support the currency code " + currency);
        } else {
            return bitmexCode;
        }
    }

    public static String translateCurrency(Currency currencyIn) {
        String currencyOut = (String)bitmexCurrencies.get(currencyIn);
        if (currencyOut == null) {
            throw new ExchangeException("Bitmex does not support the currency code " + currencyIn);
        } else {
            return currencyOut;
        }
    }

    public static Currency translateBitmexCurrency(String currencyIn) {
        Currency currencyOut = (Currency)bitmexCurrencies.inverse().get(currencyIn);
        if (currencyOut == null) {
            throw new ExchangeException("Bitmex does not support the currency code " + currencyIn);
        } else {
            return currencyOut;
        }
    }

    public static String translateBitmexContract(BitmexContract contractIn) {
        String contractOut = bitmexContracts.inverse().get(contractIn);
        if (contractOut == null) {
            if (contractIn.prompt == BitmexPrompt.QUARTERLY) {
                contractOut = bitmexContracts.inverse().get(new BitmexContract(contractIn.pair, BitmexPrompt.MONTHLY));
                if (contractOut == null) {
                    contractOut = bitmexContracts.inverse().get(new BitmexContract(contractIn.pair, BitmexPrompt.WEEKLY));
                }
            }
            if (contractOut == null) {
                throw new ExchangeException("Bitmex does not support the contact " + contractIn);
            }

        }
        return contractOut;
    }

    public static Currency translateBitmexCurrencyCode(String currencyIn) {
        Currency currencyOut = (Currency)assetsMap.get(currencyIn);
        if (currencyOut == null) {
            throw new ExchangeException("Bitmex does not support the currency code " + currencyIn);
        } else {
            return currencyOut.getCommonlyUsedCurrency();
        }
    }

    public class CustomBitmexContractSerializer extends JsonSerializer<BitmexContract> {
        public CustomBitmexContractSerializer() {
        }

        public void serialize(BitmexContract contract, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
            jsonGenerator.writeString(contract.toString());
        }
    }
}
