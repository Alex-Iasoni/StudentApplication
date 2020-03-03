package fr.isen.iasoni.studentapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_create_event.*
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity2 : AppCompatActivity() {

    lateinit var optionMusic : Spinner
    lateinit var optionDrink : Spinner

    var surname: String? = ""
    var mail: String? = ""
    var name: String? = ""
    var birthDate: String? = ""
    var school: String? = ""
    var mdp: String? = ""
    var city: String? = ""
    var music: String? = ""
    var drink: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        countPage.text = "3/3";


        surname =  intent.getStringExtra("surname")
        mail =  intent.getStringExtra("mail")
        name =  intent.getStringExtra("name")
        birthDate =  intent.getStringExtra("birthDate")
        school =  intent.getStringExtra("school")
        city =  intent.getStringExtra("city")
        mdp =  intent.getStringExtra("mdp")





        optionMusic = findViewById(R.id.spinnerMusic) as Spinner

        val musicController = MusicController()
        //musicController.getMusics()

        val options = arrayOf("Rap", "House", "Trap", "RnB", "Ragae", "Electro","Trans","HardStyle")

        optionMusic.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, options)

        optionMusic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                music = options.get(position)
            }

        }


        optionDrink = findViewById(R.id.spinnerDrink) as Spinner

        val optionsDrink = arrayOf("Un peu", "Beaucoup", "A la folie", "Pas du tout")

        optionDrink.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsDrink)

        optionDrink.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                drink = options.get(position)
            }

        }

    }


    fun onChangeSmoke(view: View){

        if(switchMaterial.isChecked == true){
            privateorPublic.text = "Public"
        }
        else {
            privateorPublic.text = "Etudiant"
        }
    }
}
