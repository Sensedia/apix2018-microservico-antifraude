package com.sensedia.antifraude.repository;

import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.domain.enums.StatusEnum;

public interface ISolicitacaoPagamentoRepository {

	Long salvarSolicitacao(final SolicitacaoPagamento solicitacaoPagamento, final StatusEnum status);

	SolicitacaoPagamento retornarUltimoRegistroDaSolicitacaoPagamento(final String numeroCartao);

	Double retornarValorMediaPagamento(final String numeroCartao);

}
