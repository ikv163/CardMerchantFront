package com.pay.typay.biz.reports.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TreeParser {
    public static <E extends TreeEntity<E>> List<E> getTreeList(String parent, List<E> entityList) {
        List<E> resultList=new ArrayList<>();
        //添加顶层元素集合
        resultList.addAll(entityList.stream().filter(e -> parent.equals(e.getParentAgentId())).collect(Collectors.toList()));
        //获取每个顶层元素的子数据集合
        for (E entity : resultList) {
            entity.setChildList(getSubList(entity.getId(),entityList));
        }
        return resultList;
    }

    private  static  <E extends TreeEntity<E>>  List<E> getSubList(String code, List<E> entityList) {
        List<E> childList=new ArrayList<>();
        //子集的直接子对象
        childList.addAll(entityList.stream().filter(e -> code.equals(e.getParentAgentId())).collect(Collectors.toList()));

        //子集的间接子对象
        for (E entity : childList) {
            entity.setChildList(getSubList(entity.getId(), entityList));
        }
        //递归退出条件
        if(childList == null || childList.isEmpty()){
            return null;
        }
        return childList;
    }
}
