package uz.ibrohim.meat.login.request_response

data class RequestLogin(
    val password: String,
    val phone: String
)