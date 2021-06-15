package org.knowm.xchange.bittrex;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bittrex.dto.BittrexException;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexCurrency;
import org.knowm.xchange.bittrex.dto.marketdata.BittrexSymbol;
import org.knowm.xchange.bittrex.service.BittrexAccountService;
import org.knowm.xchange.bittrex.service.BittrexMarketDataService;
import org.knowm.xchange.bittrex.service.BittrexMarketDataServiceRaw;
import org.knowm.xchange.bittrex.service.BittrexTradeService;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.account.Fee;
import org.knowm.xchange.utils.nonce.AtomicLongIncrementalTime2013NonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class BittrexExchange extends BaseExchange implements Exchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongIncrementalTime2013NonceFactory();

  private static final Object INIT_LOCK = new Object();

  private static final Logger EXCHANGE_LOGGER = Logger.getLogger(BittrexExchange.class.getName());

  private static List<BittrexSymbol> bittrexSymbols = new ArrayList<>();

  private static ResilienceRegistries resilienceRegistries;

  @Override
  protected void initServices() {
    BittrexAuthenticated bittrex = ExchangeRestProxyBuilder.forInterface(
            BittrexAuthenticated.class, getExchangeSpecification())
            .build();
    this.marketDataService = new BittrexMarketDataService(this, bittrex, getResilienceRegistries());
    this.accountService = new BittrexAccountService(this, bittrex, getResilienceRegistries());
    this.tradeService = new BittrexTradeService(this, bittrex, getResilienceRegistries());
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass());
    exchangeSpecification.setSslUri("https://api.bittrex.com/");
    exchangeSpecification.setHost("bittrex.com");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Bittrex");
    exchangeSpecification.setExchangeDescription("Bittrex is a cryptocurrencies exchange.");

    return exchangeSpecification;
  }

  @Override
  public synchronized ResilienceRegistries getResilienceRegistries() {
    if (resilienceRegistries == null) {
      resilienceRegistries = BittrexResilience.createRegistries();
    }
    return resilienceRegistries;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public void remoteInit() throws IOException {
    if (bittrexSymbols.isEmpty()) {
      synchronized (INIT_LOCK) {
        if (bittrexSymbols.isEmpty()) {
          bittrexSymbols = ((BittrexMarketDataServiceRaw) marketDataService).getBittrexSymbols();
          List<BittrexCurrency> bittrexCurrencies =
                  ((BittrexMarketDataServiceRaw) marketDataService).getBittrexCurrencies();
          Map<CurrencyPair, Fee> dynamicTradingFees = null;
          try {
            dynamicTradingFees = accountService.getDynamicTradingFees();
          } catch (BittrexException | IOException e) {
              EXCHANGE_LOGGER.log(Level.WARNING,
                    "Error during remote init, can not fetch trading fees. May be missing auth tokens ?");
          }
          BittrexAdapters.adaptMetaData(bittrexSymbols, bittrexCurrencies, dynamicTradingFees, exchangeMetaData);
        }
      }
    }
  }
}
