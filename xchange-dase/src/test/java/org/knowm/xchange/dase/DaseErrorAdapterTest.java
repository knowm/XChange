package org.knowm.xchange.dase;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.ExchangeSecurityException;
import org.knowm.xchange.exceptions.FundsExceededException;
import org.knowm.xchange.exceptions.RateLimitExceededException;

class DaseErrorAdapterTest {

  @Test
  void unauthorized_maps_to_security() {
    ExchangeException ex = DaseErrorAdapter.adapt("Unauthorized", "nope");
    assertThat(ex).isInstanceOf(ExchangeSecurityException.class);
    assertThat(ex.getMessage()).isEqualTo("nope");
  }

  @Test
  void forbidden_maps_to_security() {
    ExchangeException ex = DaseErrorAdapter.adapt("Forbidden", "access denied");
    assertThat(ex).isInstanceOf(ExchangeSecurityException.class);
    assertThat(ex.getMessage()).isEqualTo("access denied");
  }

  @Test
  void insufficientFunds_maps_to_fundsExceeded() {
    ExchangeException ex = DaseErrorAdapter.adapt("InsufficientFunds", "insufficient");
    assertThat(ex).isInstanceOf(FundsExceededException.class);
    assertThat(ex.getMessage()).isEqualTo("insufficient");
  }

  @Test
  void tooManyRequests_maps_to_rateLimit() {
    ExchangeException ex = DaseErrorAdapter.adapt("TooManyRequests", "slow down");
    assertThat(ex).isInstanceOf(RateLimitExceededException.class);
    assertThat(ex.getMessage()).isEqualTo("slow down");
  }

  @Test
  void invalidInput_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("InvalidInput", "invalid size");
    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("invalid size");
  }

  @Test
  void invalidIdFormat_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("InvalidIdFormat", "invalid id");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void invalidNumberFormat_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("InvalidNumberFormat", "invalid number");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void payloadTooLarge_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("PayloadTooLarge", "too large");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void marketOrdersDisabled_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("MarketOrdersDisabled", "not allowed");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void notFound_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("NotFound", "not found");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void serviceStarting_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServiceStarting", "starting");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void serviceRestarting_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServiceRestarting", "restarting");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void serviceUnavailable_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServiceUnavailable", "unavailable");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void serviceReadOnly_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServiceReadOnly", "read-only");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void serviceCancelOnly_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServiceCancelOnly", "cancel-only");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void servicePostOnly_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServicePostOnly", "post-only");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void serviceShuttingDown_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("ServiceShuttingDown", "shutting down");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void internalError_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("InternalError", "internal error");
    assertThat(ex).isInstanceOf(DaseException.class);
  }

  @Test
  void unknown_type_maps_to_daseException() {
    ExchangeException ex = DaseErrorAdapter.adapt("Whatever", "msg");
    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("msg");
  }

  @Test
  void null_type_with_message_uses_message() {
    ExchangeException ex = DaseErrorAdapter.adapt(null, "some error");
    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("some error");
  }

  @Test
  void null_type_and_null_message_uses_default() {
    ExchangeException ex = DaseErrorAdapter.adapt(null, null);
    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("Unknown error");
  }

  @Test
  void null_message_uses_type_as_message() {
    ExchangeException ex = DaseErrorAdapter.adapt("InvalidInput", null);
    assertThat(ex).isInstanceOf(DaseException.class);
    assertThat(ex.getMessage()).isEqualTo("InvalidInput");
  }
}
