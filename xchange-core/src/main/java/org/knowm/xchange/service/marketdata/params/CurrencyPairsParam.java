package org.knowm.xchange.service.marketdata.params;

import java.util.Collection;
import org.knowm.xchange.currency.CurrencyPair;

public interface CurrencyPairsParam extends Params {

  Collection<CurrencyPair> getCurrencyPairs();
}
