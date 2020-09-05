package BST_Tool.Model.AVL;

import BST_Tool.Model.BST.AbstractBST;
import BST_Tool.Model.Node;
import BST_Tool.Model.Tree;

public class AVLTree extends AbstractBST implements Tree {

    private final String IDENTIFIER = "AVL";

    public AVLTree(){
        super.identifier = IDENTIFIER;
    }

    // Get Balance factor of node N
    private int getBalance(AVLNode N) {
        if (N == null)
            return 0;

        return height((AVLNode) N.getLeft()) - height((AVLNode) N.getRight());
    }


    @Override
    public void addNode(Node node, boolean doTransitions) {
        int value = node.getValue();
        addNode(value, doTransitions);
    }

    @Override
    protected Node deleteRec(Node root, int value) {
        return null;
    }

    @Override
    protected Node addRecursive(Node node, int value, Boolean doTransitions) {
        // 1.  Perform the normal BST insertion
        if (node == null)
            return (new AVLNode(value));
        AVLNode avlNode = new AVLNode(node.getValue());


        if (value < node.getValue())
            avlNode.setLeft(addRecursive(avlNode.getLeft(), value, doTransitions));
        else if (value > node.getValue())
            avlNode.setRight(addRecursive(avlNode.getRight(), value, doTransitions));
        else // Duplicate keys not allowed
            return avlNode;

        // 2. Update height of this ancestor node
        avlNode.setHeight(1 + Math.max(height((AVLNode) avlNode.getLeft()),
                height((AVLNode) node.getRight())));

        //3. Get the balance factor of this ancestor
        //  node to check whether this node became unbalanced
        int balance = getBalance(avlNode);

        // If this node becomes unbalanced, then there
        // are 4 cases Left Left Case
        if (balance > 1 && value < avlNode.getLeft().getValue())
            return rightRotate(avlNode);

        // Right Right Case
        if (balance < -1 && value > avlNode.getRight().getValue())
            return leftRotate(avlNode);

        // Left Right Case
        if (balance > 1 && value > avlNode.getLeft().getValue()) {
            avlNode.setLeft(leftRotate((AVLNode) avlNode.getLeft()));
            return rightRotate(avlNode);
        }

        // Right Left Case
        if (balance < -1 && value < avlNode.getRight().getValue()) {
            avlNode.setRight(rightRotate((AVLNode) avlNode.getRight()));
            return leftRotate(avlNode);
        }

        // return the (unchanged) node pointer
        return avlNode;
    }

    @Override
    public void addNode(int value, boolean doTransitions) {
        AVLNode node = new AVLNode(value);
        addRecursive(node, value, doTransitions);
    }

    @Override
    public boolean deleteNode(int value) {
        return false;
    }

    @Override
    public String getDiagram(Tree tree) {
        return null;
    }

    @Override
    public Tree deepCopy(Tree toCopy) {
        return null;
    }


    // A utility function to get the height of the tree
    private int height(AVLNode node) {
        if (node == null)
            return 0;

        return node.getHeight();
    }


    // A utility function to right rotate subtree rooted with y
    // See the diagram given above.
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
    // See the diagram given above.
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
}
