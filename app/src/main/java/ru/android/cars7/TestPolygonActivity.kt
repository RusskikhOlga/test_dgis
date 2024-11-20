package ru.android.cars7

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.android.cars7.databinding.ActivityTestPolygonBinding
import ru.dgis.sdk.coordinates.GeoPoint
import ru.dgis.sdk.map.Color
import ru.dgis.sdk.map.MapObjectManager
import ru.dgis.sdk.map.Polygon
import ru.dgis.sdk.map.PolygonOptions

class TestPolygonActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTestPolygonBinding.inflate(layoutInflater) }
    private var mapObjectManager: MapObjectManager? = null
    private val polygonsBlue: MutableList<Polygon> = mutableListOf()
    private val polygonsLightBlue: MutableList<Polygon> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.mapView.getMapAsync { map ->
            mapObjectManager = MapObjectManager(map)


            polygonsBlue.add(
                Polygon(
                    PolygonOptions(
                        contours = listOf(
                            listOf(
                                GeoPoint(latitude = 55.046347, longitude = 82.90426),
                                GeoPoint(latitude = 55.042574, longitude = 82.89267),
                                GeoPoint(latitude = 55.037593, longitude = 82.897807),
                                GeoPoint(latitude = 55.040813, longitude = 82.908651),
                                GeoPoint(latitude = 55.046347, longitude = 82.90426),
                            )
                        ),
                        color = Color(red = 49, green = 65, blue = 119, alpha = 80),
                        strokeColor = Color(red = 49, green = 65, blue = 119, alpha = 255),
                    )
                )
            )
            polygonsBlue.add(
                Polygon(
                    PolygonOptions(
                        contours = listOf(
                            listOf(
                                GeoPoint(latitude = 55.042213, longitude = 82.917609),
                                GeoPoint(latitude = 55.036593, longitude = 82.918662),
                                GeoPoint(latitude = 55.038705, longitude = 82.942752),
                                GeoPoint(latitude = 55.044341, longitude = 82.941758),
                                GeoPoint(latitude = 55.042213, longitude = 82.917609),
                            )
                        ),
                        color = Color(red = 49, green = 65, blue = 119, alpha = 80),
                        strokeColor = Color(red = 49, green = 65, blue = 119, alpha = 255),
                    )
                )
            )
            mapObjectManager!!.addObjects(polygonsBlue)

            polygonsLightBlue.add(
                Polygon(
                    PolygonOptions(
                        contours = listOf(
                            listOf(
                                GeoPoint(latitude = 55.106416, longitude = 82.827259),
                                GeoPoint(latitude = 55.101216, longitude = 83.035203),
                                GeoPoint(latitude = 54.947494, longitude = 83.095428),
                                GeoPoint(latitude = 54.905705, longitude = 82.894301),
                                GeoPoint(latitude = 55.014005, longitude = 82.792033),
                                GeoPoint(latitude = 55.106416, longitude = 82.827259),
                            )
                        ),
                        color = Color(red = 193, green = 210, blue = 252, alpha = 80),
                        strokeColor = Color(red = 193, green = 210, blue = 252, alpha = 255),
                    )
                )
            )
            mapObjectManager!!.addObjects(polygonsLightBlue)
        }
    }
}
