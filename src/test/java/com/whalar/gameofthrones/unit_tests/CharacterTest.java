package com.whalar.gameofthrones.unit_tests;

import com.whalar.gameofthrones.model.Character;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CharacterTest {

	@Test
	public void testEquals_EqualObjects_ReturnsTrue() {
		var character1 = new Character();
		character1.setId(1L);
		character1.setName("John");
		character1.setNickname("Johnny");
		character1.setLink("http://example.com");
		character1.setRoyal(true);
		character1.setKingsguard(false);
		character1.setImageFull("http://example.com/full.jpg");
		character1.setImageThumb("http://example.com/thumb.jpg");

		var character2 = new Character();
		character2.setId(1L);
		character2.setName("John");
		character2.setNickname("Johnny");
		character2.setLink("http://example.com");
		character2.setRoyal(true);
		character2.setKingsguard(false);
		character2.setImageFull("http://example.com/full.jpg");
		character2.setImageThumb("http://example.com/thumb.jpg");

		assertThat(character1.equals(character2)).isTrue();
	}

	@Test
	public void testEquals_DifferentObjects_ReturnsFalse() {
		var character1 = new Character();
		character1.setId(1L);
		character1.setName("John");

		var character2 = new Character();
		character2.setId(2L);
		character2.setName("Doe");

		assertThat(character1.equals(character2)).isFalse();
	}

	@Test
	public void testHashCode_EqualObjects_ReturnsSameHashCode() {
		var character1 = new Character();
		character1.setId(1L);
		character1.setName("John");
		character1.setNickname("Johnny");
		character1.setLink("http://example.com");
		character1.setRoyal(true);
		character1.setKingsguard(false);
		character1.setImageFull("http://example.com/full.jpg");
		character1.setImageThumb("http://example.com/thumb.jpg");

		var character2 = new Character();
		character2.setId(1L);
		character2.setName("John");
		character2.setNickname("Johnny");
		character2.setLink("http://example.com");
		character2.setRoyal(true);
		character2.setKingsguard(false);
		character2.setImageFull("http://example.com/full.jpg");
		character2.setImageThumb("http://example.com/thumb.jpg");

		assertThat(character1.hashCode()).isEqualTo(character2.hashCode());
	}

	@Test
	public void testHashCode_DifferentObjects_ReturnsDifferentHashCode() {
		Character character1 = new Character();
		character1.setId(1L);
		character1.setName("John");

		Character character2 = new Character();
		character2.setId(2L);
		character2.setName("Doe");

		assertThat(character1.hashCode()).isNotEqualTo(character2.hashCode());
	}
}
