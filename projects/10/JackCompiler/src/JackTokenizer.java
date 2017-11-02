import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

enum TokenType {
	KEYWORD,
	SYMBOL,
	INT_CONSTANT,
	STR_CONSTANT,
	IDENTIFIER,
	NULL
}

enum Keyword {
	CLASS, CONSTRUCTOR, FUNCTION, METHOD, 
	FIELD, STATIC, VAR, INT, CHAR, BOOLEAN, VOID, 
	TRUE, FALSE, NULL, THIS, 
	LET, DO, IF, ELSE, WHILE, RETURN 
}

enum Type {
	INT, CHAR, BOOLEAN, CLASS
}

public class JackTokenizer {
	public HashMap<String, Keyword> keywordsMap = new HashMap<String, Keyword>();
	public HashMap<TokenType, String> tokenTypesMap = new HashMap<TokenType, String>();
	
	public List<String> symbolsList = new ArrayList<String>();
	public HashMap<String, String> symbolHtmlEntitesMap = new HashMap<String, String>();
	
	public List<String> identifiersList = new ArrayList<String>();
	
	public HashMap<String, TokenType> tokensMap = new HashMap<String, TokenType>();
	public List<Token> tokensList = new ArrayList<Token>();
	
	private String currentToken = "";
	
	public JackTokenizer() {
		symbolsList.add("("); 
		symbolsList.add(")"); 
		symbolsList.add("{"); 
		symbolsList.add("}"); 
		symbolsList.add("[");
		symbolsList.add("]");
		symbolsList.add(".");
		symbolsList.add(",");
		symbolsList.add(";");
		symbolsList.add("+");
		symbolsList.add("-");
		symbolsList.add("*");
		symbolsList.add("/");
		symbolsList.add("&");
		symbolsList.add("|");
		symbolsList.add("<");
		symbolsList.add(">");
		symbolsList.add("=");
		symbolsList.add("~");
		
		identifiersList.add("Array");
		
		symbolHtmlEntitesMap.put("<", "&lt;");
		symbolHtmlEntitesMap.put(">", "&gt;");
		symbolHtmlEntitesMap.put("&", "&amp;");
		// symbolHtmlEntitesMap.put("|", "&vert;");
		
		keywordsMap.put("class", Keyword.CLASS);
		keywordsMap.put("constructor", Keyword.CONSTRUCTOR);
		keywordsMap.put("function", Keyword.FUNCTION);
		keywordsMap.put("method", Keyword.METHOD);
		keywordsMap.put("field", Keyword.FIELD);
		keywordsMap.put("static", Keyword.STATIC);
		keywordsMap.put("var", Keyword.VAR);
		keywordsMap.put("int", Keyword.INT);
		keywordsMap.put("char", Keyword.CHAR);
		keywordsMap.put("boolean", Keyword.BOOLEAN);
		keywordsMap.put("void", Keyword.VOID);
		keywordsMap.put("true", Keyword.TRUE);
		keywordsMap.put("false", Keyword.FALSE);
		keywordsMap.put("null", Keyword.NULL);
		keywordsMap.put("this", Keyword.THIS);
		keywordsMap.put("let", Keyword.LET);
		keywordsMap.put("do", Keyword.DO);
		keywordsMap.put("if", Keyword.IF);
		keywordsMap.put("else", Keyword.ELSE);
		keywordsMap.put("while", Keyword.WHILE);
		keywordsMap.put("return", Keyword.RETURN);
		
		tokenTypesMap.put(TokenType.KEYWORD, "keyword");
		tokenTypesMap.put(TokenType.SYMBOL, "symbol");
		tokenTypesMap.put(TokenType.INT_CONSTANT, "integerConstant");
		tokenTypesMap.put(TokenType.STR_CONSTANT, "stringConstant");
		tokenTypesMap.put(TokenType.IDENTIFIER, "identifier");
	}
	
	public String tokenize(String input) {
		String str = "<tokens>\n";
		
		for (char ch : input.toCharArray()) {
			// System.out.println(currentToken);
			// System.out.println(checkTokenType(currentToken));
			
			if (currentToken.length() >= 1) {
				if (currentToken.replaceAll(" ", "").length() == 0) {
					currentToken = "";
				}
			}
			
			if (checkTokenType(""+ch) != TokenType.IDENTIFIER || checkTokenType(currentToken) != TokenType.IDENTIFIER || checkEndOfIdentifier(currentToken)) {
				// tokensMap.put(currentToken, checkTokenType(currentToken));
				
				if (!currentToken.equals("")  && checkCurrentAndNextToken(""+ch, currentToken)) {
					str += flushCurrentToken();
					
					if ((""+ch).equals(" ")) {
						continue;
					}
				}
			}
			
			currentToken += ch;
			
			/* if (!currentToken.equals("")) {
				int startIndex = currentToken.indexOf(" ");
				int endIndex = currentToken.lastIndexOf(" ");
				
				if (startIndex == 0 && endIndex >= 0 && endIndex == currentToken.length()-1) {
					currentToken = currentToken.substring(startIndex, endIndex);
				}
			} */
			
		}
		
		if (!currentToken.equals("") && !currentToken.equals(" ")) {
			str += flushCurrentToken();
		}
		
		str += "</tokens>";
		
		return str;
	}
	
	private String flushCurrentToken() {
		Token token = new Token();
		token.type = checkTokenType(currentToken);
		
		if (token.type == TokenType.STR_CONSTANT) { // If token type is a string constant.
			int startIndex = currentToken.indexOf("\"");
			int endIndex = currentToken.lastIndexOf("\"");
			
			// Get the content between the quotes.
			if (startIndex == 0 && endIndex > 0 && endIndex == currentToken.length()-1) {
				currentToken = currentToken.substring(startIndex+1, endIndex);
			}
		}
		
		if (token.type == TokenType.IDENTIFIER) { // If token type is a identifier.
			if (currentToken.charAt(currentToken.length()-1) == " ".charAt(0)) { // If the last character is " ".
				currentToken = currentToken.substring(0, currentToken.length()-1); // Get the content between before the " ".
			}
		}
		
		if (token.type == TokenType.SYMBOL) {
			String entity = symbolHtmlEntitesMap.get(currentToken);
			
			if (entity != null) {
				currentToken = entity;
			}
		}
		
		token.value = currentToken;
		tokensList.add(token);
		
		String str = generateXml(currentToken, token.type);
		
		currentToken = "";
		
		return str;
	}
	
	private String generateXml(String token, TokenType type) {
		String str = "";
		
		String typeStr = tokenTypesMap.get(type);
		
		str += "<" + typeStr + "> " +  token + " </" + typeStr + ">\n";
		
		return str;
	}
	
	private TokenType checkTokenType(String str) {
		TokenType type = TokenType.IDENTIFIER;
		
		if (keywordsMap.get(str) != null) {
			return TokenType.KEYWORD;
		} else if (symbolsList.contains(str)) {
			return TokenType.SYMBOL;
		}
		
		try {
			int num = Integer.parseInt(str);
			
			if (num >= 0 && num <= 32767) {
				return TokenType.INT_CONSTANT;
			}
		} catch (NumberFormatException e) {
			// e.printStackTrace();
		}
		
		int startIndex = str.indexOf("\"");
		int endIndex = str.lastIndexOf("\"");
		
		if (startIndex == 0 && endIndex > 0 && endIndex == str.length()-1) {
			return TokenType.STR_CONSTANT;
		}
		
		return type;
	}
	
	private boolean checkEndOfIdentifier(String str) {
		if (!str.equals("") && !str.contains("\"")) {
			int startIndex = str.indexOf(" ");
			int endIndex = str.lastIndexOf(" ");
			
			if (endIndex >= 0 && endIndex == str.length()-1) {
				return true;
			}
		}
		
		return false;
	}
	
	private boolean checkCurrentAndNextToken(String next, String current) {
		TokenType nextTokenType = checkTokenType(next);
		TokenType currentTokenType = checkTokenType(current);
		if (currentTokenType == TokenType.INT_CONSTANT && nextTokenType == TokenType.INT_CONSTANT) {
				return false;
		}
		
		return true;
	}
}
