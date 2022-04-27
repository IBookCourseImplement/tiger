package lexer;

import static control.Control.ConLexer.dump;

import java.io.InputStream;

import lexer.Token.Kind;
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
            default:
                // Lab 1, exercise 2: supply missing code to
                // lex other kinds of tokens.
                // Hint: think carefully about the basic
                // data structure and algorithms. The code
                // is not that much and may be less than 50 lines. If you
                // find you are writing a lot of code, you
                // are on the wrong way.
                new Todo();
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
