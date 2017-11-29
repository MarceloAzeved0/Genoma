package pkg_genoma;

import java.util.HashMap;
import java.util.Map;

public class AminoacidTable {
	private static AminoacidTable este = null;
	private Map<String, String> aminoacidTable;
	
	public AminoacidTable() {
		aminoacidTable = new HashMap<String, String>();
        populateAminoacidTable();
	}

	public static AminoacidTable getInstance() {
		if (este == null) {
			este = new AminoacidTable();
		}
		return este;
	}
	
	public String getAminoacid(String codon){
        return aminoacidTable.get(codon);
	}
	
	private void populateAminoacidTable() {
		aminoacidTable.put("TTT", "F");// Phe
		aminoacidTable.put("TTC", "F");

		aminoacidTable.put("TTA", "L");// Leu
		aminoacidTable.put("TTG", "L");

		aminoacidTable.put("CTT", "L");// Leu
		aminoacidTable.put("CTC", "L");
		aminoacidTable.put("CTA", "L");
		aminoacidTable.put("CTG", "L");

		aminoacidTable.put("ATT", "I"); // Ile
		aminoacidTable.put("ATC", "I");
		aminoacidTable.put("ATA", "I");

		aminoacidTable.put("ATG", "Met");

		aminoacidTable.put("GTT", "V");// Val
		aminoacidTable.put("GTC", "V");
		aminoacidTable.put("GTA", "V");
		aminoacidTable.put("GTG", "V");

		aminoacidTable.put("TCT", "S"); // Ser
		aminoacidTable.put("TCC", "S");
		aminoacidTable.put("TCA", "S");
		aminoacidTable.put("TCG", "S");

		aminoacidTable.put("CCT", "P"); // Pro
		aminoacidTable.put("CCC", "P");
		aminoacidTable.put("CCA", "P");
		aminoacidTable.put("CCG", "P");

		aminoacidTable.put("ACT", "T"); // Thr
		aminoacidTable.put("ACC", "T");
		aminoacidTable.put("ACA", "T");
		aminoacidTable.put("ACG", "T");

		aminoacidTable.put("GCT", "A"); // Ala
		aminoacidTable.put("GCC", "A");
		aminoacidTable.put("GCA", "A");
		aminoacidTable.put("GCG", "A");

		aminoacidTable.put("TAT", "Y"); // Tyr
		aminoacidTable.put("TAC", "Y");

		aminoacidTable.put("TAA", "Stop");
		aminoacidTable.put("TAG", "Stop");

		aminoacidTable.put("CAT", "H"); // His
		aminoacidTable.put("CAC", "H");

		aminoacidTable.put("CAA", "Q"); // Gln
		aminoacidTable.put("CAG", "Q");

		aminoacidTable.put("AAT", "N");// Asn
		aminoacidTable.put("AAC", "N");

		aminoacidTable.put("AAA", "K");// Lys
		aminoacidTable.put("AAG", "K");

		aminoacidTable.put("GAT", "D");// Asp
		aminoacidTable.put("GAC", "D");

		aminoacidTable.put("GAA", "E"); // Glu
		aminoacidTable.put("GAG", "E");

		aminoacidTable.put("TGT", "C"); // Cys
		aminoacidTable.put("TGC", "C");

		aminoacidTable.put("TGA", "Stop");

		aminoacidTable.put("TGG", "W"); // Trp

		aminoacidTable.put("CGT", "R"); // Arg
		aminoacidTable.put("CGC", "R");
		aminoacidTable.put("CGA", "R");
		aminoacidTable.put("CGG", "R");

		aminoacidTable.put("AGT", "S"); // Ser
		aminoacidTable.put("AGC", "S");

		aminoacidTable.put("AGA", "R"); // Arg
		aminoacidTable.put("AGG", "R");

		aminoacidTable.put("GGT", "G"); // Gly
		aminoacidTable.put("GGC", "G");
		aminoacidTable.put("GGA", "G");
		aminoacidTable.put("GGG", "G");
	}
}
