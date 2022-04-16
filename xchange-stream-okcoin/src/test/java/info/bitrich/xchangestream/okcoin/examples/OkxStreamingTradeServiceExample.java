package info.bitrich.xchangestream.okcoin.examples;

import com.fasterxml.jackson.core.JsonProcessingException;
import info.bitrich.xchangestream.okcoin.OkxStreamingExchange;
import info.bitrich.xchangestream.okcoin.OkxStreamingService;
import info.bitrich.xchangestream.okcoin.OkxStreamingTradeService;
import info.bitrich.xchangestream.okcoin.dto.okx.enums.OkxInstType;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.derivative.FuturesContract;
import org.knowm.xchange.instrument.Instrument;
import org.knowm.xchange.okex.v5.OkexAdapters;

public class OkxStreamingTradeServiceExample {

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

        Thread.sleep(3000);

        OkxStreamingTradeService tradeService = new OkxStreamingTradeService(service);
        Instrument instrument = CurrencyPair.BTC_USDT;
        tradeService.getUserTrades(
                instrument
                , OkxInstType.SPOT
                , OkexAdapters.adaptCurrencyPairId(instrument)
                , OkexAdapters.adaptInstrumentId(instrument)
        ).forEach(System.out::println);
    }
}
