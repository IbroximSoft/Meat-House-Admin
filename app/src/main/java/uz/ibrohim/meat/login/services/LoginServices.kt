package uz.ibrohim.meat.login.services

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import uz.ibrohim.meat.login.request_response.LoginRequest
import uz.ibrohim.meat.login.request_response.LoginResponse

interface LoginServices {

    @POST("account/login/")
    suspend fun getLogin(@Body body: LoginRequest): Response<LoginResponse>
}