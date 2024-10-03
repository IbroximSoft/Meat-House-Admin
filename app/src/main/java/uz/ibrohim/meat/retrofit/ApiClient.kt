package uz.ibrohim.meat.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.ibrohim.meat.login.services.LoginServices

object ApiClient {

    private const val base_url = "http://104.248.171.154/"

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    // Response code test
    var client: OkHttpClient = OkHttpClient.Builder()
//        .addInterceptor(ChuckerInterceptor(App.instance))
        .build()


    val apiServices: LoginServices = getRetrofit().create(LoginServices::class.java)

}