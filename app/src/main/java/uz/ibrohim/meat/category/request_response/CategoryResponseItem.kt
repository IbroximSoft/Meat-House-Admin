package uz.ibrohim.meat.category.request_response

data class CategoryResponseItem(
    val children: List<Any>,
    val id: Int,
    val name: String,
    val order: Int
)