package org.knowm.xchange.ascendex.service;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Mac;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class AscendexDigest extends BaseParamsDigest {

  public AscendexDigest(String secretKeyBase64) throws IllegalArgumentException {
    super(secretKeyBase64, HMAC_SHA_256);
  }

  public static AscendexDigest createInstance(String secretKeyBase64) {
    if (secretKeyBase64 != null) {
      return new AscendexDigest(secretKeyBase64);
    } else return null;
  }

  @Override
  public String digestParams(RestInvocation restInvocation) {

      String signUrl = getSignUrl(restInvocation.getPath());

      // signature have no regulate

      String message=restInvocation.getHttpHeadersFromParams().get("x-auth-timestamp")+signUrl;
 /* String path =restInvocation.getPath();
    int index=0;
    if (path.contains("api/pro/v1/cash")){
      index=path.lastIndexOf("api/pro/v1/cash")+16;
    }else if (path.contains("api/pro/v1/margin")){
      index=path.lastIndexOf("api/pro/v1/margin")+18;
    }else if (path.contains("api/pro/v1")){
      index=path.lastIndexOf("api/pro/v1")+11;
    }else if (path.contains("/api/pro/v1/spot/fee")){
      message=messageBefore+"fee";

    }

    if (index!=0){
        message= messageBefore+path.substring(index);
    }*/

/*
   if (restInvocation.getPath().contains("cash")) {
      message =
              messageBefore
              + restInvocation
                  .getPath()
                  .substring(restInvocation.getPath().lastIndexOf("cash") + 5);
    } else if (restInvocation.getPath().contains("margin")) {
      message =
              messageBefore
              + restInvocation
                  .getPath()
                  .substring(restInvocation.getPath().lastIndexOf("margin") + 7);
    } else {
      message =
              messageBefore
              + restInvocation.getPath().substring(restInvocation.getPath().lastIndexOf("/") + 1);
    }
    // special message
    String path = restInvocation.getPath().substring(1);*/
   /* switch (path){
      case "pi/pro/data/v1/cash/balance/snapshot":
        // because no <account-group>
        message=messageBefore+"data/v1/cash/balance/snapshot";
        break;
      case "pi/pro/data/v1/margin/balance/snapshot":
        // because no <account-group>
        message=messageBefore+"data/v1/margin/balance/snapshot";
        break;
      case "pi/pro/data/v1/cash/balance/history":
        // because no <account-group>
        message=messageBefore+"data/v1/cash/balance/history";
        break;
      case "pi/pro/data/v1/margin/balance/history":
        // because no <account-group>
        message=messageBefore+"data/v1/margin/balance/history";
        break;
      case "pi/pro/v1/wallet/deposit/address":
        // because no <account-group>
        message=messageBefore+"wallet/deposit/address";
        break;
    }*/

    Mac mac256 = getMac();
    mac256.update(message.getBytes(StandardCharsets.UTF_8));

    return Base64.getEncoder().encodeToString(mac256.doFinal()).trim();
  }

    private String getSignUrl(String path) {
        if (path.contains("api/pro/v1/spot/fee")){
            return "fee";
        }
        if (path.contains("api/pro/v1/margin/risk")){
            return "margin/risk";
        }
        if (path.startsWith("api/pro/data/")){
            return path.substring(8);
        }
        if (path.startsWith("api/pro/v1/wallet")){
            return path.substring(11);
        }
        if (path.contains("cash")) {
            return  path.substring(path.lastIndexOf("cash") + 5);
        } else if (path.contains("margin")) {
          return path.substring(path.lastIndexOf("margin") + 7);
        } else {
            return  path.substring(path.lastIndexOf("/") + 1);
        }
    }
}
