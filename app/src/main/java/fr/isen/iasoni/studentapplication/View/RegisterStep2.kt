package fr.isen.iasoni.studentapplication.View

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_register.*

/**
 * A simple [Fragment] subclass.
 */
class RegisterStep2 : Fragment() {
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("Message","onCreateView");

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register_step_2, container, false)
    }

 /*   override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            //listener?.switchFrag()
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }*/

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
