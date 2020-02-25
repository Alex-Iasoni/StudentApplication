package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class Badge(

    @PropertyName("id_badge") val id_badge: String?,
    @PropertyName("img") var name: String?,
    @PropertyName("name") var img: String?)


{
    constructor() : this(null,null, null)
}
