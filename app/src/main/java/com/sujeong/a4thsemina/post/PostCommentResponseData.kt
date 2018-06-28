package com.sujeong.a4thsemina.post

data class PostCommentResponseData (
        var user_id : String?,
        var board_idx : Int,
        var comment_content : String?,
        var comment_pw : String?
)