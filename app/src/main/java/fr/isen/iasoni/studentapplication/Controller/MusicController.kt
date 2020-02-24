package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.Music
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MusicController {

    @RequiresApi(Build.VERSION_CODES.O)
    fun DateCurrent() : String{
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        val formatted = current.format(formatter)
        return formatted
    }
    val database = FirebaseDatabase.getInstance()
    fun getMusic (id_music: String?,callback: (Music) -> Unit ) {
        var music : Music= Music()
        val myRef = database.getReference("Musics")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_music)){

                        music  = value.getValue(Music::class.java)!!

                    }
                }
                callback.invoke(music)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun MusicExist(name : String?) : Boolean{
        val data = database.getReference("Musics")
        var exist : Boolean = false
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    var MusicComp = value.getValue(Music::class.java)!!
                    if(MusicComp.name != name){

                        exist = false
                    }else{
                        exist = true
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        return exist
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMusic(img : String?, name: String?){
        val data = database.getReference("Musics")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        if(!MusicExist(name)){
            var music = Music(newId,name, date)
            data.child(newId).setValue(music)
        }


    }


}