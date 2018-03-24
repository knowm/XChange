package org.knowm.xchange.independentreserve.dto.trade;

import org.knowm.xchange.independentreserve.dto.auth.AuthAggregate;

/**
 * see https://www.independentreserve.com/API#SynchDigitalCurrencyDepositAddressWithBlockchain
 */
public class IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest extends AuthAggregate {

  /**
   * @param depositAddress Bitcoin or Ether deposit address to check for new deposits.
   */
  public IndependentReserveSynchDigitalCurrencyDepositAddressWithBlockchainRequest(String apiKey, Long nonce, String depositAddress) {
    super(apiKey, nonce);
    this.parameters.put("depositAddress", depositAddress);
  }
}
