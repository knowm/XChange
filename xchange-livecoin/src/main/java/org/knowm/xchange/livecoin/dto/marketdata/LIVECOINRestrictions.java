package org.knowm.xchange.livecoin.dto.marketdata;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LIVECOINRestrictions {

	private final Boolean success;
	private List<LIVECOINRestriction> restrictions = new ArrayList<LIVECOINRestriction>();

	public LIVECOINRestrictions(@JsonProperty("success") Boolean success,
			@JsonProperty("restrictions") List<LIVECOINRestriction> restrictions) {
		super();
		this.success = success;
		this.restrictions = restrictions;
	}

	public Boolean getSuccess() {
		return success;
	}

	public List<LIVECOINRestriction> getRestrictions() {
		return restrictions;
	}

}
