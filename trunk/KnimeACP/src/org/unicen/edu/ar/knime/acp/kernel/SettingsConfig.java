package org.unicen.edu.ar.knime.acp.kernel;

import java.util.HashMap;
import java.util.Vector;

public class SettingsConfig {

	HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes;
	Double[][] resultMatrix;
	String[] variableNames;
	int factorsNumber;
	public HashMap<Integer, Vector<ComponentePrincipalComponent>> getComponentes() {
		return componentes;
	}
	public void setComponentes(
			HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes) {
		this.componentes = componentes;
	}
	public Double[][] getResultMatrix() {
		return resultMatrix;
	}
	public void setResultMatrix(Double[][] resultMatrix) {
		this.resultMatrix = resultMatrix;
	}
	public String[] getVariableNames() {
		return variableNames;
	}
	public void setVariableNames(String[] variableNames) {
		this.variableNames = variableNames;
	}
	public int getFactorsNumber() {
		return factorsNumber;
	}
	public void setFactorsNumber(int factorsNumber) {
		this.factorsNumber = factorsNumber;
	}
	
	
}
