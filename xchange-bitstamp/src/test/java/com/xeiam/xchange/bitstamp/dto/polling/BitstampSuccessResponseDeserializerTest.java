package com.xeiam.xchange.bitstamp.dto.polling;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * @author gnandiga
 */
public class BitstampSuccessResponseDeserializerTest
{
    @Test
    public void verifyDeserialize() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        BitstampSuccessResponse response =  mapper.readValue("{\"error\": \"User not verified\"}", BitstampSuccessResponse.class);

        Assert.assertEquals("User not verified", response.getError());

        response =  mapper.readValue("true", BitstampSuccessResponse.class);

        Assert.assertTrue(response.getSuccess());

    }
}
