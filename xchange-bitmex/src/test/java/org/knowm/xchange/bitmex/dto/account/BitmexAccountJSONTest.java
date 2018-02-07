package org.knowm.xchange.bitmex.dto.account;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Test;
import org.knowm.xchange.bitmex.util.JSON;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test BitstampTicker JSON parsing
 */
public class BitmexAccountJSONTest {

    @Test
    public void testUnmarshal() throws IOException {

        // Read in the JSON from the example resources
        BitmexAccount bitmexAccount;
        try (InputStream is = BitmexAccountJSONTest.class.getResourceAsStream("/account/example-account.json"); InputStreamReader reader = new InputStreamReader(is);  ) { Gson jsonReader = new JSON().getGson();
            bitmexAccount=jsonReader.       fromJson(reader, BitmexAccount.class);
        }



    // Verify that the example data was unmarshalled correctly
    assertThat(bitmexAccount.getFirstname()).

    isEqualTo("string");

    assertThat(bitmexAccount.getLastname()).

    isEqualTo("string");

    assertThat(bitmexAccount.getId()).

    isEqualTo(0);

    assertThat(bitmexAccount.getOwnerId()).

    isEqualTo(0);

    assertThat(bitmexAccount.getEmail()).

    isEqualTo("string");

    assertThat(bitmexAccount.getUsername()).

    isEqualTo("string");

    assertThat(bitmexAccount.getPhone()).

    isEqualTo("string");
}

}