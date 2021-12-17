package com.example.kotlinfloweksempel1

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.kotlinfloweksempel1.databinding.FragmentFirstBinding
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    fun mySimpleFlow(): Flow<Int> = flow{
        Log.i("flowvalues", "Flow started")
        for (i in 1..3){
            delay(1000)
            emit(i)
        }
    }

    fun flowTest1() = runBlocking {
        mySimpleFlow().collect{ value -> Log.i("flowvalues", value.toString()) }
        Log.i("flowvalues", "Flow finished")
    }

    fun flowTest2() = runBlocking {
        launch {
            for (i in 1..3){
                Log.i("flowvalues", "Not blocked $i")
                delay(1000)
            }
        }
        mySimpleFlow().collect{ value ->  Log.i("flowvalues", value.toString()) }
        Log.i("flowvalues", "Flow finished")
    }

    suspend fun flowTestUI() {
        mySimpleFlow().collect{ value -> binding.flowTextview.text = value.toString(); Log.i("flowvalues", value.toString()) }
    }

    var list = listOf<Int>(1,2, 3, 4)

    suspend fun flowTestList(){
        list.asFlow().collect { number -> Log.i("flowvalues", "$number"); delay(100) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.flowButton1.setOnClickListener{
            flowTest1()
        }

        binding.flowButton2.setOnClickListener{
            flowTest2()
        }

        binding.flowButton3.setOnClickListener{
            lifecycleScope.launch { flowTestUI() }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}