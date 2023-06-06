package com.tazarki.weather.presentation.fragments.base

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.tazarki.weather.R

typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {
    private var _binding: VB? = null
    val binding get() = _binding!!
    var rotated = false
    lateinit var dialogLoading: Dialog


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }


    fun showSnackbar(idText: Int) {
        Snackbar.make(binding.root, idText, Snackbar.LENGTH_SHORT)
            .show()
    }

    fun initDialogLoading() {
        val dialogBuilder = AlertDialog.Builder(context)
        val dialogView =
            LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)
        dialogBuilder.setView(dialogView)
        dialogLoading = dialogBuilder.create()
        dialogLoading.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogLoading.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogLoading.setCancelable(false)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}