package org.knowm.xchange.idex

import org.knowm.xchange.dto.meta.*
import java.math.*

class IdexCurrencyMeta(scale: Int, withdrawalFee: BigDecimal?, val meta: String,
                       val name: String, val decimals: Int) : CurrencyMetaData(scale, withdrawalFee) {

}