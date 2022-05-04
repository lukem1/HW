//
// HashDictionary.java
// lukem1
// November 26 2019
//

//
// The following is a modified version of Clifford Shaffer's HashDictionary implementation
// Source: https://people.cs.vt.edu/~shaffer/Book/JAVA3elatest.pdf
//


/**Dictionary implemented using hashing.*/
public class HashDictionary<Key extends Comparable<? super Key>, E> implements Dictionary<Key, E>
{
    private static final int defaultSize = 10;
    private HashTable<Key,E> T; // The hash table
    private int count; // # of records now in table
    private int maxsize; // Maximum size of dictionary

    HashDictionary()
    {
        this(defaultSize);
    }

    HashDictionary(int sz)
    {
        T = new HashTable<Key,E>(sz);
        count = 0;
        maxsize = sz;
    }

    /**Reinitialize*/
    public void clear()
    {

        T = new HashTable<Key,E>(maxsize);
        count = 0;
    }

    /**Insert an element*/
    public void insert(Key k, E e)
    {
        assert count < maxsize : "Hash table is full";
        T.hashInsert(k, e);
        count++;
    }

    /**Remove an element*/
    public E remove(Key k)
    {

        E temp = T.hashRemove(k);
        if (temp != null) count--;
        return temp;
    }

    /**Remove some element.*/
    public E removeAny()
    {

        if (count != 0)
        {
            count--;
            return T.hashRemoveAny();
        }
        else return null;
    }

    /**Find a record with key value "k"*/
    public E find(Key k) { return T.hashSearch(k); }

    /**Return number of values in the hash table*/
    public int size() { return count; }

    /**Print the contents of the HashTable*/
    public void print() {T.tablePrint();}

    public int getProbe(){return T.getProbeCount();} // Used for testing
}
