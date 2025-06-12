package mining;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;



/**
 * La classe AssociationRuleArchive modella un contenitore di coppie pattern-regole di associazione.
 */
public class AssociationRuleArchive implements Serializable{
	/**
	 * Contenitore HashMap per le coppie pattern-regole di associazione.
	 */
	private HashMap<FrequentPattern, TreeSet<AssociationRule>> archive;
	
	/**
	 * Costruttore che istanzia un nuovo AssociationRuleArchive.
	 */
	public AssociationRuleArchive(){
		archive= new LinkedHashMap<FrequentPattern, TreeSet<AssociationRule>>();
		
	}
	
	/**
	 * Memorizza nell' archivio una coppia avente come chiave fp se questa non è già presente in esso. 
	 *
	 * @param fp FrequentPattern da memorizzare nell'archivio
	 */
	public void put(FrequentPattern fp){
		if(!archive.containsKey(fp))
			archive.put(fp, new TreeSet<AssociationRule>());
	}
	
	/**
	 * Memorizza nell'archivio una coppia pattern frequente-regola di associazione(fp,rule) se il pattern
	 * frequente non vi è già contenuto come chiave. 
	 * Altrimenti, aggiunge la regola di associazione al TreeSet delle regole indicizzato da fp.
	 *
	 * @param fp FrequentPattern da memorizzare 
	 * @param rule regola di associazione estratta da fp
	 */
	public void put(FrequentPattern fp, AssociationRule rule){
		if(!archive.containsKey(fp)){
			TreeSet<AssociationRule> tree = new TreeSet<AssociationRule>();
			tree.add(rule);
			archive.put(fp,tree);}
		else 
			archive.get(fp).add(rule);
	}
	
	/**
	 * Restituisce le regole di associazione aventi come chiave fp.
	 *
	 * @param fp chiave di cui restituire le regole di associazione
	 * @return le regole di associazione di fp
	 * @throws NoPatternException Sollevata nel caso in cui la chiave fp non compaia nell'archivio.
	 */
	public TreeSet<AssociationRule> getRules(FrequentPattern fp) throws NoPatternException{
		if(archive.containsKey(fp)){
			return archive.get(fp);
		}
		throw new NoPatternException() ;
	}
	/**
	 * Restituisce una stringa che rappresenta l'archivio.
	 * @return stringa che rappresenta l'archivio.
	 */
	public String toString(){
		String s="";
		Iterator<FrequentPattern> i= archive.keySet().iterator();
		Integer k= new Integer(1);
		
		while(i.hasNext()){
			FrequentPattern temp= i.next();
			s+=k.toString()+" "+temp+"\n";
			
			Iterator<AssociationRule> j = archive.get(temp).iterator();
			Integer m=new Integer(1);
			while(j.hasNext()){
				s+=k.toString()+"."+m.toString()+" "+j.next()+"\n";
				m=m+1;
			}
			if(!j.hasNext()){
				s+="\n";
				k++;
			}
			
		}
		
		return s;
	}
	
	/**
	 * Esegue la serializzazione dell'archivio sul file il cui nome è passato come parametro(nomeFile).
	 *
	 * @param nomeFile nome del file su cui memorizzare l'archivio.
	 * @throws FileNotFoundException Sollevata nel caso in cui il file non viene trovato.
	 * @throws IOException Sollevata in caso di errori durante il salvataggio su file.
	 */
	public void salva(String nomeFile) throws FileNotFoundException, IOException{
		File f =new File(nomeFile);
		
		FileOutputStream outstream=null ;
		ObjectOutputStream s=null;
		try{
		outstream = new FileOutputStream(f);
		 s = new ObjectOutputStream(outstream);
		s.writeObject(this);}
		finally{
		s.flush();
		s.close();
		outstream.close();}
	}
	
	/**
	 * Restituisce l'archivio salvato sul file nomeFile mediante la deserializzazione. 
	 *
	 * @param nomeFile nome del file da cui caricare l'archivio
	 * @return AssociatioRuleArchive salvato su file.
	 * @throws IOException Sollevata in caso di errori durante il caricamento dell'archivio.
	 * @throws ClassNotFoundException Sollevata nel caso in cui una classe di un oggetto serializzato non viene trovata.
	 */
	
	public static AssociationRuleArchive carica(String nomeFile) throws FileNotFoundException, IOException, ClassNotFoundException  {
		FileInputStream in=null ;
		ObjectInputStream s=null;
		AssociationRuleArchive temp=null;
		
		in = new FileInputStream(nomeFile);
		s = new ObjectInputStream(in);
		temp =(AssociationRuleArchive) s.readObject();
		s.close();
		in.close();
		
		return temp;
	}
}


