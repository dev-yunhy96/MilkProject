package com.mk.api.dto.response;

import java.time.LocalDateTime;
import java.util.Map;

import com.mk.db.code.Code;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel("ItemGetResponseDto")
public class ItemGetResponseDto extends BaseResponseDto {

	@ApiModelProperty(name="아이템 ID")
	private String itemId;
	
	@ApiModelProperty(name="작성자 ID")
	private String userId;
	
	@ApiModelProperty(name="작성자 닉네임")
	private String userNiㄴckname;
	
	@ApiModelProperty(name="제목", example="커뮤니티 제목이에요")
	private Code division;

	@ApiModelProperty(name="상품명", example="유모차")
	private String itemName;

	@ApiModelProperty(name="카테고리", example="B01")
	private Code category;

	@ApiModelProperty(name="가격", example="30000")
	private int price;
	
	@ApiModelProperty(name="설명", example = "설명이에요~")
	private String description;
	
	@ApiModelProperty(name="등록일시", example = "2022-01-01 00:00:00")
	private LocalDateTime regDate;
	
	@ApiModelProperty(name="법정동코드", example = "00000000")
	private int position;

	@ApiModelProperty(name="대여시작일", example="2022-01-01 00:00:00")
	private LocalDateTime rentStartDate;

	@ApiModelProperty(name="대여종료일", example="2022-01-01 00:00:00")
	private LocalDateTime rentEndDate;
	
	@ApiModelProperty(name="파일", example="orginFile, 파일 URL")
	private Map<String, String> files;
	
}
