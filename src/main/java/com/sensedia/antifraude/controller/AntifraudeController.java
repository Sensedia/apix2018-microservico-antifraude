package com.sensedia.antifraude.controller;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.service.IAntiFraudeService;

@RestController
@RequestMapping("/pagamentos")
public class AntifraudeController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AntifraudeController.class);
	
	@Autowired
	private IAntiFraudeService antifraudeService;
	
	@GetMapping
	ResponseEntity<SolicitacaoPagamento> buscarPorId(@PathVariable Long id){
		Optional<SolicitacaoPagamento> solicitacao = Optional.ofNullable(this.antifraudeService.buscarPorId(id));
		if(solicitacao.isPresent()) {
			ResponseEntity.ok(solicitacao);
		}
		LOGGER.info("[Solicitação: {}] - Não foi encontrada na base de dados",id);
		return ResponseEntity.noContent().build();
	}

}
