package org.example.task.domain.cpu.service;

import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.dto.CpuUsageResponseDto;
import org.example.task.domain.cpu.entity.CpuUsage;
import org.example.task.domain.cpu.repository.CpuRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CpuService {

	private final CpuRepository cpuRepository;
	public List<CpuUsageResponseDto> getCpuUsageList(LocalDateTime start, LocalDateTime end) {

		List<CpuUsage> cpuUsageList = cpuRepository.findAllByTimeBetween(start, end);

		return cpuUsageList.stream().map(CpuUsageResponseDto::new).toList();
	}
}
