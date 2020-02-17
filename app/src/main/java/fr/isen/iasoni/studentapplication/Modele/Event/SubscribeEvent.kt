package fr.isen.iasoni.studentapplication.Modele.Event

import com.google.firebase.database.PropertyName

data class SubscribeEvent (

    @PropertyName("id_subscribe_event") val id_subscribe_event: String?,
    @PropertyName("id_user") val id_user: ArrayList<String>?


)


{
    constructor() : this(null,null)
}
