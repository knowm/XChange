package org.knowm.xchange.quoine.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.ws.rs.HeaderParam;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import org.knowm.xchange.service.BaseParamsDigest;

import net.iharder.Base64;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

public class QuoineSignatureDigest implements ParamsDigest {

    private final JWTCreator.Builder builder;
    private final String tokenID;
    private final byte[] userSecret;

    public QuoineSignatureDigest(String tokenID, String userSecret) {
        this.tokenID = tokenID;
        this.userSecret = userSecret.getBytes();

        this.builder = JWT.create();
    }

    public ParamsDigest getContentMD5Digester() {
        return new QuoineContentMD5Digest();
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {

        String path = "/" + restInvocation.getMethodPath();

        final String sign = builder
                .withClaim("path", path)
                .withClaim("nonce", String.valueOf(System.nanoTime()))
                .withClaim("token_id", tokenID)
                .sign(Algorithm.HMAC256(userSecret));

        return sign;
    }

    private String getContentMD5(String content) {
        if(content == null || "".equals(content)) {
            return "";
        }
        String digest = null;
        try {
            byte[] bytesOfMessage = content.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            digest = Base64.encodeBytes(md.digest(bytesOfMessage));
        } catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return digest;
    }

    private class QuoineContentMD5Digest implements ParamsDigest {

        @Override
        public String digestParams(RestInvocation restInvocation) {

            return getContentMD5(restInvocation.getRequestBody());
        }
    }
}
