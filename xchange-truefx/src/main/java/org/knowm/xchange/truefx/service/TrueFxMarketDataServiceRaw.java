package org.knowm.xchange.truefx.service;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.truefx.TrueFxPublic;
import org.knowm.xchange.truefx.dto.marketdata.TrueFxTicker;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class TrueFxMarketDataServiceRaw extends BaseExchangeService {

  private final TrueFxPublic trueFx;
  private final JacksonObjectMapperFactory factory =
      new DefaultJacksonObjectMapperFactory() {
        @Override
        protected ObjectMapper createInstance() {
          return new CsvMapper();
        }

        @Override
        public void configureObjectMapper(ObjectMapper mapper) {
          super.configureObjectMapper(mapper);

          final SimpleModule customDeserializer =
              new SimpleModule(TrueFxTicker.class.getSimpleName(), Version.unknownVersion());
          customDeserializer.addDeserializer(
              TrueFxTicker.class, new TrueFxTicker.TrueFxTickerDeserializer());
          mapper.registerModule(customDeserializer);
        }
      };

  protected TrueFxMarketDataServiceRaw(Exchange exchange) {
    super(exchange);

    final ClientConfig config = getClientConfig();
    config.setJacksonObjectMapperFactory(factory);

    trueFx =
        RestProxyFactory.createProxy(
            TrueFxPublic.class, exchange.getExchangeSpecification().getPlainTextUri(), config);
  }

  public TrueFxTicker getTicker(CurrencyPair pair) throws IOException {
    return trueFx.getTicker(pair);
  }

  public ObjectMapper createObjectMapper() {
    ObjectMapper mapper = factory.createObjectMapper();
    factory.configureObjectMapper(mapper);
    return mapper;
  }
}
