package org.knowm.xchange.huobi.service;

import java.io.IOException;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.huobi.Huobi;
import org.knowm.xchange.huobi.dto.HuobiResult;
import org.knowm.xchange.huobi.dto.HuobiResultV2;
import org.knowm.xchange.huobi.dto.marketdata.HuobiAsset;
import org.knowm.xchange.huobi.dto.marketdata.results.HuobiAssetsResult;
import org.knowm.xchange.service.BaseExchangeService;
import org.knowm.xchange.service.BaseService;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestProxyFactory;

public class HuobiBaseService extends BaseExchangeService implements BaseService {

  protected Huobi huobi;
  protected ParamsDigest signatureCreator;

  public HuobiBaseService(Exchange exchange) {
    super(exchange);
    huobi =
        RestProxyFactory.createProxy(
            Huobi.class, exchange.getExchangeSpecification().getSslUri(), getClientConfig());
    signatureCreator =
        HuobiDigest.createInstance(exchange.getExchangeSpecification().getSecretKey());
  }

  protected <R> R checkResult(HuobiResult<R> huobiResult) {
    if (!huobiResult.isSuccess()) {
      String huobiError = huobiResult.getError();
      if (huobiError.length() == 0) {
        throw new ExchangeException("Missing error message");
      } else {
        throw new ExchangeException(huobiError);
      }
    }
    return huobiResult.getResult();
  }

  protected <R> R checkResult(HuobiResultV2<R> huobiResult) {
    if (!huobiResult.isSuccess()) {
      String huobiError = huobiResult.getMessage();
      if (huobiError.length() == 0) {
        throw new ExchangeException("Missing error message");
      } else {
        throw new ExchangeException(huobiError);
      }
    }
    return huobiResult.getResult();
  }

  public HuobiAsset[] getHuobiAssets() throws IOException {
    HuobiAssetsResult assetsResult = huobi.getAssets();
    return checkResult(assetsResult);
  }
}
