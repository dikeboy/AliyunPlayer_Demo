package com.hupu.testtex

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *
 * @des:     类
 * @auth:         ldh
 * @date:     2021/1/7 3:36 PM
 */

class TestAdapter(var context: Context) : RecyclerView.Adapter<BaseHolder>() {
    var playList = ArrayList<PlayBean>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.test_item,null,false)
        var holder = BaseHolder(view)
        return holder
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.tv.text="pos ${position}"
        holder.itemView.setOnClickListener {
            var intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("https://bigboy.hupu.com/sns/detail/100000617")
        }
        holder.videoView.visibility = View.INVISIBLE

        holder.itemView.setOnClickListener {
            var intent = Intent(context,TestActivity::class.java)
            intent.putExtra("playBean",playList.get(position))
            context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int {
       return  50
    }

}

open class BaseHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv = itemView.findViewById<TextView>(R.id.testTv)
    var videoView = itemView.findViewById<LinearLayout>(R.id.videoLayout)
}