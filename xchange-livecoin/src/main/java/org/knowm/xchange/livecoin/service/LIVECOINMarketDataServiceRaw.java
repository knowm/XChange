package org.knowm.xchange.livecoin.service;

import java.io.IOException;
import java.util.List;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.livecoin.LIVECOIN;
import org.knowm.xchange.livecoin.dto.marketdata.LIVECOINRestriction;
import org.knowm.xchange.livecoin.dto.marketdata.LIVECOINRestrictions;

public class LIVECOINMarketDataServiceRaw extends LIVECOINBasePollingService<LIVECOIN> {

	public LIVECOINMarketDataServiceRaw(Exchange exchange) {
		super(LIVECOIN.class, exchange);
	}

	public List<LIVECOINRestriction> getConbaseExProducts() throws IOException {
		LIVECOINRestrictions data = (LIVECOINRestrictions) coinbaseEx.getProducts();
	    return data.getRestrictions();
	}

}
