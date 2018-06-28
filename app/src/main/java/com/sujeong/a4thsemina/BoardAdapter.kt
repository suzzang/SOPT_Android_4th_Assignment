package com.sujeong.a4thsemina

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.RequestManager
import com.sujeong.a4thsemina.get.GetBoardResponseData

class BoardAdapter(var boardItems : ArrayList<GetBoardResponseData>, var requestManager: RequestManager) : RecyclerView.Adapter<BoardViewHolder>() {
    private lateinit var onItemClick: View.OnClickListener
    fun setOnItemClickListner(I : View.OnClickListener){
        onItemClick = I
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BoardViewHolder {
        var mainView : View = LayoutInflater.from(parent.context).inflate(R.layout.board_item,parent,false)

        mainView.setOnClickListener(onItemClick)

        return BoardViewHolder(mainView)
    }

    override fun getItemCount(): Int = boardItems.size

    override fun onBindViewHolder(holder: BoardViewHolder, position: Int) {
        holder.boardContent.text = boardItems[position].board_content
        holder.boardId.text = boardItems[position].user_id
        holder.boardTime.text = boardItems[position].board_writetime
        holder.boardTitle.text = boardItems[position].board_title

        //이미지는 requestManger와 glide를 연결해서 쓴다. 메인액티비티에 리퀘스트매니저 = 글라이드.with(this) 이렇게 먼저 선언

        requestManager.load(boardItems[position].board_photo) //이미지를 로드해서
                .into(holder.boardProfile) //여기에 넣는다.
    }

}