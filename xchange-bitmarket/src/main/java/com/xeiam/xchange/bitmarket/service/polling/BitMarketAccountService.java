package com.xeiam.xchange.bitmarket.service.polling;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.bitmarket.BitMarketAdapters;
import com.xeiam.xchange.bitmarket.dto.BitMarketBaseResponse;
import com.xeiam.xchange.bitmarket.dto.account.BitMarketAccount;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.exceptions.ExchangeException;
import com.xeiam.xchange.service.polling.account.PollingAccountService;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author yarkh
 */
public class BitMarketAccountService extends BitMarketAccountServiceRaw implements PollingAccountService {

    public BitMarketAccountService(Exchange exchange) {
        super(exchange);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {

        BitMarketBaseResponse<BitMarketAccount> response = getBitMarketAccountInfo();
        if (isSuccess(response)) {
            return BitMarketAdapters.adaptAccountInfo(response.getData());
        } else if (isError(response)) {
            throw new ExchangeException(response.getErrorMsg());
        }
        return null;
    }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws IOException {
        return null;
    }
}
