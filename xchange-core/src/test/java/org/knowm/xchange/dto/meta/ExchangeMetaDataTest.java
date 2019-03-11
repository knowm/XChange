package org.knowm.xchange.dto.meta;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ExchangeMetaDataTest {

  /** 1 call per second => 1000ms delay */
  @Test
  public void testGetPollDelayMillis1000() {
    RateLimit limit = new RateLimit(1, 1, SECONDS);
    assertEquals(1000L, limit.getPollDelayMillis());
  }

  /** 2 calls per second => 500ms delay */
  @Test
  public void testGetPollDelayMillis500() {
    RateLimit limit = new RateLimit(2, 1, SECONDS);
    assertEquals(500L, limit.getPollDelayMillis());
  }

  /** 1 cal per second or 2 calls per second => 1000ms delay (500ms for burst calls) */
  @Test
  public void testGetPollDelayMillisMulti() {
    assertEquals(
        1000L,
        (long)
            ExchangeMetaData.getPollDelayMillis(
                new RateLimit[] {new RateLimit(2, 1, SECONDS), new RateLimit(1, 1, SECONDS)}));
  }

  /** null for an unknown value */
  @Test
  public void testGetPollDelayMillisNull() {
    assertEquals(null, ExchangeMetaData.getPollDelayMillis(null));
  }

  /** null for an unknown value */
  @Test
  public void testGetPollDelayMillisEmpty() {
    assertEquals(null, ExchangeMetaData.getPollDelayMillis(new RateLimit[0]));
  }
}
