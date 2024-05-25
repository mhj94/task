package org.example.task.domain.cpu.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.dto.CpuUsageResponseDto;
import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto;
import org.example.task.domain.cpu.entity.Cpu;
import org.example.task.domain.cpu.repository.CpuRepository;
import org.example.task.global.exception.GlobalException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@Service
@RequiredArgsConstructor
@Slf4j
public class CpuService {

	private final CpuRepository cpuRepository;

	public double getCpuUsage() {

		SystemInfo systemInfo = new SystemInfo();
		HardwareAbstractionLayer hardwareAbstractionLayer = systemInfo.getHardware();
		CentralProcessor centralProcessor = hardwareAbstractionLayer.getProcessor();

		// 초기 사용량
		long[] previousTicks = centralProcessor.getSystemCpuLoadTicks();

		try {
			Thread.sleep(1000);
			// 초기 사용량에 대한 사용률
			return centralProcessor.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
		} catch (InterruptedException e) {
			log.error("쓰레드 오류 발생", e);
			throw new GlobalException("CPU 사용량 수집 실패", e);
		}
	}

	@Transactional
	public void saveCpuUsage() {
		cpuRepository.save(new Cpu(getCpuUsage()));
	}
	// 지정시간 분단위 조회
	public List<CpuUsageResponseDto> getCpuUsageListByMinutes(LocalDateTime startTime, LocalDateTime endTime) {

		List<Cpu> cpuUsageList = cpuRepository.findAllByTimeBetween(startTime, endTime);
		return cpuUsageList.stream().map(CpuUsageResponseDto::new).toList();
	}

	// 지정날짜 시간단위 조회(최소/최대/평균)
	public List<CpuUsageStatisticResponseDto> getCpuUsageStatisticsListByHours(LocalDate startDate, LocalDate endDate) {

		// 해당일과 지정일 범위 초기화
		LocalDateTime resetStartDate = startDate.atStartOfDay();
		LocalDateTime restEndDate = endDate.atTime(23, 59, 59, 999999999);

		return cpuRepository.findCpuUsageStatisticsListByHourBetween(resetStartDate, restEndDate);
	}

	// 지정날짜 일단위 조회(최소/최대/평균)
	public List<CpuUsageStatisticResponseDto> getCpuUsageStatisticsListByDays(LocalDate startDate, LocalDate endDate) {

		// 해당일과 지정일 범위 초기화
		LocalDateTime resetStartDate = startDate.atStartOfDay();
		LocalDateTime restEndDate = endDate.atTime(23, 59, 59, 999999999);

		return cpuRepository.findCpuUsageStatisticsListByDayBetween(resetStartDate, restEndDate);
	}
}