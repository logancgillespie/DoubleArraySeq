import java.util.Arrays;

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
     * element exists.  Set to -1 if there is no current element.
     */
    private static int NO_CURRENT;

    /**
     * Number of elements in the array
     */
    private int numElements = 0;

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
        currentElement = 1;
        manyItems=initialCapacity;
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
        //Might be an invalid parameter exception
        if (s1 == null || s2 == null) throw new NullPointerException();


        //Make a new DoubleArraySeq to return 
        DoubleArraySeq newSeq = new DoubleArraySeq(s1.size() + s2.size());
        newSeq.addAll(s1);
        newSeq.addAll(s2);
        newSeq.manyItems= s1.manyItems+s2.manyItems;
        newSeq.numElements= s1.numElements+s2.numElements;
        newSeq.NoCurrent();
        return newSeq;
    }

    /**
     * Determines the number of elements in this sequence.
     *
     * @return the number of elements in this sequence
     */
    public int size() {
        return numElements;
    }

    /**
     * This function increases the size of the this.data array and
     * copies data's current content into the new array.
     *
     * The var boostFactor is the factor by which the arrays current
     * size will be multiplied by
     *
     * Should only be called if the array is full or data
     * loss may occur.
     *
     * @return the new array with increased size
     */
    private void arrayBoost() {
        int boostFactor = 2;
        int newSize = data.length * boostFactor + 1;
        double[] tmp = new double[newSize];
        System.arraycopy(data, 0, tmp, 0, manyItems);
        data = tmp;
        manyItems =manyItems *boostFactor+1;
    }


    /**
     * Sets the current element at the front of this sequence.
     *
     * @postcondition: If this sequence is not empty, the front element of this
     *                 sequence is now the current element; otherwise, there is
     *                 no current element.
     */
    public void start() {
        if (this.size() > 0) {
            this.Current(0);
        } else {
            this.NoCurrent();
        }
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

        return data[currentElement];
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
     *                 method places the new element after the current element.
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
        //make sure there is room in the data array.
        if(this.size()  == data.length){
            this.arrayBoost();
        }

        //check to see if there is a current element
        if(this.size() <= 0 ) {
            //think I should set this
            this.Current(0);
            data[currentElement] = element;

            //debugging
            //System.out.println("\nFIRST THE SIZE: "+this.size()+"\n");

        } else {

            if (!this.isCurrent()) {
                //debuggings
                //System.out.println("\nSEC THE SIZE: "+this.size()+"\n");

                data[this.size()] = element;
                currentElement = this.size();
            } else {
                //changed to +1

                //debugging
                //System.out.println("\nLAST THE SIZE: "+ this.size() + " " + this.isCurrent() + "\n");

                System.arraycopy(data, currentElement, data, currentElement+1, this.size());
                currentElement++;
                data[currentElement] = element;
            }
        }

        numElements++;
        //set the current element.
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
    //Question:
    //Do we want add the whole sequence or do we just want to add values that aren't 0.0?
    //Just going to access the private data field of the other DoubleArraySeq
    //TODO: Make sure the increase array sequence size method doesn't crash or anything
    public void addAll(DoubleArraySeq other) {
        if (other != null) {
            //make sure this data array is large enough
            while (other.size() >= this.data.length ) arrayBoost();
            System.arraycopy(other.data, 0, this.data, this.numElements, this.size()+other.size());
            manyItems= manyItems+other.size();

        } else {
            throw new NullPointerException();
        }
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
    //TODO: maybe add to ternary operations to use even LESS lines
    public void addBefore(double element) {
        //check to see if there is a current element
        if(!this.isCurrent()) this.Current(0);

        //make sure there is room in the data array.
        if(this.size() == data.length) this.arrayBoost();

        //much more prettier
        System.arraycopy(data, currentElement, data, currentElement + 1, this.size());
        //set the current element.
        data[currentElement] = element;
        numElements++;
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

            //Debugging
            //System.out.println("\n In advance " + newCur + "\n");

            if (newCur == this.size()/*+1*/) {
                //System.out.println("SET NO CURRENT");
                this.NoCurrent();
            } else {
                //debuggging
                //System.out.println("SETTING NEXT");
                this.currentElement = newCur;
            }
        }
    }

    //TODO: figure out what this is for.
    /**
     *
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
    //TODO: implement deep cloning
    protected DoubleArraySeq clone() {
        DoubleArraySeq theCopy;
        try {
            theCopy = (DoubleArraySeq) super.clone();
            System.arraycopy(data,1,theCopy.data,0,data.length-1);
            theCopy.numElements = numElements;
            theCopy.manyItems = manyItems;
            theCopy.currentElement = currentElement-1;
        } catch (CloneNotSupportedException ex) {
            throw new RuntimeException("This class does not implement the Cloneable interface");
        }
        return theCopy;
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
        try{
            if (data.length < minimumCapacity) {

                double[] tmp = new double[minimumCapacity];
                System.arraycopy(data, 0, tmp, 0, this.size());
                data = tmp;
                //I don't think we need this?
                //Not adding elements
                // manyItems = minimumCapacity;
            }
        }catch(OutOfMemoryError er){
            System.out.println(er.getMessage());
        }
    }

    /**
     * Determines if this object is equal to the other object.
     *
     * @Override equals in class Object
     *
     * @return true if this object is equal to the other object, false
     *         otherwise.
     */
    //TODO: Compare fields in both objects
    public boolean equals(Object other) {
        boolean result = false;
        if (other == null) result = false;
        if(this.getClass() == other.getClass()){
            result = true;
        }
        return result;
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
        return data.length;
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
        } else {
            System.arraycopy(data,currentElement,data,currentElement-1,currentElement-getCapacity());
            currentElement = currentElement-1;
            numElements--;
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
        String st ="";
        if(data == null || numElements ==0){
            st = "<>";
        } else{
            st+="<";
            for(int i =0; i<numElements; i++){
                if(i == currentElement){
                    st+= "[" + data[i] + "]";
                }
                else{
                    st +=data[i];
                }
                if(i<numElements-1){
                    st += ", ";
                }
            }
            st+=">";
        }
        return st;
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
        try{
            //this needs to be fixed
            double[] datanew = new double[this.size()];
            System.arraycopy(data,0,datanew,0,this.size());
            data = datanew;
        }catch(OutOfMemoryError er){
            System.out.println(er.getMessage());
        }
    }

    /**
     * Sets the NO_CURRENT value to indicate that there is there is no 
     * current value selected.  -1 inicates there is no current value.
     *
     */
    private void NoCurrent() {
        currentElement = -1;
        this.NO_CURRENT = -1;
    }

    /**
     * Sets the currentElement to the index of the new current element,
     * and sets the NO_CURRENT value to indicate that there is a current
     * value.
     *
     * @param curIndex new current element index
     */
    private void Current(int curIndex) {
        this.currentElement = curIndex;
        this.NO_CURRENT = 1;
    }
}