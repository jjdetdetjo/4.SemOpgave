package com.example.kotlinfloweksempel1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kotlinfloweksempel1.TestModel.TestModel
import com.example.kotlinfloweksempel1.TestViewModel.TestViewModel
import com.example.kotlinfloweksempel1.databinding.FragmentSecondBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null
    private var messages = ArrayList<TestModel>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val testViewModel: TestViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }

        binding.flowButton.setOnClickListener{
            lifecycleScope.launch {
                binding.cancelFlowButton.setOnClickListener{
                    cancel()
                    Log.i("flowvalues", "Flow was cancelled")
                }
                testViewModel.collectFlow()
            }
        }

        binding.mockDataFlowButton.setOnClickListener{
            lifecycleScope.launch {
                binding.cancelFlowButton.setOnClickListener{
                    cancel()
                    Log.i("flowvalues", "Flow was cancelled")
                }
                testViewModel.collectMockMessageTest()
            }
        }

        testViewModel.testModelList.observe(viewLifecycleOwner){
            message -> messages.add(message)
            binding.messagesTextview.text = "$messages "
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}