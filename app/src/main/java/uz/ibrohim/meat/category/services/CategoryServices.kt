package uz.ibrohim.meat.category.services

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import uz.ibrohim.meat.category.request_response.CategoryResponse

interface CategoryServices {

    @GET("product/categories/")
    suspend fun getCategory(): Response<CategoryResponse>
}