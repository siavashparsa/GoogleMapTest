package meco.evidc.googlemaptest

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.lifecycle.lifecycleScope

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import meco.evidc.googlemaptest.databinding.ActivityMapsBinding
import kotlin.math.log

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Navigation"

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        // Add a marker in Sydney and move the camera
        val mapna = LatLng(35.7370623129875, 51.00088295525412)
        val marker = map.addMarker(MarkerOptions()
            .position(mapna)
            .title("Marker in Mapna")
            .icon(BitmapDescriptorFactory.defaultMarker(134f))
        )

        marker?.tag = "test"
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(mapna, 15f), null)

        map.uiSettings.apply {
            isZoomGesturesEnabled = true
            isZoomControlsEnabled = true
            isScrollGesturesEnabled = true
        }
//        map.setPadding(0,0,300,0)

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return true
    }
    private fun fromVectorToBitmap(id:Int,color:Int) :BitmapDescriptor {

        val vectorDrawable : Drawable? = ResourcesCompat.getDrawable(resources,id,null)
        if (vectorDrawable == null) {
            Log.d("test","testt")
            return BitmapDescriptorFactory.defaultMarker()
        }
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.setBounds(0,0,canvas.width,canvas.height)
        DrawableCompat.setTint(vectorDrawable,color)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}