package com.xeiam.xchange.cryptotrade.dto.marketdata;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradeOrderType;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradePair;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradePairType;
import com.xeiam.xchange.cryptotrade.dto.CryptoTradePairs;
import com.xeiam.xchange.currency.CurrencyPair;

public class CryptoTradeMarketDataJsonTest {

  @Test
  public void testDeserializeTicker() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-ticker-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeTicker ticker = mapper.readValue(is, CryptoTradeTicker.class);

    assertThat(ticker.getLast()).isEqualTo("128");
    assertThat(ticker.getLow()).isEqualTo("127.9999");
    assertThat(ticker.getHigh()).isEqualTo("129.1");
    assertThat(ticker.getVolumeTradeCurrency()).isEqualTo("5.4");
    assertThat(ticker.getVolumePriceCurrency()).isEqualTo("693.8199");
    assertThat(ticker.getMinAsk()).isEqualTo("129.1");
    assertThat(ticker.getMaxBid()).isEqualTo("128");

    assertThat(ticker.getStatus()).isEqualTo("success");
    assertThat(ticker.getError()).isNullOrEmpty();
  }

  @Test
  public void testDeserializeTickers() throws IOException {

    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-tickers-data.json");

    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeTickers tickers = mapper.readValue(is,  CryptoTradeTickers.class);

    Map<CurrencyPair, CryptoTradeTicker> tickerMap = tickers.getTickers();
    assertThat(tickerMap).hasSize(3);
    assertThat(tickerMap).containsKey(CurrencyPair.BTC_EUR);
    assertThat(tickerMap).containsKey(CurrencyPair.BTC_USD);
    assertThat(tickerMap).containsKey(CurrencyPair.LTC_BTC);

    CryptoTradeTicker ticker = tickerMap.get(CurrencyPair.BTC_EUR);
    assertThat(ticker.getLast()).isEqualTo("350.00000001");
    assertThat(ticker.getLow()).isEqualTo("0");
    assertThat(ticker.getHigh()).isEqualTo("0");
    assertThat(ticker.getVolumeTradeCurrency()).isEqualTo("0");
    assertThat(ticker.getVolumePriceCurrency()).isEqualTo("0");
    assertThat(ticker.getMinAsk()).isEqualTo("419.99999998");
    assertThat(ticker.getMaxBid()).isEqualTo("351.00000002");

    ticker = tickerMap.get(CurrencyPair.BTC_USD);
    assertThat(ticker.getLast()).isEqualTo("523.01007854");
    assertThat(ticker.getLow()).isEqualTo("481.00000001");
    assertThat(ticker.getHigh()).isEqualTo("523.01007854");
    assertThat(ticker.getVolumeTradeCurrency()).isEqualTo("0.01583568");
    assertThat(ticker.getVolumePriceCurrency()).isEqualTo("8.19821109");
    assertThat(ticker.getMinAsk()).isEqualTo("523.01007854");
    assertThat(ticker.getMaxBid()).isEqualTo("481.00279738");

    ticker = tickerMap.get(CurrencyPair.LTC_BTC);
    assertThat(ticker.getLast()).isEqualTo("0.01050002");
    assertThat(ticker.getLow()).isEqualTo("0.01020003");
    assertThat(ticker.getHigh()).isEqualTo("0.0107");
    assertThat(ticker.getVolumeTradeCurrency()).isEqualTo("8.529654");
    assertThat(ticker.getVolumePriceCurrency()).isEqualTo("0.08856878");
    assertThat(ticker.getMinAsk()).isEqualTo("0.01079995");
    assertThat(ticker.getMaxBid()).isEqualTo("0.01050004");

    assertThat(tickers.getStatus()).isEqualTo("success");
    assertThat(tickers.getError()).isNullOrEmpty();
  }

  @Test
  public void testDeserializeDepth() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-depth-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradeDepth depth = mapper.readValue(is, CryptoTradeDepth.class);

    List<CryptoTradePublicOrder> asks = depth.getAsks();
    assertThat(asks).hasSize(3);

    CryptoTradePublicOrder ask = asks.get(0);
    assertThat(ask.getPrice()).isEqualTo("102");
    assertThat(ask.getAmount()).isEqualTo("0.81718312");

    assertThat(depth.getStatus()).isNullOrEmpty();
    assertThat(depth.getError()).isNullOrEmpty();
  }

  @Test
  public void testDeserializePublicTrades() throws IOException {

    // Read in the JSON from the example resources
    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-trades-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePublicTrades trades = mapper.readValue(is, CryptoTradePublicTrades.class);

    List<CryptoTradePublicTrade> tradeList = trades.getPublicTrades();
    assertThat(tradeList).hasSize(2);

    CryptoTradePublicTrade trade = tradeList.get(0);
    assertThat(trade.getId()).isEqualTo(399328);
    assertThat(trade.getTimestamp()).isEqualTo(1405856801);
    assertThat(trade.getAssetPair()).isEqualTo("btc_usd");
    assertThat(trade.getType()).isEqualTo(CryptoTradeOrderType.Sell);
    assertThat(trade.getOrderAmount()).isEqualTo("0.08540439");
    assertThat(trade.getRate()).isEqualTo("618.19");

    assertThat(trades.getStatus()).isEqualTo("success");
    assertThat(trades.getError()).isNullOrEmpty();
  }

  @Test
  public void testDeserializePair() throws IOException {

    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-pair-data.json");

    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePair pair = mapper.readValue(is, CryptoTradePair.class);

    assertThat(pair.getLabel()).isEqualTo("esl_ltc");
    assertThat(pair.getType()).isEqualTo(CryptoTradePairType.security_pair);
    assertThat(pair.getMinOrderAmount()).isEqualTo("0.1");
    assertThat(pair.getDecimals()).isEqualTo(8);

    assertThat(pair.getStatus()).isEqualTo("success");
    assertThat(pair.getError()).isNullOrEmpty();
  }

  @Test
  public void testDeserializePairs() throws IOException {

    InputStream is = CryptoTradeMarketDataJsonTest.class.getResourceAsStream("/marketdata/example-pairs-data.json");

    ObjectMapper mapper = new ObjectMapper();
    CryptoTradePairs pairs = mapper.readValue(is, CryptoTradePairs.class);

    Map<CurrencyPair, CryptoTradePair> pairMap = pairs.getPairs();
    assertThat(pairMap).hasSize(4);
    assertThat(pairMap).containsKey(CurrencyPair.BTC_USD);
    assertThat(pairMap).containsKey(CurrencyPair.LTC_BTC);
    assertThat(pairMap).containsKey(new CurrencyPair("AMC", "BTC"));
    assertThat(pairMap).containsKey(new CurrencyPair("BMI", "BTC"));

    CryptoTradePair pair = pairMap.get(CurrencyPair.BTC_USD);
    assertThat(pair.getLabel()).isEqualTo("btc_usd");
    assertThat(pair.getType()).isEqualTo(CryptoTradePairType.normal_pair);
    assertThat(pair.getMinOrderAmount()).isEqualTo("0.001");
    assertThat(pair.getDecimals()).isEqualTo(8);

    pair = pairMap.get(new CurrencyPair("AMC", "BTC"));
    assertThat(pair.getLabel()).isEqualTo("amc_btc");
    assertThat(pair.getType()).isEqualTo(CryptoTradePairType.security_pair);
    /*
     * XXX: Exchange doesn't return minOrderAmount or Decimals for security_pairs
     */
    assertThat(pair.getMinOrderAmount()).isNull();
    assertThat(pair.getDecimals()).isEqualTo(0);

    assertThat(pairs.getStatus()).isEqualTo("success");
    assertThat(pairs.getError()).isNullOrEmpty();
  }
}
