package com.knowm.xchange.serum.structures;

import com.igormaznitsa.jbbp.JBBPParser;
import com.igormaznitsa.jbbp.mapper.Bin;
import com.igormaznitsa.jbbp.mapper.BinType;
import com.knowm.xchange.serum.core.Base58;
import com.knowm.xchange.serum.dto.PublicKey;
import com.knowm.xchange.serum.structures.AccountFlagsLayout.AccountFlags;
import com.knowm.xchange.serum.structures.EventFlagsLayout.EventFlags;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EventQueueLayout {

  public static int HEADER_LEN = 37;
  public static int NODE_LEN = 88;

  public static class HeaderLayout extends Struct {

    @Bin(order = 1, name = "blob5", type = BinType.BYTE_ARRAY)
    public byte[] blob5;

    @Bin(order = 2, name = "accountFlags", type = BinType.STRUCT)
    public AccountFlagsLayout accountFlags;

    @Bin(order = 3, name = "head", type = BinType.BYTE_ARRAY)
    public byte[] head;

    @Bin(order = 4, name = "zeros1", type = BinType.BYTE_ARRAY)
    public byte[] zeros1;

    @Bin(order = 5, name = "count", type = BinType.BYTE_ARRAY)
    public byte[] count;

    @Bin(order = 6, name = "zeros2", type = BinType.BYTE_ARRAY)
    public byte[] zeros2;

    @Bin(order = 7, name = "seqNum", type = BinType.BYTE_ARRAY)
    public byte[] seqNum;

    @Bin(order = 8, name = "zeros3", type = BinType.BYTE_ARRAY)
    public byte[] zeros3;

    public static final StructDecoder<Header> DECODER =
        bytes -> {
          final HeaderLayout headerLayout =
              JBBPParser.prepare(
                      ""
                          + "byte[5] blob5; "
                          + "accountFlags {"
                          + " byte[8] bytes; "
                          + "} "
                          + "byte[4] head;"
                          + "byte[4] zeros1;"
                          + "byte[4] count;"
                          + "byte[4] zeros2;"
                          + "byte[4] seqNum;"
                          + "byte[4] zeros3;")
                  .parse(bytes)
                  .mapTo(new HeaderLayout());
          return new Header(
              headerLayout.accountFlags.decode(),
              getUnsignedInt(headerLayout.head),
              getUnsignedInt(headerLayout.count),
              getUnsignedInt(headerLayout.seqNum));
        };
  }

  public static class Header {

    public final AccountFlags accountFlags;
    public final long head;
    public final long count;
    public final long seqNum;

    public Header(final AccountFlags accountFlags, long head, long count, long seqNum) {
      this.accountFlags = accountFlags;
      this.head = head;
      this.count = count;
      this.seqNum = seqNum;
    }
  }

  public static class NodeLayout extends Struct {

    @Bin(order = 1, name = "eventFlags", type = BinType.STRUCT)
    public EventFlagsLayout eventFlags;

    @Bin(order = 2, name = "openOrdersSlot", type = BinType.BYTE)
    public byte openOrdersSlot;

    @Bin(order = 3, name = "feeTier", type = BinType.BYTE)
    public byte feeTier;

    @Bin(order = 4, name = "blob5", type = BinType.BYTE_ARRAY)
    public byte[] blob5;

    @Bin(order = 5, name = "nativeQuantityReleased", type = BinType.BYTE_ARRAY)
    public byte[] nativeQuantityReleased;

    @Bin(order = 6, name = "nativeQuantityPaid", type = BinType.BYTE_ARRAY)
    public byte[] nativeQuantityPaid;

    @Bin(order = 7, name = "nativeFeeOrRebate", type = BinType.BYTE_ARRAY)
    public byte[] nativeFeeOrRebate;

    @Bin(order = 8, name = "orderId", type = BinType.BYTE_ARRAY)
    public byte[] orderId;

    @Bin(order = 9, name = "openOrders", type = BinType.STRUCT)
    public PublicKeyLayout openOrders;

    @Bin(order = 10, name = "clientOrderId", type = BinType.BYTE_ARRAY)
    public byte[] clientOrderId;

    public static final StructDecoder<EventNode> DECODER =
        bytes -> {
          final NodeLayout nodeLayout =
              JBBPParser.prepare(
                      ""
                          + "eventFlags {"
                          + " byte[1] bytes; "
                          + "} "
                          + "byte openOrdersSlot; "
                          + "byte feeTier;"
                          + "byte[5] blob5; "
                          + "byte[8] nativeQuantityReleased;"
                          + "byte[8] nativeQuantityPaid;"
                          + "byte[8] nativeFeeOrRebate;"
                          + "byte[16] orderId;"
                          + "openOrders {"
                          + " byte[32] publicKeyLayout; "
                          + "} "
                          + "byte[8] clientOrderId;")
                  .parse(bytes)
                  .mapTo(new NodeLayout());
          return new EventNode(
              nodeLayout.eventFlags.decode(),
              Byte.toUnsignedInt(nodeLayout.openOrdersSlot),
              Byte.toUnsignedInt(nodeLayout.feeTier),
              decodeLong(nodeLayout.nativeQuantityReleased),
              decodeLong(nodeLayout.nativeQuantityPaid),
              decodeLong(nodeLayout.nativeFeeOrRebate),
              Base58.encode(nodeLayout.orderId),
              nodeLayout.orderId,
              nodeLayout.openOrders.getPublicKey(),
              ByteBuffer.wrap(nodeLayout.clientOrderId).getLong());
        };
  }

  public static class EventNode {

    public final EventFlags eventFlags;
    public final long openOrdersSlot;
    public final long feeTier;
    public final long nativeQuantityReleased;   // amount the user received (nativeQuantityUnlocked)
    public final long nativeQuantityPaid;       // amount the user paid     (nativeQuantityStillLocked)
    public final long nativeFeeOrRebate;
    public final String orderId;
    public final byte[] orderIdBytes;
    public final PublicKey openOrders;
    public final long clientOrderId;

    public EventNode(
        final EventFlags eventFlags,
        long openOrdersSlot,
        long feeTier,
        long nativeQuantityReleased,
        long nativeQuantityPaid,
        long nativeFeeOrRebate,
        final String orderId,
        final byte[] orderIdBytes,
        final PublicKey openOrders,
        long clientOrderId) {
      this.eventFlags = eventFlags;
      this.openOrdersSlot = openOrdersSlot;
      this.feeTier = feeTier;
      this.nativeQuantityReleased = nativeQuantityReleased;
      this.nativeQuantityPaid = nativeQuantityPaid;
      this.nativeFeeOrRebate = nativeFeeOrRebate;
      this.orderId = orderId;
      this.orderIdBytes = orderIdBytes;
      this.openOrders = openOrders;
      this.clientOrderId = clientOrderId;
    }
  }

  public static long getUnsignedInt(byte[] data) {
    ByteBuffer bb = ByteBuffer.wrap(data);
    bb.order(ByteOrder.LITTLE_ENDIAN);
    return bb.getInt() & 0xffffffffL;
  }
}
