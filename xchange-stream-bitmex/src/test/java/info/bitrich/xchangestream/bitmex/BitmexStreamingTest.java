package info.bitrich.xchangestream.bitmex;

import java.io.IOException;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

/** @author Foat Akhmadeev 13/06/2018 */
public class BitmexStreamingTest {
  @Test
  public void shouldGetCorrectSubscribeMessage() throws IOException, JSONException {
    BitmexStreamingService service = new BitmexStreamingService("url", "api", "secret");

    JSONAssert.assertEquals(
        "{\"op\":\"subscribe\",\"args\":[\"name\"]}", service.getSubscribeMessage("name"), JSONCompareMode.NON_EXTENSIBLE);
  }

  @Test
  public void shouldGetCorrectUnsubscribeMessage() throws IOException, JSONException {
    BitmexStreamingService service = new BitmexStreamingService("url", "api", "secret");

    JSONAssert.assertEquals(
        "{\"op\":\"unsubscribe\",\"args\":[\"name\"]}", service.getUnsubscribeMessage("name"), JSONCompareMode.NON_EXTENSIBLE);
  }
}
