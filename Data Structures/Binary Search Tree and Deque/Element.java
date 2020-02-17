//
// Element.java
// Luke M
// October 24 2019
//

class Element<E extends Comparable<E>>
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

    public E getValue() { return value; }
    public void setValue(E value) { this.value = value; }

    public Element<E> getPrev() { return prev; }
    public void setPrev(Element<E> prev) { this.prev = prev; }

    public Element<E> getNext() { return next; }
    public void setNext(Element<E> next) { this.next = next; }
}
