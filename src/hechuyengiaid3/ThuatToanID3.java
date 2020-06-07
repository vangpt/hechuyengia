package hechuyengiaid3;

import static java.lang.Math.log;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThuatToanID3 {

    ArrayList<ArrayList<String>> data;
    ArrayList<Attribute> attrs;
    ArrayList<String> target = new ArrayList<>();
    String solution = "";
    TreeNode tree;
    int depth = 0;

    public ThuatToanID3() {
    }

    public ThuatToanID3(ArrayList<ArrayList<String>> data, ArrayList<Attribute> attrs, ArrayList<String> target) {
        this.data = data;
        this.attrs = attrs;
        this.target = target;
        this.solution = "";
        this.tree = null;
        this.depth = 0;
    }

    public ArrayList<ArrayList<String>> getData() {
        return data;
    }

    public void setData(ArrayList<ArrayList<String>> data) {
        this.data = data;
    }

    public ArrayList<Attribute> getAttrs() {
        return attrs;
    }

    public void setAttrs(ArrayList<Attribute> attrs) {
        this.attrs = attrs;
    }

    public ArrayList<String> getTarget() {
        return target;
    }

    public void setTarget(ArrayList<String> target) {
        this.target = target;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public TreeNode getTree() {
        return tree;
    }

    public void setTree(TreeNode tree) {
        this.tree = tree;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    //tính Entropy
    public double Entropy(int[] arrayValue) {
        int dem = 0;
        for (int i = 0; i < arrayValue.length; i++) {
            if (arrayValue[i] == 0) {
                dem++;
            }
        }
        if (dem == arrayValue.length) {
            return 0;
        } else {
            double total = 0;
            for (int i = 0; i < arrayValue.length; i++) {
                total += arrayValue[i];
            }
            double[] rates = new double[10];
            for (int i = 0; i < arrayValue.length; i++) {
                rates[i] = arrayValue[i] / total;
            }
            double entropy = 0;
            for (int i = 0; i < arrayValue.length; i++) {
                if (rates[i] > 0) {
                    entropy -= rates[i] * log(rates[i]) / log(2);
                }
            }

            return entropy;
        }
    }

    //tính Gian
    public double Gain(ArrayList<ArrayList<String>> data, Attribute attribute, String bestAt) {
        int sizeTarget = this.target.size();
        int[] countBase = new int[sizeTarget];
        for (int i = 0; i < sizeTarget; i++) {
            countBase[i] = 0;
        }
        int[][] countAttr = new int[10][10];
        int col = this.attrs.indexOf(attribute);

        if (attribute != null) {
            for (int i = 0; i < attribute.getValue().length; i++) {
                for (int j = 0; j < sizeTarget; j++) {
                    countAttr[i][j] = 0;
                }
            }
            for (ArrayList<String> data1 : data) {
                int j = Arrays.asList(attribute.getValue()).indexOf(data1.get(col));

                if (j > -1) {
                    String valTargetData = data1.get(data.get(0).size() - 1);
                    int idx = this.target.indexOf(valTargetData);
                    if (idx > -1) {
                        countBase[idx]++;
                        countAttr[j][idx]++;
                    }
                }
            }
            int total = data.size();
            double result = this.Entropy(countBase);
            for (int i = 0; i < attribute.getValue().length; i++) {
                double rateValue = 0;
                for (int j = 0; j < countAttr[i].length; j++) {
                    rateValue += countAttr[i][j];
                }
                result -= rateValue * this.Entropy(countAttr[i]) / total;
            }
            return result;
        }
        return 0;
    }

    // tim thuoc tinh co do loi thong tin nhat
    public Attribute getBestAttribute(ArrayList<ArrayList<String>> data, ArrayList<Attribute> attrs, String bestAt) {
        double maxGain = this.Gain(data, attrs.get(0), bestAt);
        int idxMax = 0;

        for (int i = 0; i < attrs.size(); i++) {
            double curGain = this.Gain(data, attrs.get(i), bestAt);
            if (maxGain < curGain) {
                maxGain = curGain;
                idxMax = i;
            }
        }
        this.solution += "\n=> Ta chọn thuộc tính tốt nhất là: " + attrs.get(idxMax).name;

        return attrs.get(idxMax);
    }

    //kiểm tra mục tiêu có bị trùng lặp hay không
    public boolean isDuplicateData(ArrayList<ArrayList<String>> data) {
        ArrayList<String> newData = new ArrayList<>();
        ArrayList<String> duplicateData = new ArrayList<>();

        for (ArrayList<String> i : data) {
            newData.add(i.get(data.get(0).size() - 1));
        }
        for (String newData1 : newData) {
            if (!duplicateData.contains(newData1)) {
                duplicateData.add(newData1);
            }
        }

        return duplicateData.size() <= 1;
    }

    public TreeNode ID3(ArrayList<ArrayList<String>> data, ArrayList<Attribute> attrs, String bestAt) {
        this.solution += "\nXét nút: " + bestAt;
        if (this.isDuplicateData(data)) {
            this.solution += "\nTrả về nút gốc với nhãn: " + GetMostCommonValue(data);
            return new TreeNode(new Attribute(GetMostCommonValue(data)));
        }
        Attribute bestAttr = this.getBestAttribute(data, attrs, bestAt);
        int idxMax = attrs.indexOf(bestAttr);
        TreeNode root = new TreeNode(bestAttr);

        for (int i = 0; i < bestAttr.getValue().length; i++) {
            ArrayList<ArrayList<String>> newData = new ArrayList<>();
            for (ArrayList<String> data1 : data) {
                if (data1.get(idxMax).equals(bestAttr.value[i])) {
                    newData.add(data1);
                }
            }

            if (newData.isEmpty()) {
                this.setSolution("\nCác thuộc tính rỗng => Trả về nút gốc có giá trị phổ biến nhất");
                return new TreeNode(new Attribute(GetMostCommonValue(newData)));
            } else {
//                attrs.remove(bestAttr);
                attrs.set(idxMax, null);
                root.addNode(this.ID3(newData, attrs, bestAttr.value[i]));
            }
        }

        return root;
    }

    //xây dụng cây
    public void GetTree() {
        ArrayList<Attribute> att = new ArrayList<>();
        for (Attribute attr : this.attrs) {
            att.add(attr);
        }
        ArrayList<ArrayList<String>> d = this.data;
        this.tree = ID3(d, att, "gốc");
        this.depth = GetDepth(this.tree);
    }

    //lấy độ sâu của cây
    public int GetDepth(TreeNode tree) {
        int depthLocal = 0;

        try {
            if (tree.childs.length == 0) {
                return 0;
            } else {

                depthLocal = this.GetDepth(tree.childs[0]);
                for (int i = 1; i < tree.childs.length; i++) {
                    int depthChild = this.GetDepth(tree.childs[i]);
                    if (depthLocal < depthChild) {
                        depthLocal = depthChild;
                    }
                }
                depthLocal++;
            }
        } catch (Exception e) {
        };
        return depthLocal;
    }

    //lấy giá trị phổ biến nhất của tập đích
    public String GetMostCommonValue(ArrayList<ArrayList<String>> data) {
        int[] dem = new int[this.target.size()];
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j < target.size(); j++) {
                if (data.get(i).get(data.get(0).size() - 1).equals(target.get(j))) {
                    dem[j]++;
                }
            }
        }
        int max = dem[0];
        int index = 0;
        for (int i = 0; i < dem.length; i++) {
            if (max < dem[i]) {
                max = dem[i];
                index = i;
            }
        }

        return target.get(index);
    }
}
