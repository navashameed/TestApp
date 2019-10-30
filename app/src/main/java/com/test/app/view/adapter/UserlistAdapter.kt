package com.test.app.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.app.R
import com.test.app.databinding.ListItemUserInfoBinding
import com.test.app.model.User

class UserlistAdapter :
    RecyclerView.Adapter<UserlistAdapter.UserViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(userId: Int?)
    }

    var usersList: List<User> = ArrayList()
    var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): UserViewHolder {
        val listItemUserInfoBinding: ListItemUserInfoBinding = DataBindingUtil.inflate(
            LayoutInflater.from(viewGroup.getContext()),
            R.layout.list_item_user_info, viewGroup, false
        )
        return UserViewHolder(listItemUserInfoBinding)
    }

    fun setItemsAndListener(usersList: List<User>, listener: OnItemClickListener) {
        this.usersList = usersList
        this.listener = listener
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user: User = usersList[position]
        holder.userListItemBinding.user = user
        holder.userListItemBinding.onClickListener = listener
    }

    override fun getItemCount(): Int = usersList.size

    inner class UserViewHolder(val userListItemBinding: ListItemUserInfoBinding) :
        RecyclerView.ViewHolder(userListItemBinding.getRoot())

    companion object {
        @JvmStatic
        @BindingAdapter("userItems")
        fun RecyclerView.bindItems(items: List<User>) {
            val adapter = adapter as UserlistAdapter
            adapter.setItemsAndListener(items, object : OnItemClickListener {
                override fun onItemClick(id: Int?) {
                    adapter.listener?.onItemClick(id)
                }

            })
        }
    }
}
