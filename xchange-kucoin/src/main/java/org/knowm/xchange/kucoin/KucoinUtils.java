package org.knowm.xchange.kucoin;

import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.kucoin.dto.KucoinResponse;
import org.knowm.xchange.kucoin.dto.marketdata.KucoinKLineResponse;

public class KucoinUtils {

  public static <D> KucoinResponse<D> checkSuccess(KucoinResponse<D> response) {

    if (response.isSuccess()) {
      return response;
    } else {
      throw new ExchangeException(response.getCode() + ": " + response.getMsg());
    }
  }

  public static KucoinKLineResponse checkSuccess(KucoinKLineResponse response) {

    if (response.getSuccess()) {
      return response;
    } else {
      throw new ExchangeException(response.getCode() + ": " + response.getMsg());
    }
  }
}
