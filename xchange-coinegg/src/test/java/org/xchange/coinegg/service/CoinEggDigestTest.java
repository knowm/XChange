package org.xchange.coinegg.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.FormParam;

import org.junit.Test;

import si.mazi.rescu.Params;
import si.mazi.rescu.RestInvocation;

public class CoinEggDigestTest {

  // These Are Expired Keys
  private final String privateKey = "d}rVH-IMPIu-c3RQ4-[9fn.-R^jDN-sM$Ix-SUk$y";
  private final String publicKey = "p4pay-2min8-v41si-hah14-g5iqg-metp3-4d7tp";
  
  // The Signature That Is Expected To Be Created From Test Data
  private final String expectedSignature = "a08e3586b43abc89d0b27b127c95add1613831f9f4da8b68aa1e60e69334e0da";
  
  @Test
  public void paramDigestTest() throws Exception {
    RestInvocation invocation = testRestInvocation();
    CoinEggDigest digest = CoinEggDigest.createInstance(privateKey);
    
    assertThat(digest.digestParams(invocation)).isEqualTo(expectedSignature);
  }
  
  protected RestInvocation testRestInvocation() {
    Params formParams = Params.of("key", publicKey)
                              .add("type", "all")
                              .add("nonce", "1515959060")
                              .add("since", "0")
                              .add("coin", "eth")
                              .add("signature", "");
   
    Map<Class<? extends Annotation>, Params> params = new HashMap<Class<? extends Annotation>, Params>();
    params.put(FormParam.class, formParams);
    
    return new RestInvocation(params, null, null, "", "", "", "", null);
  }
}
