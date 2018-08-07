package com.sensedia.antifraude.dto.topico.output;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class SolicitacaoPagamentoOutput implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final Collection<SolicitacaoPagamentoDetalheOutput> detalhes;

	public SolicitacaoPagamentoOutput(Long id, Collection<SolicitacaoPagamentoDetalheOutput> retornos) {
		this.id = id;
		this.detalhes = retornos;
	}

	public Long getId() {
		return id;
	}

	public Collection<SolicitacaoPagamentoDetalheOutput> getDetalhes() {
		return Collections.unmodifiableCollection(detalhes);
	}

}
