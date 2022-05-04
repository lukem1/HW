//
// HashTable.java
// lukem1
// November 26 2019
//

import java.util.Random;

public class HashTable<Key extends Comparable, E>
{
    private KVpair[] HT;
    // Empty Slot: null
    private final String EMPTYSTRING = "EmPtY";
    private int M;

    // Permutation for pseudo random probing
    private int[] permutation;

    // Public instance var to allow for keeping track of probing
    private int probeCount;
    public int getProbeCount() {return probeCount;}

    HashTable(int size)
    {
        M = size;
        HT = new KVpair[M];

        // Create permutation for pseudo random probing
        Random rand = new Random();
        //rand.setSeed(42); // Seeded for consistent testing
        permutation = new int[M];
        for (int i = 0; i < M; i++)
        {
            permutation[i] = i;
        }

        for (int i = 0; i < M; i++)
        {
            int randpos = rand.nextInt(M);
            int temp = permutation[i];
            permutation[i] = permutation[randpos];
            permutation[randpos] = temp;
        }
        /*
        for (int i = 0; i < M; i++)
        {
            System.out.print(permutation[i] + ", ");
        }
        System.out.println();
        */
    }

    /**Hash Key k and return position*/
    int h(Key k)
    {
        int code = k.hashCode();
        if (code < 0) code *= -1;
        return code % M;
    }

    // Probing Functions
    // Note: Only one method should be uncommented at a given time

    /**Linear Probing given Key k and sequence number i*/
    //*
    private int p(int i)
    {
        return i;
    }
    //*/
    /**Quadratic Probing given Key k and sequence number i*/
    /*
    private int p(int i)
    {
        // Standard quadratic probing function
        // Does not visit every slot in the table, resulting in failed inserts into tables with space remaining
        //return i*i;
        // Improved Quadratic probing function
        // With a hash table that has a size that is a power of 2 this function will visit every spot
        return ((i*i)+i)/2;
    }
    //*/
    /**Pseudo random Probing given Key k and sequence number i*/
    /*
    private int p(int i)
    {
        return permutation[i];
    }
    //*/

    /**Remove record with Key k from HT*/
    E hashRemove(Key k)
    {
        int home; // Home position for k
        int pos = home = h(k); // Initial position
        probeCount = 1;
        for (int i = 1; (HT[pos] != null) && (HT[pos].key().compareTo(k) != 0); i++, probeCount++) {
            pos = (home + p(i)) % M;   // Next probe position
            if (pos < 0) return null;
        }
        if (HT[pos] == null) return null; // Key not in hash table
        else // Found it
        {
            E val = (E) HT[pos].value();
            HT[pos].setKey(EMPTYSTRING);
            return val;
        }
    }

    /**Remove any record?*/
    E hashRemoveAny()
    {
        // This removes the earliest KVpair in the HT
        int i = 0;
        while ((HT[i] != null) && (HT[i].key().compareTo(EMPTYSTRING) != 0))
            i++;

        if (HT[i] == null) return null; // Key not in hash table
        else // Found it
        {
            E val = (E) HT[i].value();
            HT[i].setKey(EMPTYSTRING);
            return val;
        }
    }

    // The following two methods were taken from Clifford Shaffer's implementation
    // Source: https://people.cs.vt.edu/~shaffer/Book/JAVA3elatest.pdf

    /**Insert record r with key k into HT*/
    void hashInsert(Key k, E r)
    {
        if (k == EMPTYSTRING) {
            System.out.println("!!! Insertion with reserved key, call ignored.");
            return; // Key EMPTYSTRING is reserved for tombstones
        }
        int home; // Home position for r
        probeCount = 1;
        int pos = home = h(k); // Initial position

        for (int i = 1; HT[pos] != null && HT[pos].key() != EMPTYSTRING; i++) {
            probeCount += 1;
            pos = (home + p(i)) % M; // Next probe slot
            if (pos < 0)
            {
                System.out.println("!!! Insert Failed");
                return;
            }
            assert HT[pos].key().compareTo(k) != 0 : "Duplicates not allowed";
        }
        HT[pos] = new KVpair<Key, E>(k, r); // Insert R
    }

    /**Search in hash table HT for the record with key k*/
    E hashSearch(Key k)
    {
        int home; // Home position for k
        int pos = home = h(k); // Initial position
        for (int i = 1; (HT[pos] != null) && (HT[pos].key().compareTo(k) != 0); i++)
            pos = (home + p(i)) % M;   // Next probe position
        if (HT[pos] == null) return null; // Key not in hash table
        else return (E) HT[pos].value(); // Found it
    }

    public void tablePrint()
    {
        for (int i = 0; i < M; i++)
        {
            if (HT[i] == null)
                System.out.print("| null ");
            else
                System.out.print("| <"+HT[i].key()+", "+HT[i].value()+"> ");
        }
        System.out.println(" |");
    }
}
