package org.knowm.xchange.blockchain.service;

import org.knowm.xchange.blockchain.BlockchainAuthenticated;
import org.knowm.xchange.blockchain.BlockchainExchange;
import org.knowm.xchange.client.ResilienceRegistries;
import org.knowm.xchange.service.BaseResilientExchangeService;

public class BlockchainBaseService extends BaseResilientExchangeService<BlockchainExchange> {

    protected final String apiKey;
    protected final BlockchainAuthenticated blockchainApi;

    protected BlockchainBaseService(BlockchainExchange exchange,
                                    BlockchainAuthenticated blockchainApi,
                                    ResilienceRegistries resilienceRegistries) {
        super(exchange, resilienceRegistries);
        this.blockchainApi = blockchainApi;
        this.apiKey = exchange.getExchangeSpecification().getApiKey();
    }
}
