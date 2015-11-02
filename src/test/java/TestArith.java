import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.yandex.qatools.allure.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TestArith {

    @Test(dataProvider = "parseData")
    public void testAction(@Parameter("op1") int a, @Parameter("op2") int b,
        @Parameter("func") String fun, @Parameter("res") Integer res, @Parameter("src") String src,
        String error, Integer calc) {
        if (error != null)
            Assert.fail(error);
        Assert.assertEquals(res, calc, "result");
    }

    @DataProvider(name = "parseData")
    public Object[][] provideData() throws Exception {
        ArrayList<ActionData> data = readData();
        Object[][] output = new Object[data.size()][];
        for (int i = 0; i < data.size(); i++) {
            ActionData ad = data.get(i);
            output[i] = new Object[] {ad.getOp1(), ad.getOp2(),
                ad.getFun(), ad.getRes(), ad.getSrc(),
                ad.getError(), ad.calc()};
        }
        return output;
    }

    @BeforeClass
    public ArrayList<ActionData> readData() throws Exception {
        ArrayList<ActionData> data = new ArrayList<ActionData>();
        Scanner in = new Scanner(new File("data.txt"));
        while(in.hasNext()) {
            String line = in.nextLine();
            ActionData ops = getOps(line);
            data.add(ops);
        }
        in.close();
        return data;
    }

    private ActionData getOps(String line) {
        if (line == null)
            return new ActionData(line, "Empty line in data file.");
        String[] ops = line.split(";");
        if (ops.length != 4)
            return new ActionData(line, "Wrong number of operands: " + line);

        String error = "";
        String fun = ops[2];
        int[] int_ops = new int[3];
        int index = 0;
        for (int i : new int[] {0, 1, 3}) {
            Integer val = strToInt(ops[i]);
            if (val == null)
                error += String.format("Wrong type of operand %d: %s. ", i+1, ops[i]);
            else
                int_ops[index++] = val;
        }
        if (ActionData.Types.notType(fun))
            error += String.format("Wrong type of operation: %s. ", fun);
        if (int_ops[1] == 0 && fun.equals(ActionData.Types.Div))
            error += "Division by zero is assumed.";
        if (error.equals(""))
            return new ActionData(line, null, int_ops[0], int_ops[1], fun, int_ops[2]);
        else
            return new ActionData(line, error);
    }

    private Integer strToInt(String s) {
        Integer op = null;
        try {
            op = Integer.parseInt(s);
        }
        catch (NumberFormatException e) {
        }
        return op;
    }

}
