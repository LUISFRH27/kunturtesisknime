package org.unicen.edu.ar.knime.acp.kernel;

public class ComponentePrincipalComponent {

	String nombreVariable;
	Double peso;
	
	public ComponentePrincipalComponent(String nombreVar,Double peso){
		nombreVariable=nombreVar;
		this.peso=peso;
	}
	
	public String getNombreVar(){return nombreVariable;}
	public Double getPeso(){return peso;}
	public void setNombreVar(String value){nombreVariable=value;}
	public void setPeso(Double value){peso=value;}
}
