package org.knowm.xchange.bitcoincore;

import java.io.IOException;
import org.knowm.xchange.BaseExchange;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.bitcoincore.service.BitcoinCoreAccountService;
import org.knowm.xchange.exceptions.ExchangeException;
import si.mazi.rescu.SynchronizedValueFactory;

public class BitcoinCoreWallet extends BaseExchange {

  @Override
  public ExchangeSpecification getDefaultExchangeSpecification() {
    ExchangeSpecification specification =
        new ExchangeSpecification(this.getClass().getCanonicalName());
    specification.setShouldLoadRemoteMetaData(false);
    specification.setPlainTextUri("http://localhost:8332/");
    specification.setExchangeName("BitcoinCore");
    specification.setExchangeDescription("BitcoinCore wallet");
    return specification;
  }

  @Override
  public void applySpecification(final ExchangeSpecification specification) {
    if (specification.getPassword() == null) {
      throw new IllegalStateException("password must be set to enable the wallet's RPC interface");
    }

    super.applySpecification(specification);
  }

  @Override
  protected void initServices() {
    this.accountService = new BitcoinCoreAccountService(this);
  }

  @Override
  public void remoteInit() throws IOException, ExchangeException {
    // there is no remote init
  }

  @Override
  public SynchronizedValueFactory<Long> getNonceFactory() {
    throw new UnsupportedOperationException("not applicable");
  }
}
