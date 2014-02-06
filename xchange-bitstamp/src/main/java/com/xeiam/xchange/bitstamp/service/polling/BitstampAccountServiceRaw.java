package com.xeiam.xchange.bitstamp.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.BitstampUtils;
import com.xeiam.xchange.bitstamp.dto.account.BitstampBalance;
import com.xeiam.xchange.bitstamp.dto.polling.BitStampGenericResponse;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author gnandiga
 */
public class BitstampAccountServiceRaw extends BasePollingExchangeService {

    protected final BitstampDigest signatureCreator;
    protected final BitstampAuthenticated bitstampAuthenticated;

    /**
     * Initialize common properties from the exchange specification
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    protected BitstampAccountServiceRaw(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);

        this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class,
                                                                    exchangeSpecification.getSslUri());
        this.signatureCreator = BitstampDigest.createInstance(exchangeSpecification.getSecretKey(),
                                                                exchangeSpecification.getUserName(),
                                                                exchangeSpecification.getApiKey());
    }

    public BitstampBalance getBitstampBalance() throws IOException {
        BitstampBalance bitstampBalance = bitstampAuthenticated.getBalance(exchangeSpecification.getApiKey(),
                                                                            signatureCreator,
                                                                            BitstampUtils.getNonce());
        if (bitstampBalance.getError() != null) {
            throw new ExchangeException("Error getting balance. " + bitstampBalance.getError());
        }
        return bitstampBalance;
    }

    public BitStampGenericResponse withdrawBitstampFunds(final BigDecimal amount, final String address) throws IOException {
        final BitStampGenericResponse response = bitstampAuthenticated.withdrawBitcoin(exchangeSpecification.getApiKey(),
                                                                                        signatureCreator, BitstampUtils.getNonce(),
                                                                                        amount, address);
        if(response.getError() != null)
        {
            throw new ExchangeException("Withdrawing funds from bitStamp failed: " + response.getError());
        }
        return response;

    }

    public BitStampGenericResponse getBitstampBitcoinDepositAddress() throws IOException {
        final BitStampGenericResponse response = bitstampAuthenticated.getBitcoinDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());
        if(response.getError() != null)
        {
            throw new ExchangeException("Requesting Bitcoin deposit address failed: " + response.getError());
        }
        return response;
    }
}
