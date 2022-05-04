//
// BSTNode.java
// lukem1
// October 24 2019
//

public class BSTNode<E extends Comparable<E>>
{
        private E value;
        private BSTNode<E> left;
        private BSTNode<E> right;

        BSTNode(E value)
        {
            this.value = value;
        }

        BSTNode(E value, BSTNode<E> left, BSTNode<E> right)
        {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        Comparable getValue() { return value; }
        void setValue(E value) { this.value = value; }

        BSTNode<E> getLeft() { return left; }
        void setLeft(BSTNode<E> left) { this.left = left; }

        BSTNode<E> getRight() { return right; }
        void setRight(BSTNode<E> right) { this.right = right; }

        public boolean isLeaf()
        {
            return (left == null && right == null);
        }
}
