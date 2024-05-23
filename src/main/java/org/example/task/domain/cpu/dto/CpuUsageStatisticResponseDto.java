package org.example.task.domain.cpu.dto;

import lombok.Getter;

@Getter
public class CpuUsageStatisticResponseDto {

	private Double minimumCpuUsage;
	private Double maximumCpuUsage;
	private Double averageCpuUsage;

	public CpuUsageStatisticResponseDto(Double minimumCpuUsage, Double maximumCpuUsage, Double averageCpuUsage) {
		this.minimumCpuUsage = minimumCpuUsage;
		this.maximumCpuUsage = maximumCpuUsage;
		this.averageCpuUsage = averageCpuUsage;
	}

}
