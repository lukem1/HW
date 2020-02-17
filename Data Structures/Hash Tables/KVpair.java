//
// KVpair.java
// Luke M
// November 26 2019
//

//
// The following is a modified version of the KVpair class that was provided on slack
//

class KVpair<Key extends Comparable, E>
{
    private Key k;
    private E e;
    /** Constructors */
    KVpair()
    { k = null; e = null; }
    KVpair(Key kval, E eval)
    { k = kval; e = eval; }  /** Data member access functions */
    public void setKey(Key k) { this.k = k; }
    public Key key() { return k; }
    public E value() { return e; }
}
