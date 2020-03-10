package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class Notification(

    @PropertyName("id_notification") var id_notification: String?,
    @PropertyName("name") var name: String?,
    @PropertyName("id_event") val id_event: String?,
    @PropertyName("id_user") val id_user: String?,
    @PropertyName("id_user_admin") val id_user_admin: String?,
    @PropertyName("date_added") var date : String?)


{
    constructor() : this(null,null, null,null,null,null)

}
