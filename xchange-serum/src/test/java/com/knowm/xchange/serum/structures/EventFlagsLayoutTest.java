package com.knowm.xchange.serum.structures;

import org.junit.Assert;
import org.junit.Test;
import org.knowm.xchange.utils.DigestUtils;

public class EventFlagsLayoutTest {

    @Test
    public void test() {
        byte[] bts1 = DigestUtils.hexToBytes("0500000000000000");
        EventFlagsLayout flagsLayout1 = new EventFlagsLayout();
        flagsLayout1.bytes = bts1;
        EventFlagsLayout.EventFlags flg1 = flagsLayout1.decode();
        Assert.assertTrue(flg1.fill);
        Assert.assertFalse(flg1.out);
        Assert.assertTrue(flg1.bid);
        Assert.assertFalse(flg1.maker);

        byte[] bts = DigestUtils.hexToBytes("0600000000000000");
        EventFlagsLayout flagsLayout = new EventFlagsLayout();
        flagsLayout.bytes = bts;
        EventFlagsLayout.EventFlags flg = flagsLayout.decode();
        Assert.assertFalse(flg.fill);
        Assert.assertTrue(flg.out);
        Assert.assertTrue(flg.bid);
        Assert.assertFalse(flg.maker);
    }
}
