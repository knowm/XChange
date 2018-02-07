package org.knowm.xchange.bitmex.service;

import org.knowm.xchange.service.BaseParamsDigest;
import org.knowm.xchange.utils.DigestUtils;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.FormParam;

import static java.nio.charset.StandardCharsets.UTF_8;

public class BitmexDigest extends BaseParamsDigest {

    private String apiKey;

    /**
     * Constructor
     *
     * @param apiSecret
     */

    private BitmexDigest(byte[] apiSecret) {
        super(apiSecret, HMAC_SHA_256);
    }

    private BitmexDigest(String apiSecret, String apiKey) {
        super(apiSecret, HMAC_SHA_256);
        this.apiKey = apiKey;
    }

    public static BitmexDigest createInstance(String apiSecret) {

        if (apiSecret != null)
            return new BitmexDigest(/*Base64.decode*/(apiSecret.getBytes()));
        return null;
    }

    public static BitmexDigest createInstance(String apiSecret, String apiKey) {

        return apiSecret == null ? null : new BitmexDigest(apiSecret, apiKey);
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {

        Mac mac = getMac();

        mac.reset();
        mac.update(restInvocation.getHttpMethod().getBytes(UTF_8));
        String queryString = restInvocation.getQueryString();
        queryString = queryString == null || queryString.isEmpty() ? "" : "?" + queryString;
        mac.update(("/" + restInvocation.getPath()+queryString).getBytes(UTF_8)   );
        mac.update(restInvocation.getParamValue(FormParam.class, "nonce").toString().getBytes(UTF_8));
        mac.update(restInvocation.getRequestBody().getBytes(UTF_8));
        return DigestUtils.bytesToHex(mac.doFinal());

    }



    public String digestParams(String payload) {

        Mac mac = getMac();
        mac.reset();
        mac.update(payload.getBytes(UTF_8));
        return  DigestUtils.bytesToHex(mac.doFinal() );

    }

}
