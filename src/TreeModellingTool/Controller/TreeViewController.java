package TreeModellingTool.Controller;

import TreeModellingTool.Model.AVL.AVLTree;
import TreeModellingTool.Model.BST.BinarySearchTree;
import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.RedBlack.RedBlackTree;
import TreeModellingTool.Model.Transitions;
import TreeModellingTool.Model.Tree;
import TreeModellingTool.View.HintTextField;

import javax.swing.*;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.Highlighter;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TreeViewController {
    private JPanel panel1;
    private JTextArea textArea;
    private JButton addNodeBtn;
    private JButton delNodeBtn;
    private JButton minNodeBtn;
    private JButton inorderBtn;
    private JButton postOrderBtn;
    private JButton randTreeBtn;
    private JButton findNodeBtn;
    private JButton maxNodeBtn;
    private JButton preOrderBtn;
    private JButton balanceTreeBtn;
    private JTextField valueInputTF;
    private JButton nextTransitionBtn;
    private JButton prevTransitionBtn;
    private JButton deleteAllNodesButton;
    private JButton fontSmallerBtn;
    private JButton fontLargerBtn;
    private JButton transitionFastBackwardBtn;
    private JButton transitionFastForwardBtn;
    private JTextField nodesAmountTextField;
    private JTextField minValueTextField;
    private JTextField maxValueTextField;
    private JLabel transitionNameLabel;
    private JLabel transitionsLabel;
    private JRadioButton BstRadioBtn;
    private JRadioButton AvlRadioBtn;
    private JRadioButton RedBlackRadioBtn;


    private Tree tree = new BinarySearchTree();
    private static int originalDiagramFontSize;

    private final Highlighter.HighlightPainter highlightStacked = new MyHighlightPainter(Color.yellow);
    private final Highlighter.HighlightPainter highlightMarked = new MyHighlightPainter(Color.red);

    public TreeViewController() {
        JFrame frame = new JFrame("Binary Search Trees Modelling Tool");
        frame.setContentPane(panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeFrameFullSize(frame);
        //frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        ButtonGroup group = new ButtonGroup();
        group.add(BstRadioBtn);
        group.add(AvlRadioBtn);
        group.add(RedBlackRadioBtn);


        originalDiagramFontSize = textArea.getFont().getSize();

        randTreeBtn.addActionListener(e -> {
            Transitions.clear();
            try{
                tree = createRandomTree();
            }
            catch(NumberFormatException ex){
                displayNumberFormatException();
            }
            updateDiagram();
            updateTransitionName();
        });

        balanceTreeBtn.addActionListener(e -> {
            tree.balanceTree();
            updateDiagram();
            updateTransitionName();
        });

        inorderBtn.addActionListener(e -> {
            tree.getInOrder();
            updateDiagram();
            updateTransitionName();
        });

        preOrderBtn.addActionListener(e -> {
            tree.getPreOrder(true);
            updateDiagram();
            updateTransitionName();
        });

        postOrderBtn.addActionListener(e -> {
            tree.getPostOrder();
            updateDiagram();
            updateTransitionName();
        });

        minNodeBtn.addActionListener(e -> {
            tree.showMinNode();
            updateDiagram();
            updateTransitionName();
        });

        maxNodeBtn.addActionListener(e -> {
            tree.showMaxNode();
            updateDiagram();
            updateTransitionName();
        });

        addNodeBtn.addActionListener(e -> {
            getNodeToAdd();
            updateDiagram();
        });

        findNodeBtn.addActionListener(e -> {
            getNodeToFind();
            updateDiagram();
        });

        delNodeBtn.addActionListener(e -> getNodeToDelete());

        deleteAllNodesButton.addActionListener(e -> {
            Transitions.clear();
            tree.clear();
            textArea.setText("(Empty tree)");
            updateTransitionName();
        });

        fontLargerBtn.addActionListener(e -> {
            originalDiagramFontSize += 2;
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, originalDiagramFontSize));
        });

        fontSmallerBtn.addActionListener(e -> {
            originalDiagramFontSize -= 2;
            textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, originalDiagramFontSize));
        });

        prevTransitionBtn.addActionListener(e -> {
            String diagram = Transitions.getPrevTransition();
            if (diagram != null) {
                updateDiagram(diagram);
            }
            updateTransitionName();

        });

        nextTransitionBtn.addActionListener(e -> {
            String diagram = Transitions.getNextTransition();
            if (diagram != null) {
                updateDiagram(diagram);
            }
            updateTransitionName();
        });

        transitionFastForwardBtn.addActionListener(e -> {

        });

        transitionFastBackwardBtn.addActionListener(e -> {

        });

        // Checkboxes:
        AvlRadioBtn.addActionListener(e -> {
            if (!(tree instanceof AVLTree)) { // not to start a new tree if is already selected
                tree = new AVLTree();
                Transitions.clear();
                textArea.setText("");
                balanceTreeBtn.setEnabled(false);
            }
        });

        BstRadioBtn.addActionListener(e -> {
            if (!(tree.getIdentifier().equals("bst"))) {
                tree = new BinarySearchTree();
                Transitions.clear();
                textArea.setText("");
                balanceTreeBtn.setEnabled(true);
            }
        });

        RedBlackRadioBtn.addActionListener(e -> {
            if (!(tree instanceof RedBlackTree)) {
                tree = new RedBlackTree();
                Transitions.clear();
                textArea.setText("");
                balanceTreeBtn.setEnabled(false);
            }
        });
    }

    private void updateTransitionName() {
        String transitionName = Transitions.getTransitionName();
        transitionNameLabel.setText(transitionName);
    }

   /* private void resizeFont(Dimension originalWindowSize) {
        Dimension newSize = panel1.getSize();
        // according to proportion:
        // 20 (originalDiagramFontSize) - (originalWindowSize)
        // x                            - (newSize)
        int x = (int) (originalDiagramFontSize * newSize.getHeight() / originalWindowSize.getHeight());

        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, x));
    }

    */


    private void makeFrameFullSize(JFrame frame) {
        Rectangle rect = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        frame.setSize(rect.getSize());
    }



    private void getNodeToAdd() {
        String value = valueInputTF.getText();
        if (isInteger(value)){
            boolean addedSuccessfully = addNode(value);
            if (!addedSuccessfully){
                displayNumberFormatException();
            }
        } else {
            displayNotIntErrorMsg();
        }
    }

    private void displayNumberFormatException() {
        String msg = "The number must be between -2147483648 to 2147483647";
        JOptionPane.showMessageDialog(null,msg);
    }

    private void displayNotIntErrorMsg() {
        String msg = "Input has to be integer";
        JOptionPane.showMessageDialog(null,msg);
    }

    private void displayValueMissingErrorMsg(int value) {
        String msg = "The tree does not have the value: " + value;
        JOptionPane.showMessageDialog(null,msg);
    }

    private boolean addNode(String value) {
        try {
            int intVal = Integer.parseInt(value);
            tree.addNode(intVal, true);
            return true;
        }
        catch(java.lang.NumberFormatException e){
            System.out.println(e.toString());
            return false;
        }
    }

    private void getNodeToFind() { // todo check if int is not too big/ too small
        String value = valueInputTF.getText();
        if (isInteger(value)){
            int intVal = Integer.parseInt(value);
            Node node = tree.findNode(intVal, true);
            if (node == null) {
                displayValueMissingErrorMsg(intVal);
            }
        }
        else {
            displayNotIntErrorMsg();
        }
    }

    // Gets node value to delete and deletes it
    private void getNodeToDelete() { // todo check if int is not too big/ too small
        boolean deleted;
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
            updateTransitionName();
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
    // It could ask the user input for numbers used, though I sometimes use it for manual testing
    private Tree createRandomTree() throws NumberFormatException {

        Tree randTree;
        if (AvlRadioBtn.isSelected()){
            randTree = new AVLTree();
        }
        else if (BstRadioBtn.isSelected()) {
            randTree = new BinarySearchTree();
        }
        else {
            randTree = new RedBlackTree();
        }

        int nodesMinAmountDefault = 5;
        int nodesMaxAmountDefault = 20 + nodesMinAmountDefault;
        int nodesAmount = (int) (Math.random() * nodesMaxAmountDefault + nodesMinAmountDefault);
        if (!nodesAmountTextField.getText().isEmpty())
        {
            String nodesAmountStr = nodesAmountTextField.getText();
            if (isInteger(nodesAmountStr)){
                nodesAmount = Integer.parseInt(nodesAmountStr);
            }
            else {
                displayNotIntErrorMsg();
                return null;
            }
        }


        int maxValue = 50;
        if (!maxValueTextField.getText().isEmpty())
        {
            String nodesAmountStr = maxValueTextField.getText();
            if (isInteger(nodesAmountStr)){
                maxValue = Integer.parseInt(nodesAmountStr);
            }
            else {
                displayNotIntErrorMsg();
                return null;
            }
        }

        int minValue = -50;
        if (!minValueTextField.getText().isEmpty())
        {
            String nodesAmountStr = minValueTextField.getText();
            if (isInteger(nodesAmountStr)){
                minValue = Integer.parseInt(nodesAmountStr);
            }
            else {
                displayNotIntErrorMsg();
                return null;
            }
        }

        // 2. Limit: Nodes Values
        for (int i = 0; i < nodesAmount; i++) {
            // from -50 to 50
            int randNodeVal = (int) (Math.random() * ((maxValue - minValue) + 1)) + minValue;
            randTree.addNode(randNodeVal, true);
        }

        return randTree;
    }


    private void updateDiagram(){
        Transitions.visitLast();
        String diagram = Transitions.getLastTransition();
        diagram = diagram.replaceAll("%", "[")
                .replaceAll("\\$","[");
        highlightTreeParts(diagram);

    }

    private void updateDiagram(String diagram){
        diagram = diagram.replaceAll("%", "[")
                .replaceAll("\\$","[");
        highlightTreeParts(diagram);
    }

    private void highlightTreeParts(String diagram) {
        textArea.setText(diagram);

        if (!diagram.equals(("(Empty tree)").toLowerCase())) {

            Boolean[] stackedNodes = Transitions.getStackedNodes();
            ArrayList<String> stackedStringList = getTextToColour(diagram, stackedNodes);
            for (String s : stackedStringList) {
                highlight(textArea, s, highlightStacked);
            }

            Boolean[] markedNodes = Transitions.getMarkedNodes();
            ArrayList<String> markedStringList = getTextToColour(diagram, markedNodes);
            for (String s : markedStringList) {
                highlight(textArea, s, highlightMarked);
            }
        }
    }


    private ArrayList<String> getTextToColour(String diagram, Boolean[] colourBoolArr){
        ArrayList <String> colorfulText = new ArrayList<>();

    //    assert (diagram.length()-1 == colourBoolArr.length);

        char[] diagramChars = diagram.toCharArray();

        StringBuilder highlightedStrBuilder = new StringBuilder();
        boolean appending = false;

        for (int i = 0; i < colourBoolArr.length; i++) {
            if(colourBoolArr[i]){
                appending = true;
                highlightedStrBuilder.append(diagramChars[i]);
            }
            else if (appending){
                appending = false;
                colorfulText.add(highlightedStrBuilder.toString());
                highlightedStrBuilder = new StringBuilder();
            }
        }
        if (appending){
            colorfulText.add(highlightedStrBuilder.toString());
        }

        return colorfulText;
    }

    private static boolean isInteger(String s) {
        return s.matches("-?(0|[1-9]\\d*)");
    }

    public void highlight (JTextComponent textComponent, String pattern, Highlighter.HighlightPainter painter){
        try {
            Highlighter hilite = textComponent.getHighlighter();
            Document doc = textComponent.getDocument();
            String text = doc.getText(0, doc.getLength());
            int pos = 0;
            while((pos=text.toUpperCase().indexOf(pattern.toUpperCase(), pos))>=0){
                hilite.addHighlight(pos, pos + pattern.length(), painter);
                pos += pattern.length();
            }
        }
        catch(Exception e){
            System.out.println(e.toString());
        }
    }

    private void createUIComponents() {
        maxValueTextField = new HintTextField("default: 50");
        minValueTextField = new HintTextField("default: -50");
        nodesAmountTextField = new HintTextField("default: random from [5, 25]");
    }

    private static class MyHighlightPainter extends DefaultHighlighter.DefaultHighlightPainter{

        /**
         * Constructs a new highlight painter. If <code>c</code> is null,
         * the JTextComponent will be queried for its selection color.
         *
         * @param c the color for the highlight
         */
        public MyHighlightPainter(Color c) {
            super(c);
        }

    }
}
