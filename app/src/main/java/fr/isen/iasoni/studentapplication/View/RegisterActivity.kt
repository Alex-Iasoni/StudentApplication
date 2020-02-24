package fr.isen.iasoni.studentapplication.View

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_register__step_1.*
import java.text.SimpleDateFormat
import java.util.*


class RegisterActivity : AppCompatActivity(), OnFragmentInteractionListener {


    private var frag1Enable = true;
    var currentDate = Date()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        countPage.text = "1/3";


        val registerStep1 = RegisterStep1()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.register, registerStep1).commit()

/*
        activity_state_text.text = "On Create"
*/

        /*date_input.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                date_input.clearFocus()
                val dialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        onDateChoose(year, month, dayOfMonth)
                    },
                    1990,
                    7,
                    25)
                dialog.show()
            }
        }*/


        button_switch.setOnClickListener {
            switchFrag()
        }
    }





  /*  fun onDateChoose(year: Int, month: Int, day: Int) {
        date_input.setText(String.format("%02d/%02d/%04d", day, month+1, year))
        Toast.makeText(this,
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
    }*/

    override fun switchFrag() {
        countPage.text = "2/3";
        if (frag1Enable) {
            val registerStep2 = RegisterStep2()
            val ftm = supportFragmentManager.beginTransaction()
            ftm.replace(R.id.register, registerStep2).commit()
            frag1Enable = false;
        } else {
            val registerStep1 = RegisterStep1()
            val ftm = supportFragmentManager.beginTransaction()
            ftm.replace(R.id.register, registerStep1).commit()
            frag1Enable = true;
        }
    }


}
