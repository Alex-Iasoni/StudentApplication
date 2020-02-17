package fr.isen.iasoni.studentapplication.Modele.Event
import com.google.firebase.database.PropertyName

data class Event(

    @PropertyName("id_event") val id_event: String?,
    @PropertyName("name") val name: String?,
    @PropertyName("img") val img: String?,
    @PropertyName("id_user_admin") val id_user_admin: String?,
    @PropertyName("id_user") val id_user: ArrayList<String>?,
    @PropertyName("id_user") val id_school: ArrayList<String>?,
    @PropertyName("adresse") val adresse: String?,
    @PropertyName("zip") val zip :  String?,
    @PropertyName("city") val city :  String?,
    @PropertyName("id_music") val id_music :  ArrayList<String>?,
    @PropertyName("start_date") val start_date : String?,
    @PropertyName("end_date") val end_date : String?,
    @PropertyName("limit_user") val limit_user : Int?

    )


{
    constructor() : this(null,null,null,null,null,null,null,null,null,null,null,null,null)
}



