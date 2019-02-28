package org.knowm.xchange.lykke.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.lykke.dto.account.LykkeWallet;

import java.io.IOException;
import java.util.List;

public class LykkeAccountServiceRaw extends LykkeBaseService {

    public LykkeAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    public List<LykkeWallet> getWallets() throws IOException {
        return lykke.getWallets(apiKey);
    }
}
