package com.xeiam.xchange.bitstamp.dto.polling;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xeiam.xchange.bitstamp.dto.BitstampSuccessResponse;

/**
 * @author gnandiga
 */
public class BitstampSuccessResponseDeserializerTest {

  @Test
  public void verifyDeserialize() throws IOException {

    ObjectMapper mapper = new ObjectMapper();
    BitstampSuccessResponse response = mapper.readValue("{\"error\": \"User not verified\"}", BitstampSuccessResponse.class);

    Assert.assertEquals("User not verified", response.getError());

    response = mapper.readValue("true", BitstampSuccessResponse.class);

    Assert.assertTrue(response.getSuccess());

  }
}
