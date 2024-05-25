package org.example.task.domain.cpu.controller

import org.example.task.domain.cpu.dto.CpuUsageResponseDto
import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto
import org.example.task.domain.cpu.entity.Cpu
import org.example.task.domain.cpu.service.CpuService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CpuControllerTest extends Specification {

    @Autowired
    MockMvc mockMvc

    @MockBean
    CpuService cpuService

    def "GetCpuUsageByMinutes"() {

        given:
        def startTime = LocalDateTime.now().minusWeeks(1)
        def endTime = LocalDateTime.now()
        def usage = 20.0
        def cpu = new Cpu(usage)
        def cpuUsageResponseDtoList = [
                new CpuUsageResponseDto(cpu), new CpuUsageResponseDto(cpu)
        ]
        cpuService.getCpuUsageListByMinutes(startTime, endTime) >> cpuUsageResponseDtoList

        when:
        def result = mockMvc.perform(get("/api/cpu-usage/minutes")
                .param("start", startTime.toString())
                .param("end", endTime.toString())
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
    }

    def "GetCpuUsageStatisticsListByHours"() {

        given:
        def startDate = LocalDate.now().minusMonths(3)
        def endDate = LocalDate.now()
        def cpuUsageStatisticsResponseDtoList = [
                new CpuUsageStatisticResponseDto(10.0, 20.0, 5 ),
                new CpuUsageStatisticResponseDto(5,10,5)
        ]
        cpuService.getCpuUsageStatisticsListByHours(startDate, endDate) >> cpuUsageStatisticsResponseDtoList

        when:
        def result = mockMvc.perform(get("/api/cpu-usage/hours")
                .param("start", startDate.toString())
                .param("end", endDate.toString())
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
    }

    def "GetCpuUsageStatisticsListByDay"() {

        given:
        def startDate = LocalDate.now().minusYears(1)
        def endDate = LocalDate.now()
        def cpuUsageStatisticsResponseDtoList = [
                new CpuUsageStatisticResponseDto(5.0, 20.0, 5.0),
                new CpuUsageStatisticResponseDto(10.0,10.0,10.0)
        ]
        cpuService.getCpuUsageStatisticsListByDays(startDate, endDate) >> cpuUsageStatisticsResponseDtoList

        when:
        def result = mockMvc.perform(get("/api/cpu-usage/days")
                .param("start", startDate.toString())
                .param("end", endDate.toString())
                .accept(MediaType.APPLICATION_JSON))

        then:
        result.andExpect(status().isOk())
    }
}
