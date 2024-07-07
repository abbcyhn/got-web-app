package com.whalar.gameofthrones.model;

import com.whalar.gameofthrones.common.BaseCharacter;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Document(indexName = "character")
public class CharacterDocument extends BaseCharacter {

	@Id
	private String id;

	@Field(type = FieldType.Nested, includeInParent = true)
	private List<ActorDocument> actors = new ArrayList<>();
}
