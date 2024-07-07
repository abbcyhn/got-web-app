package com.whalar.gameofthrones.utils;

import com.whalar.gameofthrones.contract.CharacterSaveRequest;
import com.whalar.gameofthrones.model.Actor;
import com.whalar.gameofthrones.model.Character;
import lombok.Data;

import java.util.List;

@Data
public class CharacterSaveRequestDetail {

	CharacterSaveRequest apiRequest;
	List<Actor> actors;
	List<Character> allies;
	List<Character> parents;
	List<Character> parentOf;
	List<Character> siblings;
	List<Character> marriedEngaged;
	List<Character> abducted;
	List<Character> abductedBy;
	List<Character> killed;
	List<Character> killedBy;
	List<Character> serves;
	List<Character> servedBy;
	List<Character> guardianOf;
	List<Character> guardedBy;
}
