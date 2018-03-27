package org.knowm.xchange.service.marketdata.params;

import org.knowm.xchange.currency.CurrencyPair;

import java.util.Collection;

public interface CurrencyPairsParam extends Params {

  Collection<CurrencyPair> getCurrencyPairs();
}
