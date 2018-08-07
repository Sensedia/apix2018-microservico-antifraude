package com.sensedia.antifraude.dto.topico.input;

import java.io.Serializable;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.LatLng;
import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.dto.GeolocalizacaoDTO;

public class SolicitacaoPagamentoInput implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Long id;
	private final Double valor;
	private final Timestamp dataCriacao;
	private final GeolocalizacaoDTO geolocalizacao;
	private final String numeroCartao;
	private final String nomeCartao;
	private final String validadeCartao;
	private final String cvvCartao;
	private final String codigoPagamento;
	private final String usuarioCPF;
	private final String usuarioNome;
	private final String usuarioTelefone;
	private final String usuarioEmail;

	public SolicitacaoPagamentoInput(SolicitacaoPagamento solicitacaoPagamento) {
		this.id = solicitacaoPagamento.getId();
		this.valor = solicitacaoPagamento.getValor();
		this.dataCriacao = solicitacaoPagamento.getDataCriacao();
		this.geolocalizacao = new GeolocalizacaoDTO(solicitacaoPagamento.getGeolocalizacao().getLatitude(), solicitacaoPagamento.getGeolocalizacao().getLongitude());
		this.numeroCartao = solicitacaoPagamento.getNumeroCartao();
		this.nomeCartao = solicitacaoPagamento.getNomeCartao();
		this.validadeCartao = solicitacaoPagamento.getValidadeCartao();
		this.cvvCartao = solicitacaoPagamento.getCvvCartao();
		this.codigoPagamento = solicitacaoPagamento.getCodigoPagamento();
		this.usuarioCPF = solicitacaoPagamento.getUsuarioCPF();
		this.usuarioNome = solicitacaoPagamento.getUsuarioNome();
		this.usuarioTelefone = solicitacaoPagamento.getUsuarioTelefone();
		this.usuarioEmail = solicitacaoPagamento.getUsuarioEmail();
	}

	public Long getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public GeolocalizacaoDTO getGeolocalizacao() {
		return geolocalizacao;
	}

	public String getNumeroCartao() {
		return numeroCartao;
	}

	public String getNomeCartao() {
		return nomeCartao;
	}

	public String getValidadeCartao() {
		return validadeCartao;
	}

	public String getCvvCartao() {
		return cvvCartao;
	}

	public String getCodigoPagamento() {
		return codigoPagamento;
	}

	public String getUsuarioCPF() {
		return usuarioCPF;
	}

	public String getUsuarioNome() {
		return usuarioNome;
	}

	public String getUsuarioTelefone() {
		return usuarioTelefone;
	}
	
	public String getUsuarioEmail() {
		return usuarioEmail;
	}
	
	public LatLng toLatLng(GeolocalizacaoDTO geo) {
		if (geolocalizacao != null && geo.getLatitude() != null && geo.getLongitude() != null) {
			return LatLng.of(geo.getLatitude(), geo.getLongitude()); 
		}
		return null;
	}
	
	public SolicitacaoPagamento toDomain() {
		return SolicitacaoPagamento.Builder.newBuilder()
											.id(this.id)
											.valor(this.valor)
											.dataCriacao(this.dataCriacao)
											.geolocalizacao(toLatLng(this.geolocalizacao))
											.numeroCartao(this.numeroCartao)
											.nomeCartao(this.nomeCartao)
											.validadeCartao(this.validadeCartao)
											.cvvCartao(this.cvvCartao)
											.codigoPagamento(this.codigoPagamento)
											.usuarioCPF(this.usuarioCPF)
											.usuarioNome(this.usuarioNome)
											.usuarioTelefone(this.usuarioTelefone)
											.usuarioEmail(this.usuarioEmail)
											.build();
	}

}
