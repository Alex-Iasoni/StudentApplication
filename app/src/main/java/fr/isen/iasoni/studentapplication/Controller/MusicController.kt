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
import fr.isen.iasoni.studentapplication.Modele.School
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class MusicController {

    fun DateCurrent() : String{
        var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        return dateString
    }
    val database = FirebaseDatabase.getInstance()

    fun getMusics(callback: ( ArrayList<Music>) -> Unit ) {
        var musics : ArrayList<Music> = ArrayList<Music>()
        val myRef = database.getReference("Musics")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var music : Music = Music()
                    music  = value.getValue(Music::class.java)!!
                    musics.add(music)
                }

                callback.invoke(musics)
            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
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
    fun MusicExist(name : String?, callback: (Boolean) -> Unit){
        val data = database.getReference("Musics")

        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var exist : Boolean = false
                for (value in dataSnapshot.children){

                    var MusicComp = value.getValue(Music::class.java)!!
                    if(MusicComp.name != name){

                        exist = false
                    }else{
                        exist = true
                        break;
                    }
                }
                callback.invoke(exist)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun addMusic( name: String?){
        val data = database.getReference("Musics")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        var exist : Boolean = false
        MusicExist(name){
            exist = it
            if (exist == false){
                var music = Music(newId,name, date)
                data.child(newId).setValue(music)
            }

        }



    }

    fun editEventArray(id_music: String?, id_event: String) {
        val data = database.getReference("Musics" + id_music)

        var music: Music = Music()
        getMusic(id_music) {
            music = it


            var events: ArrayList<String>? = music!!.event

            events!!.add(id_event)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("event", events)
            data.updateChildren(childUpdates)
        }
    }


    fun getIdMusic(name : String?,  callback: (String?) -> Unit){

        var idMusic : String? = ""
        val myRef = database.getReference("Musics")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var music  =   value.getValue(Music::class.java)!!
                    if(music.name.equals(name)){

                        idMusic = music.id_music

                    }
                }
                callback.invoke(idMusic)
            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })


    }


}