import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SymbolTable {
	private HashMap<String, Symbol> classSymbolsMap = new HashMap<String, Symbol>(); // For static and field variables.
	private HashMap<String, Symbol> subroutineSymbolsMap = new HashMap<String, Symbol>(); // For local and argument variables.
	
	private HashMap<Kind, Integer> indicesMap = new HashMap<Kind, Integer>();
	
	public SymbolTable() {
		indicesMap.put(Kind.FIELD, 0);
		indicesMap.put(Kind.STATIC, 0);
		indicesMap.put(Kind.VAR, 0);
		indicesMap.put(Kind.ARG, 0);
	}
	
	public void startSubroutine() {
		subroutineSymbolsMap.clear();
		indicesMap.put(Kind.VAR, 0);
		indicesMap.put(Kind.ARG, 0);
	}
	
	public void define(String name, String type, Kind kind) {
		int index = indicesMap.get(kind);
		Symbol symbol = new Symbol(name, type, kind, index);
		indicesMap.put(kind, index+1);
		
		if (kind == Kind.VAR || kind == Kind.ARG) {
			subroutineSymbolsMap.put(name, symbol);
		} else if (kind == Kind.FIELD || kind == Kind.STATIC) {
			classSymbolsMap.put(name, symbol);
		}
	}
	
	public int varCount(Kind kind) {
		return indicesMap.get(kind);
	}
	
	public Kind kindOf(String name) {
		Symbol symbol = lookUp(name);
		
		if (symbol != null) {
			return symbol.kind;
		}
		
		return Kind.NONE;
	}
	
	public String typeOf(String name) {
		Symbol symbol = lookUp(name);
		
		if (symbol != null) {
			return symbol.type;
		}
		
		return "";
	}
	
	public int indexOf(String name) {
		Symbol symbol = lookUp(name);
		
		if (symbol != null) {
			return symbol.index;
		}
		
		return -1;
	}
	
	private Symbol lookUp(String name) {
		if (classSymbolsMap.get(name) != null) {
			return classSymbolsMap.get(name);
		} else if (subroutineSymbolsMap.get(name) != null) {
			return subroutineSymbolsMap.get(name);
		} else {
			return null;
		}
	}
	
	public void printClassTable() {
		System.out.println("Field Count: " + varCount(Kind.FIELD));
		System.out.println("Static Count: " + varCount(Kind.STATIC));
		
		Iterator it = classSymbolsMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        Symbol symbol = (Symbol)pair.getValue();
	        
	        System.out.println("Name: " + symbol.name + " type: " + symbol.type + " kind: " + symbol.kind + " index: " + symbol.index);
	        // it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	public void printSubroutineTable() {
		System.out.println("Var Count: " + varCount(Kind.VAR));
		System.out.println("Argument Count: " + varCount(Kind.ARG));
		
		Iterator it = subroutineSymbolsMap.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        
	        Symbol symbol = (Symbol)pair.getValue();
	        
	        System.out.println("Name: " + symbol.name + " type: " + symbol.type + " kind: " + symbol.kind + " index: " + symbol.index);
	        // it.remove(); // avoids a ConcurrentModificationException
	    }
	}
	
	
}
