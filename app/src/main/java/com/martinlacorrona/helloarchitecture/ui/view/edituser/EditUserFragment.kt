package com.martinlacorrona.helloarchitecture.ui.view.edituser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.martinlacorrona.helloarchitecture.R
import com.martinlacorrona.helloarchitecture.databinding.EditUserFragmentBinding
import com.martinlacorrona.helloarchitecture.ui.view.baseuserdata.BaseUserDataFragment
import com.martinlacorrona.helloarchitecture.ui.view.utils.DateUtils
import com.martinlacorrona.helloarchitecture.ui.viewmodel.EditUserViewModel
import com.martinlacorrona.helloarchitecture.ui.viewmodel.UserListViewModel
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EditUserFragment : BaseUserDataFragment() {

    private var _binding: EditUserFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainVm: UserListViewModel by koinNavGraphViewModel(R.id.nav_graph)
    override val vm: EditUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditUserFragmentBinding.inflate(inflater, container, false)

        getExtras()
        initViews()

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getExtras() {
        arguments?.let {
            EditUserFragmentArgs.fromBundle(it).apply {
                vm.id = id
                vm.remoteId = remoteId
                vm.name.value = name
                vm.birthday.value = Date(birthday)
            }
        }
    }

    override fun initValues() {
        binding.nameAndBirthday.editTextName.setText(vm.name.value)
        vm.birthday.value?.let { binding.nameAndBirthday.editTextBirthday.setText(it.toString()) }
    }

    override fun initBindings() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.nameAndBirthday.editTextName.addTextChangedListener { text -> vm.name.value = text.toString() }

        binding.nameAndBirthday.editTextBirthday.setOnClickListener {
            showDatePicker()
        }

        binding.updateButton.setOnClickListener { vm.updateUser() }
        binding.deleteButton.setOnClickListener { vm.deleteUser() }
    }

    override fun onIsDone() {
        vm.fetchUserList(mainVm.viewModelScope)
        findNavController().popBackStack()
    }

    override fun onBirthdayChange(date: Date?) {
        date?.let {
            binding.nameAndBirthday.editTextBirthday.setText(formatDate(it))
        }
    }

    override fun enableButtonIfInfoIsCompleted() {
        binding.updateButton.isEnabled =
            !vm.name.value.isNullOrEmpty()
                    && vm.birthday.value != null
                    && vm.isLoadingStatus.value == false
    }

    override fun isUpdatingView(updating: Boolean) {
        binding.progressIndicator.visibility = if (updating) View.VISIBLE else View.GONE
        binding.updateButton.isEnabled = !updating
        binding.deleteButton.isEnabled = !updating
        binding.nameAndBirthday.editTextName.isEnabled = !updating
        binding.nameAndBirthday.editTextBirthday.isEnabled = !updating
    }
}