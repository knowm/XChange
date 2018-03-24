package org.knowm.xchange.idex

import org.knowm.xchange.dto.meta.*
import java.math.*

class IdexCurrencyMeta(scale: Int, withdrawalFee: BigDecimal?, val address: String,
                       val name: String, val decimals: BigInteger) : CurrencyMetaData(scale, withdrawalFee)