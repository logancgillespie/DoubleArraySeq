/**
 * A DoubleArraySeq keeps track of a sequence of double numbers. The sequence
 * can have a special "current element", which is specified and accessed through
 * four methods that are not available in the IntArrayBag class (start,
 * getCurrent, advance, and isCurrent). Note that the addAfter and addBefore
 * methods work efficiently (without needing more memory) until the current
 * capacity of the sequence is reached.
 *
 * Note also that any attempt to increase the capacity of any sequence beyond
 * Integer.MAX_VALUE will cause the sequence to fail with an arithmetic
 * overflow.
 *
 * @author Logan Gillespie and Matt Anger
 * @version 1.0
 *
 *
 */
public class DoubleArraySeq implements Cloneable {

    /**
     * The default capacity of a newly constructed DoubleArraySeq.
     */
    static int DEFAULT_CAPACITY = 10;
    /**
     * A sentinel value used for the current element to indicate that no current
     * element exists.
     */
    private static int NO_CURRENT;
    /**
     * The index of the current element in this sequence
     */
    private int currentElement;
    /**
     * The elements of this sequence
     */
    private double[] data;
    /**
     * The current length of this sequence (i.e., how many items are in this
     * sequence).
     */
    private int manyItems;

    /**
     * Initializes an empty sequence with an initial capacity of
     * DEFAULT_CAPACITY. (This constructor should use this(...) to invoke the
     * second constructor.)
     *
     * @postcondition: Length of the data array is empty or the length of the
     *                 data array is equal to the default capacity
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory for: new
     *             double[initialCapacity].
     */
    public DoubleArraySeq() {
        this(DEFAULT_CAPACITY);

        if (data.length <= 0 && data.length == DEFAULT_CAPACITY) {
            throw new IllegalStateException();
        }
        
    }

    /**
     * Initializes an empty sequence with the specified initial capacity.
     *
     * @param initialCapacity
     *            takes input to determine the initial length of the array
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory for: new
     *             double[initialCapacity].
     *
     * @postcondition: Length of the data array is empty or the length of the
     *                 data array is equal to the default capacity
     */

    public DoubleArraySeq(int initialCapacity) {
        try {
            data = new double[initialCapacity];
        } catch (OutOfMemoryError e) {
            System.out.println("**Error in code**");
            e.printStackTrace();
        }
        if (data.length <= 0 && data.length == DEFAULT_CAPACITY) {
            throw new IllegalStateException();
        }
    }

    /**
     * Creates a new sequence that contains all the elements from s1 followed by
     * all of the elements from s2.
     *
     * @param s1
     *            the first of two sequences.
     *
     * @param s2
     *            the second of two sequences.
     *
     * @precondition neither s1 nor s2 are null.
     *
     * @return a new sequence that has the elements of s1 followed by the
     *         elements of s2 (with no current element).
     *
     * @throws NullPointerException
     *             if s1 or s2 are null.
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory for the new sequence.
     */
    //Not positive if we need the throws or not...
    public static DoubleArraySeq concatenation(DoubleArraySeq s1, DoubleArraySeq s2) 
                                        throws NullPointerException, OutOfMemoryError {
        //Throw this I guess??? 
        if (s1 == null || s2 == null) {
            throw new NullPointerException(); 
        } 
        
        //Make a new DoubleArraySeq to return 
        DoubleArraySeq newSeq = new DoubleArraySeq(s1.size() + s2.size());
        newSeq.addAll(s1); 
        newSeq.addAll(s2); 
        return newSeq;
    }

    /**
     * Determines the number of elements in this sequence.
     *
     * @return the number of elements in this sequence
     */
    public int size() {
        manyItems = data.length;
        return manyItems;
    }

    /**
     * Sets the current element at the front of this sequence.
     *
     * @postcondition: If this sequence is not empty, the front element of this
     *                 sequence is now the current element; otherwise, there is
     *                 no current element.
     */
   public void start() {

    }

    /**
     * Returns a copy of the current element in this sequence.
     *
     * @precondition isCurrent() returns true.
     *
     * @return double if there is a current element.
     *
     * @throws IllegalStateException - if there is no currentElement
     */
    
    //Think the data types are messed up on this one.
    //TODO: fix or check 
    public double getCurrent() throws IllegalStateException {

        if (!this.isCurrent()) {
            throw new IllegalStateException();     
        } 

        return this.data[currentElement];
    }

    /**
     * Adds a new element to this sequence after the current element. If this
     * new element would take this beyond its current capacity, then the
     * capacity is increased before adding the new element.
     *
     * @param element
     *            the new element that is being added to this sequence.
     *
     * @postcondition: a new copy of the element has been added to this
     *                 sequence. If there was a current element, then this
     *                 method places the new element before the current element.
     *                 If there was no current element, then this method places
     *                 the new element at the front of this sequence. The newly
     *                 added element becomes the new current element of this
     *                 sequence.
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory to increase the size of this
     *             sequence.
     */
    public void addAfter(double element) {

    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * @param other
     *            a sequence show contents will be placed at the end of this
     *            sequence.
     *
     * @precondition other must not be null.
     *
     * @postcondition: the elements from other have been placed at the end of
     *                 this sequence. The current element of this sequence
     *                 remains where it was, and other is unchanged.
     *
     * @throws NullPointerException
     *             if other is null.
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory to increase the capacity of
     *             this sequence.
     */
    public void addAll(DoubleArraySeq other) {

    }

    /**
     * Adds a new element to this sequence before the current element. If this
     * new element would take this beyond its current capacity, then the
     * capacity is increased before adding the new element.
     *
     * @param element
     *            the new element that is being added to this sequence.
     *
     * @throws NullPointerException
     *             if other is null
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory to increase the capacity of
     *             this sequence.
     *
     * @precondition a new copy of the element has been added to this sequence.
     *               If there was a current element, then this method places the
     *               new element before the current element. If there was no
     *               current element, then this method places the new element at
     *               the front of this sequence. The newly added element becomes
     *               the new current element of this sequence.
     */
    public void addBefore(double element) {

    }

    /**
     * Move forward so that the current element is now the next element in the
     * sequence.
     *
     * @precondition isCurrent() returns true.
     *
     * @postcondition If the current element was already the end element of this
     *                sequence (with nothing after it), then there is no longer
     *                any current element. Otherwise, the new element is the
     *                element immediately after the original current element.
     */
    public void advance() {
        int newCur = currentElement + 1; 
        if (this.isCurrent()) {
            //check to see if old current element is at the end of the array
            if (newCur > data.length) {
                this.setNoCurrent();  
            } else {
                this.currentElement = newCur; 
            }
        }
    }

    /**
     * Appends the value in data at the specified index onto the specified
     * StringBuilder.
     *
     * @param sb
     *            The StringBuilder on which to append
     * @param index
     *            The index of the element in data to append to sb.
     *
     * @postcondition If index == currentElement, then the element at index is
     *                appended to sb in [ ]'s, otherwise, it's appended without
     *                the brackets.
     */
    private void appendElement(StringBuilder sb, int index) {

    }

    /**
     * Creates a copy of this sequence.
     *
     * @Overrides clone in object class Object
     *
     * @return a copy of this sequence. Subsequent changes to the copy will not
     *         affect the original, nor vice versa.
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory for creating the clone
     *             object.
     *
     * @throws RuntimeException
     *             if this class does not implement Cloneable.
     */
    protected DoubleArraySeq clone() {

    }

    /**
     * Change the current capacity of this sequence to be at least the specified
     * value.
     *
     * @param minimumCapacity
     *            the new minimum capacity for this sequence.
     *
     * @postcondition This sequence's capacity has been changed to be at least
     *                minimumCapacity, but no less than size.
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory for the a new array: new
     *             double[minimumCapacity].
     */
    public void ensureCapacity(int minimumCapacity) {

    }

    /**
     * Determines if this object is equal to the other object.
     *
     * @Override equals in class Object
     *
     * @return true if this object is equal to the other object, false
     *         otherwise.
     */
    public boolean equals(Object other) {

    }

    /**
     * Determines the current capacity of this sequence.
     *
     * @precondition isCurrent() returns true.
     *
     * @return the current element of this sequence.
     *
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public int getCapacity() {

    }

    /**
     * Determines whether this sequence has specified a current element.
     * Check based off NO_CURRENT.  If it's null or less than 0 no current
     * element exists
     *
     * @return true if there is a current element, or false otherwise.
     */
    public boolean isCurrent() {
        boolean currentExists = true; 
        if (NO_CURRENT <= 0) {
            currentExists = false; 
        }
        return currentExists;
    }

    /**
     * Removes the current element from this sequence.
     *
     * @precondition isCurrent() returns true.
     *
     * @postcondition The current element has been removed from this sequence,
     *                and the following element (if there is one) is now the new
     *                current element. If there was no following element, then
     *                there is now no current element.
     *
     * @throws IllegalStateException
     *             if there is no current element.
     */
    public void removeCurrent() {

        if (!this.isCurrent()) {
            throw new IllegalStateException(); 
        }

    }

    /**
     * Returns a String representation of this sequence. If the sequence is
     * empty, the method should return "<>". If the sequence has one item, say
     * 1.1, and that item is not the current item, the method should return
     * "<1.1>". If the sequence has more than one item, they should be separated
     * by commas, for example: "<1.1, 2.2, 3.3>". If there exists a current
     * item, then that item should be surrounded by square braces. For example,
     * if the second item is the current item, the method should return:
     * "<1.1, [2.2], 3.3>".
     *
     * @Override toString in class Object
     *
     * @return a String representation of this sequence
     */
    public String toString() {

    }

    /**
     * Reduces the capacity of the sequence to the number of elements currently
     * in the sequence.
     *
     * @postcondition This sequence's capacity has been changed to its current
     *                size.
     *
     * @throws OutOfMemoryError
     *             if there is insufficient memory for altering the capacity.
     */
    public void trimToSize() {

    }


    /**
     * Sets the NO_CURRENT value to indicate that there is there is no 
     * current value selected.  0 inicates there is no current value.
     * 
     */
    private void setNoCurrent() {
        this.NO_CURRENT = 0; 
    }
}
