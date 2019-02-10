package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.blockchain.dto.BitcoinAddress;
import org.knowm.xchange.blockchain.dto.BitcoinAddresses;
import org.knowm.xchange.service.account.AccountService;

import java.io.IOException;

public class BlockchainAccountServiceRaw extends BlockchainBaseService{

    public BlockchainAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public BitcoinAddress getBitcoinAddressInfo(String address) throws IOException {
        return blockchain.getBitcoinAddress(address);
    }

    public BitcoinAddresses getBitcoinAddressesInfo(String addresses) throws IOException{
        return blockchain.getBitcoinAddresses(addresses);
    }
}
