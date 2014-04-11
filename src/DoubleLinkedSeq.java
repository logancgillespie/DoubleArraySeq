/**
 * A DoubleLinkedSeq is a sequence of double numbers.  The sequence can have
 * a special "current element", which is specified and  accessed through four
 * methods that are not available in the IntArrayBag  class (start,
 * getCurrent, advance, and isCurrent).
 * <p/>
 * <b>Limitations:</b></br>
 * Beyond Integer.MAX_VALUE element, the size method does not work.
 *
 * @author Dr. William Kreahling
 * @author Logan Gillespie
 * @author Robert (Matt) Anger
 * @version March 16, 2011, 2014
 */

public class DoubleLinkedSeq implements Sequence {

    /**
     * Invariant of the DoubleArraySeq class:
     *   1. The number of elements in the sequences is in the instance
     *   variable manyItems.
     *   2. If there is a current element, then it returns a null pointer
     *   exception.
     **/

    /**
     * A reference to the node with the current element,
     * or null if there is  no current element.
     */
    private DoubleNode cursor;

    /**
     * The head of the linked list.
     */
    private DoubleNode head;

    /**
     * Keeps track of the number of nodes in this list.
     */
    private int manyNodes;

    /**
     * A reference to the node before the current element,
     * or null if there is no current element of if the  current element is
     * the first node in the list.
     */
    private DoubleNode precursor;

    /**
     * The tail of the linked list.
     */
    private DoubleNode tail;


    /**
     * Initializes an empty DoubleLinkedSeq.
     *
     * @postcondition This sequence is empty.
     */
    public DoubleLinkedSeq() {
        head = null;
        tail = null;
        cursor = null;
        manyNodes = 0;
        precursor = null;
    }

    /**
     * Initializes a double array sequence with same elements and number of
     * elements as the DoubleArraySeq parameter.
     *
     * @param seq a DoubleArraySeq
     * @postcondition A new DoubleLinkedSequence with the same number of
     * elements and contents as the sequence that was passed in has been
     * created.
     */
    public DoubleLinkedSeq(DoubleArraySeq seq) {
        this.head = new DoubleNode(seq.get(0), null);
        this.manyNodes++;
        DoubleNode tmp = this.head;
        for (int i = 1; i < seq.size(); i++) {
            tmp.setNext(new DoubleNode(seq.get(i), null));
            if (i == seq.size() - 1) {
                this.tail = tmp;
            }
            this.manyNodes++;
            tmp = tmp.getNext();
        }

    }

    /**
     * Creates a new sequence that contains all the elements from s1
     * followed by all of the elements from s2.
     *
     * @param s1 first of two sequences
     * @param s2 second of two sequences
     * @return a new sequence that has the elements of s1 followed by the
     * elements of s2 (with no current element).
     * @throws java.lang.NullPointerException if s1 or s2 are null
     * @precondition neither s1 or s2 are null
     */
    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1,
                                                DoubleLinkedSeq s2) {
        DoubleLinkedSeq temp = new DoubleLinkedSeq();
        if (s1 != null || s2 != null) {
            DoubleNode[] tmp = s2.head.listCopyWithTail(s2.head);
            DoubleNode[] tmp1 = s1.head.listCopyWithTail(s1.head);
            temp.head = tmp1[0];
            temp.tail = tmp1[1];
            temp.tail.setNext(tmp[0]);
            temp.tail = tmp[1];
            temp.manyNodes = s1.size() + s2.size();
        } else {
            throw new NullPointerException("A sequence is null!");
        }

        return temp;
    }

    /**
     * Adds a new element to this sequence.
     *
     * @param element the new element that is being added to this sequence.
     * @precondition a new copy of the element has been added to this
     * sequence. If there was a current element, then this method places the
     * new element before the current element. If there was no current
     * element, then this method places the new element at the front of this
     * sequence. The newly added element becomes the new current element of
     * this sequence.
     */
    @Override
    public void addBefore(double element) {
        if (manyNodes <= 0) {
            DoubleNode temp = new DoubleNode(element, null);
            head = temp;
            manyNodes++;
            cursor = head;
            precursor = head;
            tail = head;
        } else {
            DoubleNode temp;
            if (isCurrent()) {
                temp = new DoubleNode(element, cursor);
                if (cursor.getData() == head.getData()) {
                    head = temp;
                    cursor = head;
                    precursor = head;
                    tail = head.getNext();
                } else {
                    temp.setNext(precursor.getNext());
                    precursor.setNext(temp);
                    cursor = temp;
                }
            } else {
                temp = new DoubleNode(element, null);
                if (cursor == null) {
                    cursor = head;
                    precursor = temp;
                    temp.setNext(cursor);
                    head = temp;
                    cursor = precursor;

                } else {
                    head.addNodeAfter(element);
                    cursor = head.getNext();
                    cursor = head;
                }
            }
            manyNodes++;
        }
    }

    /**
     * Adds a new element to this sequence.
     *
     * @param element the new element that is being added to this sequence.
     * @postcondition a new copy of the element has been added to this
     * sequence. If there was a current element, then this method places the
     * new element after the current element. If there was no current
     * element, then this method places the new element at the end of this
     * sequence. The newly added element becomes the new current element of
     * this sequence.
     */
    @Override
    public void addAfter(double element) {
        if (manyNodes <= 0) {
            DoubleNode temp = new DoubleNode(element, null);
            head = temp;
            manyNodes++;
            cursor = head;
            precursor = head;
            tail = head.getNext();
        } else {
            DoubleNode temp;
            if (isCurrent()) {
                temp = new DoubleNode(element, cursor.getNext());
                cursor.setNext(temp);
                precursor = cursor;
                cursor = temp;
                tail = cursor;
            } else {
                tail.getNext().addNodeAfter(element);
                cursor = tail.getNext().getNext();
                tail = cursor.getNext();
                precursor = null;
            }

            manyNodes++;
        }
    }

    /**
     * Places the contents of another sequence at the end of this sequence.
     *
     * @param other a sequence show contents will be placed at the end of this
     *              sequence.
     * @throws java.lang.NullPointerException if other is null
     * @precondition other must nto be full
     * @postcondition the elements from other have been placed at the end of
     * this sequence. The current element of this sequence remains where it
     * was, and other is unchanged.
     */
    @Override
    public void addAll(Sequence other) {
        if (other == null)
            throw new NullPointerException("Provided sequence is null!");
        if (this.equals(other)) {
            DoubleLinkedSeq tmp = (DoubleLinkedSeq) other.clone();
            this.tail.setNext(tmp.head);
            this.tail = tmp.tail;
            this.manyNodes = this.manyNodes + tmp.manyNodes;
        } else {
            DoubleLinkedSeq tmp = (DoubleLinkedSeq) other;
            this.tail.setNext(tmp.head);
            this.tail = tmp.tail;
            this.manyNodes = this.manyNodes + tmp.manyNodes;
        }
    }

    /**
     * Move forward so that the current element is now the next element in the
     * sequence.
     *
     * @throws IllegalStateException if there is not current element.
     * @precondition <code>isCurrent()</code> returns <code>true</code>.
     * @postcondition If the current element was already the end element of
     * this sequence (with nothing after it),
     * then there is no longer any current element.  Otherwise,
     * the new element is the element immediately after the
     * original current  element.
     */
    @Override
    public void advance() {
        if (isCurrent()) {
            if (cursor.getNext() == null) {
                precursor = cursor;
                cursor = null;
            } else {
                precursor = cursor;
                cursor = cursor.getNext();
            }
        } else {
            throw new IllegalStateException("There is no current element!");
        }
    }

    /**
     * Returns a copy of the current element in this sequence.
     *
     * @return double if there is a current element.
     * @throws IllegalStateException - if there is no currentElement
     * @precondition isCurrent() returns true.
     */
    @Override
    public double getCurrent() {
        if (isCurrent()) {
            return cursor.getData();
        } else {
            throw new IllegalStateException("No current item");
        }
    }

    /**
     * Determines whether this sequence has specified a current element.
     *
     * @return <code>true</code> if there is a current element,
     * or <code>false</code> otherwise.
     */
    @Override
    public boolean isCurrent() {
        return cursor != null;
    }

    /**
     * Removes the current element from this sequence.
     *
     * @throws IllegalStateException if there is no current element.
     * @precondition <code>isCurrent()</code> returns true.
     * @postcondition The current element has been removed from this
     * sequence, and the following element (if there is
     * one) is now the new current element.  If there was no following
     * element, then there is now no current element.
     */
    @Override
    public void removeCurrent() {
        if (isCurrent()) {
            if (precursor == head) {
                head.setNext(cursor.getNext());
                cursor = head.getNext();
            } else if (precursor != null) {
                precursor.setNext(cursor.getNext());
                cursor = precursor.getNext();
            } else {
                head = cursor.getNext();
                cursor = head;
            }
            manyNodes--;
        } else {
            throw new IllegalStateException("no current element");
        }

    }

    /**
     * Determines the current number of elements stored in this sequence.
     *
     * @return the current number of elements stored in this sequence.
     */
    @Override
    public int size() {
        return manyNodes;
    }

    /**
     * Sets the current element at the front of this sequence.
     *
     * @postcondition If this sequence is not empty, the front element of
     * this sequence is now the current element;
     * otherwise, there is no current element.
     */
    @Override
    public void start() {
        if (manyNodes > 0) {
            cursor = head;
            precursor = null;
        }
    }

    /**
     * Creates a copy of this sequence.
     *
     * @return a copy of this sequence.  Subsequent changes to the copy will
     * not affect the original, nor vice versa.
     * @throws java.lang.CloneNotSupportedException if the object isn't
     *                                              cloneable
     * @throws RuntimeException if this class does not implement Cloneable.
     */
    @Override
    public Sequence clone() {
        DoubleLinkedSeq theCopy = null;
        try {
            theCopy = (DoubleLinkedSeq) super.clone();
            DoubleNode[] tmp = this.head.listCopyWithTail(this.head);
            theCopy.head = tmp[0];
            theCopy.tail = tmp[1];

            if (this.isCurrent()) {
                DoubleNode next = this.head;
                DoubleNode copyNext = theCopy.head;
                while (next != null) {
                    next = next.getNext();
                    copyNext = copyNext.getNext();
                    if (next == this.precursor) {
                        theCopy.precursor = copyNext;
                    }
                    if (next == this.cursor) {
                        theCopy.cursor = copyNext;
                        next = null;
                    }
                }

            }

        } catch (CloneNotSupportedException cnse) {
            System.out.println("Clone not supported!");
        } catch (RuntimeException er) {
            System.out.println("Not cloneable");
        }
        return theCopy;
    }

    /**
     * Returns a String representation of this sequence.  If the sequence is
     * empty, the method should return &quot;&lt;&gt;&quot;.  If the sequence
     * has one item, say 1.1, and that item is not the current item, the method
     * should return &quot;&lt;1.1&gt;&quot;.  If the sequence has more than one
     * item, they should be separated by commas, for example:
     * &quot;&lt;1.1, 2.2, 3.3&gt;&quot;.  If there exists a current item, then
     * that item should be surrounded by square braces.  For example, if the
     * second item is the current item, the method should return: &quot;&lt;1
     * .1, [2.2], 3.3&gt;&quot;.
     *
     * @return a String representation of this sequence.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");
        if (manyNodes > 0) {
            if (isCurrent() && head.getData() == cursor.getData()) {
                sb.append("[");
                sb.append(head.getData());
                sb.append("]");
            } else {
                sb.append(head.getData());
            }
            for (DoubleNode i = head.getNext(); i != null; i = i.getNext
                    ()) {
                if (i == cursor) {
                    sb.append(", ").append("[");
                    sb.append(i.getData());
                    sb.append("]");
                } else {
                    sb.append(", ").append(i.getData());
                }
            }
        }
        sb.append(">");
        return sb.toString();
    }

    /**
     * Determines if this object is equal to the other object.
     *
     * @return true if this object is equal to the other object,
     * false otherwise.
     */
    public boolean equals(Object other) {
        boolean result = true;
        if (other == null) {
            result = false;
        } else if (other instanceof DoubleLinkedSeq) {
            DoubleLinkedSeq checkObj = (DoubleLinkedSeq) other;
            if (this.size() > checkObj.size() ||
                    checkObj.size() > this.size()) {
                result = false;
            } else if (this.isCurrent() && checkObj.isCurrent()) {

                if (this.cursor.getData() != checkObj.cursor.getData()) {
                    result = false;
                }
            } else if ((!this.isCurrent() && checkObj.isCurrent()) ||
                    (this.isCurrent() && !checkObj.isCurrent())) {
                result = false;
            } else if (this.size() > 0 && checkObj.size() > 0) {
                DoubleNode chk1 = ((DoubleLinkedSeq) other).head;
                DoubleNode chk2 = this.head;
                while (chk2 != null) {
                    if (chk1.getData() != chk2.getData()) {
                        result = false;
                    }
                    chk1 = chk1.getNext();
                    chk2 = chk2.getNext();
                }
            }
        }
        return result;
    }
}
