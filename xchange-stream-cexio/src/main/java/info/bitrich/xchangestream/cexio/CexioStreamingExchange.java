package info.bitrich.xchangestream.cexio;

import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import io.reactivex.Completable;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.cexio.CexIOExchange;
import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;

public class CexioStreamingExchange extends CexIOExchange implements StreamingExchange {

  private static final String API_URI = "wss://ws.cex.io/ws/";

  private final CexioStreamingMarketDataService streamingMarketDataService;
  private final CexioStreamingRawService streamingOrderDataService;

  public CexioStreamingExchange() {
    this.streamingOrderDataService = new CexioStreamingRawService(API_URI);
    this.streamingMarketDataService =
        new CexioStreamingMarketDataService(streamingOrderDataService);
  }

  @Override
  public Completable connect(ProductSubscription... args) {
    return streamingOrderDataService.connect();
  }

  @Override
  public Completable disconnect() {
    return streamingOrderDataService.disconnect();
  }

  @Override
  public boolean isAlive() {
    return streamingOrderDataService.isSocketOpen();
  }

  @Override
  public StreamingMarketDataService getStreamingMarketDataService() {
    return streamingMarketDataService;
  }

  @Override
  public void useCompressedMessages(boolean compressedMessages) {
    throw new NotYetImplementedForExchangeException();
  }

  public void setCredentials(String apiKey, String apiSecret) {
    streamingOrderDataService.setApiKey(apiKey);
    streamingOrderDataService.setApiSecret(apiSecret);
  }

  @Override
  public void applySpecification(ExchangeSpecification specification) {
    super.applySpecification(exchangeSpecification);
    ExchangeSpecification finalSpec = getExchangeSpecification();
    String apiKey = finalSpec.getApiKey();
    String secretKey = finalSpec.getSecretKey();
    if (apiKey != null && secretKey != null) {
      setCredentials(apiKey, secretKey);
    }
  }

  public CexioStreamingRawService getStreamingRawService() {
    return streamingOrderDataService;
  }
}
