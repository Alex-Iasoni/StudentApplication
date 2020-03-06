package fr.isen.iasoni.studentapplication.View

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.UserController
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_create_event.*
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.countPage
import kotlinx.android.synthetic.main.activity_register2.*

class RegisterActivity2 : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    lateinit var optionMusic : Spinner
    lateinit var optionDrink : Spinner


    var surname: String? = ""
    var mail: String? = "test"
    var name: String? = ""
    var birthDate: String? = ""
    var school: String? = ""
    var mdp: String? = ""
    var city: String? = ""
    var music: String? = ""
    var drink: String? = "Un peu"
    var smoke: String? = "Non Fumeur"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register2)
        countPage.text = "3/3";

        mAuth = FirebaseAuth.getInstance()


        surname =  intent.getStringExtra("surname")
        mail =  intent.getStringExtra("mail")
        name =  intent.getStringExtra("name")
        birthDate =  intent.getStringExtra("birthDate")
        school =  intent.getStringExtra("school")
        city =  intent.getStringExtra("city")
        mdp =  intent.getStringExtra("mdp")


        optionMusic = findViewById(R.id.spinnerMusic) as Spinner

        var musicController  = MusicController()
        var options =  ArrayList<String?>()


        musicController.getMusics{

            for(music in it){
                options.add(music.name)
            }
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
                drink = optionsDrink.get(position)
            }

        }

        Log.d("MOTDEPASS",mdp.toString())
        Log.d("MAILLL TOUTTT SES MORT", mail.toString())



    }


    fun onChangeSmoke(view: View){

        if(switchMaterialSmoke.isChecked){
            smoke = "Fumeur"
        }
        else {
            smoke= "Non Fumeur"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createUser(view: View){
        mAuth?.createUserWithEmailAndPassword(mail.toString(), mdp.toString())?.addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                val user = mAuth?.currentUser
                var userController  = UserController()
                userController.register(user!!.uid, name, surname, mail, birthDate, school, city)

            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
            }

        }
    }

}
