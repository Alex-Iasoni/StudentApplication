package fr.isen.iasoni.studentapplication.Modele
import com.google.firebase.database.PropertyName

data class User(


        @PropertyName("id_like") val id_like: String?,
        @PropertyName("user_id") val user_id: String?,
        @PropertyName("post_id") val post_id: String?,
        @PropertyName("type") val type : Int?,
        @PropertyName("date_added") val date_added :  String?)
    {
        constructor() : this(null,null,null,null,null)
    }



