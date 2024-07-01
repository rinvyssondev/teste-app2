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

    var px = positionMaps?.l?.flatMap { l -> l.vs.map { v -> Pair(v.px, l.lt0) } }
    var py = positionMaps?.l?.flatMap { l -> l.vs.map { v -> Pair(v.py, l.lt1) } }
    var lt0 = positionMaps?.l?.map { it.lt0 + " - " + it.lt1 }
    var avgPy = py?.toList()
    var avgPx = px?.toList()
//    var combined = avgPx?.zip(avgPy ?: listOf())?.zip(lt0 ?: listOf()){
//        (px, py), lt0 -> Triple(px, py, lt0)
//    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        avgPx?.zip(avgPy ?: listOf())?.forEachIndexed {index, (px, py) ->
            println(px.first.toString() + " - " + px.second + " - " + py)
            Marker(
                state = MarkerState(position = LatLng(py.first, px.first)),
                title = "IDA: ${px.second}",
                snippet = "VOLTA: ${py.second}",
                icon = BitmapDescriptorFactory.fromResource(R.drawable.bus)
            )
        }
//        combined?.forEach { (px, py, lt0) ->
//            println(lt0 + " - " + px + " - " + py)
//            Marker(
//                state = MarkerState(position = LatLng(py, px)),
//                title = "IDA: ${lt0.substringBefore(" - ")}",
//                snippet = "VOLTA: ${lt0.substringAfter(" - ")}",
//                icon = BitmapDescriptorFactory.fromResource(R.drawable.bus)
//            )}
//        Marker(
//            state = MarkerState(position = LatLng(-23.85093875, -46.70703)),
//            title = "Name of city",
//            snippet = "Description of city",
//            icon = BitmapDescriptorFactory.fromResource(R.drawable.bus)
//        )
    }
}
