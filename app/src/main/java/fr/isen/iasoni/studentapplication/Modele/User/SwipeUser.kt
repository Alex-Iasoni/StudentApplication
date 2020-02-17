package fr.isen.iasoni.studentapplication.Modele.User

import com.google.firebase.database.PropertyName

data class SwipeUser(

    @PropertyName("id_swipe") val id_swipe: String?,
    @PropertyName("id_event") val id_event: String?,
    @PropertyName("position") val position: Int?,
    @PropertyName("id_user") val id_user: String?)


{
    constructor() : this(null,null,null,null)
}