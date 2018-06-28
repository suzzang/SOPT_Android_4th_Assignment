package com.sujeong.a4thsemina.get

data class GetCommentResponseData (

        var comment_idx : Int,
        var comment_content : String?,
        var comment_writetime : String?,
        var user_id : String?,
        var board_idx : Int,
        var user_idx : Int //3008에서만 필요합니다

)