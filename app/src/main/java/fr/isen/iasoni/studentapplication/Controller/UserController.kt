package fr.isen.iasoni.studentapplication.Controller

import com.google.firebase.database.FirebaseDatabase
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import fr.isen.iasoni.studentapplication.Modele.School
import fr.isen.iasoni.studentapplication.Modele.User.User
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WriteUser {

    companion object {
        private const val TAG = "WriteUser"
    }

    val database = FirebaseDatabase.getInstance()

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateCurrent() : String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun Register(id: String, name: String?, surname: String?, email : String?, birthday: String?, school : String?, city : String?) {
        val formatted = DateCurrent()
        val  posts : ArrayList<String>? = ArrayList<String>()

        val schoolC = SchoolController()
        val id_school = schoolC.getIdSchool(school)


        val dataPost = database.getReference("Users")
        val user = User(id,name, surname, email, birthday, id_school, city)
        dataPost.child(id).setValue(user)

    }

}