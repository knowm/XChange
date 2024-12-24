package info.bitrich.xchangestream.bybit.example;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;

import info.bitrich.xchangestream.bybit.BybitStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;

public class BaseBybitExchange {
  public static StreamingExchange connect(BybitCategory category, boolean withAuth) {
    ExchangeSpecification exchangeSpecification =
        new BybitStreamingExchange().getDefaultExchangeSpecification();
    if(withAuth) {
      exchangeSpecification.setApiKey(System.getProperty("test_api_key"));
      exchangeSpecification.setSecretKey(System.getProperty("test_secret_key"));
    }
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(
        BybitStreamingExchange.EXCHANGE_TYPE, category);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, true);
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    exchange.connect().blockingAwait();
    return exchange;
  }
}
