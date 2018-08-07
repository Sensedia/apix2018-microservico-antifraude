package com.sensedia.antifraude.dto.topico.output;

import java.io.Serializable;

import com.sensedia.antifraude.domain.enums.StatusEnum;

public class SolicitacaoPagamentoDetalheOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	private final StatusEnum status;
	private final String mensagem;

	public SolicitacaoPagamentoDetalheOutput(StatusEnum status, String mensagem) {
		this.status = status;
		this.mensagem = mensagem;
	}

	public String getMensagem() {
		return mensagem;
	}

	public StatusEnum getStatus() {
		return status;
	}

}