package com.knowm.xchange.serum;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.knowm.xchange.serum.core.Market;
import com.knowm.xchange.serum.core.WrapperFuncs;
import com.knowm.xchange.serum.dto.MarketMeta;
import com.knowm.xchange.serum.service.SerumMarketDataServiceRaw;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.knowm.xchange.currency.CurrencyPair;

public class SerumAdapters {

  private static final Map<CurrencyPair, Market> pairToMarket = new ConcurrentHashMap<>();

  /**
   * Serum represents markets as individual Solana addresses. As part of starting up the the
   * scaffold for Serum we load all the "markets" from the REST api and associate currency pairs to
   * their addresses and metadata.
   *
   * <p>The markets file maintained in XChange should mirror the one found:
   * https://github.com/project-serum/serum-ts/blob/master/packages/serum/src/markets.json
   *
   * @param raw service to use to query for data
   */
  public static void loadMarkets(final SerumMarketDataServiceRaw raw) throws Exception {
    final String text =
        new String(
            Files.readAllBytes(
                Paths.get(SerumAdapters.class.getResource("/markets.json").toURI())));
    final MarketMeta[] marketMetas = new ObjectMapper().readValue(text, MarketMeta[].class);

    for (MarketMeta meta : marketMetas) {
      final Market m = WrapperFuncs.runUntilSuccess(() ->
              raw.load(meta.address, null, meta.programId), 5000, 3);
      pairToMarket.put(new CurrencyPair(meta.name), m);
    }
  }

  /**
   * Extracts the address associated to a currency pair
   *
   * @param pair to query address for
   * @return address as string
   */
  public static String toSolanaAddress(final CurrencyPair pair) {
    return pairToMarket.get(pair).decoded.getOwnAddress().getKeyString();
  }

  /**
   * Extracts the address associated to a currency pair's specific data type. Serum manages the
   * different parts of an order's lifecycle by using different addresses. e.g.
   *
   * request queue  (submitted orders)
   * event queue    (fills)
   * bids           (orderbook bids)
   * asks           (orderbook asks)
   *
   *
   * @param pair to query address for
   * @return address as string
   */
  public static String getSolanaDataTypeAddress(final CurrencyPair pair, final String dataType) {
    final Market m = pairToMarket.get(pair);
    switch (dataType) {
      case "bids":
        return m.decoded.getBids().getKeyString();
      case "asks":
        return m.decoded.getAsks().getKeyString();
      case "eventQueue":
        return m.decoded.getEventQueue().getKeyString();
      case "requestQueue":
        return m.decoded.getRequestQueue().getKeyString();
      default:
        throw new IllegalArgumentException(String.format("Market Data Type %s not valid", dataType));
    }
  }

  public static Market getMarket(final CurrencyPair pair) {
    return pairToMarket.get(pair);
  }
}
