package com.whalar.gameofthrones.contract;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whalar.gameofthrones.common.EasyRandomExclude;
import com.whalar.gameofthrones.common.EasyRandomFakerName;
import com.whalar.gameofthrones.common.EasyRandomFakerNickname;
import com.whalar.gameofthrones.common.EasyRandomFakerUrl;
import com.whalar.gameofthrones.constant.HouseName;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CharacterSaveRequest  {

	@NotNull(message = "Character name must not be null")
	@NotEmpty(message = "Character name must not be empty")
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "The full name of the character")
	@EasyRandomFakerName
	private String name = "";

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "Nickname or alias of the character")
	@EasyRandomFakerNickname
	private String nickname = "";

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "Link related to the character (if any)")
	@EasyRandomFakerUrl
	private String link;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@Schema(description = "Indicates if the character is royal")
	private boolean royal;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@Schema(description = "Indicates if the character is a member of the Kingsguard")
	private boolean kingsguard;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "Full image URL of the character (if any)")
	@EasyRandomFakerUrl
	private String imageFull;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "Thumbnail image URL of the character (if any)")
	@EasyRandomFakerUrl
	private String imageThumb;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of house names associated with the character")
	private List<HouseName> houses = new ArrayList<>();

	@Schema(description = "List of actor ids of the character")
	@EasyRandomExclude
	private List<Long> actors = new ArrayList<>();

	@Schema(description = "List of ally ids of the character")
	@EasyRandomExclude
	private List<Long> allies = new ArrayList<>();

	@Schema(description = "List of parent ids of the character")
	@EasyRandomExclude
	private List<Long> parents = new ArrayList<>();

	@Schema(description = "List of children ids of the character")
	@EasyRandomExclude
	private List<Long> parentOf = new ArrayList<>();

	@Schema(description = "List of sibling ids of the character")
	@EasyRandomExclude
	private List<Long> siblings = new ArrayList<>();

	@Schema(description = "List of individual ids married or engaged to the character")
	@EasyRandomExclude
	private List<Long> marriedEngaged = new ArrayList<>();

	@Schema(description = "List of individual ids abducted by the character")
	@EasyRandomExclude
	private List<Long> abducted = new ArrayList<>();

	@Schema(description = "List of individual ids who abducted the character")
	@EasyRandomExclude
	private List<Long> abductedBy = new ArrayList<>();

	@Schema(description = "List of individual ids killed by the character")
	@EasyRandomExclude
	private List<Long> killed = new ArrayList<>();

	@Schema(description = "List of individual ids who killed the character")
	@EasyRandomExclude
	private List<Long> killedBy = new ArrayList<>();

	@Schema(description = "List of individual ids served by the character")
	@EasyRandomExclude
	private List<Long> serves = new ArrayList<>();

	@Schema(description = "List of individual ids who served the character")
	@EasyRandomExclude
	private List<Long> servedBy = new ArrayList<>();

	@Schema(description = "List of individual ids guarded by the character")
	@EasyRandomExclude
	private List<Long> guardedBy = new ArrayList<>();

	@Schema(description = "List of individual ids whom the character guards")
	@EasyRandomExclude
	private List<Long> guardianOf = new ArrayList<>();
}
