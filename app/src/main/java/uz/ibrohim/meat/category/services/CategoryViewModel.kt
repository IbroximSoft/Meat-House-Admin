package uz.ibrohim.meat.category.services

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import uz.ibrohim.meat.retrofit.NetworkHelper
import uz.ibrohim.meat.retrofit.Resource

class CategoryViewModel(apiServices: CategoryServices, private val networkHelper: NetworkHelper) : ViewModel() {

    private val moveRepository = CategoryRepository(apiServices)
    val flow = MutableStateFlow<Resource>(Resource.Loading)

    fun getCategory(): StateFlow<Resource> {
        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    val response = moveRepository.getCategory()
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

    fun getAddCategory(name: RequestBody, request: MultipartBody.Part, parent: RequestBody): StateFlow<Resource> {
        viewModelScope.launch {
            try {
                if (networkHelper.isNetworkConnected()) {
                    val response = moveRepository.getAddCategory(name = name, request = request, parent = parent)
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