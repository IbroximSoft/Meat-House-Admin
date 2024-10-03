package uz.ibrohim.meat.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import uz.ibrohim.meat.R
import uz.ibrohim.meat.databinding.FragmentCategoryBinding
import uz.ibrohim.meat.databinding.FragmentHomeBinding

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.apply {

        }

        return binding.root
    }

}