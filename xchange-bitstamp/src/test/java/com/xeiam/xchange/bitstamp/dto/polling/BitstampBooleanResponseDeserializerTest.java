package com.xeiam.xchange.bitstamp.dto.polling;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBooleanResponse;

/**
 * @author gnandiga
 */
public class BitstampBooleanResponseDeserializerTest {

  @Test
  public void verifyDeserialize() throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    BitstampBooleanResponse response = mapper.readValue("{\"error\": \"User not verified\"}", BitstampBooleanResponse.class);

    Assert.assertEquals("User not verified", response.getError());

    response = mapper.readValue("true", BitstampBooleanResponse.class);

    Assert.assertTrue(response.getResponse());

  }
}
