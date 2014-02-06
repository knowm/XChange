package com.xeiam.xchange.bitstamp.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitstamp.BitstampAuthenticated;
import com.xeiam.xchange.bitstamp.BitstampUtils;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampOrder;
import com.xeiam.xchange.bitstamp.dto.trade.BitstampUserTransaction;
import com.xeiam.xchange.bitstamp.service.BitstampDigest;
import com.xeiam.xchange.service.polling.BasePollingExchangeService;
import si.mazi.rescu.RestProxyFactory;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author gnandiga
 */
public class BitstampTradeServiceRaw extends BasePollingExchangeService{

    protected final BitstampAuthenticated bitstampAuthenticated;
    protected final BitstampDigest signatureCreator;

    /**
     * Initialize common properties from the exchange specification
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    public BitstampTradeServiceRaw(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
        this.bitstampAuthenticated = RestProxyFactory.createProxy(BitstampAuthenticated.class, exchangeSpecification.getSslUri());
        this.signatureCreator = BitstampDigest.createInstance(exchangeSpecification.getSecretKey(), exchangeSpecification.getUserName(), exchangeSpecification.getApiKey());
    }

    public BitstampOrder[] getBitstampOpenOrders() throws IOException {
        return bitstampAuthenticated.getOpenOrders(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce());
    }

    public BitstampOrder sellBitstampOrder(BigDecimal tradableAmount, BigDecimal amount) throws IOException {
        return bitstampAuthenticated.sell(exchangeSpecification.getApiKey(), signatureCreator,
                BitstampUtils.getNonce(), tradableAmount, amount);
    }

    public BitstampOrder buyBitStampOrder(BigDecimal tradableAmount, BigDecimal amount) throws IOException {
        return bitstampAuthenticated.buy(exchangeSpecification.getApiKey(), signatureCreator,
                BitstampUtils.getNonce(), tradableAmount, amount);
    }

    public boolean cancelBitstampOrder(int orderId) throws IOException {
        return bitstampAuthenticated.cancelOrder(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), orderId).equals(true);
    }

    public BitstampUserTransaction[] getBitstampUserTransactions(Long numberOfTransactions) throws IOException {
        return bitstampAuthenticated.getUserTransactions(exchangeSpecification.getApiKey(), signatureCreator, BitstampUtils.getNonce(), numberOfTransactions);
    }
}
