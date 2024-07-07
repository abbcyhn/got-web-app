package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.EasyRandomExclude;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "allies")
public class Ally {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EasyRandomExclude
	private Long id;

	@ManyToOne
	@JoinColumn(name = "character_id", nullable = false)
	@EasyRandomExclude
	private Character character;

	@ManyToOne
	@JoinColumn(name = "ally_id", nullable = false)
	@EasyRandomExclude
	private Character ally;
}

