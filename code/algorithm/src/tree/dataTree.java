import java.util.List;

public interface dataTree<T> {

     Integer getId();

     Integer getParentId();

     void setChildList(List<T> childList);

     List<T> getChildList();

}
