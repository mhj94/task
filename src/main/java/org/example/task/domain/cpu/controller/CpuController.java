package org.example.task.domain.cpu.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.dto.CpuUsageResponseDto;
import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto;
import org.example.task.domain.cpu.service.CpuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cpu-usage")
@Tag(name = "CPU usage", description = "CPU 사용량 통계 API")
public class CpuController {

	private final CpuService cpuService;

	@GetMapping("/minutes")
	@Operation(summary = "분 단위 통계", description = "최근 1주간의 데이터를 분 단위로 제공합니다.")
	public ResponseEntity<List<CpuUsageResponseDto>> getCpuUsageByMinutes(@RequestParam("start") LocalDateTime startTime,
		@RequestParam("end")LocalDateTime endTime) {

		List<CpuUsageResponseDto> cpuUsageResponseDtoList = cpuService.getCpuUsageListByMinutes(startTime, endTime);
		return ResponseEntity.ok(cpuUsageResponseDtoList);
	}

	@GetMapping("/hours")
	@Operation(summary = "시 단위 통계", description = "최근 3달간의 데이터를 시 단위로 제공합니다.")
	public ResponseEntity<List<CpuUsageStatisticResponseDto>> getCpuUsageStatisticsListByHours(@RequestParam("start") LocalDate startDay, @RequestParam("end") LocalDate endDay) {

		List<CpuUsageStatisticResponseDto> cpuUsageStatisticsResponseDtoList = cpuService.getCpuUsageStatisticsListByHours(
			startDay, endDay);
		return ResponseEntity.ok(cpuUsageStatisticsResponseDtoList);
	}

	@GetMapping("/days")
	@Operation(summary = "일 단위 통계", description = "최근 1년간의 데이터를 일 단위로 제공합니다.")
	public ResponseEntity<List<CpuUsageStatisticResponseDto>> getCpuUsageStatisticsListByDay(@RequestParam("start") LocalDate startDay, @RequestParam("end") LocalDate endDay) {

		List<CpuUsageStatisticResponseDto> cpuUsageStatisticsResponseDtoList = cpuService.getCpuUsageStatisticsListByDay(
			startDay, endDay);
		return ResponseEntity.ok(cpuUsageStatisticsResponseDtoList);
	}

}
