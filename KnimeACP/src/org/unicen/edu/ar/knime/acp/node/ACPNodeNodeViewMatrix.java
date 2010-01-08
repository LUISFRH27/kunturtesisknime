package org.unicen.edu.ar.knime.acp.node;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.knime.core.node.NodeView;

public class ACPNodeNodeViewMatrix extends NodeView<ACPNodeNodeModel> {

	protected ACPNodeNodeViewMatrix(ACPNodeNodeModel nodeModel) {
		super(nodeModel);
		// TODO Auto-generated constructor stub
		Double [][] matrix=nodeModel.getMatrix();
		//String[][] data=convertData(nodeModel.getMatrix(),nodeModel.getNumeroDeComponentes());
		String[] columsNames=getNombresComponentes(nodeModel.getNumeroDeComponentes());
		DefaultTableModel dtm=new DefaultTableModel(matrix,columsNames);
		dtm.addColumn("Variables", nodeModel.nombresVariables);
		normalizarDatos(dtm);
		JTable table=new JTable(dtm);
		
		table.setFillsViewportHeight(true);
		table.setCellSelectionEnabled(false);
		table.setColumnSelectionAllowed(false);
		table.setRowSelectionAllowed(false);
		
		JScrollPane scrollPane = new JScrollPane(table);
		
		
		setComponent(scrollPane);
	}

	private void normalizarDatos(DefaultTableModel dtm) {
		// TODO Auto-generated method stub
		for(int i=0;i<dtm.getRowCount();i++)
			for(int j=0;j<dtm.getColumnCount();j++)
				if((dtm.getValueAt(i, j).toString()).equals("0.0"))
					dtm.setValueAt("", i, j);
	}

	private String[][] convertData(Double[][] matrix,int numeroColumnas) {
		// TODO Auto-generated method stub
		
		return null;
	}

	private String[] getNombresComponentes(int numeroDeComponentes) {
		// TODO Auto-generated method stub
		String[] nombres=new String[numeroDeComponentes];
		
		for(int i=0;i<numeroDeComponentes;i++)
		{
			nombres[i]="CP "+i;
		}
		return nombres;
	}

	@Override
	protected void onClose() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void modelChanged() {
		// TODO Auto-generated method stub

	}

}
