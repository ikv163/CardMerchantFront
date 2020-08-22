package com.pay.typay.biz.reports.domain;

import java.util.List;

public interface TreeEntity<E> {
    String getId();
    String getParentAgentId();
    void setChildList(List<E> childList);
}
