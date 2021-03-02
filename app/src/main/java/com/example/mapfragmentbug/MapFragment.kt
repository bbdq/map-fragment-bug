package com.example.mapfragmentbug

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment

class MapFragment: Fragment() {

    private var _googleMap: GoogleMap? = null


    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                findNavController().navigate(R.id.detailsFragment)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Allow permissions pls",
                    Toast.LENGTH_LONG
                ).show()
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { map ->
            _googleMap = map
        }

        view.findViewById<Button>(R.id.open_details).setOnClickListener {
            requestPermission.launch(Manifest.permission.CAMERA)
        }

        return view
    }

    override fun onDestroyView() {
        _googleMap = null
        super.onDestroyView()
    }
}