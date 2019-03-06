package org.knowm.xchange.blockchain.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.blockchain.dto.BitcoinAddress;
import org.knowm.xchange.blockchain.dto.BitcoinAddresses;

public class BlockchainAccountServiceRaw extends BlockchainBaseService {

  public BlockchainAccountServiceRaw(Exchange exchange) {
    super(exchange);
  }

  public BitcoinAddress getBitcoinAddressInfo(String address) throws IOException {
    return blockchain.getBitcoinAddress(address);
  }

  public BitcoinAddresses getBitcoinAddressesInfo(String addresses) throws IOException {
    return blockchain.getBitcoinAddresses(addresses);
  }
}
