package info.bitrich.xchangestream.kraken;

import info.bitrich.xchangestream.kraken.config.Config;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.utils.ArrayUtils;
import org.knowm.xchange.utils.nonce.CurrentTimeIncrementalNonceFactory;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.SynchronizedValueFactory;

public class KrakenPrivateStreamingService extends KrakenStreamingService {

  protected KrakenStreamingExchange krakenStreamingExchange;
  protected KrakenAuthenticated krakenAuthenticated;
  protected ParamsDigest signatureCreator;

  private final SynchronizedValueFactory<Long> nonceFactory =
      new CurrentTimeIncrementalNonceFactory(TimeUnit.MILLISECONDS);

  public KrakenPrivateStreamingService(String apiUri, KrakenStreamingExchange exchange) {
    super(apiUri);

    krakenAuthenticated =
        ExchangeRestProxyBuilder.forInterface(
                KrakenAuthenticated.class, exchange.getExchangeSpecification())
            .build();
    signatureCreator =
        KrakenDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());

    krakenStreamingExchange = exchange;
  }

  /**
   * @return subscribe message containing a websocket token needed for private channels
   */
  @Override
  public String getSubscribeMessage(String channelName, Object... args) throws IOException {
    CurrencyPair currencyPair = ArrayUtils.getElement(0, args, CurrencyPair.class, null);
    var message = KrakenStreamingAdapters.toSubscribeMessage(channelName, currencyPair);

    // get token for private channels
    if (Config.PRIVATE_CHANNELS.contains(channelName)) {
      var tokenResult =
          krakenAuthenticated.getWebsocketToken(
              krakenStreamingExchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              nonceFactory);

      message.getParams().setToken(tokenResult.getResult().getToken());
    }
    return objectMapper.writeValueAsString(message);
  }

  /**
   * @return unsubscribe message containing a websocket token needed for private channels
   */
  @Override
  public String getUnsubscribeMessage(String subscriptionUniqueId, Object... args)
      throws IOException {
    var message = KrakenStreamingAdapters.toUnsubscribeMessage(subscriptionUniqueId);

    // get token for private channels
    if (Config.PRIVATE_CHANNELS.contains(message.getParams().getChannel())) {
      var tokenResult =
          krakenAuthenticated.getWebsocketToken(
              krakenStreamingExchange.getExchangeSpecification().getApiKey(),
              signatureCreator,
              nonceFactory);

      message.getParams().setToken(tokenResult.getResult().getToken());
    }
    return objectMapper.writeValueAsString(message);
  }
}
