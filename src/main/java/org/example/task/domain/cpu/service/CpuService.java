package org.example.task.domain.cpu.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.dto.CpuUsageResponseDto;
import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto;
import org.example.task.domain.cpu.entity.Cpu;
import org.example.task.domain.cpu.repository.CpuRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@Service
@RequiredArgsConstructor
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
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 초기 사용량에 대한 사용률
		return centralProcessor.getSystemCpuLoadBetweenTicks(previousTicks) * 100;
	}

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
	public List<CpuUsageStatisticResponseDto> getCpuUsageStatisticsListByDay(LocalDate startDate, LocalDate endDate) {

		// 해당일과 지정일 범위 초기화
		LocalDateTime resetStartDate = startDate.atStartOfDay();
		LocalDateTime restEndDate = endDate.atTime(23, 59, 59, 999999999);

		return cpuRepository.findCpuUsageStatisticsListByDayBetween(resetStartDate, restEndDate);
	}
}