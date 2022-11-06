package info.bitrich.xchangestream.okex.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import info.bitrich.xchangestream.okex.OkexStreamingExchange;
import info.bitrich.xchangestream.okex.OkexStreamingService;
import org.knowm.xchange.ExchangeSpecification;

public class OkxStreamingServiceExample {

  public static void main(String[] args) throws JsonProcessingException, InterruptedException {
    // Enter your authentication details here to run private endpoint tests
    final String API_KEY = System.getenv("okx_apikey");
    final String SECRET_KEY = System.getenv("okx_secretkey");
    final String PASSPHRASE = System.getenv("okx_passphrase");

    String url = "wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999";
    ExchangeSpecification spec = new OkexStreamingExchange().getDefaultExchangeSpecification();
    spec.setApiKey(API_KEY);
    spec.setSecretKey(SECRET_KEY);
    spec.setExchangeSpecificParametersItem("passphrase", PASSPHRASE);

    OkexStreamingService service = new OkexStreamingService(url, spec);

    service.connect().blockingAwait();

    service.login();

    Thread.sleep(5000);

    System.exit(0);
  }
}
