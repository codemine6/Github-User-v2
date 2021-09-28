package com.app.githubuser.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.githubuser.api.RetrofitInstance
import com.app.githubuser.data.Repository
import com.app.githubuser.data.UserDetail
import com.app.githubuser.data.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDetailViewModel : ViewModel() {
    private val _user = MutableLiveData<UserDetail>()
    val user: LiveData<UserDetail> = _user

    private val _following = MutableLiveData<List<UserItem>>()
    val following: LiveData<List<UserItem>> = _following

    private val _followers = MutableLiveData<List<UserItem>>()
    val followers: LiveData<List<UserItem>> = _followers

    private val _repositories = MutableLiveData<List<Repository>>()
    val repositories: LiveData<List<Repository>> = _repositories

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isError = MutableLiveData<Boolean>()
    val isError: LiveData<Boolean> = _isError

    fun getUser(login: String) {
        _isLoading.value = true
        _isError.value = false
        val client = RetrofitInstance.api.getUser(login)
        client.enqueue(object : Callback<UserDetail> {
            override fun onResponse(call: Call<UserDetail>, response: Response<UserDetail>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<UserDetail>, t: Throwable) {
                _isLoading.value = false
                _isError.value = true
            }
        })
    }

    fun getUserFollowing(login: String) {
        val client = RetrofitInstance.api.getUserFollowing(login)
        client.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    _following.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {}
        })
    }

    fun getUserFollowers(login: String) {
        val client = RetrofitInstance.api.getUserFollowers(login)
        client.enqueue(object : Callback<List<UserItem>> {
            override fun onResponse(
                call: Call<List<UserItem>>,
                response: Response<List<UserItem>>
            ) {
                if (response.isSuccessful) {
                    _followers.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<UserItem>>, t: Throwable) {}
        })
    }

    fun getUserRepositories(login: String) {
        val client = RetrofitInstance.api.getUserRepositories(login)
        client.enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                if (response.isSuccessful) {
                    _repositories.value = response.body()
                }
            }
            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {}
        })
    }
}