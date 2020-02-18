package fr.isen.iasoni.studentapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), OnFragmentInteractionListener {


    private var frag1Enable = true;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val registerStep1 = RegisterStep1()
        val ft = supportFragmentManager.beginTransaction()
        ft.add(R.id.register, registerStep1).commit()

        activity_state_text.text = "On Create"

        button_switch.setOnClickListener {
            switchFrag()
        }
    }

    override fun switchFrag() {
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
