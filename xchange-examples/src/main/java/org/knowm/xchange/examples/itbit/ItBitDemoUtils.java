package org.knowm.xchange.examples.itbit;

import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.itbit.v1.ItBitExchange;

/**
 * Created by joseph on 6/15/17.
 */
public class ItBitDemoUtils {
    public static Exchange createExchange() {
        ExchangeSpecification exSpec = new ItBitExchange().getDefaultExchangeSpecification();
        exSpec.setUserName("userId/walletId");
        exSpec.setApiKey("xxx");
        exSpec.setSecretKey("xxx");
        return ExchangeFactory.INSTANCE.createExchange(exSpec);
    }
}
