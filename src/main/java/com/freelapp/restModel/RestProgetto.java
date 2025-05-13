package com.freelapp.restModel;

public class RestProgetto {
	
	private String nome;
	
	private Integer progettoId;
	
	private String nomeCliente;
	
	private Integer clienteId;
	
	private Integer countTaskProgetto;
	
	private String tipologia;

	public RestProgetto(String nome, Integer progettoId, String nomeCliente, Integer clienteId, Integer taskProgetto,
			String tipologia) {
		super();
		this.nome = nome;
		this.progettoId = progettoId;
		this.nomeCliente = nomeCliente;
		this.clienteId = clienteId;
		this.countTaskProgetto = taskProgetto;
		this.tipologia = tipologia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getProgettoId() {
		return progettoId;
	}

	public void setProgettoId(Integer progettoId) {
		this.progettoId = progettoId;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}

	

	public Integer getCountTaskProgetto() {
		return countTaskProgetto;
	}

	public void setCountTaskProgetto(Integer countTaskProgetto) {
		this.countTaskProgetto = countTaskProgetto;
	}

	public String getTipologia() {
		return tipologia;
	}

	public void setTipologia(String tipologia) {
		this.tipologia = tipologia;
	}
	
	
	

}
