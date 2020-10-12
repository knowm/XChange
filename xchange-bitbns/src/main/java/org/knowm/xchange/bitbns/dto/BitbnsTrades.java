package org.knowm.xchange.bitbns.dto;

import java.util.List;

public class BitbnsTrades {

	private List<BitbnsTrades> bitbnsTrades;

	public List<BitbnsTrades> getBitbnsTrades() {
		return bitbnsTrades;
	}

	public void setBitbnsTrades(List<BitbnsTrades> bitbnsTrades) {
		this.bitbnsTrades = bitbnsTrades;
	}

	@Override
	public String toString() {
		return "bitbnsTrades [bitbnsTrades=" + bitbnsTrades + "]";
	}

}
