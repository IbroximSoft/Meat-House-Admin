package uz.ibrohim.meat.login.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import uz.ibrohim.meat.login.request_response.RequestLogin
import uz.ibrohim.meat.retrofit.NetworkHelper
import uz.ibrohim.meat.retrofit.Resource

class LoginViewModel(apiServices: LoginServices, private val networkHelper: NetworkHelper) : ViewModel() {

    private val moveRepository = LoginRepository(apiServices)
    val flow = MutableStateFlow<Resource>(Resource.Loading)

    fun getLogin(body: RequestLogin): StateFlow<Resource> {
        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    val response = moveRepository.getLogin(body)
                    if (response.isSuccessful && response.body() != null) {
                        flow.emit(Resource.Success(response.body()!!))
                    } else {
                        flow.emit(Resource.Error("Sever Error !"))
                    }
                } else {
                    flow.emit(Resource.Error("Network no connection !"))
                }
            } catch (e: Exception) {
                flow.emit(Resource.Error(e.message.toString()))
            }
        }
        return flow
    }
}