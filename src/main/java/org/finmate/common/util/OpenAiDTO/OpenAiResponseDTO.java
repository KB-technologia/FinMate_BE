package org.finmate.common.util.OpenAiDTO;

import java.util.List;
import lombok.Data;

@Data
public class OpenAiResponseDTO {
	private Object instructions;
	private Metadata metadata;
	private Reasoning reasoning;
	private Usage usage;
	private int createdAt;
	private boolean store;
	private Object error;
	private List<Object> tools;
	private List<OutputItem> output;
	private Object topP;
	private Object previousResponseId;
	private boolean parallelToolCalls;
	private Object temperature;
	private String toolChoice;
	private String model;
	private String id;
	private Text text;
	private Object incompleteDetails;
	private String truncation;
	private Object user;
	private String object;
	private String status;
	private Object maxOutputTokens;
}