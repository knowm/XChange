package com.xeiam.xchange.justcoin.service.trade;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.justcoin.dto.PostCreateResponse;
import com.xeiam.xchange.justcoin.service.marketdata.JustcoinDepthTest;

/**
 * @author jamespedwards42
 */
public class JustcoinPostCreateResponseJsonTest {

  @Test
  public void testUnmarshal() throws IOException {

    // Read in the JSON from the example resources
    final InputStream is = JustcoinDepthTest.class.getResourceAsStream("/trade/example-post-create-response-data.json");

    // Use Jackson to parse it
    final ObjectMapper mapper = new ObjectMapper();
    final PostCreateResponse response = mapper.readValue(is, PostCreateResponse.class);

    assertThat(response.getId()).isEqualTo("1895549");
  }
}
