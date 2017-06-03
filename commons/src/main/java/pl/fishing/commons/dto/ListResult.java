package pl.fishing.commons.dto;

import java.util.List;

/**
 * Created by lukas on 03.06.2017.
 */
public class ListResult<T> {

    private Long count;
    private List<T> list;


    public ListResult (Long count, List<T> list){
        this.count = count;
        this.list = list;
    }
    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
