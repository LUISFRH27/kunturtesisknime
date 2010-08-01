package org.unicen.edu.ar.knime.acp.node;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpec;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataRow;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.DataType;
import org.knime.core.data.RowKey;
import org.knime.core.data.container.DataContainer;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.def.IntCell;
import org.knime.core.data.def.StringCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.defaultnodesettings.SettingsModelBoolean;
import org.knime.core.node.defaultnodesettings.SettingsModelDouble;
import org.knime.core.node.defaultnodesettings.SettingsModelIntegerBounded;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeLogger;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettings;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.unicen.edu.ar.knime.acp.kernel.ComponentePrincipalComponent;
import org.unicen.edu.ar.knime.acp.kernel.PCA;
import org.unicen.edu.ar.knime.acp.kernel.SettingsConfig;
import org.unicen.edu.ar.knime.acp.kernel.XStreamManager;



import flanagan.math.Matrix;


/**
 * This is the model implementation of ACPNode.
 * Este nodo toma como entrada un conjunto de variables numericas y realiza el algoritmo de componentes principales. para reducir la dimension del problema a analizar
 *
 * @author Rodrigo
 */
public class ACPNodeNodeModel extends NodeModel {
    
    // the logger instance
    private static final NodeLogger logger = NodeLogger
            .getLogger(ACPNodeNodeModel.class);
        
    /** the settings key which is used to retrieve and 
        store the settings (from the dialog or from a settings file)    
       (package visibility to be usable from the dialog). */
	static final String CFGKEY_COUNT = "Count";

    /** initial default count value. */
    static final int DEFAULT_COUNT = 100;

    // example value: the models count variable filled from the dialog 
    // and used in the models execution method. The default components of the
    // dialog work with "SettingsModels".
//    private final SettingsModelIntegerBounded m_count =
//        new SettingsModelIntegerBounded(ACPNodeNodeModel.CFGKEY_COUNT,
//                    ACPNodeNodeModel.DEFAULT_COUNT,
//                    Integer.MIN_VALUE, Integer.MAX_VALUE);
    static final String CFGKEY_IS_AUTOVAL="Is_autoval";
    
    static final boolean DEFAULT_IS_AUTOVAL=true;
    
    private final SettingsModelBoolean isAutoval=new SettingsModelBoolean(ACPNodeNodeModel.CFGKEY_IS_AUTOVAL,ACPNodeNodeModel.DEFAULT_IS_AUTOVAL);
   
    static final String CFGKEY_MIN_AUTOVAL="Min_Autoval";
    static final double DEFAULT_MIN_AUTOVAL=1D;
    
    private final SettingsModelDouble minAutoval=new SettingsModelDouble(ACPNodeNodeModel.CFGKEY_MIN_AUTOVAL,ACPNodeNodeModel.DEFAULT_MIN_AUTOVAL);
    static final String CFGKEY_Is_CantComp="Is_CantComp";
    static final boolean DEFAULT_IS_CANT_COMP=false;
    
    private final SettingsModelBoolean isCantComp=new SettingsModelBoolean(ACPNodeNodeModel.CFGKEY_Is_CantComp,ACPNodeNodeModel.DEFAULT_IS_CANT_COMP);
    
    static final String CFGKEY_CANT_COMP="CantComp";
    static final int DEFAULT_CANT_COMP=1;
    
    private final SettingsModelIntegerBounded cantComp=new SettingsModelIntegerBounded(ACPNodeNodeModel.CFGKEY_CANT_COMP,ACPNodeNodeModel.DEFAULT_CANT_COMP,0,Integer.MAX_VALUE);
    
    static final String CFGKEY_IS_VARIMAX="IsVarimax";
    static final boolean DEFAULT_IS_VARIMAX=false;
    
    private final SettingsModelBoolean isVarimax=new SettingsModelBoolean(ACPNodeNodeModel.CFGKEY_IS_VARIMAX,ACPNodeNodeModel.DEFAULT_IS_VARIMAX);
    
    static final String CFGKEY_CANT_ITERACIONES="CantIteraciones";
    static final int DEFAULT_CAN_ITERACIONES=0;
    
	private final SettingsModelIntegerBounded cantIteraciones=new SettingsModelIntegerBounded(ACPNodeNodeModel.CFGKEY_CANT_ITERACIONES,ACPNodeNodeModel.DEFAULT_CAN_ITERACIONES,0,Integer.MAX_VALUE);
    
	static final String CFGKEY_MIN_LOADING_FACTOR="MinLoadingFactor";
	static final double DEFAULT_MIN_LOADING_FACTOR=0D;
	
	private final SettingsModelDouble minLoadingFactor=new SettingsModelDouble(ACPNodeNodeModel.CFGKEY_MIN_LOADING_FACTOR,ACPNodeNodeModel.DEFAULT_MIN_LOADING_FACTOR);
	
    HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes;
    
    private Double [][] matrizResultado;
    
    String[] nombresVariables;

    private int numeroDeFactores;
    
    static final String CFG_FILENAME = "savedata.xml";
    
    
    static final String CFG_COMPONENTS = "components";
    
    /**
     * Constructor for the node model.
     */
    protected ACPNodeNodeModel() {
    
        // TODO one incoming port and one outgoing port is assumed
        super(1, 1);
        cantComp.setEnabled(false);
        cantIteraciones.setEnabled(false);
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected BufferedDataTable[] execute(final BufferedDataTable[] inData,
            final ExecutionContext exec) throws Exception {

        // TODO do something here
        logger.info("Node Model Stub... this is not yet implemented !");

        
        // the data table spec of the single output table, 
        // the table will have three columns:
        
        // let's add m_count rows to it
      
        
        BufferedDataTable table = inData[0];
        int rowCount = table.getRowCount();
        
        nombresVariables=new String[table.getDataTableSpec().getNumColumns()];
        double[][] dataSet=new double[table.getDataTableSpec().getNumColumns()][rowCount];
        
        for (int i = 0; i < table.getDataTableSpec().getNumColumns(); i++) {
			nombresVariables[i]=table.getDataTableSpec().getColumnSpec(i).getName();		
		}
        int currentRow = 0;
       
        for (DataRow row : table) {
            // check if the user cancelled the execution
            exec.checkCanceled();
            // report progress
            exec.setProgress((double)currentRow / rowCount, 
                   " processing row " + currentRow);
            for (int i = 0; i < row.getNumCells(); i++) {
                DataCell cell = row.getCell(i);
                if (!cell.isMissing()) {
                   
                   dataSet[i][currentRow]=Double.parseDouble(cell.toString());
                   
                }
            }
            currentRow++;
        }
      

        componentes=getComponentes(dataSet, nombresVariables,table.getDataTableSpec().getNumColumns());
        
        DataColumnSpec[] allColSpecs = new DataColumnSpec[componentes.keySet().size()];
        for(int i=0;i<componentes.keySet().size();i++)
        {
        	allColSpecs[i]=new DataColumnSpecCreator("Column "+i,DoubleCell.TYPE).createSpec();
        }

        
        
        DataTableSpec outputSpec = new DataTableSpec(allColSpecs);
        // the execution context will provide us with storage capacity, in this
        // case a data container to which we will add rows sequentially
        // Note, this container can also handle arbitrary big data tables, it
        // will buffer to disc if necessary.
        BufferedDataContainer container = exec.createDataContainer(outputSpec);
        
        int[] numerosColumnas=new int[componentes.keySet().size()];
        int pos=0;
        for (Iterator<Integer> iterator = componentes.keySet().iterator(); iterator.hasNext();) {
			Integer type = (Integer) iterator.next();
			Vector<ComponentePrincipalComponent> factores=componentes.get(type);
			numerosColumnas[pos++]=getMayor(factores);			
		}
        
        for (int i = 0; i < rowCount; i++) {
            RowKey key = new RowKey("Row" + i);
          
            DataCell[] cells = new DataCell[componentes.keySet().size()];
            for(int j=0;j<componentes.keySet().size();j++)
            {
            	cells[j]=new DoubleCell(dataSet[numerosColumnas[j]][i]);
            }

           
            
            
            DataRow row = new DefaultRow(key, cells);
            container.addRowToTable(row);
            
            // check if the execution monitor was canceled
            exec.checkCanceled();
            exec.setProgress(i / (double)componentes.keySet().size(), 
                "Adding row " + i);
        }
        
        
        
        // once we are done, we close the container and return its table
        container.close();
        BufferedDataTable out = container.getTable();
        
        return new BufferedDataTable[]{out};
    }
    


	private int getMayor(Vector<ComponentePrincipalComponent> factores) {
		double peso=0;
		int fila=-1;
		for(int i=0;i<factores.size();i++)
			if(factores.get(i).getPeso()>peso)
			{
				fila=i;
				peso=factores.get(i).getPeso();
			}
			
		
		return fila;
	}    
    

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
        // TODO Code executed on reset.
        // Models build during execute are cleared here.
        // Also data handled in load/saveInternals will be erased here.
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected DataTableSpec[] configure(final DataTableSpec[] inSpecs)
            throws InvalidSettingsException {
        
        // TODO: check if user settings are available, fit to the incoming
        // table structure, and the incoming types are feasible for the node
        // to execute. If the node can execute in its current state return
        // the spec of its output data table(s) (if you can, otherwise an array
        // with null elements), or throw an exception with a useful user message

        return new DataTableSpec[]{null};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {

        // TODO save user settings to the config object.
        
        isAutoval.saveSettingsTo(settings);
        minAutoval.saveSettingsTo(settings);
        isCantComp.saveSettingsTo(settings);
        cantComp.saveSettingsTo(settings);
        isVarimax.saveSettingsTo(settings);
        cantIteraciones.saveSettingsTo(settings);
        minLoadingFactor.saveSettingsTo(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
        // TODO load (valid) settings from the config object.
        // It can be safely assumed that the settings are valided by the 
        // method below.
        
        isAutoval.loadSettingsFrom(settings);
        minAutoval.loadSettingsFrom(settings);
        isCantComp.loadSettingsFrom(settings);
        cantComp.loadSettingsFrom(settings);
        isVarimax.loadSettingsFrom(settings);
        cantIteraciones.loadSettingsFrom(settings);
        minLoadingFactor.loadSettingsFrom(settings);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
            
        // TODO check if the settings could be applied to our model
        // e.g. if the count is in a certain range (which is ensured by the
        // SettingsModel).
        // Do not actually set any values of any member variables.

        isAutoval.validateSettings(settings);
        minAutoval.validateSettings(settings);
        isCantComp.validateSettings(settings);
        cantComp.validateSettings(settings);
        isVarimax.validateSettings(settings);
        cantIteraciones.validateSettings(settings);
        minLoadingFactor.validateSettings(settings);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
        
        // TODO load internal data. 
        // Everything handed to output ports is loaded automatically (data
        // returned by the execute method, models loaded in loadModelContent,
        // and user settings set through loadSettingsFrom - is all taken care 
        // of). Load here only the other internals that need to be restored
        // (e.g. data used by the views).
    	String fileName = internDir + "\\" + CFG_FILENAME;
       SettingsConfig settingConfig = new XStreamManager().GetonfigSettings(fileName);
       
    	componentes = settingConfig.getComponentes();
        nombresVariables = settingConfig.getVariableNames();
        matrizResultado = settingConfig.getResultMatrix();
        numeroDeFactores = settingConfig.getFactorsNumber();
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
       
        // TODO save internal models. 
        // Everything written to output ports is saved automatically (data
        // returned by the execute method, models saved in the saveModelContent,
        // and user settings saved through saveSettingsTo - is all taken care 
        // of). Save here only the other internals that need to be preserved
        // (e.g. data used by the views).
       // HashMap<Integer, Vector<ComponentePrincipalComponent>> components = this.getComponentes();
    	String fileName = internDir + "\\" + CFG_FILENAME;
    	SettingsConfig config = new SettingsConfig();
    	config.setComponentes(componentes);
    	config.setResultMatrix(matrizResultado);
    	config.setVariableNames(nombresVariables);
    	config.setFactorsNumber(numeroDeFactores);
    	
        new XStreamManager().SaveXMLFile(fileName, config);
    }
    
    public HashMap<Integer, Vector<ComponentePrincipalComponent>> getComponentes(){
    	return componentes;
    }
    
    private HashMap<Integer, Vector<ComponentePrincipalComponent>> getComponentes(double [][] dataSet,String[] nombresVariables,int cantCol){
		HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes=new HashMap<Integer, Vector<ComponentePrincipalComponent>>();
		
		
		PCA pca=new PCA();
		
		pca.enterScoresAsRowPerItem(dataSet);		
		pca.pca();
	
		double[][] loadingFactors;		
        
		numeroDeFactores=0;
        int indice=0;
       
         
         if(isCantComp.getBooleanValue())           
    		  numeroDeFactores=cantComp.getIntValue();
         else
        	 while (indice<pca.orderedEigenValues().length && pca.orderedEigenValues()[indice]>minAutoval.getDoubleValue())
             {
          	   numeroDeFactores++;
          	   indice++;
             }
         
		if(isVarimax.getBooleanValue())
         {         
    		 pca.normalVarimaxRotation(numeroDeFactores);    		 
             loadingFactors=pca.getRotatedLoadingFactorsAsRows();
         }
		else
		 {
			 loadingFactors=pca.loadingFactorsAsRows();
		 }       
        
        Double [][] transpuesta=new Double[cantCol][numeroDeFactores];
        for (int i = 0; i < numeroDeFactores; i++) {
			for (int j = 0; j < cantCol; j++) {
				transpuesta[j][i]=loadingFactors[i][j];
			}
		}
        

       
      
        if(isAutoval.getBooleanValue())
        {
        for(int j=0;j<cantCol;j++)
    	{
    	int i=0;
    	int numeroComponente=0;
    	double aux=0D;
        while(i< cantCol&& pca.orderedEigenValues()[i]>1)
        {
           	if(Math.abs(transpuesta[j][i])>aux)
           	{
                numeroComponente=i;           
                aux=Math.abs(transpuesta[j][i]);
           	}
           	i++;
        }
       if(componentes.get(numeroComponente)!=null)
        	componentes.get(numeroComponente).add(new ComponentePrincipalComponent(nombresVariables[j],aux));
       else
       {
    	   Vector<ComponentePrincipalComponent> vector=new Vector<ComponentePrincipalComponent>();
    	   vector.add(new ComponentePrincipalComponent(nombresVariables[j],aux));
    	   componentes.put(new Integer(numeroComponente), vector);
       }
      
        }
      }
        if(isCantComp.getBooleanValue())
        {
        	  for(int j=0;j<cantCol;j++)
          	{
          	int i=0;
          	int numeroComponente=0;
          	double aux=0D;
              while(i< cantComp.getIntValue())
              {
                 	if(Math.abs(transpuesta[j][i])>aux)
                 	{
                      numeroComponente=i;           
                      aux=Math.abs(transpuesta[j][i]);
                 	}
                 	i++;
              }
             if(componentes.get(numeroComponente)!=null)
              	componentes.get(numeroComponente).add(new ComponentePrincipalComponent(nombresVariables[j],aux));
             else
             {
          	   Vector<ComponentePrincipalComponent> vector=new Vector<ComponentePrincipalComponent>();
          	   vector.add(new ComponentePrincipalComponent(nombresVariables[j],aux));
          	   componentes.put(new Integer(numeroComponente), vector);
             }
            
              }
        }
        
        for (int i = 0; i < cantCol; i++) {
        	
        	for(int j=0;j< numeroDeFactores;j++){
        		if(Math.abs(transpuesta[i][j])>=minLoadingFactor.getDoubleValue())
        		transpuesta[i][j]=Redondear(transpuesta[i][j], 2);
        		else
        			transpuesta[i][j]=0D;
//			if(Math.abs(transpuesta[i][j])>0.3)
//			System.out.println(j+""+Math.abs(transpuesta[i][j]));
//			else
//				System.out.println(j+"nada");
		}
        }
        matrizResultado=transpuesta;
	
		return componentes;
	}
    public double Redondear(double nD, int nDec)
	{
	  return Math.round(nD*Math.pow(10,nDec))/Math.pow(10,nDec);
	}
    
    public Double[][] getMatrix(){return matrizResultado;}

    public String[] getNombreVariables(){return nombresVariables;}
    public int getNumeroDeComponentes(){return numeroDeFactores;}
}

