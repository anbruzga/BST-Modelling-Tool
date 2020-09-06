package TreeModellingTool.Model.AVL;

import TreeModellingTool.Model.BST.AbstractBST;
import TreeModellingTool.Model.BST.BinarySearchTree;
import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.Tree;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AVLTree extends BinarySearchTree implements Tree {

    private final String IDENTIFIER = "AVL";
    private AVLNode root;

    public AVLTree() {
        super.identifier = IDENTIFIER;
    }

    // Get Balance factor of node N
    private int getBalance(AVLNode N) {
        if (N == null)
            return 0;

        return height(N.getLeft()) - height(N.getRight());
    }


    @Override
    public void addNode(Node node, boolean doTransitions)
    {
        int value = node.getValue();
        addNode(value, doTransitions);
    }

    @Override
    public void addNode(int value, boolean doTransitions) {
        root = addRecursive(root, value, doTransitions);
        super.root = this.root;
    }

    protected AVLNode addRecursive(AVLNode node, int value, Boolean doTransitions) {
        /* 1.  Perform the normal BST insertion */
        if (node == null)
            return (new AVLNode(value));

        if (value < node.getValue()) {
            final AVLNode left = addRecursive(node.getLeft(), value, doTransitions);
            node.setLeft(left);
        } else if (value > node.getValue()) {
            final AVLNode right = addRecursive(node.getRight(), value, doTransitions);
            node.setRight(right);
        } else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        int newHeight = 1 + Math.max(height(node.getLeft()),
                height(node.getRight()));
        node.setHeight(newHeight);



        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && value    < node.getLeft().getValue())
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && value > node.getRight().getValue())
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && value > node.getLeft().getValue()) {
            final AVLNode left = leftRotate(node.getLeft());
            node.setLeft(left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && value < node.getRight().getValue()) {
            final AVLNode right = rightRotate(node.getRight());
            node.setRight(right);
            return leftRotate(node);
        }

        /* return the (unchanged) node pointer */
        return node;
    }

    @Override
    public boolean deleteNode(int value) {

        if (null != findNode(value, false)) {
            return false;
            // todo give notice that value does not exist.
            // Give transitions for search
        }

        root = deleteRec(root, value);

        return true;
    }

    protected AVLNode deleteRec(AVLNode root, int value) {
        // STEP 1: PERFORM STANDARD BST DELETE
        if (root == null)
            return root;

        // If the value to be deleted is smaller than
        // the root's value, then it lies in left subtree
        if (value < root.getValue())
            root.setLeft(deleteRec(root.getLeft(), value));

            // If the value to be deleted is greater than the
            // root's value, then it lies in right subtree
        else if (value > root.getValue())
            root.setRight(deleteRec(root.getRight(), value));

            // if value is same as root's value, then this is the node
            // to be deleted
        else {

            // node with only one child or no child
            if ((root.getLeft() == null) || (root.getRight() == null)) {
                AVLNode temp = null;
                if (temp == root.getLeft())
                    temp = root.getRight();
                else
                    temp = root.getLeft();

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of
                // the non-empty child
            } else {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                Node temp = findMinNode(root.getRight());

                // Copy the inorder successor's data to this node
                root.setValue(temp.getValue());

                // Delete the inorder successor
                root.setRight(deleteRec(root.getRight(), temp.getValue()));
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.setHeight(Math.max(height(root.getLeft()), height(root.getRight())) + 1);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            root.setLeft(leftRotate(root.getLeft()));
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0) {
            root.setRight(rightRotate(root.getRight()));
            return leftRotate(root);
        }

        return root;
    }

    @Override
    public String getDiagram(Tree tree) {
        throw new NotImplementedException();
    }

    @Override
    public Tree deepCopy(Tree toCopy) {
        throw new NotImplementedException();
    }


    // A utility function to get the height of the tree
    private int height(AVLNode node) {
        if (node == null)
            return 0;

        return node.getHeight();
    }

    // A utility function to right rotate subtree rooted with y
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = (AVLNode) y.getLeft();
        AVLNode T2 = (AVLNode) x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        // Update heights
        y.setHeight(Math.max(height((AVLNode) y.getLeft()), height((AVLNode) y.getRight())) + 1);
        x.setHeight(Math.max(height((AVLNode) x.getLeft()), height((AVLNode) x.getRight())) + 1);

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = (AVLNode) x.getRight();
        AVLNode T2 = (AVLNode) y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);

        //  Update heights
        x.setHeight(Math.max(height((AVLNode) x.getLeft()), height((AVLNode) x.getRight())) + 1);
        y.setHeight(Math.max(height((AVLNode) y.getLeft()), height((AVLNode) y.getRight())) + 1);

        // Return new root
        return y;
    }

    @Override
    public AVLNode getRoot(){
        return root;
    }


    protected List<Integer> inOrder() {
        List<Integer> inOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> inOrderList = inOrder(root, inOrderListToPopulate);
        traverseAll(root, true, true);
        return inOrderList;
    }

    protected List<Integer> inOrder(AVLNode temp, List<Integer> listToPopulate) {

        if (null == temp)
            return null;

        temp.setStacked(true);
        addTransition(INORDER);

        // recur on left child
        inOrder(temp.getLeft(), listToPopulate);

        temp.setMarked(true);
        addTransition(INORDER);

        // add the data of node
        listToPopulate.add(temp.getValue());

        // recur on right child

        inOrder(temp.getRight(), listToPopulate);

        return listToPopulate;
    }


    @Override
    public String toString(){
        return super.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        AVLTree avlTree = (AVLTree) o;

        return Objects.equals(root, avlTree.root);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (root != null ? root.hashCode() : 0);
        return result;
    }
}
