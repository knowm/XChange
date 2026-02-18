package org.knowm.xchange.dase.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

public class DaseErrorResponseTest {

  private static final ObjectMapper MAPPER = new ObjectMapper();

  @Test
  public void deserialize_error_response() throws Exception {
    String json = "{\"type\":\"InsufficientFunds\",\"message\":\"Not enough balance\"}";

    DaseErrorResponse response = MAPPER.readValue(json, DaseErrorResponse.class);

    assertNotNull(response);
    assertEquals("InsufficientFunds", response.getType());
    assertEquals("Not enough balance", response.getMessage());
  }

  @Test
  public void deserialize_error_response_with_null_message() throws Exception {
    String json = "{\"type\":\"NotFound\",\"message\":null}";

    DaseErrorResponse response = MAPPER.readValue(json, DaseErrorResponse.class);

    assertNotNull(response);
    assertEquals("NotFound", response.getType());
    assertEquals(null, response.getMessage());
  }

  @Test
  public void toString_includes_type_and_message() {
    DaseErrorResponse response = new DaseErrorResponse("Unauthorized", "Invalid API key");

    String result = response.toString();

    assertNotNull(result);
    assertEquals("DaseErrorResponse{type='Unauthorized', message='Invalid API key'}", result);
  }
}
