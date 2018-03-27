package org.knowm.xchange.gdax.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Test;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import org.knowm.xchange.gdax.GDAXAdapters;
import org.knowm.xchange.gdax.GDAXExchange;
import org.knowm.xchange.gdax.dto.marketdata.GDAXProduct;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class GDAXMetadataTest {

  @Test
  public void unmarshalTest() throws IOException {

    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is = getClass().getResourceAsStream("/org/knowm/xchange/gdax/dto/products.json");
    GDAXProduct[] products = mapper.readValue(is, GDAXProduct[].class);
    assertThat(products).hasSize(9);

    ExchangeSpecification specification = new ExchangeSpecification(GDAXExchange.class);
    specification.setShouldLoadRemoteMetaData(false);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    exchangeMetaData = GDAXAdapters.adaptToExchangeMetaData(exchangeMetaData, products);
  }
}
