package hechuyengiaid3;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author HongNguyen (dối trá)
 */
public class HeChuyenGiaID3 {

    public static void main(String[] args) {
        
        String[][] example = {
            {"cao",         "cao",          "manh",     "it",       "mua"},//1
            {"cao",         "cao",          "manh",     "nhieu",    "mua"},//2
            {"trung binh",  "cao",          "manh",     "it",       "mua"},//3
            {"thap",        "trung binh",   "manh",     "it",       "tanh"},//4
            {"thap",        "thap",         "nhe",      "it",       "tanh"},//5
            {"thap",        "cao",          "nhe",      "nhieu",    "mua"},//6
            {"trung binh",  "thap",         "nhe",      "nhieu",    "tanh"},//7
            {"cao",         "trung binh",   "manh",     "it",       "mua"},//8
            {"cao",         "thap",         "nhe",      "it",       "tanh"},//9
            {"thap",        "trung binh",   "nhe",      "it",       "tanh"},//10
            {"cao",         "trung binh",   "nhe",      "nhieu",    "tanh"},//11
            {"trung binh",  "trung binh",   "manh",     "nhieu",    "tanh"},//12
            {"trung binh",  "cao",          "nhe",      "it",       "mua"},//13
            {"thap",        "trung binh",   "manh",     "nhieu",    "mua"}//14
        };
        ArrayList<ArrayList<String>> data = new ArrayList<>();
        for (String[] example1 : example) {
            ArrayList<String> list = new ArrayList<>(Arrays.asList(example1));
            data.add(list);
        }
        ArrayList<String> TARGET = new ArrayList<>();
        TARGET.add("mua");
        TARGET.add("tanh");
        ArrayList<Attribute> attrs = new ArrayList<>();
        Attribute a1 = new Attribute("nhiet do", new String[]{"cao", "trung binh", "thap"});
        Attribute a2 = new Attribute("do am", new String[]{"cao", "trung binh", "thap"});
        Attribute a3 = new Attribute("gio", new String[]{"manh", "nhe"});
        Attribute a4 = new Attribute("may", new String[]{"it", "nhieu"});
        attrs.add(a1);
        attrs.add(a2);
        attrs.add(a3);
        attrs.add(a4);

        ThuatToanID3 id3 = new ThuatToanID3(data, attrs, TARGET);
        id3.GetTree();
        System.out.println("Depth: " + id3.solution);
        ArrayList<String> value = new ArrayList<>();
        value.add("cao");
        value.add("cao");
        value.add("manh");
        value.add("nhieu");
        forecastWeather(id3.tree, value, 0);
    }

    public static void forecastWeather(TreeNode tree, ArrayList<String> data, int depth) {
        if (tree.n == 0) {
            System.out.println("Kết quả kiểm tra: " + tree.attrs.name);
        }

        for (int i = 0; i < tree.attrs.value.length; i++) {
            for (int j = 0; j < data.size(); j++) {
                if (data.get(j) == null ? tree.attrs.value[i] == null : data.get(j).equals(tree.attrs.value[i])) {
                    data.remove(depth);
                    depth++;
                    forecastWeather(tree.childs[i], data, depth);
                }
            }
        }
    }
}
