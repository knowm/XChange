package org.knowm.xchange.bitso.dto.account;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BalancePayload {

	private List<BitsoBalances> balances;

	public BalancePayload(@JsonProperty("balances") List<BitsoBalances> balances) {
		this.balances = balances;
	}

	public List<BitsoBalances> getBalances() {
		return balances;
	}

	public void setBalances(List<BitsoBalances> balances) {
		this.balances = balances;
	}

	@Override
	public String toString() {
		return "BalancePayload [balances=" + balances + "]";
	}
}
