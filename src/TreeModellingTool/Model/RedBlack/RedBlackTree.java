package TreeModellingTool.Model.RedBlack;

import TreeModellingTool.Model.BST.BinarySearchTree;
import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.Tree;

public class RedBlackTree extends BinarySearchTree implements Tree {

    private final String IDENTIFIER = "RBT";
    private RedBlackNode root;
    private RedBlackNode TNULL;


    public RedBlackTree(){
        super.identifier = IDENTIFIER;
        TNULL = new RedBlackNode();
        TNULL.setColor(0);
        TNULL.setLeft(null);
        TNULL.setRight(null);
        TNULL.setValue(Integer.MIN_VALUE);
        root = TNULL;
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
            insert(value);
          //  root = addRecursive(root, value, doTransitions);
            super.root = this.root;
            if (doTransitions) {
                addTransition(ADDING);
                traverseAll(root, true, true);
            }
        }
    }

    @Override
    public boolean deleteNode(int elem) {

        if (contains(root, elem)) {
            deleteNodeHelper(this.root, elem);
            super.root = this.root;

            addTransition(DELETING);
            traverseAll(root, true, true);

            return true;
        }

        return false;
    }


    public RedBlackNode getRoot(){
        return this.root;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    // print the tree structure on the screen
    public void prettyPrint() {
        printHelper(this.root, "", true);
    }


    private void preOrderHelper(RedBlackNode node) {
        if (node != TNULL) {
            System.out.print(node.getValue() + " ");
            preOrderHelper(node.getLeft());
            preOrderHelper(node.getRight());
        }
    }

    private void inOrderHelper(RedBlackNode node) {
        if (node != TNULL) {
            inOrderHelper(node.getLeft());
            System.out.print(node.getValue() + " ");
            inOrderHelper(node.getRight());
        }
    }

    private void postOrderHelper(RedBlackNode node) {
        if (node != TNULL) {
            postOrderHelper(node.getLeft());
            postOrderHelper(node.getRight());
            System.out.print(node.getValue() + " ");
        }
    }

    private RedBlackNode searchTreeHelper(RedBlackNode node, int key) {
        if (node == TNULL || key == node.getValue()) {
            return node;
        }

        if (key < node.getValue()) {
            return searchTreeHelper(node.getLeft(), key);
        }
        return searchTreeHelper(node.getRight(), key);
    }

    // fix the rb tree modified by the delete operation
    private void fixDelete(RedBlackNode x) {
        RedBlackNode s;
        while (x != root && x.getColor() == 0) {
            if (x == x.getParent().getLeft()) {
                s = x.getParent().getRight();
                if (s.getColor() == 1) {
                    // case 3.1
                    s.setColor(0);
                    x.getParent().setColor(1);
                    leftRotate(x.getParent());
                    s = x.getParent().getRight();
                }

                if (s.getLeft().getColor() == 0 && s.getRight().getColor() == 0) {
                    // case 3.2
                    s.setColor(1);
                    x = x.getParent();
                } else {
                    if (s.getRight().getColor() == 0) {
                        // case 3.3
                        s.getLeft().setColor(0);
                        s.setColor(1);
                        rightRotate(s);
                        s = x.getParent().getRight();
                    }

                    // case 3.4
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(0);
                    s.getRight().setColor(0);
                    leftRotate(x.getParent());
                    x = root;
                }
            } else {
                s = x.getParent().getLeft();
                if (s.getColor() == 1) {
                    // case 3.1
                    s.setColor(0);
                    x.getParent().setColor(1);
                    rightRotate(x.getParent());
                    s = x.getParent().getLeft();
                }

                if (s.getRight().getColor() == 0 && s.getRight().getColor() == 0) {
                    // case 3.2
                    s.setColor(1);
                    x = x.getParent();
                } else {
                    if (s.getLeft().getColor() == 0) {
                        // case 3.3
                        s.getRight().setColor(0);
                        s.setColor(1);
                        leftRotate(s);
                        s = x.getParent().getLeft();
                    }

                    // case 3.4
                    s.setColor(x.getParent().getColor());
                    x.getParent().setColor(0);
                    s.getLeft().setColor(0);
                    rightRotate(x.getParent());
                    x = root;
                }
            }
        }
        x.setColor(0);
    }


    private void rbTransplant(RedBlackNode u, RedBlackNode v){
        if (u.getParent() == null) {
            root = v;
        } else if (u == u.getParent().getLeft()){
            u.getParent().setLeft(v);
        } else {
            u.getParent().setRight(v);
        }
        v.setParent(u.getParent());
    }

    private void deleteNodeHelper(RedBlackNode node, int key) {
        // find the RedBlackNode containing key
        RedBlackNode z = TNULL;
        RedBlackNode x, y;
        while (node != TNULL){
            if (node.getValue() == key) {
                z = node;
            }

            if (node.getValue() <= key) {
                node = node.getRight();
            } else {
                node = node.getLeft();
            }
        }

        if (z == TNULL) {
            System.out.println("Couldn't find key in the tree");
            return;
        }

        y = z;
        int yOriginalColor = y.getColor();
        if (z.getLeft() == TNULL) {
            x = z.getRight();
            rbTransplant(z, z.getRight());
        } else if (z.getRight() == TNULL) {
            x = z.getLeft();
            rbTransplant(z, z.getLeft());
        } else {
            y = minimum(z.getRight());
            yOriginalColor = y.getColor();
            x = y.getRight();
            if (y.getParent() == z) {
                x.setParent(y);
            } else {
                rbTransplant(y, y.getRight());
                y.setRight(z.getRight());
                y.getRight().setParent(y);
            }

            rbTransplant(z, y);
            y.setLeft(z.getLeft());
            y.getLeft().setParent(y);
            y.setColor(z.getColor());
        }
        if (yOriginalColor == 0){
            fixDelete(x);
        }
    }

    // fix the red-black tree
    private void fixInsert(RedBlackNode k){
        RedBlackNode u;
        while (k.getParent().getColor() == 1) {
            if (k.getParent() == k.getParent().getParent().getRight()) {
                u = k.getParent().getParent().getLeft(); // uncle
                if (u.getColor() == 1) {
                    // case 3.1
                    u.setColor(0);
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getLeft()) {
                        // case 3.2.2
                        k = k.getParent();
                        rightRotate(k);
                    }
                    // case 3.2.1
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    leftRotate(k.getParent().getParent());
                }
            } else {
                u = k.getParent().getParent().getRight(); // uncle

                if (u.getColor() == 1) {
                    // mirror case 3.1
                    u.setColor(0);
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    k = k.getParent().getParent();
                } else {
                    if (k == k.getParent().getRight()) {
                        // mirror case 3.2.2
                        k = k.getParent();
                        leftRotate(k);
                    }
                    // mirror case 3.2.1
                    k.getParent().setColor(0);
                    k.getParent().getParent().setColor(1);
                    rightRotate(k.getParent().getParent());
                }
            }
            if (k == root) {
                break;
            }
        }
        root.setColor(0);
    }

    private void printHelper(RedBlackNode root, String indent, boolean last) {
        // print the tree structure on the screen
        if (root != TNULL) {
            System.out.print(indent);
            if (last) {
                System.out.print("R----");
                indent += "     ";
            } else {
                System.out.print("L----");
                indent += "|    ";
            }

            String color = root.getColor() == 1?"RED":"BLACK";
            System.out.println(root.getValue() + "(" + color + ")");
            printHelper(root.getLeft(), indent, false);
            printHelper(root.getRight(), indent, true);
        }
    }
    

    // Pre-Order traversal
    // Node.getLeft() Subtree.getRight() Subtree
    public void preorder() {
        preOrderHelper(this.root);
    }

    // In-Order traversal
    // getLeft() Subtree . RedBlackNode . Right() Subtree
    public void inorder() {
        inOrderHelper(this.root);
    }

    // Post-Order traversal
    // getLeft() Subtree . Right() Subtree . Node
    public void postorder() {
        postOrderHelper(this.root);
    }

    // search the tree for the key k
    // and return the corresponding node
    public RedBlackNode searchTree(int k) {
        return searchTreeHelper(this.root, k);
    }

    // find the RedBlackNode with the minimum key
    public RedBlackNode minimum(RedBlackNode node) {
        while (node.getLeft() != TNULL) {
            node = node.getLeft();
        }
        return node;
    }

    // find the RedBlackNode with the maximum key
    public RedBlackNode maximum(RedBlackNode node) {
        while (node.getRight() != TNULL) {
            node = node.getRight();
        }
        return node;
    }

    // find the successor of a given node
    public RedBlackNode successor(RedBlackNode x) {
        // if the Right() subtree is not null,
        // the successor is the getLeft()most RedBlackNode in the
        // Right() subtree
        if (x.getRight() != TNULL) {
            return minimum(x.getRight());
        }

        // else it is the lowest ancestor of x whose
        // getLeft() child is also an ancestor of x.
        RedBlackNode y = x.getParent();
        while (y != TNULL && x == y.getRight()) {
            x = y;
            y = y.getParent();
        }
        return y;
    }

    // find the predecessor of a given node
    public RedBlackNode predecessor(RedBlackNode x) {
        // if the getLeft() subtree is not null,
        // the predecessor is the Right()most RedBlackNode in the
        // getLeft() subtree
        if (x.getLeft() != TNULL) {
            return maximum(x.getLeft());
        }

        RedBlackNode y = x.getParent();
        while (y != TNULL && x == y.getLeft()) {
            x = y;
            y = y.getParent();
        }

        return y;
    }

    // rotate getLeft() at RedBlackNode x
    public void leftRotate(RedBlackNode x) {
        RedBlackNode y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != TNULL) {
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }
        y.setLeft(x);
        x.setParent(y);
    }

    // rotate Right() at RedBlackNode x
    public void rightRotate(RedBlackNode x) {
        RedBlackNode y = x.getLeft();
        x.setLeft(y.getRight());
        if (y.getRight() != TNULL) {
            y.getRight().setParent(x);
        }
        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getRight()) {
            x.getParent().setRight(y);
        } else {
            x.getParent().setLeft(y);
        }
        y.setRight(x);
        x.setParent(y);
    }

    // insert the key to the tree in its appropriate position
    // and fix the tree
    public void insert(int key) {
        // Ordinary Binary Search Insertion
        RedBlackNode node = new RedBlackNode();
        node.setParent(null);
        node.setValue(key);
        node.setLeft(TNULL);
        node.setRight(TNULL);
        node.setColor(1); // new RedBlackNode must be red

        RedBlackNode y = null;
        RedBlackNode x = this.root;

        while (x != TNULL) {
            y = x;
            if (node.getValue() < x.getValue()) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        // y is getParent() of x
        node.setParent(y);
        if (y == null) {
            root = node;
        } else if (node.getValue() < y.getValue()) {
            y.setLeft(node);
        } else {
            y.setRight(node);
        }

        // if new RedBlackNode is a root node, simply return
        if (node.getParent() == null){
            node.setColor(0);
            return;
        }

        // if the grandgetParent() is null, simply return
        if (node.getParent().getParent() == null) {
            return;
        }

        // Fix the tree
        fixInsert(node);
    }


}
