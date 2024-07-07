package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.EasyRandomExclude;
import com.whalar.gameofthrones.common.EasyRandomFakerName;
import com.whalar.gameofthrones.common.EasyRandomFakerNickname;
import com.whalar.gameofthrones.common.EasyRandomFakerUrl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "characters")
public class Character {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EasyRandomExclude
	private Long id;

	@EasyRandomFakerName
	private String name;

	@EasyRandomFakerNickname
	private String nickname;

	@EasyRandomFakerUrl
	private String link;

	private boolean royal;

	private boolean kingsguard;

	@Column(name = "image_full")
	@EasyRandomFakerUrl
	private String imageFull;

	@Column(name = "image_thumb")
	@EasyRandomFakerUrl
	private String imageThumb;

	@OneToMany(mappedBy = "character", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Actor> actors = new ArrayList<>();

	@OneToMany(mappedBy = "character", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<House> houses = new ArrayList<>();

	@OneToMany(mappedBy = "character", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Ally> allies = new ArrayList<>();

	@OneToMany(mappedBy = "character", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Relationship> relationships = new ArrayList<>();

	@OneToMany(mappedBy = "character", fetch = FetchType.EAGER)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Action> actions = new ArrayList<>();
}