package org.knowm.xchange.bibox.service;

import static org.knowm.xchange.bibox.dto.BiboxCommands.COIN_LIST_CMD;

import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.bibox.BiboxException;
import org.knowm.xchange.bibox.dto.BiboxCommands;
import org.knowm.xchange.bibox.dto.BiboxPagedResponses;
import org.knowm.xchange.bibox.dto.BiboxPagedResponses.BiboxPage;
import org.knowm.xchange.bibox.dto.BiboxSingleResponse;
import org.knowm.xchange.bibox.dto.account.BiboxCoin;
import org.knowm.xchange.bibox.dto.account.BiboxDeposit;
import org.knowm.xchange.bibox.dto.account.BiboxFundsCommandBody;
import org.knowm.xchange.bibox.dto.account.BiboxTransferCommandBody;
import org.knowm.xchange.bibox.dto.account.BiboxWithdrawal;
import org.knowm.xchange.currency.Currency;
import org.knowm.xchange.exceptions.ExchangeException;

/** @author odrotleff */
public class BiboxAccountServiceRaw extends BiboxBaseService {

  protected BiboxAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public List<BiboxCoin> getBiboxAccountInfo() {
    try {
      BiboxSingleResponse<List<BiboxCoin>> response =
          bibox.coinList(COIN_LIST_CMD.json(), apiKey, signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public String requestBiboxDepositAddress(Currency currency) {
    try {
      BiboxSingleResponse<String> response =
          bibox.depositAddress(
              BiboxCommands.depositAddressCommand(currency.getCurrencyCode()).json(),
              apiKey,
              signatureCreator);
      throwErrors(response);
      return response.get().getResult();
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BiboxPage<BiboxWithdrawal> requestBiboxWithdrawals(BiboxFundsCommandBody body) {
    try {
      BiboxPagedResponses<BiboxWithdrawal> response =
          bibox.transferOutList(
              BiboxCommands.withdrawalsCommand(body).json(), apiKey, signatureCreator);
      throwErrors(response);
      BiboxPage<BiboxWithdrawal> page = response.get().getResult();
      return page;
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public BiboxPage<BiboxDeposit> requestBiboxDeposits(BiboxFundsCommandBody body) {
    try {
      BiboxPagedResponses<BiboxDeposit> response =
          bibox.transferInList(
              BiboxCommands.depositsCommand(body).json(), apiKey, signatureCreator);
      throwErrors(response);
      BiboxPage<BiboxDeposit> page = response.get().getResult();
      return page;
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }

  public void requestBiboxWithdraw(BiboxTransferCommandBody body) {
    try {
      BiboxSingleResponse<String> response =
          bibox.transfer(BiboxCommands.transferCommand(body).json(), apiKey, signatureCreator);
      throwErrors(response);
    } catch (BiboxException e) {
      throw new ExchangeException(e.getMessage());
    }
  }
}
