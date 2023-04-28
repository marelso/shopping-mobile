package com.example.shoppingmobile.basics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass.
 * Use the [BasicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicsFragment : Fragment() {

    private var inputField: EditText? = null
    private var submitButton: Button? = null
    private var recyclerView: RecyclerView? = null
    private var textAdapter: TextAdapter = TextAdapter()
    private lateinit var viewModel: TextViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_basics, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(TextViewModel::class.java)

        inputField = view.findViewById(R.id.inputField)
        submitButton = view.findViewById(R.id.submitButton)
        recyclerView = view.findViewById(R.id.recyclerView)

        submitButton?.setOnClickListener { onSubmitClicked() }

        recyclerView?.layoutManager = LinearLayoutManager(requireContext())
        recyclerView?.adapter = textAdapter

        submitButton?.setOnClickListener { onSubmitClicked() }

        lifecycleScope.launch {
            viewModel.dataList.collect { data ->
                textAdapter.setData(data.toMutableList())
            }
        }
    }

    private fun onSubmitClicked() {
        val text = inputField?.text.toString()

        viewModel.addItem(text)

        inputField?.text?.clear()
    }
}