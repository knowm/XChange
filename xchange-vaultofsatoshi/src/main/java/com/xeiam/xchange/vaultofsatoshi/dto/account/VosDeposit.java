
package com.xeiam.xchange.vaultofsatoshi.dto.account;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Michael Lagacé
 */
public final class VosDeposit {

	  private String wallet_address;
	  private String currency;

	  public VosDeposit(@JsonProperty("wallet_address") String wallet_address, 
			  @JsonProperty("currency") String currency) {

	    this.wallet_address = wallet_address;
	    this.currency = currency;
	  }

	public String getWallet_address() {
		return wallet_address;
	}

	public String getCurrency() {
		return currency;
	}

	@Override
	public String toString() {
		return "VosDeposit [wallet_address=" + wallet_address + ", currency=" + currency + "]";
	}

}
