package org.finmate.common.util.OpenAiDTO;

import java.util.List;
import lombok.Data;

@Data
public class OutputItem{
	private String role;
	private String id;
	private String type;
	private List<ContentItem> content;
	private String status;
}