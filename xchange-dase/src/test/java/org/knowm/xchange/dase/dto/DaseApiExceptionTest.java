package org.knowm.xchange.dase.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.knowm.xchange.dase.DaseException;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

public class DaseApiExceptionTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  public void deserialize_error_response() throws Exception {
    String json = "{\"type\":\"InsufficientFunds\",\"message\":\"Not enough balance\"}";

    DaseApiException ex = MAPPER.readValue(json, DaseApiException.class);

    assertThat(ex).isNotNull();
    assertThat(ex.getType()).isEqualTo("InsufficientFunds");
    assertThat(ex.getMessage()).isEqualTo("Not enough balance");
  }

  @Test
  public void toExchangeException_maps_insufficient_funds() {
    DaseApiException apiEx = new DaseApiException("InsufficientFunds", "Not enough BTC");

    ExchangeException ex = apiEx.toExchangeException();

    assertThat(ex).isInstanceOf(FundsExceededException.class);
    assertThat(ex.getMessage()).isEqualTo("Not enough BTC");
  }

  @Test
  public void toExchangeException_maps_unauthorized() {
    DaseApiException apiEx = new DaseApiException("Unauthorized", "Invalid API key");

    ExchangeException ex = apiEx.toExchangeException();

    assertThat(ex).isInstanceOf(ExchangeSecurityException.class);
    assertThat(ex.getMessage()).isEqualTo("Invalid API key");
  }

  @Test
  public void toExchangeException_maps_rate_limit() {
    DaseApiException apiEx = new DaseApiException("TooManyRequests", "Rate limit exceeded");

    ExchangeException ex = apiEx.toExchangeException();

    assertThat(ex).isInstanceOf(RateLimitExceededException.class);
    assertThat(ex.getMessage()).isEqualTo("Rate limit exceeded");
  }

  @Test
  public void toExchangeException_maps_service_errors() {
    DaseApiException apiEx = new DaseApiException("ServiceUnavailable", "Service is down");

    ExchangeException ex = apiEx.toExchangeException();

    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("Service is down");
  }

  @Test
  public void toExchangeException_maps_invalid_input() {
    DaseApiException apiEx = new DaseApiException("InvalidInput", "Invalid size");

    ExchangeException ex = apiEx.toExchangeException();

    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("Invalid size");
  }
}
