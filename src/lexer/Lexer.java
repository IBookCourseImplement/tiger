package lexer;

import static control.Control.ConLexer.dump;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import lexer.Token.Kind;
import util.Bug;
import util.Todo;

public class Lexer {
    String fname; // the input file name to be compiled
    InputStream fstream; // input stream for the above file
    int lineNum = 1;
    int lineIndex = 0;
    static final Map<String, Token.Kind> tokenMapper = new HashMap<>() {{
        put("boolean", Kind.TOKEN_BOOLEAN);
        put("class", Kind.TOKEN_CLASS);
        put("else", Kind.TOKEN_ELSE);
        put("extends", Kind.TOKEN_EXTENDS);
        put("false", Kind.TOKEN_FALSE);
        put("if", Kind.TOKEN_IF);
        put("int", Kind.TOKEN_INT);
        put("length", Kind.TOKEN_LENGTH);
        put("main", Kind.TOKEN_MAIN);
        put("new", Kind.TOKEN_NEW);
        put("null", Kind.TOKEN_NULL);
        put("out", Kind.TOKEN_OUT);
        put("println", Kind.TOKEN_PRINTLN);
        put("public", Kind.TOKEN_PUBLIC);
        put("return", Kind.TOKEN_RETURN);
        put("static", Kind.TOKEN_STATIC);
        put("String", Kind.TOKEN_STRING);
        put("System", Kind.TOKEN_SYSTEM);
        put("this", Kind.TOKEN_THIS);
        put("true", Kind.TOKEN_TRUE);
        put("void", Kind.TOKEN_VOID);
        put("while", Kind.TOKEN_WHILE);
    }};

    public Lexer(String fname, InputStream fstream) {
        this.fname = fname;
        this.fstream = fstream;
    }

    // When called, return the next token (refer to the code "Token.java")
    // from the input stream.
    // Return TOKEN_EOF when reaching the end of the input stream.
    private Token nextTokenInternal() throws Exception {
        int c = this.fstream.read();
        lineIndex++;
        if (-1 == c)
            // The value for "lineNum" is now "null",
            // you should modify this to an appropriate
            // line number for the "EOF" token.
            return new Token(Kind.TOKEN_EOF, lineNum, lineIndex);

        // skip all kinds of "blanks"
        while (' ' == c || '\t' == c || '\n' == c) {
            if (c == '\n') {
                lineNum++;
                lineIndex = 0;
            }
            c = this.fstream.read();
            lineIndex++;
        }

        if (-1 == c) return new Token(Kind.TOKEN_EOF, lineNum, lineIndex);

        switch (c) {
            // below branches are for the "special characters"
            case '+':
                return new Token(Kind.TOKEN_ADD, lineNum, lineIndex);
            case '-':
                return new Token(Kind.TOKEN_SUB, lineNum, lineIndex);
            case '*':
                return new Token(Kind.TOKEN_TIMES, lineNum, lineIndex);
            case '/':
                return new Token(Kind.TOKEN_DIV, lineNum, lineIndex);
            case '=':
                return new Token(Kind.TOKEN_ASSIGN, lineNum, lineIndex);
            case ',':
                return new Token(Kind.TOKEN_COMMA, lineNum, lineIndex);
            case '.':
                return new Token(Kind.TOKEN_DOT, lineNum, lineIndex);
            case '{':
                return new Token(Kind.TOKEN_LBRACE, lineNum, lineIndex);
            case '[':
                return new Token(Kind.TOKEN_LBRACK, lineNum, lineIndex);
            case '(':
                return new Token(Kind.TOKEN_LPAREN, lineNum, lineIndex);
            case '<':
                return new Token(Kind.TOKEN_LT, lineNum, lineIndex);
            case '!':
                return new Token(Kind.TOKEN_NOT, lineNum, lineIndex);
            case ')':
                return new Token(Kind.TOKEN_RPAREN, lineNum, lineIndex);
            case '}':
                return new Token(Kind.TOKEN_RBRACE, lineNum, lineIndex);
            case ']':
                return new Token(Kind.TOKEN_RBRACK, lineNum, lineIndex);
            case ';':
                return new Token(Kind.TOKEN_SEMI, lineNum, lineIndex);
            case '&':
                int tmp = lineIndex;
                this.fstream.mark(1);
                c = this.fstream.read();
                lineIndex++;
                if (c == '&') {
                    return new Token(Kind.TOKEN_AND, lineNum, tmp);
                } else {
                    // TODO: need judge or error handle
                    this.fstream.reset();
                    lineIndex--;
                }
            default:
                // Lab 1, exercise 2: supply missing code to
                // lex other kinds of tokens.
                // Less than 50 lines, Please!
                // Below is used to lex identifiers, numbers, and keywords.
                if (Character.isLetter(c) || c == '_') {
                    tmp = lineIndex;
                    StringBuilder sb = new StringBuilder();
                    sb.append((char) c);
                    c = this.fstream.read();
                    lineIndex++;
                    // TODO: 区别 '-' 语义： sub symbol and id definition
                    while (Character.isLetterOrDigit(c) || c == '_') {
                        sb.append((char) c);
                        c = this.fstream.read();
                        lineIndex++;
                    }
                    // 待测试 这里应该需要换行判断
                    if (c == '\n') {
                        lineNum++;
                        lineIndex = 0;
                    }
                    final var s = sb.toString();
                    if (tokenMapper.containsKey(s)) {
                        return new Token(tokenMapper.get(s), lineNum, tmp);  // return the token
                    } else {
                        return new Token(Kind.TOKEN_ID, lineNum, tmp, s);
                    }
                } else if (Character.isDigit(c)) {
                    // lexer number
                    tmp = lineIndex;
                    StringBuilder sb = new StringBuilder();
                    sb.append((char) c);
                    c = this.fstream.read();
                    lineIndex++;
                    while (Character.isDigit(c)) {
                        sb.append((char) c);
                        c = this.fstream.read();
                        lineIndex++;
                    }
                    // 待测试 这里应该需要换行判断
                    if (c == '\n') {
                        lineNum++;
                        lineIndex = 0;
                    }
                    return new Token(Kind.TOKEN_NUM, lineNum, tmp, sb.toString());
                }
                return null;
        }
    }

    public Token nextToken() {
        Token t = null;

        try {
            t = this.nextTokenInternal();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (dump) {
            assert t != null;
            System.out.println(t.toString());
        }
        return t;
    }
}
