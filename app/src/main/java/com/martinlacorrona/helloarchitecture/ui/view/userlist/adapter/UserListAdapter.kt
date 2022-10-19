package com.martinlacorrona.helloarchitecture.ui.view.userlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.martinlacorrona.helloarchitecture.ui.model.UserModel
import com.martinlacorrona.helloarchitecture.ui.view.createuser.CreateUserFragment
import com.martinlacorrona.helloarchitecture.ui.view.userlist.UserListFragmentDirections
import com.martinlacorrona.helloarchitecture.ui.view.utils.DateUtils
import com.martinlacorrona.helloarchitecture.databinding.UserListItemBinding
import java.util.*

class UserListAdapter : RecyclerView.Adapter<UserListAdapter.ViewHolder>() {

    private val data: ArrayList<UserModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(data[position])
    }

    override fun getItemCount() = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun addNewItems(newUsers: List<UserModel>) {
        data.clear()
        data.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class ViewHolder(
        private val binding: UserListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UserModel) {
            binding.name.text = item.name
            binding.birthday.text = formatDate(item.birthday)
            binding.root.setOnClickListener {
                Navigation.findNavController(itemView).navigate(
                    UserListFragmentDirections
                        .actionUserListFragmentToEditUserFragment(
                            item.id, item.remoteId, item.name, item.birthday
                        )
                )
            }
        }

        private fun formatDate(date: Long): String =
            DateUtils.formatDate(Date(date), CreateUserFragment.DATE_PATTERN)
    }
}