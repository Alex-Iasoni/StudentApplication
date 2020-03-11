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

class CreateEventActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, LocationListener {

    private lateinit var mAuth: FirebaseAuth
    lateinit var optionVille : Spinner
    lateinit var optionSchool : Spinner
    lateinit var optionMusic : Spinner

    var selectedPhotoUri: Uri? = null
    var music: String? = ""
    var arrayMusic = ArrayList<String>()
    var school: String? = ""
    var ville: String? = ""

    lateinit  var locationManager: LocationManager
    var currentDate = Date()

    companion object {
        val pictureRequestCode = 1
        val contactPermissionRequestCode = 2
        val gpsPermissionRequestCodde = 3
        val TAG = "CreateEventActivity"

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
/*
        locationTextView.text = "latitude : ${location.latitude} \nlongitude : ${location.longitude}"
*/
    }


    override fun onLocationChanged(location: Location?) {
        location?.let {
            refreshPositionUI(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

       super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        var array : ArrayList<String> = ArrayList<String>()

        mAuth = FirebaseAuth.getInstance()

        val imageView = findViewById<ImageView>(R.id.selectphoto_imageview_register)

        val uid = FirebaseAuth.getInstance().uid ?: ""

        var eventControllerImage = EventController()
        eventControllerImage.getEvent(uid){
            if(it.img != "none" && it.img != null) {
                Glide.with(this).load(it.img).into(imageView)
            }

        }

//        createButton.setOnClickListener {
//            performRegister()
//        }
//
//        addCover.setOnClickListener {
//            Log.d(TAG, "Try to show photo selector")
//
//            val intent = Intent(Intent.ACTION_PICK)
//            intent.type = "image/*"
//            startActivityForResult(intent, 0)
//        }



        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

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
            val uid = FirebaseAuth.getInstance().uid ?: ""
            var eventController = EventController()

            var etudiant = "false"

            eventController.createEvent(eventTitle.text.toString(),uid,eventPlace.text.toString(), "", ville.toString(), school.toString(), arrayMusic, date_event_input.text.toString(), date_event_input_2.text.toString(), eventDescription.text.toString(), etudiant.toString(),  nbPlacesEdit.toString())

            eventController.FindIdEvent(eventTitle.text.toString(), uid){
                val foo = Intent(this, EventInfoActivity::class.java)
                foo.putExtra("idEvent", it)
                this.startActivity(foo)
            }

        }


/*
        //Get the widgets reference from XML layout
        //
        var tv = TextView(findViewById(R.id.tv))
        var np = NumberPicker(findViewById(R.id.np))

        //Set TextView text color
        tv.setTextColor(Color.parseColor("#ffd32b3b"));

        //Populate NumberPicker values from minimum and maximum value range
        //Set the minimum value of NumberPicker
        np.setMinValue(0);
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(10);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected number from picker
                tv.setText("Selected Number : " + newVal);
            }
        });*/


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

                    saveEventToFirebaseDatabase(it.toString())
                }
            }
            .addOnFailureListener {
                Log.d(TAG, "Failed to upload image to storage: ${it.message}")
            }
    }

    private fun saveEventToFirebaseDatabase(eventImageUrl: String) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val ref = FirebaseDatabase.getInstance().getReference("/Event/$uid")


        var eventController = EventController()
        eventController.getEvent(uid) {

            var eventToSet = it
            eventToSet.img = eventImageUrl


            var cityController = CityController()
            cityController.getIdCity(ville.toString()) {
                eventToSet.id_city = it

                var schoolController = SchoolController()
                schoolController.getIdSchool(school) {

                    eventToSet.id_school = it

                    ref.setValue(eventToSet)
                        .addOnSuccessListener {
                            Log.d(TAG, "Finally we saved the event to Firebase Database")
                        }
                        .addOnFailureListener {
                            Log.d(TAG, "Failed to set value to database: ${it.message}")
                        }


                }


            }


        }




    }

    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {}

    override fun onProviderEnabled(provider: String?) {}

    override fun onProviderDisabled(provider: String?) {}

}
