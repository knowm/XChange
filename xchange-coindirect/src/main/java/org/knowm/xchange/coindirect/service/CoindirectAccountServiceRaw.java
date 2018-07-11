package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.coindirect.dto.CoindirectException;
import org.knowm.xchange.coindirect.dto.account.CoindirectWallet;
import si.mazi.rescu.HttpStatusIOException;

import java.io.IOException;
import java.util.List;

public class CoindirectAccountServiceRaw extends CoindirectBaseService {
    /**
     * Constructor
     *
     * @param exchange
     */
    protected CoindirectAccountServiceRaw(Exchange exchange) {
        super(exchange);
    }

    List<CoindirectWallet> listCoindirectWallets(long max) throws IOException, CoindirectException {
        return coindirect.listWallets(max, signatureCreator);
    }
}
