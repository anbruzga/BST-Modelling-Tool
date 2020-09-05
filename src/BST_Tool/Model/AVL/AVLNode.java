package BST_Tool.Model.AVL;

import BST_Tool.Model.BST.BSTNode;
import BST_Tool.Model.Node;

public class AVLNode extends BSTNode implements Node {

    private int height;

    public AVLNode(int elem) {
        super(elem);
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
