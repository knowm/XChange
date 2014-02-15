package com.xeiam.xchange.coinbase.service.polling;

import java.io.IOException;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.coinbase.CoinbaseUtils;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAccountChanges;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddress;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddressCallback;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseAddresses;
import com.xeiam.xchange.coinbase.dto.account.CoinbaseContacts;
import com.xeiam.xchange.coinbase.dto.marketdata.CoinbaseAmount;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransactions;
import com.xeiam.xchange.coinbase.dto.trade.CoinbaseTransfers;

public class CoinbaseAccountServiceRaw extends CoinbaseBaseAuthenticatedService {

  protected CoinbaseAccountServiceRaw(final ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);
  }

  public CoinbaseAmount getCoinbaseBalance() throws IOException {

    final CoinbaseAmount balance = coinbaseAuthenticated.getBalance(exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return balance;
  }

  public CoinbaseAddress getCoinbaseReceiveAddress() throws IOException {

    final CoinbaseAddress receiveResult = coinbaseAuthenticated.getReceiveAddress(exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return receiveResult;
  }

  public CoinbaseAddresses getCoinbaseAddresses() throws IOException {

    return getCoinbaseAddresses(null, null, null);
  }

  public CoinbaseAddresses getCoinbaseAddresses(final Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseAddresses receiveResult = coinbaseAuthenticated.getAddresses(page, limit, filter, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return receiveResult;
  }

  public CoinbaseAddress generateCoinbaseReceiveAddress() throws IOException {

    return generateCoinbaseReceiveAddress(null, null);
  }

  public CoinbaseAddress generateCoinbaseReceiveAddress(final String callbackUrl, final String label) throws IOException {

    final CoinbaseAddressCallback callbackUrlParam = new CoinbaseAddressCallback(callbackUrl, label);
    final CoinbaseAddress generateReceiveAddress = coinbaseAuthenticated.generateReceiveAddress(callbackUrlParam, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());

    return handlePostResponse(generateReceiveAddress);
  }

  public CoinbaseAccountChanges getCoinbaseAccountChanges() throws IOException {

    return getCoinbaseAccountChanges(null);
  }

  public CoinbaseAccountChanges getCoinbaseAccountChanges(final Integer page) throws IOException {

    final CoinbaseAccountChanges accountChanges = coinbaseAuthenticated.getAccountChanges(page, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return accountChanges;
  }

  public CoinbaseContacts getCoinbaseContacts() throws IOException {

    return getCoinbaseContacts(null, null, null);
  }
  
  public CoinbaseContacts getCoinbaseContacts(final Integer page, final Integer limit, final String filter) throws IOException {

    final CoinbaseContacts contacts = coinbaseAuthenticated.getContacts(page, limit, filter, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return contacts;
  }

  public CoinbaseTransfers getCoinbaseTransfers() throws IOException {

    return getCoinbaseTransfers(null, null);
  }

  public CoinbaseTransfers getCoinbaseTransfers(final Integer page, final Integer limit) throws IOException {

    final CoinbaseTransfers transfers = coinbaseAuthenticated.getTransfers(page, limit, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return transfers;
  }
  
  public CoinbaseTransactions getCoinbaseTransactions() throws IOException {

    return getCoinbaseTransactions(null);
  }
  
  public CoinbaseTransactions getCoinbaseTransactions(final Integer page) throws IOException {

    final CoinbaseTransactions transactions = coinbaseAuthenticated.getTransactions(page, exchangeSpecification.getApiKey(), signatureCreator, CoinbaseUtils.getNonce());
    return transactions;
  }
}
