package com.sujeong.a4thsemina


import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class BookMarkVO : RealmObject(){
    @PrimaryKey
    var board_idx :Int = 0
    var user_idx :Int = 0

}