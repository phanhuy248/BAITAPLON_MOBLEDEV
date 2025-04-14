package com.neatroots.instagramclone.adapters

import Models.User
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neatroots.instagramclone.R
import com.neatroots.instagramclone.databinding.FollowRvBinding
import com.neatroots.instagramclone.databinding.SearchRvBinding

class FollowAdapter(var context: Context,var followList: ArrayList<User>) :RecyclerView.Adapter<FollowAdapter.ViewHolder>() {
    inner class ViewHolder(var binding: FollowRvBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding= FollowRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return followList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(followList.get(position).image).placeholder(R.drawable.user_icon).into(holder.binding.profileImage)
        holder.binding.name.text=followList.get(position).name
    }
}