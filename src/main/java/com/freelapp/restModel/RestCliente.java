package com.freelapp.restModel;

public class RestCliente {
	
	private Integer id;
	
	private String labelCliente;
	
	private String logoCliente;
	
	
	public RestCliente(Integer id, String labelCliente, String logoCliente) {
		
		super();
		this.id = id;
		this.labelCliente = labelCliente;
		this.logoCliente = logoCliente;
		
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getLabelCliente() {
		return labelCliente;
	}


	public void setLabelCliente(String labelCliente) {
		this.labelCliente = labelCliente;
	}


	public String getLogoCliente() {
		return logoCliente;
	}


	public void setLogoCliente(String logoCliente) {
		this.logoCliente = logoCliente;
	}

	
	
}
