package pkg_genoma;

import java.util.List;

public class Gene {
    private String locus;
    private long begin;
    private long end;
    private List<Character> bases;
	
    public Gene(String locus, long begin, long end, List<Character> bases) {
		super();
		this.locus = locus;
		this.begin = begin;
		this.end = end;
		this.bases = bases;
	}

	public String getLocus() {
		return locus;
	}

	public long getBegin() {
		return begin;
	}

	public long getEnd() {
		return end;
	}

	public List<Character> getBases() {
		return bases;
	}

	@Override
	public String toString() {
		return "Gene [locus=" + locus + ", begin=" + begin + ", end=" + end + ", bases=" + bases + "]";
	}    
}
