package uz.ibrohim.meat.category.services

import okhttp3.MultipartBody
import okhttp3.RequestBody

class CategoryRepository(private val apiServices: CategoryServices) {

    suspend fun getCategory() = apiServices.getCategory()

    suspend fun getAddCategory(name: RequestBody, request: MultipartBody.Part, parent: RequestBody) =
        apiServices.getAddCategory(name = name, request = request, parent = parent)
}