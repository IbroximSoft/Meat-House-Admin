package uz.ibrohim.meat.login.services

import uz.ibrohim.meat.login.request_response.RequestLogin


class LoginRepository(private val apiServices: LoginServices) {

    suspend fun getLogin(body: RequestLogin) = apiServices.getLogin(body)
}