package pkg_genoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.sound.midi.Sequence;

import com.sun.org.apache.bcel.internal.generic.LSTORE;

public class Genoma {
    private List<Gene> genes;
    AminoacidTable amino = new AminoacidTable();
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
		/*List <String>lst = compareAminoacid(mergeCharacter(sequence));
		for(int i = 0;i<lst.size();i++) {
			System.out.println(lst.get(i));
		}
		int c =compareCorrect(lst);	
		System.out.println(c);*/
		
		int a = compareMerge(sequence);
		System.out.println(a);
		data.close();
	}
	public List mergeCharacter(List<Character> lst) {
        List<String> t = new ArrayList<>();
        String palavra = "";
        ListIterator<Character> it = lst.listIterator();

        while(it.hasNext()) {
            palavra = palavra + it.next();

            if (palavra.length() % 3 == 0) {
                t.add(palavra);
                palavra = "";
            }
        }
        return  t;
    }

    public List mergeCharacterPlusOne(List<Character> lst) {
        List<String> t = new ArrayList<>();
        String palavra = "";
        ListIterator<Character> it = lst.listIterator();
        it.next();

        while(it.hasNext()) {
            palavra = palavra + it.next();

            if (palavra.length() % 3 == 0) {
                t.add(palavra);
                palavra = "";
            }
        }
        return  t;

    }
    public int compareMerge(List <Character>lst){
    	int maior = 0;
    	List<Integer> t = new ArrayList<>();
    	
    	t.add(compareCorrect(compareAminoacid(mergeCharacter(lst))));
    	t.add(compareCorrect(compareAminoacid(mergeCharacterPlusOne(lst))));
    	t.add(compareCorrect(compareAminoacid(mergeCharacterPlusTwo(lst))));
    	t.add(compareCorrect(compareAminoacid(mergeCharacterContrario(lst))));
    	t.add(compareCorrect(compareAminoacid(mergeCharacterContrarioLessOne(lst))));
    	t.add(compareCorrect(compareAminoacid(mergeCharacterContrarioLessTwo(lst))));
    	
    	for(int i = 0; i<t.size();i++) {
    		if(maior < t.get(i)) {
    			maior = t.get(i);
    		}
    	}
    	
    	
    	
    	
    	return maior;
    }

    public List mergeCharacterPlusTwo(List<Character> lst) {
        List<String> t = new ArrayList<>();
        String palavra = "";
        ListIterator<Character> it = lst.listIterator();
        it.next();
        it.next();

        while(it.hasNext()) {
            palavra = palavra + it.next();

            if (palavra.length() % 3 == 0) {
                t.add(palavra);
                palavra = "";
            }
        }
        return  t;

    }
    public List mergeCharacterContrario(List<Character> lst) {
        List<String> t = new ArrayList<>();
        String palavra = "";
        ListIterator<Character> it = lst.listIterator(lst.size());

        while(it.hasPrevious()) {
            palavra = palavra + it.previous();

            if (palavra.length() % 3 == 0) {
                t.add(palavra);
                palavra = "";
            }
        }
        return  t;
    }

    public List mergeCharacterContrarioLessOne(List<Character> lst) {
        List<String> t = new ArrayList<>();
        String palavra = "";
        ListIterator<Character> it = lst.listIterator(lst.size());
        it.previous();

        while(it.hasPrevious()) {
            palavra = palavra + it.previous();

            if (palavra.length() % 3 == 0) {
                t.add(palavra);
                palavra = "";
            }
        }
        return  t;
    }

    public List mergeCharacterContrarioLessTwo(List<Character> lst) {
        List<String> t = new ArrayList<>();
        String palavra = "";
        ListIterator<Character> it = lst.listIterator(lst.size());
        it.previous();
        it.previous();

        while(it.hasPrevious()) {
            palavra = palavra + it.previous();

            if (palavra.length() % 3 == 0) {
                t.add(palavra);
                palavra = "";
            }
        }
        return  t;
    }
	public List compareAminoacid(List <String> codon) {
		List <String> amin = new ArrayList<>(200);
		for(int i = 0; i<codon.size();i++) {
			amin.add(amino.getAminoacid(codon.get(i)));
		}
		
		return amin;
	}
	public int compareCorrect(List<String> amin) {
		
		for(int i = 0;i<amin.size();i++) {
			if(amin.get(i)== "Met") {
				return contaDistancia(amin,i);
			}
		}
		return 0;
	}
	private int contaDistancia(List<String> amin,int inicia) {
		int count=0;
		for( ;inicia<amin.size();inicia++) {
			if(amin.get(inicia)!="Stop") {
				count++;
			}else {
				return count;
			}
		}
		return count;
	}
	
	
    public Genoma() throws FileNotFoundException{
    	//List<Character> sequence = new ArrayList<>(200);
    	genes = new LinkedList<>();
    	loadData(new File("sequence.txt"));
    }
    
    public List<Gene> getGenes(){
    	return genes;
    }
    
    public static void main(String args[]) throws FileNotFoundException{
        Genoma genoma = new Genoma();
    }
}
