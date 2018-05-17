package server

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LocationController(val locationRepository: LocationRepository) {

    @RequestMapping("/location")
    fun query(@RequestParam(value = "q", defaultValue = "") query: String) = locationRepository.findByNameIgnoreCaseContaining(query)
}