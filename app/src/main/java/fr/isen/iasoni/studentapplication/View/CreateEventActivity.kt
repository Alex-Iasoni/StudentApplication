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
import fr.isen.iasoni.studentapplication.Controller.CityController
import fr.isen.iasoni.studentapplication.Controller.EventController
import fr.isen.iasoni.studentapplication.Controller.MusicController
import fr.isen.iasoni.studentapplication.Controller.SchoolController
import kotlinx.android.synthetic.main.activity_create_event.*
import java.io.File
import java.security.AccessController.getContext
import java.security.acl.Permission
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest
import kotlin.collections.ArrayList

open class CreateEventActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, LocationListener {

    private lateinit var mAuth: FirebaseAuth
    lateinit var optionVille : Spinner
    lateinit var optionSchool : Spinner
    lateinit var optionMusic : Spinner

    var ville: String? = ""
    var music: String? = ""
    var arrayMusic = ArrayList<String>()
    var school: String? = ""

    lateinit  var locationManager: LocationManager
    var currentDate = Date()


    companion object {
        val pictureRequestCode = 1
        val contactPermissionRequestCode = 2
        val gpsPermissionRequestCodde = 3
    }

    @SuppressLint("MissingPermission")
    fun startGPS(){
        locationManager.requestSingleUpdate(LocationManager.NETWORK_PROVIDER, this, null)
        val location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        location?.let{
            refreshPositionUI(it)
        }
    }

    fun refreshPositionUI(location: Location) {
        locationTextView.text = "latitude : ${location.latitude} \nlongitude : ${location.longitude}"
    }

    override fun onLocationChanged(location: Location?) {
        location?.let {
            refreshPositionUI(it)
        }
    }





    override fun onCreate(savedInstanceState: Bundle?) {
       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        mAuth = FirebaseAuth.getInstance()

        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        coverImage.setOnClickListener {
            onChangePhoto()
        }
        date_event_input.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus) {
                date_event_input.clearFocus()
                val dialog = DatePickerDialog(this,
                    DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                        onDateChoose(year, month, dayOfMonth)
                    },
                    1990,
                    7,
                    25)
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
                    1990,
                    7,
                    25)
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
            val user = mAuth?.currentUser
            Log.d("School", school)
            Log.d("Ville", ville)
            var eventController = EventController()
            eventController.createEvent(eventTitle.toString(), user!!.uid,eventPlace.toString(), "", ville.toString(), school.toString(), arrayMusic, date_event_input.text.toString(), date_event_input_2.text.toString(), eventDescription.text.toString(), false,  100)

        }



    }


    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
    }


    fun onChangePhoto() {
        val galleryIntent = Intent(Intent.ACTION_PICK)
        galleryIntent.type = "image/*"

        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val intentChooser = Intent.createChooser(galleryIntent, "Choose your picture library")
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(cameraIntent))
        startActivityForResult(intentChooser, CreateEventActivity.pictureRequestCode)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(grantResults.isNotEmpty()) {
            if (requestCode == CreateEventActivity.contactPermissionRequestCode &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
              /*  readContacts()*/
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == CreateEventActivity.pictureRequestCode &&
            resultCode == Activity.RESULT_OK) {
            if(data?.data != null) { // Gallery
                coverImage.setImageURI(data?.data)
            } else { // Camera
                val bitmap = data?.extras?.get("data") as? Bitmap
                bitmap?.let {
                    coverImage.setImageBitmap(it)
                }
            }
        }
    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

}
