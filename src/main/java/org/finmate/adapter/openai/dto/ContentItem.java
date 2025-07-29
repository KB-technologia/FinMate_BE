package org.finmate.adapter.openai.dto;

import java.util.List;
import lombok.Data;

@Data
public class ContentItem{
	private List<Object> annotations;
	private String text;
	private String type;
}