package TreeModellingTool.Model.RedBlack;

import TreeModellingTool.Model.BST.AbstractBST;
import TreeModellingTool.Model.Node;
import TreeModellingTool.Model.Tree;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class RedBlackTree extends AbstractBST implements Tree {

    // TODO:
    // Implement all the methods

    private final String IDENTIFIER = "RBT";

    public RedBlackTree(){
        super.identifier = IDENTIFIER;
        throw new NotImplementedException();
    }

    @Override
    public void addNode(Node node, boolean doTransitions) {

    }

    @Override
    protected Node deleteRec(Node root, int value) {
        return null;
    }

    protected Node addRecursive(Node current, int value, Boolean doTransitions) {
        return null;
    }

    @Override
    public void addNode(int value, boolean doTransitions) {

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

    @Override
    public String getIdentifier() {
        return identifier;
    }
}
