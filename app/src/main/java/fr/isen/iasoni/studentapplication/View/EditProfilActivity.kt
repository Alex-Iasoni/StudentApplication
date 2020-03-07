package fr.isen.iasoni.studentapplication.View

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import fr.isen.iasoni.studentapplication.Adapters.BadgeProfilAdapter
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.SchoolController
import fr.isen.iasoni.studentapplication.Controller.UserController
import fr.isen.iasoni.studentapplication.Modele.Badge
import fr.isen.iasoni.studentapplication.Modele.City
import fr.isen.iasoni.studentapplication.Modele.Music
import fr.isen.iasoni.studentapplication.Modele.User.User
import fr.isen.iasoni.studentapplication.R
import kotlinx.android.synthetic.main.activity_edit_profil.*
import kotlinx.android.synthetic.main.activity_profil.*
import java.util.*
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.View
import android.widget.*
import com.squareup.picasso.Picasso


class EditProfilActivity : AppCompatActivity() {

    companion object {
        val TAG = "RegisterActivity"
    }
    var selectedPhotoUri: Uri? = null
    lateinit var optionVille : Spinner
    lateinit var optionSchool : Spinner

    var school: String? = ""
    var ville: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        val imageView = findViewById<ImageView>(R.id.selectphoto_imageview_register)

        val uid = FirebaseAuth.getInstance().uid ?: ""
        var userContollerImg = UserController()
        userContollerImg.getUser(uid){
            if(it.img_profil != "none" && it.img_profil != null) {
                Glide.with(this).load(it.img_profil).into(imageView)
            }

        }



        selectphoto_button_register.setOnClickListener {
            Log.d(TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }


        optionVille = findViewById(R.id.spinnerVille) as Spinner

        var cityController  = CityController()
        var optionsVilles =  ArrayList<String?>()

        cityController.getCities{

            for(city in it){
                optionsVilles.add(city.name)
            }
            optionVille.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsVilles)

            optionVille.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    ville = optionsVilles.get(position)
                }
            }
        }
        optionSchool = findViewById(R.id.spinnerSchool) as Spinner
        var schoolController  = SchoolController()
        var optionsSchools =  ArrayList<String?>()

        schoolController.getSchools{
            for(school in it){
                optionsSchools.add(school.name)
            }
            optionSchool.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsSchools)
            optionSchool.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    school = optionsSchools.get(position)
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            selectphoto_imageview_register.setImageBitmap(bitmap)

            selectphoto_button_register.alpha = 0f

        }
    }

    private fun performRegister() {

        uploadImageToFirebaseStorage()


    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$filename")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location: $it")

                    saveUserToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveUserToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")


        var userController = UserController()
        userController.getUser(uid){

            var userToSet = it
            userToSet.img_profil = profileImageUrl


            var cityController = CityController()
            cityController.getIdCity(ville.toString()){
                userToSet.id_city = it

                var schoolController = SchoolController()
                schoolController.getIdSchool(school){

                    userToSet.id_school = it

                    ref.setValue(userToSet)
                        .addOnSuccessListener {
                            Log.d(TAG, "Finally we saved the user to Firebase Database")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Failed to set value to database: ${it.message}")
                        }


                }



            }


        }



    }



}

