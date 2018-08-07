package com.sensedia.antifraude.domain;

import com.google.cloud.Timestamp;
import com.google.cloud.datastore.LatLng;
import com.sensedia.antifraude.domain.enums.StatusEnum;

public class SolicitacaoPagamento {

	private final Long id;
	private final Double valor;
	private final StatusEnum status;
	private final Timestamp dataCriacao;
	private final LatLng geolocalizacao;
	// info cartao
	private final String numeroCartao;
	private final String nomeCartao;
	private final String validadeCartao;
	private final String cvvCartao;
	// info boleto
	private final String codigoPagamento;
	// info user
	private final String usuarioCPF;
	private final String usuarioNome;
	private final String usuarioTelefone;
	private final String usuarioEmail;

	public static final String ID = "id";
	public static final String VALOR = "valor";
	public static final String STATUS = "status";
	public static final String DATA_CRIACAO = "dataCriacao";
	public static final String GEOLOCALIZACAO = "geolocalizacao";
	public static final String CARTAO_NUMERO = "numeroCartao";
	public static final String CARTAO_NOME = "nomeCartao";
	public static final String CARTAO_VALIDADE = "validadeCartao";
	public static final String CARTAO_CVV = "cvvCartao";
	public static final String CODIGO_PAGAMENTO = "codigoPagamento";
	public static final String USUARIO_CPF = "cpf";
	public static final String USUARIO_NOME = "nome";
	public static final String USUARIO_TELEFONE = "telefone";
	public static final String USUARIO_EMAIL = "email";

	private SolicitacaoPagamento(Builder builder) {
		this.id = builder.id;
		this.valor = builder.valor;
		this.status = builder.status;
		this.dataCriacao = builder.dataCriacao;
		this.geolocalizacao = builder.geolocalizacao;
		this.numeroCartao = builder.numeroCartao;
		this.nomeCartao = builder.nomeCartao;
		this.validadeCartao = builder.validadeCartao;
		this.cvvCartao = builder.cvvCartao;
		this.codigoPagamento = builder.codigoPagamento;
		this.usuarioCPF = builder.usuarioCPF;
		this.usuarioNome = builder.usuarioNome;
		this.usuarioTelefone = builder.usuarioTelefone;
		this.usuarioEmail = builder.usuarioEmail;
	}

	public static class Builder {

		private Long id;
		private Double valor;
		private StatusEnum status;
		private Timestamp dataCriacao;
		private LatLng geolocalizacao;
		// info cartao
		private String numeroCartao;
		private String nomeCartao;
		private String validadeCartao;
		private String cvvCartao;
		// info boleto
		private String codigoPagamento;
		// info user
		private String usuarioCPF;
		private String usuarioNome;
		private String usuarioTelefone;
		private String usuarioEmail;

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder valor(Double valor) {
			this.valor = valor;
			return this;
		}

		public Builder status(StatusEnum status) {
			this.status = status;
			return this;
		}

		public Builder dataCriacao(Timestamp dataCriacao) {
			this.dataCriacao = dataCriacao;
			return this;
		}

		public Builder geolocalizacao(LatLng geolocalizacao) {
			this.geolocalizacao = geolocalizacao;
			return this;
		}

		public Builder numeroCartao(String numeroCartao) {
			this.numeroCartao = numeroCartao;
			return this;
		}

		public Builder nomeCartao(String nomeCartao) {
			this.nomeCartao = nomeCartao;
			return this;
		}

		public Builder validadeCartao(String validadeCartao) {
			this.validadeCartao = validadeCartao;
			return this;
		}

		public Builder cvvCartao(String cvvCartao) {
			this.cvvCartao = cvvCartao;
			return this;
		}

		public Builder codigoPagamento(String codigoPagamento) {
			this.codigoPagamento = codigoPagamento;
			return this;
		}

		public Builder usuarioCPF(String usuarioCPF) {
			this.usuarioCPF = usuarioCPF;
			return this;
		}

		public Builder usuarioNome(String usuarioNome) {
			this.usuarioNome = usuarioNome;
			return this;
		}

		public Builder usuarioTelefone(String usuarioTelefone) {
			this.usuarioTelefone = usuarioTelefone;
			return this;
		}

		public Builder usuarioEmail(String usuarioEmail) {
			this.usuarioTelefone = usuarioEmail;
			return this;
		}

		public SolicitacaoPagamento build() {
			return new SolicitacaoPagamento(this);
		}

	}

	public StatusEnum getStatus() {
		return status;
	}

	public Timestamp getDataCriacao() {
		return dataCriacao;
	}

	public Long getId() {
		return id;
	}

	public Double getValor() {
		return valor;
	}

	public LatLng getGeolocalizacao() {
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
	
	@Override
	public String toString() {
		return "SolicitacaoPagamento [id=" + id + ", valor=" + valor + ", status=" + status + ", dataCriacao="
				+ dataCriacao + ", geolocalizacao=" + geolocalizacao + ", numeroCartao=" + numeroCartao
				+ ", nomeCartao=" + nomeCartao + ", validadeCartao=" + validadeCartao + ", cvvCartao=" + cvvCartao
				+ ", codigoPagamento=" + codigoPagamento + ", usuarioCPF=" + usuarioCPF + ", usuarioNome=" + usuarioNome
				+ ", usuarioTelefone=" + usuarioTelefone + ",usuarioEmail=" + usuarioEmail + "]";
	}

}
