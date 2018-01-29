
package org.knowm.xchange.bitcoinde.dto.account;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "btc", "bch", "eth" })
public class BitcoindeBalances {

	@JsonProperty("btc")
	private BitcoindeBalance btc;
	@JsonProperty("bch")
	private BitcoindeBalance bch;
	@JsonProperty("eth")
	private BitcoindeBalance eth;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * No args constructor for use in serialization
	 * 
	 */
	public BitcoindeBalances() {
	}

	/**
	 * 
	 * @param eth
	 * @param bch
	 * @param btc
	 */
	public BitcoindeBalances(BitcoindeBalance btc, BitcoindeBalance bch, BitcoindeBalance eth) {
		super();
		this.btc = btc;
		this.bch = bch;
		this.eth = eth;
	}

	@JsonProperty("btc")
	public BitcoindeBalance getBtc() {
		return btc;
	}

	@JsonProperty("btc")
	public void setBtc(BitcoindeBalance btc) {
		this.btc = btc;
	}

	@JsonProperty("bch")
	public BitcoindeBalance getBch() {
		return bch;
	}

	@JsonProperty("bch")
	public void setBch(BitcoindeBalance bch) {
		this.bch = bch;
	}

	@JsonProperty("eth")
	public BitcoindeBalance getEth() {
		return eth;
	}

	@JsonProperty("eth")
	public void setEth(BitcoindeBalance eth) {
		this.eth = eth;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
