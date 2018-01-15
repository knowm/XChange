package org.knowm.xchange.abucoins.dto;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.knowm.xchange.abucoins.AbucoinsAdapters;
import org.knowm.xchange.abucoins.dto.trade.AbucoinsOrder;
import org.knowm.xchange.currency.CurrencyPair;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

public class AbucoinsCreateLimitOrderRequestSkipsOptionalTest {
  AbucoinsCreateLimitOrderRequest request;
  ObjectMapper objectMapper;
        
  @Before
  public void setUp() throws Exception {
    objectMapper = new ObjectMapper();
    SimpleModule module = new SimpleModule();
    module.addSerializer(BigDecimal.class, new ToStringSerializer());
    objectMapper.registerModule(module);
  }
        
  @Test
  public void testSkipsOptionalValuesWhenNull() throws Exception {
    request = new AbucoinsCreateLimitOrderRequest(AbucoinsOrder.Side.buy, AbucoinsAdapters.adaptCurrencyPairToProductID(CurrencyPair.BTC_USD), new BigDecimal("500"), new BigDecimal("2"));
    String s = objectMapper.writeValueAsString(request);
    assertNotNull("String is null", s);
    assertFalse("Contains optional stp", s.indexOf("stp") != -1);
    assertFalse("Contains optional hidden", s.indexOf("hidden") != -1);
    assertFalse("Contains optional time_in_force", s.indexOf("time_in_force") != -1);
    assertFalse("Contains optional cancel_after", s.indexOf("cancel_after") != -1);
    assertFalse("Contains optional post_only", s.indexOf("post_only") != -1);
  }
        
  @Test
  public void testIncludesOptionalValues() throws Exception {
    request = new AbucoinsCreateLimitOrderRequest(AbucoinsOrder.Side.buy,
                                                  AbucoinsAdapters.adaptCurrencyPairToProductID(CurrencyPair.BTC_USD),
                                                  "co",
                                                  true,
                                                  new BigDecimal("500"),
                                                  new BigDecimal("2"),
                                                  AbucoinsOrder.TimeInForce.FOK,
                                                  null,
                                                  null);
    String s = objectMapper.writeValueAsString(request);
    assertNotNull("String is null", s);
    assertTrue("Contains optional stp", s.indexOf("stp") != -1);
    assertTrue("Contains optional hidden", s.indexOf("hidden") != -1);
    assertTrue("Contains optional time_in_force", s.indexOf("time_in_force") != -1);
    assertFalse("Contains optional cancel_after", s.indexOf("cancel_after") != -1);
    assertFalse("Contains optional post_only", s.indexOf("post_only") != -1);
  }
}
