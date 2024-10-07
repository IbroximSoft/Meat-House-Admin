package uz.ibrohim.meat.category.request_response

data class Children(
    val children: List<Any>,
    val id: Int,
    val image: String,
    val name: String,
    val parent: Int
)