package lexer;

import static control.Control.ConLexer.dump;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import lexer.Token.Kind;

public class Lexer {
    String fname; // the input file name to be compiled
    InputStream fstream; // input stream for the above file
    int lineNum = 1;
    int lineCol = 0;
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
        put("private", Kind.TOKEN_PRIVATE);
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

    private void newLineTCAndSpaceCheck(int c) {
        if ('\n' == c) {
            lineNum++;
            lineCol = 0;
        } else if ('\t' == c) {
            lineCol += 1;
        } else if (' ' == c) {
            lineCol++;
        }
    }

    // When called, return the next token (refer to the code "Token.java")
    // from the input stream.
    // Return TOKEN_EOF when reaching the end of the input stream.
    private Token nextTokenInternal() throws Exception {
        int c = this.fstream.read();
        lineCol++;
        if (-1 == c)
            // The value for "lineNum" is now "null",
            // you should modify this to an appropriate
            // line number for the "EOF" token.
            return new Token(Kind.TOKEN_EOF, lineNum, lineCol);

        // skip all kinds of "blanks"
        while (' ' == c || '\t' == c || '\n' == c) {
            newLineTCAndSpaceCheck(c);
            c = this.fstream.read();
            lineCol++;
        }

        if (-1 == c) return new Token(Kind.TOKEN_EOF, lineNum, lineCol);

        switch (c) {
            // below branches are for the "special characters"
            case '+':
                return new Token(Kind.TOKEN_ADD, lineNum, lineCol);
            case '-':
                return new Token(Kind.TOKEN_SUB, lineNum, lineCol);
            case '*':
                return new Token(Kind.TOKEN_TIMES, lineNum, lineCol);
            case '/':
                return new Token(Kind.TOKEN_DIV, lineNum, lineCol);
            case '=':
                return new Token(Kind.TOKEN_ASSIGN, lineNum, lineCol);
            case ',':
                return new Token(Kind.TOKEN_COMMA, lineNum, lineCol);
            case '.':
                return new Token(Kind.TOKEN_DOT, lineNum, lineCol);
            case '{':
                return new Token(Kind.TOKEN_LBRACE, lineNum, lineCol);
            case '[':
                return new Token(Kind.TOKEN_LBRACKET, lineNum, lineCol);
            case '(':
                return new Token(Kind.TOKEN_LPAREN, lineNum, lineCol);
            case '<':
                return new Token(Kind.TOKEN_LT, lineNum, lineCol);
            case '!':
                return new Token(Kind.TOKEN_NOT, lineNum, lineCol);
            case ')':
                return new Token(Kind.TOKEN_RPAREN, lineNum, lineCol);
            case '}':
                return new Token(Kind.TOKEN_RBRACE, lineNum, lineCol);
            case ']':
                return new Token(Kind.TOKEN_RBRACKET, lineNum, lineCol);
            case ';':
                return new Token(Kind.TOKEN_SEMI, lineNum, lineCol);
            case '&':
                int tmp = lineCol;
                this.fstream.mark(1);
                c = this.fstream.read();
                lineCol++;
                if (c == '&') {
                    return new Token(Kind.TOKEN_AND, lineNum, tmp);
                } else {
                    this.fstream.reset();
                    lineCol--;
                }
            default:
                // Lab 1, exercise 2: supply missing code to
                // lex other kinds of tokens.
                // Less than 50 lines, Please!
                // Below is used to lex identifiers, numbers, and keywords.
                // support snake_case variable
                if (Character.isLetter(c) || c == '_') {
                    tmp = lineCol;
                    StringBuilder sb = new StringBuilder();
                    sb.append((char) c);
                    c = this.fstream.read();
                    lineCol++;
                    // TODO: 区别 '-' 语义： sub symbol and id definition
                    while (Character.isLetterOrDigit(c) || c == '_') {
                        fstream.mark(1);
                        sb.append((char) c);
                        c = this.fstream.read();
                        lineCol++;
                    }
                    fstream.reset();
                    // 待测试 这里应该需要换行判断
                    newLineTCAndSpaceCheck(c);
                    final var s = sb.toString();
                    if (tokenMapper.containsKey(s)) {
                        return new Token(tokenMapper.get(s), lineNum, tmp);  // return the token
                    } else {
                        return new Token(Kind.TOKEN_ID, lineNum, tmp, s);
                    }
                } else if (Character.isDigit(c)) {
                    // lexer number
                    tmp = lineCol;
                    StringBuilder sb = new StringBuilder();
                    sb.append((char) c);
                    c = this.fstream.read();
                    lineCol++;
                    while (Character.isDigit(c)) {
                        fstream.mark(1);
                        sb.append((char) c);
                        c = this.fstream.read();
                        lineCol++;
                    }
                    fstream.reset();
                    // 待测试 这里应该需要换行判断
                    newLineTCAndSpaceCheck(c);
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
