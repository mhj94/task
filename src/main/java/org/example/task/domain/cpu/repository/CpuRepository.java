package org.example.task.domain.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.entity.CpuUsage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpuRepository extends JpaRepository<Long, CpuUsage> {

	List<CpuUsage> findAllByTimeBetween(LocalDateTime start, LocalDateTime end);
}
