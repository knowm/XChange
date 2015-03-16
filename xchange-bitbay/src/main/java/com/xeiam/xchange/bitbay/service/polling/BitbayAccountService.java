package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitbay.BitbayAdapters;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author yarkh
 */
public class BitbayAccountService extends BitbayAccountServiceRaw implements PollingAccountService {

    public BitbayAccountService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        BitbayAccount bitbayAccountInfo = getBitbayAccountInfo(null);
        if (bitbayAccountInfo.getSuccess() == null) {
            throw new ExchangeException(bitbayAccountInfo.getMessage());
        }
        return BitbayAdapters.adaptAccount(bitbayAccountInfo);
    }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws IOException {
        BitbayAccount bitbayAccountInfo = getBitbayAccountInfo(currency);
        return bitbayAccountInfo.getAddresses().get(currency);
    }
}
