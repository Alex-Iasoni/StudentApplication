package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.SchoolController
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.button_switch
import kotlinx.android.synthetic.main.activity_register.countPage
import kotlinx.android.synthetic.main.activity_register1.*
import java.util.*

class RegisterActivity1 : AppCompatActivity() {


    var surname: String? = ""
    var mail: String? = ""
    var name: String? = ""
    var birthDate: String? = ""
    var ville: String? = ""
    var school: String? = ""


    lateinit var optionVille : Spinner
    lateinit var optionSchool : Spinner



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)
        countPage.text = "2/3";

        surname =  intent.getStringExtra("surname")
        mail =  intent.getStringExtra("mail")
        name =  intent.getStringExtra("name")
        birthDate =  intent.getStringExtra("birthDate")



        optionVille = findViewById(R.id.spinnerVille) as Spinner

        var cityController  = CityController()
        var optionsVilles =  ArrayList<String?>()


        cityController.getCities{

            for(city in it){
                optionsVilles.add(city.name)
            }
            optionVille.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsVilles)

            optionVille.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ville = optionsVilles.get(position)
                }
            }
        }


        optionSchool = findViewById(R.id.spinnerSchool) as Spinner

        var schoolController  = SchoolController()
        var optionsSchools =  ArrayList<String?>()


        schoolController.getSchools{

            for(school in it){
                optionsSchools.add(school.name)
            }
            optionSchool.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsSchools)

            optionSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    school = optionsSchools.get(position)
                }
            }
        }

        button_switch.setOnClickListener {
            val foo = Intent(this, RegisterActivity2::class.java)
            foo.putExtra("school", ville)
            foo.putExtra("city", school)
            foo.putExtra("mdp", mdp1.text.toString())
            foo.putExtra("surname", surname)
            foo.putExtra("birthDate", birthDate)
            foo.putExtra("mail", mail)
            foo.putExtra("name", name)
            if(mdp1.text.toString() == mdp2.text.toString()){
                this.startActivity(foo)

            }else{
                Toast.makeText(this,
                    "Les mots de passes sont differents", Toast.LENGTH_LONG).show()
            }
        }

    }
}
