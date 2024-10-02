package org.knowm.xchange.bitget.service;

import java.io.IOException;
import java.util.List;
import org.knowm.xchange.bitget.BitgetAdapters;
import org.knowm.xchange.bitget.BitgetExchange;
import org.knowm.xchange.bitget.dto.account.BitgetBalanceDto;
import org.knowm.xchange.bitget.dto.account.BitgetTransferRecordDto;
import org.knowm.xchange.bitget.dto.account.params.BitgetTransferHistoryParams;

public class BitgetAccountServiceRaw extends BitgetBaseService {

  public BitgetAccountServiceRaw(BitgetExchange exchange) {
    super(exchange);
  }


  public List<BitgetBalanceDto> getBitgetBalances() throws IOException {
    return bitgetAuthenticated.balances(apiKey, bitgetDigest, passphrase, exchange.getNonceFactory()).getData();
  }


  public List<BitgetTransferRecordDto> getBitgetTransferRecords(BitgetTransferHistoryParams params) throws IOException {
    Long from = params.getStartTime() != null ? params.getStartTime().toEpochMilli() : null;
    Long to = params.getEndTime() != null ? params.getEndTime().toEpochMilli() : null;

    return bitgetAuthenticated.transferRecords(apiKey, bitgetDigest, passphrase, exchange.getNonceFactory(),
        BitgetAdapters.toString(params.getCurrency()), params.getLimit(), params.getClientOid(),
        BitgetAdapters.toString(params.getFromAccountType()), from, to, params.getEndId()).getData();
  }


}
