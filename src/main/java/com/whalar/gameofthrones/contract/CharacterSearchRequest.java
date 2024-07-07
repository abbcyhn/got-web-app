package com.whalar.gameofthrones.contract;

import com.whalar.gameofthrones.constant.HouseName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CharacterSearchRequest {

	@Schema(description = "The full name of the character")
	private String name = "";

	@Schema(description = "Nickname or alias of the character")
	private String nickname = "";

	@Schema(description = "Indicates if the character is royal")
	private Boolean royal;

	@Schema(description = "Indicates if the character is a member of the Kingsguard")
	private Boolean kingsguard;

	@Schema(description = "The full name of the actor")
	private String actorName = "";

	@Schema(description = "House name associated with the character")
	private HouseName houseName;

	@Schema(description = "Ally of the character")
	private String allyName;

	@Schema(description = "Parent of the character")
	private String parentName;

	@Schema(description = "Child of the character")
	String parentOf;

	@Schema(description = "Sibling of the character")
	String sibling;

	@Schema(description = "Individuals married or engaged to the character")
	String marriedEngaged;

	@Schema(description = "Individuals abducted by the character")
	String abducted;

	@Schema(description = "Individuals who abducted the character")
	String abductedBy;

	@Schema(description = "Individuals killed by the character")
	String killed;

	@Schema(description = "Individuals who killed the character")
	String killedBy;

	@Schema(description = "Individuals served by the character")
	String serves;

	@Schema(description = "Individuals who served the character")
	String servedBy;

	@Schema(description = "Individuals guarded by the character")
	String guardedBy;

	@Schema(description = "Individuals whom the character guards")
	String guardianOf;
}
