package uz.ibrohim.meat.login.request_response

data class LoginRequest(
    val password: String,
    val phone: String
)