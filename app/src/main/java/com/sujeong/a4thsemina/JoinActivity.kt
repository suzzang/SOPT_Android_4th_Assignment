package com.sujeong.a4thsemina

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sujeong.a4thsemina.post.PostJoinResponse
import com.sujeong.a4thsemina.post.PostJoinResponseData
import kotlinx.android.synthetic.main.activity_join.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class JoinActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        networkService = ApplicationController.instance.networkService

        join_sign_btn.setOnClickListener {
            postJoin()
        }

    }
    fun postJoin() {

        var user_id = join_id.text.toString()
        var user_pw = join_passwd.text.toString()

        var joindata = PostJoinResponseData(user_id,user_pw)
        var postJoinResponse = networkService.postJoin(joindata)

        postJoinResponse.enqueue(object : Callback<PostJoinResponse>{
            override fun onFailure(call: Call<PostJoinResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PostJoinResponse>?, response: Response<PostJoinResponse>?) {
                if(response!!.isSuccessful){
                    startActivity(Intent(applicationContext,LoginActivity::class.java))
                }
            }

        })

    }
}
