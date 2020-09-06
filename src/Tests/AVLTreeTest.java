import TreeModellingTool.Model.AVL.AVLNode;
import TreeModellingTool.Model.AVL.AVLTree;
import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.Tree;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;
import java.util.List;

class AVLTreeTest {

    private Tree sampleTree;
    @BeforeEach
    void setUp() {
        sampleTree = new AVLTree();
        sampleTree.addNode(10, false);
        sampleTree.addNode(20, false);
        sampleTree.addNode(30, false);
        sampleTree.addNode(-10, false);
        sampleTree.addNode(-15, false);
        sampleTree.addNode(5, false);
        sampleTree.addNode(15, false);
        sampleTree.addNode(13, false);
        sampleTree.addNode(12, false);
        sampleTree.addNode(23, false);

    }


    @Test
    void add0(){
        Tree tree = new AVLTree();
        tree.addNode(10, false);
        tree.addNode(25, false);
        tree.addNode(2, false);
        tree.addNode(1, false);
        tree.addNode(33, false);
        String goodDiagram =
                "               +--[1 ]\n" +
                        "               |      \n" +
                        "      +--[2 ]--+      \n" +
                        "      |               \n" +
                        "[10]--+               \n" +
                        "      |               \n" +
                        "      +--[25]--+      \n" +
                        "               |      \n" +
                        "               +--[33]";
        assert(tree.getDiagram().equals(goodDiagram));
        System.out.println(tree.getDiagram());
    }

    @Test
    void add1(){
        Tree tree = new AVLTree();
        tree.addNode(13, false);
        tree.addNode(10, false);
        tree.addNode(15, false);
        tree.addNode(16, false);
        tree.addNode(11, false);
        tree.addNode(5, false);
        tree.addNode(6, false);
        tree.addNode(4, false);
        tree.addNode(14, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "                        +--[4]\n" +
                        "                        |     \n" +
                        "               +--[5 ]--+     \n" +
                        "               |        |     \n" +
                        "               |        +--[6]\n" +
                        "               |              \n" +
                        "      +--[10]--+              \n" +
                        "      |        |              \n" +
                        "      |        +--[11]        \n" +
                        "      |                       \n" +
                        "[13]--+                       \n" +
                        "      |                       \n" +
                        "      |        +--[14]        \n" +
                        "      |        |              \n" +
                        "      +--[15]--+              \n" +
                        "               |              \n" +
                        "               +--[16]        ";
        assert(tree.getDiagram().equals(goodDiagram));
    }

    @Test
    void add2(){
        Tree tree = new AVLTree();
        tree.addNode(13, false);
        tree.addNode(10, false);
        tree.addNode(15, false);
        tree.addNode(16, false);
        tree.addNode(11, false);
        tree.addNode(5, false);
        tree.addNode(4, false);
        tree.addNode(8, false);
        tree.addNode(3, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "                        +--[3 ]\n" +
                        "                        |      \n" +
                        "               +--[4 ]--+      \n" +
                        "               |               \n" +
                        "      +--[5 ]--+               \n" +
                        "      |        |               \n" +
                        "      |        |        +--[8 ]\n" +
                        "      |        |        |      \n" +
                        "      |        +--[10]--+      \n" +
                        "      |                 |      \n" +
                        "      |                 +--[11]\n" +
                        "      |                        \n" +
                        "[13]--+                        \n" +
                        "      |                        \n" +
                        "      +--[15]--+               \n" +
                        "               |               \n" +
                        "               +--[16]         ";
        assert(tree.getDiagram().equals(goodDiagram));

    }

    @Test
    void add3(){
        Tree tree = new AVLTree();
        tree.addNode(30, false);
        tree.addNode(35, false);
        tree.addNode(5, false);
        tree.addNode(40, false);
        tree.addNode(32, false);
        tree.addNode(45, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "               +--[5 ]\n" +
                        "               |      \n" +
                        "      +--[30]--+      \n" +
                        "      |        |      \n" +
                        "      |        +--[32]\n" +
                        "      |               \n" +
                        "[35]--+               \n" +
                        "      |               \n" +
                        "      +--[40]--+      \n" +
                        "               |      \n" +
                        "               +--[45]";
        assert(tree.getDiagram().equals(goodDiagram));
    }

    @Test
    void add4(){
        Tree tree = new AVLTree();
        tree.addNode(13, false);
        tree.addNode(10, false);
        tree.addNode(15, false);
        tree.addNode(16, false);
        tree.addNode(11, false);
        tree.addNode(5, false);
        tree.addNode(4, false);
        tree.addNode(6, false);
        tree.addNode(7, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "                        +--[4 ]\n" +
                "                        |      \n" +
                "               +--[5 ]--+      \n" +
                "               |               \n" +
                "      +--[6 ]--+               \n" +
                "      |        |               \n" +
                "      |        |        +--[7 ]\n" +
                "      |        |        |      \n" +
                "      |        +--[10]--+      \n" +
                "      |                 |      \n" +
                "      |                 +--[11]\n" +
                "      |                        \n" +
                "[13]--+                        \n" +
                "      |                        \n" +
                "      +--[15]--+               \n" +
                "               |               \n" +
                "               +--[16]         ";
        assert(tree.getDiagram().equals(goodDiagram));
    }

    @Test
    void add5(){
        Tree tree = new AVLTree();
        tree.addNode(5, false);
        tree.addNode(2, false);
        tree.addNode(7, false);
        tree.addNode(6, false);
        tree.addNode(9, false);
        tree.addNode(16, false);
        tree.addNode(4, false);
        tree.addNode(1, false);
        tree.addNode(3, false);
        tree.addNode(15, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "                       +--[1]\n" +
                        "                       |     \n" +
                        "              +--[2 ]--+     \n" +
                        "              |        |     \n" +
                        "              |        +--[3]\n" +
                        "              |              \n" +
                        "     +--[4 ]--+              \n" +
                        "     |        |              \n" +
                        "     |        +--[5 ]--+     \n" +
                        "     |                 |     \n" +
                        "     |                 +--[6]\n" +
                        "     |                       \n" +
                        "[7]--+                       \n" +
                        "     |                       \n" +
                        "     |        +--[9 ]        \n" +
                        "     |        |              \n" +
                        "     +--[15]--+              \n" +
                        "              |              \n" +
                        "              +--[16]        ";
        assert(tree.getDiagram().equals(goodDiagram));
    }

    @Test
    void add6(){
        Tree tree = new AVLTree();
        tree.addNode(1, false);
        tree.addNode(2, false);
        tree.addNode(3, false);
        tree.addNode(5, false);
        tree.addNode(6, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "     +--[1]        \n" +
                        "     |             \n" +
                        "[2]--+             \n" +
                        "     |             \n" +
                        "     |       +--[3]\n" +
                        "     |       |     \n" +
                        "     +--[5]--+     \n" +
                        "             |     \n" +
                        "             +--[6]";
        assert(tree.getDiagram().equals(goodDiagram));
    }

    @Test
    void add7(){
        Tree tree = new AVLTree();
        tree.addNode(6, false);
        tree.addNode(5, false);
        tree.addNode(4, false);
        tree.addNode(3, false);
        tree.addNode(2, false);
        System.out.println(tree.getDiagram());
        String goodDiagram =
                "             +--[2]\n" +
                        "             |     \n" +
                        "     +--[3]--+     \n" +
                        "     |       |     \n" +
                        "     |       +--[4]\n" +
                        "     |             \n" +
                        "[5]--+             \n" +
                        "     |             \n" +
                        "     +--[6]        ";
        assert(tree.getDiagram().equals(goodDiagram));
    }


    @Test
    void deleteNode0() {
        System.out.println(sampleTree.getDiagram());
        System.out.println("________________________________________________");
        boolean deleted = sampleTree.deleteNode(-10);
        assert (deleted);

        System.out.println(sampleTree.getDiagram());
        System.out.println("________________________________________________");
        sampleTree.deleteNode(5);
        System.out.println(sampleTree.getDiagram());

        System.out.println("________________________________________________");
        sampleTree.deleteNode(-15);
        System.out.println(sampleTree.getDiagram());
        System.out.println(sampleTree.getDiagram());
    }

    @Test
    void deleteNode1() {
        System.out.println(sampleTree.getDiagram());
        System.out.println("________________________________________________");
        boolean deleted = sampleTree.deleteNode(-10);
        assert (deleted);

        assert (sampleTree.getInOrder().size() == 9);

        System.out.println(sampleTree.getDiagram());
        System.out.println("________________________________________________");
        sampleTree.deleteNode(5);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 8);

        System.out.println("________________________________________________");
        sampleTree.deleteNode(13);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 7);

        System.out.println("________________________________________________");
        sampleTree.deleteNode(23);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 6);

        System.out.println("________________________________________________");
        sampleTree.deleteNode(20);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 5);

        System.out.println("________________________________________________");
        sampleTree.deleteNode(30);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 4);


        System.out.println("________________________________________________");
        sampleTree.deleteNode(-15);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 3);

        System.out.println("________________________________________________");
        sampleTree.deleteNode(15);
        System.out.println(sampleTree.getDiagram());
        assert (sampleTree.getInOrder().size() == 2);

        String goodDiagram =
                "      +--[10]\n" +
                "      |      \n" +
                "[12]--+      ";
        assert(sampleTree.getDiagram().equals(goodDiagram));
    }

    @Test
    void deleteNode2() {
    }

    @Test
    void deleteNode3() {
    }

    @Test
    void deleteNode4() {
    }

    @Test
    void deleteNode5() {
    }

    @Test
    void deleteNode6() {
    }



    @Test
    void deepCopy() {
    }
}