package com.knowm.xchange.serum.structures;

import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;

public class EventFlagsLayout extends Struct {

    @Bin(order = 1, name = "bytes", type = BinType.BYTE_ARRAY)
    byte[] bytes;

    /** There is probably a cleaner way to do this but making it work for now */
    public EventFlags decode() {
        final byte[] copy = new byte[8];
        System.arraycopy(bytes, 0, copy, 0, bytes.length);
        boolean[] results = booleanFlagsDecoder(copy, 4);
        return new EventFlags(results[0], results[1], results[2], results[3]);
    }

    public static class EventFlags {

        public boolean fill;
        public boolean out;
        public boolean bid;
        public boolean maker;

        EventFlags(boolean fill, boolean out, boolean bid, boolean maker) {
            this.fill = fill;
            this.out = out;
            this.bid = bid;
            this.maker = maker;
        }
    }
}
