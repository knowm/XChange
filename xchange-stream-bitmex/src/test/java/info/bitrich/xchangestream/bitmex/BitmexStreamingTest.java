package info.bitrich.xchangestream.bitmex;

import java.io.IOException;
import org.junit.Assert;
import org.junit.Test;

/** @author Foat Akhmadeev 13/06/2018 */
public class BitmexStreamingTest {
  @Test
  public void shouldGetCorrectSubscribeMessage() throws IOException {
    BitmexStreamingService service = new BitmexStreamingService("url", "api", "secret");

    Assert.assertEquals(
        "{\"op\":\"subscribe\",\"args\":[\"name\"]}", service.getSubscribeMessage("name"));
  }

  @Test
  public void shouldGetCorrectUnsubscribeMessage() throws IOException {
    BitmexStreamingService service = new BitmexStreamingService("url", "api", "secret");

    Assert.assertEquals(
        "{\"op\":\"unsubscribe\",\"args\":[\"name\"]}", service.getUnsubscribeMessage("name"));
  }
}
