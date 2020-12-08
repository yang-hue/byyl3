package miniplc0java.tokenizer;

import miniplc0java.error.TokenizeError;
import miniplc0java.error.ErrorCode;
import miniplc0java.util.Pos;

public class Tokenizer {

    private StringIter it;

    public Tokenizer(StringIter it) {
        this.it = it;
    }

    // 这里本来是想实现 Iterator<Token> 的，但是 Iterator 不允许抛异常，于是就这样了
    /**
     * 获取下一个 Token
     * 
     * @return
     * @throws TokenizeError 如果解析有异常则抛出
     */
    public Token nextToken() throws TokenizeError {
        it.readAll();

        // 跳过之前的所有空白字符
        skipSpaceCharacters();

        if (it.isEOF()) {
            return new Token(TokenType.EOF, "", it.currentPos(), it.currentPos());
        }

        char peek = it.peekChar();
        if (Character.isDigit(peek)) {
            return lexUInt();
        } else if (peek=='_'||(peek<='z'&&peek>='a')||(peek<='Z'&&peek>='A')) {
            return lexIdentOrKeyword();
        }
        else if(peek=='"') {
            return lexStringLiteral();
        }
        else {
            return lexOperatorOrUnknown();
        }
    }

    private Token lexUInt() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字为止:
        // -- 前进一个字符，并存储这个字符
        StringBuffer s = new StringBuffer();
        Pos spos=it.currentPos();
        Pos epos=it.nextPos();
        while(it.peekChar()<='9'&&it.peekChar()>='0'){
            s.append(it.nextChar());
        }
        epos=it.currentPos();
        return new Token(TokenType.digit,Integer.parseInt(s.toString()),spos,epos);
        //
        // 解析存储的字符串为无符号整数
        // 解析成功则返回无符号整数类型的token，否则返回编译错误
        //
        // Token 的 Value 应填写数字的值
        //throw new Error("Not implemented");
    }

    private Token lexStringLiteral() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        StringBuffer s = new StringBuffer();
        Pos spos=it.currentPos();
        Pos epos=it.nextPos();
        it.nextChar();
        while(it.peekChar()!='"'){
            s.append(it.nextChar());
        }
        it.nextChar();
        epos=it.currentPos();
        String str=s.toString();
        return new Token(TokenType.STRING_LITERAL,str,spos,epos);
    }

    private Token lexIdentOrKeyword() throws TokenizeError {
        // 请填空：
        // 直到查看下一个字符不是数字或字母为止:
        // -- 前进一个字符，并存储这个字符
        StringBuffer s = new StringBuffer();
        Pos spos=it.currentPos();
        Pos epos=it.nextPos();
        while(it.peekChar()=='_'||(it.peekChar()<='9'&&it.peekChar()>='0')||(it.peekChar()<='Z'&&it.peekChar()>='A')||(it.peekChar()<='z'&&it.peekChar()>='a')){
            s.append(it.nextChar());
        }
        epos=it.currentPos();
        String str=s.toString();
        if(str.equals("fn")){
            return new Token(TokenType.FN_KW,null,spos,epos);
        }
        else if(str.equals("let")){
            return new Token(TokenType.LET_KW,null,spos,epos);
        }
        else if(str.equals("const")){
            return new Token(TokenType.CONST_KW,null,spos,epos);
        }
        else if(str.equals("as")){
            return new Token(TokenType.AS_KW,null,spos,epos);
        }
        else if(str.equals("while")){
            return new Token(TokenType.WHILE_KW,null,spos,epos);
        }
        else if(str.equals("if")){
            return new Token(TokenType.IF_KW,null,spos,epos);
        }
        else if(str.equals("else")){
            return new Token(TokenType.ELSE_KW,null,spos,epos);
        }
        else if(str.equals("return")){
            return new Token(TokenType.RETURN_KW,null,spos,epos);
        }
        else if(str.equals("break")){
            return new Token(TokenType.BREAK_KW,null,spos,epos);
        }
        else if(str.equals("continue")){
            return new Token(TokenType.CONTINUE_KW,null,spos,epos);
        }
        else{
            return new Token(TokenType.Ident,str,spos,epos);
        }
        // 尝试将存储的字符串解释为关键字
        // -- 如果是关键字，则返回关键字类型的 token
        // -- 否则，返回标识符
        //
        // Token 的 Value 应填写标识符或关键字的字符串
    }

    private Token lexOperatorOrUnknown() throws TokenizeError {
        Pos spos=it.currentPos();
        Pos epos=it.nextPos();
        char temp=it.nextChar();
        if(temp=='='){
            if(it.peekChar()=='='){
                it.nextChar();
                epos=it.currentPos();
                return new Token(TokenType.EQ,"==",spos,epos);
            }
        }
        else if(temp=='!'){
            if(it.peekChar()=='='){
                it.nextChar();
                epos=it.currentPos();
                return new Token(TokenType.NEQ,"!=",spos,epos);
            }
        }
        else if(temp=='<'){
            if(it.peekChar()=='='){
                it.nextChar();
                epos=it.currentPos();
                return new Token(TokenType.LE,"<=",spos,epos);
            }
        }
        else if(temp=='>'){
            if(it.peekChar()=='='){
                it.nextChar();
                epos=it.currentPos();
                return new Token(TokenType.GE,">=",spos,epos);
            }
        }
        else if(temp=='-'){
            if(it.peekChar()=='>'){
                it.nextChar();
                epos=it.currentPos();
                return new Token(TokenType.ARROW,"->",spos,epos);
            }
        }
        switch (temp) {
            case '+':
                return new Token(TokenType.PLUS, '+', it.previousPos(), it.currentPos());
            case '-':
                // 填入返回语句
                return new Token(TokenType.MINUS, '-', it.previousPos(), it.currentPos());
            case '*':
                // 填入返回语句
                return new Token(TokenType.MUL, '*', it.previousPos(), it.currentPos());
            case '/':
                // 填入返回语句
                return new Token(TokenType.DIV, '/', it.previousPos(), it.currentPos());
            // 填入更多状态和返回语句
            case '=':
                return new Token(TokenType.ASSIGN, '=', it.previousPos(), it.currentPos());
            case '<':
                return new Token(TokenType.LT, '<', it.previousPos(), it.currentPos());
            case '>':
                return new Token(TokenType.GT, '>', it.previousPos(), it.currentPos());
            case '(':
                return new Token(TokenType.L_PAREN, '(', it.previousPos(), it.currentPos());
            case ')':
                return new Token(TokenType.R_PAREN, ')', it.previousPos(), it.currentPos());
            case '{':
                return new Token(TokenType.R_BRACE, '{', it.previousPos(), it.currentPos());
            case '}':
                return new Token(TokenType.L_BRACE, '}', it.previousPos(), it.currentPos());
            case ',':
                return new Token(TokenType.COMMA, ',', it.previousPos(), it.currentPos());
            case ':':
                return new Token(TokenType.COLON, ':', it.previousPos(), it.currentPos());
            case ';':
                return new Token(TokenType.SEMICOLON, ';', it.previousPos(), it.currentPos());
            default:
                // 不认识这个输入，摸了
                throw new TokenizeError(ErrorCode.InvalidInput, it.previousPos());
        }
    }

    private void skipSpaceCharacters() {
        while (!it.isEOF() && Character.isWhitespace(it.peekChar())) {
            it.nextChar();
        }
    }
}