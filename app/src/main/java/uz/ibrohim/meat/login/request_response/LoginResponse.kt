package uz.ibrohim.meat.login.request_response

data class LoginResponse(
    val access: String,
    val refresh: String
)