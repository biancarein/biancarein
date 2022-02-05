/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Linda Deng (1/26/2022)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        int count = 0;
        if (_front == null){
            return count;
        } else {
            DNode curr_node = _front;
            while (curr_node != null) {
                count += 1;
                curr_node = curr_node._next;
            }
            return count;
        }
    }

    /**
     * @param index index of node to return,
     *          where index = 0 returns the first node,
     *          index = 1 returns the second node, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size.
     * @return The node at index index
     */
    private DNode getNode(int index) {
        DNode curr_node = _front;
        while (index > 0){
            curr_node = curr_node._next;
            index -= 1;
        }
        return curr_node;
    }

    /**
     * @param index index of element to return,
     *          where index = 0 returns the first element,
     *          index = 1 returns the second element,and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size.
     * @return The integer value at index index
     */
    public int get(int index) {
        return getNode(index)._val;
    }

    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        DNode new_front = new DNode(d);
        if (_front == null){
            _back = new_front;
            _front = new_front;
        } else {
            new_front._next = _front;
            _front._prev = new_front;
            _front = new_front;
        }
    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        DNode new_back = new DNode(d);
        if(_back == null){
            _front = new_back;
            _back = new_back;
        } else {
            _back._next = new_back;
            new_back._prev = _back;
            _back = new_back;
        }
    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position, and so onh.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size.
     */
    public void insertAtIndex(int d, int index) {
        DNode new_node = new DNode(d);
        if (size()== 0){
            _front = new_node;
            _back = new_node;
        } else {
            if(index == size()){
                DNode previous = getNode(index - 1);
                new_node._prev = previous;
                new_node._next = null;
                previous._next = new_node;
                _back = new_node;
            } else {
                DNode curr_node = getNode(index);
                new_node._prev = curr_node._prev;
                new_node._next = curr_node;
                if (index == 0) {
                    _front = new_node;
                } else {
                    curr_node._prev._next = new_node;
                    curr_node._prev = new_node;
                }
            }
        }
    }

    /**
     * Removes the first item in the IntDList and returns it.
     * Assume `deleteFront` is never called on an empty IntDList.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        int old_front = getFront();
        if (size() == 1){
            _front = null;
            _back = null;
            return old_front;
        } else{
            _front._next._prev = null;
            _front = _front._next;
           return old_front;
        }
    }

    /**
     * Removes the last item in the IntDList and returns it.
     * Assume `deleteBack` is never called on an empty IntDList.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        int old_back = getBack();
        if (size() == 1){
            _front = null;
            _back = null;
            return old_back;
        }else{
            _back._prev._next = null;
            _back = _back._prev;
            return old_back;
        }
    }

    /**
     * @param index index of element to be deleted,
     *          where index = 0 returns the first element,
     *          index = 1 will delete the second element, and so on.
     *          You can assume index will always be a valid index,
     *              i.e 0 <= index < size.
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        // TODO: Implement this method and return correct value
        return 0;
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        if (size() == 0) {
            return "[]";
        }
        String str = "[";
        DNode curr = _front;
        for (; curr._next != null; curr = curr._next) {
            str += curr._val + ", ";
        }
        str += curr._val +"]";
        return str;
    }

    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /** Previous DNode. */
        protected DNode _prev;
        /** Next DNode. */
        protected DNode _next;
        /** Value contained in DNode. */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }

}
