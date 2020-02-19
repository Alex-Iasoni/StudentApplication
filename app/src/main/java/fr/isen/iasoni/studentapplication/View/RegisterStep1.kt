package fr.isen.iasoni.studentapplication.View


import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.util.Log
import java.util.*
import android.content.Context
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.fragment_register__step_1.*
import java.text.SimpleDateFormat


class RegisterStep1 : Fragment() {
    private var listener: OnFragmentInteractionListener? = null



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?




    ): View? {
        Log.d("Message","onCreateView");





        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register__step_1, container, false)
    }



    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            //listener?.switchFrag()
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("Message","onActivityCreate");

    }


    //override fun onStart() {
    //      super.onStart()
    //    swipeButton.setOnClickListener{
    //      listener = context
    //}else{

    //}
    //Log.d("Message","onStart");

    //}

    override fun onResume() {
        super.onResume()
        Log.d("Message","onResume");

    }

}



