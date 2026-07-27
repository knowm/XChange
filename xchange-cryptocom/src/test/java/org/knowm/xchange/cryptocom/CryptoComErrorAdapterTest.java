package org.knowm.xchange.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

public class CryptoComErrorAdapterTest {

  @Test
  public void testInsufficientAvailableBalance() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.INSUFFICIENT_AVAILABLE_BALANCE);
    assertThat(adapted).isInstanceOf(FundsExceededException.class);
  }

  @Test
  public void testExceedMaxTradableAmount() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.EXCEED_MAX_TRADABLE_AMOUNT);
    assertThat(adapted).isInstanceOf(FundsExceededException.class);
  }

  @Test
  public void testTooManyRequests() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.TOO_MANY_REQUESTS);
    assertThat(adapted).isInstanceOf(RateLimitExceededException.class);
  }

  @Test
  public void testInvalidNonce() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.INVALID_NONCE);
    assertThat(adapted).isInstanceOf(NonceException.class);
  }

  @Test
  public void testUnmappedCodeFallsBackToGenericException() {
    ExchangeException adapted = adaptError(999999);
    assertThat(adapted).isExactlyInstanceOf(ExchangeException.class);
    assertThat(adapted.getMessage()).contains("999999").contains("boom");
  }

  private static ExchangeException adaptError(int code) {
    CryptoComResponse response = new CryptoComResponse();
    response.setCode(code);
    response.setMessage("boom");
    return CryptoComErrorAdapter.adaptError(response);
  }
}
