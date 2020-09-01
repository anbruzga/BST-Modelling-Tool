package BST_Tool;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinarySearchTree {

    private final String ADDING = "Adding Node";
    private final String DELETING = "Deleting Node";
    private final String FINDING = "Finding Node";
    private final String FINDING_MIN = "Finding Min";
    private final String FINDING_MAX = "Finding Max";
    private final String INORDER = "Inorder Traversal";
    private final String PREORDER = "Preorder Traversal";
    private final String POSTORDER = "Postorder Traversal";
    private final String BALANCING = "Balancing";
    // VARIABLES
    private BSTNode root;
    private BinarySearchTree tempTree;

    // CONSTRUCTORS
    public BinarySearchTree(int rootVal) {
        root = new BSTNode(rootVal);
    }

    public BinarySearchTree() {
    }

    // PUBLIC
    public void addNode(int value, boolean doTransitions) {
        root = addRecursive(root, value, doTransitions);
        if (doTransitions) {
            addTransition(ADDING);
            traverseAll(root, true, true);
        }
    }

    public void addNode(BSTNode node, boolean doTransitions) {
        int value = node.getValue();
        addNode(value, doTransitions);
    }

    public boolean deleteNode(int value) {

        BSTNode nodeToDelete = findNode(value, false);

        //todo this kind of avoid highlighting. Is it needed?
        // returns false if node does not exists
        if (nodeToDelete == null) {
            return false;
        }

        deleteRec(root, value);
        addTransition(DELETING);
        traverseAll(root, true, true);

        return true;
    }

    public String showMinNode() {
        if (null == root) {
            return "The tree is empty!";
        }

        BSTNode tempNode = root;
        while (null != tempNode.getLeft()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MIN);
            tempNode = tempNode.getLeft();
        }
        tempNode.setMarked(true);
        addTransition(FINDING_MIN);
        traverseAll(root, true, true);
        return "Min Node: " + tempNode.toString();
    }

    public BSTNode findMinNode(BSTNode node) {
        if (null == node) {
            System.out.println("The tree is empty!");
            return null;
        }

        BSTNode tempNode = node;
        while (null != tempNode.getLeft()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MIN);
            tempNode = tempNode.getLeft();
        }
        return tempNode;
    }

    public String showMaxNode() {
        if (null == root) {
            return "The tree is empty!";
        }

        BSTNode tempNode = root;
        while (null != tempNode.getRight()) {
            tempNode.setStacked(true);
            addTransition(FINDING_MAX);
            tempNode = tempNode.getRight();
        }

        tempNode.setMarked(true);
        addTransition(FINDING_MAX);
        traverseAll(root, true, true);

        return "Max Node: " + tempNode.toString();
    }

    public BSTNode findNode(int nodeValueToFind, boolean addTransitions) {

        if (root == null) {
            return null;
        }

        BSTNode tempNode = root;

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

    public String printAllNodes() {
        return (toString(root));
    }

    public String getDiagram() {
        TreeString ts = new TreeString();
        return ts.solve();
    }

    public void balanceTree() {

        // getting a inOrder traversed list
        List<Integer> inOrderList = inOrder();

        // converting it into an array
        int[] inOrderArr = intListToArray(inOrderList);
        tempTree = new BinarySearchTree();
        root = sortedArrayToBST(inOrderArr, 0, inOrderArr.length - 1);

    }

    public List<Integer> getPreOrder(boolean doTransitions) {
        return preOrder(doTransitions);
    }

    public List<Integer> getPostOrder() {
        return postOrder();
    }

    public List<Integer> getInOrder() {
        return inOrder();
    }

    public void deleteValue(int key) {
        root = deleteRec(root, key);
    }

    public void clear() {
        root = null;
    }

    /* A recursive function to insert a new key in BST */
    private BSTNode deleteRec(BSTNode root, int value) {
        /* Base Case: If the tree is empty */
        if (root == null) return root;

        root.setStacked(true);
        addTransition(DELETING);

        /* Otherwise, recur down the tree */
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

    protected String getDiagram(BinarySearchTree tempBst) {
        // doing triangulation:
        // to save original in a deep copy
        BinarySearchTree originalDeepCopy = deepCopy(this);

        // to get THIS instance as tempBst
        BinarySearchTree tempFakeThis = deepCopy(tempBst);

        // to get diagram from this
        String diagram = tempFakeThis.getDiagram();

        // to reset this back to original this
        BinarySearchTree thisTree = deepCopy(originalDeepCopy);
        thisTree = this; // not redundant as this reference matters

        // to return the diagram of tempBst
        return diagram;


    }

    protected BinarySearchTree deepCopy(BinarySearchTree toCopy) {

        BinarySearchTree toPaste = new BinarySearchTree();

        //make deep copy
        List<Integer> preOrderArgBst = toCopy.preOrder(false);

        for (int i = 0; i < preOrderArgBst.size(); i++) {
            //System.out.println(preOrderArgBst.get(i) + "pasting");
            toPaste.addNode(preOrderArgBst.get(i), false);
        }
        return toPaste;
    }

    private BSTNode sortedArrayToBST(int arr[], int start, int end) {

        if (start > end) {
            return null;
        }

        // making middle element root
        int mid = (start + end) / 2;
        BSTNode node = new BSTNode(arr[mid]);

        tempTree.addNode(arr[mid], true);
        node.setMarked(true);
        node.setStacked(true);

        addTransition(tempTree, ADDING);

        // constructing left subtree and making it child of root
        node.setLeft(sortedArrayToBST(arr, start, mid - 1));

        // same for right subtree
        node.setRight(sortedArrayToBST(arr, mid + 1, end));

        return node;
    }

    private void addTransition(BinarySearchTree bst, String name) {
        String diagram = bst.getDiagram(bst);
        Transitions.add(diagram, name);
    }

    private int[] intListToArray(List<Integer> list) {
        // change list to array
        int[] array = new int[list.size()];

        Iterator<Integer> it = list.iterator();
        for (int i = 0; i < array.length; i++) {
            array[i] = it.next();
        }
        return array;
    }

    private void traverseAll(BSTNode temp, boolean unmark, boolean unstack) {
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

    private List<Integer> inOrder() {
        List<Integer> inOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> inOrderList = inOrder(root, inOrderListToPopulate);
        traverseAll(root, true, true);
        return inOrderList;
    }

    private List<Integer> inOrder(BSTNode temp, List<Integer> listToPopulate) {

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

    private List<Integer> preOrder(Boolean doTransitions) {
        List<Integer> preOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> preOrderList = preOrder(root, preOrderListToPopulate, doTransitions);
        if (doTransitions) {
            traverseAll(root, true, true);
        }
        return preOrderList;
    }

    private List<Integer> preOrder(BSTNode temp, List<Integer> listToPopulate, Boolean doTransitions) {

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

    private List<Integer> postOrder() {
        List<Integer> postOrderListToPopulate = new ArrayList<>();
        /* passing a list to populate and the root node */
        List<Integer> postOrderList = postOrder(root, postOrderListToPopulate);
        traverseAll(root, true, true);

        return postOrderList;
    }

    private List<Integer> postOrder(BSTNode temp, List<Integer> listToPopulate) {

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

    private BSTNode addRecursive(BSTNode current, int value, Boolean doTransitions) {

        if (current == null) {
            BSTNode node = new BSTNode(value);
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

    private void addTransition(String name) {
        TreeString ts = new TreeString();
        String diagram = ts.solve();
        Transitions.add(diagram, name);
    }


    public String toString() {
        return toString(root);
    }

    private String toString(BSTNode root) {
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


    // Modified version of work by "eirikhalvard" at
    // https://github.com/eirikhalvard ,
    // https://github.com/eirikhalvard/binary-tree-to-string
    public class TreeString {
        private int treeHeight;
        private int size;
        private int yHeight;
        private int xHeight;
        private int[] maxWordLength;
        private int[][] preorderHeights;
        private char[][] chars;


        private String solve() {
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

        private void fillCharsWithWhitespace() {
            for (int i = 0; i < yHeight; i++) {
                for (int j = 0; j < xHeight; j++) {
                    chars[i][j] = ' ';
                }
            }
        }

        private int height(BSTNode n) {
            if (n == null) {
                return -1;
            }
            return 1 + Math.max(height(n.getLeft()), height(n.getRight()));
        }

        private int size(BSTNode n) {
            if (n == null) return 0;
            return 1 + size(n.getLeft()) + size(n.getRight());
        }

        private void traverseAndWrite() {
            this.preorderHeights = new int[size][3];
            findPreorderHeights(root, 0);

            // find starting y-point
            int rootsLeftHeight = preorderHeights[0][1];
            int rootStartY = rootsLeftHeight == 0 ? 0 : rootsLeftHeight + 1;

            traverseAndWrite(root, 0, rootStartY, 0, new int[]{0});
        }

        private void traverseAndWrite(BSTNode n, int depth, int startY, int startX, int[] iterator) {
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

        private void writeConnectingLines(int startY, int endY, int startX) {
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

        private int[] findPreorderHeights(BSTNode n, int h) {
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

        private void writeToCharArray(String line, int y, int x) {
            if (line.length() + x >= xHeight) {
                new Exception("Line was to long to write");
            }

            for (int i = 0; i < line.length(); i++) {
                chars[y][x + i] = line.charAt(i);
            }
        }

        private String charArrayToString() {
            String result = "";
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < chars[0].length; j++) {
                    result += String.valueOf(chars[i][j]);
                }
                result += "\n";
            }
            return result.substring(0, result.length() - 1); // remove last newline
        }

        private int computeYLength(BSTNode n) {
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

        private int computeXLength() {
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

        private void computeMaxWordLength(BSTNode n, int depth) {
            if (n == null) return;
            int nodeStringLength = ("" + n.getValue()).length();
            if (nodeStringLength > maxWordLength[depth]) {
                maxWordLength[depth] = nodeStringLength;
            }
            computeMaxWordLength(n.getLeft(), depth + 1);
            computeMaxWordLength(n.getRight(), depth + 1);
        }

        private String valueString(BSTNode n, int depth) {

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

        private String repeat(String s, int count) {
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < count; i++) {
                result.append(s);
            }
            return result.toString();
        }
    }


}