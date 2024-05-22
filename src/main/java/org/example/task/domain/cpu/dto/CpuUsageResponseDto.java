package org.example.task.domain.cpu.dto;

import java.time.LocalDateTime;

import org.example.task.domain.cpu.entity.CpuUsage;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CpuUsageResponseDto {

	private Long id;
	private LocalDateTime time;
	private Double usage;

	public CpuUsageResponseDto(CpuUsage cpuUsage) {
		this.time = cpuUsage.getTime();
		this.usage = cpuUsage.getUsage();
	}

}
