package com.sensedia.antifraude.dto.request;

import java.io.Serializable;

public class UsuarioRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String cpf;
	private final String nome;
	private final String telefone;
	private final String email;

	public UsuarioRequest(String cpf, String nome, String telefone, String email) {
		this.cpf = cpf;
		this.nome = nome;
		this.telefone = telefone;
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public String getNome() {
		return nome;
	}

	public String getTelefone() {
		return telefone;
	}
	
	public String getEmail() {
		return email;
	}

}