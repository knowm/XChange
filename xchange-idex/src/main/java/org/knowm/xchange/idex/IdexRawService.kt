package org.knowm.xchange.idex

import com.squareup.okhttp.*
import com.squareup.okhttp.internal.http.*
import org.knowm.xchange.idex.service.*
import org.knowm.xchange.idex.util.*

object IdexRawService : DefaultApi() {
    init {

        apiClient = object : ApiClient() {
            override fun buildRequest(path: String?, method: String?, queryParams: MutableList<Pair>?,
                                      collectionQueryParams: MutableList<Pair>?,
                                      body: Any?,
                                      headerParams: MutableMap<String, String>?,
                                      formParams: MutableMap<String, Any>?,
                                      authNames: Array<out String>?,
                                      progressRequestListener: ProgressRequestBody.ProgressRequestListener?): Request {

                val url = buildUrl(path, queryParams, collectionQueryParams)
                val reqBuilder = Request.Builder().url(url)

                val contentType = headerParams!!["Content-Type"] ?: "application/json"

                val permitsRequestBody = HttpMethod.permitsRequestBody(method)

                val reqBody = when {
                    permitsRequestBody -> {
                        val mediaType = MediaType.parse(contentType)
                        val json = JSON().gson.toJson(formParams)
                        RequestBody.create(mediaType,
                                           json)
                    }
                    else -> null
                }
                reqBuilder.headers(Headers.of(headerParams))
                var request: Request? = null
                updateParamsForAuth(authNames, queryParams, headerParams)
                if (progressRequestListener != null && reqBody != null) {
                    val progressRequestBody = ProgressRequestBody(reqBody, progressRequestListener)
                    request = reqBuilder.method(method, progressRequestBody).build()
                } else {
                    request = reqBuilder.method(method, reqBody).build()
                }
                return request
            }
        }
    }
}