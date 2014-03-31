public class DoubleArraySeqTester {
    private int passes;
    private int failures;

    public void runTests() {
        passes = 0;
        failures = 0;

        System.out.print("========================================");
        System.out.println("========================================");
        System.out.println("Testing class DoubleArraySeq");
        System.out.print("========================================");
        System.out.println("========================================");

        testConstruction();
        testAddAfter();
        testAddBefore();
        testAddAll();
        testClone();
        testConcatenation();
        testRemoveCurrent();
        testEquals();
        testPreconditions();

        System.out.print("========================================");
        System.out.println("========================================");
        System.out.println("Tests executed:    " + (passes + failures));
        System.out.println("     Successful:   " + passes);
        System.out.println("     Unsuccessful: " + failures);
    }

    public int getPasses() {
        return passes;
    }

    public int getFailures() {
        return failures;
    }

    /**
     * A helper method that increments the pass/fail counters and prints
     * an appropriate message based on the value of the specified condition.
     *
     * @param condition A condition for which to test.  If the condition is
     *                  true, the test passed; otherwise, it fails.
     * @param message   A message to print indicating the context for the test.
     */
    private void test(boolean condition, String message) {
        if (condition) {
            System.out.println("PASSED: " + message);
            passes = passes + 1;
        } else {
            System.out.println("FAILED: " + message);
            failures = failures + 1;
        }
    }

    private void testConstruction() {
        System.out.println("\nTesting Constructor");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s = new DoubleArraySeq();

        test(s.getCapacity() == 10,
                "Default parameter gives a capacity of 10: " + s.getCapacity());
        test(!s.isCurrent(),
                "isCurrent returns false: " + s.isCurrent());
        test(s.size() == 0,
                "size returns 0: " + s.size());
        test(s.toString().equals("<>"),
                "toString returns \"<>\": " + s.toString());


        DoubleArraySeq s2 = new DoubleArraySeq(27);
        test(s2.getCapacity() == 27,
                "new DoubleArraySeq(27) gives a capacity of 27: "
                        + s2.getCapacity()
        );
        test(!s2.isCurrent(),
                "isCurrent returns false: " + s2.isCurrent());
        test(s2.size() == 0,
                "size returns 0: " + s2.size());
        test(s2.toString().equals("<>"),
                "toString returns \"<>\": " + s2.toString());

        test(s.equals(s2),
                "Two empty sequences are equal: " + s.equals(s2));

        s.trimToSize();
        test(s.getCapacity() == 0,
                "After trimToSize(), capacity is 0: " + s.getCapacity());

        test(s.equals(s2),
                "Two empty sequences are still equal: " + s.equals(s2));


        s.ensureCapacity(128);
        test(s.getCapacity() == 128,
                "After ensureCapacity(128), capacity is 128: " + s
                        .getCapacity()
        );
    }

    private void testAddAfter() {
        System.out.println("\nTesting addAfter()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s = new DoubleArraySeq();
        s.addAfter(1.1);

        test(s.toString().equals("<[1.1]>"),
                "After addAfter(1.1), toString returns <[1.1]>: " + s
                        .toString()
        );
        test(s.getCurrent() == 1.1,
                "Current is 1.1: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 1,
                "size is 1: " + s.size());

        s.addAfter(2.2);
        test(s.toString().equals("<1.1, [2.2]>"),
                "After addAfter(2.2), toString returns <1.1, [2.2]>: "
                        + s.toString()
        );
        test(s.getCurrent() == 2.2,
                "Current is 2.2: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 2,
                "size is 2: " + s.size());

        s.addAfter(3.3);
        test(s.toString().equals("<1.1, 2.2, [3.3]>"),
                "After addAfter(3.3), toString returns <1.1, 2.2, [3.3]>:\n"
                        + "        " + s.toString()
        );
        test(s.getCurrent() == 3.3,
                "Current is 3.3: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 3,
                "size is 3: " + s.size());

        s.start();
        test(s.toString().equals("<[1.1], 2.2, 3.3>"),
                "After start, toString returns <[1.1], 2.2, 3.3>:\n"
                        + "        " + s.toString()
        );

        s.addAfter(4.4);
        test(s.toString().equals("<1.1, [4.4], 2.2, 3.3>"),
                "After addAfter(4.4), toString returns <1.1, [4.4], 2.2, " +
                        "3.3>:\n"
                        + "        " + s.toString()
        );
        test(s.getCurrent() == 4.4,
                "Current is 4.4: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 4,
                "size is 4: " + s.size());

        s.advance();
        test(s.toString().equals("<1.1, 4.4, [2.2], 3.3>"),
                "After advance, toString returns <1.1, 4.4, [2.2], 3.3>:\n"
                        + "        " + s.toString()
        );

        s.addAfter(5.5);
        test(s.toString().equals("<1.1, 4.4, 2.2, [5.5], 3.3>"),
                "After addAfter(5.5), toString returns "
                        + "<1.1, 4.4, 2.2, [5.5], 3.3>:\n"
                        + "        " + s.toString()
        );

        s.advance();
        s.advance();
        test(s.toString().equals("<1.1, 4.4, 2.2, 5.5, 3.3>"),
                "After 2x advance(), toString returns "
                        + "<1.1, 4.4, 2.2, 5.5, 3.3>:\n"
                        + "        " + s.toString()
        );

        s.addAfter(6.6);
        test(s.toString().equals("<1.1, 4.4, 2.2, 5.5, 3.3, [6.6]>"),
                "After addAfter(6.6), toString returns "
                        + "<1.1, 4.4, 2.2, 5.5, 3.3, [6.6]>:\n"
                        + "        " + s.toString()
        );

        s.addAfter(7.7);
        s.addAfter(8.8);
        s.addAfter(9.9);
        s.addAfter(1.2);
        test(s.toString().equals("<1.1, 4.4, 2.2, 5.5, 3.3, 6.6, 7.7, "
                        + "8.8, 9.9, [1.2]>"),
                "After adding 4 more, toString returns\n        "
                        + "<1.1, 4.4, 2.2, 5.5, 3.3, 6.6, 7.7, 8.8, 9.9, " +
                        "[1.2]>:\n"
                        + "        " + s.toString()
        );
        test(s.getCapacity() == 10,
                "Capacity is 10: " + s.getCapacity());
        test(s.size() == 10,
                "size returns 10: " + s.size());


        s.addAfter(2.3);
        test(s.toString().equals("<1.1, 4.4, 2.2, 5.5, 3.3, 6.6, 7.7, "
                        + "8.8, 9.9, 1.2, [2.3]>"),
                "After adding 1 more, toString returns\n        "
                        + "<1.1, 4.4, 2.2, 5.5, 3.3, 6.6, 7.7, 8.8, 9.9, 1.2," +
                        " [2.3]>:\n"
                        + "        " + s.toString()
        );
        test(s.getCapacity() > 11,
                "Capacity is > 11: " + s.getCapacity());
        test(s.size() == 11,
                "size returns 11: " + s.size());
    }

    private void testAddBefore() {
        System.out.println("\nTesting addBefore()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s = new DoubleArraySeq();
        s.addBefore(1.1);

        test(s.toString().equals("<[1.1]>"),
                "After addBefore(1.1), toString returns <[1.1]>: " + s
                        .toString()
        );
        test(s.getCurrent() == 1.1,
                "Current is 1.1: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 1,
                "size is 1: " + s.size());

        s.addBefore(2.2);
        test(s.toString().equals("<[2.2], 1.1>"),
                "After addBefore(2.2), toString returns <[2.2], 1.1>: "
                        + s.toString()
        );
        test(s.getCurrent() == 2.2,
                "Current is 2.2: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 2,
                "size is 2: " + s.size());

        s.addBefore(3.3);
        test(s.toString().equals("<[3.3], 2.2, 1.1>"),
                "After addBefore(3.3), toString returns <[3.3], 2.2, 1.1>:\n"
                        + "        " + s.toString()
        );
        test(s.getCurrent() == 3.3,
                "Current is 3.3: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 3,
                "size is 3: " + s.size());

        s.advance();
        test(s.toString().equals("<3.3, [2.2], 1.1>"),
                "After advance(), toString returns <3.3, [2.2], 1.1>:\n"
                        + "        " + s.toString()
        );

        s.addBefore(4.4);
        test(s.toString().equals("<3.3, [4.4], 2.2, 1.1>"),
                "After addBefore(4.4), toString returns <3.3, [4.4], 2.2, " +
                        "1.1>:\n"
                        + "        " + s.toString()
        );
        test(s.getCurrent() == 4.4,
                "Current is 4.4: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 4,
                "size is 4: " + s.size());

        s.advance();
        s.advance();
        s.advance();
        test(s.toString().equals("<3.3, 4.4, 2.2, 1.1>"),
                "After 3x advance(), toString returns <3.3, 4.4, 2.2, 1.1>:\n"
                        + "        " + s.toString()
        );

        s.addBefore(5.5);
        test(s.toString().equals("<[5.5], 3.3, 4.4, 2.2, 1.1>"),
                "After addBefore(5.5), toString returns "
                        + "<[5.5], 3.3, 4.4, 2.2, 1.1>:\n        " + s
                        .toString()
        );
        test(s.getCurrent() == 5.5,
                "Current is 5.5: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 5,
                "size is 5: " + s.size());

        s.addBefore(6.6);
        s.addBefore(7.7);
        s.addBefore(8.8);
        s.addBefore(9.9);
        s.addBefore(1.2);

        test(s.getCurrent() == 1.2,
                "Current is 1.2: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() == 10,
                "capacity is still 10: " + s.getCapacity());
        test(s.size() == 10,
                "size is 10: " + s.size());

        s.addBefore(2.3);
        test(s.getCurrent() == 2.3,
                "Current is 2.3: " + s.getCurrent());
        test(s.isCurrent(),
                "isCurrent() returns true: " + s.isCurrent());
        test(s.getCapacity() > 11,
                "capacity is > 11: " + s.getCapacity());
        test(s.size() == 11,
                "size is 11: " + s.size());
    }

    private void testAddAll() {
        System.out.println("\nTesting addAll()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s1 = new DoubleArraySeq();

        s1.addAfter(1.1);
        s1.addAfter(3.3);
        s1.addBefore(2.2);

        DoubleArraySeq s2 = new DoubleArraySeq();
        s2.addAfter(4.4);
        s2.addAfter(5.5);
        s2.addAfter(6.6);

        s1.addAll(s2);
        test(s1.toString().equals("<1.1, [2.2], 3.3, 4.4, 5.5, 6.6>"),
                "After addAll(s2), s1 is <1.1, [2.2], 3.3, 4.4, 5.5, 6.6>");
        test(s1.size() == 6,
                "s1's size is 6: " + s1.size());
        test(s1.getCapacity() == 10,
                "s1's capacity is 10: " + s1.getCapacity());

        test(s2.toString().equals("<4.4, 5.5, [6.6]>"),
                "After addAll(), s2 is <4.4, 5.5, [6.6]>");
        test(s2.size() == 3,
                "s2's size is 3: " + s2.size());
        test(s2.getCapacity() == 10,
                "s2's capacity is 10: " + s2.getCapacity());

        s1.addAll(s1);
        test(s1.toString().equals("<1.1, [2.2], 3.3, 4.4, 5.5, 6.6, 1.1, "
                        + "2.2, 3.3, 4.4, 5.5, 6.6>"),
                "After addAll(s1), s1 is\n        <1.1, [2.2], 3.3, 4.4, 5.5, "
                        + "6.6, 1.1, 2.2, 3.3, 4.4, 5.5, " +
                        "6.6>:\n        " + s1.toString()
        );
        test(s1.size() == 12,
                "s1's size is 12: " + s1.size());
        test(s1.getCapacity() >= 12,
                "s1's capacity should be >= 12: " + s1.getCapacity());

    }

    private void testClone() {
        System.out.println("\nTesting clone()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s1 = new DoubleArraySeq();
        s1.addBefore(1.1);
        s1.addAfter(3.3);
        s1.addBefore(2.2);

        DoubleArraySeq s2 = s1.clone();

        test(s1.equals(s2),
                "The clone and the original are equal: " + s1.equals(s2));

        test(s1.toString().equals("<1.1, [2.2], 3.3>"),
                "s1 is <1.1, [2.2], 3.3>: " + s1.toString());
        test(s1.size() == 3,
                "The size of the original is 3: " + s1.size());
        test(s1.getCapacity() == 10,
                "The capacity of the original is 10: " + s1.getCapacity());
        test(s1.getCurrent() == 2.2,
                "The current of the original is 2.2: " + s1.getCurrent());

        test(s2.toString().equals("<1.1, [2.2], 3.3>"),
                "s2 is <1.1, [2.2], 3.3>: " + s2.toString());
        test(s2.size() == 3,
                "The size of the clone is 3: " + s2.size());
        test(s2.getCapacity() == 10,
                "The capacity of the clone is 10: " + s2.getCapacity());
        test(s2.getCurrent() == 2.2,
                "The current of the clone is 2.2: " + s2.getCurrent());

        s2.addAfter(4.4);

        test(s1.toString().equals("<1.1, [2.2], 3.3>"),
                "s1 is <1.1, [2.2], 3.3>: " + s1.toString());
        test(s1.size() == 3,
                "The size of the original is 3: " + s1.size());
        test(s1.getCapacity() == 10,
                "The capacity of the original is 10: " + s1.getCapacity());
        test(s1.getCurrent() == 2.2,
                "The current of the original is 2.2: " + s1.getCurrent());

        test(s2.toString().equals("<1.1, 2.2, [4.4], 3.3>"),
                "s2 is <1.1, 2.2, [4.4], 3.3>: " + s2.toString());
        test(s2.size() == 4,
                "The size of the clone is 4: " + s2.size());
        test(s2.getCapacity() == 10,
                "The capacity of the clone is 10: " + s2.getCapacity());
        test(s2.getCurrent() == 4.4,
                "The current of the clone is 4.4: " + s2.getCurrent());
    }

    private void testConcatenation() {
        System.out.println("\nTesting concatenation()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s1 = new DoubleArraySeq();
        s1.addAfter(1.1);
        s1.addAfter(2.2);

        DoubleArraySeq s2 = new DoubleArraySeq();
        s2.addBefore(2.2);
        s2.addBefore(1.1);

        DoubleArraySeq s3 = DoubleArraySeq.concatenation(s1, s2);

        test(s1.toString().equals("<1.1, [2.2]>"),
                "s1 is still <1.1, [2.2]>: " + s1.toString());
        test(s2.toString().equals("<[1.1], 2.2>"),
                "s2 is still <[1.1], 2.2>: " + s1.toString());

        test(s3.toString().equals("<1.1, 2.2, 1.1, 2.2>"),
                "New seq is: <1.1, 2.2, 1.1, 2.2>: " + s3.toString());
        test(s3.size() == 4,
                "New seq's size is 4: " + s3.size());
        test(s3.getCapacity() >= 4,
                "New seq's capacity is >= 4: " + s3.getCapacity());
        test(!s3.isCurrent(),
                "New seq has a current (false): " + s3.isCurrent());
    }

    private void testRemoveCurrent() {
        System.out.println("\nTesting removeCurrent()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s = new DoubleArraySeq();

        s.addAfter(1.0);
        s.addAfter(2.0);
        s.addAfter(3.0);
        s.addAfter(4.0);
        s.addAfter(5.0);
        s.addAfter(6.0);
        s.addAfter(7.0);
        s.addAfter(8.0);
        s.addAfter(9.0);
        s.addAfter(1.0);
        s.addAfter(1.1);

        int oldCapacity = s.getCapacity();

        s.start();
        test(s.toString().equals("<[1.0], 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, "
                        + "8.0, 9.0, 1.0, 1.1>"),
                "Seq is: <[1.0], 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, "
                        + "8.0, 9.0, 1.0, 1.1>:\n        " + s.toString()
        );

        s.removeCurrent();
        test(s.size() == 10, "size is 10: " + s.size());
        test(s.getCapacity() == oldCapacity,
                "Capacity is " + oldCapacity + ": " + s.getCapacity());
        test(s.toString().equals("<[2.0], 3.0, 4.0, 5.0, 6.0, 7.0, "
                        + "8.0, 9.0, 1.0, 1.1>"),
                "After removeCurrent: <[2.0], 3.0, 4.0, 5.0, 6.0, 7.0, "
                        + "8.0, 9.0, 1.0, 1.1>:\n        " + s.toString()
        );

        s.advance();
        s.removeCurrent();
        test(s.size() == 9, "size is 9: " + s.size());
        test(s.getCapacity() == oldCapacity,
                "Capacity is " + oldCapacity + ": " + s.getCapacity());
        test(s.toString().equals("<2.0, [4.0], 5.0, 6.0, 7.0, "
                        + "8.0, 9.0, 1.0, 1.1>"),
                "After advance/removeCurrent:\n        <2.0, [4.0], 5.0, 6.0, "
                        + "7.0, 8.0, 9.0, 1.0, 1.1>:\n        " + s.toString()
        );

        s.advance();
        s.advance();
        s.removeCurrent();
        test(s.size() == 8, "size is 8: " + s.size());
        test(s.getCapacity() == oldCapacity,
                "Capacity is " + oldCapacity + ": " + s.getCapacity());
        test(s.toString().equals("<2.0, 4.0, 5.0, [7.0], "
                        + "8.0, 9.0, 1.0, 1.1>"),
                "After advance/advance/removeCurrent:\n        <2.0, 4.0, 5.0, "
                        + "[7.0], 8.0, 9.0, 1.0, 1.1>:\n        " + s.toString()
        );

        s.advance();
        s.advance();
        s.advance();
        s.advance();
        s.removeCurrent();
        test(s.size() == 7, "size is 7: " + s.size());
        test(s.getCapacity() == oldCapacity,
                "Capacity is " + oldCapacity + ": " + s.getCapacity());
        test(s.toString().equals("<2.0, 4.0, 5.0, 7.0, "
                        + "8.0, 9.0, 1.0>"),
                "After 4x advance/removeCurrent:  <2.0, 4.0, 5.0, "
                        + "7.0, 8.0, 9.0, 1.0>:\n        " + s.toString()
        );
        test(!s.isCurrent(),
                "Is there a current: (false): " + s.isCurrent());

        s.start();
        s.removeCurrent();
        s.removeCurrent();
        s.removeCurrent();
        s.removeCurrent();
        s.removeCurrent();
        s.removeCurrent();
        s.removeCurrent();

        test(s.size() == 0, "size is 0: " + s.size());
        test(s.getCapacity() == oldCapacity,
                "Capacity is " + oldCapacity + ": " + s.getCapacity());
        test(s.toString().equals("<>"), "After removing everything: <>: "
                + s.toString());
        test(!s.isCurrent(),
                "Is there a current: (false): " + s.isCurrent());
    }

    private void testEquals() {
        System.out.println("\nTesting equals()");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        DoubleArraySeq s1 = new DoubleArraySeq();
        DoubleArraySeq s2 = new DoubleArraySeq();
        DoubleArraySeq s3 = new DoubleArraySeq();
        DoubleArraySeq s4 = new DoubleArraySeq();
        DoubleArraySeq s5 = new DoubleArraySeq();
        s3.addAfter(1.1);
        s4.addAfter(1.1);
        s5.addAfter(1.1);
        s5.addAfter(2.2);


        test(s1.equals(s1),
                "An empty sequence is equal to itself: " + s1.equals(s1));
        test(s1.equals(s2),
                "An empty sequence is equal to an empty sequence : "
                        + s1.equals(s2)
        );
        test(!s1.equals(s3),
                "An empty sequence is not equal to a non-empty one: (false): "
                        + s1.equals(s3)
        );
        test(s3.equals(s4),
                "Two non-empty equal sequences are equal: " + s3.equals(s4));
        test(!s3.equals(s5),
                "Two non-empty, non-equal sequences are equal (false): "
                        + s3.equals(s5)
        );

        s3.ensureCapacity(27);
        test(s3.equals(s4),
                "Two non-empty equal sequences (w/ different capacities) "
                        + "are equal:\n        " + s3.equals(s4)
        );


        s3.addAfter(2.2);
        s4.addAfter(2.2);
        s3.start();

        test(!s3.equals(s4),
                "Two sequence w/ same element but different current "
                        + "are equal (false):\n        " + s3.equals(s4)
        );

        s4.advance();
        test(!s3.equals(s4),
                "Two sequence w/ same element but different current "
                        + "are equal (false):\n        " + s3.equals(s4)
        );
    }

    private void testPreconditions() {
        System.out.println("\nTesting preconditions");
        System.out.print("----------------------------------------");
        System.out.println("----------------------------------------");

        try {
            DoubleArraySeq s = new DoubleArraySeq();
            s.addAll(null);
            test(false, "addAll(null) should throw a NPE");
        } catch (NullPointerException ex) {
            test(true, "addAll(null) should throw a NPE: " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "addAll(null) should throw a NPE, got: "
                    + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            DoubleArraySeq s = new DoubleArraySeq();
            s.advance();
            test(false, "advance() with no current should throw a ISE");
        } catch (IllegalStateException ex) {
            test(true, "advance() with no current should throw a ISE:\n"
                    + "        " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "advance() with no current should throw a ISE, got:\n"
                    + "        " + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            DoubleArraySeq.concatenation(null, null);
            test(false, "concatenation(null, null) should throw a NPE");
        } catch (NullPointerException ex) {
            test(true, "concatenation(null, null) should throw a NPE:\n"
                    + "        " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "concatenation(null, null) should throw a NPE, got:\n"
                    + "        " + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            DoubleArraySeq s = new DoubleArraySeq();
            DoubleArraySeq.concatenation(null, s);
            test(false, "concatenation(null, s) should throw a NPE");
        } catch (NullPointerException ex) {
            test(true, "concatenation(null, s) should throw a NPE:\n"
                    + "        " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "concatenation(null, s) should throw a NPE, got:\n"
                    + "        " + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            DoubleArraySeq s = new DoubleArraySeq();
            DoubleArraySeq.concatenation(s, null);
            test(false, "concatenation(s, null) should throw a NPE");
        } catch (NullPointerException ex) {
            test(true, "concatenation(s, null) should throw a NPE:\n"
                    + "        " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "concatenation(s, null) should throw a NPE, got:\n"
                    + "        " + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            DoubleArraySeq s = new DoubleArraySeq();
            s.getCurrent();
            test(false, "getCurrent() w/ no current should throw a ISE");
        } catch (IllegalStateException ex) {
            test(true, "getCurrent() w/ no current should throw a ISE:\n"
                    + "        " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "getCurrent() w/ no current should throw a ISE, got:\n"
                    + "        " + ex.getMessage());
            ex.printStackTrace();
        }

        try {
            DoubleArraySeq s = new DoubleArraySeq();
            s.removeCurrent();
            test(false, "removeCurrent() w/ no current should throw a ISE");
        } catch (IllegalStateException ex) {
            test(true, "removeCurrent() w/ no current should throw a ISE:\n"
                    + "        " + ex.getMessage());
        } catch (Throwable ex) {
            test(false, "removeCurrent() w/ no current should throw a ISE,\n"
                    + "        got: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DoubleArraySeqTester tester = new DoubleArraySeqTester();
        tester.runTests();
    }
}