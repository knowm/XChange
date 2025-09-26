package info.bitrich.xchangestream.bybit.example;

import static org.knowm.xchange.Exchange.USE_SANDBOX;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_ACCOUNT_TYPE;
import static org.knowm.xchange.bybit.BybitExchange.SPECIFIC_PARAM_TESTNET;

import info.bitrich.xchangestream.bybit.BybitStreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bybit.dto.BybitCategory;
import org.knowm.xchange.bybit.dto.account.walletbalance.BybitAccountType;
import org.knowm.xchange.utils.AuthUtils;

public class BaseBybitExchange {

  public static StreamingExchange connectDemoApi(BybitCategory category, boolean withAuth) {
    ExchangeSpecification exchangeSpecification =
        new BybitStreamingExchange().getDefaultExchangeSpecification();

    if (!withAuth) {
      exchangeSpecification.setApiKey(null);
      exchangeSpecification.setSecretKey(null);
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

  public static StreamingExchange connectMainApi(BybitCategory category, boolean withAuth) {
    ExchangeSpecification exchangeSpecification =
        new BybitStreamingExchange().getDefaultExchangeSpecification();
    if (!withAuth) {
      exchangeSpecification.setApiKey(null);
      exchangeSpecification.setSecretKey(null);
    }
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(USE_SANDBOX, false);
    exchangeSpecification.setExchangeSpecificParametersItem(
        BybitStreamingExchange.EXCHANGE_TYPE, category);
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "bybit-main");
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    exchange.connect().blockingAwait();
    return exchange;
  }

  public static StreamingExchange connectTestApi(BybitCategory category, boolean withAuth) {
    ExchangeSpecification exchangeSpecification =
        new BybitStreamingExchange().getDefaultExchangeSpecification();
    exchangeSpecification.setExchangeSpecificParametersItem(
        SPECIFIC_PARAM_ACCOUNT_TYPE, BybitAccountType.UNIFIED);
    exchangeSpecification.setExchangeSpecificParametersItem(
        BybitStreamingExchange.EXCHANGE_TYPE, BybitCategory.LINEAR);
    exchangeSpecification.setExchangeSpecificParametersItem(SPECIFIC_PARAM_TESTNET, true);
    AuthUtils.setApiAndSecretKey(exchangeSpecification, "bybit-testnet");
    StreamingExchange exchange =
        StreamingExchangeFactory.INSTANCE.createExchange(exchangeSpecification);
    exchange.connect().blockingAwait();
    return exchange;
  }
}
