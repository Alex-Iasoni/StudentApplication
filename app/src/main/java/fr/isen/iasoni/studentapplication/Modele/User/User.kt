package fr.isen.iasoni.studentapplication.Modele.User
import com.google.firebase.database.PropertyName

data class User(

      @PropertyName("id_user") val id_user: String?,
        @PropertyName("name") val name: String?,
        @PropertyName("prenom") val prenom: String?,
      @PropertyName("img_profil") val img_profil: String?,
      @PropertyName("img_cover") val img_cover: String?,
        @PropertyName("email") val email: String?,
        @PropertyName("birthday") val birthday : String?,
        @PropertyName("id_school") val id_school :  String?,
        @PropertyName("city") val city :  String?,
      @PropertyName("certified") val certified :  Boolean?,
        @PropertyName("id_music") val id_music :  ArrayList<String>?,
        @PropertyName("smoke") val smoke : Int?,
        @PropertyName("drink") val drink : ArrayList<String>?,
        @PropertyName("sport") val sport : ArrayList<String>?,
        @PropertyName("level") val level : String?,
        @PropertyName("badges") val badges : ArrayList<String>?,
        @PropertyName("events") val events : ArrayList<String>?)

{
        constructor() : this(null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null)
    }



