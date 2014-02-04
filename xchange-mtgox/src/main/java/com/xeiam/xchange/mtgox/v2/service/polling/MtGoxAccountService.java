/**
 * Copyright (C) 2012 - 2014 Xeiam LLC http://xeiam.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.xeiam.xchange.mtgox.v2.service.polling;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.mtgox.v2.MtGoxAdapters;
import com.xeiam.xchange.service.polling.PollingAccountService;
import com.xeiam.xchange.utils.Assert;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * <p>
 * XChange service to provide the following to {@link com.xeiam.xchange.Exchange}:
 * </p>
 * <ul>
 * <li>MtGox specific methods to handle account-related operations</li>
 * </ul>
 */
public class MtGoxAccountService extends MtGoxAccountServiceRaw implements PollingAccountService {

    /**
     * Constructor
     *
     * @param exchangeSpecification The {@link ExchangeSpecification}
     */
    public MtGoxAccountService(ExchangeSpecification exchangeSpecification) {
        super(exchangeSpecification);
    }

    @Override
    public AccountInfo getAccountInfo() throws IOException {
        return MtGoxAdapters.adaptAccountInfo(getMtGoxAccountInfo());
    }

    @Override
    public String withdrawFunds(BigDecimal amount, String address) throws IOException {

        return mtGoxWithdrawFunds(amount, address).getTransactionId();
    }

    @Override
    public String requestBitcoinDepositAddress(final String... arguments) throws IOException {
        if (arguments.length < 2) {
            throw new ExchangeException("Expected description and notificationUrl, in that order.");
        }
        Assert.notNull(arguments[0], "Description cannot be null.");
        Assert.notNull(arguments[1], "NotificationUrl cannot be null.");

        String description = arguments[0];
        String notificationUrl = arguments[1];

        return mtGoxRequestDepositAddress(description, notificationUrl).getAddres();
    }

}
