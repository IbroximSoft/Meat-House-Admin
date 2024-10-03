package uz.ibrohim.meat.category.services

class CategoryRepository(private val apiServices: CategoryServices) {

    suspend fun getCategory() = apiServices.getCategory()
}