package com.example.android.roomwordsample.network

//const val BASE_URL_NEWS = "http://newsapi.org/v2/"


//private val moshi = Moshi.Builder()
//    .add(KotlinJsonAdapterFactory())
//    .build()

//private val retrofitNews = Retrofit.Builder()
//    .addConverterFactory(MoshiConverterFactory.create(moshi))
//    .baseUrl(BASE_URL_NEWS)
//    .build()

interface ApiRequests {

//    @GET("/facts/random")
//    fun getCatFacts(): Call<CatJson>

//    @GET("top-headlines")
//    fun getHeadlines(
//        @Query("country") country: String,
//        @Query("category") category: String,
//        @Query("apiKey") apiKey: String
//    ): Call<NewsJson>

}

//object NewsApi {
//    val retrofitNewsService: ApiRequests by lazy {
//        retrofitNews.create(ApiRequests::class.java)
//    }
//}


//object ApiClient {
//    /**
//     * Get Retrofit Instance
//     */
//    private val retrofitInstance: Retrofit
//        private get() = Retrofit.Builder()
//            .baseUrl(Contants.BASE_URL)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//    /**
//     * Get API Service
//     *
//     * @return API Service
//     */
//    val apiService: ApiResponse
//        get() = retrofitInstance.create(ApiResponse::class.java)
//}


//object Internet {
//    /**
//     * CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT
//     */
//    fun checkConnection(@NonNull context: Context): Boolean {
//        return (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null
//    }
//}

//class NewsResource {
//    @SerializedName("articles")
//    @Expose
//    var articles: ArrayList<News>? = null
//}