package org.example.task.domain.cpu.service

import org.example.task.domain.cpu.dto.CpuUsageResponseDto
import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto
import org.example.task.domain.cpu.entity.Cpu
import org.example.task.domain.cpu.repository.CpuRepository
import spock.lang.Specification

import java.time.LocalDate
import java.time.LocalDateTime

class CpuServiceTest extends Specification {
    CpuService cpuService
    CpuRepository cpuRepository = Mock(CpuRepository)

    def setup() {
        cpuService = new CpuService(cpuRepository)
    }

    def "GetCpuUsage"() {

        given:
        def cpuMock = new Cpu(usage: 50.0)
        cpuRepository.save(_ as Cpu) >> cpuMock

        when:
        def result = cpuService.getCpuUsage()

        then: "예외가 발생하지 않아야 함"
        result >= 0 && result <= 100
        noExceptionThrown()
    }

    def "SaveCpuUsage"() {

        given:
        double cpuUsage = 50.0
        def cpuMock = new Cpu(usage: cpuUsage)
        cpuRepository.save(_ as Cpu) >> cpuMock
        cpuService.metaClass.getCpuUsage = { -> cpuUsage }

        when:
        cpuService.saveCpuUsage()

        then:
        1 * cpuRepository.save(_ as Cpu)
    }

    def "GetCpuUsageListByMinutes"() {

        given:
        def startTime = LocalDateTime.now().minusMinutes(1)
        def endTime = LocalDateTime.now()
        def cpuList = [
                new Cpu(usage: 10.0, time: startTime.plusMinutes(1)),
                new Cpu(usage: 60.0, time: startTime.plusMinutes(2))
        ]

        cpuRepository.findAllByTimeBetween(startTime, endTime) >> cpuList
        def expectedResult = cpuList.collect { cpu ->
            return new CpuUsageResponseDto(cpu)
        }

        when:
        def result = cpuService.getCpuUsageListByMinutes(startTime, endTime)

        then:
        result.every { value ->
            expectedResult.find { expectedValue ->
                value.usage == expectedValue.usage && value.time == expectedValue.time
            }
        }
    }

    def "GetCpuUsageStatisticsListByHours"() {

        given:
        def startDate = LocalDate.now().minusDays(1)
        def endDate = LocalDate.now()
        def statisticList = [
                new CpuUsageStatisticResponseDto(24, 05, 23, 13, 10.0, 50.0, 30.0)
        ]

        cpuRepository.findCpuUsageStatisticsListByHourBetween(startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59, 999999999)) >> statisticList

        when:
        def result = cpuService.getCpuUsageStatisticsListByHours(startDate, endDate)

        then:
        result == statisticList
    }

    def "GetCpuUsageStatisticsListByDay"() {
        given:
        def startDate = LocalDate.now().minusDays(7)
        def endDate = LocalDate.now()
        def statisticList = [
                new CpuUsageStatisticResponseDto(24, 05, 23, 5.0, 10.0, 30)
        ]

        cpuRepository.findCpuUsageStatisticsListByDayBetween(startDate.atStartOfDay(),
                endDate.atTime(23, 59, 59, 999999999)) >> statisticList

        when:
        def result = cpuService.getCpuUsageStatisticsListByDays(startDate, endDate)

        then:
        result == statisticList
    }
}
