package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class Badges(

    @PropertyName("id_badge") val id_badge: String?,
    @PropertyName("img") val name: String?,
    @PropertyName("name") val img: String?)


{
    constructor() : this(null,null, null)
}
