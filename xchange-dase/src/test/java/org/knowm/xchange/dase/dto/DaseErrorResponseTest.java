package org.knowm.xchange.dase.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class DaseErrorResponseTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  void deserialize_error_response() throws Exception {
    String json = "{\"type\":\"InsufficientFunds\",\"message\":\"Not enough balance\"}";

    DaseErrorResponse response = MAPPER.readValue(json, DaseErrorResponse.class);

    assertThat(response).isNotNull();
    assertThat(response.getType()).isEqualTo("InsufficientFunds");
    assertThat(response.getMessage()).isEqualTo("Not enough balance");
  }

  @Test
  void deserialize_error_response_with_null_message() throws Exception {
    String json = "{\"type\":\"NotFound\",\"message\":null}";

    DaseErrorResponse response = MAPPER.readValue(json, DaseErrorResponse.class);

    assertThat(response).isNotNull();
    assertThat(response.getType()).isEqualTo("NotFound");
    assertThat(response.getMessage()).isNull();
  }

  @Test
  void toString_includes_type_and_message() {
    DaseErrorResponse response = new DaseErrorResponse("Unauthorized", "Invalid API key");

    String result = response.toString();

    assertThat(result).isNotNull();
    assertThat(result)
        .isEqualTo("DaseErrorResponse{type='Unauthorized', message='Invalid API key'}");
  }
}
