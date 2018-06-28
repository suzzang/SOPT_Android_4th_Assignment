package com.sujeong.a4thsemina

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.sujeong.a4thsemina.post.PostBoardResponse
import kotlinx.android.synthetic.main.activity_board.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileNotFoundException
import java.io.InputStream

class BoardActivity : AppCompatActivity() {

    lateinit var networkService: NetworkService
    private val REQ_CODE_SELECT_IMAGE = 100
    lateinit var data : Uri
    private var image : MultipartBody.Part? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        networkService = ApplicationController.instance.networkService //글쓰기 완료!(매애애애앤 마지막 단계)




        write_image_btn.setOnClickListener{
            changeImage()
        }//갤러리에서 이미지 불러오는것 끝!

        write_post_btn.setOnClickListener {  //서버에 글 데이터를 보내는 코드

            postBoard()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) { //리퀘스트코드가 이게 맞냐?!
            if (resultCode == Activity.RESULT_OK) { //맞다! 그래서 들어와서 확인을 눌렀을때 리졸트코드로 들어감
                try {
                    //if(ApplicationController.getInstance().is)
                    this.data = data!!.data //그이미지의Uri를 가져옴
                    Log.v("이미지", this.data.toString())

                    val options = BitmapFactory.Options()

                    var input: InputStream? = null // here, you need to get your context.
                    try {
                        input = contentResolver.openInputStream(this.data)
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }

                    val bitmap = BitmapFactory.decodeStream(input, null, options) // InputStream 으로부터 Bitmap 을 만들어 준다.
                    val baos = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos)
                    val photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray())
                    val photo = File(this.data.toString()) // 가져온 파일의 이름을 알아내려고 사용합니다

                    ///RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());
                    // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!

                    image = MultipartBody.Part.createFormData("photo", photo.name, photoBody) //여기의 photo는 키값의 이름하고 같아야함

                    //body = MultipartBody.Part.createFormData("image", photo.getName(), profile_pic);

                    Glide.with(this)
                            .load(data.data)
                            .centerCrop()
                            .into(write_image) //만들었던 이미지 뷰에 이미지를 띄우기 위한것

                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
        }
    }
    fun changeImage() {
        var intent = Intent(Intent.ACTION_PICK)
        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE //저장소 접근
        intent.data = android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI //저장소 접근
        startActivityForResult(intent,REQ_CODE_SELECT_IMAGE) // 액티비티를 켜서 거기에서 응답값을 받는 것
    }
    fun postBoard() {


        var title = RequestBody.create(MediaType.parse("text/plain"),write_title_tv.text.toString())
        var content = RequestBody.create(MediaType.parse("text/plain"),write_content_tv.text.toString())
        var pw = RequestBody.create(MediaType.parse("text/parse"),"1234")
         var id = RequestBody.create(MediaType.parse("text/plain"),"su")//로그인 아이디 이름 !!!3009일때!!!
        var idx =RequestBody.create(MediaType.parse("text/plain"),intent.getIntExtra("user_idx",0).toString())


      //3009일때  var postBoardResponse = networkService.postBoard(image,id,title,pw,content) //보낼때 NetworkService에 적었던 아이템 순서대로 적어야함!!!!!!!!!!!!
        var postBoardResponse = networkService.postBoard(image,idx,title,content) //얘는 포트 3008일때

        postBoardResponse.enqueue(object : Callback<PostBoardResponse>{
            override fun onFailure(call: Call<PostBoardResponse>?, t: Throwable?) {

            }

            override fun onResponse(call: Call<PostBoardResponse>?, response: Response<PostBoardResponse>?) {
                if(response!!.isSuccessful){

                    var user_idx = intent.getIntExtra("user_idx",0)

                    var intent : Intent = Intent(applicationContext,MainActivity::class.java)
                    intent.putExtra("user_idx",user_idx)
                    startActivity(intent)
                    finish() //이 액티비티를 한번 켰으면 back을 못하게끔 그 전것을 지워버린다는것
                }
            }

        })

    }
}
