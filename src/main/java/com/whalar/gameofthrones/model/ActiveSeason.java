package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.EasyRandomExclude;
import jakarta.persistence.Column;
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
@Table(name = "active_seasons")
public class ActiveSeason {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EasyRandomExclude
	private Long id;

	@Column(name = "season_number")
	private int seasonNumber;

	@ManyToOne
	@JoinColumn(name = "actor_id", nullable = false)
	@EasyRandomExclude
	private Actor actor;
}