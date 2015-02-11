package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.NotAvailableFromExchangeException;
import com.xeiam.xchange.NotYetImplementedForExchangeException;
import com.xeiam.xchange.bitbay.BitbayAdapters;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.OrderBook;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.marketdata.Trades;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.service.polling.PollingMarketDataService;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author yarkh
 */
public class BitbayAccountService extends BitbayAccountServiceRaw implements PollingAccountService {


    /**
     * Constructor Initialize common properties from the exchange specification
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    public BitbayAccountService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
    }

    @Override
    public AccountInfo getAccountInfo() throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        BitbayAccount bitbayAccountInfo = getBitbayAccountInfo(null);
        if (bitbayAccountInfo.getSuccess() == null) {
            throw new ExchangeException(bitbayAccountInfo.getMessage());
        }
        return BitbayAdapters.adaptAccount(bitbayAccountInfo);
    }

    @Override
    public String withdrawFunds(String currency, BigDecimal amount, String address) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        return null;
    }

    @Override
    public String requestDepositAddress(String currency, String... args) throws ExchangeException, NotAvailableFromExchangeException, NotYetImplementedForExchangeException, IOException {
        BitbayAccount bitbayAccountInfo = getBitbayAccountInfo(currency);
        return bitbayAccountInfo.getAddresses().get(currency);
    }
}
