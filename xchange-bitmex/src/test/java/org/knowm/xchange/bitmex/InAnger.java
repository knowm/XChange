package org.knowm.xchange.bitmex;


import org.knowm.xchange.bitmex.auth.ApiKeyAuth;
import org.knowm.xchange.bitmex.dto.BitmexInstrument;
import org.knowm.xchange.bitmex.dto.BitmexWallet;
import org.knowm.xchange.bitmex.service.InstrumentApi;
import org.knowm.xchange.bitmex.service.UserApi;
import org.knowm.xchange.bitmex.util.ApiClient;
import org.knowm.xchange.bitmex.util.ApiException;
import org.knowm.xchange.bitmex.util.Configuration;

import java.util.List;

import static org.knowm.xchange.bitmex.util.ApiClient.NONCE_FACTORY;

class InAnger {
    public static void main(String[] args) {
        ApiClient defaultClient = Configuration.getDefaultApiClient();

// Configure API key authorization: apiKey
        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("apiKey");
        apiKey.setApiKey(args[0]);
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//apiKey.setApiKeyPrefix("Token");

// Configure API key authorization: apiNonce
        ApiKeyAuth apiNonce = (ApiKeyAuth) defaultClient.getAuthentication("apiNonce");
        apiNonce.setApiKey(args.length > 2 ? args[2] : "" + NONCE_FACTORY.createValue());

// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//apiNonce.setApiKeyPrefix("Token");

// Configure API key authorization: apiSignature
        ApiKeyAuth apiSignature = (ApiKeyAuth) defaultClient.getAuthentication("apiSignature");
        apiSignature.setApiKey(args[1]);
// Uncomment the following line to set a prefix for the API key, e.g. "Token" (defaults to null)
//apiSignature.setApiKeyPrefix("Token");
        InstrumentApi instrumentApi = new InstrumentApi(defaultClient);
        try {

            List<BitmexInstrument> xbtm15 = instrumentApi.instrumentGet(null, "{\"symbol\": \"XBTM15\"}", null, null, null, null, null, null);
            System.err.println(xbtm15.toString());
        } catch (ApiException e) {
            e.printStackTrace();
        }
        //reload
        apiSignature.setApiKey(args[1]);
        UserApi apiInstance = new UserApi(defaultClient);
        String currency = "XBt"; // String |
        try {

            BitmexWallet result = apiInstance.userGetWallet(currency);
            System.out.println(result.toString());
        } catch (ApiException e) {
            System.err.println("Exception when calling UserApi#userGetWalletSummary");
            e.printStackTrace();
        }
    }
}