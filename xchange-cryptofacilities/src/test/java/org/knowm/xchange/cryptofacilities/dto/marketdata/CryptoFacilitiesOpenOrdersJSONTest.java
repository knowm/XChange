package org.knowm.xchange.cryptofacilities.dto.marketdata;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;

/** @author Jean-Christophe Laruelle */
public class CryptoFacilitiesOpenOrdersJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    InputStream is =
        CryptoFacilitiesOpenOrdersJSONTest.class.getResourceAsStream(
            "/org/knowm/xchange/cryptofacilities/dto/marketdata/example-openOrders-data.json");

    // Use Jackson to parse it
    ObjectMapper mapper = new ObjectMapper();
    CryptoFacilitiesOpenOrders cryptoFacilitiesOpenOrders =
        mapper.readValue(is, CryptoFacilitiesOpenOrders.class);

    // Verify that the example data was unmarshalled correctly
    assertThat(cryptoFacilitiesOpenOrders.isSuccess()).isTrue();

    List<CryptoFacilitiesOpenOrder> orders = cryptoFacilitiesOpenOrders.getOrders();

    assertThat(orders.size()).isEqualTo(2);

    Iterator<CryptoFacilitiesOpenOrder> it = orders.iterator();
    CryptoFacilitiesOpenOrder ord = it.next();

    assertThat(ord.getStatus()).isEqualTo("partiallyFilled");
    assertThat(ord.getId()).isEqualTo("c18f0c17-9971-40e6-8e5b-10df05d422f0");
    assertThat(ord.getType()).isEqualTo("lmt");
    assertThat(ord.getSymbol()).isEqualTo("f-xbt:usd-sep16");
    assertThat(ord.getDirection()).isEqualTo("sell");
    assertThat(ord.getUnfilled()).isEqualTo(new BigDecimal("2"));
    assertThat(ord.getFilled()).isEqualTo(new BigDecimal("3"));
    assertThat(ord.getQuantity()).isEqualTo(new BigDecimal("5"));
    assertThat(ord.getLimitPrice()).isEqualTo(new BigDecimal("430.11"));
  }
}
