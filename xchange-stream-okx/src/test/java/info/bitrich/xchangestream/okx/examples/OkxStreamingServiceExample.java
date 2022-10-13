package info.bitrich.xchangestream.okx.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import info.bitrich.xchangestream.okx.OkxStreamingExchange;
import info.bitrich.xchangestream.okx.OkxStreamingService;
import org.knowm.xchange.ExchangeSpecification;

public class OkxStreamingServiceExample {

    public static void main(String[] args) throws JsonProcessingException, InterruptedException {
        // Enter your authentication details here to run private endpoint tests
        final String API_KEY = System.getenv("okx_apikey");
        final String SECRET_KEY = System.getenv("okx_secretkey");
        final String PASSPHRASE = System.getenv("okx_passphrase");

        String url = "wss://wspap.okx.com:8443/ws/v5/private?brokerId=9999";
        ExchangeSpecification spec = new OkxStreamingExchange().getDefaultExchangeSpecification();
        spec.setApiKey(API_KEY);
        spec.setSecretKey(SECRET_KEY);
        spec.setExchangeSpecificParametersItem("passphrase", PASSPHRASE);

        OkxStreamingService service = new OkxStreamingService(url, spec);

        service.connect().blockingAwait();

        service.login();

        Thread.sleep(5000);

        System.exit(0);
    }
}
