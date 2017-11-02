enum Kind {STATIC, FIELD, ARG, VAR, NONE}

public class Symbol {
	public String name = "";
	public String type;
	public Kind kind;
	public int index;
	
	public Symbol(String name, String type, Kind kind, int index) {
		this.name = name;
		this.type = type;
		this.kind = kind;
		this.index = index;
	}
}
