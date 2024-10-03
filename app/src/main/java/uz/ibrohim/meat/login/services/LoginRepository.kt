package uz.ibrohim.meat.login.services

import uz.ibrohim.meat.login.request_response.LoginRequest


class LoginRepository(private val apiServices: LoginServices) {

    suspend fun getLogin(body: LoginRequest) = apiServices.getLogin(body)
}