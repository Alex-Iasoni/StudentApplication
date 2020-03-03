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
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_create_event.*
import java.io.File
import java.security.AccessController.getContext
import java.security.acl.Permission
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import java.util.jar.Manifest

open class CreateEventActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener, LocationListener {

    lateinit var optionMusic : Spinner
    lateinit var resultMusics: TextView

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



        /*   var COUNTRIES: Array<String> = arrayOf("green", "red", "blue")


           ArrayAdapter<String> adapter =
                               new ArrayAdapter<>(
                                   getContext(),
                                   R.layout.dropdown_menu_popup_item,
                                   COUNTRIES);

                       AutoCompleteTextView editTextFilledExposedDropdown =
                           view.findViewById(R.id.filled_exposed_dropdown);
                       editTextFilledExposedDropdown.setAdapter(adapter);*/
       /* requestPermission(android.Manifest.permission.READ_CONTACTS, contactPermissionRequestCode) {
            readContacts()
        }*/

        optionMusic = findViewById(R.id.spinnerMusic) as Spinner
        resultMusics = findViewById(R.id.resultMusics) as TextView

        val options = arrayOf("Rap", "House", "Trap", "RnB", "Ragae", "Electro","Trans","HardStyle")

        optionMusic.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, options)
        
        optionMusic.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            resultMusics.text = "Selectionnez un genre"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resultMusics.text = options.get(position)
            }

        }




    }


 /*   override fun onCreateDialog(savedInstanceState: Bundle): Dialog {
        // Use the current time as the default values for the picker
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        // Create a new instance of TimePickerDialog and return it
        return TimePickerDialog(this, this, hour, minute, DateFormat.is24HourFormat(this))
    }*/

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        // Do something with the time chosen by the user
    }
 /*   fun showTimePickerDialog(v: View) {
        Dialog.show(supportFragmentManager, "timePicker")
    }*/


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



 /*   fun readContacts() {
        val contactList = ArrayList<ContactModel>()
        val contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null)
        while(contacts?.moveToNext() ?: false){
            val displayName = contacts?.getString(contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val contactModel = ContactModel()
            contactModel.displayName = displayName.toString()
            contactList.add(contactModel)
        }
        contactRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        contactRecyclerView.adapter = ContactsAdapter(contactList)
        Log.d("contacts", "${contacts}")

    }*/

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
