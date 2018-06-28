package com.sujeong.a4thsemina

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sujeong.a4thsemina.get.GetBoardResponseData
import com.sujeong.a4thsemina.get.GetCommentResponseData

class CommentAdapter(var commentItems : ArrayList<GetCommentResponseData>) : RecyclerView.Adapter<CommentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {

        var mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.comment_item,parent,false)

        return CommentViewHolder(mainView)

    }

    override fun getItemCount(): Int = commentItems.size

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {

       // holder.commentIdx.text = commentItems[position].comment_idx.toString()//인트형데이터를 스트링형으로 변환해서 받기!

        holder.commentId.text = commentItems[position].user_id
        holder.commentTime.text = commentItems[position].comment_writetime
        holder.commentContent.text = commentItems[position].comment_content
    }

}