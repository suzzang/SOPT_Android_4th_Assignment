package com.sujeong.a4thsemina

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.sujeong.a4thsemina.SharedPreferenceController.getBoardIdx
import com.sujeong.a4thsemina.delete.DeleteBookmarkResponse
import com.sujeong.a4thsemina.delete.DeleteBookmarkResponseData
import com.sujeong.a4thsemina.get.GetCommentResponse
import com.sujeong.a4thsemina.get.GetCommentResponseData
import com.sujeong.a4thsemina.post.PostBookmarkResponse
import com.sujeong.a4thsemina.post.PostBookmarkResponseData
import com.sujeong.a4thsemina.post.PostCommentResponse
import com.sujeong.a4thsemina.post.PostCommentResponseData
import io.realm.Realm
import io.realm.Realm.init
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.board_item.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body

class DetailAvctivity : AppCompatActivity(),View.OnClickListener {



    override fun onClick(p0: View?) {
        when(p0){
            btn_detail_bookmark->{
                if(btn_detail_bookmark.isSelected == false){
                    btn_detail_bookmark.isSelected = true
                    insertBoardIdx()
                    postBookmark()

                }else if(btn_detail_bookmark.isSelected == true){
                    btn_detail_bookmark.isSelected = false
                   deleteBookmarkList(intent.getIntExtra("idx",0))
                    deleteBookmark()
                }
            }
        }
    }
    lateinit var bookmarkVO: BookMarkVO
    lateinit var bmRealm: Realm
    lateinit var networkService: NetworkService
    //lateinit var requestManager: RequestManager
    lateinit var commentAdapter : CommentAdapter
    lateinit var commentItems : ArrayList<GetCommentResponseData>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        init()

        if(getUserIdx(intent.getIntExtra("user_idx",0)).isEmpty()){

        }else{
            if(getBoardIdxlist(intent.getIntExtra("idx",0)).isEmpty()){
                btn_detail_bookmark.isSelected = false;
            }else{
                btn_detail_bookmark.isSelected = true;
            }
        }



        cmt_rcv.layoutManager = LinearLayoutManager(this)


        detail_id.text = intent.getStringExtra("id")
        detail_content.text = intent.getStringExtra("content")

        val gb : String? = intent.getStringExtra("img")
        if(gb.equals(null)){

        }else {
            Glide.with(this).load(Uri.parse(gb)).into(detail_img)
        }

        btn_detail_bookmark.setOnClickListener(this)


        networkService = ApplicationController.instance.networkService //요건 ApplicationController에서 만들어놨던 networkService를 사용하기 위해서당!

        val getCommentResponse = networkService.getComment(intent.getIntExtra("idx",0))
        getCommentResponse.enqueue(object : Callback<GetCommentResponse>{
            override fun onFailure(call: Call<GetCommentResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<GetCommentResponse>?, response: Response<GetCommentResponse>?) {
                if(response!!.isSuccessful){
                    commentItems = response.body().data
                    commentAdapter = CommentAdapter(commentItems)

                    cmt_rcv.adapter = commentAdapter
                }
            }

        })

        detail_cmt_btn.setOnClickListener {
            postComment()
        }


    }
    fun init(){
        Realm.init(this)
       bmRealm = Realm.getDefaultInstance()
    }
    fun insertBoardIdx(){
        bookmarkVO = BookMarkVO()
        bookmarkVO.board_idx = intent.getIntExtra("idx",0)
        bookmarkVO.user_idx = intent.getIntExtra("user_idx",0)

        bmRealm.beginTransaction()
        bmRealm.copyToRealm(bookmarkVO)//memberRealm에있는 값을 복사해 넣겠다.

        bmRealm.commitTransaction()
    }
    fun deleteBookmarkList(idx:Int){
        val result = bmRealm.where(BookMarkVO::class.java)
                .equalTo("board_idx",idx)
                .findAll()

        if(result.isEmpty()){
            return
        }
        bmRealm.beginTransaction() //수정을 위한
        result.deleteAllFromRealm()//네임과 일치하는걸 뺴고 렘에서 다 지운다.
        bmRealm.commitTransaction()
       // Toast.makeText(this,"삭제성공", Toast.LENGTH_SHORT).show()

    }

    fun getBoardIdxlist(idx:Int) : RealmResults<BookMarkVO> {
        return bmRealm.where(BookMarkVO::class.java).equalTo("board_idx", idx).findAll()
    }
    fun getUserIdx(user_idx:Int) : RealmResults<BookMarkVO> {
        return bmRealm.where(BookMarkVO::class.java).equalTo("user_idx", user_idx).findAll()
    }
    fun postComment(){

        //변수만들어서 각각의 보낼 데이터들을 넣어준다.
        var id = detail_cmt_id.text.toString()
        var idx = intent.getIntExtra("idx",0)
        var content = detail_cmt_content.text.toString()

        //그리고 PostCommentResponseData 에 위에서 만든 변수들을 순서에 맞게 넣어주고나서 cmt변수에 담는다(데이터객체변수)
        var cmt = PostCommentResponseData(id,idx,content,"1234")

        //똑같이!
        val getCommentResponse = networkService.getComment(idx)
        var postCommentResponse = networkService.postComment(cmt)
        postCommentResponse.enqueue(object : Callback<PostCommentResponse>{
            override fun onFailure(call: Call<PostCommentResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PostCommentResponse>?, response: Response<PostCommentResponse>?) { //신기하게 이걸 여기다 넣어줘야지 댓글달고나서 화면이 안바뀐다.
                if(response!!.isSuccessful){
                    getCommentResponse.enqueue(object : Callback<GetCommentResponse>{
                        override fun onFailure(call: Call<GetCommentResponse>?, t: Throwable?) {

                        }

                        override fun onResponse(call: Call<GetCommentResponse>?, response: Response<GetCommentResponse>?) {
                            if(response!!.isSuccessful){
                                commentItems = response.body().data
                                commentAdapter = CommentAdapter(commentItems)

                                cmt_rcv.adapter = commentAdapter
                            }
                        }

                    })

                    //에딧트텍스트 셋텍스트 초기화!!
                    detail_cmt_id.setText("")
                    detail_cmt_content.setText("")

                }
            }

        })
    }
    fun clearSelected(){
       btn_detail_bookmark.isSelected = false
    }
    fun postBookmark(){

        var user_idx = intent.getIntExtra("user_idx",0)
        var board_idx = intent.getIntExtra("idx",0)

        var bookmark = PostBookmarkResponseData(user_idx,board_idx)
        val postBookmarkResponse = networkService.postBookmark(bookmark)

        postBookmarkResponse.enqueue(object : Callback<PostBookmarkResponse>{
            override fun onFailure(call: Call<PostBookmarkResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PostBookmarkResponse>?, response: Response<PostBookmarkResponse>?) {

            }

        })

    }

    fun deleteBookmark(){
        var user_idx = intent.getIntExtra("user_idx",0)
        var board_idx = intent.getIntExtra("idx",0)

        var dbookmark = DeleteBookmarkResponseData(user_idx,board_idx)
        val deleteBookmarkResponse = networkService.deleteBookmark(dbookmark)
        deleteBookmarkResponse.enqueue(object : Callback<DeleteBookmarkResponse>{
            override fun onFailure(call: Call<DeleteBookmarkResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<DeleteBookmarkResponse>?, response: Response<DeleteBookmarkResponse>?) {
            }

        })

    }
}
