package com.sensedia.antifraude.service;

import com.sensedia.antifraude.domain.SolicitacaoPagamento;

public interface IAntiFraudeService {

	Long processar(final SolicitacaoPagamento solicitacaoPagamento);
	
	SolicitacaoPagamento buscarPorId(Long id);
}
