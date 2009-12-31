package org.unicen.edu.ar.knime.acp.node;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.geom.Rectangle2D;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.knime.core.node.NodeView;
import org.unicen.edu.ar.knime.acp.kernel.ComponentePrincipalComponent;

/**
 * <code>NodeView</code> for the "ACPNode" Node.
 * Este nodo toma como entrada un conjunto de variables numericas y realiza el algoritmo de componentes principales. para reducir la dimension del problema a analizar
 *
 * @author Rodrigo
 */
public class ACPNodeNodeView extends NodeView<ACPNodeNodeModel> {

	private JPanel m_panel;
	
    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link ACPNodeNodeModel})
     */
    protected ACPNodeNodeView(final ACPNodeNodeModel nodeModel) {
        super(nodeModel);

        // TODO instantiate the components of the view here.
      HashMap<Integer,Vector<ComponentePrincipalComponent>> componentes=nodeModel.getComponentes();
     m_panel=(JPanel)getPanel(componentes);
     this.setViewTitleSuffix("vista ACP");
      setComponent(m_panel);
    }

    private Component getPanel(
			HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes) {
		JPanel panel=new JPanel();
	//	panel.setSize(500, 400);
		panel.setBounds(0, 0, 500, 500);
		
		panel.setLayout(null);
		panel.setName("ACP view");
		int x=0;
		int y=0;
		for (Iterator iterator = componentes.keySet().iterator(); iterator.hasNext();) {
			Integer type = (Integer) iterator.next();
			Vector<ComponentePrincipalComponent> factores=componentes.get(type);
			 if(y+factores.size()*20> 431)
			    {
			        x+=300;	
			        y=0;
			    }
			JLabel componente=new JLabel();
			componente.setBounds(x+220,y+15*factores.size(),40,40);
			componente.setBackground(Color.CYAN);			
			componente.setText("CP"+type.intValue());
			componente.setOpaque(true);
			componente.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		    
			panel.add(componente);
			
			for (Iterator iterator2 = factores.iterator(); iterator2
			.hasNext();) {
		ComponentePrincipalComponent componentePrincipalComponent = (ComponentePrincipalComponent) iterator2
				.next();
						
		JLabel variable=new JLabel();
		variable.setBounds(x, y, 60, 20);

		variable.setBackground(Color.BLUE);
		variable.setOpaque(true);
		variable.setText(componentePrincipalComponent.getNombreVar());
		variable.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(variable);

		
		
	    y+=45;
	}
 y+=20;
			
			
		}
		
		return panel;
	}

	/**
     * {@inheritDoc}
     */
    @Override
    protected void modelChanged() {

        // TODO retrieve the new model from your nodemodel and 
        // update the view.
        ACPNodeNodeModel nodeModel = 
            (ACPNodeNodeModel)getNodeModel();
        assert nodeModel != null;
        
        // be aware of a possibly not executed nodeModel! The data you retrieve
        // from your nodemodel could be null, emtpy, or invalid in any kind.
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onClose() {
    
        // TODO things to do when closing the view
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void onOpen() {

        // TODO things to do when opening the view
    }

}

