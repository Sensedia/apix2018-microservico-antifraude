package com.sensedia.antifraude.dto.response;

import java.io.Serializable;

public class CartaoResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String numero;

	public CartaoResponse(String numero) {
		this.numero = numero;
	}

	public String getNumero() {
		return numero;
	}

}