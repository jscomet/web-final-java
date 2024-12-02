package homework.web.util.beans;

import java.util.List;

/**
 * @param <T> 数组类型响应
 */
public class ListResult<T> {
    private List<T> list;
    private Long total;

    public ListResult() {
    }

    public ListResult(List<T> list) {
        this.list = list;
        this.total = (long)list.size();
    }

    public ListResult(List<T> list, Integer total) {
        this.list = list;
        this.total = (long)total;
    }

    public ListResult(List<T> list, Long total) {
        this.list = list;
        this.total = total;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Long getTotal() {
        return this.total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }
}

