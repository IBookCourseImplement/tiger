package lexer;

public class Token {
    // Lab 1, exercise 1: read the MiniJava specification
    // carefully, and answer these two questions:
    //   1. whether or not one should add other token kinds?
    //   2. which tokens come with an extra "lexeme", and
    //      which don't?
    // It's highly recommended that these token names are
    // alphabetically ordered, if you add new ones.
    public enum Kind {
        TOKEN_ADD, // "+"
        TOKEN_AND, // "&&"
        TOKEN_ASSIGN, // "="
        TOKEN_BOOLEAN, // "boolean",
        TOKEN_CLASS, // "class"
        TOKEN_COMMA, // ","
        TOKEN_DOT, // ".",
        TOKEN_DIV, // "/",
        TOKEN_ELSE, // "else"
        TOKEN_EOF, // EOF
        TOKEN_EXTENDS, // "extends"
        TOKEN_FALSE, // "false"
        TOKEN_ID, // Identifier
        TOKEN_IF, // "if"
        TOKEN_INT, // "int"
        TOKEN_LBRACE, // "{"
        TOKEN_LBRACKET, // "["
        TOKEN_LENGTH, // "length"
        TOKEN_LPAREN, // "("
        TOKEN_LT, // "<"
        TOKEN_MAIN, // "main"
        TOKEN_NEW, // "new"
        TOKEN_NOT, // "!"
        TOKEN_NULL, // "null"
        TOKEN_NUM, // IntegerLiteral
        // "out" is not a Java key word, but we treat it as
        // a MiniJava keyword, which will make the
        // compilation a little easier. Similar cases apply
        // for "println", "System" and "String".
        TOKEN_OUT, // "out"
        TOKEN_PRINTLN, // "println"
        TOKEN_PRIVATE, // "private"
        TOKEN_PUBLIC, // "public"
        TOKEN_RBRACE, // "}"
        TOKEN_RBRACKET, // "]"
        TOKEN_RETURN, // "return"
        TOKEN_RPAREN, // ")"
        TOKEN_SEMI, // ";"
        TOKEN_STATIC, // "static"
        TOKEN_STRING, // "String"
        TOKEN_SUB, // "-"
        TOKEN_SYSTEM, // "System"
        TOKEN_THIS, // "this"
        TOKEN_TIMES, // "*"
        TOKEN_TRUE, // "true"
        TOKEN_VOID, // "void"
        TOKEN_WHILE, // "while"
    }

    public Kind kind; // kind of the token
    public String lexeme; // extra lexeme for this token, if any
    public Integer lineNum; // on which line of the source file this token appears

    public Integer lineCol;

    // Some tokens don't come with lexeme but
    // others do.
    public Token(Kind kind, Integer lineNum, Integer lineCol) {
        this.kind = kind;
        this.lineNum = lineNum;
        this.lineCol = lineCol;
    }

    public Token(Kind kind, Integer lineNum, Integer lineCol, String lexeme) {
        this(kind, lineNum, lineCol);
        this.lexeme = lexeme;
    }

    @Override
    public String toString() {
        String s;

        // to check that the "lineNum" field has been properly set.
        if (this.lineNum == null) new util.Todo();

        s = ": " + ((this.lexeme == null) ? "<NONE>" : this.lexeme) + " : at line " + this.lineNum.toString() + " index: " + this.lineCol.toString();
        return this.kind.toString() + s;
    }
}
