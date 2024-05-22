package org.example.task.domain.cpu.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.example.task.domain.cpu.entity.Cpu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CpuRepository extends JpaRepository<Cpu, Long> {

	List<Cpu> findAllByTimeBetween(LocalDateTime start, LocalDateTime end);
}
