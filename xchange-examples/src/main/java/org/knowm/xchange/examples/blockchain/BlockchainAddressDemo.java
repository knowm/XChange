package org.knowm.xchange.examples.blockchain;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.blockchain.Blockchain;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BitcoinAddress;
import org.knowm.xchange.blockchain.dto.BitcoinAddresses;
import org.knowm.xchange.client.ExchangeRestProxyBuilder;

/** @author timmolter */
public class BlockchainAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange blockchainExchangexchange =
        ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class);
    Blockchain blockchain =
        ExchangeRestProxyBuilder.forInterface(
                Blockchain.class, blockchainExchangexchange.getExchangeSpecification())
            .build();

    BitcoinAddress bitcoinAddress = blockchain.getBitcoinAddress("XXX");
    System.out.println(bitcoinAddress.toString());

    BitcoinAddresses bitcoinAddresses = blockchain.getBitcoinAddresses("XXX");
    for (BitcoinAddress bitcoinAddress2 : bitcoinAddresses.getBitcoinAddresses()) {
      System.out.println(bitcoinAddress2.toString());
    }
  }
}
