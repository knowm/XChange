package org.knowm.xchange.bybit.service;

import org.knowm.xchange.exceptions.NotYetImplementedForExchangeException;
import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.Params;
import si.mazi.rescu.ParamsDigest;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.QueryParam;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.TreeMap;

import static org.knowm.xchange.utils.DigestUtils.bytesToHex;

public class BybitDigest extends BaseParamsDigest {

    private static final String SIGNATURE = "sign";

    public BybitDigest(String secretKeyBase64) {
        super(secretKeyBase64, HMAC_SHA_256);
    }

    public static ParamsDigest createInstance(String secretKeyBase64) {
        return new BybitDigest(secretKeyBase64);
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {
        Params p = Params.of();
        Map<String, String> params = getInputParams(restInvocation);
        params.remove(SIGNATURE);
        Map<String, String> sortedParams = new TreeMap<>(params);
        sortedParams.forEach(p::add);
        String input = p.asQueryString();
        Mac mac = getMac();
        mac.update(input.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(mac.doFinal());
    }

    private Map<String, String> getInputParams(RestInvocation restInvocation) {
        if ("GET".equals(restInvocation.getHttpMethod())) {
            return restInvocation.getParamsMap().get(QueryParam.class).asHttpHeaders();
        }
        if ("POST".equals(restInvocation.getHttpMethod())) {
            return restInvocation.getParamsMap().get(FormParam.class).asHttpHeaders();
        }
        throw new NotYetImplementedForExchangeException("Only GET and POST are supported in digest");
    }

}
