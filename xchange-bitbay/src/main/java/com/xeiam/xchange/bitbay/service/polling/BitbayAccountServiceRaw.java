package com.xeiam.xchange.bitbay.service.polling;

import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.bitbay.Bitbay;
import com.xeiam.xchange.bitbay.BitbayAuthentiacated;
import com.xeiam.xchange.bitbay.dto.account.BitbayAccount;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayOrderBook;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTicker;
import com.xeiam.xchange.bitbay.dto.marketdata.BitbayTrade;
import com.xeiam.xchange.currency.CurrencyPair;
import com.xeiam.xchange.dto.account.AccountInfo;

import java.io.IOException;
import java.util.Date;

/**
 * @author yarkh
 */
public class BitbayAccountServiceRaw extends BitbayBasePollingService<BitbayAuthentiacated> {


  /**
   * Constructor Initialize common properties from the exchange specification
   *
   * @param exchangeSpecification The {@link com.xeiam.xchange.ExchangeSpecification}
   */
  protected BitbayAccountServiceRaw(ExchangeSpecification exchangeSpecification) {

    super(BitbayAuthentiacated.class, exchangeSpecification);
  }

    protected BitbayAccount getBitbayAccountInfo(String currency) throws IOException {

        BitbayAccount info = bitbay.info(apiKey, signatureCreator, new Date().getTime(), currency);
        return info;
    }

}
