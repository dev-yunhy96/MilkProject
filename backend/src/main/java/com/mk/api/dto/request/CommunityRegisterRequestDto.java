package com.mk.api.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel("CommunityRegisterRequestDto")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CommunityRegisterRequestDto {
	
	@ApiModelProperty(name="회원PK", example="00000000-0000-0000-0000-00000000")
	private String userId;
	
	@ApiModelProperty(name="제목", example="커뮤니티 제목이에요")
	private String title;
	
	@ApiModelProperty(name="내용", example = "내용이에요~")
	private String content;
	
}
