package org.unicen.edu.ar.knime.acp.node;

import org.knime.core.node.NodeDialogPane;
import org.knime.core.node.NodeFactory;
import org.knime.core.node.NodeView;

/**
 * <code>NodeFactory</code> for the "ACPNode" Node.
 * Este nodo toma como entrada un conjunto de variables numericas y realiza el algoritmo de componentes principales. para reducir la dimension del problema a analizar
 *
 * @author Rodrigo
 */
public class ACPNodeNodeFactory 
        extends NodeFactory<ACPNodeNodeModel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public ACPNodeNodeModel createNodeModel() {
        return new ACPNodeNodeModel();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getNrNodeViews() {
        return 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeView<ACPNodeNodeModel> createNodeView(final int viewIndex,
            final ACPNodeNodeModel nodeModel) {
        return new ACPNodeNodeView(nodeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasDialog() {
        return true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public NodeDialogPane createNodeDialogPane() {
        return new ACPNodeNodeDialog();
    }

}

