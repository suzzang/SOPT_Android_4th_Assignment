package com.sujeong.a4thsemina

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import org.w3c.dom.Text

class CommentViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

   // var commentIdx : TextView = itemView!!.findViewById(R.id.cmt_item_idx) as TextView
    var commentId : TextView = itemView!!.findViewById(R.id.cmt_item_id) as TextView
    var commentTime : TextView = itemView!!.findViewById(R.id.cmt_item_time) as TextView
    var commentContent : TextView = itemView!!.findViewById(R.id.cmt_item_content) as TextView
}