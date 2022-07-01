package info.bitrich.xchangestream.binance;

import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.AuthUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BinanceUsStreamingExchange extends BinanceStreamingExchange {
  private static final Logger LOG = LoggerFactory.getLogger(BinanceStreamingExchange.class);
  private static final String API_BASE_URI = "wss://stream.binance.us:9443/";

  @Override
  protected String getStreamingBaseUri() {
    return API_BASE_URI;
  }

  // This is needed since BinanceStreamingExchange extends BinanceExchange which has the spec
  // configured for binance.com
  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {

    ExchangeSpecification spec = new ExchangeSpecification(this.getClass());
    spec.setSslUri("https://api.binance.us");
    spec.setHost("www.binance.us");
    spec.setPort(80);
    spec.setExchangeName("Binance US");
    spec.setExchangeDescription("Binance US Exchange.");
    AuthUtils.setApiAndSecretKey(spec, "binanceus");
    return spec;
  }
}
