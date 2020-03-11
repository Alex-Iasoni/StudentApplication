package fr.isen.iasoni.studentapplication.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.iasoni.studentapplication.R
import android.R.attr
import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.app.Activity
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.provider.ContactsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.firebase.auth.FirebaseAuth
import fr.isen.iasoni.studentapplication.Modele.Event.Event
import kotlinx.android.synthetic.main.activity_create_event.*
import java.io.File
import java.security.AccessController.getContext
import java.security.acl.Permission
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList
import android.widget.NumberPicker;
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import fr.isen.iasoni.studentapplication.Controller.*
import kotlinx.android.synthetic.main.activity_edit_profil.*

class CreateEventActivity : AppCompatActivity(){


    private lateinit var mAuth: FirebaseAuth
    lateinit var optionVille : Spinner
    lateinit var optionSchool : Spinner
    lateinit var optionMusic : Spinner

    var selectedPhotoUri: Uri? = null
    var music: String? = ""
    var img: String? = "none"
    var arrayMusic = ArrayList<String>()
    var school: String? = ""
    var ville: String? = ""

    var currentDate = Date()

    companion object {
        val TAG = "RegisterActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {

       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        var array : ArrayList<String> = ArrayList<String>()

        mAuth = FirebaseAuth.getInstance()


        val uid = FirebaseAuth.getInstance().uid ?: ""



        addCover.setOnClickListener {
            Log.d(EditProfilActivity.TAG, "Try to show photo selector")

            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        date_event_input.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                date_event_input.clearFocus()
                val dialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        onDateChoose(year, month, dayOfMonth)
                    },
                    2020,
                    3,
                    7)
                dialog.show()
            }
        }

        date_event_input_2.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                date_event_input_2.clearFocus()
                val dialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        onDateChoose2(year, month, dayOfMonth)
                    },
                    2020,
                    3,
                    7)
                dialog.show()
            }
        }

        privateorPublic.text = "Public"

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

        optionMusic = findViewById(R.id.spinnerMusic) as Spinner
        var musicController  = MusicController()
        var optionsMusics =  ArrayList<String?>()

        musicController.getMusics{

            for(music in it){
                optionsMusics.add(music.name)
            }
            optionMusic.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, optionsMusics)

            optionMusic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    music = optionsMusics.get(position)
                }
            }
        }

        buttonAdd.setOnClickListener {
            val inflater = LayoutInflater.from(this@CreateEventActivity)
            val chip_item = inflater.inflate(R.layout.chip_item, null, false) as Chip
            chip_item.text = music
            arrayMusic.add(music.toString())
            chip_item.setOnCloseIconClickListener{view ->
                chipGroupMusic.removeView(view)
            }
            chipGroupMusic.addView(chip_item)
        }


        createButton.setOnClickListener {

            performRegister()
        }


    }





    fun onChangePrivatePublic(view: View){

        if(switchMaterial.isChecked == true){
            privateorPublic.text = "Public"
        }
        else {
            privateorPublic.text = "Etudiant"
        }
    }


    fun requestPermission(permissionToRequest: String, requestCode: Int, handler: ()-> Unit) {
        if(ContextCompat.checkSelfPermission(this, permissionToRequest) != PackageManager.PERMISSION_GRANTED) {
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permissionToRequest)) {
                //display toast
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(permissionToRequest), requestCode)
            }
        } else {
            handler()
        }
    }

    fun onDateChoose(year: Int, month: Int, day: Int) {
        date_event_input.setText(String.format("%02d/%02d/%04d", day, month+1, year))
        Toast.makeText(this,
            "date : ${date_event_input.text.toString()}",
            Toast.LENGTH_LONG).show()
    }
    fun onDateChoose2(year: Int, month: Int, day: Int) {
        date_event_input_2.setText(String.format("%02d/%02d/%04d", day, month+1, year))
        Toast.makeText(this,
            "date : ${date_event_input_2.text.toString()}",
            Toast.LENGTH_LONG).show()
    }

    fun getAge(year: Int, month: Int, day: Int): Int {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val dateString = formatter.format(currentDate)
        val components = dateString.split("/")
        var age = components[2].toInt() - year
        if(components[1].toInt() < month){
            age--
        } else if (components[1].toInt() == month &&
            components[0].toInt() < day){
            age --
        }
        return age
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
                Log.d(CreateEventActivity.TAG, "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener {
                    Log.d(EditProfilActivity.TAG, "File Location: $it")

                    saveEventToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(CreateEventActivity.TAG, "Failed to upload image to storage: ${it.message}")
            }
    }


    private fun saveEventToFirebaseDatabase(profileImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Users/$uid")

        if (profileImageUrl != ""){
            img=profileImageUrl
        }


        var eventController = EventController()

        var etudiant = "false"

        eventController.createEvent(eventTitle.text.toString(),uid,img ,eventPlace.text.toString(), "", ville.toString(), school.toString(), arrayMusic, date_event_input.text.toString(), date_event_input_2.text.toString(), eventDescription.text.toString(), etudiant.toString(),  nbPlacesEdit.text.toString())

        eventController.FindIdEvent(eventTitle.text.toString(), uid){
            val foo = Intent(this, EventInfoActivity::class.java)
            foo.putExtra("idEvent", it)
            this.startActivity(foo)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {
            // proceed and check what the selected image was....
            Log.d(CreateEventActivity.TAG, "Photo was selected")

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUri)

            coverImage.setImageBitmap(bitmap)

            addCover.alpha = 0f

        }
    }

}
