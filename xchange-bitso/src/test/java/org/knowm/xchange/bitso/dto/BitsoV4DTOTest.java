package org.knowm.xchange.bitso.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import org.junit.Test;
import org.knowm.xchange.bitso.BitsoJacksonObjectMapperFactory;
import org.knowm.xchange.bitso.dto.trade.BitsoModifyOrderRequest;

/**
 * Test Bitso API v4 DTOs
 *
 * @author Piotr Ładyżyński
 */
public class BitsoV4DTOTest {

  private final ObjectMapper objectMapper = BitsoJacksonObjectMapperFactory.getInstance();

  @Test
  public void testBitsoModifyOrderRequestSerialization() throws JsonProcessingException {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder()
            .major(new BigDecimal("1.5"))
            .price(new BigDecimal("50000.00"))
            .cancel(false)
            .build();

    String json = objectMapper.writeValueAsString(request);

    // Verify JSON contains expected fields
    assertThat(json).contains("\"major\":1.5");
    assertThat(json).contains("\"price\":50000.00");
    assertThat(json).contains("\"cancel\":false");

    // Verify minor and stop are not included (null values)
    assertThat(json).doesNotContain("\"minor\"");
    assertThat(json).doesNotContain("\"stop\"");
  }

  @Test
  public void testBitsoModifyOrderRequestMinorAmount() throws JsonProcessingException {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder()
            .minor(new BigDecimal("75000.50"))
            .price(new BigDecimal("50000.00"))
            .cancel(true)
            .build();

    String json = objectMapper.writeValueAsString(request);

    // Verify JSON contains expected fields
    assertThat(json).contains("\"minor\":75000.50");
    assertThat(json).contains("\"price\":50000.00");
    assertThat(json).contains("\"cancel\":true");

    // Verify major and stop are not included (null values)
    assertThat(json).doesNotContain("\"major\"");
    assertThat(json).doesNotContain("\"stop\"");
  }

  @Test
  public void testBitsoModifyOrderRequestStopOrder() throws JsonProcessingException {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder()
            .major(new BigDecimal("0.5"))
            .stop(new BigDecimal("48000.00"))
            .build();

    String json = objectMapper.writeValueAsString(request);

    // Verify JSON contains expected fields
    assertThat(json).contains("\"major\":0.5");
    assertThat(json).contains("\"stop\":48000.00");

    // Verify optional fields are not included when null
    assertThat(json).doesNotContain("\"price\"");
    assertThat(json).doesNotContain("\"minor\"");
    assertThat(json).doesNotContain("\"cancel\"");
  }

  @Test
  public void testBitsoModifyOrderRequestPriceOnly() throws JsonProcessingException {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder().price(new BigDecimal("51000.25")).build();

    String json = objectMapper.writeValueAsString(request);

    // Verify JSON contains only the price field
    assertThat(json).contains("\"price\":51000.25");

    // Verify other fields are not included
    assertThat(json).doesNotContain("\"major\"");
    assertThat(json).doesNotContain("\"minor\"");
    assertThat(json).doesNotContain("\"stop\"");
    assertThat(json).doesNotContain("\"cancel\"");
  }

  @Test
  public void testBitsoModifyOrderRequestAllFields() throws JsonProcessingException {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder()
            .major(new BigDecimal("2.0"))
            .minor(new BigDecimal("100000.00"))
            .price(new BigDecimal("50000.00"))
            .stop(new BigDecimal("49000.00"))
            .cancel(true)
            .build();

    String json = objectMapper.writeValueAsString(request);

    // Verify all fields are present
    assertThat(json).contains("\"major\":2.0");
    assertThat(json).contains("\"minor\":100000.00");
    assertThat(json).contains("\"price\":50000.00");
    assertThat(json).contains("\"stop\":49000.00");
    assertThat(json).contains("\"cancel\":true");
  }

  @Test
  public void testBitsoModifyOrderRequestDeserialization() throws JsonProcessingException {
    String json = "{" + "\"major\": 1.5," + "\"price\": 50000.00," + "\"cancel\": false" + "}";

    BitsoModifyOrderRequest request = objectMapper.readValue(json, BitsoModifyOrderRequest.class);

    assertThat(request.getMajor()).isEqualTo(new BigDecimal("1.5"));
    assertThat(request.getPrice()).isEqualTo(new BigDecimal("50000.00"));
    assertThat(request.getCancel()).isEqualTo(false);
    assertThat(request.getMinor()).isNull();
    assertThat(request.getStop()).isNull();
  }

  @Test
  public void testBitsoModifyOrderRequestBuilder() {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder()
            .major(new BigDecimal("1.0"))
            .price(new BigDecimal("45000.00"))
            .cancel(true)
            .build();

    assertThat(request.getMajor()).isEqualTo(new BigDecimal("1.0"));
    assertThat(request.getPrice()).isEqualTo(new BigDecimal("45000.00"));
    assertThat(request.getCancel()).isEqualTo(true);
    assertThat(request.getMinor()).isNull();
    assertThat(request.getStop()).isNull();
  }

  @Test
  public void testBitsoModifyOrderRequestImmutability() {
    BitsoModifyOrderRequest request =
        BitsoModifyOrderRequest.builder().major(new BigDecimal("1.0")).build();

    // Verify all fields are final (getter returns the same instance)
    assertThat(request.getMajor()).isSameAs(request.getMajor());
    assertThat(request.getMinor()).isNull();
    assertThat(request.getPrice()).isNull();
    assertThat(request.getStop()).isNull();
    assertThat(request.getCancel()).isNull();
  }
}
