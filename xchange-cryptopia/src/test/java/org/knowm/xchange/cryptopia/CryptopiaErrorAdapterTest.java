package org.knowm.xchange.cryptopia;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.knowm.xchange.cryptopia.dto.CryptopiaException;
import org.knowm.xchange.exceptions.CurrencyPairNotValidException;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author walec51 */
public class CryptopiaErrorAdapterTest {

  @Test
  public void adapt_wrongMarket() {
    CryptopiaException e = new CryptopiaException();
    e.setError("Market STRG_BTC not found");
    ExchangeException adapted = CryptopiaErrorAdapter.adapt(e);
    assertThat(adapted).isExactlyInstanceOf(CurrencyPairNotValidException.class);
  }

  @Test
  public void adapt_unrecognizedErrorMessage() {
    CryptopiaException e = new CryptopiaException();
    e.setError("Some other error message");
    ExchangeException adapted = CryptopiaErrorAdapter.adapt(e);
    assertThat(adapted).isExactlyInstanceOf(ExchangeException.class);
  }

  @Test
  public void adapt_noErrorMessage() {
    ExchangeException adapted = CryptopiaErrorAdapter.adapt(new CryptopiaException());
    assertThat(adapted).isExactlyInstanceOf(ExchangeException.class);
  }
}
