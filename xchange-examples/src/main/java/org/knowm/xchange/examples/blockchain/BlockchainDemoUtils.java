package org.knowm.xchange.examples.blockchain;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.blockchain.BlockchainExchange;

import java.math.BigDecimal;

public class BlockchainDemoUtils {
    public static final Long END_TIME = 12 * 30 * 24 * 60 * 60 * 1000L;
    public static final String BENEFICIARY = "8a2e42ee-c94a-4641-9208-9501cbc0fed0";
    public static final String SYMBOL = "ADA/USDT";
    public static final BigDecimal AMOUNT = new BigDecimal("1.0");
    public static final BigDecimal AMOUNT_LIMIT = new BigDecimal("0.01");
    public static final BigDecimal STOP_PRICE = new BigDecimal("1");
    public static final BigDecimal STOP_LIMIT = new BigDecimal("1");
    public static Exchange createExchange() {

        Exchange bcd = ExchangeFactory.INSTANCE.createExchange(BlockchainExchange.class);
        ExchangeSpecification bcdSpec = bcd.getDefaultExchangeSpecification();

        bcdSpec.setApiKey("");
        bcdSpec.setSecretKey("");

        bcd.applySpecification(bcdSpec);

        return bcd;
    }
}
