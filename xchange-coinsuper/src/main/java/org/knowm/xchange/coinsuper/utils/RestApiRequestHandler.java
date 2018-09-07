package org.knowm.xchange.coinsuper.utils;

/** https://exp.samcoin.pro:9243/api/v1/market/orderBook */
public class RestApiRequestHandler {

  /**
   * 封装请求参数
   *
   * @param data
   * @param <T>
   * @return
   */
  public static <T> RestRequestParam<T> generateRequestParam(
      T data, String accessKey, String secretKey) {
    RestRequestParam<T> param = new RestRequestParam<T>();
    param.setData(data);
    RestRequestParam.Common common = param.newCommon(accessKey, System.currentTimeMillis());
    String sign = RestSignUtil.generateSign(param, secretKey);
    common.setSign(sign);
    return param;
  }
}
