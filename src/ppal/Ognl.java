package ppal;


import org.ognl.el.Environment;
import org.ognl.el.ExecutionEnvironment;
import org.ognl.el.Expression;
import org.ognl.el.ExpressionSyntaxException;
import org.ognl.el.extensions.DefaultExecutionEnvironment;

public class Ognl {
    
    private Ognl() {
        env = context = new DefaultExecutionEnvironment();
    }
    
    public static Ognl getInstancia() {
        return ognl;
    }
    
    public Expression getExpression(String exp) throws ExpressionSyntaxException {
        return env.parseExpression(exp);
    }
    
    public Object findValue(Expression exp, Object obj) throws Exception {
        return context.getValue(exp, obj);
    }
    
    public Object findValue(Expression exp, Object obj, Class clase) throws Exception {
        return context.getValue(exp, obj, clase);
    }
    
    public Object findValue(String exp, Object obj) throws Exception {
        return context.getValue(env.parseExpression(exp), obj);
    }
    
    public Object findValue(String exp, Object obj, Class clase) throws Exception {
        return context.getValue(env.parseExpression(exp), obj, clase);
    }
    
    public void setValue(String exp, Object destino, Object fuente) throws Exception {
        context.setValue(getExpression(exp), destino, fuente);
    }
    
       
    private static Ognl ognl = new Ognl();
    protected Environment env;
    protected ExecutionEnvironment context;
}
