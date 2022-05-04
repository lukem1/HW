//
// HW4.java
// lukem1
// October 24 2019
//

public class HW4
{
    public static void main(String[] args) {
        // Deque Tests
        System.out.println("---Tests For The deque Class---");
        deque myDeque = new deque();
        String[] myStrs = {"Hello", "World"};
        int[] myInts = {42, 33};
        char[] myChars = {'a', 'b'};
        for (int i = 0; i < myStrs.length; i++) {
            myDeque.inject(myStrs[i]);
        }
        for (int i = 0; i < myInts.length; i++)
        {
            myDeque.inject(myInts[i]);
        }
        for (int i = 0; i < myChars.length; i++)
        {
            myDeque.inject(myChars[i]);
        }
        System.out.println("Contents of deque after injecting values:");
        myDeque.printList();
        for (int i = myDeque.length(); i > 0; i--) {
            System.out.println("Ejected Value: " + myDeque.eject());
        }
        System.out.println("Contents of deque after ejecting values:");
        myDeque.printList();
        for (int i = 0; i < myStrs.length; i++) {
            myDeque.push(myStrs[i]);
        }
        for (int i = 0; i < myInts.length; i++)
        {
            myDeque.push(myInts[i]);
        }
        for (int i = 0; i < myChars.length; i++)
        {
            myDeque.push(myChars[i]);
        }
        System.out.println("Contents of deque after pushing values:");
        myDeque.printList();
        myDeque.push("push");
        myDeque.inject("inject");
        System.out.println("Contents of deque after pushing 'push' and injecting 'inject':");
        myDeque.printList();
        for (int i = myDeque.length(); i > 0; i--)
        {
            System.out.println("Popped Value: " + myDeque.pop());
        }
        // BST Tests
        System.out.println("---Tests For The BST Class---");
        BST myTree = new BST();

        int[] myOtherInts = {47, 16, 82, 57, 50, 62, 24, 24, 15, 63};
        for (int i: myOtherInts)
        {
            myTree.insert(i);
        }
        System.out.println("Integer Tree myTree:");
        System.out.print("Preorder Traversal of myTree: ");
        myTree.preorderPrint();
        System.out.println();
        System.out.print("Parents of one in myTree: ");
        System.out.println(myTree.countParentsOfOne());
        System.out.println("Print level-order traversal:");
        myTree.printTreeLevelOrder();
        char[] myOtherChars = {'E', 'G', 'F', 'K', 'J', 'L', 'M', 'B', 'A', 'C'};
        myTree = new BST();
        for (char c: myOtherChars)
        {
            myTree.insert(c);
        }
        System.out.println("Character Tree myTree:");
        System.out.print("Preorder traversal of myTree: ");
        myTree.preorderPrint();
        System.out.println();
        System.out.print("Parents of one in myTree: ");
        System.out.println(myTree.countParentsOfOne());
        System.out.println("Print level-order traversal:");
        myTree.printTreeLevelOrder();
    }
}
