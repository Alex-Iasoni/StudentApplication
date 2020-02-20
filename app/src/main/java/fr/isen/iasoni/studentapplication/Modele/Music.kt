package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class Music(

    @PropertyName("id_music") val id_music: String?,
    @PropertyName("name") val name: String?,
    @PropertyName("date_added") val date : String?)


{
    constructor() : this(null,null,null)
}
