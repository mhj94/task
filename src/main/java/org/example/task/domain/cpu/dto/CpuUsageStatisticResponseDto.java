package org.example.task.domain.cpu.dto;

import lombok.Getter;

@Getter
public class CpuUsageStatisticResponseDto {

	private final Integer year;
	private final Integer month;
	private final Integer day;
	private Integer hour;
	private final Double minimumCpuUsage;
	private final Double maximumCpuUsage;
	private final Double averageCpuUsage;

	public CpuUsageStatisticResponseDto(Integer year, Integer month, Integer day, Integer hour, Double minimumCpuUsage, Double maximumCpuUsage, Double averageCpuUsage) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.hour = hour;
		this.minimumCpuUsage = minimumCpuUsage;
		this.maximumCpuUsage = maximumCpuUsage;
		this.averageCpuUsage = averageCpuUsage;
	}

	public CpuUsageStatisticResponseDto(Integer year, Integer month, Integer day, Double minimumCpuUsage, Double maximumCpuUsage, Double averageCpuUsage) {
		this.year = year;
		this.month = month;
		this.day = day;
		this.minimumCpuUsage = minimumCpuUsage;
		this.maximumCpuUsage = maximumCpuUsage;
		this.averageCpuUsage = averageCpuUsage;
	}

}
