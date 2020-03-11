package fr.isen.iasoni.studentapplication.Modele.Event
import com.google.firebase.database.PropertyName

data class Event(

    @PropertyName("id_event") var id_event: String?,
    @PropertyName("name") var name: String?,
    @PropertyName("img") var img: String?,
    @PropertyName("id_user_admin") var id_user_admin: String?,
    @PropertyName("id_subscribe_event") var id_subscribe_event: String,//SubscribeEventModel
    @PropertyName("adresse") var adresse: String?,
    @PropertyName("zip") var zip :  String?,
    @PropertyName("id_city") var id_city :  String?,
    @PropertyName("id_school") var id_school :  String?,
    @PropertyName("id_music") var id_music :  ArrayList<String>?, //MusicModel
    @PropertyName("start_date") var start_date : String?,
    @PropertyName("end_date") var end_date : String?,
    @PropertyName("description") var description : String?,
    @PropertyName("etudiant") var etudiant : String?,
    @PropertyName("limit_user") var limit_user : Int?,
    @PropertyName("date_added") var date_added: String?,
    @PropertyName("complete") var complete: String?)


{
    constructor() : this(null,null,null,null,"",null,null,null,null,null,null,null,null,null,null,null,null)


}



