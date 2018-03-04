package org.knowm.xchange.cryptopia;

import org.knowm.xchange.service.BaseParamsDigest;
import si.mazi.rescu.RestInvocation;
import si.mazi.rescu.SynchronizedValueFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class CryptopiaDigest extends BaseParamsDigest {

    private final SynchronizedValueFactory<Long> nonceFactory;
    private final String apiKey;
    private final String secretKey;

    private CryptopiaDigest(SynchronizedValueFactory<Long> nonceFactory, String secretKey, String apiKey) {
        super(BaseParamsDigest.decodeBase64(secretKey), BaseParamsDigest.HMAC_SHA_256);

        this.nonceFactory = nonceFactory;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
    }

    public static CryptopiaDigest createInstance(SynchronizedValueFactory<Long> nonceFactory, String secretKey, String apiKey) {
        if (secretKey == null)
            return null;

        try {
            return new CryptopiaDigest(nonceFactory, secretKey, apiKey);
        } catch (Exception e) {
            throw new IllegalStateException("cannot create digest", e);
        }
    }

    @Override
    public String digestParams(RestInvocation restInvocation) {
        try {

            String urlMethod = CryptopiaDigest.privateUrl + restInvocation.getHttpMethod();
            String nonce = String.valueOf(System.currentTimeMillis());

            String reqSignature =
                    this.apiKey
                            + "POST"
                            + URLEncoder.encode(urlMethod, StandardCharsets.UTF_8.toString()).toLowerCase()
                            + nonce
                            + getMD5_B64(restInvocation.getRequestBody());

            String AUTH = "amx "
                    + this.apiKey
                    + ":"
                    + sha256_B64(secretKey, reqSignature)
                    + ":"
                    + nonce;
            return AUTH;
        } catch (Exception e) {
            throw new IllegalStateException("Faile to sign request", e);
        }
    }

    public static String privateUrl = "https://www.cryptopia.co.nz/Api/";

    public static class HttpCryptopia {
        public final String response;

        public HttpCryptopia(String method, String jSonPostParam, String apiKey, String secretKey) throws Exception {
            String urlMethod = privateUrl + method;
            String nonce = String.valueOf(System.currentTimeMillis());

            String reqSignature =
                    apiKey
                            + "POST"
                            + URLEncoder.encode(urlMethod, StandardCharsets.UTF_8.toString()).toLowerCase()
                            + nonce
                            + CryptopiaDigest.getMD5_B64(jSonPostParam);

            String AUTH = "amx "
                    + apiKey
                    + ":"
                    + sha256_B64(secretKey, reqSignature)
                    + ":"
                    + nonce;

            this.response = continueForHttp(urlMethod, jSonPostParam, AUTH);


            if (HttpCryptopia.debugMe) System.out.println("API RESPONSE : " + response);
        }

        static final Boolean debugMe = System.getProperty("XChangeDebug", "false") == "true";

        private String continueForHttp(String urlMethod, String postParam, String AUTH) throws Exception {
            URLConnection con = new URL(urlMethod).openConnection(); // CREATE POST CONNECTION
            con.setDoOutput(true);

            HttpsURLConnection httpsConn = (HttpsURLConnection) con;
            httpsConn.setRequestMethod("POST");
            httpsConn.setInstanceFollowRedirects(true);

            con.setRequestProperty("Authorization", AUTH);
            con.setRequestProperty("Content-Type", "application/json");

            // WRITE POST PARAMS
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(postParam);
            wr.flush();
            wr.close();

            // READ THE RESPONSE
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        }


    }

    static String getMD5_B64(String postParameter) throws Exception {
        return java.util.Base64.getEncoder().encodeToString(MessageDigest.getInstance("MD5").digest(postParameter.getBytes("UTF-8")));
    }

    static String sha256_B64(String secretKey, String msg) throws Exception {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(java.util.Base64.getDecoder().decode(secretKey), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return java.util.Base64.getEncoder().encodeToString(sha256_HMAC.doFinal(msg.getBytes("UTF-8")));
    }
}