package fr.imt.vrignaud

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface BookService {

    @GET("books")
    suspend fun getBooks(): List<Book>

    @GET("books/{ids}/commercialOffers")
    suspend fun getCommercialOffers(@Path("ids") ids: String): OffersWrapper

    companion object {
        private const val BASE_URL = "http://henri-potier.xebia.fr/"

        fun create(): BookService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BookService::class.java)
        }
    }
}