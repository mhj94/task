package org.example.task.domain.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto;
import org.example.task.domain.cpu.entity.Cpu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CpuRepository extends JpaRepository<Cpu, Long> {

	List<Cpu> findAllByTimeBetween(LocalDateTime start, LocalDateTime end);

	@Query("SELECT new org.example.task.domain.cpu.dto.CpuUsageStatisticResponseDto(MIN(c.usage), MAX(c.usage), AVG(c.usage))" +
		"FROM Cpu c WHERE c.time BETWEEN :start AND :end GROUP BY FUNCTION('DAY', c.time)")
	List<CpuUsageStatisticResponseDto> findCpuUsageStatisticsListByTimeBetween(LocalDateTime start, LocalDateTime end);


}
