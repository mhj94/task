package org.example.task.domain.cpu.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.dto.CpuUsageResponseDto;
import org.example.task.domain.cpu.service.CpuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cpu-usage")
public class CpuController {

	private final CpuService cpuService;

	@GetMapping("/minutes")
	public ResponseEntity<List<CpuUsageResponseDto>> getCpuUsageByMinutes(@RequestParam("start") LocalDateTime start,
		@RequestParam("end")LocalDateTime end) {

		List<CpuUsageResponseDto> cpuUsageResponseDtoList = cpuService.getCpuUsageList(start, end);

		return null;
	}
}
