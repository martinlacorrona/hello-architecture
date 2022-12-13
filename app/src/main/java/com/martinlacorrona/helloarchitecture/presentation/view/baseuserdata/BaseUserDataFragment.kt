package com.martinlacorrona.helloarchitecture.presentation.view.baseuserdata

import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.martinlacorrona.helloarchitecture.R
import com.martinlacorrona.helloarchitecture.presentation.view.utils.DateUtils
import com.martinlacorrona.helloarchitecture.presentation.viewmodel.BaseUserDataViewModel
import java.util.*

abstract class BaseUserDataFragment : Fragment() {

    abstract val vm: BaseUserDataViewModel

    fun initViews() {
        initValues()
        initBindings()
        initObservers()
    }

    abstract fun initValues()
    abstract fun initBindings()

    open fun initObservers() {
        vm.isLoadingStatus.observe(viewLifecycleOwner) {
            it?.let {
                isUpdatingView(it)
            }
        }
        vm.isDone.observe(viewLifecycleOwner) {
            if (it == true) {
                onIsDone()
            }
        }
        vm.isError.observe(viewLifecycleOwner) {
            if (it == true) showError()
        }
        vm.name.observe(viewLifecycleOwner) {
            enableButtonIfInfoIsCompleted()
        }
        vm.birthday.observe(viewLifecycleOwner) {
            enableButtonIfInfoIsCompleted()
            onBirthdayChange(it)
        }
    }

    abstract fun isUpdatingView(updating: Boolean)
    abstract fun enableButtonIfInfoIsCompleted()
    abstract fun onIsDone()
    abstract fun onBirthdayChange(date: Date?)

    fun showDatePicker() {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(R.string.select_birthday).apply {
                vm.birthday.value?.let { setSelection(it.time) }
            }.build().apply {
                addOnPositiveButtonClickListener {
                    vm.birthday.value = Date(it)
                }
            }.show(parentFragmentManager, DATE_PICKER_TAG)
    }

    fun formatDate(date: Date): String =
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