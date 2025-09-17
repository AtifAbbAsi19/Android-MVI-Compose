package com.mak.androidmvi.network

/*
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
        encodeDefaults = true
    }

    @Provides
    @Singleton
    fun provideHttpClient(json: Json, @ApplicationContext context: Context): HttpClient {
        return HttpClient(OkHttp) {
            engine {
                // OkHttp-specific config
                preconfigured = okhttp3.OkHttpClient.Builder()
                    .retryOnConnectionFailure(true)
                    .build()
            }

            install(ContentNegotiation) {
                json(json)
            }

            install(Logging) {
                logger = Logger.SIMPLE
                level = LogLevel.INFO
            }

            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                connectTimeoutMillis = 15_000
                socketTimeoutMillis = 15_000
            }

            defaultRequest {
                // set base headers globally, if needed
                header(HttpHeaders.Accept, "application/json")
            }

            // Optional: add auth token interceptor via feature
            install("Auth") {
                // custom plugin or use Request pipeline to add tokens
            }
        }
    }
}*/
