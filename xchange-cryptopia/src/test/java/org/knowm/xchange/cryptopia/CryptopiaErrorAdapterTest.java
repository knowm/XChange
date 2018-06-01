package org.knowm.xchange.cryptopia;

import org.junit.Test;
import org.knowm.xchange.cryptopia.dto.CryptopiaBaseResponse;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.exceptions.InvalidCurrencyPairException;

/** @author walec51 */
public class CryptopiaErrorAdapterTest {

  @Test(expected = InvalidCurrencyPairException.class)
  public void throwIfErrorResponse_wrongMarket() {
    // Cryptopia actualy sets successful = true in this situation
    CryptopiaBaseResponse response =
        new CryptopiaBaseResponse(true, null, null, "Market STRG_BTC not found");
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
  }

  @Test(expected = ExchangeException.class)
  public void throwIfErrorResponse_unrecognizedErrorMessage() {
    CryptopiaBaseResponse response =
        new CryptopiaBaseResponse(false, null, null, "Some other error message");
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
  }

  @Test(expected = ExchangeException.class)
  public void throwIfErrorResponse_unsuccessfulWithoutErrorMessage() {
    CryptopiaBaseResponse response = new CryptopiaBaseResponse(false, null, null, null);
    CryptopiaErrorAdapter.throwIfErrorResponse(response);
  }
}
