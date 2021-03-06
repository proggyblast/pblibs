package com.pblibs.pbrestclient.retrofit.service

import android.content.Context
import com.pblibs.base.PBApplication
import com.pblibs.utility.PBConstants
import com.pblibs.utility.PBSessionManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Proggy Blast on 16/5/20 6:56 PM
 */

class RetrofitService {

    companion object {

        private val context = PBApplication.getInstance().context
        private val sessionManager = PBSessionManager.getInstance(context)

        val retrofit = Retrofit.Builder().baseUrl(sessionManager.getString(PBConstants.BASE_URL, ""))
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttpClient(context))
            .build()

        fun <S> createService(serviceClass: Class<S>?): S {
            return retrofit.create(serviceClass)
        }

        private fun okhttpClient(context: Context): OkHttpClient {
            val builder=OkHttpClient.Builder()
            builder.addInterceptor(AuthInterceptor(context))
            builder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            return builder.build()
        }
    }


}