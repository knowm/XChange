package com.knowm.xchange.serum.structures;

import org.junit.Assert;
import org.junit.Test;

public class EventFlagsLayoutTest {

    @Test
    public void test() {
        byte[] bts1 = fromHexString("0500000000000000");
        EventFlagsLayout flagsLayout1 = new EventFlagsLayout();
        flagsLayout1.bytes = bts1;
        EventFlagsLayout.EventFlags flg1 = flagsLayout1.decode();
        Assert.assertTrue(flg1.fill);
        Assert.assertFalse(flg1.out);
        Assert.assertTrue(flg1.bid);
        Assert.assertFalse(flg1.maker);

        byte[] bts = fromHexString("0600000000000000");
        EventFlagsLayout flagsLayout = new EventFlagsLayout();
        flagsLayout.bytes = bts;
        EventFlagsLayout.EventFlags flg = flagsLayout.decode();
        Assert.assertFalse(flg.fill);
        Assert.assertTrue(flg.out);
        Assert.assertTrue(flg.bid);
        Assert.assertFalse(flg.maker);
    }

    private static byte[] fromHexString(final String encoded) {
        if ((encoded.length() % 2) != 0)
            throw new IllegalArgumentException("Input string must contain an even number of characters");

        final byte[] result = new byte[encoded.length()/2];
        final char[] enc = encoded.toCharArray();
        for (int i = 0; i < enc.length; i += 2) {
            StringBuilder curr = new StringBuilder(2);
            curr.append(enc[i]).append(enc[i + 1]);
            result[i/2] = (byte) Integer.parseInt(curr.toString(), 16);
        }
        return result;
    }
}
