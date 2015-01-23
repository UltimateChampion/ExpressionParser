import junit.framework.TestCase;

import java.beans.Expression;

public class ExpressionParserTest extends TestCase {

    private ExpressionParser ep;

    public void testGeneral() throws Exception {

        System.out.println(ExpressionParser.parseString("(57.9)*24/18 +      95.4/2"));
        assertTrue(ExpressionParser.parseString("(57.9)*24/18 +      95.4/2").equals("124.9"));
    }

    public void testParseRPN() throws Exception {

        assertTrue(ExpressionParser.parseRPN("2 2 +").equals("4.0"));
        assertTrue(ExpressionParser.parseRPN("45 14 2 / -").equals("38.0"));
        //assertTrue(ExpressionParser.parseRPN("102938.43 12.675          17 2 2 2 - + / * - ").equals("102830.6925"));
    }

    public void testParseRPNNegatives() throws Exception {

        //
        assertTrue(ExpressionParser.parseRPN("2 3 -").equals("-1.0"));
        assertTrue(ExpressionParser.parseRPN("-2 3 -").equals("-5.0"));

    }

    public void testCalc() throws Exception {

    }

    public void testConvertToRPN() throws Exception {

    }

    public void testTokenize() throws Exception {

    }

    public void testIsBalanced() throws Exception {

        assertTrue(ExpressionParser.isBalanced(""));
        assertTrue(ExpressionParser.isBalanced("()"));
        assertTrue(ExpressionParser.isBalanced("(())"));

        assertFalse(ExpressionParser.isBalanced(")("));
        assertFalse(ExpressionParser.isBalanced(")()("));
        assertFalse(ExpressionParser.isBalanced("(()"));
    }

    public void testIsBalanced2() throws Exception {

        assertTrue(ExpressionParser.isBalanced("djjckdls"));
        assertTrue(ExpressionParser.isBalanced("()fkjlnc,sl(())"));
        assertTrue(ExpressionParser.isBalanced("(g(h)(d))"));

        assertFalse(ExpressionParser.isBalanced("(sd"));
        assertFalse(ExpressionParser.isBalanced(")43"));
        assertFalse(ExpressionParser.isBalanced("(dfd((g)s)"));
    }

    public void testFixString() {

        assertTrue(ExpressionParser.fixRegularString("(45)  (34)").equals("(45)*(34)"));
    }
}