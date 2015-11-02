public class ActionData {
    private int a;
    private int b;
    private String fun;
    private int res;
    private String error;
    private String src;

    public static class Types {
        static final String Mul = "*";
        static final String Div = "/";
        static final String Minus = "-";
        static final String Plus = "+";

        public static boolean isType(String s) {
            if (s.equals(Mul) || s.equals(Div) || s.equals(Minus) || s.equals(Plus))
                return true;
            return false;
        }

        public static boolean notType(String s) {
            return !isType(s);
        }
    }

    public ActionData(String line, String s, int a, int b, String fun, int res) {
        this(line, s);
        this.a = a;
        this.b = b;
        this.fun = fun;
        this.res = res;
    }

    public ActionData(String line, String s) {
        this.src = line;
        this.error = s;
        a = 0;
        b = 0;
        fun = "";
        res = 0;
    }

    public String getError() {
        return error;
    }

    public Integer calc() {
        if (error != null)
            return null;
        Integer result = null;
        if (fun.equals(Types.Plus))
            result = a + b;
        else if (fun.equals(Types.Minus))
            result = a - b;
        else if (fun.equals(Types.Div))
            result = a / b;
        else if (fun.equals(Types.Mul))
            result = a * b;
        return result;
    }

    public Integer getRes() {
        return res;
    }

    public int getOp1() {
        return a;
    }

    public int getOp2() {
        return b;
    }

    public String getFun() {
        return fun;
    }

    public String getSrc() {
        return src;
    }

    public static void main(String... args) {
        ActionData ad = new ActionData("7;2;*;5", null, 7, 2, "*", 5);
        System.out.print(ad.calc());
    }
}
