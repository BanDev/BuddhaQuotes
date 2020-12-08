package org.bandev.buddhaquotes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.bandev.buddhaquotes.databinding.FragmentSampleBinding

class SampleFragment : Fragment() {
    companion object {
        fun newInstance(position: Int): SampleFragment {
            val instance =
                SampleFragment()
            val args = Bundle()
            args.putInt("position", position)
            instance.arguments = args
            return instance
        }
    }

    private var _binding: FragmentSampleBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSampleBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val position = arguments?.getInt("position", -1) ?: -1
        binding.textContent.text = "Test $position"
    }
}