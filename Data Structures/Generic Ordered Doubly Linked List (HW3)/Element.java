//
// Element.java
// Luke M
// October 8 2019
//

class Element<E extends Comparable>
{
    private E value;
    private Element<E> prev;
    private Element<E> next;

    Element(E value)
    {
        this.value = value;
    }

    Element(E value, Element<E> prev, Element<E> next)
    {
        this.value = value;
        this.prev = prev;
        this.next = next;
    }

    Element(Element<E> prev, Element<E> next)
    {
        this.prev = prev;
        this.next = next;
    }

    E getValue() { return value; }
    void setValue(E value) { this.value = value; }

    Element<E> getPrev() { return prev; }
    void setPrev(Element<E> prev) { this.prev = prev; }

    Element<E> getNext() { return next; }
    void setNext(Element<E> next) { this.next = next; }

}
