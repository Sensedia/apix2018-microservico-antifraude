package com.sensedia.antifraude.exception;

import com.sensedia.antifraude.domain.enums.StatusEnum;

public class FraudeDetectadaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final StatusEnum status;

	public FraudeDetectadaException(StatusEnum status, String mensagem) {
		super(mensagem);
		this.status = status;
	}

	public StatusEnum getStatus() {
		return status;
	}

}
