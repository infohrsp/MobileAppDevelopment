package com.example.myapplication

import android.app.Activity
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

class MyAdapter(val context : Activity, val dataList: List<Data>): RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView= LayoutInflater.from(context).inflate(R.layout.each_item, parent, false)
        return MyViewHolder(itemView)

    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val currentItem= dataList[position]
        val mediaplayer= MediaPlayer.create(context, currentItem.preview.toUri())
        holder.title.text= currentItem.title
        Picasso.get().load(currentItem.album.cover_medium).into(holder.image)
        holder.play.setOnClickListener {
            mediaplayer.start()
        }
        holder.pause.setOnClickListener {
            mediaplayer.stop()
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class MyViewHolder(itemview: View): RecyclerView.ViewHolder(itemview) {
        val image: ImageView
        val title: TextView
        val play: ImageButton
        val pause: ImageButton

        init {
            image= itemview.findViewById(R.id.musicImage)
            title= itemview.findViewById(R.id.musicTitle)
            play= itemview.findViewById(R.id.btn_play)
            pause= itemview.findViewById(R.id.btn_pause)
        }
    }


}