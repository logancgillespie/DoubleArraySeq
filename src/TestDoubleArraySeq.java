import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * The class <code>TestDoubleArraySeq.java</code> tests the constructors and
 * public methods of class <code>DoubleArraySeq</code> using a console menu.
 * When the program pauses, the user should enter one of the following with
 * one that is appropriate dependent on the context:
 * <p>
 * <ul>
 *          <li>a single integer menu choice</li>
 *          <li>a single double value</li>
 *          <li>several double values separated by blanks</li>
 * </ul>
 * Exceptions <code>IllegalArgumentException</code> and
 * <code>IllegalStateException</code> are caught by the <code>main</code>
 * method.  No other exceptions are caught.
 * <p>
 * The <code>DoubleArraySeq</code> methods <code>clone</code>,
 * <code>size</code>, and <code>getCapacity</code> are implicitly tested by the
 * <code>display</code> method.
 * <p>
 * The original version of this file was downloaded from:
 * http://ctas.east.asu.edu/millard/CET230/proj/proj2/TestDoubleArraySeq.java
 *
 * @author Dr. Mark A. Holliday
 * @author Bob Houston
 * @version 25 February
 */
public class TestDoubleArraySeq {
    private BufferedReader stdin  = new BufferedReader(
            new InputStreamReader(System.in));

    public void runTests() throws IOException {
        DoubleArraySeq seq  = new DoubleArraySeq();
        boolean        done = false;

        display("seq", seq);

        while (!done) {
            try {
                switch (getMenuChoice()) {
                    case  0: done = true;              break;
                    case  1: seq = newDASeq();         break;
                    case  2: testAddAfter(seq);        break;
                    case  3: testAddBefore(seq);       break;
                    case  4: seq.removeCurrent();      break;
                    case  5: testIsCurrent(seq);       break;
                    case  6: testGetCurrent(seq);      break;
                    case  7: seq.start();              break;
                    case  8: seq.advance();            break;
                    case  9: seq.trimToSize();         break;
                    case 10: testEnsureCapacity(seq);  break;
                    case 11: testAddAll(seq);          break;
                    case 12: testConcatenation(seq);   break;
                    case 13: testToString(seq);        break;
                    case 14: testEquals(seq);          break;
                }
            } catch (IllegalArgumentException e1) {
                System.out.println();
                System.out.print("CAUGHT: IllegalArgumentException: ");
                System.out.println(e1.getMessage());
            } catch (IllegalStateException e2) {
                System.out.println();
                System.out.print("CAUGHT: IllegalStateException: ");
                System.out.println(e2.getMessage());
            }

            display("seq", seq);
        }
    }


    private void display(String label, DoubleArraySeq seq) {
        DoubleArraySeq temp       = seq.clone();
        int            currPos    = posOfCurrent(seq.clone());
        String         outElement;

        System.out.println();
        System.out.print(label + ": {");

        temp.start();
        for (int i = 0; i < temp.size(); i++) {
            outElement = Double.toString(temp.getCurrent());

            if (i == currPos) {
                System.out.print("[" + outElement + "]");
            } else {
                System.out.print("<" + outElement + ">");
            }

            temp.advance();
        }


        for (int i = temp.size(); i < temp.getCapacity(); i++) {
            System.out.print("<>");
        }

        System.out.println("}");
    }


    private int posOfCurrent(DoubleArraySeq seq) {
        int count = 0;

        while (seq.isCurrent()) {
            seq.advance();
            count++;
        }

        return seq.size() - count;
    }


    private int getMenuChoice() throws IOException {
        System.out.println();
        System.out.println("Menu:");
        System.out.println("------------------------------------------------");
        System.out.println("1 Constructor   6 getCurrent     11 addAll    ");
        System.out.println("2 addAfter      7 start          12 concatenation");
        System.out.println("3 addBefore     8 advance        13 toString");
        System.out.println("4 removeCurrent 9 trimToSize     14 equals ");
        System.out.println("5 isCurrent    10 ensureCapacity");
        System.out.println("------------------------------------------------");
        System.out.print("Number of method to test (0 to quit): ");

        return Integer.parseInt(stdin.readLine());
    }


    private DoubleArraySeq newDASeq() throws IOException {
        System.out.println();
        System.out.print("Enter initial capacity: ");

        return new DoubleArraySeq(Integer.parseInt(stdin.readLine()));
    }


    private void testAddAfter(DoubleArraySeq seq) throws IOException {
        System.out.println();
        System.out.print("Enter double value to be added: ");
        seq.addAfter(Double.parseDouble(stdin.readLine()));
    }


    private void testAddBefore(DoubleArraySeq seq) throws IOException {
        System.out.println();
        System.out.print("Enter double value to be added: ");
        seq.addBefore(Double.parseDouble(stdin.readLine()));
    }


    private void testIsCurrent(DoubleArraySeq seq) {
        System.out.println();
        if (seq.isCurrent()) {
            System.out.println("A current element exists.");
        } else {
            System.out.println("No current element exists.");
        }
    }

    private void testGetCurrent(DoubleArraySeq seq) {
        System.out.println();
        System.out.println(  "Current element: "
                + Double.toString(seq.getCurrent()));
    }

    private void testEnsureCapacity(DoubleArraySeq seq) throws IOException {
        System.out.println();
        System.out.print("Enter minimum capacity: ");
        seq.ensureCapacity(Integer.parseInt(stdin.readLine()));
    }

    private void testAddAll(DoubleArraySeq seq) throws IOException {
        DoubleArraySeq addend = new DoubleArraySeq();

        System.out.println();
        System.out.print("Enter 0 or more double values for addend: ");
        StringTokenizer st = new StringTokenizer(stdin.readLine(), " ,");

        while (st.hasMoreTokens()) {
            addend.addAfter(Double.parseDouble(st.nextToken()));
        }
        display("addend", addend);

        seq.addAll(addend);
    }

    private void testConcatenation(DoubleArraySeq s1) throws IOException {
        StringTokenizer st;
        DoubleArraySeq  s2 = new DoubleArraySeq();

        display("s1", s1);
        System.out.println();
        System.out.print("Enter 0 or more double values for s2: ");
        st = new StringTokenizer(stdin.readLine(), " ,");

        while (st.hasMoreTokens()) {
            s2.addAfter(Double.parseDouble(st.nextToken()));
        }
        display("s2", s2);

        display("result", DoubleArraySeq.concatenation(s1, s2));
    }

    private void testToString(DoubleArraySeq seq) {
        System.out.println();
        System.out.println(seq.toString());
    }

    private void testEquals(DoubleArraySeq seq) {
        System.out.println();
        DoubleArraySeq s1 = seq;
        System.out.println(seq.equals(s1) + " " + s1.equals(seq));
        s1 = new DoubleArraySeq();
        s1.addAfter(2.5);
        s1.addAfter(4.0);
        System.out.println(seq.equals(s1) + " " + s1.equals(seq));
        DoubleArraySeq s2 = new DoubleArraySeq();
        s2.addAfter(4.0);
        s2.addAfter(2.5);
        System.out.println(s2.equals(s1) + " " + s1.equals(s2));
    }

    public static void main(String[] args) throws IOException {
        TestDoubleArraySeq tester = new TestDoubleArraySeq();
        tester.runTests();
    }
}