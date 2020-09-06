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
        if (node == null) {
            AVLNode nodeToAdd = new AVLNode(value);
            if (doTransitions) {
                nodeToAdd.setMarked(true);
            }
            return (nodeToAdd);
        }

        if (doTransitions) {
            node.setStacked(true);
            addTransition(ADDING);
        }

        if (value < node.getValue()) {
            final AVLNode left = addRecursive(node.getLeft(), value, doTransitions);
            node.setLeft(left);
        } else if (value > node.getValue()) {
            final AVLNode right = addRecursive(node.getRight(), value, doTransitions);
            node.setRight(right);
        } else // Duplicate keys not allowed
            return node;

        /* 2. Update height of this ancestor node */
        final int leftHeight = height(node.getLeft());
        final int rightHeight = height(node.getRight());
        final int currentMaxHeight = Math.max(leftHeight,
                rightHeight);
        int newHeight = 1 + currentMaxHeight;
        node.setHeight(newHeight);


        /* 3. Get the balance factor of this ancestor
              node to check whether this node became
              unbalanced */
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && value < node.getLeft().getValue()) {
            if (doTransitions) {
                node.setStacked(true);
                addTransition(ADDING);
            }
            return rightRotate(node);
        }

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

        if (null == findNode(value, false)) {
            return false;
            // todo give notice that value does not exist.
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
        if (value < root.getValue()) {
            final AVLNode left = deleteRec(root.getLeft(), value);
            root.setLeft(left);
        }

            // If the value to be deleted is greater than the
            // root's value, then it lies in right subtree
        else if (value > root.getValue()) {
            final AVLNode right = deleteRec(root.getRight(), value);
            root.setRight(right);
        }

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

                System.out.println("TEST:");
                System.out.println(getDiagram());
            } else {

                // node with two children: Get the inorder
                // successor (smallest in the right subtree)
                AVLNode temp = findMinNode(root.getRight());

                // Copy the inorder successor's data to this node
                root.setValue(temp.getValue());

                // Delete the inorder successor
                final AVLNode right = deleteRec(root.getRight(), temp.getValue());
                root.setRight(right);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        final int heightLeft = height(root.getLeft());
        final int heightRight = height(root.getRight());
        final int newHeight = Math.max(heightLeft, heightRight) + 1;
        root.setHeight(newHeight);

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether
        // this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases
        // Left Left Case
        if (balance > 1 && getBalance(root.getLeft()) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.getLeft()) < 0) {
            final AVLNode left = leftRotate(root.getLeft());
            root.setLeft(left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.getRight()) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.getRight()) > 0) {
            final AVLNode right = rightRotate(root.getRight());
            root.setRight(right);
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

        AVLNode x = y.getLeft();
        AVLNode T2 = x.getRight();

        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);

        tempTree = new AVLTree();

        // Update heights
        countNewHeight(y, x);

        // Return new root
        //setRoot(x);
        return x;
    }

    // A utility function to left rotate subtree rooted with x
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode T2 = y.getLeft();

        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);
        countNewHeight(x, y);

        // Return new root
        //setRoot(y);
        return y;
    }

    private void countNewHeight(AVLNode x, AVLNode y) {
        //  Update heights
        final int heightLeftX = height(x.getLeft());
        final int heightRightX = height(x.getRight());
        final int newHeightX = Math.max(heightLeftX, heightRightX) + 1;
        x.setHeight(newHeightX);

        final int heightLeftY = height(y.getLeft());
        final int heightRightY = height(y.getRight());
        final int newHeightY = Math.max(heightLeftY, heightRightY) + 1;
        y.setHeight(newHeightY);
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

    public AVLNode findMinNode(AVLNode root) {
        if (null == root) {
            System.out.println("The tree is empty!");
            return null;
        }

        AVLNode tempNode = root;
        while (null != tempNode.getLeft()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MIN);
            tempNode = tempNode.getLeft();
        }
        return tempNode;
    }


    @Override
    public String toString(){
        return getDiagram();
        //return super.toString();
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
