package org.knowm.xchange.ftx.service;

import org.bouncycastle.util.encoders.Hex;
import org.knowm.xchange.exceptions.ExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;
import java.nio.charset.StandardCharsets;

public class FtxDigest extends BaseParamsDigest {

    private FtxDigest(byte[] secretKey) {

        super(secretKey, HMAC_SHA_256);
    }

    public static FtxDigest createInstance(String secretKey) {

        if (secretKey != null) {
            return new FtxDigest(secretKey.getBytes());
        } else return null;
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {

        String message = restInvocation.getParamValue(HeaderParam.class, "FTX-TS").toString()
            +restInvocation.getHttpMethod().toUpperCase()
            +restInvocation.getPath();

        if(restInvocation.getHttpMethod().equals("POST")) {
            message += restInvocation.getRequestBody();
        }

        Mac mac256 = getMac();
        System.out.println(message);

        try {
            mac256.update(message.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new ExchangeException("Digest encoding exception", e);
        }

        return Hex.toHexString(mac256.doFinal()).toLowerCase();
    }
}
