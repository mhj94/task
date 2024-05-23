package org.example.task.domain.cpu.scheduler;

import org.example.task.domain.cpu.entity.Cpu;
import org.example.task.domain.cpu.repository.CpuRepository;
import org.example.task.domain.cpu.service.CpuService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CpuUsageScheduler {
	private static final long delay = 60000L;

	private final CpuService cpuService;
	private final CpuRepository cpuRepository;

	//@Scheduled(fixedDelay = delay)
	@Transactional
	public void collectCpuUsage() {
		cpuRepository.save(new Cpu(cpuService.getCpuUsage()));
	}
}
