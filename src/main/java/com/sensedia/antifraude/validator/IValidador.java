package com.sensedia.antifraude.validator;

import java.util.Optional;

import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.dto.topico.output.SolicitacaoPagamentoDetalheOutput;

public interface IValidador {

	Optional<SolicitacaoPagamentoDetalheOutput> validate(final SolicitacaoPagamento solicitacaoPagamento);

}
