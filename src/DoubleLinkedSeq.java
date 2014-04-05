import java.lang.IllegalStateException;

/**
 * @author Logan Gillespie
 * @version 1.5
 */

public class DoubleLinkedSeq implements Sequence {

    private DoubleNode cursor;
    private DoubleNode head;
    private int manyNodes;
    private DoubleNode precursor;
    private DoubleNode tail;


    public DoubleLinkedSeq() {
        head = null;
        tail = null;
        cursor = null;
        manyNodes = 0;
        precursor = null;
    }

    public DoubleLinkedSeq(DoubleArraySeq seq) {

    }

    public static DoubleLinkedSeq concatenation(DoubleLinkedSeq s1,
                                                DoubleLinkedSeq s2) {
        DoubleLinkedSeq temp = new DoubleLinkedSeq();

        return temp;
    }

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
                //temp = new DoubleNode(element, cursor);
                if (cursor == null) {
                    cursor = head;
                    precursor = temp;
                    temp.setNext(cursor);
                    //cursor.setNext(cursor.getNext());
                    head = temp;
                    //cursor;
                    //head.setNext(cursor.getNext());
                    //precursor = head;
                    cursor = precursor;

                } else {
                    temp = new DoubleNode(element, null);
                    head.addNodeAfter(element);
                    //head.setNext(null);
                    cursor = head.getNext();
                    cursor = head;
                    //precursor = cursor;
                }
            }
            manyNodes++;
        }
    }

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

    @Override
    public void addAll(Sequence other) {

    }

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

    @Override
    public double getCurrent() {
        if (isCurrent()) {
            return cursor.getData();
        } else {
            throw new IllegalStateException("No current item");
        }
        //return 0;
    }

    @Override
    public boolean isCurrent() {
        return cursor != null;
    }

    @Override
    public void removeCurrent() {
        if (isCurrent()) {
            if (precursor == head) {
                head.setNext(cursor);
            } else {
                precursor.setNext(cursor.getNext());
            }
            // cursor.setNext(precursor.getNext().getNext());
            manyNodes--;
            //cursor = precursor;
        } else {
            throw new IllegalStateException("no current element");
        }

    }

    @Override
    public int size() {
        return manyNodes;
    }

    @Override
    public void start() {
        if (manyNodes > 0) {
            cursor = head;
            precursor = null;
        }
    }

    @Override
    public Sequence clone() {
        Sequence theCopy = null;
        try {
            theCopy = (Sequence) super.clone();
            DoubleNode tmp = head;
            while (tmp != null) {
                // theCopy.;
                tmp.setNext(tmp.getNext());
            }
        } catch (CloneNotSupportedException er) {
            System.out.println("Not cloneable");
        }
        return theCopy;
    }

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

    public boolean equals(Object other) {
        return true;
    }

    private void appendItem(StringBuilder sb, DoubleNode node) {

    }
}
