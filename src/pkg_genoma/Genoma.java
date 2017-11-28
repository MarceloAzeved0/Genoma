package pkg_genoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.Sequence;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

public class Genoma {
    private List<Gene> genes;
    
    
	private void loadData(File sourceFile) throws FileNotFoundException {
		if (sourceFile == null) return;
		
		Pattern pattern = Pattern.compile("(\\[(.*?)\\])");
		Pattern patloc = Pattern.compile("(\\[location=(\\d+)..(\\d+)\\])");
		Pattern patLocus = Pattern.compile("(\\[locus_tag=(.*?)\\])");

		Scanner data = new Scanner(sourceFile);

		String locus = "";
		long begin = -1;
		long end = -1;
		List<Character> sequence = new ArrayList<>(200);
		List <String> sequenceString = new ArrayList<>();
		// Percorre o arquivo do genoma
		while (data.hasNextLine()) {
			String line = data.nextLine();
			// Se � uma linha de cabe�alho ...
			if (line.length() > 0 && line.charAt(0) == '>') {
				if (begin != -1) { // Se j� tem um Gene para montar, monta e armazena
					// Cria gene e acrescenta na lista
                    Gene gene = new Gene(locus,begin,end,sequence);
                    genes.add(gene);
                    
                    // Limpa os campos para ler o pr�ximo gene ...
					locus = "";
					begin = -1;
					end = -1;
					sequence = new ArrayList<>(200);
				}
				Matcher matcher = pattern.matcher(line);
				// Procura pelos dados
				while (matcher.find()) {
					String token = matcher.group(1);
					Matcher matchLoc = patloc.matcher(token);
					if (matchLoc.matches()) {
						begin = Long.parseLong(matchLoc.group(2));
						end = Long.parseLong(matchLoc.group(3));
					} else {
						Matcher matchLocus = patLocus.matcher(token);
						if (matchLocus.matches()) {
							locus = matchLocus.group(2);
						}
					}
				}
			} else { // Se � uma linha de sequencia de gene ...
				for (int i = 0; i < line.length(); i++) {
					
					sequence.add(line.charAt(i));
					
				}
			}
			
		}
		
			String palavra="";
			 
			 for(int j=0;j<sequence.size();j++) {
				 palavra= palavra+sequence.get(j);
				 if((j+1)%3==0) {
					 System.out.println(j);
					 sequenceString.add(palavra);
					 palavra="";
				 }
			 }
			
		
		for(int i =0;i<sequenceString.size();i++) {
			System.out.println(sequenceString.get(i));
		}
		
		data.close();
	}
	
	
    public Genoma() throws FileNotFoundException{
    	genes = new LinkedList<>();
    	loadData(new File("sequence.txt"));
    }
    
    public List<Gene> getGenes(){
    	return genes;
    }
    
    public static void main(String args[]){
    	Genoma genoma;
		try {
			genoma = new Genoma();
	    	System.out.println(genoma.getGenes().size());
	    	
	    	
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }
}
