package com.sensedia.antifraude.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.domain.enums.StatusEnum;
import com.sensedia.antifraude.dto.topico.output.SolicitacaoPagamentoDetalheOutput;
import com.sensedia.antifraude.exception.FraudeDetectadaException;
import com.sensedia.antifraude.repository.impl.SolicitacaoPagamentoRepository;
import com.sensedia.antifraude.service.IAntiFraudeService;
import com.sensedia.antifraude.validator.IValidador;

@Service
public class AntiFraudeService implements IAntiFraudeService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AntiFraudeService.class);

	@Autowired
	private SolicitacaoPagamentoRepository solicitacaoPagamentoRepository;

	@Override
	public Long processar(final SolicitacaoPagamento solicitacaoPagamento) {
		
		LOGGER.info("[Solicitacao: {}] Mensagem: inicializando a validacao", solicitacaoPagamento.getId());

		List<SolicitacaoPagamentoDetalheOutput> validacoes = validar(solicitacaoPagamento);

		StatusEnum status = validacoes.stream()
									  .map(SolicitacaoPagamentoDetalheOutput::getStatus)
								  	  .filter(StatusEnum.PROCESSADO_FRAUDE::equals)
								  	  .findFirst().orElse(StatusEnum.PROCESSADO_SUCESSO);

		LOGGER.info("[Solicitacao: {}] Mensagem: salvando com status {}", solicitacaoPagamento.getId(), status);
		
		return solicitacaoPagamentoRepository.salvarSolicitacao(solicitacaoPagamento, status);
	}
	
	@Override
	public SolicitacaoPagamento buscarPorId(Long id) {
		return this.solicitacaoPagamentoRepository.buscarPorId(id);
	}

	private List<SolicitacaoPagamentoDetalheOutput> validar(final SolicitacaoPagamento solicitacaoPagamento) {
		
		LOGGER.info("[Solicitacao: {}] Mensagem: inicializando a validacao", solicitacaoPagamento.getId());

		List<IValidador> validacoes = Arrays.asList(this::validacaoDeValorNegativo, 
													this::validacaoDeValorZero,
													this::validacaoDeGeolocalizacao, 
													this::validacaoDeValidadeDoCartao,
													this::verificacaoDeSolicitacaoDePagamento,
													this::validacaoDePagamentoAcimaDaMedia);
		
		return validacoes.stream()
					     .map(valid -> valid.validate(solicitacaoPagamento))
					     .filter(Optional::isPresent)
					     .map(Optional::get)
					     .collect(Collectors.toList());
		
	}

	private Optional<SolicitacaoPagamentoDetalheOutput> verificacaoDeSolicitacaoDePagamento(SolicitacaoPagamento solicitacaoPagamento)
			throws FraudeDetectadaException {
		
		Optional<SolicitacaoPagamento> ultimoRegistoDePagamento = Optional.ofNullable(solicitacaoPagamentoRepository.retornarUltimoRegistroDaSolicitacaoPagamento(solicitacaoPagamento.getNumeroCartao()));
		
		if (ultimoRegistoDePagamento.isPresent()) {
			processar(solicitacaoPagamento);
		}
		
		return Optional.empty();
	}
	
	/*
	 * Calcula a distância entre dois pontos dado a latitude e longitude desses pontos e verifica se a distancia é maior
	 * que 100 KM, se sim, houve a detecção de de uma tentativa de pagamento através de uma localizade não permitida
	 * Referência base:http://www.geodatasource.com/developers/java Teste online da fórmula:
	 * https://www.nhc.noaa.gov/gccalc.shtml
	 */
	private Optional<SolicitacaoPagamentoDetalheOutput> validacaoDeGeolocalizacao(SolicitacaoPagamento solicitacaoPagamento) {
		
		// TODO implementar
		
	}
	
	private Optional<SolicitacaoPagamentoDetalheOutput> validacaoDeValorNegativo(SolicitacaoPagamento solicitacaoPagamento) {

		// TODO implementar
	
	}

	private Optional<SolicitacaoPagamentoDetalheOutput> validacaoDeValorZero(SolicitacaoPagamento solicitacaoPagamento) {
		if (solicitacaoPagamento.getValor() == 0) {
			LOGGER.info("Solicitação ID :{} Registro de Fraude - Tentativa de pagamento com valor zero.",solicitacaoPagamento.getId());
			return Optional.ofNullable(new SolicitacaoPagamentoDetalheOutput(StatusEnum.PROCESSADO_FRAUDE,"Tentativa de pagamento com valor zero."));
		}
		return Optional.empty();
	}

	private Optional<SolicitacaoPagamentoDetalheOutput> validacaoDeValidadeDoCartao(SolicitacaoPagamento solicitacaoPagamento) {

		// TODO implementar
		
	}

	
	private Optional<SolicitacaoPagamentoDetalheOutput> validacaoDePagamentoAcimaDaMedia(SolicitacaoPagamento solicitacaoPagamento) {
		
		Double mediaPagamento = solicitacaoPagamentoRepository.retornarValorMediaPagamento(solicitacaoPagamento.getNumeroCartao());
		if(solicitacaoPagamento.getValor() > mediaPagamento) {
			LOGGER.info(
					"Registro de Fraude para a solicitação ID : {} - Tentativa de pagamento com valor acima da média."+solicitacaoPagamento.getId());
			return Optional.ofNullable(new SolicitacaoPagamentoDetalheOutput(StatusEnum.PROCESSADO_FRAUDE,"Tentativa de pagamento com valor acima da média."));
		}
		
		return Optional.empty();
	}

	private static double grausParaRadiano(double grau) {
		return (grau * Math.PI / 180.0);
	}

	private static double radianoParaGraus(double radiano) {
		return (radiano * 180 / Math.PI);
	}

}
