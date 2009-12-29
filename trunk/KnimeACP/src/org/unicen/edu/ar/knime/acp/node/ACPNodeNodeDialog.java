package org.unicen.edu.ar.knime.acp.node;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;

/**
 * <code>NodeDialog</code> for the "ACPNode" Node.
 * Este nodo toma como entrada un conjunto de variables numericas y realiza el algoritmo de componentes principales. para reducir la dimension del problema a analizar
 *
 * This node dialog derives from {@link DefaultNodeSettingsPane} which allows
 * creation of a simple dialog with standard components. If you need a more 
 * complex dialog please derive directly from 
 * {@link org.knime.core.node.NodeDialogPane}.
 * 
 * @author Rodrigo
 */
public class ACPNodeNodeDialog extends DefaultNodeSettingsPane {

	
	private JPanel jPanelRotacion;
	private JLabel jLblRotacion;
	private JLabel jLblVarimax;
	private JRadioButton jRButtonVarimax;
	private JRadioButton jRButtonAutovalor;
	private JLabel jLblNroIteraciones;
	private JTextField jTxtNroIteraciones;
	private JTextField jTxtMinAutoval;
	private JTextField jTxtCantidadComponentes;
	private JRadioButton jRButtonCantComp;
	private JPanel jPanel;
	private JLabel jLblError;
	private JPanel jExtraccion;
	private JLabel jLblExtraccion;
	private JPanel jPanelOpcionesVisual;
	private JLabel jLblVisualizaon;
	private JLabel jLblVAbsMenores;
	private JTextField jTxtMinLoadingFactors;
	
    /**
     * New pane for configuring ACPNode node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected ACPNodeNodeDialog() {
        super();
        
        JPanel panel=new JPanel();
        panel.setLayout(new GridBagLayout());
        JLabel label=new JLabel("Cantidad de componentes");
        JTextField texto=new JTextField();
        
        
        panel.add(label);
        panel.add(texto);
     //   addDialogComponent(panel);
        addDialogComponent(new DialogComponentNumber(
                new SettingsModelIntegerBounded(
                    ACPNodeNodeModel.CFGKEY_COUNT,
                    ACPNodeNodeModel.DEFAULT_COUNT,
                    Integer.MIN_VALUE, Integer.MAX_VALUE),
                    "Counter:", /*step*/ 1, /*componentwidth*/ 5));
        addTab("ACP", getJPanel());
        crearListener();            
    }
    
    private JPanel getJPanelRotacion() {
		if (jPanelRotacion == null) {
			jLblRotacion = new JLabel();
			jLblRotacion.setBounds(new Rectangle(10, 10, 300, 20));
			jLblRotacion.setText("Rotación");
			
			jLblVarimax = new JLabel();
			jLblVarimax.setBounds(new Rectangle(25, 40, 300, 20));
			jLblVarimax.setText("¿Aplicar varimax?");
			
			jRButtonVarimax = new JRadioButton();
			jRButtonVarimax.setBounds(new Rectangle(40, 70, 300, 20));
			jRButtonVarimax.setText("Si");
			
			jLblNroIteraciones = new JLabel();
			jLblNroIteraciones.setBounds(new Rectangle(25, 100, 300, 20));
			jLblNroIteraciones.setText("Cantidad máxima de iteraciones varimax:");
			jLblNroIteraciones.setEnabled(false);
			
			jTxtNroIteraciones = new JTextField();
			jTxtNroIteraciones.setBounds(new Rectangle(40, 130, 30, 20));
			jTxtNroIteraciones.setEnabled(false);
			
			jPanelRotacion = new JPanel();
			jPanelRotacion.setLayout(null);
			jPanelRotacion.setBounds(new Rectangle(0, 150, 300, 150));
			jPanelRotacion.add(jLblRotacion, null);
			jPanelRotacion.add(jLblVarimax, null);
			jPanelRotacion.add(jRButtonVarimax, null);
			jPanelRotacion.add(jLblNroIteraciones, null);
			jPanelRotacion.add(jTxtNroIteraciones, null);
		}
		return jPanelRotacion;
	}
    
    
	private void crearListener() {
		// TODO Auto-generated method stub
		jRButtonVarimax.addActionListener(new ActionListener(){			

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				jLblNroIteraciones.setEnabled(!jLblNroIteraciones.isEnabled());
				jTxtNroIteraciones.setEnabled(!jTxtNroIteraciones.isEnabled());
			}
			
		});
		
		jTxtNroIteraciones.addKeyListener(new KeyListener(){

			@Override
			public void keyPressed(KeyEvent arg0) {
				// TODO Auto-generated method stub

		     
				
			}
            @Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
				 String s=String.valueOf(arg0.getKeyChar());
				try {
					Integer i=Integer.parseInt(s);
				} catch (NumberFormatException e) {
					jTxtNroIteraciones.setText(jTxtNroIteraciones.getText().replaceAll(String.valueOf(arg0.getKeyChar()), ""));
					
				  
				} 
					
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	
		
		jRButtonAutovalor.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(jRButtonAutovalor.isSelected())
				{
					jTxtMinAutoval.setEnabled(true);
					jTxtCantidadComponentes.setEnabled(false);
					jRButtonCantComp.setSelected(false);
				}
				
				
			}
			
		});
		
		jRButtonCantComp.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				if(jRButtonCantComp.isSelected())
				{
					jTxtCantidadComponentes.setEnabled(true);
					jTxtMinAutoval.setEnabled(false);
					jRButtonAutovalor.setSelected(false);
				}
				
			}
			
		});
	}
	
	private JPanel getJPanel() {
		if (jPanel == null) {
			jLblError = new JLabel();
			jLblError.setBounds(new Rectangle(11, 401, 279, 16));
			jLblError.setText("");
			jLblError.setVisible(false);
			Font font=new Font("arial",Font.BOLD,12);
			jLblError.setForeground(Color.RED);
			jLblError.setFont(font);
			jPanel = new JPanel();
			jPanel.setLayout(null);
			jPanel.add(getJExtraccion(), null);
			jPanel.add(getJPanelRotacion(), null);
			jPanel.add(getJPanelOpcionesVisual(), null);			
			jPanel.add(jLblError, null);
		}
		return jPanel;
	}
	
	private JPanel getJExtraccion() {
		if (jExtraccion == null) {
			jLblExtraccion = new JLabel();
			jLblExtraccion.setBounds(new Rectangle(10, 10, 71, 20));
			jLblExtraccion.setText("Extracción");
			
			
			jTxtMinAutoval = new JTextField();
			jTxtMinAutoval.setBounds(new Rectangle(40, 70, 25, 20));
			
			
			jTxtCantidadComponentes = new JTextField();
			jTxtCantidadComponentes.setBounds(new Rectangle(40, 130, 25, 20));
		
			jExtraccion = new JPanel();
			jExtraccion.setLayout(null);
			jExtraccion.setBounds(new Rectangle(0, 0, 300, 150));
			jExtraccion.add(jTxtMinAutoval, null);
			jExtraccion.add(jLblExtraccion, null);
			jExtraccion.add(jTxtCantidadComponentes, null);
			jTxtCantidadComponentes.setEnabled(false);
			jExtraccion.add(getJRButtonAutovalor(), null);
			jExtraccion.add(getJRButtonCantComp(), null);
		}
		return jExtraccion;
	}
	
	private JRadioButton getJRButtonAutovalor() {
		if (jRButtonAutovalor == null) {
			jRButtonAutovalor = new JRadioButton();
			jRButtonAutovalor.setBounds(new Rectangle(35, 45, 195, 21));
			jRButtonAutovalor.setText("Autovalores mayores a:");
			jRButtonAutovalor.setSelected(true);
		}
		return jRButtonAutovalor;
	}

	/**
	 * This method initializes jRButtonCantComp	
	 * 	
	 * @return javax.swing.JRadioButton	
	 */
	private JRadioButton getJRButtonCantComp() {
		if (jRButtonCantComp == null) {
			jRButtonCantComp = new JRadioButton();
			jRButtonCantComp.setBounds(new Rectangle(35, 106, 256, 21));
			jRButtonCantComp.setText("Cantidad de componentes principales");
			jRButtonCantComp.setSelected(false);
		}
		return jRButtonCantComp;
	}
	
	private JPanel getJPanelOpcionesVisual() {
		if (jPanelOpcionesVisual == null) {
			jLblVisualizaon = new JLabel();
			jLblVisualizaon.setBounds(new Rectangle(10, 10, 300, 20));
			jLblVisualizaon.setText("Visualización");
			
			jLblVAbsMenores = new JLabel();
			jLblVAbsMenores.setBounds(new Rectangle(25, 40, 300, 20));
			jLblVAbsMenores.setText("Suprimir valores absolutos menores a:");
			
			jTxtMinLoadingFactors = new JTextField();
			jTxtMinLoadingFactors.setBounds(new Rectangle(40, 70, 30, 20));
			
			jPanelOpcionesVisual = new JPanel();
			jPanelOpcionesVisual.setLayout(null);
			jPanelOpcionesVisual.setBounds(new Rectangle(0, 300, 300, 90));
			jPanelOpcionesVisual.add(jLblVisualizaon, null);
			jPanelOpcionesVisual.add(jLblVAbsMenores, null);
			jPanelOpcionesVisual.add(jTxtMinLoadingFactors, null);
		}
		return jPanelOpcionesVisual;
	}

}

