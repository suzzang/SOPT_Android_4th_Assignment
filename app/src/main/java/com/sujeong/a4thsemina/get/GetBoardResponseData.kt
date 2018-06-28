package com.sujeong.a4thsemina.get

data class GetBoardResponseData (

        //String 뒤에 ?써서 null값일 경우를 대처하기!
        var board_idx : Int,
        var board_title : String?,
        var board_content : String?,
        var board_views : Int,
        var board_photo : String?,
        var board_writetime : String?,
        var user_idx : Int,
        var user_id : String?
//조회수, 유저인덱스 수정


)
