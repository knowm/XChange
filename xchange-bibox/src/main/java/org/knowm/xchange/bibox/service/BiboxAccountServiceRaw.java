package org.knowm.xchange.bibox.service;

import static org.knowm.xchange.bibox.dto.BiboxCommands.COIN_LIST_CMD;

import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.BiboxException;
import org.knowm.xchange.bibox.dto.BiboxCommands;
import org.knowm.xchange.bibox.dto.BiboxSingleResponse;
import org.knowm.xchange.bibox.dto.account.BiboxCoin;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;

/**
 * @author odrotleff
 */
public class BiboxAccountServiceRaw extends BiboxBaseService {

  protected BiboxAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BiboxCoin> getBiboxAccountInfo() {
    try {
      BiboxSingleResponse<List<BiboxCoin>> response = bibox.coinList(COIN_LIST_CMD.json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public String requestBiboxDepositAddress(Currency currency) {
    try {
      BiboxSingleResponse<String> response = bibox.depositAddress(BiboxCommands.depositAddressCommand(currency.getCurrencyCode()).json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
