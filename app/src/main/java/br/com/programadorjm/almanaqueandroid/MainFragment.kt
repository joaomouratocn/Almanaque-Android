package br.com.programadorjm.almanaqueandroid

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.programadorjm.almanaqueandroid.databinding.FragmentMainBinding
import br.com.programadorjm.almanaqueandroid.util.extensions.fragment_ex.navigateTo

class MainFragment : Fragment() {
    lateinit var binding:FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnToastSnackBar.setOnClickListener { navigateTo(R.id.action_mainFragment_to_toastSnackBar) }
    }
}