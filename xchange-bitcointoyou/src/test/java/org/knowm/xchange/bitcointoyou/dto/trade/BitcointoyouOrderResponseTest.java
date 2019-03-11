package org.knowm.xchange.bitcointoyou.dto.trade;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.junit.Test;
import org.knowm.xchange.bitcointoyou.BitcointoyouAdaptersTest;

/**
 * Testes the {@link BitcointoyouOrderInfo} class
 *
 * @author Danilo Guimaraes
 */
public class BitcointoyouOrderResponseTest {

  private static BitcointoyouOrderResponse bitcointoyouOrderResponse;
  private static BitcointoyouOrderResponse bitcointoyouOrderResponseMultipleOrders;
  private static BitcointoyouOrderResponse bitcointoyouOrderResponseError;

  @BeforeClass
  public static void setUp() throws Exception {
    bitcointoyouOrderResponse = loadBitcointoyouOrderResponseFromExampleData();
    bitcointoyouOrderResponseMultipleOrders =
        loadBitcointoyouOrderResponseMultipleOrdersFromExampleData();
    bitcointoyouOrderResponseError = loadBitcointoyouOrderResponseErrorFromExampleData();
  }

  private static BitcointoyouOrderResponse loadBitcointoyouOrderResponseFromExampleData()
      throws IOException {

    return loadBitcointoyouOrderResponse(
        "/org/knowm/xchange/bitcointoyou/dto/trade/example-single-order-response-data.json");
  }

  private static BitcointoyouOrderResponse
      loadBitcointoyouOrderResponseMultipleOrdersFromExampleData() throws IOException {

    return loadBitcointoyouOrderResponse(
        "/org/knowm/xchange/bitcointoyou/dto/trade/example-multiple-orders-response-data.json");
  }

  private static BitcointoyouOrderResponse loadBitcointoyouOrderResponseErrorFromExampleData()
      throws IOException {

    return loadBitcointoyouOrderResponse(
        "/org/knowm/xchange/bitcointoyou/dto/trade/example-order-response-data-error.json");
  }

  private static BitcointoyouOrderResponse loadBitcointoyouOrderResponse(String resource)
      throws IOException {
    InputStream is = BitcointoyouAdaptersTest.class.getResourceAsStream(resource);

    ObjectMapper mapper = new ObjectMapper();
    // 'oReturn' field can be either an object or a String or an array... This feature handle this.
    mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
    return mapper.readValue(is, BitcointoyouOrderResponse.class);
  }

  @Test
  public void testOrderResponse() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(bitcointoyouOrderResponse).isNotNull();
    softly.assertThat(bitcointoyouOrderResponse.getSuccess()).isEqualTo("1");

    BitcointoyouOrderInfo orderInfo = bitcointoyouOrderResponse.getOrderList().get(0);
    softly.assertThat(orderInfo).isNotNull();
    softly.assertThat(orderInfo.getAsset()).isEqualTo("BTC");
    softly.assertThat(orderInfo.getCurrency()).isEqualTo("BRL");
    softly.assertThat(orderInfo.getId()).isEqualTo("67233");
    softly.assertThat(orderInfo.getAction()).isEqualTo("buy");
    softly.assertThat(orderInfo.getStatus()).isEqualTo("canceled");
    softly.assertThat(orderInfo.getPrice()).isEqualTo(new BigDecimal("500.9800000000"));
    softly.assertThat(orderInfo.getAmount()).isEqualTo(new BigDecimal("0.01000000"));
    softly.assertThat(orderInfo.getExecutedPriceAverage()).isEqualTo("0.00000000");
    softly.assertThat(orderInfo.getExecutedAmount()).isEqualTo("0.00000000");
    softly.assertThat(orderInfo.getDateCreated()).isEqualTo("2014-10-09 14:07:52");

    softly.assertThat(bitcointoyouOrderResponse.getDate()).isEqualTo("2014-10-09 14:14:04.543");
    softly.assertThat(bitcointoyouOrderResponse.getTimestamp()).isEqualTo("1412864044");

    softly.assertAll();
  }

  @Test
  public void testOrderResponseMultipleOrders() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(bitcointoyouOrderResponseMultipleOrders).isNotNull();
    softly.assertThat(bitcointoyouOrderResponseMultipleOrders.getSuccess()).isEqualTo("1");
    softly.assertThat(bitcointoyouOrderResponseMultipleOrders.getOrderList()).size().isEqualTo(11);

    BitcointoyouOrderInfo orderInfo = bitcointoyouOrderResponseMultipleOrders.getOrderList().get(0);
    softly.assertThat(orderInfo).isNotNull();
    softly.assertThat(orderInfo.getAsset()).isEqualTo("BTC");
    softly.assertThat(orderInfo.getCurrency()).isEqualTo("BRL");
    softly.assertThat(orderInfo.getId()).isEqualTo("2262102");
    softly.assertThat(orderInfo.getAction()).isEqualTo("buy");
    softly.assertThat(orderInfo.getStatus()).isEqualTo("EXECUTED");
    softly.assertThat(orderInfo.getPrice()).isEqualTo(new BigDecimal("2669.000000000000000"));
    softly.assertThat(orderInfo.getAmount()).isEqualTo(new BigDecimal("0.096589707883710"));
    softly.assertThat(orderInfo.getExecutedPriceAverage()).isEqualTo("257.797930341622795");
    softly.assertThat(orderInfo.getExecutedAmount()).isEqualTo("0.096589707883710");
    softly.assertThat(orderInfo.getDateCreated()).isEqualTo("2016-12-20 12:37:36.750");

    softly
        .assertThat(bitcointoyouOrderResponseMultipleOrders.getDate())
        .isEqualTo("2018-01-15 12:48:41.700");
    softly
        .assertThat(bitcointoyouOrderResponseMultipleOrders.getTimestamp())
        .isEqualTo("1516038521");

    softly.assertAll();
  }

  @Test
  public void testOrderResponseError() throws Exception {

    final SoftAssertions softly = new SoftAssertions();

    softly.assertThat(bitcointoyouOrderResponseError).isNotNull();
    softly.assertThat(bitcointoyouOrderResponseError.getSuccess()).isEqualTo("0");
    softly
        .assertThat(bitcointoyouOrderResponseError.getDate())
        .isEqualTo("2014-10-09 14:14:04.543");
    softly.assertThat(bitcointoyouOrderResponseError.getTimestamp()).isEqualTo("1412864044");

    softly.assertAll();
  }
}
