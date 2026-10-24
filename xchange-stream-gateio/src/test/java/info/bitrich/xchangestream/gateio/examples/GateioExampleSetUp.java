package info.bitrich.xchangestream.gateio.examples;

import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.gateio.GateioStreamingExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.utils.AuthUtils;

import static org.knowm.xchange.gateio.GateioExchange.EXCHANGE_TYPE;
import static org.knowm.xchange.gateio.dto.GateioExchangeType.FUTURES;
import static org.knowm.xchange.gateio.dto.GateioExchangeType.SPOT;

public class GateioExampleSetUp {

  public static GateioStreamingExchange initFutures() {
    ExchangeSpecification spec = new GateioStreamingExchange().getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, FUTURES);
    AuthUtils.setApiAndSecretKey(spec, "gateio-main");
    GateioStreamingExchange exchange = (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    exchange.connect().blockingAwait();
    return exchange;
  }

  public static GateioStreamingExchange initSpot() {
    ExchangeSpecification spec =
        StreamingExchangeFactory.INSTANCE
            .createExchangeWithoutSpecification(GateioStreamingExchange.class)
            .getDefaultExchangeSpecification();
    spec.setExchangeSpecificParametersItem(EXCHANGE_TYPE, SPOT);
    AuthUtils.setApiAndSecretKey(spec, "gateio-main");
    GateioStreamingExchange exchange = (GateioStreamingExchange) StreamingExchangeFactory.INSTANCE.createExchange(spec);
    exchange.connect().blockingAwait();
    return exchange;
  }
}
