package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.EasyRandomExclude;
import com.whalar.gameofthrones.constant.RelationType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Getter
@Setter
@Entity
@Table(name = "relationships")
public class Relationship {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EasyRandomExclude
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "relation_type")
	@JdbcTypeCode(SqlTypes.NAMED_ENUM)
	private RelationType relationType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "character_id", nullable = false)
	@EasyRandomExclude
	private Character character;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "relation_to", nullable = false)
	private Character relationTo;
}
