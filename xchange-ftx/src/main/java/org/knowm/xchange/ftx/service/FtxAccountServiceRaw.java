package org.knowm.xchange.ftx.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ftx.FtxException;
import org.knowm.xchange.ftx.dto.FtxResponse;
import org.knowm.xchange.ftx.dto.account.*;

public class FtxAccountServiceRaw extends FtxBaseService {

  public FtxAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public FtxResponse<FtxAccountDto> getFtxAccountInformation(String subaccount)
      throws FtxException, IOException {

    try {
      return ftx.getAccountInformation(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxWalletBalanceDto>> getFtxWalletBalances(String subaccount)
      throws FtxException, IOException {

    try {
      return ftx.getWalletBalances(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxSubAccountBalanceDto> getFtxSubAccountBalances(String nickname)
      throws FtxException, IOException {
    try {
      return ftx.getSubAccountBalances(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          nickname);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxSubAccountBalanceDto> changeFtxSubAccountName(
      String nickname, String newNickname) throws FtxException, IOException {
    try {
      return ftx.changeSubAccountName(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          new FtxChangeSubAccountNamePOJO(nickname, newNickname));
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxSubAccountDto>> getFtxAllSubAccounts()
      throws FtxException, IOException {
    try {
      return ftx.getAllSubAccounts(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse deleteFtxAllSubAccounts(String nickname) throws FtxException, IOException {
    try {
      return ftx.deleteSubAccounts(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          new FtxSubAccountRequestPOJO(nickname));
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxSubAccountDto> createFtxSubAccount(String nickname)
      throws FtxException, IOException {
    try {
      return ftx.createSubAccount(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          new FtxSubAccountRequestPOJO(nickname));
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxSubAccountTranferDto> transferBetweenFtxSubAccount(
      FtxSubAccountTransferPOJO payload) throws FtxException, IOException {
    try {
      return ftx.transferBetweenSubAccounts(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          payload);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<FtxLeverageDto> changeLeverage(String subaccount, int leverage)
      throws FtxException, IOException {
    try {
      return ftx.changeLeverage(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          null,
          new FtxLeverageDto(leverage));
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }

  public FtxResponse<List<FtxFundingPaymentsDto>> getFtxFundingPayments(
      String subaccount, Long startTime, Long endTime, String future)
      throws FtxException, IOException {
    try {
      return ftx.getFundingPayments(
          exchange.getExchangeSpecification().getApiKey(),
          exchange.getNonceFactory().createValue(),
          signatureCreator,
          subaccount,
          startTime,
          endTime,
          future);
    } catch (FtxException e) {
      throw new FtxException(e.getMessage());
    }
  }
}
