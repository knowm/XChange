package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshi;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTicker;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosTrade;

/**
 * <p>
 * Implementation of the market data service for VaultOfSatoshi
 * </p>
 * <ul>
 * <li>Provides access to various market data values</li>
 * </ul>
 */
public class VaultOfSatoshiMarketDataServiceRaw extends VaultOfSatoshiBasePollingService {

  private final VaultOfSatoshi vaultOfSatoshi;

  /**
   * Constructor
   * 
   * @param exchangeSpecification The {@link ExchangeSpecification}
   */
  public VaultOfSatoshiMarketDataServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
    this.vaultOfSatoshi = RestProxyFactory.createProxy(VaultOfSatoshi.class, exchangeSpecification.getSslUri());
  }

  public VosTicker getVosTicker(CurrencyPair pair) throws IOException {

    // Request data
    VosTicker vosTicker = vaultOfSatoshi.getTicker(pair.baseSymbol, pair.counterSymbol).getData();

    // Adapt to XChange DTOs
    return vosTicker;
  }

  public VosDepth getVosOrderBook(CurrencyPair pair) throws IOException {

    // Request data
    VosDepth vosDepth = vaultOfSatoshi.getFullDepth(pair.baseSymbol, pair.counterSymbol).getData();

    return vosDepth;
  }

  public List<VosTrade> getVosTrades(CurrencyPair pair) throws IOException {

    // Request data
    List<VosTrade> vosTrades = vaultOfSatoshi.getTrades(pair.baseSymbol, pair.counterSymbol, null, 100).getData();

    return vosTrades;
  }

  public List<VosTrade> getVosTrades(CurrencyPair pair, Long sinceId, int count) throws IOException {

    // Request data
    List<VosTrade> vosTrades = vaultOfSatoshi.getTrades(pair.baseSymbol, pair.counterSymbol, sinceId, count).getData();

    return vosTrades;
  }

}
