import BST_Tool.Model.BST.BSTNode;
import BST_Tool.Model.BST.BinarySearchTree;
import BST_Tool.Model.Tree;
import org.junit.jupiter.api.BeforeEach;

import java.util.LinkedList;
import java.util.List;

class BinarySearchTreeTest {


    private Tree exampleBst;
    private final String MIN_NODE_STR = "-3";
    private final String MIN_NODE_STR_FULL = "BSTNode{value=-3, left=null, right=null}";
    private final String MAX_NODE_STR_FULL = "BSTNode{value=14, left=BSTNode{value=13, left=null, right=null}, right=null}";
    private final String MAX_NODE_STR= "14";


    @BeforeEach
    void initExampleBst(){
        exampleBst = new BinarySearchTree();
        exampleBst.addNode(12, false);
        exampleBst.addNode(14, false);
        exampleBst.addNode(10, false);
        exampleBst.addNode(-3,false);
        exampleBst.addNode(11, false);
        exampleBst.addNode(13, false);
    }

    @org.junit.jupiter.api.Test
    void testAddNode() {
        BinarySearchTree bst = new BinarySearchTree();
        bst.addNode(12, false);
        bst.addNode(14, false);
        bst.addNode(10, false);
        bst.addNode(-3,false);
        bst.addNode(11, false);
        bst.addNode(13, false);
        String diagramThatShouldBe =
                "               +--[-3]\n" +
                "               |      \n" +
                "      +--[10]--+      \n" +
                "      |        |      \n" +
                "      |        +--[11]\n" +
                "      |               \n" +
                "[12]--+               \n" +
                "      |               \n" +
                "      |        +--[13]\n" +
                "      |        |      \n" +
                "      +--[14]--+      ";

        String diagramThatIs = bst.getDiagram();
        assert (diagramThatIs.equals(diagramThatShouldBe));
    }

    @org.junit.jupiter.api.Test
    void deleteNode() {
        exampleBst.deleteNode(13);
        String diagramThatShouldBe =
                "               +--[-3]\n" +
                "               |      \n" +
                "      +--[10]--+      \n" +
                "      |        |      \n" +
                "      |        +--[11]\n" +
                "      |               \n" +
                "[12]--+               \n" +
                "      |               \n" +
                "      +--[14]         ";


        String diagramThatIs = exampleBst.getDiagram();

        assert (diagramThatShouldBe.equals(diagramThatIs));

    }

    @org.junit.jupiter.api.Test
    void deleteNode2() {
        exampleBst.deleteNode(10);
        String diagramThatShouldBe =
                "               +--[-3]\n" +
                "               |      \n" +
                "      +--[11]--+      \n" +
                "      |               \n" +
                "[12]--+               \n" +
                "      |               \n" +
                "      |        +--[13]\n" +
                "      |        |      \n" +
                "      +--[14]--+      ";

        String diagramThatIs = exampleBst.getDiagram();

        assert (diagramThatShouldBe.equals(diagramThatIs));

    }

    @org.junit.jupiter.api.Test
    void deleteNode3() {
        exampleBst.deleteNode(13);
        String diagramThatShouldBe =
                "               +--[-3]\n" +
                "               |      \n" +
                "      +--[10]--+      \n" +
                "      |        |      \n" +
                "      |        +--[11]\n" +
                "      |               \n" +
                "[12]--+               \n" +
                "      |               \n" +
                "      +--[14]         ";

        String diagramThatIs = exampleBst.getDiagram();

        assert (diagramThatShouldBe.equals(diagramThatIs));

    }

    @org.junit.jupiter.api.Test
    void showMinNode() {
        String minNodeStr = exampleBst.showMinNode();
        System.out.println(minNodeStr);
        assert (minNodeStr.equals(MIN_NODE_STR_FULL));
    }

    @org.junit.jupiter.api.Test
    void findMinNode() {

        BSTNode root = exampleBst.getRoot();
        BSTNode minNode = exampleBst.findMinNode(root);
        int minValue = minNode.getValue();
        System.out.println(minValue);
        assert (minValue == Integer.parseInt(MIN_NODE_STR));

    }

    @org.junit.jupiter.api.Test
    void showMaxNode() {
        String maxValue = exampleBst.showMaxNode();
        assert (maxValue.equals(MAX_NODE_STR_FULL));
    }

    @org.junit.jupiter.api.Test
    void findNode() {

        BSTNode node = exampleBst.findNode(11,false);
        assert (node != null);

        BSTNode node2 = exampleBst.findNode(-3, false);
        assert (node2 != null);

        BSTNode node3 = exampleBst.findNode(55,false);
        assert (null == node3);
    }

    @org.junit.jupiter.api.Test
    void printAllNodes() {
        String allNodesInLine = exampleBst.printAllNodes();
        String asItShouldBe = "12(12, 10(10, -310(10, -3, 11)12(12, 10(10, -310(10, -3, 11), 14(14, 1314(14, 13, null))";
        assert (allNodesInLine.equals(asItShouldBe));
    }

    @org.junit.jupiter.api.Test
    void getDiagram() {
        String diagramThatShouldBe =
                "               +--[-3]\n" +
                        "               |      \n" +
                        "      +--[10]--+      \n" +
                        "      |        |      \n" +
                        "      |        +--[11]\n" +
                        "      |               \n" +
                        "[12]--+               \n" +
                        "      |               \n" +
                        "      |        +--[13]\n" +
                        "      |        |      \n" +
                        "      +--[14]--+      ";

        String diagram = exampleBst.getDiagram();
        assert (diagramThatShouldBe.equals(diagram));
    }

    @org.junit.jupiter.api.Test
    void balanceTree() {
        BinarySearchTree tree = new BinarySearchTree();
        addUnbalancedTreeNodes(tree);
        testTreeBalancing(tree);
    }

    private void testTreeBalancing(BinarySearchTree tree) {
        tree.balanceTree();
        String diagramThatShouldBe =
                "              +--%-5]--+      \n" +
                "              |        |      \n" +
                "              |        +--%-4]\n" +
                "              |               \n" +
                "     +--%-3]--+               \n" +
                "     |        |               \n" +
                "     |        +--%-2]--+      \n" +
                "     |                 |      \n" +
                "     |                 +--%-1]\n" +
                "     |                        \n" +
                "%0]--+                        \n" +
                "     |                        \n" +
                "     |        +--%1 ]--+      \n" +
                "     |        |        |      \n" +
                "     |        |        +--%2 ]\n" +
                "     |        |               \n" +
                "     +--%3 ]--+               \n" +
                "              |               \n" +
                "              +--%4 ]--+      \n" +
                "                       |      \n" +
                "                       +--%5 ]";
        String diagramThatIs = tree.getDiagram();

        assert (diagramThatIs.equals(diagramThatShouldBe));
    }

    private void addUnbalancedTreeNodes(BinarySearchTree tree) {
        // preparing absolutely unbalanced tree going from -5 to 5 on one branch
        for (int i = -5; i < 6; i++) {
            BSTNode nodeToAdd = new BSTNode(i);
            tree.addNode(nodeToAdd, false);
        }
        String currentDiagram = tree.getDiagram();
        String goodDiagram =
                "[-5]--+                                                                                 \n" +
                "      |                                                                                 \n" +
                "      +--[-4]--+                                                                        \n" +
                "               |                                                                        \n" +
                "               +--[-3]--+                                                               \n" +
                "                        |                                                               \n" +
                "                        +--[-2]--+                                                      \n" +
                "                                 |                                                      \n" +
                "                                 +--[-1]--+                                             \n" +
                "                                          |                                             \n" +
                "                                          +--[0]--+                                     \n" +
                "                                                  |                                     \n" +
                "                                                  +--[1]--+                             \n" +
                "                                                          |                             \n" +
                "                                                          +--[2]--+                     \n" +
                "                                                                  |                     \n" +
                "                                                                  +--[3]--+             \n" +
                "                                                                          |             \n" +
                "                                                                          +--[4]--+     \n" +
                "                                                                                  |     \n" +
                "                                                                                  +--[5]";

        assert(goodDiagram.equals(currentDiagram));
    }

    @org.junit.jupiter.api.Test
    void getPreOrder() {
        List<Integer> preOrder = exampleBst.getPreOrder(false);
        List<Integer> exampleList = new LinkedList<>();
        exampleList.add(12);
        exampleList.add(10);
        exampleList.add(-3);
        exampleList.add(11);
        exampleList.add(14);
        exampleList.add(13);

        assert (exampleList.equals(preOrder));

    }

    @org.junit.jupiter.api.Test
    void getPostOrder() {
        List<Integer> postOrder = exampleBst.getPostOrder();
        List<Integer> exampleList = new LinkedList<>();
        exampleList.add(-3);
        exampleList.add(11);
        exampleList.add(10);
        exampleList.add(13);
        exampleList.add(14);
        exampleList.add(12);

        assert (exampleList.equals(postOrder));
    }

    @org.junit.jupiter.api.Test
    void getInOrder() {
        List<Integer> inOrder = exampleBst.getInOrder();
        List<Integer> exampleList = new LinkedList<>();
        exampleList.add(-3);
        exampleList.add(10);
        exampleList.add(11);
        exampleList.add(12);
        exampleList.add(13);
        exampleList.add(14);

        assert (exampleList.equals(inOrder));
    }

    @org.junit.jupiter.api.Test
    void clear() {
        exampleBst.clear();
        BSTNode root = exampleBst.getRoot();
        assert(root == null);

        List<Integer> inOrder = exampleBst.getInOrder();
        assert(inOrder == null);
    }


    @org.junit.jupiter.api.Test
    void deepCopy() {
        Tree copiedTree = exampleBst.deepCopy(exampleBst);
        assert(copiedTree.equals(exampleBst));
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        String goodToString = "12(12, 10(10, -310(10, -3, 11)12(12, 10(10, -310(10, -3, 11), 14(14, 1314(14, 13, null))";
        String TreeToString = exampleBst.toString();
        assert (goodToString.equals(TreeToString));

    }
}