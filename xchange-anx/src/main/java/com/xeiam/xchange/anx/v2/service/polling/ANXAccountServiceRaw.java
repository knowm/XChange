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
package com.xeiam.xchange.anx.v2.service.polling;

import java.io.IOException;
import java.math.BigDecimal;

import com.xeiam.xchange.anx.ANXUtils;
import com.xeiam.xchange.anx.v2.ANXV2;
import com.xeiam.xchange.anx.v2.dto.ANXException;
import com.xeiam.xchange.anx.v2.dto.account.polling.*;
import com.xeiam.xchange.anx.v2.service.ANXBaseService;
import com.xeiam.xchange.anx.v2.service.ANXV2Digest;
import si.mazi.rescu.RestProxyFactory;

import com.xeiam.xchange.ExchangeException;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.utils.Assert;

public class ANXAccountServiceRaw extends ANXBaseService {

    private final ANXV2 anxV2;
    private final ANXV2Digest signatureCreator;

    /**
     * Initialize common properties from the exchange specification
     *
     * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
     */
    protected ANXAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

        super(exchangeSpecification);

        Assert.notNull(exchangeSpecification.getSslUri(), "Exchange specification URI cannot be null");
        this.anxV2 = RestProxyFactory.createProxy(ANXV2.class, exchangeSpecification.getSslUri());
        this.signatureCreator = ANXV2Digest.createInstance(exchangeSpecification.getSecretKey());
    }

    public ANXAccountInfo getANXAccountInfo() throws IOException {

        try {
            ANXAccountInfoWrapper anxAccountInfoWrapper = anxV2.getAccountInfo(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce());
            return anxAccountInfoWrapper.getANXAccountInfo();
        } catch (ANXException e) {
            throw new ExchangeException("Error calling getAccountInfo(): " + e.getError(), e);
        }
    }

    public ANXWithdrawalResponse anxWithdrawFunds(String currency, BigDecimal amount, String address) throws IOException {

        try {
            ANXWithdrawalResponseWrapper anxWithdrawalResponseWrapper =
                    anxV2.withdrawBtc(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce(), currency, address, amount.multiply(
                            new BigDecimal(ANXUtils.BTC_VOLUME_AND_AMOUNT_INT_2_DECIMAL_FACTOR_2)).intValue(), 1, false, false);
            return anxWithdrawalResponseWrapper.getAnxWithdrawalResponse();
        } catch (ANXException e) {
            throw new ExchangeException("Error calling withdrawFunds(): " + e.getError(), e);
        }
    }

    public ANXBitcoinDepositAddress anxRequestDepositAddress(String currency) throws IOException {

        try {
            ANXBitcoinDepositAddressWrapper anxBitcoinDepositAddressWrapper =
                    anxV2.requestDepositAddress(exchangeSpecification.getApiKey(), signatureCreator, ANXUtils.getNonce(), currency);
            return anxBitcoinDepositAddressWrapper.getAnxBitcoinDepositAddress();
        } catch (ANXException e) {
            throw new ExchangeException("Error calling requestBitcoinDepositAddress(): " + e.getError(), e);
        }
    }
}
