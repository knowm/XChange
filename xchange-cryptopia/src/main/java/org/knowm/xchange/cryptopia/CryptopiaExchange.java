package org.knowm.xchange.cryptopia;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataService;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataServiceRaw;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.SynchronizedValueFactory;

public class CryptopiaExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory = new CurrentTimeNonceFactory();

  @Override
  protected void initServices() {
    this.marketDataService = new CryptopiaMarketDataService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification = new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.cryptopia.co.nz");
    exchangeSpecification.setHost("www.cryptopia.co.nz");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptopia");
    exchangeSpecification.setExchangeDescription("Cryptopia is a Bitcoin exchange registered in New Zealand");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    List<CryptopiaCurrency> currencies = ((CryptopiaMarketDataServiceRaw) marketDataService).getCryptopiaCurrencies();
    List<CryptopiaTradePair> tradePairs = ((CryptopiaMarketDataServiceRaw) marketDataService).getCryptopiaTradePairs();

    exchangeMetaData = CryptopiaAdapters.adaptToExchangeMetaData(currencies, tradePairs);
  }
}
