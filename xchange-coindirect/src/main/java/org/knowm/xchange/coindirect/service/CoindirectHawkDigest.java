package org.knowm.xchange.coindirect.service;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

public class CoindirectHawkDigest extends BaseParamsDigest {

    private CoindirectHawkDigest(String apiKey, String secretKeyBase64) {
        super(secretKeyBase64, HMAC_SHA_256);
    }

    public static CoindirectHawkDigest createInstance(String apiKey, String secretKey) {
        return secretKey == null || apiKey == null ? null : new CoindirectHawkDigest(apiKey, secretKey);
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {
        return null;
    }
}
