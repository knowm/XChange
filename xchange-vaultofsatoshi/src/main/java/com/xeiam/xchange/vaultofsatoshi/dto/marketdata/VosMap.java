package com.xeiam.xchange.vaultofsatoshi.dto.marketdata;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnySetter;

/**
 * Data object used to map key/value pairs returned from VaultOfSatoshi
 * 
 * @param <T>
 */
public final class VosMap<T> {

	private Map<String, T> currencies = new HashMap<String, T>();

	@JsonAnySetter
	public void putValue(String name, T vosValue) {

		this.currencies.put(name, vosValue);
	}

	public Map<String, T> getVosMap() {

		return currencies;
	}

	@Override
	public String toString() {
		return "VosMap [currencies=" + currencies + "]";
	}

}
