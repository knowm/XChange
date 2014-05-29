package com.xeiam.xchange.atlasats.dtos;

import java.io.Serializable;

public class AtlasOrderId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String value;

	public AtlasOrderId(String value) {
		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AtlasOrderId [value=");
		builder.append(value);
		builder.append("]");
		return builder.toString();
	}
	
	

}
