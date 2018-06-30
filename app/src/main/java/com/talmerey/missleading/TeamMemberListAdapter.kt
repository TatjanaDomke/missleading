package com.talmerey.missleading

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class TeamMemberListAdapter(private val context: Context, private val itemList: List<TeamMember>) : RecyclerView.Adapter<TeamMemberListAdapter.ViewHolder>() {
    private var clickListener: ItemClickListener? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var tv1: TextView
        internal var tv2: TextView
        internal var imageView: ImageView

        init {
            tv1 = itemView.findViewById<View>(R.id.team_member_name) as TextView
            tv2 = itemView.findViewById<View>(R.id.team_name) as TextView
            imageView = itemView.findViewById<View>(R.id.list_avatar) as ImageView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.team_member_list_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: TeamMemberListAdapter.ViewHolder, position: Int) {
        val teamMember = itemList[position]
        holder.tv1.text = teamMember.name
        holder.tv2.text = itemList[position].team
        holder.imageView.setImageResource(itemList[position].photo)
        holder.imageView.setOnClickListener { v -> if (clickListener != null) clickListener!!.itemClick(v, position) }
        holder.imageView.tag = holder
    }

    fun setClickListener(itemClickListener: ItemClickListener) {
        this.clickListener = itemClickListener
    }

    override fun getItemCount(): Int {
        return this.itemList.size
    }

    interface ItemClickListener {
        fun itemClick(view: View, position: Int)
    }
}