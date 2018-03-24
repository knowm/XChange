package org.knowm.xchange.idex

import org.apache.commons.codec.binary.Hex
import org.web3j.crypto.ECKeyPair
import org.web3j.crypto.Sign
import org.web3j.crypto.Sign.SignatureData
import org.web3j.crypto.TransactionEncoder

import java.io.ByteArrayOutputStream
import java.io.IOException
import java.math.BigInteger
import java.nio.ByteBuffer
import java.util.LinkedList

import java.lang.Integer.max
import java.lang.Integer.min
import java.nio.charset.StandardCharsets.UTF_8
import java.util.Arrays.asList
import org.web3j.crypto.Hash.sha3

object IdexSignature {
    internal var debugMe: Boolean? = IdexMarketDataService.debugMe

    internal
            /** Generate v, r, s values from payload  */
    fun generateSignature(apiSecret: String, data: List<List<String>>): SignatureData {
        var rawhash: ByteArray? = null
        val saltBytes: ByteArray
        var bytes: ByteArray? = null
        val last = arrayOfNulls<String>(1)
        val apiSecret1: BigInteger
        val ecKeyPair: ECKeyPair
        try {
            ByteArrayOutputStream().use { sig_arr ->

                if (debugMe!!) {
                    System.err.println(data)
                }
                for (d in data) {
                    val data1 = d[1]
                    /* remove 0x prefix and convert to bytes*/
                    if (debugMe!!) System.err.println("\n===\nsignature  for (len: " + d[1].length + " ):" + d)
                    var segment = ByteArray(0)
                    var r = ByteArray(0)
                    last[0] = LinkedList(asList(*data1.toLowerCase().split(
                            "0x".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())).last
                    when (d[2]) {
                        "address" -> {
                            segment = ByteArray(20)
                            r = BigInteger(last[0], 16).toByteArray()
                        }

                        "uint256" -> {
                            segment = ByteArray(32)
                            r = BigInteger(last[0], 10).toByteArray()
                        }
                    }
                    val segLen = segment.size
                    val rlen = min(max(segLen, r.size), r.size)
                    val oversize = r.size - segLen
                    System.arraycopy(r, if (oversize > 0) oversize else 0, segment,
                                     if (oversize > 0) 0 else segLen - rlen, min(segLen, rlen))

                    sig_arr.write(segment)

                    if (debugMe!!) {
                        System.err.println(
                                "signature: results: (len: " + rlen + "->" + segLen + ") " + Hex.encodeHexString(
                                        segment))
                        System.err.println("signature: accumulated: " + Hex.encodeHexString(sig_arr.toByteArray()))
                    }
                }
                rawhash = sha3(sig_arr.toByteArray())
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // salt the hashed packed string
        saltBytes = "\u0019Ethereum Signed Message:\n32".toByteArray()

        assert(Hex.encodeHexString(
                saltBytes).toLowerCase() === "19457468657265756d205369676e6564204d6573736167653a0a3332")

        var salted: ByteArray? = null
        try {
            ByteArrayOutputStream().use { byteArrayOutputStream ->
                byteArrayOutputStream.write(saltBytes)
                byteArrayOutputStream.write(rawhash!!)
                bytes = byteArrayOutputStream.toByteArray()
                salted = bytes
                //            sha3(bytes);
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        if (debugMe!!) {
            System.err.println("\n== unsalted:\t" + Hex.encodeHexString(rawhash!!))
            System.err.println("== salt raw :\t" + Hex.encodeHexString(saltBytes))
            System.err.println("== salted raw :\t" + Hex.encodeHexString(bytes!!))
            System.err.println("== salted hash:\t" + Hex.encodeHexString(salted!!))
        }
        last[0] = LinkedList(
                asList(*apiSecret.split("0x".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray())).last
        apiSecret1 = BigInteger(last[0], 16)
        ecKeyPair = ECKeyPair.create(apiSecret1)


        val signatureData: SignatureData
        signatureData = Sign.signMessage(
                /* message = */salted!!,
                /* keyPair=  */ ecKeyPair
        )
        return signatureData
    }


}