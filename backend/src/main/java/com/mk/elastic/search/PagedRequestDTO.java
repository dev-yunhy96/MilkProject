package com.mk.elastic.search;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagedRequestDTO {
	
	private static final int DEFAULT_SIZE = 30;

    private int page;
    private int size;
    
}