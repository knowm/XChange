package com.xeiam.xchange.bitmarket.dto;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import static org.fest.assertions.api.Assertions.assertThat;

public class BitMarketDtoTestSupport {
  private static ObjectMapper mapper = new ObjectMapper();

  protected <E> E parse(String baseName, Class<E> type) throws IOException {
    InputStream is = getStream(baseName);
    return mapper.readValue(is, type);
  }

  protected InputStream getStream(String baseName) {
    return BitMarketDtoTestSupport.class.getResourceAsStream(String.format("/%s.json", baseName));
  }

  protected <T extends BitMarketBaseResponse> void verifyErrorResponse(Class<T> responseType) throws IOException {
    // when
    T response = parse("example-error", responseType);

    // then
    assertThat(response.getSuccess()).isFalse();
    assertThat(response.getError()).isEqualTo(502);
    assertThat(response.getErrorMsg()).isEqualTo("Invalid message hash");
    assertThat(response.getData()).isNull();
    assertThat(response.getLimit()).isNull();
  }

  protected void verifyResponseLimit(BitMarketAPILimit limit, int used, int allowed, long expires) {
    assertThat(limit.getUsed()).isEqualTo(used);
    assertThat(limit.getAllowed()).isEqualTo(allowed);
    assertThat(limit.getExpires()).isEqualTo(expires);
  }
}
