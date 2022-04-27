package lexer;

import static control.Control.ConLexer.dump;

import java.io.InputStream;

import lexer.Token.Kind;
import util.Bug;
import util.Todo;

public class Lexer {
    String fname; // the input file name to be compiled
    InputStream fstream; // input stream for the above file
    int lineNum = 1;

    public Lexer(String fname, InputStream fstream) {
        this.fname = fname;
        this.fstream = fstream;
    }

    // When called, return the next token (refer to the code "Token.java")
    // from the input stream.
    // Return TOKEN_EOF when reaching the end of the input stream.
    private Token nextTokenInternal() throws Exception {
        int c = this.fstream.read();
        if (-1 == c)
            // The value for "lineNum" is now "null",
            // you should modify this to an appropriate
            // line number for the "EOF" token.
            return new Token(Kind.TOKEN_EOF, lineNum);

        // skip all kinds of "blanks"
        while (' ' == c || '\t' == c || '\n' == c) {
            if (c == '\n') lineNum++;
            c = this.fstream.read();
        }

        if (-1 == c) return new Token(Kind.TOKEN_EOF, lineNum);

        switch (c) {
            // below branches are for the "special characters"
            case '+':
                return new Token(Kind.TOKEN_ADD, lineNum);
            case '-':
                return new Token(Kind.TOKEN_SUB, lineNum);
            case '*':
                return new Token(Kind.TOKEN_TIMES, lineNum);
            case '/':
                return new Token(Kind.TOKEN_DIV, lineNum);
            case '=':
                return new Token(Kind.TOKEN_ASSIGN, lineNum);
            case ',':
                return new Token(Kind.TOKEN_COMMA, lineNum);
            case '.':
                return new Token(Kind.TOKEN_DOT, lineNum);
            case '{':
                return new Token(Kind.TOKEN_LBRACE, lineNum);
            case '[':
                return new Token(Kind.TOKEN_LBRACK, lineNum);
            case '(':
                return new Token(Kind.TOKEN_LPAREN, lineNum);
            case '<':
                return new Token(Kind.TOKEN_LT, lineNum);
            case '!':
                return new Token(Kind.TOKEN_NOT, lineNum);
            case ')':
                return new Token(Kind.TOKEN_RPAREN, lineNum);
            case '}':
                return new Token(Kind.TOKEN_RBRACE, lineNum);
            case ']':
                return new Token(Kind.TOKEN_RBRACK, lineNum);
            case ';':
                return new Token(Kind.TOKEN_SEMI, lineNum);
            case '&':
                this.fstream.mark(1);
                c = this.fstream.read();
                if (c == '&') {
                    return new Token(Kind.TOKEN_AND, lineNum);
                } else {
                    this.fstream.reset();
                    new Bug();
                }
            default:
                // Lab 1, exercise 2: supply missing code to
                // lex other kinds of tokens.
                // Less than 50 lines, Please!
                // Below is used to lex identifiers, numbers, and keywords.
                if (Character.isLetter(c)) {
                    StringBuilder sb = new StringBuilder();
                    sb.append((char) c);
                    c = this.fstream.read();
                    while (Character.isLetterOrDigit(c)) {
                        sb.append((char) c);
                        c = this.fstream.read();
                    }
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
