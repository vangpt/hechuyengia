package hechuyengiaid3;

import com.sun.corba.se.impl.protocol.giopmsgheaders.MessageBase;
import java.util.ArrayList;

public class TreeNode {

    Attribute attrs;
    int n;
    int numberLabel;
    TreeNode[] childs;

    public TreeNode(Attribute attrs, TreeNode[] childs) {
        this.attrs = attrs;
        this.n = 0;
        this.numberLabel = 0;
        this.childs = childs;
    }

    public Attribute getAttrs() {
        return attrs;
    }

    public void setAttrs(Attribute attrs) {
        this.attrs = attrs;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public int getNumberLabel() {
        return numberLabel;
    }

    public void setNumberLabel(int numberLabel) {
        this.numberLabel = numberLabel;
    }

    public TreeNode[] getChilds() {
        return childs;
    }

    public void setChilds(TreeNode[] childs) {
        this.childs = childs;
    }

    public TreeNode(Attribute attrs) {
        this.attrs = attrs;
        this.childs = new TreeNode[attrs.value.length];
        this.n = 0;
        for (int i = 0; i < attrs.value.length; i++) {
            this.childs[i] = null;
        }
        if (attrs.value.length == 0) {
            numberLabel = 1;
        } else {
            numberLabel = 0;
        }
    }

    public void addNode(TreeNode child) {
        try {
            if (this.getN() < this.getChilds().length) {
                //System.out.println("" + this.getN());
                this.childs[this.getN()] = child;
                this.numberLabel = this.numberLabel + child.numberLabel;
                //System.out.println("hihihi");
            }

        } catch (Exception e) {
            System.out.print("lỗi add node");
        }
        this.n++;
        //System.out.println(this.n+"");
    }
}
