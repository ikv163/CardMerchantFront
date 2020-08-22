
        //拖动回调事件
        function dragCallback() {
            saveTableCustom(customCode);
        }


        //去重
        function unique(arr, type) {
            const res = new Map();
            return arr.filter((a) => !res.has(a[type]) && res.set(a[type], 1));
        }

        function loadInitColumnData(column2,options){
            var array=[];
            //剔除没有权限的列
            $.each(options.columns,function (index,value) {
                //console.log(value.field+"--"+value.hasPermission)
                //undefined 或者 '' 有权限 ，hidden 无权限
                if(value.hasPermission===undefined || value.hasPermission===''){
                    //将有权限的加入数组
                    array.push(value);
                }
            });
            if(column2 === null  || column2 === undefined){
                options.columns=array;//用作初始化表格
                return  false;
            }
            //去除重复字段
            var column = unique(column2,"field");//去除重复的字段
            var sortName='';
            var sortOrder='';
            let tempColumn=[];
            //第一步去除相同的字段,得到不同的字段
            options.columns=array.concat();//一定要用concat 方法重新赋值
            $.each(column, function (index, value) {
                for(let i=0;i<array.length;i++){
                    if(value.field === array[i].field){
                        array.splice(i,1);
                        i--;
                    }
                }
            });
            //第二步取出本地和数据库相同的字段
            $.each(column, function (index, value) {
                $.each(options.columns, function (columnIndex, columnValue) {
                    if (value.field === columnValue.field) {
                        if(value.sortName!=='' && value.sortName !== undefined){
                            sortName=value.sortName;
                            sortOrder=value.sortOrder;
                        }
                        columnValue.visible=true;
                        tempColumn.push(columnValue);
                        return false;
                    }
                });
            });

            if(options.showColumns) {
                $.each(array, function (index, value) {
                    value.visible = false;
                    //console.error(value.field)
                })
            }
            tempColumn=tempColumn.concat(array);
           options.columns=tempColumn;//用作初始化表格
            //console.log(sortName +"--"+sortOrder)
            //得到默认排序的字段
            if(sortName!=='' && sortName !==undefined){
                options.sortName=sortName;
                options.sortOrder=sortOrder;
            }
           columnList=tempColumn;//用作保存上次拖动后的状态

        }

       /**
        * 初始化Tree表格数据
        */
        function initTreeTableData(column) {
            let options=assembleOptions();
            options["onPostHeader"]=onPostHeader;
            loadInitColumnData(column,options);//重新加载表格数据
            $.treeTable.init(options);//表格刷新数据
        }


        /**
         * 初始化表格数据
         */
        function initTableData(column) {
            let options = assembleOptions();
            options["onPostHeader"]=onPostHeader;
            loadInitColumnData(column, options);//重新加载表格数据
            $.table.init(options);//表格刷新数据

            if(typeof initFooterTableData=="function"){
                initFooterTableData();
            }
            if (options.showColumns) {
                //监听隐藏，显示列点击事件
                $(".dropdown-menu-visible").on("click", function () {
                    saveTableCustom(customCode, 0)
                })
            }
        }

        //在表格列头渲染完成，并在 DOM 中可见后触发
        function onPostHeader() {
            $('#bootstrap-table').dragTableColumns();
        }
        //保存表格自定义信息
        function saveTableCustom(customCode,mark,sortName,sortOrder) {
            let thNode=$("#drag-row").children("th");
            let array=[];//声明一个数组
            thNode.each(function(index,th){
                let columnField=$(th).attr("data-field");
                //let sortIndex=index;
                let object={};//声明对象
                object["field"]=columnField;
                //console.log("sortName："+sortName)
                if(columnField === sortName){
                    object["sortName"]=sortName;
                    object["sortOrder"]=sortOrder;
                }
                array.push(object)
            });
            let object={};
            object["customCode"]=customCode;
            object["customContent"]=array;
            let data = {
                "data": JSON.stringify(object)
            };

           $.ajax({
                type: 'post',
                url: ctx+"system/tablecustom/add",
                data: data,
                dataType: "json",
                beforeSend: function (request) {
                    request.setRequestHeader("newDate", new Date().getTime());
                    //$.modal.loading("正在处理中，请稍后...");
                    //$.modal.disable();
                },
                success: function (result) {
                    switch (result.code) {
                        case web_status.SUCCESS:
                            // console.log("表格类型================="+table.options.type)
                            if (table.options.type === table_type.bootstrapTable) {
                                //$.modal.close();
                                //parent.$.modal.msgSuccess(result.msg);
                                if(mark !==0) {
                                  $('#bootstrap-table').bootstrapTable('destroy');//销毁table
                                   initTableData(array);//重新初始化表格
                                    if(typeof  loadTotal=='function'){
                                        loadTotal();
                                    }
                               }
                            } else if (table.options.type === table_type.bootstrapTreeTable) {
                                // $.modal.close();
                                //parent.$.modal.msgSuccess(result.msg);
                               // console.log("treeTable")
                                if(mark !==0) {
                                   $('#bootstrap-tree-table').bootstrapTable('destroy');//销毁table
                                    initTreeTableData(columnList);
                                    if(typeof  loadTotal=='function'){
                                        loadTotal();
                                    }
                                }
                            } else {
                                //$.modal.msgReload("保存成功,正在刷新数据请稍后……", modal_status.SUCCESS);
                            }
                            break;
                       case web_status.WARNING:
                            $.modal.alertWarning(result.msg);
                            break;
                        default:
                            if(mark !==0) {
                                //$('#bootstrap-table').bootstrapTable('destroy');//销毁table
                                initTableData(columnList);//重新初始化表格
                                if(typeof  loadTotal=='function'){
                                    loadTotal();
                                }
                                $.modal.alertError(result.msg);
                            }
                            break;
                    }
                    $.modal.closeLoading();
                    $.modal.enable();
                }
            });
        }
   
