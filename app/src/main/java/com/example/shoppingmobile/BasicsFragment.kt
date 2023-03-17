package com.example.shoppingmobile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.adapters.TextAdapter
import com.example.shoppingmobile.view_model.TextViewModel
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BasicsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BasicsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var inputField: EditText? = null
    private var submitButton: Button? = null
    private var recyclerView: RecyclerView? = null
    private var textAdapter: TextAdapter = TextAdapter()
    private lateinit var viewModel: TextViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

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

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BasicsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BasicsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}