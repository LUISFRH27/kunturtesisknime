package org.unicen.edu.ar.knime.acp.node;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SpringLayout;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridLayout;
import org.jgraph.JGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.DefaultGraphModel;
import org.jgraph.graph.GraphConstants;
import org.jgraph.graph.GraphModel;
import org.knime.core.node.NodeView;
import org.unicen.edu.ar.knime.acp.kernel.ComponentePrincipalComponent;

import sun.java2d.loops.DrawLine;

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
     getPanel(componentes);
     
     this.setViewTitleSuffix("vista ACP");
      setComponent(m_panel);
    }

    private void getPanel(
			HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes) {
		
		GraphModel model = new DefaultGraphModel();
		JGraph graph = new JGraph(model);
		// Control-drag should clone selection
		graph.setCloneable(true);

		// Enable edit without final RETURN keystroke
		graph.setInvokesStopCellEditing(true);

		// When over a cell, jump to its default port (we only have one, anyway)
		graph.setJumpToDefaultPort(true);
		
		
		int nroVar=0;
		int nroComp=0;
		
		int x=0;
		int y=0;
		DefaultGraphCell[] cells=new DefaultGraphCell[3];
		for (Iterator iterator = componentes.keySet().iterator(); iterator.hasNext();) {
			Integer type = (Integer) iterator.next();
			Vector<ComponentePrincipalComponent> factores=componentes.get(type);
			 if(y+factores.size()*20> 431)
			    {
			        x+=300;	
			        y=0;
			    }
			
		//agregar un CP en x+220 e y+15*factores.size()
			 cells[0]=createVertex("CP"+type.intValue(), x+220, y+15D*factores.size(), 40, 40, Color.CYAN, false);
			
			
			for (Iterator iterator2 = factores.iterator(); iterator2
			.hasNext();) {
		ComponentePrincipalComponent componentePrincipalComponent = (ComponentePrincipalComponent) iterator2
				.next();
						
		
	    //agregar una variable
        cells[1]=createVertex(componentePrincipalComponent.getNombreVar(), x, y, 60, 40, Color.BLUE, false);
		
		//agregar arco
		DefaultEdge edge = new DefaultEdge();
		// Fetch the ports from the new vertices, and connect them with the edge
		edge.setSource(cells[0].getChildAt(0));
		edge.setTarget(cells[1].getChildAt(0));
		edge.getAttributes().applyMap(createEdgeAttributes());
		edge.setUserObject(Redondear(componentePrincipalComponent.getPeso(), 2));
		cells[2] = edge;
//
//		// Set Arrow Style for edge
		int arrow = GraphConstants.ARROW_CLASSIC;
		GraphConstants.setLineEnd(edge.getAttributes(), arrow);
		GraphConstants.setEndFill(edge.getAttributes(), true);

		// Insert the cells via the cache, so they get selected
		graph.getGraphLayoutCache().insert(cells);
		
	    y+=45;
	}
 y+=20;
			
			
		}		

		// Show in Frame
		
		JFrame frame=this.createFrame("sumburule");
		frame.getContentPane().add(new JScrollPane(graph));		
		frame.pack();
		frame.setVisible(true);
		}

    public double Redondear(double nD, int nDec)
	{
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);
	}
    
	private Map createEdgeAttributes() {
		Map map = new Hashtable();

	
		
		// La etiqueta del archo se pone a lo largo del mismo
		GraphConstants.setLabelAlongEdge(map, true);
		// Le damos un mayor tamaño a lalinea
		GraphConstants.setLineWidth(map, new Double(2.0).floatValue());
		// Hacemos que el fondo del borde sea opaco
		GraphConstants.setOpaque(map, true);
		// No permitimos que se pueda editar
		GraphConstants.setEditable(map, false);
		// No permitimos que se pueda mover
		GraphConstants.setMoveable(map, false);
		// No permitimos desconectar la linea
		GraphConstants.setDisconnectable(map, false);
		// Seteamos el color del borde
		GraphConstants.setBorderColor(map, new Color(34, 200, 34));
		// Le damos color a la linea
		GraphConstants.setLineColor(map, new Color(34, 200, 34));
		//prueba de flecha

		
		
		return map;
	}
    
    
    public static DefaultGraphCell createVertex(String name, double x,
			double y, double w, double h, Color bg, boolean raised) {

		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(name);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(
				x, y, w, h));

		// Set fill color
		if (bg != null) {
			GraphConstants.setGradientColor(cell.getAttributes(), bg);
			GraphConstants.setOpaque(cell.getAttributes(), true);
		}

		// Set raised border
		if (raised)
			GraphConstants.setBorder(cell.getAttributes(), BorderFactory
					.createRaisedBevelBorder());
		else
			// Set black border
			GraphConstants.setBorderColor(cell.getAttributes(), Color.black);

		// Add a Floating Port
		cell.addPort();

		return cell;
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

