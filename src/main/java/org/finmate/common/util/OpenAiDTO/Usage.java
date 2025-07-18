package org.finmate.common.util.OpenAiDTO;

import lombok.Data;

@Data
public class Usage{
	private InputTokensDetails inputTokensDetails;
	private int totalTokens;
	private int outputTokens;
	private int inputTokens;
	private OutputTokensDetails outputTokensDetails;
}