package br.com.programadorjm.almanaqueandroid.toast_snackbar

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import br.com.programadorjm.almanaqueandroid.R
import br.com.programadorjm.almanaqueandroid.databinding.FragmentToastSnackBarBinding
import com.google.android.material.snackbar.Snackbar

class ToastSnackBarFragment : Fragment() {
    lateinit var binding:FragmentToastSnackBarBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToastSnackBarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Redefine apenas este fragmento para os dois modos de orientação da tela.
        //Manifesto esta definido somente portrait para a aplicação
        //requireActivity().requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED

        binding.btnShowToast.setOnClickListener{
            Toast.makeText(requireContext(), getString(R.string.str_sample_toast),Toast.LENGTH_SHORT).show()
        }

        binding.btnShowSnackBar.setOnClickListener {
            Snackbar.make(view, getString(R.string.str_sample_sanckBar), Snackbar.LENGTH_SHORT).show()
        }

        binding.btnShowSanckBarWithAction.setOnClickListener {
            Snackbar.make(view, getString(R.string.str_sample_snackbar_with_action), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.str_Undo), View.OnClickListener {
                    Toast.makeText(requireContext(), getString(R.string.str_success),Toast.LENGTH_SHORT).show()
                })
                .setActionTextColor(Color.RED)
                .setBackgroundTint(Color.BLUE).show()
        }
    }
}