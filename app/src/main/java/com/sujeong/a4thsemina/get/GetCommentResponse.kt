package com.sujeong.a4thsemina.get

data class GetCommentResponse (
        var message : String,
        var data : ArrayList<GetCommentResponseData>
)