package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class School(

    @PropertyName("id_school") val id_school: String?,
    @PropertyName("name") val name: String?,
    @PropertyName("adresse") val adresse: String?,
    @PropertyName("img") val img: String?)


{
    constructor() : this(null,null, null,null)
}
