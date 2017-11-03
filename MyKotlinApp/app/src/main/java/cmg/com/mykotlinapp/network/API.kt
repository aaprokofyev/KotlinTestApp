package cmg.com.mykotlinapp.network

import cmg.com.mykotlinapp.datamodel.User
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * Created by alprokof on 02.11.17.
 */

object API {
    val baseUrl = "https://api.github.com/"

    val apiInstance: EndpointInterface

    init {
        apiInstance = Retrofit.Builder().baseUrl(baseUrl).build().create(EndpointInterface::class.java);
    }

    interface EndpointInterface {
        @GET("users/")
        fun getUsers(@Query("since") sinceId: String): Call<List<User>>
    }
}
