package com.whalar.gameofthrones.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.whalar.gameofthrones.constant.HouseName;
import com.whalar.gameofthrones.contract.ActorFetchResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public abstract class BaseCharacter {

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
	private String link = "";

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@Schema(description = "Indicates if the character is royal")
	private boolean royal;

	@JsonInclude(JsonInclude.Include.NON_DEFAULT)
	@Schema(description = "Indicates if the character is a member of the Kingsguard")
	private boolean kingsguard;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "Full image URL of the character (if any)")
	@EasyRandomFakerUrl
	private String imageFull = "";

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "Thumbnail image URL of the character (if any)")
	@EasyRandomFakerUrl
	private String imageThumb = "";

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of house names associated with the character")
	private List<HouseName> houses = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of allies of the character")
	private List<String> allies = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of parents of the character")
	private List<String> parents = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of children of the character")
	private List<String> parentOf = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of siblings of the character")
	private List<String> siblings = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals married or engaged to the character")
	private List<String> marriedEngaged = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals abducted by the character")
	private List<String> abducted = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals who abducted the character")
	private List<String> abductedBy = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals killed by the character")
	private List<String> killed = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals who killed the character")
	private List<String> killedBy = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals served by the character")
	private List<String> serves = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals who served the character")
	private List<String> servedBy = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals guarded by the character")
	private List<String> guardedBy = new ArrayList<>();

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@Schema(description = "List of individuals whom the character guards")
	private List<String> guardianOf = new ArrayList<>();
}
