package org.example.task.domain.cpu.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cpu")
public class Cpu {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cpu_time")
	private LocalDateTime time;

	@Column(name = "cpu_usage")
	private Double usage;

	public Cpu(Double cpuUsage) {
		this.usage = cpuUsage;
		this.time = LocalDateTime.now();
	}
}
