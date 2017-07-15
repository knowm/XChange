package org.knowm.xchange.dsx.dto.account;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Test DSXAccountInfo JSON parsing
 *
 * @author Mikhail Wall
 */
public class DSXAccountInfoJSONTest {

  @Test
  public void testUnmarshal() throws IOException {

    InputStream is = DSXAccountInfoJSONTest.class.getResourceAsStream("/account/example-account-info-data.json");

    ObjectMapper mapper = new ObjectMapper();
    DSXAccountInfoReturn ai = mapper.readValue(is, DSXAccountInfoReturn.class);

    assertThat(ai.getReturnValue().getRights().isInfo()).isTrue();
    assertThat(ai.getReturnValue().getFunds().get("BTC")).isEqualsToByComparingFields(new DSXCurrencyAmount(new BigDecimal("100"), new BigDecimal("95")));
    assertThat(ai.getReturnValue().getFunds().get("USD")).isEqualsToByComparingFields(new DSXCurrencyAmount(new BigDecimal("10000"), new BigDecimal("9995")));
  }
}
