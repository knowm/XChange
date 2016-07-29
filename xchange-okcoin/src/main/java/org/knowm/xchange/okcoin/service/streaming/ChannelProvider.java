package org.knowm.xchange.okcoin.service.streaming;

import org.knowm.xchange.currency.CurrencyPair;

/** Returns market data channel name for given connection method and currency pair */
interface ChannelProvider {
  String getTicker(CurrencyPair currencyPair);

  String getDepth(CurrencyPair currencyPair);

  String getTrades(CurrencyPair currencyPair);
}