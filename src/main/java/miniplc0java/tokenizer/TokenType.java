package miniplc0java.tokenizer;

public enum TokenType {
    /** 空 */
    FN_KW,
    /** 无符号整数 */
    LET_KW,
    /** 标识符 */
    CONST_KW,
    /** Begin */
    AS_KW,
    /** End */
    WHILE_KW,
    /** Var */
    IF_KW,
    /** Const */
    ELSE_KW,
    /** Print */
    RETURN_KW,
    /** 加号 */
    BREAK_KW,
    /** 减号 */
    CONTINUE_KW,
    /** 乘号 */
    digit,
    /** 除号 */
    UINT_LITERAL,
    /** 等号 */
    STRING_LITERAL,
    /** 右括号 */
    DOUBLE_LITERAL,
    /** 文件尾 */
    char_regular_char,
    CHAR_LITERAL,
    IDENT,
    PLUS,
    MINUS,
    MUL,
    DIV,
    ASSIGN,
    EQ,
    NEQ,
    LT,
    GT,
    LE,
    GE,
    L_PAREN,
    R_PAREN,
    L_BRACE,
    R_BRACE,
    ARROW,
    COMMA,
    COLON,
    SEMICOLON,
    COMMENT
    ;

    @Override
    public String toString() {
        switch (this) {
            case None:
                return "NullToken";
            case Begin:
                return "Begin";
            case Const:
                return "Const";
            case Div:
                return "DivisionSign";
            case EOF:
                return "EOF";
            case End:
                return "End";
            case Equal:
                return "EqualSign";
            case Ident:
                return "Identifier";
            case LParen:
                return "LeftBracket";
            case Minus:
                return "MinusSign";
            case Mult:
                return "MultiplicationSign";
            case Plus:
                return "PlusSign";
            case Print:
                return "Print";
            case RParen:
                return "RightBracket";
            case Semicolon:
                return "Semicolon";
            case Uint:
                return "UnsignedInteger";
            case Var:
                return "Var";
            default:
                return "InvalidToken";
        }
    }
}
