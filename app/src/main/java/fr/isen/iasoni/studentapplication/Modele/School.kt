package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class School(

    @PropertyName("id_school") val id_school: String?,
    @PropertyName("name") val name: String?,
    @PropertyName("adresse") val adresse: String?,
    @PropertyName("img") val img: String?,
    @PropertyName("users") val users: ArrayList<String>?)



{
    constructor() : this(null,null, null,null,null)

    fun getIdSchool(): String? {
        return this.id_school
    }
    fun getNameSchool(): String? {
     return this.name
    }
    fun getAdresseSchool(): String? {
        return this.adresse
    }
    fun getUsersBySchool():  ArrayList<String>? {
        return this.users
    }


                }


