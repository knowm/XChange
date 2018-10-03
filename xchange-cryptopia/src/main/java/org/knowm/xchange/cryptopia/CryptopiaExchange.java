package org.knowm.xchange.cryptopia;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaCurrency;
import org.knowm.xchange.cryptopia.dto.marketdata.CryptopiaTradePair;
import org.knowm.xchange.cryptopia.service.CryptopiaAccountService;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataService;
import org.knowm.xchange.cryptopia.service.CryptopiaMarketDataServiceRaw;
import org.knowm.xchange.cryptopia.service.CryptopiaTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.utils.jackson.CurrencyPairDeserializer;
import org.knowm.xchange.utils.nonce.AtomicLongCurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.SynchronizedValueFactory;

public class CryptopiaExchange extends BaseExchange {

  private final SynchronizedValueFactory<Long> nonceFactory =
      new AtomicLongCurrentTimeIncrementalNonceFactory();
  private Map<CurrencyPair, CryptopiaTradePair> lookupByCcyPair;
  private Map<Long, CryptopiaTradePair> lookupById;

  @Override
  protected void initServices() {
    this.accountService = new CryptopiaAccountService(this);
    this.marketDataService = new CryptopiaMarketDataService(this);
    this.tradeService = new CryptopiaTradeService(this);
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    return nonceFactory;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification exchangeSpecification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    exchangeSpecification.setSslUri("https://www.cryptopia.co.nz");
    exchangeSpecification.setHost("www.cryptopia.co.nz");
    exchangeSpecification.setPort(80);
    exchangeSpecification.setExchangeName("Cryptopia");
    exchangeSpecification.setExchangeDescription(
        "Cryptopia is a Bitcoin exchange registered in New Zealand");

    return exchangeSpecification;
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    List<CryptopiaCurrency> currencies =
        ((CryptopiaMarketDataServiceRaw) marketDataService).getCryptopiaCurrencies();
    List<CryptopiaTradePair> tradePairs =
        ((CryptopiaMarketDataServiceRaw) marketDataService).getCryptopiaTradePairs();

    Map<CurrencyPair, CryptopiaTradePair> lookupByCcyPair = new HashMap<>();
    Map<Long, CryptopiaTradePair> lookupById = new HashMap<>();
    for (CryptopiaTradePair tradePair : tradePairs) {
      lookupByCcyPair.put(
          CurrencyPairDeserializer.getCurrencyPairFromString(tradePair.getLabel()), tradePair);
      lookupById.put(tradePair.getId(), tradePair);
    }

    this.lookupByCcyPair = lookupByCcyPair;
    this.lookupById = lookupById;

    exchangeMetaData = CryptopiaAdapters.adaptToExchangeMetaData(currencies, tradePairs);
  }

  public Long tradePairId(CurrencyPair currencyPair) {
    CryptopiaTradePair cryptopiaTradePair = lookupByCcyPair.get(currencyPair);
    if (cryptopiaTradePair == null) {
      throw new RuntimeException("Pair not supported by Cryptopia exchange: " + currencyPair);
    }
    return cryptopiaTradePair.getId();
  }

  public CryptopiaTradePair tradePair(Long id) {
    return lookupById.get(id);
  }

  @Override
  public AccountService getAccountService() {
    return accountService;
  }
}
