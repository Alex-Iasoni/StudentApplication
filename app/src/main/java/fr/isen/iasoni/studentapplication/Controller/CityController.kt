package fr.isen.iasoni.studentapplication.Controller

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.City
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.School
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class CityController {

    fun DateCurrent() : String{
        var currentDate = Date()
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        return dateString
    }
    val database = FirebaseDatabase.getInstance()

    fun getCities(callback: ( ArrayList<City>) -> Unit ) {
        var cities : ArrayList<City> = ArrayList<City>()
        val myRef = database.getReference("Cities")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    var city : City = City()
                    city  = value.getValue(City::class.java)!!
                    cities.add(city)



                }
                callback.invoke(cities)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun getCity (id_city: String?,callback: (City) -> Unit ) {
        var city : City= City()
        val myRef = database.getReference("Cities")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    if(value.key.equals(id_city)){

                        city  = value.getValue(City::class.java)!!

                    }
                }
                callback.invoke(city)

            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })

    }
    fun CityExist(name : String?, callback: (Boolean) -> Unit){
        val data = database.getReference("Cities")
        var exist : Boolean = false
        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){

                    var CityComp = value.getValue(City::class.java)!!
                    if(CityComp.name != name){

                        exist = false
                    }else{
                        exist = true
                    }
                }
                callback.invoke(exist)
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun addCity( name: String?){
        val data = database.getReference("Cities")
        val newId = data.push().key.toString()
        val date = DateCurrent()
        var exist : Boolean = false
        CityExist(name){
            exist = it
            if (!exist){
                var city = City(newId,name, date)
                data.child(newId).setValue(city)
            }

        }



    }

    fun editEventArray(id_city: String?, id_event: String) {
        val data = database.getReference("Cities" + id_city)

        var city: City = City()
        getCity(id_city) {
            city = it


            var events: ArrayList<String>? = city!!.event

            events!!.add(id_event)

            val childUpdates = HashMap<String, Any>()
            childUpdates.put("event", events)
            data.updateChildren(childUpdates)
        }
    }

    fun getIdCity(name : String?,  callback: (String?) -> Unit){

        var idCity : String? = ""
        val myRef = database.getReference("Cities")
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (value in dataSnapshot.children){
                    var city  =   value.getValue(City::class.java)!!
                    if(city.name.equals(name)){

                        idCity = city.id_city

                    }
                }
                callback.invoke(idCity)
            }
            override fun onCancelled(error: DatabaseError) {

                //Log.d
            }
        })


    }


}