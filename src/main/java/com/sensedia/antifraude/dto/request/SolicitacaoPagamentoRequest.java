package com.sensedia.antifraude.dto.request;

import java.io.Serializable;

import com.google.cloud.datastore.LatLng;
import com.sensedia.antifraude.domain.SolicitacaoPagamento;
import com.sensedia.antifraude.dto.GeolocalizacaoDTO;

public class SolicitacaoPagamentoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private final Double valor;
	private final String codigoPagamento;
	private final CartaoRequest cartao;
	private final UsuarioRequest usuario;
	private final GeolocalizacaoDTO geolocalizacao;

	public SolicitacaoPagamentoRequest(Double valor, String codigoPagamento, CartaoRequest cartao, UsuarioRequest usuario,
			GeolocalizacaoDTO geolocalizacao) {
		this.valor = valor;
		this.codigoPagamento = codigoPagamento;
		this.cartao = cartao;
		this.usuario = usuario;
		this.geolocalizacao = geolocalizacao;
	}

	public Double getValor() {
		return valor;
	}

	public String getCodigoPagamento() {
		return codigoPagamento;
	}

	public CartaoRequest getCartao() {
		return cartao;
	}

	public UsuarioRequest getUsuario() {
		return usuario;
	}

	public GeolocalizacaoDTO getGeolocalizacao() {
		return geolocalizacao;
	}

	public SolicitacaoPagamento toDomain() {
		return new SolicitacaoPagamento.Builder()
				.valor(this.valor)
				.codigoPagamento(this.codigoPagamento)
				.nomeCartao(this.getCartao().getNome())
				.numeroCartao(this.getCartao().getNumero())
				.validadeCartao(this.getCartao().getValidade())
				.cvvCartao(this.getCartao().getCvv())
				.usuarioCPF(this.getUsuario().getCpf())
				.usuarioNome(this.getUsuario().getNome())
				.usuarioTelefone(this.getUsuario().getTelefone())
				.usuarioEmail(this.getUsuario().getEmail())
				.geolocalizacao(LatLng.of(this.getGeolocalizacao().getLatitude(), this.getGeolocalizacao().getLongitude()))
				.build();
	}
}