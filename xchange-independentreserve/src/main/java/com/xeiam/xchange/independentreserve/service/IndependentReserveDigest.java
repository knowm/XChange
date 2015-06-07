package com.xeiam.xchange.independentreserve.service;

import com.xeiam.xchange.independentreserve.util.ExchangeEndpoint;
import com.xeiam.xchange.service.BaseParamsDigest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import si.mazi.rescu.RestInvocation;

import javax.crypto.Mac;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Author: Kamil Zbikowski
 * Date: 4/10/15
 */
public class IndependentReserveDigest extends BaseParamsDigest {

    private final Logger logger = LoggerFactory.getLogger(IndependentReserveDigest.class);

    private final String apiKey;
    private final String sslUri;


    private IndependentReserveDigest(String secretKeyBase64, String apiKey, String sslUri) {
        super(secretKeyBase64, HMAC_SHA_256);
        this.apiKey = apiKey;
        this.sslUri = sslUri;
    }

    public static IndependentReserveDigest createInstance(String secretKeyBase64, String apiKey, String sslUri) {
        return secretKeyBase64 == null ? null : new IndependentReserveDigest(secretKeyBase64, apiKey, sslUri);
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {
        throw new IllegalStateException("For Independent Reserve one should use digestParamsToString method instead");
    }

    public String digestParamsToString(ExchangeEndpoint endpoint, Long nonce, Map<String, String> parameters){
        Mac mac256 = getMac();

        String url = ExchangeEndpoint.getUrlBasingOnEndpoint(sslUri, endpoint)+",";
        logger.debug("digestParamsToString: url: {}", url);
        mac256.update(url.getBytes());

        String namedApiKey = "apiKey=" + apiKey + ",";
        logger.debug("digestParamsToString: apiKey: {}",namedApiKey);
        mac256.update(namedApiKey.getBytes());

        String namedNonce = "nonce=" + nonce.toString();
        logger.debug("digestParamsToString: nonce: {}",namedNonce);
        mac256.update(namedNonce.getBytes());

        if(parameters != null && parameters.size() > 0) {
            List<String> namedParameters = new ArrayList<String>();
            for (Map.Entry<String, String> parameter : parameters.entrySet()) {
                String namedParameter = parameter.getKey() + "=" + parameter.getValue();
                namedParameters.add(namedParameter);
            }
            String joinedNamedParameters="";
            for(String namedParameter : namedParameters){
                joinedNamedParameters += namedParameter + ",";
            }
            joinedNamedParameters = joinedNamedParameters.substring(0,joinedNamedParameters.length()-1);
            if (!joinedNamedParameters.equals("")) {
                joinedNamedParameters=","+joinedNamedParameters;
                mac256.update(joinedNamedParameters.getBytes());
            }
        }
        return String.format("%064x", new BigInteger(1, mac256.doFinal())).toUpperCase();
    }
}

