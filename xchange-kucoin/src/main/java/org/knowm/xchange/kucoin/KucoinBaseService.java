package org.knowm.xchange.kucoin;

import com.google.common.base.Strings;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.kucoin.service.AccountAPI;
import org.knowm.xchange.kucoin.service.DepositAPI;
import org.knowm.xchange.kucoin.service.FillAPI;
import org.knowm.xchange.kucoin.service.HistOrdersAPI;
import org.knowm.xchange.kucoin.service.HistoryAPI;
import org.knowm.xchange.kucoin.service.KucoinApiException;
import org.knowm.xchange.kucoin.service.KucoinDigest;
import org.knowm.xchange.kucoin.service.LimitOrderAPI;
import org.knowm.xchange.kucoin.service.OrderAPI;
import org.knowm.xchange.kucoin.service.OrderBookAPI;
import org.knowm.xchange.kucoin.service.SymbolAPI;
import org.knowm.xchange.kucoin.service.TradingFeeAPI;
import org.knowm.xchange.kucoin.service.WebsocketAPI;
import org.knowm.xchange.kucoin.service.WithdrawalAPI;
import org.knowm.xchange.service.BaseResilientExchangeService;
import si.mazi.rescu.SynchronizedValueFactory;

public class KucoinBaseService extends BaseResilientExchangeService<KucoinExchange> {

  protected final SymbolAPI symbolApi;
  protected final OrderBookAPI orderBookApi;
  protected final HistoryAPI historyApi;
  protected final AccountAPI accountApi;
  protected final WithdrawalAPI withdrawalAPI;
  protected final DepositAPI depositAPI;
  protected final OrderAPI orderApi;
  protected final LimitOrderAPI limitOrderAPI;
  protected final FillAPI fillApi;
  protected final HistOrdersAPI histOrdersApi;
  protected final WebsocketAPI websocketAPI;
  protected final TradingFeeAPI tradingFeeAPI;

  protected KucoinDigest digest;
  protected String apiKey;
  protected String passphrase;
  protected SynchronizedValueFactory<Long> nonceFactory;

  protected KucoinBaseService(KucoinExchange exchange, ResilienceRegistries resilienceRegistries) {
    super(exchange, resilienceRegistries);
    this.symbolApi = service(exchange, SymbolAPI.class);
    this.orderBookApi = service(exchange, OrderBookAPI.class);
    this.historyApi = service(exchange, HistoryAPI.class);
    this.accountApi = service(exchange, AccountAPI.class);
    this.withdrawalAPI = service(exchange, WithdrawalAPI.class);
    this.depositAPI = service(exchange, DepositAPI.class);
    this.orderApi = service(exchange, OrderAPI.class);
    this.limitOrderAPI = service(exchange, LimitOrderAPI.class);
    this.fillApi = service(exchange, FillAPI.class);
    this.histOrdersApi = service(exchange, HistOrdersAPI.class);
    this.websocketAPI = service(exchange, WebsocketAPI.class);

    this.digest = KucoinDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
    this.apiKey = exchange.getExchangeSpecification().getApiKey();
    this.passphrase =
        (String)
            exchange.getExchangeSpecification().getExchangeSpecificParametersItem("passphrase");
    this.nonceFactory = exchange.getNonceFactory();

    this.tradingFeeAPI = service(exchange, TradingFeeAPI.class);
  }

  private <T> T service(KucoinExchange exchange, Class<T> clazz) {
    return ExchangeRestProxyBuilder.forInterface(clazz, exchange.getExchangeSpecification())
        .build();
  }

  protected void checkAuthenticated() {
    if (Strings.isNullOrEmpty(this.apiKey)) throw new KucoinApiException("Missing API key");
    if (this.digest == null) throw new KucoinApiException("Missing secret key");
    if (Strings.isNullOrEmpty(this.passphrase)) throw new KucoinApiException("Missing passphrase");
  }
}
