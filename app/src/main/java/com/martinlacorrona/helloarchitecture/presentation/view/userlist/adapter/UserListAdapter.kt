package com.martinlacorrona.helloarchitecture.presentation.view.userlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.martinlacorrona.helloarchitecture.databinding.UserListItemBinding
import com.martinlacorrona.helloarchitecture.domain.model.UserModel
import com.martinlacorrona.helloarchitecture.presentation.view.baseuserdata.BaseUserDataFragment.Companion.DATE_PATTERN
import com.martinlacorrona.helloarchitecture.presentation.view.userlist.UserListFragmentDirections
import com.martinlacorrona.helloarchitecture.presentation.view.utils.DateUtils
import java.util.*

class UserListAdapter : ListAdapter<UserModel, UserListAdapter.ViewHolder>(UserModelDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = UserListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(currentList[position])
    }

    override fun getItemCount() = currentList.size

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
            DateUtils.formatDate(Date(date), DATE_PATTERN)
    }

    object UserModelDiffCallback : DiffUtil.ItemCallback<UserModel>() {
        override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
            return oldItem == newItem
        }
    }
}