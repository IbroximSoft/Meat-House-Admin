package uz.ibrohim.meat.category.request_response

data class CategoryResponseItem(
    val children: List<Children>,
    val id: Int,
    val image: String,
    val name: String,
    val parent: Any
)