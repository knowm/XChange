package org.knowm.xchange.cryptocom;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.cryptocom.dto.CryptoComException;
import org.knowm.xchange.cryptocom.dto.CryptoComResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.NonceException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

class CryptoComErrorAdapterTest {

  @Test
  void insufficientAvailableBalance() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.INSUFFICIENT_AVAILABLE_BALANCE);
    assertThat(adapted).isInstanceOf(FundsExceededException.class);
  }

  @Test
  void exceedMaxTradableAmount() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.EXCEED_MAX_TRADABLE_AMOUNT);
    assertThat(adapted).isInstanceOf(FundsExceededException.class);
  }

  @Test
  void tooManyRequests() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.TOO_MANY_REQUESTS);
    assertThat(adapted).isInstanceOf(RateLimitExceededException.class);
  }

  @Test
  void invalidNonce() {
    ExchangeException adapted = adaptError(CryptoComErrorAdapter.INVALID_NONCE);
    assertThat(adapted).isInstanceOf(NonceException.class);
  }

  @Test
  void unmappedCodeFallsBackToGenericException() {
    ExchangeException adapted = adaptError(999999);
    assertThat(adapted).isExactlyInstanceOf(ExchangeException.class);
    assertThat(adapted.getMessage()).contains("999999").contains("boom");
  }

  @Test
  void httpStatusExceptionInsufficientBalanceIsAlsoMapped() {
    ExchangeException adapted =
        CryptoComErrorAdapter.adaptError(
            new CryptoComException(CryptoComErrorAdapter.INSUFFICIENT_AVAILABLE_BALANCE, "boom"));
    assertThat(adapted).isInstanceOf(FundsExceededException.class);
  }

  @Test
  void httpStatusExceptionUnmappedCodeWrapsCauseWithoutDoublePrefixing() {
    CryptoComException exception = new CryptoComException(999999, "boom");

    ExchangeException adapted = CryptoComErrorAdapter.adaptError(exception);

    assertThat(adapted).isExactlyInstanceOf(ExchangeException.class);
    assertThat(adapted).hasCause(exception);
    assertThat(adapted.getMessage()).isEqualTo(exception.getMessage());
  }

  private static ExchangeException adaptError(int code) {
    CryptoComResponse response = new CryptoComResponse();
    response.setCode(code);
    response.setMessage("boom");
    return CryptoComErrorAdapter.adaptError(response);
  }
}
