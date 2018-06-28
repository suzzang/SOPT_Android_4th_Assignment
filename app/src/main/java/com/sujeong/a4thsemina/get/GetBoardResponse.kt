package com.sujeong.a4thsemina.get

data class GetBoardResponse ( //맨 처음 중괄호, 데이터클래스로 관리
    var message : String,
    var data : ArrayList<GetBoardResponseData>

)