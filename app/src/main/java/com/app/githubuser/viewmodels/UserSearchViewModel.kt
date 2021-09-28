package com.app.githubuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuser.api.RetrofitInstance
import com.app.githubuser.data.SearchResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserSearchViewModel : ViewModel() {
    private val _results = MutableLiveData<SearchResult>()
    val results: LiveData<SearchResult> = _results

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun searchUser(query: String) {
        _isLoading.value = true
        _isError.value = false
        val client = RetrofitInstance.api.searchUser(query)
        client.enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _results.value = response.body()
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }
}