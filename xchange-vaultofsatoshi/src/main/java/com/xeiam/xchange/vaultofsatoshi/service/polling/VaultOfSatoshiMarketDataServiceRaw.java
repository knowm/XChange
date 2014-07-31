package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;
import java.util.List;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshi;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiDepth;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiTicker;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VaultOfSatoshiTrade;

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

  public VaultOfSatoshiTicker getVosTicker(CurrencyPair pair) throws IOException {

    // Request data
    VaultOfSatoshiTicker vosTicker = vaultOfSatoshi.getTicker(pair.baseSymbol, pair.counterSymbol).getTicker();

    // Adapt to XChange DTOs
    return vosTicker;
  }

  public VaultOfSatoshiDepth getVosOrderBook(CurrencyPair pair) throws IOException {

    // Request data
    VaultOfSatoshiDepth vosDepth = vaultOfSatoshi.getFullDepth(pair.baseSymbol, pair.counterSymbol).getDepth();

    return vosDepth;
  }

  public List<VaultOfSatoshiTrade> getVosTrades(CurrencyPair pair) throws IOException {

    // Request data
    List<VaultOfSatoshiTrade> vosTrades = vaultOfSatoshi.getTrades(pair.baseSymbol, pair.counterSymbol, null, 100).getTrades();

    return vosTrades;
  }

  public List<VaultOfSatoshiTrade> getVosTrades(CurrencyPair pair, Long sinceId, int count) throws IOException {

    // Request data
    List<VaultOfSatoshiTrade> vosTrades = vaultOfSatoshi.getTrades(pair.baseSymbol, pair.counterSymbol, sinceId, count).getTrades();

    return vosTrades;
  }

}
