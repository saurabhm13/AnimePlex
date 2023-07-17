package com.example.animeplex.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.animeplex.data.CharacterData
import com.example.animeplex.data.Characters
import com.example.animeplex.retrofit.RetrofitInstance
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersViewModel(): ViewModel() {


    private var characterLiveData = MutableLiveData<Characters>()

    @OptIn(DelicateCoroutinesApi::class)
    fun getCharacters(id: Int) {

        GlobalScope.launch(Dispatchers.IO){

            try {

                RetrofitInstance.api.getCharactersDetail(id).enqueue(object : Callback<Characters>{
                    override fun onResponse(call: Call<Characters>, response: Response<Characters>) {
                        if (response.body() != null){
                            characterLiveData.value = response.body()
                        }
                    }

                    override fun onFailure(call: Call<Characters>, t: Throwable) {
                        Log.d("Character", t.message.toString())
                    }

                })

            }catch (e: Exception){
                Log.d("Character", e.message.toString())
            }
        }

    }

    fun observeCharacterLiveData(): LiveData<Characters>{
        return characterLiveData
    }

}