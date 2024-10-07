package uz.ibrohim.meat.category.services

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import uz.ibrohim.meat.category.request_response.CategoryResponse
import uz.ibrohim.meat.category.request_response.CategoryResponseItem
import uz.ibrohim.meat.utils.Preferences

interface CategoryServices {

    @GET("product/categories/")
    suspend fun getCategory(@Header("Authorization") token: String = "Bearer " + Preferences.token):
            Response<CategoryResponse>

    @Multipart
    @POST("product/categories/")
    suspend fun getAddCategory(@Header("Authorization") token: String = "Bearer " + Preferences.token,
                               @Part("name") name: RequestBody,
                               @Part request: MultipartBody.Part,
                               @Part("parent") parent: RequestBody,
    ):Response<CategoryResponseItem>
}