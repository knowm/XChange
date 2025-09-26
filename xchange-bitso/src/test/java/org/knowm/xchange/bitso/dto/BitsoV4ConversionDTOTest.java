package org.knowm.xchange.bitso.dto;

import static org.junit.Assert.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionExecutionResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionQuoteRequest;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionQuoteResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionStatusResponse;

/** Tests for Bitso v4 Currency Conversion DTOs */
public class BitsoV4ConversionDTOTest {

  private final ObjectMapper mapper = BitsoJacksonObjectMapperFactory.getInstance();

  @Test
  public void testConversionQuoteRequest() {
    // Test builder pattern with all fields
    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from("mxn")
            .to("usd")
            .amount(new BigDecimal("1854.21860516"))
            .amountType("exact_in")
            .build();

    assertNotNull(request);
    assertEquals("mxn", request.getFrom());
    assertEquals("usd", request.getTo());
    assertEquals(new BigDecimal("1854.21860516"), request.getAmount());
    assertEquals("exact_in", request.getAmountType());
  }

  @Test
  public void testConversionQuoteRequestMinimal() {
    // Test builder pattern with minimal required fields
    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from("btc")
            .to("mxn")
            .amount(new BigDecimal("0.1"))
            .build();

    assertNotNull(request);
    assertEquals("btc", request.getFrom());
    assertEquals("mxn", request.getTo());
    assertEquals(new BigDecimal("0.1"), request.getAmount());
  }

  @Test
  public void testConversionQuoteResponseDeserialization() throws Exception {
    String json =
        "{\n"
            + "  \"id\": \"quote_123456\",\n"
            + "  \"from_amount\": \"1854.21860516\",\n"
            + "  \"from_currency\": \"mxn\",\n"
            + "  \"to_amount\": \"100.00000000\",\n"
            + "  \"to_currency\": \"usd\",\n"
            + "  \"created\": 1719862355209,\n"
            + "  \"expires\": 1719862385209,\n"
            + "  \"rate\": \"18.54218605\",\n"
            + "  \"plain_rate\": \"18.36\",\n"
            + "  \"rate_currency\": \"mxn\",\n"
            + "  \"book\": \"usd_mxn\"\n"
            + "}";

    BitsoConversionQuoteResponse response =
        mapper.readValue(json, BitsoConversionQuoteResponse.class);

    assertNotNull(response);
    assertEquals("quote_123456", response.getId());
    assertEquals(new BigDecimal("1854.21860516"), response.getFromAmount());
    assertEquals("mxn", response.getFromCurrency());
    assertEquals(new BigDecimal("100.00000000"), response.getToAmount());
    assertEquals("usd", response.getToCurrency());
    assertEquals(Long.valueOf(1719862355209L), response.getCreated());
    assertEquals(Long.valueOf(1719862385209L), response.getExpires());
    assertEquals(new BigDecimal("18.54218605"), response.getRate());
    assertEquals(new BigDecimal("18.36"), response.getPlainRate());
    assertEquals("mxn", response.getRateCurrency());
    assertEquals("usd_mxn", response.getBook());
  }

  @Test
  public void testConversionExecutionResponseDeserialization() throws Exception {
    String json = "{\n" + "  \"oid\": \"conversion_789012\"\n" + "}";

    BitsoConversionExecutionResponse response =
        mapper.readValue(json, BitsoConversionExecutionResponse.class);

    assertNotNull(response);
    assertEquals("conversion_789012", response.getConversionId());
  }

  @Test
  public void testConversionStatusResponseDeserialization() throws Exception {
    String json =
        "{\n"
            + "  \"id\": \"7316\",\n"
            + "  \"from_amount\": \"1854.21860516\",\n"
            + "  \"from_currency\": \"mxn\",\n"
            + "  \"to_amount\": \"100.00000000\",\n"
            + "  \"to_currency\": \"usd\",\n"
            + "  \"created\": 1719862355209,\n"
            + "  \"expires\": 1719862385209,\n"
            + "  \"rate\": \"18.54218605\",\n"
            + "  \"plain_rate\": \"18.36\",\n"
            + "  \"rate_currency\": \"mxn\",\n"
            + "  \"book\": \"xrp_mxn\",\n"
            + "  \"status\": \"completed\"\n"
            + "}";

    BitsoConversionStatusResponse response =
        mapper.readValue(json, BitsoConversionStatusResponse.class);

    assertNotNull(response);
    assertEquals("7316", response.getId());
    assertEquals(new BigDecimal("1854.21860516"), response.getFromAmount());
    assertEquals("mxn", response.getFromCurrency());
    assertEquals(new BigDecimal("100.00000000"), response.getToAmount());
    assertEquals("usd", response.getToCurrency());
    assertEquals(Long.valueOf(1719862355209L), response.getCreated());
    assertEquals(Long.valueOf(1719862385209L), response.getExpires());
    assertEquals(new BigDecimal("18.54218605"), response.getRate());
    assertEquals(new BigDecimal("18.36"), response.getPlainRate());
    assertEquals("mxn", response.getRateCurrency());
    assertEquals("xrp_mxn", response.getBook());
    assertEquals("completed", response.getStatus());
    assertEquals(
        BitsoConversionStatusResponse.ConversionStatus.COMPLETED, response.getStatusEnum());
  }

  @Test
  public void testConversionStatusResponseQueuedState() throws Exception {
    String json =
        "{\n"
            + "  \"id\": \"7317\",\n"
            + "  \"from_amount\": \"500.00000000\",\n"
            + "  \"from_currency\": \"usd\",\n"
            + "  \"to_amount\": \"0.02500000\",\n"
            + "  \"to_currency\": \"btc\",\n"
            + "  \"created\": 1719862355209,\n"
            + "  \"expires\": 1719862385209,\n"
            + "  \"rate\": \"20000.00000000\",\n"
            + "  \"plain_rate\": \"19800.00\",\n"
            + "  \"rate_currency\": \"usd\",\n"
            + "  \"book\": \"btc_usd\",\n"
            + "  \"status\": \"queued\"\n"
            + "}";

    BitsoConversionStatusResponse response =
        mapper.readValue(json, BitsoConversionStatusResponse.class);

    assertNotNull(response);
    assertEquals("7317", response.getId());
    assertEquals("queued", response.getStatus());
    assertEquals(BitsoConversionStatusResponse.ConversionStatus.QUEUED, response.getStatusEnum());
  }

  @Test
  public void testConversionStatusEnumFromString() {
    assertEquals(
        BitsoConversionStatusResponse.ConversionStatus.OPEN,
        BitsoConversionStatusResponse.ConversionStatus.fromString("open"));
    assertEquals(
        BitsoConversionStatusResponse.ConversionStatus.QUEUED,
        BitsoConversionStatusResponse.ConversionStatus.fromString("queued"));
    assertEquals(
        BitsoConversionStatusResponse.ConversionStatus.COMPLETED,
        BitsoConversionStatusResponse.ConversionStatus.fromString("completed"));
    assertEquals(
        BitsoConversionStatusResponse.ConversionStatus.FAILED,
        BitsoConversionStatusResponse.ConversionStatus.fromString("failed"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConversionStatusEnumInvalidString() {
    BitsoConversionStatusResponse.ConversionStatus.fromString("invalid_status");
  }

  @Test
  public void testConversionStatusEnumGetApiValue() {
    assertEquals("open", BitsoConversionStatusResponse.ConversionStatus.OPEN.getApiValue());
    assertEquals("queued", BitsoConversionStatusResponse.ConversionStatus.QUEUED.getApiValue());
    assertEquals(
        "completed", BitsoConversionStatusResponse.ConversionStatus.COMPLETED.getApiValue());
    assertEquals("failed", BitsoConversionStatusResponse.ConversionStatus.FAILED.getApiValue());
  }

  @Test
  public void testConversionStatusResponseAllStates() throws Exception {
    // Test all possible conversion states
    String[] states = {"open", "queued", "completed", "failed"};

    for (String state : states) {
      String json =
          "{\n"
              + "  \"id\": \"test_id\",\n"
              + "  \"from_amount\": \"100.00\",\n"
              + "  \"from_currency\": \"mxn\",\n"
              + "  \"to_amount\": \"5.00\",\n"
              + "  \"to_currency\": \"usd\",\n"
              + "  \"created\": 1719862355209,\n"
              + "  \"expires\": 1719862385209,\n"
              + "  \"rate\": \"20.00\",\n"
              + "  \"plain_rate\": \"19.80\",\n"
              + "  \"rate_currency\": \"mxn\",\n"
              + "  \"book\": \"usd_mxn\",\n"
              + "  \"status\": \""
              + state
              + "\"\n"
              + "}";

      BitsoConversionStatusResponse response =
          mapper.readValue(json, BitsoConversionStatusResponse.class);
      assertNotNull(response);
      assertEquals(state, response.getStatus());

      // Verify enum conversion works for all states
      BitsoConversionStatusResponse.ConversionStatus statusEnum = response.getStatusEnum();
      assertNotNull(statusEnum);
      assertEquals(state, statusEnum.getApiValue());
    }
  }

  @Test
  public void testConversionQuoteRequestSerialization() throws Exception {
    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from("btc")
            .to("mxn")
            .amount(new BigDecimal("0.5"))
            .amountType("exact_in")
            .build();

    String json = mapper.writeValueAsString(request);

    System.out.println(json);

    assertTrue(json.contains("\"from\":\"btc\""));
    assertTrue(json.contains("\"to\":\"mxn\""));
    assertTrue(json.contains("\"amount\":0.5"));
    assertTrue(json.contains("\"amount_type\":\"exact_in\""));
  }

  @Test
  public void testLombokFunctionality() {
    // Test Lombok-generated methods
    BitsoConversionQuoteRequest request1 =
        BitsoConversionQuoteRequest.builder()
            .from("btc")
            .to("mxn")
            .amount(new BigDecimal("1.0"))
            .build();

    BitsoConversionQuoteRequest request2 =
        BitsoConversionQuoteRequest.builder()
            .from("btc")
            .to("mxn")
            .amount(new BigDecimal("1.0"))
            .build();

    // Test equals and hashCode
    assertEquals(request1, request2);
    assertEquals(request1.hashCode(), request2.hashCode());

    // Test toString
    String toString = request1.toString();
    assertTrue(toString.contains("btc"));
    assertTrue(toString.contains("mxn"));
    assertTrue(toString.contains("1.0"));
  }
}
