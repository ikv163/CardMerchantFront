package com.pay.typay.common.core.page;

import com.pay.typay.common.core.domain.SubtotalEntity;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author js-oswald
 */
@Data
public class TableDataInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private BigDecimal totalwithdraw;
    private BigDecimal totaldeposit;
    /**
     * 总记录数
     */
    private long total;

    /**
     * 列表数据
     */
    private List<?> rows;

    /**
     * 消息状态码
     */
    private int code;

    /**
     * 消息内容
     */
    private int msg;

    /**
     * 查询到账户总余额
     */
    private BigDecimal sumBalance;

    /**
     * 小计
     */
    private BigDecimal curPageTotal;
    /**
     * 总计
     */
    private BigDecimal totalAmount;

    /**
     * 小计entity
     */
    private SubtotalEntity subtotal;
    /**
     * 总计entity
     */
    private SubtotalEntity totalEntity;
    /**
     * 表格数据对象
     */
    public TableDataInfo() {
    }

    /**
     * 分页
     *
     * @param list  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> list, int total) {
        this.rows = list;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getMsg() {
        return msg;
    }

    public void setMsg(int msg) {
        this.msg = msg;
    }
}