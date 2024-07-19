package com.tian.my_qa.dto;

import java.util.List;

public class Pager<E> {
    private Integer total;
    private List<E> list;
    public Pager (List<E> list, Integer total) {
        this.total = total;
        this.list = list;
    }
    public Integer getTotal() {
        return this.total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<E> getList() {
        return this.list;
    }

    public void setList(List<E> list) {
        this.list = list;
    }

}
