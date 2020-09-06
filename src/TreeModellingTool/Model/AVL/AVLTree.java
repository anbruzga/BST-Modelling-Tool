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

    // Tracks the number of nodes inside the tree.
    private int nodeCount = 0;

    // The height of a rooted tree is the number of edges between the tree's
    // root and its furthest leaf. This means that a tree containing a single
    // node has a height of 0.
    public int height() {
        if (root == null) return 0;
        return root.getHeight();
    }

    // Returns the number of nodes in the tree.
    public int size() {
        return nodeCount;
    }

    // Returns whether or not the tree is empty.
    public boolean isEmpty() {
        return size() == 0;
    }

    // Return true/false depending on whether a value exists in the tree.
    public boolean contains(int value) {
        return contains(root, value);
    }

    private int compareTo(int value1, int value2){ //todo check if correct implementation
        if (value1 > value2){
            return 1;
        }
        else if(value1 < value2){
            return -1;
        }
        else return 0;
    }

    // Recursive contains helper method.
    private boolean contains(Node node, int value) {

        if (node == null) return false;

        // Compare current value to the value in the node.
        int cmp = compareTo(value, node.getValue());

        // Dig into left subtree.
        if (cmp < 0) return contains(node.getLeft(), value);

        // Dig into right subtree.
        if (cmp > 0) return contains(node.getRight(), value);

        // Found value in tree.
        return true;

    }

    @Override
    public void addNode(Node node, boolean doTransitions) {
        int value = node.getValue();
        addNode(value, doTransitions);
    }

    // Insert/add a value to the AVL tree. The value must not be null, O(log(n))
    @Override
    public void addNode(int value, boolean doTransitions){
        if (!contains(root, value)) {
            root = addRecursive(root, value, doTransitions);
            nodeCount++;
            super.root = this.root;
            if (doTransitions) {
                addTransition(ADDING);
                traverseAll(root, true, true);
            }
        }
    }

    // Inserts a value inside the AVL tree.
    private AVLNode addRecursive(AVLNode node, int value, boolean doTransitions) {

        // Base case.
        if (node == null) {
            AVLNode newNode = new AVLNode(value);
            if (doTransitions) {
                newNode.setMarked(true);
            }
            return newNode;
        }

        if (doTransitions) {
            node.setStacked(true);
            addTransition(ADDING);
        }

        // Compare current value to the value in the node.
        int cmp = compareTo(value, node.getValue());

        // Insert node in left subtree.
        if (cmp < 0) {
            node.setLeft(addRecursive(node.getLeft(), value, doTransitions));;

            // Insert node in right subtree.
        } else {
            node.setRight(addRecursive(node.getRight(), value, doTransitions));
        }

        // Update balance factor and height values.
        update(node);

        // Re-balance tree.
        return balance(node);

    }

    // Update a node's height and balance factor.
    private void update(AVLNode node) {

        int leftNodeHeight  = (node.getLeft()  == null) ? -1 : node.getLeft().getHeight();
        int rightNodeHeight = (node.getRight() == null) ? -1 : node.getRight().getHeight();

        // Update this node's height.
        node.setHeight(1 + Math.max(leftNodeHeight, rightNodeHeight));

        // Update balance factor.
        node.setBf(rightNodeHeight - leftNodeHeight);

    }

    // Re-balance a node if its balance factor is +2 or -2.
    private AVLNode balance(AVLNode node) {

        // Left heavy subtree.
        if (node.getBf() == -2) {

            // Left-Left case.
            if (node.getLeft().getBf() <= 0) {
                return leftLeftCase(node);

                // Left-Right case.
            } else {
                return leftRightCase(node);
            }

            // Right heavy subtree needs balancing.
        } else if (node.getBf() == +2) {

            // Right-Right case.
            if (node.getRight().getBf() >= 0) {
                return rightRightCase(node);

                // Right-Left case.
            } else {
                return rightLeftCase(node);
            }

        }

        // Node either has a balance factor of 0, +1 or -1 which is fine.
        return node;

    }

    private AVLNode leftLeftCase(AVLNode node) {
        return rightRotation(node);
    }

    private AVLNode leftRightCase(AVLNode node) {
        node.setLeft(leftRotation(node.getLeft()));
        return leftLeftCase(node);
    }

    private AVLNode rightRightCase(AVLNode node) {
        return leftRotation(node);
    }

    private AVLNode rightLeftCase(AVLNode node) {
        node.setRight(rightRotation(node.getRight()));
        return rightRightCase(node);
    }

    private AVLNode leftRotation(AVLNode node) {
        AVLNode newParent = node.getRight();
        node.setRight(newParent.getLeft());
        newParent.setLeft( node);
        update(node);
        update(newParent);
        return newParent;
    }

    private AVLNode rightRotation(AVLNode node) {
        AVLNode newParent = node.getLeft();
        node.setLeft(newParent.getRight());
        newParent.setRight(node);
        update(node);
        update(newParent);
        return newParent;
    }

    // Remove a value from this binary tree if it exists, O(log(n))
    @Override
    public boolean deleteNode(int elem) {

        if (contains(root, elem)) {
            root = deleteRec(root, elem);
            nodeCount--;
            super.root = this.root;

            addTransition(DELETING);
            traverseAll(root, true, true);

            return true;
        }

        return false;
    }

    // Removes a value from the AVL tree.
    private AVLNode deleteRec(AVLNode node, int elem) {

        if (node == null) return null;

        node.setStacked(true);
        addTransition(DELETING);

        int cmp = compareTo(elem, node.getValue());

        // Dig into left subtree, the value we're looking
        // for is smaller than the current value.
        if (cmp < 0) {
            node.setLeft(deleteRec(node.getLeft(), elem));

            // Dig into right subtree, the value we're looking
            // for is greater than the current value.
        } else if (cmp > 0) {
            node.setRight(deleteRec(node.getRight(), elem));

            // Found the node we wish to remove.
        } else {

            node.setMarked(true);
            addTransition(DELETING);

            // This is the case with only a right subtree or no subtree at all.
            // In this situation just swap the node we wish to remove
            // with its right child.
            if (node.getLeft() == null) {
                return node.getRight();

                // This is the case with only a left subtree or
                // no subtree at all. In this situation just
                // swap the node we wish to remove with its left child.
            } else if (node.getRight() == null) {
                return node.getLeft();

                // When removing a node from a binary tree with two links the
                // successor of the node being removed can either be the largest
                // value in the left subtree or the smallest value in the right
                // subtree. As a heuristic, I will remove from the subtree with
                // the most nodes in hopes that this may help with balancing.
            } else {

                // Choose to remove from left subtree
                if (node.getLeft().getHeight() > node.getRight().getHeight()) {

                    // Swap the value of the successor into the node.
                    int successorValue = findMax(node.getLeft());
                    node.setValue(successorValue);

                    // Find the largest node in the left subtree.
                    node.setLeft(deleteRec(node.getLeft(), successorValue));

                } else {

                    // Swap the value of the successor into the node.
                    int successorValue = findMin(node.getRight());
                    node.setValue(successorValue);

                    // Go into the right subtree and remove the leftmost node we
                    // found and swapped data with. This prevents us from having
                    // two nodes in our tree with the same value.
                    node.setRight(deleteRec(node.getRight(), successorValue));
                }
            }
        }

        // Update balance factor and height values.
        update(node);

        // Re-balance tree.
        return balance(node);

    }

    // Helper method to find the leftmost node (which has the smallest value)
    private int findMin(AVLNode node) {
        while(node.getLeft() != null)
            node = node.getLeft();
        return node.getValue();
    }

    // Helper method to find the rightmost node (which has the largest value)
    private int findMax(AVLNode node) {
        while(node.getRight() != null)
            node = node.getRight();
        return node.getValue();
    }


}
