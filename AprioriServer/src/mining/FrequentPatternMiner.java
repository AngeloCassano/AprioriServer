package mining;


import data.*;
import java.util.Iterator;
import java.util.LinkedList;

import data.Attribute;
import data.Data;
import data.DiscreteAttribute;
import data.EmptySetException;
import utility.Queue;

/**
 * La classe FrequentPatternMiner implementa i metodi necessari per la scoperta di pattern frequenti 
 * tramite l'algoritmo Apriori.
 */
public class FrequentPatternMiner {
	
	/**
	 * A partire dalla tabella vengono generati i pattern frequenti di lunghezza = 1 e 
	 * per ognuno di essi, richiamando il metodo expandFrequentPatterns,
	 * genera tutti i possibili pattern frequenti di lunghezza > 1.
	 * 
	 * @param data tabella da cui ricavare i pattern frequenti
	 * @param minSup valore minimo di supporto da confrontare con quello di ogni pattern frequente generato
	 * @return lista collegata dei pattern frequenti generati
	 * @throws EmptySetException sollevata nel caso in cui la tabella sia priva di transazioni
	 */
	public static LinkedList<FrequentPattern> frequentPatternDiscovery(Data data,float minSup) throws EmptySetException{
		if(data.getNumberOfExamples()==0) throw new EmptySetException()  ;
		else {
		Queue<FrequentPattern> fpQueue=new Queue<FrequentPattern>();		
		LinkedList<FrequentPattern> outputFP=new LinkedList<>();
		for(int i=0;i<data.getNumberOfAttributes();i++)
		{
			Attribute currentAttribute=data.getAttribute(i);
			if(currentAttribute instanceof DiscreteAttribute){
				for(int j=0;j<((DiscreteAttribute)currentAttribute).getNumberOfDistinctValues();j++){
					DiscreteItem item=new DiscreteItem( 
							(DiscreteAttribute)currentAttribute, 
							((DiscreteAttribute)currentAttribute).getValue(j));
					FrequentPattern fp=new FrequentPattern();
					fp.addItem(item);
					fp.setSupport(FrequentPatternMiner.computeSupport(data, fp));
					if(fp.getSupport()>=minSup){ 
						fpQueue.enqueue(fp);
						outputFP.add(fp);
					}
			}
				
			}else{
				Iterator<Float>it=((ContinuousAttribute)currentAttribute).iterator();
				if(it.hasNext()) {
					float inf=it.next();
					while(it.hasNext()){
						float sup=it.next();
						ContinuousItem item;
						if(it.hasNext())
							item=new ContinuousItem((ContinuousAttribute)currentAttribute,new Interval(inf, sup));
						else
							item=new ContinuousItem((ContinuousAttribute)currentAttribute,new Interval(inf, sup+0.01f*(sup-inf)));
						inf=sup;
						FrequentPattern fp=new FrequentPattern();
						fp.addItem(item);
						fp.setSupport(FrequentPatternMiner.computeSupport(data,fp));
						if(fp.getSupport()>=minSup){ // 1-FP CANDIDATE
							fpQueue.enqueue(fp);
							outputFP.add(fp);
						}
					}
				}
			}
		}
		outputFP=expandFrequentPatterns(data,minSup,fpQueue,outputFP);
		
		return outputFP;
		}
	}
	
	/**
	 * Estrae dalla coda dei frequent pattern un elemento (pattern frequente) e richiamando 
	 * il metodo refineFrequentPattern, lo raffina. Se il valore di supporto del raffinamento 
	 * ottenuto è maggiore del valore minimo di supporto lo si aggiunge sia alla coda, 
	 * che alla lista dei pattern frequenti da restituire.
	 * 
	 * @param data tabella da cui ricavare gli item 
	 * @param minSup valore minimo di supporto da confrontare con quello ottenuto dal raffinamento
	 * @param fpQueue coda dei frequent pattern da cui effettuare le estrazioni
	 * @param outputFP lista dei frequent pattern già estratti
	 * @return lista dei frequent pattern di lunghezza > 1 ottenuta dai raffinamenti
	 */
	private static LinkedList<FrequentPattern> expandFrequentPatterns(Data data, float minSup, Queue<FrequentPattern> fpQueue,LinkedList<FrequentPattern> outputFP)  {
		while(!fpQueue.isEmpty()){
			FrequentPattern temp =(FrequentPattern)fpQueue.first();
			for(int j=0; j<data.getNumberOfAttributes(); j++){
				Attribute currentAttribute=data.getAttribute(j);
				if(currentAttribute instanceof DiscreteAttribute){
					
				DiscreteAttribute currentDiscreteA = (DiscreteAttribute) currentAttribute;
				
				for(int i=0; i<currentDiscreteA.getNumberOfDistinctValues(); i++){
					
					boolean flag=false;
					
					for(int k=0; k<temp.getPatternLength() && flag==false; k++){

						if(temp.getItem(k) instanceof DiscreteItem ){
							
							if(temp.getItem(k).checkItemCondition(currentDiscreteA.getValue(i)) || temp.getItem(k).getAttribute().getName()==currentDiscreteA.getName()){
							
							flag=true;
							}
					
						}
					}	
					if(flag==false){
						Item b= new DiscreteItem(currentDiscreteA,currentDiscreteA.getValue(i));
						FrequentPattern temp1= refineFrequentPattern(temp,b);
						temp1.setSupport(computeSupport(data,temp1));
						if(computeSupport(data,temp1)>=minSup){
							fpQueue.enqueue(temp1);
							outputFP.add(temp1);
							
						}		
					}
				}
				}else{
					
					
					ContinuousAttribute currentContinuousA= (ContinuousAttribute) currentAttribute;
					boolean flagCon=false;
					for(int n=0; n<temp.getPatternLength() && flagCon==false; n++){
						if(temp.getItem(n) instanceof ContinuousItem ){
							flagCon=true;
							
						}
						
					}	
					if(flagCon==false){
						Iterator<Float>it=((ContinuousAttribute)currentAttribute).iterator();
						if(it.hasNext()) {
							float inf=it.next();
							while(it.hasNext()){
								float sup=it.next();
								ContinuousItem item;
								if(it.hasNext())
									item=new ContinuousItem((ContinuousAttribute)currentAttribute,new Interval(inf, sup));
								else
									item=new ContinuousItem((ContinuousAttribute)currentAttribute,new Interval(inf, sup+0.01f*(sup-inf)));
								inf=sup;
								FrequentPattern fp1= refineFrequentPattern(temp,item);
								
								fp1.setSupport(FrequentPatternMiner.computeSupport(data,fp1));
								if(fp1.getSupport()>=minSup){ // 1-FP CANDIDATE
								
									fpQueue.enqueue(fp1);
									outputFP.add(fp1);
								}
							}	
						}
					}
			}	
		}	
			fpQueue.dequeue();
	}
		return outputFP;
	}
	
	/**
	 * Calcola e restituisce il valore di supporto del pattern frequente relativo alle transazioni della tabella.
	 * 
	 * @param data tabella su cui calcolare il supporto
	 * @param FP pattern frequente di cui calcolare il supporto
	 * @return valore di supporto di FP calcolato
	 */
	private static float computeSupport(Data data,FrequentPattern FP){
		int suppCount=0;
		// indice esempio
		for(int i=0;i<data.getNumberOfExamples();i++){
			//indice item
			boolean isSupporting=true;
			for(int j=0;j<FP.getPatternLength();j++)
			{
				//DiscreteItem
				Item item=FP.getItem(j);
				Attribute attribute=item.getAttribute();
				//Extract the example value
				Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
				if(!item.checkItemCondition(valueInExample)){
					isSupporting=false;
					break; //the ith example does not satisfy fp
				}
				
			}
			if(isSupporting)
				suppCount++;
		}
		return ((float)suppCount)/(data.getNumberOfExamples());
		
	}
	/**
	 * Partendo da un pattern frequente e un item restituisce un nuovo pattern ottenuto 
	 * dalla loro concatenazione.
	 *  
	 * @param FP pattern frequente da raffinare 
	 * @param item item da aggiungere al pattern frequente
	 * @return pattern frequente ottenuto aggiungendo a fp l'item indicato da item  
	 */
	private static FrequentPattern refineFrequentPattern(FrequentPattern FP, Item item){
		FrequentPattern temp = new FrequentPattern();
		for(int i=0; i<FP.getPatternLength(); i++){
			temp.addItem(FP.getItem(i));
		}
		temp.addItem(item);
		return temp;
	}

}

