package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class City(

    @PropertyName("id_city") var id_city: String?,
    @PropertyName("name") var name: String?,
    @PropertyName("event") val event: ArrayList<String>?,
    @PropertyName("date_added") var date : String?)


{
    constructor() : this(null,null,null,null)
    constructor(id_city : String?, name : String?, date: String?) : this(){ //AddSchool
        this.id_city = id_city
        this.name = name

        this.date = date

    }
}
