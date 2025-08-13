 package com.client.recouvrementapp.data.remote

import android.content.Context
import android.util.Log
import com.client.recouvrementapp.data.local.Constantes.Companion.HOST_DEV
import com.client.recouvrementapp.data.local.Constantes.Companion.HOST_PROD
import com.client.recouvrementapp.data.local.Constantes.Companion.IS_PROD
import com.client.recouvrementapp.data.shared.StoreData
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.observer.ResponseObserver
import io.ktor.client.request.accept
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.URLProtocol
import io.ktor.http.contentType
import io.ktor.http.encodedPath
import io.ktor.http.headers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json


//val httpClientAndroid = HttpClient(Android) {
//    install(ContentNegotiation) {
//        json(
//            Json {
//                prettyPrint = true
//                isLenient = true
//                useAlternativeNames = true
//                ignoreUnknownKeys = true
//                encodeDefaults = true
//            }
//        )
//    }
//    install(HttpTimeout) {
//        requestTimeoutMillis = NETWORK_TIME_OUT
//        connectTimeoutMillis = NETWORK_TIME_OUT
//        socketTimeoutMillis = NETWORK_TIME_OUT
//    }
//    install(ResponseObserver) {
//        onResponse { response ->
//            Log.d("HTTP status:", "${response.status.value}")
//        }
//    }
//    install(DefaultRequest) {
//        header(HttpHeaders.ContentType, ContentType.Application.Json)
//    }
////    if (tokenAccess != ""){
////        install(Auth) {
////            bearer {
////                loadTokens {
////                    BearerTokens(
////                        accessToken = tokenAccess,
////                        refreshToken = ""
////                    )
////                }
////            }
////        }
////    }
//    defaultRequest {
//        contentType(ContentType.Application.Json)
//        accept(ContentType.Application.Json)
//    }
//}
class HttpClientAndroidBuild(var tokenAccess: String = ""){
    private val NETWORK_TIME_OUT = 6_000L
        val httpClientAndroid = HttpClient(Android) {
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        useAlternativeNames = true
                        ignoreUnknownKeys = false
                        encodeDefaults = true
                    }
                )
            }
            install(HttpTimeout) {
                requestTimeoutMillis = NETWORK_TIME_OUT
                connectTimeoutMillis = NETWORK_TIME_OUT
                socketTimeoutMillis = NETWORK_TIME_OUT
            }
            install(ResponseObserver) {
                onResponse { response ->
                    Log.d("HTTP status:", "${response.status.value}")
                }
            }
            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
            }
            if (tokenAccess != ""){
                install(Auth) {
                    bearer {
                        loadTokens {
                            BearerTokens(
                                accessToken = tokenAccess,
                                refreshToken = ""
                            )
                        }
                    }
                }
            }
            defaultRequest {
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
            }
        }
}
class KtorClientAndroid {

    suspend fun postData(route: String, data: Any, token: String = "", typeToken: String = "Bearer"): HttpResponse {
        return HttpClientAndroidBuild(token).httpClientAndroid.post {
            url {
                protocol = if (IS_PROD) URLProtocol.HTTPS else URLProtocol.HTTP
                host = if (IS_PROD) HOST_PROD else HOST_DEV
                encodedPath = route
            }
            headers {
                append(HttpHeaders.ContentType, "application/json")
            }
             // Serialize the body
            setBody(data)
        }
    }
    suspend fun getData(route: String, token : String = "", typeToken : String = "Bearer"): HttpResponse {
        return HttpClientAndroidBuild(token).httpClientAndroid.get {
            url {
                protocol = if (IS_PROD) URLProtocol.HTTPS else URLProtocol.HTTP
                host = if (IS_PROD) HOST_PROD else HOST_DEV
                encodedPath = route
            }
            headers {
//                Log.e("header security ->>>",token)
//                Log.e("header security typeToken->>>",typeToken)
                append(HttpHeaders.ContentType, "application/json")
            }
        }
    }
}

data class TokenModel(
    val token : String,
    val tokenType : String
)
suspend fun requestServer(context : Context, data : Any = {}, route : String, method : String = "GET"): HttpResponse {
    val channel = Channel<TokenModel>()
    CoroutineScope(Dispatchers.IO).launch {
        StoreData(context).getUser.collect {
            if (it.access_token.isNotEmpty()){
                channel.send(TokenModel(it.access_token,it.token_type))
                Log.i("request server ->","Elie Oko")
                Log.i("request server ->","$")
            }
        }
    }
    val tokenModel = channel.receive()
    //token = channel.receive()
    Log.i("token server ->",tokenModel.token)
    Log.w("method server ->", method.lowercase())
    return when(method.lowercase()){
        "get"  -> KtorClientAndroid().getData(route, tokenModel.token, typeToken = tokenModel.tokenType)
        "post" -> KtorClientAndroid().postData(route = route, data = data, token = tokenModel.token, typeToken = tokenModel.tokenType)
        else -> {
        }
    } as HttpResponse
}

