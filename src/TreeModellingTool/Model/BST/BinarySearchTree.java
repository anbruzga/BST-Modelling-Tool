package TreeModellingTool.Model.BST;


import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.Tree;

import java.util.List;

public class BinarySearchTree extends AbstractBST implements Tree {

    private final String IDENTIFIER = "BST";

    // CONSTRUCTORS
    public BinarySearchTree(int rootVal) {
        super.identifier = IDENTIFIER;
        root = new BSTNode(rootVal);
    }

    public BinarySearchTree() {
        super.identifier = IDENTIFIER;
    }

    // PUBLIC
    @Override
    public void addNode(int value, boolean doTransitions) {
        root = addRecursive(root, value, doTransitions);
        if (doTransitions) {
            addTransition(ADDING);
            traverseAll(root, true, true);
        }
    }

    @Override
    public void addNode(Node node, boolean doTransitions) {
        int value = node.getValue();
        addNode(value, doTransitions);
    }

    @Override
    public boolean deleteNode(int value) {
        Node nodeToDelete = findNode(value, false);

        // returns false if node does not exists
        if (nodeToDelete == null) {
            return false;
        }

        deleteRec(root, value);
        addTransition(DELETING);
        traverseAll(root, true, true);

        return true;
    }

    // A recursive function to insert a new key in BST
    @Override
    protected Node deleteRec(Node root, int value) {
        // Base Case: If the tree is empty
        if (root == null) return root;

        root.setStacked(true);
        addTransition(DELETING);

        // Otherwise, recur down the tree
        if (value < root.getValue()) {
            root.setLeft(deleteRec(root.getLeft(), value));
        } else if (value > root.getValue()) {
            root.setRight(deleteRec(root.getRight(), value));
        }

        // if key is same as root's key, then This is the node
        // to be deleted
        else {
            root.setMarked(true);
            addTransition(DELETING);

            // node with only one child or no child
            if (root.getLeft() == null)
                return root.getRight();
            else if (root.getRight() == null)
                return root.getLeft();

            // node with two children: Get the inorder successor (smallest
            // in the right subtree)
            root.setValue(findMinNode(root.getRight()).getValue());

            // Delete the inorder successor
            root.setRight(deleteRec(root.getRight(), root.getValue()));
        }

        return root;
    }

    protected Node addRecursive(Node current, int value, Boolean doTransitions) {

        if (current == null) {
            Node node = new BSTNode(value);
            if (doTransitions) {
                node.setMarked(true);
            }
            return node;
        }

        if (doTransitions) {
            current.setStacked(true);
            addTransition(ADDING);
        }

        if (value < current.getValue()) {
            current.setLeft(addRecursive(current.getLeft(), value, doTransitions));
        } else if (value > current.getValue()) {
            current.setRight(addRecursive(current.getRight(), value, doTransitions));
        } else {
            // value already exists
            return current;
        }
        return current;
    }


    public String getDiagram(Tree tempBst) {
        // doing triangulation:
        // to save original in a deep copy
        Tree originalDeepCopy = deepCopy(this);

        // to get THIS instance as tempBst
        Tree tempThis = deepCopy(tempBst);

        // to get diagram from this
        String diagram = tempThis.getDiagram();

        // to reset this back to original this
        Tree thisTree = deepCopy(originalDeepCopy);
        thisTree = this; // not redundant as this reference matters

        // to return the diagram of tempBst
        return diagram;


    }

    @Override
    public Tree deepCopy(Tree toCopy) {

        Tree newTree = new BinarySearchTree();

        //make deep copy
        List<Integer> preOrderArgBst = toCopy.getPreOrder(false);

        for (Integer integer : preOrderArgBst) {
            newTree.addNode(integer, false);
        }
        return newTree;
    }

}
