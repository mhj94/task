package org.example.task.domain.cpu.repository

import org.example.task.TaskApplication
import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto
import org.example.task.domain.cpu.entity.Cpu
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

import java.time.LocalDateTime

@DataJpaTest
class CpuRepositoryTest extends Specification {

    CpuRepository cpuRepository = Mock(CpuRepository)

    def "Save"() {

        given:
        Cpu cpu = new Cpu(20.0)
        cpuRepository.save(_) >> cpu

        when:
        Cpu saveCpu = cpuRepository.save(cpu)

        then:
        saveCpu.usage == 20.0
    }

    def "FindAllByTimeBetween"() {

        given:
        def startTime = LocalDateTime.now().minusDays(1)
        def endTime = LocalDateTime.now()
        def cpuList = [
                new Cpu(5.0),
                new Cpu(10.0)
        ]
        cpuRepository.findAllByTimeBetween(startTime, endTime) >> cpuList

        when:
        def result = cpuRepository.findAllByTimeBetween(startTime, endTime)

        then:
        result.size() == 2
        result == cpuList

    }

    def "FindCpuUsageStatisticsListByHourBetween"() {

        given:
        def startDate = LocalDateTime.of(2024, 5, 20, 0, 0)
        def endDate = LocalDateTime.of(2024, 5, 25, 23, 59)
        def cpuList = [
                new CpuUsageStatisticResponseDto(20.0, 50.0, 35.0),
                new CpuUsageStatisticResponseDto(25.0, 55.0, 40.0)
        ]
        cpuRepository.findCpuUsageStatisticsListByHourBetween(startDate, endDate) >> cpuList

        when:
        def result = cpuRepository.findCpuUsageStatisticsListByHourBetween(startDate, endDate)

        then:
        result.size() == 2
        result == cpuList
    }

    def "FindCpuUsageStatisticsListByDayBetween"() {

        given:
        def startDate = LocalDateTime.of(2024, 5, 20, 0, 0)
        def endDate = LocalDateTime.of(2024, 5, 25, 23, 59)
        def cpuList = [
                new CpuUsageStatisticResponseDto(10, 40, 30),
                new CpuUsageStatisticResponseDto(20, 60, 40)
        ]
        cpuRepository.findCpuUsageStatisticsListByDayBetween(startDate, endDate) >> cpuList

        when:
        def result = cpuRepository.findCpuUsageStatisticsListByDayBetween(startDate, endDate)

        then:
        result.size() == 2
        result == cpuList
    }
}
