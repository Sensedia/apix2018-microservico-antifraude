package com.sensedia.antifraude.dto.request;

import java.io.Serializable;

public class CartaoRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String nome;
	private final String numero;
	private final String validade;
	private final String cvv;

	public CartaoRequest(String nome, String numero, String validade, String cvv) {
		this.nome = nome;
		this.numero = numero;
		this.validade = validade;
		this.cvv = cvv;
	}

	public String getNome() {
		return nome;
	}

	public String getNumero() {
		return numero;
	}

	public String getValidade() {
		return validade;
	}

	public String getCvv() {
		return cvv;
	}
}

