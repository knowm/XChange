package org.knowm.xchange.idex;

import org.apache.commons.codec.binary.Hex;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.crypto.Sign.SignatureData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Arrays.asList;
import static org.web3j.crypto.Hash.sha3;

public class IdexSignature {
    static Boolean debugMe = IdexMarketDataService.Companion.getDebugMe();

    static/** Generate v, r, s values from payload */
    SignatureData generateSignature(String apiSecret, List<List<String>> data) throws IOException {
        ByteBuffer sig_arr = ByteBuffer.allocate(4096);

        if (debugMe) {
            System.err.println(data);
        }
        data.stream().forEach(


                (List<String> d) -> {
                    String data1 = d.get(1);
                    /* remove 0x prefix and convert to bytes*/
                    if (debugMe) System.err.println("\n===\nsignature  for (len: " + d.get(1).length() + " ):" + d);
                    byte[] segment = new byte[0];
                    byte[] r;
                    String last = new LinkedList<>(asList(data1.toLowerCase().split("0x"))).getLast();
                    switch (d.get(2)) {
                        case "address": {
                            segment = new byte[20];

                            break;
                        }

                        case "uint256": {
                            segment = new byte[32];

                            break;
                        }

                    }
                    r = new BigInteger(last, 16).toByteArray();
                    int segLen = segment.length;
                    int rlen = min(max(segLen, r.length), r.length);
                    int oversize = r.length - segLen;
                    System.arraycopy(r, oversize > 0?oversize:0, segment, oversize > 0?0:(segLen - rlen), min(segLen,rlen));
                    ByteBuffer snap = (ByteBuffer) sig_arr.duplicate().flip();
                    sig_arr.put(segment);

                    if (debugMe) {
                        System.err.println("signature: results: (len: " + rlen+"->" +segLen + ") " + Hex.encodeHexString(segment));
                        System.err.println("signature: accumulated: " + Hex.encodeHexString(buf2byte(sig_arr)));

                    }
                });

        // hash the packed string
        byte[] rawhash = new byte[0];

        rawhash = sha3(buf2byte(sig_arr));

        // salt the hashed packed string
        byte[] saltBytes = "\u0019Ethereum Signed Message:\n32".getBytes();

        assert (Hex.encodeHexString(
                saltBytes).toLowerCase() == "19457468657265756d205369676e6564204d6573736167653a0a3332");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byteArrayOutputStream.write(saltBytes);
        byteArrayOutputStream.write(rawhash);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        byte[] salted = sha3(bytes);
        if (debugMe) {
            System.err.println("\n== unsalted:\t" + Hex.encodeHexString(rawhash));
            System.err.println("== salt raw :\t" + Hex.encodeHexString(saltBytes));
            System.err.println("== salted raw :\t" + Hex.encodeHexString(bytes));
            System.err.println("== salted hash:\t" + Hex.encodeHexString(salted));
        }
        String last = new LinkedList<String>(asList(apiSecret.split("0x"))).getLast();
        BigInteger apiSecret1 = new BigInteger(last, 16);

        ECKeyPair ecKeyPair = ECKeyPair.create(apiSecret1);


        return
                Sign.signMessage(
                        /* message = */salted,
                        /* keyPair=  */ ecKeyPair
                );
    }

    private static byte[] buf2byte(ByteBuffer sig_arr) {
        return UTF_8.decode((ByteBuffer) ((ByteBuffer) sig_arr.duplicate()).flip()).toString().getBytes();
    }
}