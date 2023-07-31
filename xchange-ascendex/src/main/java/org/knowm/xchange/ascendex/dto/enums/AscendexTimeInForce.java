package org.knowm.xchange.ascendex.dto.enums;

/**
 * Package:org.knowm.xchange.ascendex.dto.enums
 * Description:
 *
 * @date:2022/7/20 9:41
 * @author:wodepig
 */
public enum AscendexTimeInForce {
    // good-till-canceled有效直到取消
    GTC,
    // immediate-or-cancel立即成交或取消
    IOC,
    // fill-or-kill全部成交或取消
    FOK;
}
