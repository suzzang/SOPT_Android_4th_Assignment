package com.sujeong.a4thsemina

import android.content.Intent
import android.graphics.Paint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.sujeong.a4thsemina.post.PostLoginResponse
import com.sujeong.a4thsemina.post.PostLoginResponseData
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        networkService = ApplicationController.instance.networkService

        login_btn.setOnClickListener {

            postLogin()
           // startActivity(Intent(applicationContext,MainActivity::class.java))
        }

        login_join_btn.setOnClickListener {
            var intent : Intent = Intent(applicationContext,JoinActivity::class.java)
            startActivity(intent)
        }
    }

    fun postLogin(){

        var login_id = login_id.text.toString()
        var login_passwd = login_passwd.text.toString()

        var logindata = PostLoginResponseData(login_id,login_passwd)

        var postLoginResponse = networkService.postLogin(logindata)
        postLoginResponse.enqueue(object : Callback<PostLoginResponse>{
            override fun onFailure(call: Call<PostLoginResponse>?, t: Throwable?) {


            }

            override fun onResponse(call: Call<PostLoginResponse>?, response: Response<PostLoginResponse>?) {
                if(response!!.isSuccessful){
                    var idx : Int = response.body().user_idx// Call을 했으니까 Callback을 해서 서버가보낸 그 메세지나 유저인덱스를 받아올수 있다.
                    var intent : Intent = Intent(applicationContext,MainActivity::class.java) //이거 반드시 필요하다!! startActivity의 intent하고 동일시 해야하기때문에!
                    Log.v("유저인덱스", idx.toString())
                    intent.putExtra("user_idx",idx)


                    startActivity(intent)//값을 넣어준 intent를 스타트해라!
                    finish()
                }

            }

        })
    }
}
