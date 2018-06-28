package com.sujeong.a4thsemina

import android.content.Context
import android.net.wifi.WifiEnterpriseConfig.Eap.PWD
import android.os.Build.ID

object SharedPreferenceController {
    //Application controller와 비슷한 개념, 오브젝트 클래스는 객체를 생성하지않고 바로 접근이 가능하다. 값을 차곡차곡 넣어놓는 개념이다.
    private val USER_IDX = "user_idx"
    private val BOARD_IDX = "board_idx"


    fun setBoardIdx(context: Context,idx:Int ){
        val pref = context.getSharedPreferences(BOARD_IDX,Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putInt(BOARD_IDX,idx)
        editor.commit()
    }
    fun getBoardIdx(context: Context):Int{
        val pref = context.getSharedPreferences(BOARD_IDX,Context.MODE_PRIVATE)
        return pref.getInt(BOARD_IDX,0)
    }

    fun clearSPC(context: Context){
        val pref = context.getSharedPreferences(USER_IDX,Context.MODE_PRIVATE)
        val editor =pref.edit()
        editor.clear()
        editor.commit()
    }
}