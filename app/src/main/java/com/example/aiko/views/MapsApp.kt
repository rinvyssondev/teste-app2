package com.example.aiko.views

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.example.aiko.R
import com.example.aiko.data.model.Position
import com.example.aiko.data.model.V
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsApp(viewModel: PositionViewModel) {

    val positionMaps by viewModel.position.observeAsState(null)

    LaunchedEffect(Unit) {
        viewModel.fetchPosition()
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-23.85093875, -46.70703), 13f)
    }

    var px = positionMaps?.l?.flatMap { l -> l.vs.map { v -> v.px } }
    var py = positionMaps?.l?.flatMap { l -> l.vs.map { v -> v.py } }
    var avgPy = py?.distinct()?.toList()
    var avgPx = px?.distinct()?.toList()

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        avgPx?.zip(avgPy ?: listOf())?.forEach { (px, py) ->
            println(px.toString() + " " + py)
            Marker(
                state = MarkerState(position = LatLng(py, px)),
                title = "Name of city",
                snippet = "Description of city",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.bus)
            )
        }
//        Marker(
//            state = MarkerState(position = LatLng(-23.85093875, -46.70703)),
//            title = "Name of city",
//            snippet = "Description of city",
//            icon = BitmapDescriptorFactory.fromResource(R.drawable.bus)
//        )
    }
}
