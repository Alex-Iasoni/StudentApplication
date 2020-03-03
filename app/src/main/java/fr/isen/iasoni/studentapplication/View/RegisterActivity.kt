package fr.isen.iasoni.studentapplication.View

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_register.*
import java.text.SimpleDateFormat
import java.util.*



class RegisterActivity : AppCompatActivity() {
    var currentDate = Date()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        countPage.text = "1/3";


        date_input.setOnFocusChangeListener { view, hasFocus ->
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
        }




        button_switch.setOnClickListener {
            val foo = Intent(this, RegisterActivity1::class.java)
            foo.putExtra("surname", surnameEdit.text.toString())
            foo.putExtra("birthDate", date_input.text.toString())
            foo.putExtra("email", mailEdit.text.toString())
            foo.putExtra("name", nameEdit.text.toString())
            this.startActivity(foo)
        }
    }



    fun onDateChoose(year: Int, month: Int, day: Int) {
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
    }






}
