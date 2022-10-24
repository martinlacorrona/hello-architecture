package com.martinlacorrona.helloarchitecture.ui.view.userlist

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.martinlacorrona.helloarchitecture.ui.view.userlist.adapter.UserListAdapter
import com.martinlacorrona.helloarchitecture.ui.viewmodel.UserListViewModel
import com.martinlacorrona.helloarchitecture.R
import com.martinlacorrona.helloarchitecture.databinding.UserListFragmentBinding
import org.koin.androidx.navigation.koinNavGraphViewModel

class UserListFragment : Fragment() {

    private var _binding: UserListFragmentBinding? = null
    private val binding get() = _binding!!

    private val vm: UserListViewModel by koinNavGraphViewModel(R.id.nav_graph)

    private val userListAdapter = UserListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserListFragmentBinding.inflate(inflater, container, false)

        initToolbar()
        initRecyclerView()
        initBindings()
        initObservers()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = GridLayoutManager(
            requireContext(), resources.getInteger(R.integer.gallery_columns)
        )
        binding.recyclerView.isNestedScrollingEnabled = false
        binding.recyclerView.adapter = userListAdapter
    }

    private fun initBindings() {
        binding.swipeRefresh.setOnRefreshListener {
            vm.fetchUserList()
        }
        binding.createButton.setOnClickListener {
            findNavController().navigate(R.id.action_userListFragment_to_createUserFragment)
        }
    }

    private fun initObservers() {
        vm.userList.observe(viewLifecycleOwner) {
            userListAdapter.submitList(it)
        }
        vm.isLoadingStatus.observe(viewLifecycleOwner) {
            binding.swipeRefresh.isRefreshing = it
            binding.progressIndicator.visibility = if (it) View.VISIBLE else View.GONE
        }
        vm.isError.observe(viewLifecycleOwner) {
            if (it == true) showError()
        }
    }

    private fun showError() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.an_error_has_occurred)
            .setMessage(R.string.cannot_get_last_user_data)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .setCancelable(false)
            .show()
        vm.clearError()
    }

    private fun initToolbar() {
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.toolbar_menu, menu)

                (menu.findItem(R.id.toolbar_menu_search)?.actionView as SearchView).apply {
                    setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return true
                        }

                        override fun onQueryTextChange(newText: String?): Boolean {
                            vm.searchValue.value = newText ?: ""
                            return true
                        }
                    })
                }
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        })
    }
}