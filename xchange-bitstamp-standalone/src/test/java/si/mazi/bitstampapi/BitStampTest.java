package si.mazi.bitstampapi;

import org.testng.annotations.Test;

/**
 * @author Matija Mazi <br/>
 */
public class BitStampTest {

    @Test
    public void testBsApi() throws Exception {
        BitStamp bitStamp = BitstampFactory.createResteasyEndpoint();
        System.out.println(bitStamp.getTicker());
        System.out.println(bitStamp.getOrderBook());
    }
}
