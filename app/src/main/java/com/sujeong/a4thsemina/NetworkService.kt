package com.sujeong.a4thsemina

import com.sujeong.a4thsemina.delete.DeleteBookmarkResponse
import com.sujeong.a4thsemina.delete.DeleteBookmarkResponseData
import com.sujeong.a4thsemina.get.GetBoardResponse
import com.sujeong.a4thsemina.get.GetCommentResponse
import com.sujeong.a4thsemina.post.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

//annotation사용!!!!
interface NetworkService {
    @Multipart //파일과 함께 전송 될때는 멀티파트 사용
    @POST("board")
    fun postBoard(
            @Part photo : MultipartBody.Part?,
           @Part ("user_idx") idx : RequestBody,//3009에는 이게 아님
           // @Part ("user_id") id : RequestBody,
            @Part ("board_title") title : RequestBody,
          //  @Part ("board_pw") pw : RequestBody,
            @Part ("board_content") content : RequestBody
    ) : Call <PostBoardResponse>

    @POST("comment") //단순히 Body로 통째로 보내는 것, 보낼 객체클래스를 따로 만들어서 함수 인자로 보낸다
    fun postComment(@Body comment:PostCommentResponseData) : Call<PostCommentResponse>

    @POST("signin")
    fun postLogin(@Body login:PostLoginResponseData ) : Call<PostLoginResponse>

    @POST("signup")
    fun postJoin(@Body sign : PostJoinResponseData) : Call<PostJoinResponse>

    @POST("bookmark")
    fun postBookmark(@Body bookmark : PostBookmarkResponseData) : Call<PostBookmarkResponse>


    @GET("board") //앞에 http://어쩌구 저쩌구 / 가 생기게 됨.
    fun getContent() : Call<GetBoardResponse>

    @GET("comment/{board_idx}")
    fun getComment(@Path("board_idx")idx : Int) : Call<GetCommentResponse>

    @HTTP(path = "bookmark", method = "DELETE", hasBody = true)
    fun deleteBookmark(@Body dbookmark : DeleteBookmarkResponseData) : Call<DeleteBookmarkResponse>



}