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
    var currentDate = Date()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?




    ): View? {
        Log.d("Message","onCreateView");


        date_input.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                date_input.clearFocus()
                val dialog = DatePickerDialog(requireContext(),
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        onDateChoose(year, month, dayOfMonth)
                    },
                    1990,
                    7,
                    25)
                dialog.show()
            }
        }


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register__step_1, container, false)
    }



    fun onDateChoose(year: Int, month: Int, day: Int) {
        date_input.setText(String.format("%02d/%02d/%04d", day, month+1, year))
        Toast.makeText(requireContext(),
            "date : ${date_input.text.toString()}",
            Toast.LENGTH_LONG).show()
    }

    fun getAge(year: Int, month: Int, day: Int): Int {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        val components = dateString.split("/")
        var age = components[2].toInt() - year
        if(components[1].toInt() < month){
            age--
        } else if (components[1].toInt() == month &&
            components[0].toInt() < day){
            age --
        }
        return age
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



