package org.knowm.xchange.examples.blockchain;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.blockchain.Blockchain;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.blockchain.dto.BitcoinAddress;
import org.knowm.xchange.blockchain.dto.BitcoinAddresses;
import si.mazi.rescu.RestProxyFactory;

/** @author timmolter */
public class BlockchainAddressDemo {

  public static void main(String[] args) throws IOException {

    Exchange blockchainExchangexchange =
        ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class.getName());
    Blockchain blockchain =
        RestProxyFactory.createProxy(
            Blockchain.class,
            blockchainExchangexchange.getExchangeSpecification().getPlainTextUri());

    BitcoinAddress bitcoinAddress = blockchain.getBitcoinAddress("XXX");
    System.out.println(bitcoinAddress.toString());

    BitcoinAddresses bitcoinAddresses = blockchain.getBitcoinAddresses("XXX");
    for (BitcoinAddress bitcoinAddress2 : bitcoinAddresses.getBitcoinAddresses()) {
      System.out.println(bitcoinAddress2.toString());
    }
  }
}
