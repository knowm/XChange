package org.knowm.xchange.idex

import org.knowm.xchange.dto.*


operator fun Order.OrderType.get(type: String): Order.OrderType {
    return when (type) {
        "buy" -> Order.OrderType.BID
        "sell" -> Order.OrderType.ASK
        else -> TODO("need to parse for ordertype." + type)
    }
}
