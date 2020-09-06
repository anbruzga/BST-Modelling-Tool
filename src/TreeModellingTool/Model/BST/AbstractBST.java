package TreeModellingTool.Model.BST;

import TreeModellingTool.Model.AVL.AVLNode;
import TreeModellingTool.Model.AVL.AVLTree;
import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.RedBlack.RedBlackNode;
import TreeModellingTool.Model.RedBlack.RedBlackTree;
import TreeModellingTool.Model.Transitions;
import TreeModellingTool.Model.Tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public abstract class AbstractBST implements Tree {

    protected final String ADDING = "Adding Node";
    protected final String DELETING = "Deleting Node";
    protected final String FINDING = "Finding Node";
    protected final String FINDING_MIN = "Finding Min";
    protected final String FINDING_MAX = "Finding Max";
    protected final String INORDER = "Inorder Traversal";
    protected final String PREORDER = "Preorder Traversal";
    protected final String POSTORDER = "Postorder Traversal";
    protected Node root;
    protected Tree tempTree;
    protected String identifier;


    //________________________________________
    // INTERFACE:

    public abstract void addNode(Node node, boolean doTransitions);

    @Override
    public String showMinNode() {
        if (null == root) {
            return "The tree is empty!";
        }

        Node tempNode = root;
        while (null != tempNode.getLeft()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MIN);
            tempNode = tempNode.getLeft();
        }
        tempNode.setMarked(true);
        addTransition(FINDING_MIN);
        traverseAll(root, true, true);
        return tempNode.toString();
    }

    @Override
    public Node findMinNode(Node root) {
        if (null == root) {
            System.out.println("The tree is empty!");
            return null;
        }

        Node tempNode = root;
        while (null != tempNode.getLeft()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MIN);
            tempNode = tempNode.getLeft();
        }
        return tempNode;
    }

    @Override
    public String showMaxNode() {
        if (null == root) {
            return "The tree is empty!";
        }

        Node tempNode = root;
        while (null != tempNode.getRight()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MAX);
            tempNode = tempNode.getRight();
        }

        tempNode.setMarked(true);
        addTransition(FINDING_MAX);
        traverseAll(root, true, true);

        return tempNode.toString();
    }

    @Override
    public Node findNode(int nodeValueToFind, boolean addTransitions) {

        if (root == null) {
            return null;
        }

        Node tempNode = root;

        while (tempNode.getValue() != nodeValueToFind) {
            if (addTransitions) {
                tempNode.setStacked(true);
                addTransition(FINDING);
            }
            if (tempNode.getValue() > nodeValueToFind) {
                tempNode = tempNode.getLeft();
            } else if (tempNode.getValue() < nodeValueToFind) {
                tempNode = tempNode.getRight();
            }

            if (null == tempNode) {
                return null;
            }
        }
        if (addTransitions) {
            tempNode.setMarked(true);
            addTransition(FINDING);
            traverseAll(root, true, true);
        }
        return tempNode;
    }

    @Override
    public String printAllNodes() {
        return (toString(root));
    }

    @Override
    public String getDiagram() {
        TreeString ts = new TreeString();
        return ts.solve();
    }

    @Override
    public void balanceTree() {

        // getting a inOrder traversed list
        List<Integer> inOrderList = inOrder();

        // converting it into an array
        int[] inOrderArr = intListToArray(inOrderList);
        tempTree = createNewTree();
        root = sortedArrayToBST(inOrderArr, 0, inOrderArr.length - 1);

    }

    @Override
    public List<Integer> getPreOrder(boolean doTransitions) {
        return preOrder(doTransitions);
    }

    @Override
    public List<Integer> getPostOrder() {
        return postOrder();
    }

    @Override
    public List<Integer> getInOrder() {
        return inOrder();
    }

    @Override
    public void clear() {
        root = null;
    }

    protected abstract Node deleteRec(Node root, int value);

    public Node getRoot() {
        return root;
    }

    @Override
    public String toString() {
        return toString(root);
    }

    protected String toString(Node root) {
        StringBuilder result = new StringBuilder();
        if (root == null) {
            result.append("null");
        } else {
        result.append(result.toString() + root.getValue());
        if (root.getLeft() != null || root.getRight() != null) {
            result.append("(" + result.toString() + ", "
                    + toString(root.getLeft()));
            result.append(result.toString() + ", "
                    + toString(root.getRight()) + ")");
        }
    }

        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AbstractBST that = (AbstractBST) o;

        return Objects.equals(root, that.root);
    }

    @Override
    public int hashCode() {
        return root != null ? root.hashCode() : 0;
    }

    //________________________________________


    protected Node sortedArrayToBST(int[] arr, int start, int end) {

        if (start > end) {
            return null;
        }

        // making middle element root
        int mid = (start + end) / 2;
        Node node = createNewNode(arr[mid]);

        tempTree.addNode(arr[mid], true);
        assert node != null;
        node.setMarked(true);
        node.setStacked(true);

        addTransition(tempTree, ADDING);

        // constructing left subtree and making it child of root
        node.setLeft(sortedArrayToBST(arr, start, mid - 1));

        // same for right subtree
        node.setRight(sortedArrayToBST(arr, mid + 1, end));

        return node;
    }


    // IntelliJ fails to see that name is changed by subclasses,
    // therefore do not listen to IntelliJ "error"
    protected void addTransition(Tree tree, String name) {
        String diagram = tree.getDiagram(tree);
        Transitions.add(diagram, name);
    }

    protected int[] intListToArray(List<Integer> list) {
        // change list to array
        int[] array = new int[list.size()];

        Iterator<Integer> it = list.iterator();
        for (int i = 0; i < array.length; i++) {
            array[i] = it.next();
        }
        return array;
    }

    protected void traverseAll(Node temp, boolean unmark, boolean unstack) {
        if (null == temp)
            return;

        // recur on left child
        traverseAll(temp.getLeft(), unmark, unstack);

        // unstack and unmark - ifs are needed otherwise it will affect all of them
        if (unmark) {
            temp.setMarked(false);
        }
        if (unstack) {
            temp.setStacked(false);
        }

        // recur on right child
        traverseAll(temp.getRight(), unmark, unstack);


    }

    protected List<Integer> inOrder() {
        List<Integer> inOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> inOrderList = inOrder(root, inOrderListToPopulate);
        traverseAll(root, true, true);
        return inOrderList;
    }

    protected List<Integer> inOrder(Node temp, List<Integer> listToPopulate) {

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

    protected List<Integer> preOrder(Boolean doTransitions) {
        List<Integer> preOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> preOrderList = preOrder(root, preOrderListToPopulate, doTransitions);
        if (doTransitions) {
            traverseAll(root, true, true);
        }
        return preOrderList;
    }

    protected List<Integer> preOrder(Node temp, List<Integer> listToPopulate, Boolean doTransitions) {

        if (null == temp) {
            return null;
        }
        if (doTransitions) {
            temp.setStacked(true);
            addTransition(PREORDER);
        }
        // add the data of node
        listToPopulate.add(temp.getValue());

        // recur on left child
        preOrder(temp.getLeft(), listToPopulate, doTransitions);

        // recur on right child
        preOrder(temp.getRight(), listToPopulate, doTransitions);
        if (doTransitions) {
            temp.setMarked(true);
            addTransition(PREORDER);
        }


        return listToPopulate;
    }

    protected List<Integer> postOrder() {
        List<Integer> postOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> postOrderList = postOrder(root, postOrderListToPopulate);
        traverseAll(root, true, true);

        return postOrderList;
    }

    protected List<Integer> postOrder(Node temp, List<Integer> listToPopulate) {

        if (null == temp)
            return null;


        temp.setStacked(true);
        addTransition(POSTORDER);

        // recur on left child
        postOrder(temp.getLeft(), listToPopulate);
        // recur on right child
        postOrder(temp.getRight(), listToPopulate);
        // add the data of node
        listToPopulate.add(temp.getValue());

        temp.setMarked(true);
        addTransition(POSTORDER);

        return listToPopulate;
    }

    protected void addTransition(String name) {
        TreeString ts = new TreeString();
        String diagram = ts.solve();
        Transitions.add(diagram, name);
    }

    private Tree createNewTree() {
        switch(identifier){
            case "BST":
                tempTree = new BinarySearchTree();
                break;
            case "AVL":
                tempTree = new AVLTree();
                break;
            case "RBT":
                tempTree = new RedBlackTree();
                break;
            default:
                return null;
        }
        return tempTree;
    }

    private Node createNewNode(int value) {
        switch(identifier){
            case "BST":
                return new BSTNode(value);
            case "AVL":
                return new AVLNode(value);
            case "RBT":
                return new RedBlackNode(value);
            default:
                return null;
        }
    }


    // Modified version of work by "eirikhalvard" at
    // https://github.com/eirikhalvard ,
    // https://github.com/eirikhalvard/binary-tree-to-string
    public class TreeString {
        protected int treeHeight;
        protected int size;
        protected int yHeight;
        protected int xHeight;
        protected int[] maxWordLength;
        protected int[][] preorderHeights;
        protected char[][] chars;


        protected String solve() {
            this.treeHeight = height(root);
            this.size = size(root);
            if (size == 0) {
                return "(empty tree)";
            } //else if (size == 1) {
            //  return "[" + String.valueOf(root.getValue()) + "]";
            //}

            this.maxWordLength = new int[treeHeight + 1];
            this.yHeight = computeYLength(root);
            this.xHeight = computeXLength();
            this.chars = new char[yHeight][xHeight];
            fillCharsWithWhitespace();
            traverseAndWrite();
            return charArrayToString();
        }

        protected void fillCharsWithWhitespace() {
            for (int i = 0; i < yHeight; i++) {
                for (int j = 0; j < xHeight; j++) {
                    chars[i][j] = ' ';
                }
            }
        }

        protected int height(Node n) {
            if (n == null) {
                return -1;
            }
            return 1 + Math.max(height(n.getLeft()), height(n.getRight()));
        }

        protected int size(Node n) {
            if (n == null) return 0;
            return 1 + size(n.getLeft()) + size(n.getRight());
        }

        protected void traverseAndWrite() {
            this.preorderHeights = new int[size][3];
            findPreorderHeights(root, 0);

            // find starting y-point
            int rootsLeftHeight = preorderHeights[0][1];
            int rootStartY = rootsLeftHeight == 0 ? 0 : rootsLeftHeight + 1;

            traverseAndWrite(root, 0, rootStartY, 0, new int[]{0});
        }

        protected void traverseAndWrite(Node n, int depth, int startY, int startX, int[] iterator) {
            int num = preorderHeights[iterator[0]++][0];
            String nodeString = valueString(n, depth);
            writeToCharArray(nodeString, startY, startX);
            startX += nodeString.length();
            if (n.getLeft() != null) {
                int leftsRightHeight = preorderHeights[iterator[0]][2];
                int leftsInnerHeight = leftsRightHeight == 0 ? 1 : leftsRightHeight + 2;
                int leftStartY = (startY - 1) - leftsInnerHeight;
                writeConnectingLines(startY, leftStartY, startX);
                traverseAndWrite(n.getLeft(), depth + 1, leftStartY, startX + 5, iterator);
            }

            if (n.getRight() != null) {
                int rightsLeftHeight = preorderHeights[iterator[0]][1];
                int rightsInnerHeight = rightsLeftHeight == 0 ? 1 : rightsLeftHeight + 2;
                int rightStartY = startY + 1 + rightsInnerHeight;
                writeConnectingLines(startY, rightStartY, startX);
                traverseAndWrite(n.getRight(), depth + 1, rightStartY, startX + 5, iterator);
            }
        }

        protected void writeConnectingLines(int startY, int endY, int startX) {
            writeToCharArray("--+", startY, startX);
            int diff = endY - startY;
            int increment = diff > 0 ? 1 : -1;
            if (diff > 0) {
                for (int i = startY + 1; i < endY; i++) {
                    writeToCharArray("|", i, startX + 2);
                }
            } else {
                for (int i = endY + 1; i < startY; i++) {
                    writeToCharArray("|", i, startX + 2);
                }
            }
            writeToCharArray("+--", endY, startX + 2);

        }

        protected int[] findPreorderHeights(Node n, int h) {
            if (n.getLeft() == null && n.getRight() == null) {
                preorderHeights[h][0] = 1;
                return new int[]{preorderHeights[h][0], h};
            } else if (n.getRight() == null) {
                int[] resultLeft = findPreorderHeights(n.getLeft(), h + 1);
                preorderHeights[h][0] = 2 + resultLeft[0];
                preorderHeights[h][1] = resultLeft[0];
                return new int[]{preorderHeights[h][0], resultLeft[1]};
            } else if (n.getLeft() == null) {
                int[] resultRight = findPreorderHeights(n.getRight(), h + 1);
                preorderHeights[h][0] = 2 + resultRight[0];
                preorderHeights[h][2] = resultRight[0];
                return new int[]{preorderHeights[h][0], resultRight[1]};
            } else {
                int[] resultLeft = findPreorderHeights(n.getLeft(), h + 1);
                int[] resultRight = findPreorderHeights(n.getRight(), resultLeft[1] + 1);
                preorderHeights[h][0] = 3 + resultLeft[0] + resultRight[0];
                preorderHeights[h][1] = resultLeft[0];
                preorderHeights[h][2] = resultRight[0];
                return new int[]{preorderHeights[h][0], resultRight[1]};
            }
        }

        protected void writeToCharArray(String line, int y, int x) {
            if (line.length() + x >= xHeight) {
                new Exception("Line was to long to write");
            }

            for (int i = 0; i < line.length(); i++) {
                chars[y][x + i] = line.charAt(i);
            }
        }

        protected String charArrayToString() {
            StringBuilder result = new StringBuilder();
            for (char[] aChar : chars) {
                for (int j = 0; j < chars[0].length; j++) {
                    result.append(String.valueOf(aChar[j]));
                }
                result.append("\n");
            }
            return result.substring(0, result.length() - 1); // remove last newline
        }

        protected int computeYLength(Node n) {
            if (n.getLeft() == null && n.getRight() == null) {
                return 1;
            } else if (n.getRight() == null) {
                return 2 + computeYLength(n.getLeft());
            } else if (n.getLeft() == null) {
                return 2 + computeYLength(n.getRight());
            } else {
                return 3 + computeYLength(n.getLeft()) + computeYLength(n.getRight());
            }
        }

        protected int computeXLength() {
            computeMaxWordLength(root, 0);
            int sum = 0;
            for (int i = 0; i < treeHeight; i++) {
                sum += "[".length()
                        + maxWordLength[i]
                        + "]".length()
                        + "--+--".length();
            }
            sum += "[".length() + maxWordLength[treeHeight] + "]".length();
            return sum;
        }

        protected void computeMaxWordLength(Node n, int depth) {
            if (n == null) return;
            int nodeStringLength = ("" + n.getValue()).length();
            if (nodeStringLength > maxWordLength[depth]) {
                maxWordLength[depth] = nodeStringLength;
            }
            computeMaxWordLength(n.getLeft(), depth + 1);
            computeMaxWordLength(n.getRight(), depth + 1);
        }

        protected String valueString(Node n, int depth) {

            String result = "";
            int totalCount = maxWordLength[depth] - ("" + n.getValue()).length();

            int leftCount = totalCount / 2;
            int rightCount = totalCount - leftCount;

            String leftPadding = repeat(" ", leftCount);
            String rightPadding = repeat(" ", rightCount);

            if (n.isMarked()) {
                return "%" + leftPadding + String.valueOf(n.getValue()) + rightPadding + "]";
            } else if (n.isStacked()) {
                return "$" + leftPadding + String.valueOf(n.getValue()) + rightPadding + "]";
            } else return "[" + leftPadding + String.valueOf(n.getValue()) + rightPadding + "]";
        }

        protected String repeat(String s, int count) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < count; i++) {
                result.append(s);
            }
            return result.toString();
        }
    }
}
