package org.unicen.edu.ar.knime.acp.node;



import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.knime.core.node.defaultnodesettings.DefaultNodeSettingsPane;
import org.knime.core.node.defaultnodesettings.DialogComponentBoolean;
import org.knime.core.node.defaultnodesettings.DialogComponentNumber;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
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

	
	private final SettingsModelBoolean isAutoval=new SettingsModelBoolean(ACPNodeNodeModel.CFGKEY_IS_AUTOVAL,ACPNodeNodeModel.DEFAULT_IS_AUTOVAL);
	private final SettingsModelDouble minAutoval=new SettingsModelDouble(ACPNodeNodeModel.CFGKEY_MIN_AUTOVAL,ACPNodeNodeModel.DEFAULT_MIN_AUTOVAL);
	private final SettingsModelBoolean isCantComp=new SettingsModelBoolean(ACPNodeNodeModel.CFGKEY_Is_CantComp,ACPNodeNodeModel.DEFAULT_IS_CANT_COMP); 
	private final SettingsModelIntegerBounded cantComp=new SettingsModelIntegerBounded(ACPNodeNodeModel.CFGKEY_CANT_COMP,ACPNodeNodeModel.DEFAULT_CANT_COMP,0,Integer.MAX_VALUE); 
	private final SettingsModelBoolean isVarimax=new SettingsModelBoolean(ACPNodeNodeModel.CFGKEY_IS_VARIMAX,ACPNodeNodeModel.DEFAULT_IS_VARIMAX);
	private final SettingsModelIntegerBounded cantIteraciones=new SettingsModelIntegerBounded(ACPNodeNodeModel.CFGKEY_CANT_ITERACIONES,ACPNodeNodeModel.DEFAULT_CAN_ITERACIONES,0,Integer.MAX_VALUE);
	private final SettingsModelDouble minLoadingFactor=new SettingsModelDouble(ACPNodeNodeModel.CFGKEY_MIN_LOADING_FACTOR,ACPNodeNodeModel.DEFAULT_MIN_LOADING_FACTOR);
    /**
     * New pane for configuring ACPNode node dialog.
     * This is just a suggestion to demonstrate possible default dialog
     * components.
     */
    protected ACPNodeNodeDialog() {
        super();               
        
         
        createNewGroup("Extracción");
        addDialogComponent(new DialogComponentBoolean(isAutoval,"Autovalores mayores a:"));
        addDialogComponent(new DialogComponentNumber(minAutoval,
                "Ingrese el minimo valor de autovalor:", 0.1D));
        addDialogComponent(new DialogComponentBoolean(isCantComp,"Cantidad de Componentes"));
        addDialogComponent(new DialogComponentNumber(cantComp,"Numero de componentes:",1));
        createNewGroup("Rotación");
        addDialogComponent(new DialogComponentBoolean(isVarimax,"Rotación varimax"));
        addDialogComponent(new DialogComponentNumber(cantIteraciones,"Numero de iteraciones: ",1));
        createNewGroup("Opciones visuales");
        addDialogComponent(new DialogComponentNumber(minLoadingFactor,"Eliminar valores absolutos menores a:",0.1D));
        crearListeners(); 
        cantComp.setEnabled(false);
        cantIteraciones.setEnabled(false);
    }
    
   
   

	
    private void crearListeners(){
    	isAutoval.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				if(isAutoval.getBooleanValue()){
					isCantComp.setBooleanValue(false);
					cantComp.setEnabled(false);
					minAutoval.setEnabled(true);	
				}
				
				
			}
    		
    	});
    	
    	isCantComp.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if(isCantComp.getBooleanValue())
				{
				    isAutoval.setBooleanValue(false);
				    cantComp.setEnabled(true);
				    minAutoval.setEnabled(false);
				}
			}
    		
    	});
    	
    	isVarimax.addChangeListener(new ChangeListener(){

			@Override
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				
					cantIteraciones.setEnabled(!cantIteraciones.isEnabled());
				
			}
    		
    	});
    }	

}

