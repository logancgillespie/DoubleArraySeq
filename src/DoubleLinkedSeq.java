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
            //tail = head.getNext();
        } else {
            DoubleNode temp;
            if (isCurrent()) {
                temp = new DoubleNode(element, cursor);
                //precursor = temp;
                precursor.setNext(temp);

            } else {
                temp = new DoubleNode(element, null);
                head.addNodeAfter(element);
                cursor = head.getNext();
                cursor = head;
                //precursor = cursor;
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
                //temp = new DoubleNode(element, null);

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
        }
        return 0;
    }

    @Override
    public boolean isCurrent() {
        return cursor != null;
    }

    @Override
    public void removeCurrent() {

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
        return theCopy;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("<");

        if (manyNodes > 0) {
            if (isCurrent() && head.getData() == cursor.getData() && head
                    .getData() != 0) {
                sb.append("[");
                sb.append(head.getData());
                sb.append("]");
            } else {
                sb.append(head.getData());
            }
            for (DoubleNode i = head.getNext(); i != null; i = i.getNext()) {
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
