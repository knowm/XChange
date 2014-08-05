
package com.xeiam.xchange.vaultofsatoshi.service.polling;

import java.io.IOException;

import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiAccount;
import com.xeiam.xchange.vaultofsatoshi.VaultOfSatoshiUtils;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosAccount;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosWalletAddress;
import com.xeiam.xchange.vaultofsatoshi.dto.account.VosWalletHistory;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosResponse;
import com.xeiam.xchange.vaultofsatoshi.service.VosDigest;

/**
 * @author veken0m
 */
public class VaultOfSatoshiAccountServiceRaw extends VaultOfSatoshiBasePollingService {

  private final VosDigest signatureCreator;
  private final VaultOfSatoshiAccount vaultOfSatoshiAuthenticated;

  /**
   * Initialize common properties from the exchange specification
   * 
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected VaultOfSatoshiAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(exchangeSpecification);

    this.vaultOfSatoshiAuthenticated = RestProxyFactory.createProxy(VaultOfSatoshiAccount.class, exchangeSpecification.getSslUri());
    this.signatureCreator = VosDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getApiKey());
  }

  public VosCurrency getBalance(String currency) throws IOException {

	  final VosResponse<VosCurrency> response = vaultOfSatoshiAuthenticated.getBalance(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), currency);
	  
	  checkResult(response);
	  return response.getData();
  }

  public VosAccount getAccount() throws IOException {
	
	  final VosResponse<VosAccount> response = vaultOfSatoshiAuthenticated.getAccount(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce());
		  
	  checkResult(response);
	  return response.getData();
  }

  public VosWalletAddress getWalletAddress(String currency) throws IOException {

	  final VosResponse<VosWalletAddress> response = vaultOfSatoshiAuthenticated.getWalletAddress(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), currency);
	  
	  checkResult(response);
	  return response.getData();
  }
  
  public VosWalletHistory getWalletHistory(String currency) throws IOException {

	  final VosResponse<VosWalletHistory> response = vaultOfSatoshiAuthenticated.getWalletHistory(exchangeSpecification.getApiKey(), signatureCreator, VaultOfSatoshiUtils.getNonce(), currency, 0, 0);
		  
	  checkResult(response);
	  return response.getData();
  }
}
