package com.sujeong.a4thsemina

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.DrawableTypeRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sujeong.a4thsemina.get.GetBoardResponse
import com.sujeong.a4thsemina.get.GetBoardResponseData
import com.sujeong.a4thsemina.get.GetCommentResponse
import com.sujeong.a4thsemina.get.GetCommentResponseData
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onClick(p0: View?) {
        var user_idx = intent.getIntExtra("user_idx",0)//이거 왜 안되냐ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅅㅂ
        val idx : Int = main_board_list.getChildAdapterPosition(p0) //이건 리사이클러뷰 자체의 인덱스...ㅎ바보새캬
        val indx : Int = boardItems[idx].board_idx
        val id : String? = boardItems[idx].user_id
        val content : String? = boardItems[idx].board_content
        val img : String? = boardItems[idx].board_photo
        val intent : Intent = Intent(applicationContext,DetailAvctivity::class.java)

        intent.putExtra("idx",indx)

        intent.putExtra("id",id)
        intent.putExtra("content",content)
        intent.putExtra("img",img)

        intent.putExtra("user_idx",user_idx)
        startActivity(intent)
    }

    lateinit var networkService: NetworkService
    lateinit var boardAdapter: BoardAdapter
    lateinit var boardItems : ArrayList<GetBoardResponseData>
    lateinit var requestManager: RequestManager

    lateinit var commentAdapter : CommentAdapter
    lateinit var commentItems : ArrayList<GetCommentResponseData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       var user_idx = intent.getIntExtra("user_idx",0)//이거 왜 안되냐ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅅㅂ
        Log.v("유저인덱스2",user_idx.toString())

        networkService = ApplicationController.instance.networkService
        requestManager = Glide.with(this) //this는 application에서 쓸수 있다는것 즉 콘텍스트를 쓰기위해서 리퀘스트 매니저를 쓴것이다.

        main_board_list.layoutManager = LinearLayoutManager(this)



        val getBoardResponse = networkService.getContent() //call getBoardResponse를 담는것
        getBoardResponse.enqueue(object : Callback<GetBoardResponse>{ //g.B.R = call을 담아서 서버에 요청을 해서 서버의 응답을 담는것이 enqueue, 그걸 callBack으로 getBoardResponse를 받아오는것
            override fun onFailure(call: Call<GetBoardResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<GetBoardResponse>?, response: Response<GetBoardResponse>?) {
                if(response!!.isSuccessful){
                    boardItems = response.body().data


                    //리사이클러뷰 완성하기
                    boardAdapter = BoardAdapter(boardItems,requestManager)//보드아이템즈는 바로 위에 만들어놓은것.

                    boardAdapter.setOnItemClickListner(this@MainActivity) //아이템별로 클릭이벤트 부여

                    main_board_list.adapter = boardAdapter
                    //게시글 받아오는것 완성

                    main_write_btn.setOnClickListener {

                        var intent : Intent = Intent(applicationContext,BoardActivity::class.java)

                        intent.putExtra("user_idx",user_idx)
                        startActivity(intent)
                    }

                }
            }

        })




    }
}
