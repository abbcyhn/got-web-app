package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.EasyRandomExclude;
import com.whalar.gameofthrones.common.EasyRandomFakerName;
import com.whalar.gameofthrones.common.EasyRandomFakerUrl;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "actors")
public class Actor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EasyRandomExclude
	private Long id;

	@EasyRandomFakerName
	private String name;

	@EasyRandomFakerUrl
	private String link;

	@ManyToOne
	@JoinColumn(name = "character_id")
	@EasyRandomExclude
	private Character character;

	@OneToMany(mappedBy = "actor", fetch = FetchType.EAGER)
	private List<ActiveSeason> activeSeasons = new ArrayList<>();
}