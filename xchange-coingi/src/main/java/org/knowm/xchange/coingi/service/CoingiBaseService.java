package org.knowm.xchange.coingi.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Optional;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.coingi.dto.CoingiAuthenticatedRequest;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

public class CoingiBaseService extends BaseExchangeService implements BaseService {
  protected CoingiDigest signatureCreator;

  protected CoingiBaseService(Exchange exchange) {
    super(exchange);
  }

  @Override
  public ClientConfig getClientConfig() {
    ClientConfig config = super.getClientConfig();
    config.setJacksonObjectMapperFactory(
        new DefaultJacksonObjectMapperFactory() {
          @Override
          public ObjectMapper createObjectMapper() {
            return super.createObjectMapper();
          }

          @Override
          public void configureObjectMapper(ObjectMapper objectMapper) {
            super.configureObjectMapper(objectMapper);
            SimpleModule simpleModule = new SimpleModule();
            simpleModule.addSerializer(
                Optional.class, new CoingiBaseService.OptionalValueSerializer());
            objectMapper.registerModule(simpleModule);
          }
        });
    return config;
  }

  protected void handleAuthentication(Object obj) {
    if (obj instanceof CoingiAuthenticatedRequest) {
      CoingiAuthenticatedRequest request = (CoingiAuthenticatedRequest) obj;
      Long nonce = exchange.getNonceFactory().createValue();
      request.setToken(exchange.getExchangeSpecification().getApiKey());
      request.setNonce(nonce);
      request.setSignature(signatureCreator.sign(nonce));
    }
  }

  private static class OptionalValueSerializer extends StdSerializer<Optional> {
    public OptionalValueSerializer() {
      this(null);
    }

    public OptionalValueSerializer(Class<Optional> t) {
      super(t);
    }

    @Override
    public void serialize(Optional value, JsonGenerator jgen, SerializerProvider provider)
        throws IOException {
      if (!value.isPresent()) return;

      Object val = value.get();
      if (val instanceof Long) jgen.writeNumber((Long) val);
      else if (val instanceof Integer) jgen.writeNumber((Integer) val);
      else if (val instanceof Float) jgen.writeNumber((Float) val);
      else if (val instanceof BigDecimal) jgen.writeNumber((BigDecimal) val);
      else if (val instanceof String) jgen.writeString((String) val);
      else
        throw new UnsupportedOperationException(
            "Unsupported object type: '" + val.getClass().getName() + "'!");
    }
  }
}
