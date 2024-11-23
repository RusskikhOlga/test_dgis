package ru.android.cars7

import com.google.gson.Gson
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import ru.android.cars7.databinding.ActivityTestPolygonBinding
import ru.dgis.sdk.coordinates.Bearing
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.map.CameraPosition
import ru.dgis.sdk.map.Color
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.Polygon
import ru.dgis.sdk.map.PolygonOptions
import ru.dgis.sdk.map.Tilt
import ru.dgis.sdk.map.Zoom
import ru.dgis.sdk.map.blue
import ru.dgis.sdk.map.green
import ru.dgis.sdk.map.lpx
import ru.dgis.sdk.map.red
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


class TestPolygonActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTestPolygonBinding.inflate(layoutInflater) }
    private var mapObjectManager: MapObjectManager? = null
    private val polygonsLightBlue: MutableList<Polygon> = mutableListOf()
    private val lightBlueColor: Map<Float, Float> = mapOf(5f to 0f, 6f to 0.3f, 7.3f to 0f)
    private val lightBlueStrokeColor: Map<Float, Float> = mapOf(5f to 0f, 6.0f to 1f)
    private var connectionCamera: AutoCloseable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.mapView.getMapAsync { map ->
            mapObjectManager = MapObjectManager(map)

            val jsonString = assets.open("geozone.json").bufferedReader().use { it.readText() }
            val area: Area = Gson().fromJson(jsonString, Area::class.java)
            for (item in area.data) {
                val contours: List<List<GeoPoint>> = listOf(
                    item.map { GeoPoint(it.latitude, it.longitude) }
                )
                polygonsLightBlue.add(
                    Polygon(
                        PolygonOptions(
                            contours = contours,
                            color = Color(red = 5, green = 171, blue = 192, alpha = 80),
                            strokeColor = Color(red = 5, green = 171, blue = 192, alpha = 255),
                            strokeWidth = 2.lpx
                        )
                    )
                )
            }

            mapObjectManager!!.addObjects(polygonsLightBlue)

            connectionCamera = map.camera.positionChannel.connect { position ->
                val zoom = position.zoom.value

                val newLightBlueColor = lightBlueColor.value(zoom)
                val newLightBlueStrokeColor = lightBlueStrokeColor.value(zoom)

                for (polygon in polygonsLightBlue) {
                    polygon.strokeColor = Color(red = polygon.strokeColor.red, green = polygon.strokeColor.green, blue = polygon.strokeColor.blue, alpha = (255 * newLightBlueStrokeColor).roundToInt())
                    polygon.color = Color(red = polygon.color.red, green = polygon.color.green, blue = polygon.color.blue, alpha = (255 * newLightBlueColor).roundToInt())
                }
            }
        }
    }
}

fun Map<Float, Float>.value(zoom: Float): Float {
    val prev = entries.lastOrNull { it.key < zoom }
    if (prev == null) return entries.first().value

    val next = entries.firstOrNull { it.key > zoom }
    if (next == null) return entries.last().value

    val value = (zoom - prev.key) / (next.key - prev.key)
    if (prev.value == value) return prev.value


    val result = (prev.value - next.value).absoluteValue * value
    if (prev.value > next.value) return prev.value - result

    return prev.value + result
}