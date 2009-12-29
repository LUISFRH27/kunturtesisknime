package org.unicen.edu.ar.knime.acp.kernel;

import javax.swing.table.DefaultTableModel;

public class MyDataModel extends DefaultTableModel{

	public MyDataModel(Object[][] data, Object[] columnNames)
	{
		super(data,columnNames);
	}
	
	public boolean isCellEditable (int row, int column)
	{
		return false;
	}
//	private Object [][] data;
//	private int cantidadColumnas;
//	private int cantidadFilas;
//	
//	public MyDataModel(int x,int y) {
//		this.cantidadFilas = x;
//		this.cantidadColumnas = y;
//		this.data = new Object[x][y];
//	}
//
//	@Override
//	public int getColumnCount() {
//		return this.cantidadColumnas; 
//	}
//
//	@Override
//	public int getRowCount() {
//		return this.cantidadFilas;
//	}
//
//	@Override
//	public Object getValueAt(int rowIndex, int columnIndex) {
//		if (rowIndex >= 0 && columnIndex >=0 && rowIndex<= this.cantidadFilas && columnIndex<= this.cantidadFilas)
//			return data[rowIndex][columnIndex];
//		return null;
//	}
//	
//	@Override
//	public void setValueAt(Object aValue, int row, int column) {
//		data[row][column] = aValue;
//	}

}
