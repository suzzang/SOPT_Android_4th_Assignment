package com.sujeong.a4thsemina

import android.app.Application
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*어플리케이션의 가장 최상위를 통제하기 위해(Application()을 상속).. 이 클래스 하나로 모든 액티비티가 네트워크를 사용할수 있도록 하는것*/

class ApplicationController : Application() {
    lateinit var networkService: NetworkService
    private val baseUrl ="http://13.124.143.2:3008/"
    companion object {

        lateinit var instance : ApplicationController

    } //이 안에 있는 애들은 일종의 static이다.

    override fun onCreate() {
        super.onCreate()

        instance = this //어느 액티비티에서든지 '나'를 할당 할 수 있도록!! instance에 this를 넣어준다!

        buildNetwork()
    }

    fun buildNetwork() {
        val builder = Retrofit.Builder()
        var retrofit = builder
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())//GSON을 JSON으로 쓸수있도록
                .build()  //베이스 유알엘을 가지고 통신을 할것

        networkService = retrofit.create(NetworkService::class.java)
    }
    //

}