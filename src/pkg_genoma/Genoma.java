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
import com.sun.scenario.effect.Merge;

public class Genoma {
	List<Character> sequence = new ArrayList<>(200);
	private List<Gene> genes;
	//List<Character> sequence = new ArrayList<>(200);

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
		//List<Character> sequence = new ArrayList<>(200);
		List <String> sequenceString = new ArrayList<>();
		// Percorre o arquivo do genoma
		while (data.hasNextLine()) {
			String line = data.nextLine();
			// Se ï¿½ uma linha de cabeï¿½alho ...
			if (line.length() > 0 && line.charAt(0) == '>') {
				if (begin != -1) { // Se ja tem um Gene para montar, monta e armazena
					// Cria gene e acrescenta na lista
					Gene gene = new Gene(locus,begin,end,sequence);
					genes.add(gene);

					// Limpa os campos para ler o prï¿½ximo gene ...
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
			} else { // Se ï¿½ uma linha de sequencia de gene ...
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

		List<String> sequenciaCorreta = compareMerge();
		/*for(int i=0;i<sequenciaCorreta.size();i++){
			System.out.println(sequenciaCorreta.get(i));
		}*/
		data.close();
	}
	public void imprimindoListas(){
		List <String> merge = mergeCharacter();
		List <String> mergePlusOne = mergeCharacterPlusOne();
		List <String> mergePlusTwo = mergeCharacterPlusTwo();
		List <String> mergeContrario = mergeCharacterContrario();
		List <String> mergeContrarioPlusOne = mergeCharacterPlusOne();
		List <String> mergeContrarioPlusTwo = mergeCharacterContrarioLessTwo();

		merge.forEach(p -> System.out.println("Ordem 5-3-1\n"+p));
		mergePlusOne.forEach(p -> System.out.println("Ordem 5-3-2\n"+p));
		mergePlusTwo.forEach(p -> System.out.println("Ordem 5-3-3\n"+p));
		mergeContrario.forEach(p -> System.out.println("Ordem 3-5-1\n"+p));
		mergeContrarioPlusOne.forEach(p -> System.out.println("Ordem 3-5-2\n"+p));
		mergePlusTwo.forEach(p -> System.out.println("Ordem 3-5-3\n"+p));

	}
	public List mergeCharacter() {
		List<String> t = new ArrayList<>();
		String palavra = "";
		ListIterator<Character> it = sequence.listIterator();

		while(it.hasNext()) {
			palavra = palavra + it.next();

			if (palavra.length() % 3 == 0) {
				t.add(palavra);
				palavra = "";
			}
		}
		return  t;
	}

	public List mergeCharacterPlusOne() {
		List<String> t = new ArrayList<>();
		String palavra = "";
		ListIterator<Character> it = sequence.listIterator();
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
	public List<String> compareMerge(){

		int maior = 0;
		List<Integer> t = new ArrayList<>();

		t.add(compareCorrect(compareAminoacid(mergeCharacter())));
		t.add(compareCorrect(compareAminoacid(mergeCharacterPlusOne())));
		t.add(compareCorrect(compareAminoacid(mergeCharacterPlusTwo())));
		t.add(compareCorrect(compareAminoacid(mergeCharacterContrario())));
		t.add(compareCorrect(compareAminoacid(mergeCharacterContrarioLessOne())));
		t.add(compareCorrect(compareAminoacid(mergeCharacterContrarioLessTwo())));

		for(int i = 0; i<t.size();i++) {
			if(maior < t.get(i)) {
				maior = t.get(i);
			}
		}

		return enviarListaCorreta(maior,sequence);
	}

	private List<String> enviarListaCorreta(int maior, List<Character> lst) {
		if(compareCorrect(compareAminoacid(mergeCharacter()))==maior){
			return compareAminoacid(mergeCharacter());
		}else if(compareCorrect(compareAminoacid(mergeCharacterPlusOne())) == maior){
			return compareAminoacid(mergeCharacterPlusOne());
		}else if(compareCorrect(compareAminoacid(mergeCharacterPlusTwo())) == maior){
			return compareAminoacid(mergeCharacterPlusTwo());
		}else if(compareCorrect(compareAminoacid(mergeCharacterContrario()))==maior){
			return compareAminoacid(mergeCharacterContrario());
		}else if(compareCorrect(compareAminoacid(mergeCharacterContrarioLessOne()))==maior){
			return compareAminoacid(mergeCharacterContrarioLessOne());
		}else if(compareCorrect(compareAminoacid(mergeCharacterContrarioLessTwo()))==maior){
			return compareAminoacid(mergeCharacterContrarioLessTwo());
		}
		return null;
	}
	public List mergeCharacterPlusTwo() {
		List<String> t = new ArrayList<>();
		String palavra = "";
		ListIterator<Character> it = sequence.listIterator();
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
	public List mergeCharacterContrario() {
		List<String> t = new ArrayList<>();
		String palavra = "";
		ListIterator<Character> it = sequence.listIterator(sequence.size());

		while(it.hasPrevious()) {
			palavra = palavra + it.previous();

			if (palavra.length() % 3 == 0) {
				t.add(palavra);
				palavra = "";
			}
		}
		return  t;
	}

	public List mergeCharacterContrarioLessOne() {
		List<String> t = new ArrayList<>();
		String palavra = "";
		ListIterator<Character> it = sequence.listIterator(sequence.size());
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

	public List mergeCharacterContrarioLessTwo() {
		List<String> t = new ArrayList<>();
		String palavra = "";
		ListIterator<Character> it = sequence.listIterator(sequence.size());
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
		super();
		genes = new LinkedList<>();
		loadData(new File("sequence.txt"));
	}

	public List<Gene> getGenes(){
		return genes;
	}
	public static void menu(int opcao) throws FileNotFoundException {
		Genoma genoma = new Genoma();
		List<Gene> gene = genoma.getGenes();
		switch (opcao) {
		case 1:
			for(int i = 0;i<gene.size();i++){
				System.out.println(gene.get(i).getLocus());
			}

			//Lista locus
			break;
		case 2:
			for(int i = 0;i<gene.size();i++){
				//System.out.println(gene.get(i).getLocus());
				System.out.println("Inicio: "+gene.get(i).getBegin());
				System.out.println("Final: "+gene.get(i).getEnd()+"\n");


			}
			//Posições de início e fim dos genes dentro dos genomas
			break;
		case 3:
			for(int i = 0;i<gene.size();i++){
				System.out.println(gene.get(i).getBases());
			}
			break;
		case 4:
			genoma.imprimindoListas();
						

			break;
		case 5:
			System.out.println("Ordem 5-3-1\n"+genoma.compareAminoacid(genoma.mergeCharacter()));
			System.out.println("Ordem 5-3-2\n"+genoma.compareAminoacid(genoma.mergeCharacterPlusOne()));
			System.out.println("Ordem 5-3-3\n"+genoma.compareAminoacid(genoma.mergeCharacterPlusTwo()));
			System.out.println("Ordem 3-5-1\n"+genoma.compareAminoacid(genoma.mergeCharacterContrario()));
			System.out.println("Ordem 3-5-2\n"+genoma.compareAminoacid(genoma.mergeCharacterContrarioLessOne()));
			System.out.println("Ordem 3-5-3\n"+genoma.compareAminoacid(genoma.mergeCharacterContrarioLessTwo()));

			break;
		case 6:
			System.out.println(genoma.compareMerge());

			break;
		default:
			System.exit(0);
			break;
		}
	}

	public static void main(String args[]) throws FileNotFoundException{
		Scanner in = new Scanner(System.in);
		
		System.out.println("########################");
		System.out.println("#                      #");
		System.out.println("#    PROJETO GENOMA    #");
		System.out.println("#                      #");
		System.out.println("########################\n\n");

		while (true) {
			System.out.println("\n");
			System.out.println("1 - Listar Locus");
			System.out.println("2 - Posições de início e fim dos genes dentro dos genomas");
			System.out.println("3 - Sequência de bases dos genes");
			System.out.println("4 - Sequência de bases dos genes agrupadas em códons");
			System.out.println("5 - Sequência de aminoácidos correspondente à sequência de códons indicada");
			System.out.println("6 - Sequência “correta” de aminoácidos");
			System.out.println("0 - Sair\n");

			System.out.print("Selecione a opção desejada: ");
			menu(in.nextInt());

		
		}
	}






	
}
