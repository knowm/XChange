package org.knowm.xchange.coinbasepro.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.coinbasepro.CoinbaseProAdapters;
import org.knowm.xchange.coinbasepro.CoinbaseProExchange;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProCurrency;
import org.knowm.xchange.coinbasepro.dto.marketdata.CoinbaseProProduct;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.meta.ExchangeMetaData;
import si.mazi.rescu.serialization.jackson.DefaultJacksonObjectMapperFactory;
import si.mazi.rescu.serialization.jackson.JacksonObjectMapperFactory;

public class CoinbaseProMetadataTest {

  // @Test
  public void unmarshalTest() throws IOException {

    JacksonObjectMapperFactory factory = new DefaultJacksonObjectMapperFactory();
    ObjectMapper mapper = factory.createObjectMapper();

    InputStream is =
        getClass().getResourceAsStream("/org/knowm/xchange/coinbasepro/dto/products.json");
    CoinbaseProProduct[] products = mapper.readValue(is, CoinbaseProProduct[].class);
    assertThat(products).hasSize(10);

    ExchangeSpecification specification = new ExchangeSpecification(CoinbaseProExchange.class);
    specification.setShouldLoadRemoteMetaData(false);
    Exchange exchange = ExchangeFactory.INSTANCE.createExchange(specification);
    ExchangeMetaData exchangeMetaData = exchange.getExchangeMetaData();
    exchangeMetaData =
        CoinbaseProAdapters.adaptToExchangeMetaData(
            exchangeMetaData, products, new CoinbaseProCurrency[] {});
    assertThat(exchangeMetaData.getInstruments().get(CurrencyPair.ETC_BTC).getPriceScale())
        .isEqualTo(5);
  }
}
