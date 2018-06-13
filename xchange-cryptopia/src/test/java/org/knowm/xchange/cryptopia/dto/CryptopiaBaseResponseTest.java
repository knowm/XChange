package org.knowm.xchange.cryptopia.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.Test;
import si.mazi.rescu.ExceptionalReturnContentException;

/**
 * =
 *
 * @author walec51
 */
public class CryptopiaBaseResponseTest {

  // yes, Cryptopia actually does that in some situations
  @Test
  public void testConstructor_successfulWithErrorMessage() {
    Throwable exception =
        catchThrowable(() -> new CryptopiaBaseResponse<>(true, null, null, "Some error msg"));
    assertThat(exception).isInstanceOf(ExceptionalReturnContentException.class);
  }
}
