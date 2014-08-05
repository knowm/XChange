
package com.xeiam.xchange.vaultofsatoshi.dto.account;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosCurrency;
import com.xeiam.xchange.vaultofsatoshi.dto.marketdata.VosMap;

/**
 * @author Michael Lagacé
 */
public final class VosAccount {

	  private int account_id;
	  private long created;
	  private long last_login;
	  private VosMap<VosCurrency> monthly_volume;
	  private VosMap<VosCurrency> trade_fee;
	  private VosMap<VosWallet> wallets;
	  private String[] rights;


	  public VosAccount(@JsonProperty("account_id") int account_id, 
			  @JsonProperty("created") long created,
			  @JsonProperty("last_login") long last_login,
			  @JsonProperty("monthly_volume") VosMap<VosCurrency> monthly_volume,
			  @JsonProperty("trade_fee") VosMap<VosCurrency> trade_fee,
			  @JsonProperty("wallets") VosMap<VosWallet> wallets,
			  @JsonProperty("rights") String[] rights) {

	    this.account_id = account_id;
	    this.created = created;
	    this.last_login = last_login;
	    this.monthly_volume = monthly_volume;
	    this.trade_fee = trade_fee;
	    this.wallets = wallets;
	    this.rights = rights;
	  }


	public int getAccount_id() {
		return account_id;
	}


	public long getCreated() {
		return created;
	}


	public long getLast_login() {
		return last_login;
	}


	public VosMap<VosCurrency> getMonthly_volume() {
		return monthly_volume;
	}


	public VosMap<VosCurrency> getTrade_fee() {
		return trade_fee;
	}


	public VosMap<VosWallet> getWallets() {
		return wallets;
	}


	public String[] getRights() {
		return rights;
	}

	@Override
	public String toString() {
		return "VosAccount [account_id=" + account_id + ", created=" + created
				+ ", last_login=" + last_login + ", monthly_volume="
				+ monthly_volume + ", trade_fee=" + trade_fee + ", wallets="
				+ wallets + ", rights=" + Arrays.toString(rights) + "]";
	}

}
