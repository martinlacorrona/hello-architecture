package com.martinlacorrona.helloarchitecture.ui.view.edituser

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.martinlacorrona.helloarchitecture.ui.view.utils.DateUtils
import com.martinlacorrona.helloarchitecture.ui.viewmodel.EditUserViewModel
import com.martinlacorrona.helloarchitecture.ui.viewmodel.UserListViewModel
import com.martinlacorrona.helloarchitecture.R
import com.martinlacorrona.helloarchitecture.databinding.EditUserFragmentBinding
import org.koin.androidx.navigation.koinNavGraphViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class EditUserFragment : Fragment() {

    private var _binding: EditUserFragmentBinding? = null
    private val binding get() = _binding!!

    private val mainVm: UserListViewModel by koinNavGraphViewModel(R.id.nav_graph)
    private val vm: EditUserViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = EditUserFragmentBinding.inflate(inflater, container, false)

        getExtras()
        initValues()
        initBindings()
        initObservers()

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

    private fun initValues() {
        binding.editTextName.setText(vm.name.value)
        vm.birthday.value?.let { binding.editTextBirthday.setText(it.toString()) }
    }

    private fun initBindings() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.editTextName.addTextChangedListener { text -> vm.name.value = text.toString() }

        binding.editTextBirthday.setOnClickListener {
            showDatePicker()
        }

        binding.updateButton.setOnClickListener { vm.updateUser() }
        binding.deleteButton.setOnClickListener { vm.deleteUser() }
    }

    private fun initObservers() {
        vm.birthday.observe(viewLifecycleOwner) {
            it?.let {
                binding.editTextBirthday.setText(formatDate(it))
            }
        }
        vm.isLoadingStatus.observe(viewLifecycleOwner) {
            it?.let {
                isUpdatingView(it)
            }
        }
        vm.isDone.observe(viewLifecycleOwner) {
            if (it == true) {
                mainVm.fetchUserList()
                findNavController().popBackStack()
            }
        }
        vm.isError.observe(viewLifecycleOwner) {
            if (it == true) showError()
        }
    }

    private fun isUpdatingView(updating: Boolean) {
        binding.progressIndicator.visibility = if (updating) View.VISIBLE else View.GONE
        binding.updateButton.isEnabled = !updating
        binding.deleteButton.isEnabled = !updating
        binding.editTextName.isEnabled = !updating
        binding.editTextBirthday.isEnabled = !updating
    }

    private fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_birthday).apply {
                vm.birthday.value?.let { setSelection(it.time) }
            }.build().apply {
                addOnPositiveButtonClickListener {
                    vm.birthday.value = Date(it)
                }
            }.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    private fun formatDate(date: Date): String =
        DateUtils.formatDate(date, DATE_PATTERN)

    private fun showError() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(R.string.an_error_has_occurred)
            .setMessage(R.string.not_possible_to_connect)
            .setPositiveButton(R.string.ok) { _, _ -> }
            .setCancelable(false)
            .show()
        vm.clearError()
    }

    companion object {
        const val DATE_PICKER_TAG = "date_picker"
        const val DATE_PATTERN = "dd MMMM yyyy"
    }
}