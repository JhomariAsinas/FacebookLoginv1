package com.example.jhomasinas.fblogin.FacebookLogin

import com.example.jhomasinas.fblogin.Base.BaseResponse
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface FacebookAPI {

    @GET("me?fields=id,name,first_name,middle_name,last_name,email,gender,birthday,picture.type(large)")
    fun getfields(@Query("access_token") token : String?) : Observable<FBProfile>

    class FBProfile : BaseResponse() {

        @SerializedName("id")
        @Expose
        var id : String? = null

        @SerializedName("name")
        @Expose
        var name : String? = null

        @SerializedName("first_name")
        @Expose
        var fname : String? = null

        @SerializedName("middle_name")
        @Expose
        var mname : String? = null

        @SerializedName("last_name")
        @Expose
        var lname : String? = null

        @SerializedName("email")
        @Expose
        var email : String? = null

        @SerializedName("gender")
        @Expose
        var gender : String? = null

        @SerializedName("picture")
        @Expose
        var picture : FBPicture? = null

        @SerializedName("birthday")
        @Expose
        var birthday : String? = null

        override fun toString(): String {
            return "FBProfile(id=$id, name=$name, fname=$fname, mname=$mname, lname=$lname, email=$email, gender=$gender, picture=$picture, birthday=$birthday)"
        }

    }

    class FBPicture : BaseResponse() {

        @SerializedName("data")
        @Expose
        var data : FBPicData? = null

    }

    class FBPicData : BaseResponse() {

        @SerializedName("cache_key")
        @Expose
        var cachekey : String? = null

        @SerializedName("height")
        @Expose
        var height : Int? = null

        @SerializedName("is_silhouette")
        @Expose
        var is_silhouette : Boolean? = null

        @SerializedName("url")
        @Expose
        var url : String? = null

        @SerializedName("width")
        @Expose
        var width : Int? = null

    }


    companion object {
        val BASE_URL = "https://graph.facebook.com/v3.0/"
        fun create(): FacebookAPI {
            val retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(
                            OkHttpClient().newBuilder().addInterceptor(
                                    HttpLoggingInterceptor().apply { this.level = HttpLoggingInterceptor.Level.BODY }
                            ).build()
                    )
                    .build()
            return retrofit.create(FacebookAPI::class.java)
        }
    }


}