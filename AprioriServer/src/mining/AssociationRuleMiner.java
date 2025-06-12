package mining;

import java.util.LinkedList;

import data.ContinuousAttribute;
import data.Data;
import data.DiscreteAttribute;


/**
 * La classe AssociationRuleMiner modella la scoperta 
 * di regole di associazione confidenti a partire da un pattern frequente.
 * 
 */
public class AssociationRuleMiner {
	
	/**
	 * Genera per ogni pattern di fp, le regole di associazione (chiamando confidentAssociationRuleDiscovery) e
	 * calcola la confidenza. Le regole confidenti sono inserite in una lista.
	 *
	 * @param data tabella su cui calcolare le regole di associazione.
	 * @param fp pattern dal quale generare la regola.
	 * @param minConf valore minimo di confidenza di una regola.
	 * @return lista contenente le regole di associazione generate da fp.
	 * @throws OneLevelPatternException Sollevata nel caso in cui fp abbia un solo elemento.
	 */
	public static LinkedList<AssociationRule> confidentAssociationRuleDiscovery(Data data,FrequentPattern fp,float minConf) throws OneLevelPatternException	{
		if(fp.getPatternLength()==1){
			throw new OneLevelPatternException(fp);
		}
		else {
		LinkedList<AssociationRule> outputAR= new LinkedList<>();
		for(int i=1; i<fp.getPatternLength(); i++){
			AssociationRule ar= confidentAssociationRuleDiscovery(data, fp, minConf, i);
			if(ar.getConfidence()>=minConf){
				outputAR.add(ar);
			}
		}
		
		return outputAR;
		}
	}

/**
 * Crea una regola di associazione estraendo come antecedente l’insieme degli item posizionati in fp
 * prima dell’indice iCut e come conseguente l'insieme degli item posizionati da esso in poi.
 * Di questa regola viene calcolato il valore della confidenza.
 * @param data tabella sulla quale effettuare il calcolo della confidenza.
 * @param fp	pattern dal quale estrarre la regola di associazione.
 * @param minConf valore minimo della confidenza da confrontare con quello ottenuto dalla regola di associazione.
 * @param iCut elemento separatore di fp che distingue la parte considerata come antecedente da quella conseguente.
 * 
 * @return regola di associazione estratta da fp con valore di confidenza superiore a minConf. 
 */
private static AssociationRule confidentAssociationRuleDiscovery(Data data,FrequentPattern fp,float minConf, int iCut) {
	AssociationRule AR=new AssociationRule(fp.getSupport());
	
	//to generate the antecedent of the association rule
	for(int j=0;j<iCut;j++){
		AR.addAntecedentItem(fp.getItem(j));		
	}
	//to generate the consequent of the association rule
	for(int j=iCut;j<fp.getPatternLength();j++){
		AR.addConsequentItem(fp.getItem(j));
	}	
	AR.setConfidence(AssociationRuleMiner.computeConfidence(data,AR));
	return AR;
}

//Aggiorna il supporto
private static  float  computeConfidence(Data data, AssociationRule AR){
	int suppCountAnt=0;
	int suppCountAntCons=0;
	// indice esempio
	for(int i=0;i<data.getNumberOfExamples();i++){
		//indice item
		boolean isSupporting=true;
		for(int j=0;j<AR.getAntecedentLength();j++)
		{
			//DiscreteItem
			if(AR.getAntecedentItem(j) instanceof DiscreteItem){
				DiscreteItem item=(DiscreteItem)AR.getAntecedentItem(j);
				DiscreteAttribute attribute=(DiscreteAttribute)item.getAttribute();
				Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
				if(!item.checkItemCondition(valueInExample)){
					isSupporting=false;
				}
			}else{
				ContinuousItem item=(ContinuousItem)AR.getAntecedentItem(j);
				ContinuousAttribute attribute=(ContinuousAttribute)item.getAttribute();
				Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
				if(!item.checkItemCondition(valueInExample)){
					isSupporting=false;
				}
			}
		}
		if(isSupporting){
			suppCountAnt++;
			for(int j=0;j<AR.getConsequentLength();j++)
			{
				if(AR.getConsequentItem(j) instanceof DiscreteItem){
				//DiscreteItem
				DiscreteItem item=(DiscreteItem)AR.getConsequentItem(j);
				DiscreteAttribute attribute=(DiscreteAttribute)item.getAttribute();
				//Extract the example value
				Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
				if(!item.checkItemCondition(valueInExample)){
					isSupporting=false;
				}
				}else{
					ContinuousItem item=(ContinuousItem)AR.getConsequentItem(j);
					ContinuousAttribute attribute=(ContinuousAttribute)item.getAttribute();
					//Extract the example value
					Object valueInExample=data.getAttributeValue(i, attribute.getIndex());
					if(!item.checkItemCondition(valueInExample)){
						isSupporting=false;
					}
				}
			}
			if(isSupporting)suppCountAntCons++;
			
		}
	}
	return (float) suppCountAntCons/suppCountAnt;
}
}