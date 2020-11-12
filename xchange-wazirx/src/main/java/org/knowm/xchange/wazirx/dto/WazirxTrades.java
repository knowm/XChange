package org.knowm.xchange.wazirx.dto;

import java.util.List;

public class WazirxTrades {

	private List<WazirxTrade> wazirxTrades;

 
	public List<WazirxTrade> getWazirxTrades() {
		return wazirxTrades;
	}


	public void setWazirxTrades(List<WazirxTrade> wazirxTrades) {
		this.wazirxTrades = wazirxTrades;
	}


	@Override
	public String toString() {
		return "WazirxTrades [wazirxTrades=" + wazirxTrades + "]";
	}

}
