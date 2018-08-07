package com.sensedia.antifraude.dto.response;

import java.io.Serializable;

public class UsuarioResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String nome;

	public UsuarioResponse(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}