function getTitle(data, OrderStatusW) {

    var content = '<p class="banktotal">';

    let  totalmoney = 0;
    let  totalnum = 0;
    for (let status in OrderStatusW) {
        let ishave = false;
        for (let i = 0; i < data && data.length; i++) {
            if (status == data[i].orderstatus) {
                totalmoney += data[i].payamount;
                totalnum += data[i].num;
                ishave = true;
                index = data[i];
                content += '<span style="float:right; margin-right: 20px">' + getOrderStatus(index.orderstatus, true) + '<b></b><span style="color:red;padding-right:10px;padding-left:5px">' + index.num +'笔/'+index.payamount+'元</span></span>';
            }
        }
        if (ishave == false) {
            content += '<span style="float:right; margin-right: 20px">' + getOrderStatus(status, true) + '<b></b><span style="color:red;padding-right:10px;padding-left:5px">' + '0笔/0元' + '</span></span>';
        }
    }
    content += '<span style="float:right; margin-right: 20px">总数<b></b><span style="color:red;padding-right:10px;padding-left:5px">' + totalnum+'笔/'+totalmoney + '元</span></span>';

    return content + "</p>";
}

function getOrderStatus(id, iscolor=false) {
    for (let key in OrderStatus) {
        if (id == key) {
            let valuename = OrderStatus[key];
            if(iscolor == true){
                return  valuename;
            }else{
                // "0=创建,1=处理中,2=出款中,3=成功,4=手工补单,-1=挂起,5=超时,6=超时失败,7=失败
                if (id == 7 || id == 5) {
                    return ` <a class="badge badge-danger ">${valuename}</a>`;
                }
                if (id == 3 || id==4) {
                    return ` <a class="badge badge-primary ">${valuename}</a>`;
                }
                if (id == 1) {
                    return ` <a class="badge badge-warning ">${valuename}</a>`;
                }
            }
            return valuename;
        }
    }
}

function onClickSearch() {
    if ($.validate.form()) {
        $.table.search();
        loadTotal();
    }
}


function onClickReset() {
    $.form.resetDate();
    loadTotal();
}


