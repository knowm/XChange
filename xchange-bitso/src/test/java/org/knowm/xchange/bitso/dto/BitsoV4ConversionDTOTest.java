package org.knowm.xchange.bitso.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionExecutionResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionQuoteRequest;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionQuoteResponse;
import org.knowm.xchange.bitso.dto.trade.BitsoConversionStatusResponse;

/** Tests for Bitso v4 Currency Conversion DTOs */
class BitsoV4ConversionDTOTest {

  private final ObjectMapper mapper = BitsoJacksonObjectMapperFactory.getInstance();

  @Test
  void conversionQuoteRequest() {
    // Test builder pattern with all fields
    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from("mxn")
            .to("usd")
            .amount(new BigDecimal("1854.21860516"))
            .amountType("exact_in")
            .build();

    assertThat(request).isNotNull();
    assertThat(request.getFrom()).isEqualTo("mxn");
    assertThat(request.getTo()).isEqualTo("usd");
    assertThat(request.getAmount()).isEqualTo(new BigDecimal("1854.21860516"));
    assertThat(request.getAmountType()).isEqualTo("exact_in");
  }

  @Test
  void conversionQuoteRequestMinimal() {
    // Test builder pattern with minimal required fields
    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from("btc")
            .to("mxn")
            .amount(new BigDecimal("0.1"))
            .build();

    assertThat(request).isNotNull();
    assertThat(request.getFrom()).isEqualTo("btc");
    assertThat(request.getTo()).isEqualTo("mxn");
    assertThat(request.getAmount()).isEqualTo(new BigDecimal("0.1"));
  }

  @Test
  void conversionQuoteResponseDeserialization() throws Exception {
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

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("quote_123456");
    assertThat(response.getFromAmount()).isEqualTo(new BigDecimal("1854.21860516"));
    assertThat(response.getFromCurrency()).isEqualTo("mxn");
    assertThat(response.getToAmount()).isEqualTo(new BigDecimal("100.00000000"));
    assertThat(response.getToCurrency()).isEqualTo("usd");
    assertThat(response.getCreated()).isEqualTo(Long.valueOf(1719862355209L));
    assertThat(response.getExpires()).isEqualTo(Long.valueOf(1719862385209L));
    assertThat(response.getRate()).isEqualTo(new BigDecimal("18.54218605"));
    assertThat(response.getPlainRate()).isEqualTo(new BigDecimal("18.36"));
    assertThat(response.getRateCurrency()).isEqualTo("mxn");
    assertThat(response.getBook()).isEqualTo("usd_mxn");
  }

  @Test
  void conversionExecutionResponseDeserialization() throws Exception {
    String json = "{\n" + "  \"oid\": \"conversion_789012\"\n" + "}";

    BitsoConversionExecutionResponse response =
        mapper.readValue(json, BitsoConversionExecutionResponse.class);

    assertThat(response).isNotNull();
    assertThat(response.getConversionId()).isEqualTo("conversion_789012");
  }

  @Test
  void conversionStatusResponseDeserialization() throws Exception {
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

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("7316");
    assertThat(response.getFromAmount()).isEqualTo(new BigDecimal("1854.21860516"));
    assertThat(response.getFromCurrency()).isEqualTo("mxn");
    assertThat(response.getToAmount()).isEqualTo(new BigDecimal("100.00000000"));
    assertThat(response.getToCurrency()).isEqualTo("usd");
    assertThat(response.getCreated()).isEqualTo(Long.valueOf(1719862355209L));
    assertThat(response.getExpires()).isEqualTo(Long.valueOf(1719862385209L));
    assertThat(response.getRate()).isEqualTo(new BigDecimal("18.54218605"));
    assertThat(response.getPlainRate()).isEqualTo(new BigDecimal("18.36"));
    assertThat(response.getRateCurrency()).isEqualTo("mxn");
    assertThat(response.getBook()).isEqualTo("xrp_mxn");
    assertThat(response.getStatus()).isEqualTo("completed");
    assertThat(response.getStatusEnum())
        .isEqualTo(BitsoConversionStatusResponse.ConversionStatus.COMPLETED);
  }

  @Test
  void conversionStatusResponseQueuedState() throws Exception {
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

    assertThat(response).isNotNull();
    assertThat(response.getId()).isEqualTo("7317");
    assertThat(response.getStatus()).isEqualTo("queued");
    assertThat(response.getStatusEnum())
        .isEqualTo(BitsoConversionStatusResponse.ConversionStatus.QUEUED);
  }

  @Test
  void conversionStatusEnumFromString() {
    assertThat(BitsoConversionStatusResponse.ConversionStatus.fromString("open"))
        .isEqualTo(BitsoConversionStatusResponse.ConversionStatus.OPEN);
    assertThat(BitsoConversionStatusResponse.ConversionStatus.fromString("queued"))
        .isEqualTo(BitsoConversionStatusResponse.ConversionStatus.QUEUED);
    assertThat(BitsoConversionStatusResponse.ConversionStatus.fromString("completed"))
        .isEqualTo(BitsoConversionStatusResponse.ConversionStatus.COMPLETED);
    assertThat(BitsoConversionStatusResponse.ConversionStatus.fromString("failed"))
        .isEqualTo(BitsoConversionStatusResponse.ConversionStatus.FAILED);
  }

  @Test
  void conversionStatusEnumInvalidString() {
    assertThatExceptionOfType(IllegalArgumentException.class)
        .isThrownBy(
            () -> BitsoConversionStatusResponse.ConversionStatus.fromString("invalid_status"));
  }

  @Test
  void conversionStatusEnumGetApiValue() {
    assertThat(BitsoConversionStatusResponse.ConversionStatus.OPEN.getApiValue()).isEqualTo("open");
    assertThat(BitsoConversionStatusResponse.ConversionStatus.QUEUED.getApiValue())
        .isEqualTo("queued");
    assertThat(BitsoConversionStatusResponse.ConversionStatus.COMPLETED.getApiValue())
        .isEqualTo("completed");
    assertThat(BitsoConversionStatusResponse.ConversionStatus.FAILED.getApiValue())
        .isEqualTo("failed");
  }

  @Test
  void conversionStatusResponseAllStates() throws Exception {
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
      assertThat(response).isNotNull();
      assertThat(response.getStatus()).isEqualTo(state);

      // Verify enum conversion works for all states
      BitsoConversionStatusResponse.ConversionStatus statusEnum = response.getStatusEnum();
      assertThat(statusEnum).isNotNull();
      assertThat(statusEnum.getApiValue()).isEqualTo(state);
    }
  }

  @Test
  void conversionQuoteRequestSerialization() throws Exception {
    BitsoConversionQuoteRequest request =
        BitsoConversionQuoteRequest.builder()
            .from("btc")
            .to("mxn")
            .amount(new BigDecimal("0.5"))
            .amountType("exact_in")
            .build();

    String json = mapper.writeValueAsString(request);

    System.out.println(json);

    assertThat(json.contains("\"from\":\"btc\"")).isTrue();
    assertThat(json.contains("\"to\":\"mxn\"")).isTrue();
    assertThat(json.contains("\"amount\":0.5")).isTrue();
    assertThat(json.contains("\"amount_type\":\"exact_in\"")).isTrue();
  }

  @Test
  void lombokFunctionality() {
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
    assertThat(request2).isEqualTo(request1);
    assertThat(request2.hashCode()).isEqualTo(request1.hashCode());

    // Test toString
    String toString = request1.toString();
    assertThat(toString.contains("btc")).isTrue();
    assertThat(toString.contains("mxn")).isTrue();
    assertThat(toString.contains("1.0")).isTrue();
  }
}
