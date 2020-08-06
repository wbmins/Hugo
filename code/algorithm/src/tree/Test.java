import java.util.*;

class DataNode implements dataTree<DataNode> {

    Integer id;

    Integer parentId;

    String label;

    List<DataNode> childList;


    DataNode(Integer id, Integer parentId, String label) {
        this.id = id;
        this.parentId = parentId;
        this.label = label;
        this.childList = new ArrayList<>();
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public Integer getParentId() {
        return this.parentId;
    }

    @Override
    public void setChildList(List<DataNode> childList) {
        this.childList = childList;
    }

    @Override
    public List<DataNode> getChildList() {
        return this.childList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}


public class Test {


    public static void main(String[] args) {
        DataNode d1 = new DataNode(0, -1, "总部");
        DataNode d2 = new DataNode(1, 0, "财务部");
        DataNode d3 = new DataNode(2, 0, "教务部");
        DataNode d4 = new DataNode(3, 0, "财务部");
        DataNode d5 = new DataNode(4, 0, "后勤部");
        DataNode d6 = new DataNode(5, 0, "党务部");
        DataNode d7 = new DataNode(6, 2, "一年级语文部");
        DataNode d8 = new DataNode(7, 2, "一年级数学部");
        DataNode d9 = new DataNode(8, 2, "一年级英语部");
        DataNode d10 = new DataNode(9, 2, "二年级语文部");
        DataNode d11 = new DataNode(10, 2, "二年级数学部");
        DataNode d12 = new DataNode(11, 2, "二年级英语部");
        DataNode d13 = new DataNode(12, 6, "张三");
        DataNode d14 = new DataNode(13, 6, "李四");
        DataNode d15 = new DataNode(14, 7, "王五");
        DataNode d16 = new DataNode(15, 8, "彩笔");
        DataNode d17 = new DataNode(16, 9, "哈哈");
        DataNode d18 = new DataNode(17, 10, "四放");
        DataNode d19 = new DataNode(18, 11, "巧手");
        DataNode d20 = new DataNode(19, 1, "巧手1");
        DataNode d21 = new DataNode(20, 1, "巧手2");
        DataNode d22 = new DataNode(21, 3, "巧手3");
        DataNode d23 = new DataNode(22, 4, "巧手4");
        DataNode d24 = new DataNode(23, 5, "巧手5");
        DataNode d25 = new DataNode(24, 5, "巧手6");
        DataNode d26 = new DataNode(25, 5, "巧手7");

        List<DataNode> list = new ArrayList<>();
        list.add(d1);
        list.add(d2);
        list.add(d3);
        list.add(d4);
        list.add(d5);
        list.add(d6);
        list.add(d7);
        list.add(d8);
        list.add(d9);
        list.add(d10);
        list.add(d11);
        list.add(d12);
        list.add(d13);
        list.add(d14);
        list.add(d15);
        list.add(d16);
        list.add(d17);
        list.add(d18);
        list.add(d19);
        list.add(d20);
        list.add(d21);
        list.add(d22);
        list.add(d23);
        list.add(d24);
        list.add(d25);
        list.add(d26);
        List<DataNode> data = getTreeList(0, list);
        printTree(data);

    }

    public static void printTree(List<DataNode> data) {
        for (DataNode item : data) {
            System.out.println(item.getLabel());
            if (item.childList.size() != 0) {
                printTree(item.childList);
            }
        }
    }

    public static <T extends dataTree<T>> List<T> getTreeList(Integer topId, List<T> entityList) {
        List<T> resultList = new ArrayList<>();//存储顶层的数据

        Map<Object, T> treeMap = new HashMap<>();
        T itemTree;

        for (int i = 0; i < entityList.size() && !entityList.isEmpty(); i++) {
            itemTree = entityList.get(i);
            treeMap.put(itemTree.getId(), itemTree);//把所有的数据放到map当中，id为key
            if (topId.equals(itemTree.getParentId()) || itemTree.getParentId() == null) {//把顶层数据放到集合中
                resultList.add(itemTree);
            }
        }

        //循环数据，把数据放到上一级的 children 属性中
        for (int i = 0; i < entityList.size() && !entityList.isEmpty(); i++) {
            itemTree = entityList.get(i);
            T data = treeMap.get(itemTree.getParentId());//在map集合中寻找父亲
            if (data != null) {//判断父亲有没有
                if (data.getChildList() == null) {
                    data.setChildList(new ArrayList<>());
                }
                data.getChildList().add(itemTree);//把子节点 放到父节点childList当中
                treeMap.put(itemTree.getParentId(), data);//把放好的数据放回map当中
            }
        }
        return resultList;
    }


}

