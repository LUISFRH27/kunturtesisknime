package org.unicen.edu.ar.knime.acp.kernel;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;




import flanagan.math.Matrix;





public class DataManager {

	private static int posicionValor=1;
	
	/*public static Instances getDataSetFromListItem(ListItem[] lista){
		
		FastVector m_cumulativeStructure = new FastVector(lista.length-1);
		   FastVector m_cumulativeInstances=new FastVector();
		   FastVector atts = new FastVector(lista.length-1);
		   FastVector current=new FastVector();
		    for (int i = 0; i < lista.length; i++) {
		      m_cumulativeStructure.addElement(new Hashtable());
		    }
		  
		    for (int i = 0; i < lista.length; i++) {
		        String attname = lista[i].getTitle();
		        Hashtable tempHash = ((Hashtable)m_cumulativeStructure.elementAt(i));
		        if (tempHash.size() == 0) {
		  	atts.addElement(new Attribute(attname));
		        } else {
		  	FastVector values = new FastVector(tempHash.size());
		  	// add dummy objects in order to make the FastVector's size == capacity
		  	for (int z = 0; z < tempHash.size(); z++) {
		  	  values.addElement("dummy");
		  	}
		  	Enumeration enumeration = tempHash.keys();
		  	while (enumeration.hasMoreElements()) {
		  	  Object ob = enumeration.nextElement();
		  	  //	  if (ob instanceof Double) {
		  	  int index = ((Integer)tempHash.get(ob)).intValue();
		  	  values.setElementAt(new String(ob.toString()), index);
		  	  //	  }
		  	}
		  	atts.addElement(new Attribute(attname, values));
		        }
		      }
			 FastVector fastVector=new FastVector(lista.length-1);
				// FastVector fastVectorInstancias=new FastVector(); 
				 Vector<Instance> dataSet1=new Vector<Instance>();
				
				 for (int i = 0; i < lista.length; i++) {
					Attribute atributo=new Attribute(lista[i].getTitle());
					Vector vectorInstancias=lista[i].getVbleComponent().getRawDataTable().getDataVector();
					double[] values=new double[vectorInstancias.size()];
					int j=0;
					current=new FastVector();
					for (Iterator iterator = vectorInstancias.iterator(); iterator
							.hasNext();) {
						Vector valor = (Vector) iterator.next();
						values[j]= (Double)valor.get(1);
						current.addElement(new Double(values[j++]));
					}
					m_cumulativeInstances.addElement(current);
					Instance instance=new Instance(1.0,values);
					dataSet1.add(instance);
				
				
					fastVector.addElement(atributo);
				}
		    
		    String relationName = "a verla";
		    Instances dataSet = new Instances(relationName, 
						      atts, 
						      m_cumulativeInstances.size());   
		    
		    
			 for (int i = 0; i < m_cumulativeInstances.size(); i++) {
					
					Vector vectorInstancias=lista[i].getVbleComponent().getRawDataTable().getDataVector();
					double[] values=new double[vectorInstancias.size()];
					int j=0;
					
					for (Iterator iterator = vectorInstancias.iterator(); iterator
							.hasNext();) {
						Vector valor = (Vector) iterator.next();
						values[j++]= (Double)valor.get(1);
					
					}
					
					dataSet.add(new Instance(1.0, values));
				
				
					
				}
		  	return dataSet;
	}*/
	
//	public static HashMap<String, ComponentePrincipalComponent> getPrincipalComponents(Vector<HashMap<String, Double>> datos,ListItem[] listaVariables){
//		
//		
//	    HashMap<String, ComponentePrincipalComponent> resultado=new HashMap<String, ComponentePrincipalComponent>();
//	    
//	    for (int i = 0; i < listaVariables.length; i++) {
//			resultado.put(listaVariables[i].getTitle(),	new ComponentePrincipalComponent(-1,0D));
//		}
//	    
//	    for (int j=0;j< datos.size();j++) {
//			
//			if(datos.get(j)!=null){
//			for (int i = 0; i < listaVariables.length; i++) {
//				if(datos.get(j).get(listaVariables[i].getTitle())!=null){
//				if(Math.abs(datos.get(j).get(listaVariables[i].getTitle()))>resultado.get(listaVariables[i].getTitle()).getPeso())
//				{
//					resultado.get(listaVariables[i].getTitle()).setNumeroComp(j);
//					resultado.get(listaVariables[i].getTitle()).setPeso(Math.abs(datos.get(j).get(listaVariables[i].getTitle())));
//				}
//				}
//			}	
//				
//			}
//		}
//	    
//	    
//	    
//		return resultado;
//	}
	
	public static HashMap<Integer, Vector<ComponentePrincipalComponent>> getComponentes(double [][] dataSet,String[] nombresVariables){
		HashMap<Integer, Vector<ComponentePrincipalComponent>> componentes=new HashMap<Integer, Vector<ComponentePrincipalComponent>>();
		
		
		// Matrix	matrix=new Matrix(dataSet);	
		
	    
		
		PCA pca=new PCA();
		
		pca.enterScoresAsRowPerItem(dataSet);
		//pca.enterScoresAsRowPerItem(matrix);
		pca.pca();
	
		double[][] loadingFactors;		
        
		int numeroDeFactores=0;
        int indice=0;
        while (indice<pca.orderedEigenValues().length && pca.orderedEigenValues()[indice]>1)
         {
      	   numeroDeFactores++;
      	   indice++;
         }
         
         //if(options.getCantComponentes()>=0)
    		 numeroDeFactores=13;
         
//		if(options.getPerformVarimax())
//         {         
    		 pca.normalVarimaxRotation(numeroDeFactores);    		 
             loadingFactors=pca.getRotatedLoadingFactorsAsRows();
//         }
//		else
//		 {
//			 loadingFactors=pca.loadingFactorsAsRows();
//		 }       
//        
        double [][] transpuesta=new double[34][numeroDeFactores];
        for (int i = 0; i < numeroDeFactores; i++) {
			for (int j = 0; j < 34; j++) {
				transpuesta[j][i]=loadingFactors[i][j];
			}
		}
        
        for (int i = 0; i < 34; i++) {
        	System.out.println(nombresVariables[i]);
        	for(int j=0;j< numeroDeFactores;j++){
			if(Math.abs(transpuesta[i][j])>0.3)
			System.out.println(j+""+Math.abs(transpuesta[i][j]));
			else
				System.out.println(j+"nada");
		}
        }
        
      
//        if(options.getMinAutovalor()>=0)
//        {
        for(int j=0;j<34;j++)
    	{
    	int i=0;
    	int numeroComponente=0;
    	double aux=0D;
        while(i< 34&& pca.orderedEigenValues()[i]>1)
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
     // }
//        if(options.getCantComponentes()>=0)
//        {
//        	  for(int j=0;j<lista.size();j++)
//          	{
//          	int i=0;
//          	int numeroComponente=0;
//          	double aux=0D;
//              while(i< options.getCantComponentes())
//              {
//                 	if(Math.abs(transpuesta[j][i])>aux)
//                 	{
//                      numeroComponente=i;           
//                      aux=Math.abs(transpuesta[j][i]);
//                 	}
//                 	i++;
//              }
//             if(componentes.get(numeroComponente)!=null)
//              	componentes.get(numeroComponente).add(new ComponentePrincipalComponent(nombresVariables[j],aux));
//             else
//             {
//          	   Vector<ComponentePrincipalComponent> vector=new Vector<ComponentePrincipalComponent>();
//          	   vector.add(new ComponentePrincipalComponent(nombresVariables[j],aux));
//          	   componentes.put(new Integer(numeroComponente), vector);
//             }
//            
//              }
//        }
        
   	
	
		return componentes;
	}
}
