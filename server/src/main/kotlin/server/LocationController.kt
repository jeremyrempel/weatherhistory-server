package server

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class LocationController(val locationRepository: LocationRepository) {

    @RequestMapping("/locations")
    fun findByName(@RequestParam(value = "name") name: String) = locationRepository.findTop20ByNameContainsIgnoreCase(name)
}