package server

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class MonthlyAvgController(val monthlyAvgRepo: MonthlyAvgRepository) {
    @RequestMapping("/monthlyavg")
    fun get(@RequestParam stationcode: String) = monthlyAvgRepo.findByStationCode(stationcode)
}