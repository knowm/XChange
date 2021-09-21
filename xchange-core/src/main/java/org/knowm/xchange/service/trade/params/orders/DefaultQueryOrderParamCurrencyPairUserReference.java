package org.knowm.xchange.service.trade.params.orders;

import org.knowm.xchange.currency.CurrencyPair;

public class DefaultQueryOrderParamCurrencyPairUserReference extends DefaultQueryOrderParamCurrencyPair
	implements OrderQueryParamUserReference {

	private String userReference;
	
	public DefaultQueryOrderParamCurrencyPairUserReference() {
		super();
	}
	
	public DefaultQueryOrderParamCurrencyPairUserReference(CurrencyPair pair, String orderId, String userReference) {
		super(pair, orderId);
		this.userReference = userReference;
	}
	
	@Override
	public String getUserReference() {
		return userReference;
	}

	@Override
	public void setUserReference(String userReference) {
		this.userReference = userReference;
	}

}
