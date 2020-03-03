package fr.isen.iasoni.studentapplication.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register1)
        countPage.text = "2/3";



        surname =  intent.getStringExtra("surname")
        mail =  intent.getStringExtra("mail")
        name =  intent.getStringExtra("name")
        birthDate =  intent.getStringExtra("birthDate")



        button_switch.setOnClickListener {
            val foo = Intent(this, RegisterActivity2::class.java)
            foo.putExtra("school", cityEdit.text.toString())
            foo.putExtra("city", schoolEdit.text.toString())
            foo.putExtra("mdp", mdp1.text.toString())
            foo.putExtra("surname", surname)
            foo.putExtra("birthDate", birthDate)
            foo.putExtra("email", mail)
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
