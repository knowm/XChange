package org.knowm.xchange.hitbtc.v2.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.hitbtc.HitbtcAdapters;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcSymbol;
import org.knowm.xchange.hitbtc.v2.dto.HitbtcTicker;
import org.knowm.xchange.hitbtc.v2.internal.api.HitbtcRestClient;
import org.knowm.xchange.service.BaseService;

public class HitbtcMarketDataServiceRaw implements BaseService {


  public List<HitbtcSymbol> getHitbtcSymbols() throws IOException {

    return HitbtcRestClient.INSTANCE.call().getSymbols();
  }

  public HitbtcTicker getHitbtcTicker(CurrencyPair currencyPair) throws IOException {

    return HitbtcRestClient.INSTANCE.call().getHitbtcTicker(HitbtcAdapters.adaptCurrencyPair(currencyPair));
  }

}
