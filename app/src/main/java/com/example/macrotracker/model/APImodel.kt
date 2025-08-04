package com.example.macrotracker.model

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

data class FoodSearchResponse(
    val foods: List<USDAFood>
)

data class USDAFood(
    val description: String,
    val foodNutrients: List<Nutrient>
)

data class Nutrient(
    val nutrientName: String,
    val value: Double
)

interface USDAApiService {
    @GET("foods/search")
    suspend fun searchFoods(
        @Query("query") query: String,
        @Query("api_key") apiKey: String = "jxEXurvqtEekr38nUtmvcOXcXMZ3L18Ja9cfoJDE",
        @Query("pageSize") pageSize: Int = 10
    ): FoodSearchResponse
}

object USDAApi {
    val retrofitService: USDAApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.nal.usda.gov/fdc/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(USDAApiService::class.java)
    }
}
