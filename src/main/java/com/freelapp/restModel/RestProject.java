package com.freelapp.restModel;

public class RestProject {
	
	private Integer id;
	
	private String name;
	
	private Integer idCliente;
	
	private String nomeCliente;
	
	public RestProject(Integer id, String name, Integer idCliente, String nomeCliente) {
		super();
		this.id = id;
		this.name = name;
		this.idCliente = idCliente;
		this.nomeCliente = nomeCliente;
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	
	
}
