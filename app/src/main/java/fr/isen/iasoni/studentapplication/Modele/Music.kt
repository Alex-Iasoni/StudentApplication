package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class Music(

    @PropertyName("id_music") var id_music: String?,
    @PropertyName("name") var name: String?,
    @PropertyName("event") val event: ArrayList<String>,
    @PropertyName("date_added") var date : String?)


{
    constructor() : this(null,null, arrayListOf(),null)
    constructor(id_music : String?, name : String?, date: String?) : this(){ //AddSchool
        this.id_music = id_music
        this.name = name

        this.date = date

    }
}
