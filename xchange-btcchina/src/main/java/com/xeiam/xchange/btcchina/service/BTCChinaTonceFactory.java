package com.xeiam.xchange.btcchina.service;

import si.mazi.rescu.ValueFactory;

import com.xeiam.xchange.btcchina.BTCChinaUtils;

public class BTCChinaTonceFactory implements ValueFactory<Long> {

  /**
   * {@inheritDoc}
   */
  @Override
  public Long createValue() {

    return Long.valueOf(BTCChinaUtils.getNonce());
  }

}
