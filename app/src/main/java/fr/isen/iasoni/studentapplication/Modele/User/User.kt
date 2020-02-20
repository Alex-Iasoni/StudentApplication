package fr.isen.iasoni.studentapplication.Modele.User
import com.google.firebase.database.PropertyName

data class User(

      @PropertyName("id_user") var id_user: String?,
      @PropertyName("name") var name: String?,
      @PropertyName("surname") var surname: String?,
      @PropertyName("email") var email: String?,
      @PropertyName("birthday") var birthday : String?,
      @PropertyName("id_school") var id_school :  String?,
      @PropertyName("city") var city :  String?,
      @PropertyName("swipes") var swipes : ArrayList<String>?, //SwipeUserModel
      @PropertyName("badges") var badges : ArrayList<String>?, //BadgeModel
      @PropertyName("certified") var certified :  Boolean?,
      @PropertyName("smoke") var smoke : Boolean?,
      @PropertyName("drink") var drink : ArrayList<String>?,
      @PropertyName("sport") var sport : ArrayList<String>?,
      @PropertyName("level") var level : String?,
      @PropertyName("events") var events :  ArrayList<String>?, //EventModel
      @PropertyName("events_admin") var events_admin :  ArrayList<String>?,
      @PropertyName("img_profil") val img_profil: String?,
      @PropertyName("id_music") var id_music :  ArrayList<String>?, //MusicModel
      @PropertyName("img_cover") var img_cover: String?,
      @PropertyName("date_added") var date_added: String?)



{

        constructor() : this(null,null,null,null,null,null, null,null,null,null,null,null,null,null,null,null,null,null,null,null)
        constructor(id : String?, name : String?, surname: String?, email: String?, birthday: String?, id_school: String?, city: String?) : this(){ //Inscription
            this.id_user = id
            this.name = name
            this.surname = surname
            this.email = email
            this.birthday = birthday
            this.id_school = id_school
            this.city = city

        }

}




