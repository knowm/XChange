package org.knowm.xchange.dragonex;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dragonex.dto.DragonexException;
import org.knowm.xchange.dragonex.dto.Token;
import org.knowm.xchange.dragonex.dto.marketdata.Coin;
import org.knowm.xchange.dragonex.dto.marketdata.Symbol;
import org.knowm.xchange.dragonex.service.DragonDigest;
import org.knowm.xchange.dragonex.service.DragonexAccountService;
import org.knowm.xchange.dragonex.service.DragonexAccountServiceRaw;
import org.knowm.xchange.dragonex.service.DragonexMarketDataService;
import org.knowm.xchange.dragonex.service.DragonexTradeService;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class DragonexExchange extends BaseExchange implements Exchange {

  private Dragonex dragonexPublic;
  private DragonexAuthenticated dragonexAuthenticated;
  private ParamsDigest signatureCreator;
  private final AtomicReference<Token> currentToken = new AtomicReference<>();
  private final Map<Long, String> coins = new HashMap<>();
  private final Map<String, Long> coinsReverse = new HashMap<>();
  private Map<Long, CurrencyPair> symbols = new HashMap<>();
  private Map<CurrencyPair, Long> pairs = new HashMap<>();

  @Override
  protected void initServices() {
    this.marketDataService = new DragonexMarketDataService(this);
    this.accountService = new DragonexAccountService(this);
    this.tradeService = new DragonexTradeService(this);

    ExchangeSpecification spec = this.getExchangeSpecification();
    this.dragonexPublic = ExchangeRestProxyBuilder.forInterface(Dragonex.class, spec).build();

    if (spec.getApiKey() != null && spec.getSecretKey() != null) {
      this.dragonexAuthenticated =
          ExchangeRestProxyBuilder.forInterface(DragonexAuthenticated.class, spec).build();
      this.signatureCreator = new DragonDigest(spec.getApiKey(), spec.getSecretKey());
    }

    Token token = (Token) spec.getExchangeSpecificParametersItem("dragonex.token");
    currentToken.set(token);
  }

  public CurrencyPair pair(long symbolId) {
    return symbols.get(symbolId);
  }

  public long symbolId(CurrencyPair pair) {
    Long symbolId = pairs.get(pair);
    if (symbolId == null) {
      throw new ExchangeException("Not supported pair " + pair + " by Dragonex.");
    }
    return symbolId;
  }

  public Token getOrCreateToken() throws DragonexException, IOException {
    Token token = currentToken.get();
    if (token != null && token.valid()) {
      return token;
    }
    synchronized (currentToken) {
      token = currentToken.get();
      if (token != null && token.valid()) {
        return token;
      }
      token = ((DragonexAccountServiceRaw) accountService).tokenNew();
      currentToken.set(token);
    }
    return token;
  }

  public Dragonex dragonexPublic() {
    return dragonexPublic;
  }

  public DragonexAuthenticated dragonexAuthenticated() {
    return dragonexAuthenticated;
  }

  public ParamsDigest signatureCreator() {
    return signatureCreator;
  }

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://openapi.dragonex.io/");
    spec.setHost("openapi.dragonex.io");
    spec.setPort(80);
    spec.setExchangeName("Dragonex");
    spec.setExchangeDescription("Dragonex is a bitcoin and altcoin exchange.");
    return spec;
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new ExchangeException("Dragonex does not require a nonce factory.");
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    try {
      List<Coin> coinAll = ((DragonexMarketDataService) marketDataService).coinAll();
      coinAll.forEach(
          c -> {
            coins.put(c.coinId, c.code.toUpperCase());
            coinsReverse.put(c.code.toUpperCase(), c.coinId);
          });

      List<Symbol> symbolAll = ((DragonexMarketDataService) marketDataService).symbolAll();
      symbolAll.forEach(
          c -> {
            CurrencyPair pair = new CurrencyPair(c.symbol.toUpperCase().replace('_', '/'));
            symbols.put(c.symbolId, pair);
            pairs.put(pair, c.symbolId);
          });
    } catch (Throwable e) {
      throw new RuntimeException("Could not initialize the Dragonex service.", e);
    }
  }

  public long getCoinId(String currency) {
    Long coinId = coinsReverse.get(currency);
    if (coinId == null) {
      throw new RuntimeException("Could not find the coin id for " + currency);
    }
    return coinId;
  }

  public String getCurrency(long coinId) {
    String currency = coins.get(coinId);
    if (currency == null) {
      throw new RuntimeException("Could not find the currency for coin id: " + coinId);
    }
    return currency;
  }
}
