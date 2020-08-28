package BST_Tool;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TreeViewController {
    private JPanel panel1;
    private JTextArea textArea;
    private JButton addNodeBtn;
    private JButton delNodeBtn;
    private JButton minNodeBtn;
    private JButton printAllBtn;
    private JButton inorderBtn;
    private JButton postOrderBtn;
    private JButton randTreeBtn;
    private JButton printTreeBtn;
    private JButton exitBtn;
    private JButton findNodeBtn;
    private JButton maxNodeBtn;
    private JButton preOrderBtn;
    private JButton balanceTreeBtn;
    private JTextField valueInputTF;

    private Scanner in = new Scanner(System.in);
    private BinarySearchTree tree = new BinarySearchTree();

    public TreeViewController(){
        JFrame frame = new JFrame("Binary Search Tree Playground");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeFrameFullSize(frame);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        int originalDiagramFontSize = textArea.getFont().getSize();
        Dimension originalWindowSize = panel1.getSize();

        panel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                resizeFont(originalWindowSize, originalDiagramFontSize);
            }
            @Override
            public void componentMoved(ComponentEvent e) {
                System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });



        randTreeBtn.addActionListener(e -> {
            tree = createRandomBST();
            updateDiagram();
        });

        balanceTreeBtn.addActionListener(e -> {
            tree.balanceTree();
            updateDiagram();
        });

        printTreeBtn.addActionListener(e -> updateDiagram());

        inorderBtn.addActionListener(e -> {
            List<Integer> inOrder = tree.getInOrder();
            changeTextArea(inOrder.toString());
        });

        preOrderBtn.addActionListener(e -> {
            List<Integer> inOrder = tree.getPreOrder();
            changeTextArea(inOrder.toString());
        });

        postOrderBtn.addActionListener(e -> {
            List<Integer> inOrder = tree.getPostOrder();
            changeTextArea(inOrder.toString());
        });

        minNodeBtn.addActionListener(e -> {
            String minNodeStr = tree.showMinNode();
            changeTextArea(minNodeStr);
        });

        maxNodeBtn.addActionListener(e -> {
            String maxNodeStr = tree.showMaxNode();
            changeTextArea(maxNodeStr);
        });

        printAllBtn.addActionListener(e -> {
            String allNodesStr = tree.printAllNodes();
            changeTextArea(allNodesStr);
        });

        addNodeBtn.addActionListener(e -> getNodeToAdd());

        findNodeBtn.addActionListener(e -> getNodeToFind());

        delNodeBtn.addActionListener(e -> getNodeToDelete());

    }

    private void resizeFont(Dimension originalWindowSize, int originalDiagramFontSize) {
        Dimension newSize = panel1.getSize();

        // according to proportion:
        // 20 (originalDiagramFontSize) - (originalWindowSize)
        // x                            - (newSize)
        int x = (int) (originalDiagramFontSize * newSize.getHeight() / originalWindowSize.getHeight());

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, x));
    }


    private void makeFrameFullSize(JFrame frame) {
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        frame.setSize(rect.getSize());
    }



    private void getNodeToAdd() {
        String value = valueInputTF.getText();
        if (isInteger(value)){
            addNode(value);
        } else {
            displayNotIntErrorMsg();
        }
    }

    private void displayNotIntErrorMsg() {
        String msg = "Input has to be integer";
        JOptionPane.showMessageDialog(null,msg);
    }

    private void displayValueMissingErrorMsg(int value) {
        String msg = "The tree does not have the value: " + value;
        JOptionPane.showMessageDialog(null,msg);
    }

    private void addNode(String value) {
        int intVal = Integer.parseInt(value);
        tree.addNode(intVal);
        updateDiagram();
    }

    private void getNodeToFind() {
        String value = valueInputTF.getText();
        if (isInteger(value)){
            int intVal = Integer.parseInt(value);
            BSTNode node = tree.findNode(intVal);
            if (node == null) {
                displayValueMissingErrorMsg(intVal);
            } else {
                changeTextArea("Node found: " + node.toString());
            }
        }
        else {
            displayNotIntErrorMsg();
        }
    }

    // Gets node value to delete and deletes it
    private void getNodeToDelete() {
        boolean deleted = false;
        String value = valueInputTF.getText();
        if (isInteger(value)) {
            deleted = deleteNode(value);
        }
        else {
            displayNotIntErrorMsg();
            return;
        }

        processDeletionResult(deleted, value);
    }

    private void processDeletionResult(boolean deleted, String value) {
        if (deleted) {
            updateDiagram();
        } else {
            int valueInt = Integer.parseInt(value);
            displayValueMissingErrorMsg(valueInt);
        }
    }

    private boolean deleteNode(String value) {
        boolean deleted;
        int intVal = Integer.parseInt(value);
        deleted = tree.deleteNode(intVal);
        return deleted;
    }


    // this method quickly creates a random BST.
    // It could ask the user input for numbers used, though I left it like it is,
    // because it gives more speed in testing and playing with the program.
    private BinarySearchTree createRandomBST() {

        BinarySearchTree randTree = new BinarySearchTree();

        // 1. Limit: Nodes Amount
        final int nodesMinAmount = 5;
        final int nodesMaxAmount = 20 + nodesMinAmount;
        // from 5 to 25
        int randNodesAmount = (int) (Math.random() * nodesMaxAmount + nodesMinAmount);

        // 2. Limit: Nodes Values
        for (int i = 0; i < randNodesAmount; i++) {
            // from -50 to 50
            int randNodeVal = (int) (Math.random() * 100 - 50);
            randTree.addNode(randNodeVal);
        }

        return randTree;
    }




    // Sanitizes int input and returns it
    private int getSanitizedInt() {
        int intToSanitize = -666;
        try {
            intToSanitize = in.nextInt();
        }
        catch (InputMismatchException ex){
            System.out.println("Integer is expected");
            in.next();
            getSanitizedInt();
        }
        return intToSanitize;
    }


    private void updateDiagram(){
        String diagram = tree.getDiagram();
        //diagram = diagram.replaceAll("--\\+\\s","");
        textArea.setText(diagram);
    }

    private void changeTextArea(String str){
        textArea.setText(str);
    }

    private static boolean isInteger(String s) {
        return s.matches("-?(0|[1-9]\\d*)");
    }

}
