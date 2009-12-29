package org.unicen.edu.ar.knime.acp.node;

import org.knime.core.node.NodeView;

/**
 * <code>NodeView</code> for the "ACPNode" Node.
 * Este nodo toma como entrada un conjunto de variables numericas y realiza el algoritmo de componentes principales. para reducir la dimension del problema a analizar
 *
 * @author Rodrigo
 */
public class ACPNodeNodeView extends NodeView<ACPNodeNodeModel> {

    /**
     * Creates a new view.
     * 
     * @param nodeModel The model (class: {@link ACPNodeNodeModel})
     */
    protected ACPNodeNodeView(final ACPNodeNodeModel nodeModel) {
        super(nodeModel);

        // TODO instantiate the components of the view here.

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

