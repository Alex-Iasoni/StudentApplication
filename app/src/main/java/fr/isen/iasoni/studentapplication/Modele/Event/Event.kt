package fr.isen.iasoni.studentapplication.Modele.Event
import com.google.firebase.database.PropertyName

data class Event(

    @PropertyName("id_event") var id_event: String?,
    @PropertyName("name") var name: String?,
    @PropertyName("img") var img: String?,
    @PropertyName("id_user_admin") var id_user_admin: String?,
    @PropertyName("id_subscribe_event") var id_subscribe_event: String?,//SubscribeEventModel
    @PropertyName("adresse") var adresse: String?,
    @PropertyName("zip") var zip :  String?,
    @PropertyName("city") var city :  String?,
    @PropertyName("id_music") var id_music :  ArrayList<String>?, //MusicModel
    @PropertyName("start_date") var start_date : String?,
    @PropertyName("end_date") var end_date : String?,
    @PropertyName("description") var description : String?,
    @PropertyName("limit_user") var limit_user : Int?,
    @PropertyName("date_added") var date_added: String?)


{
    constructor() : this(null,null,null,null,null,null,null,null,null,null,null,null,null,null)
    constructor(id : String?, name : String?, id_user_admin: String?, id_subscribe_event: String?, adresse: String?, zip: String?, city: String?, start_date: String?, end_date: String?, description: String?, limit_user: Int?, date_added: String?) : this(){ //Inscription
        this.id_event = id
        this.name = name
        this.id_user_admin = id_user_admin
        this.id_subscribe_event = id_subscribe_event
        this.adresse = adresse
        this.zip = zip
        this.city = city
        this.start_date = start_date
        this.end_date = end_date
        this.description = description
        this.limit_user = limit_user
        this.date_added = date_added

    }

}



