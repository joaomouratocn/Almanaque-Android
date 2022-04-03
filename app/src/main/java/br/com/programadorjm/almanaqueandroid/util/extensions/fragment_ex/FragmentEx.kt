package br.com.programadorjm.almanaqueandroid.util.extensions.fragment_ex

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun Fragment.navigateTo(@IdRes dest:Int) = findNavController().navigate(dest)

fun Fragment.navigateTo(@IdRes dest: Int, args: Bundle){
    findNavController().navigate(dest,args)
}

fun Fragment.toast(message:String){
    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
}