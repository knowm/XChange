package org.knowm.xchange.abucoins.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import java.math.BigDecimal;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.abucoins.Abucoins;
import org.knowm.xchange.abucoins.AbucoinsAuthenticated;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ClientConfig;
import si.mazi.rescu.RestProxyFactory;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;

/** @author bryant_harris */
public class AbucoinsBaseService extends BaseExchangeService implements BaseService {
  protected final Abucoins abucoins;
  protected final AbucoinsAuthenticated abucoinsAuthenticated;
  protected final AbucoinsDigest signatureCreator;

  /**
   * Constructor
   *
   * @param exchange
   */
  public AbucoinsBaseService(Exchange exchange) {

    super(exchange);

    ClientConfig clientConfig = getClientConfig();
    clientConfig.setJacksonObjectMapperFactory(
        new DefaultJacksonObjectMapperFactory() {
          @Override
          public void configureObjectMapper(ObjectMapper objectMapper) {
            super.configureObjectMapper(objectMapper);
            SimpleModule module = new SimpleModule();
            module.addSerializer(BigDecimal.class, new ToStringSerializer());
            objectMapper.registerModule(module);
          }
        });

    abucoins =
        RestProxyFactory.createProxy(
            Abucoins.class, exchange.getExchangeSpecification().getSslUri(), clientConfig);
    abucoinsAuthenticated =
        RestProxyFactory.createProxy(
            AbucoinsAuthenticated.class,
            exchange.getExchangeSpecification().getSslUri(),
            clientConfig);
    signatureCreator =
        AbucoinsDigest.createInstance(abucoins, exchange.getExchangeSpecification().getSecretKey());
  }

  /**
   * Helper method that performs a null check. SignatureCreator is null if no API key is provided.
   *
   * @return The timestamp as maintained by the signature creator.
   */
  protected String timestamp() {
    return (signatureCreator == null) ? null : signatureCreator.timestamp();
  }
}
