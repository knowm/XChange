package org.knowm.xchange.acx.service.account;

import java.io.IOException;
import org.knowm.xchange.acx.AcxApi;
import org.knowm.xchange.acx.AcxMapper;
import org.knowm.xchange.acx.AcxSignatureCreator;
import org.knowm.xchange.acx.dto.account.AcxAccountInfo;
import org.knowm.xchange.dto.account.AccountInfo;
import org.knowm.xchange.exceptions.NotAvailableFromExchangeException;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.params.TradeHistoryParams;

public class AcxAccountService implements AccountService {
  private final AcxApi api;
  private final AcxMapper mapper;
  private final AcxSignatureCreator signatureCreator;
  private final String accessKey;

  public AcxAccountService(
      AcxApi api, AcxMapper mapper, AcxSignatureCreator signatureCreator, String accessKey) {
    this.api = api;
    this.mapper = mapper;
    this.signatureCreator = signatureCreator;
    this.accessKey = accessKey;
  }

  @Override
  public AccountInfo getAccountInfo() throws IOException {
    long tonce = System.currentTimeMillis();
    AcxAccountInfo accountInfo = api.getAccountInfo(accessKey, tonce, signatureCreator);
    return mapper.mapAccountInfo(accountInfo);
  }

  @Override
  public TradeHistoryParams createFundingHistoryParams() {
    throw new NotAvailableFromExchangeException();
  }
}
