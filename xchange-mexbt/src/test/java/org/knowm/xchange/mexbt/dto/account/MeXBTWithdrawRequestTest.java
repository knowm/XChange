package org.knowm.xchange.mexbt.dto.account;

import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.knowm.xchange.mexbt.service.MeXBTDigest;
import org.knowm.xchange.utils.nonce.CurrentTimeNonceFactory;

import si.mazi.rescu.serialization.jackson.JacksonMapper;

public class MeXBTWithdrawRequestTest {

  @Test
  public void testMeXBTWithdrawRequest() throws JsonProcessingException {
    MeXBTWithdrawRequest request = new MeXBTWithdrawRequest("myApiKey", new CurrentTimeNonceFactory(),
        MeXBTDigest.createInstance("f223148561ecda30b98083cf02be96a7", "hello", "5a1f5a691c281b3754d8826510065ab8"), "BTC",
        new BigDecimal("0.00000001"), "testAddress");
    JacksonMapper jacksonMapper = new JacksonMapper(null);
    String json = jacksonMapper.getObjectMapper().writeValueAsString(request);
    assertTrue(json.contains("\"0.00000001\""));
  }

}
