package com.xeiam.xchange.cryptsy.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CryptsyPair {

	String label;
	String primaryName;
	String primaryCode;
	String secondaryName;
	String secondaryCode;
	List<CryptsyOrder> sellOrders = new ArrayList<CryptsyOrder>();
	List<CryptsyOrder> buyOrders = new ArrayList<CryptsyOrder>();
	
	
	public CryptsyPair(@JsonProperty("label") String label, @JsonProperty("primaryname") String primaryName, @JsonProperty("primarycode") String primaryCode, @JsonProperty("secondaryname") String secondaryName, @JsonProperty("secondarycode") String secondaryCode, @JsonProperty("sellorders") List<CryptsyOrder> sellOrders, @JsonProperty("buyorders") List<CryptsyOrder> buyOrders) {
		super();
		this.label = label;
		this.primaryName = primaryName;
		this.primaryCode = primaryCode;
		this.secondaryName = secondaryName;
		this.secondaryCode = secondaryCode;
		this.sellOrders = sellOrders;
		this.buyOrders = buyOrders;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getPrimaryName() {
		return primaryName;
	}
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	public String getPrimaryCode() {
		return primaryCode;
	}
	public void setPrimaryCode(String primaryCode) {
		this.primaryCode = primaryCode;
	}
	public String getSecondaryName() {
		return secondaryName;
	}
	public void setSecondaryName(String secondaryName) {
		this.secondaryName = secondaryName;
	}
	public String getSecondaryCode() {
		return secondaryCode;
	}
	public void setSecondaryCode(String secondaryCode) {
		this.secondaryCode = secondaryCode;
	}
	public List<CryptsyOrder> getSellOrders() {
		return sellOrders;
	}
	public void setSellOrders(List<CryptsyOrder> sellOrders) {
		this.sellOrders = sellOrders;
	}
	public List<CryptsyOrder> getBuyOrders() {
		return buyOrders;
	}
	public void setBuyOrders(List<CryptsyOrder> buyOrders) {
		this.buyOrders = buyOrders;
	}
	
	
}
