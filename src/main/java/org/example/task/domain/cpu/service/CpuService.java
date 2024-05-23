package org.example.task.domain.cpu.service;

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

	// 지정시간 분단위 조회
	public List<CpuUsageResponseDto> getCpuUsageListByMinutes(LocalDateTime start, LocalDateTime end) {

		List<Cpu> cpuUsageList = cpuRepository.findAllByTimeBetween(start, end);
		return cpuUsageList.stream().map(CpuUsageResponseDto::new).toList();
	}

	// 지정날짜 시간단위 조회(최소/최대/평균)
	public List<CpuUsageStatisticResponseDto> getCpuUsageStatisticsListByHours(LocalDateTime start, LocalDateTime end) {

		LocalDateTime startDate = start.withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime endDate = end.plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);

		return cpuRepository.findCpuUsageStatisticsListByTimeBetween(startDate, endDate);
	}
}
