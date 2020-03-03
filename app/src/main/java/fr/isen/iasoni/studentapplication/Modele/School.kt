package fr.isen.iasoni.studentapplication.Modele

import com.google.firebase.database.PropertyName


data class School(

    @PropertyName("id_school") var id_school: String?,
    @PropertyName("name") var name: String?,
    @PropertyName("adresse") var adresse: String?,
    @PropertyName("img") var img: String?,
    @PropertyName("users") var users: ArrayList<String>?,
    @PropertyName("event") val event: ArrayList<String>?,
    @PropertyName("date_added") var date_added: String?)



{
    constructor() : this(null,null, null,null,null,null,null)
    constructor(id : String?, name : String?, adresse: String?, img: String?,date_added: String? ) : this(){ //AddSchool
        this.id_school = id
        this.name = name
        this.adresse = adresse
        this.img = img
        this.date_added = date_added

    }
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


