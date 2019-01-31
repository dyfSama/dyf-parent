// NProgress.start();
// window.onload = function () {
//     NProgress.done();
// };
(function ($) {
    $.extend({
        table: { //bootstrap表格
            _option: {},
            init: function (options) {
                $.table._option = options;
                //表格初始化通用方法
                $('#bootstrap-table').bootstrapTable({
                    url: options.url,
                    dataType: "json",
                    toolbar: "#" + options.tableToolbar, //工具栏
                    columns: options.columns,  //列信息
                    queryParamsType: "limit",
                    queryParams: options.queryParams,  //查询参数,分页和自定义参数
                    uniqueId: "id",
                    cache: true,
                    pageNumber: 1,  //初始页码
                    pageSize: 10,   //初始每页记录数
                    pagination: true, // 在表格底部显示分页组件，默认false
                    sidePagination: 'server', //设置为服务器端分页
                    pageList: '[5, 10, 20]',  // 设置页面可以显示的数据条数
                    paginationPreText: "上一页",  //自定义内容
                    paginationNextText: "下一页", //自定义内容
                    paginationLoop: false,  //默认true
                    // search: true,           //默认false
                    // searchOnEnterKey: true, //搜索框搜索策略, 默认false: 改变就搜索
                    // showPaginationSwitch: true,
                    clickToSelect: true, //点击选择,默认false
                    showRefresh: true, //右上角刷新按钮
                    showToggle: true, //右上角切换按钮
                    showColumns: true, //右上角列按钮
                    iconSize: "outline", //右上角按钮风格
                    icons: {//右上角按钮图标
                        refresh: " glyphicon-repeat",
                        toggle: "glyphicon-list-alt",
                        columns: "glyphicon-list"
                    },
                    onLoadSuccess: function (data) {  //加载成功时执行

                        if (data.status === "2") {
                            $.modal.msg_fail(data.message);
                        }
                        //查询方法
                        $('.btn-reset').click(function () {
                            $('.form-control').val('');
                        });
                    },
                    onLoadError: function () {  //加载失败时执行
                    }
                });
            },
            refresh: function () { //表格刷新(工具栏查询)
                $('#bootstrap-table').bootstrapTable('refresh');
            }
        },
        treeTable: {
            _instance: {},
            _option: {},
            init: function (options) {
                $.treeTable._option = options;
                $.treeTable._instance = $('#tree-table').bootstrapTreeTable({
                    id: 'id',// 选取记录返回的值
                    code: 'id',             // 用于设置父子关系
                    parentCode: 'parentId', // 用于设置父子关系
                    type: 'get',                   // 请求方式（*）
                    url: options.url,              // 请求后台的URL（*）
                    expandColumn: 1,// 在哪一列上面显示展开按钮
                    expandAll: true, // 是否全部展开
                    expandFirst: true, // 是否默认第一级展开--expandAll为false时生效
                    bordered: false, //边框
                    // striped: true, // 是否各行渐变色
                    clickToSelect: false,
                    // showToggle: true, //右上角切换按钮
                    // showColumns: true, //右上角列按钮
                    // iconSize: "outline", //右上角按钮风格
                    // icons: {
                    //     refresh: " glyphicon-repeat",
                    //     toggle: "glyphicon-list-alt",
                    //     columns: "glyphicon-list"
                    // }, //右上角按钮图标
                    toolbar: "#" + options.tableToolbar,//顶部工具条
                    expanderExpandedClass: 'glyphicon glyphicon-chevron-down',// 展开的按钮的图标
                    expanderCollapsedClass: 'glyphicon glyphicon-chevron-right',// 缩起的按钮的图标
                    columns: options.columns
                });
            },
            refresh: function (params) {
                if (typeof $.treeTable._instance.bootstrapTreeTable === "function") {
                    $.treeTable._instance.bootstrapTreeTable('refresh', params);
                }
            }

        },
        ztree: {
            _ele: {},//ztree元素id
            init: function (ztreeOption) { //ztree 初始化
                $.ztree._ele = 'ztree-ul';
                $.ajax({
                    url: ztreeOption.url,
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        //初始化 ztree
                        $.fn.zTree.init($("#ztree-ul"), ztreeOption.setting, data);
                        //初始化模糊搜索方法
                        //ztree的id  和 搜索框的id
                        fuzzySearch($.ztree._ele, '#ztreeKey', null, true);
                        //展开关闭按钮
                        $('#collapse_expand').click($.ztree.expandNodes);
                        //刷新按钮
                        /* $('.tree-refresh').click(function () {
                             var treeObj = $.fn.zTree.getZTreeObj($.ztree._ele);
                             treeObj.refresh();
                         });*/
                        // var zTree = $.fn.zTree.getZTreeObj($.ztree._ele);
                        // zTree.expandAll(true);
                    }
                });
            },
            expandNodes: function () {
                var zTree = $.fn.zTree.getZTreeObj($.ztree._ele);
                if ($(this).hasClass("expand")) {
                    zTree.expandAll(false);
                    $(this).toggleClass("expand");
                    $(this).html('展开');
                } else {
                    zTree.expandAll(true);
                    $(this).toggleClass("expand");
                    $(this).html('折叠');
                }
            }

        },
        operator: {
            delete: function (_url, _ids) { //添加弹出框
                $.modal.confirm("确定要删除吗?", function () {
                    $.operator.del(_url, _ids);
                });

            },
            del: function (_url, _ids) {
                $.modal.ajaxBlockUI("正在处理中...");
                $.ajax({
                    url: _url,
                    type: "post",
                    dataType: "json",
                    data: {id: _ids},
                    // beforeSend: function () {
                    //     layer.load("1", {shade: 0.3});
                    // },
                    success: function (data) {
                        console.info(data);
                        // layer.closeAll('loading');
                        if (data.status === "0") {
                            $.modal.msg_success(data.message);
                            $.table.refresh();
                            $.treeTable.refresh();//刷新父页面
                        } else {
                            $.modal.msg_fail(data.message);
                        }
                    },
                    error: function () {
                        // layer.closeAll('loading');
                        $.modal.msg_fail("系统错误!");
                    }
                });
            },
            batchDelete: function (_url) {
                //获取选中行
                var checkData = $("#bootstrap-table").bootstrapTable('getSelections');
                if (checkData.length === 0) {
                    return $.modal.msg_warning("请选择数据!");
                }
                var ids = [];
                checkData.forEach(function (row) {
                    ids.push(row.id);
                });
                $.modal.prompt("敏感操作，请验证口令[admin]", function (value, indexp) {
                    if (value === "admin") {
                        layer.closeAll();
                        $.modal.confirm("确定删除吗?", function () {
                            $.operator.del(_url, ids.join(","));
                        });
                    } else {
                        $.modal.msg_fail("口令不正确!");
                    }
                });
            },
            save: function (_url) {
                var parentIndex = parent.layer.getFrameIndex(window.name);
                $.modal.ajaxBlockUI("正在处理中...");
                $.ajax({
                    url: _url,
                    type: "post",
                    dataType: "json",
                    data: $("#formId").serialize(),
                    // beforeSend: function () {
                    //     layer.load("1", {shade: 0.3});
                    // },
                    success: function (data) {
                        // layer.closeAll("loading");
                        if (data.status === "0") {
                            parent.layer.close(parentIndex);//关闭弹出层
                            parent.$.modal.msg_success(data.message);//父页面提示信息
                            parent.$.table.refresh();//刷新父页面
                            parent.$.treeTable.refresh();//刷新父页面
                        } else {
                            $.modal.msg_fail(data.message);
                        }
                    },
                    error: function (data) {
                        // layer.closeAll('loading');
                        $.modal.msg_fail("系统错误!");
                    }
                });
            }
        },
        modal: { //弹窗框  icon 0  叹号 1:  对号  2 叉号 3 问好  4 锁头 5 哭脸 6 笑脸
            //anim: 0平滑放大。默认; 1从上掉落; 2从最底部往上滑入; 3从左滑入; 4从左翻滚; 5渐显; 6抖动
            add: function (_title, _url, _width, _height) { //添加弹出框
                layer.open({
                    type: 2,
                    title: _title,
                    maxmin: true,
                    area: [_width, _height],
                    shadeClose: true,//点击遮罩关闭
                    content: _url
                });
            },
            open: function (_title, _url, _width, _height) { //添加弹出框
                layer.open({
                    type: 2,
                    title: _title,
                    maxmin: true,
                    area: [_width, _height],
                    shadeClose: true,//点击遮罩关闭
                    content: _url
                });
            },
            edit: function (_title, _url, _width, _height, _id) { //添加弹出框
                layer.open({
                    type: 2,
                    title: _title,
                    maxmin: true,
                    area: [_width, _height],
                    shadeClose: true,//点击遮罩关闭
                    content: _url + "?id=" + _id
                });
            },
            addChild: function (_title, _url, _width, _height, _parentId) { //树结构添加子菜单弹出框
                layer.open({
                    type: 2,
                    title: _title,
                    maxmin: true,
                    area: [_width, _height],
                    shadeClose: true,//点击遮罩关闭
                    content: _url + "?parentId=" + _parentId
                });
            },
            confirm: function (_msg, callBack) {
                layer.confirm(_msg,
                    {icon: 3, title: '系统提示', anim: 6}, function (index) {
                        layer.close(index);
                        callBack(true);
                    });
            },
            prompt: function (_title, callBack) {
                layer.prompt({
                    formType: 1, //输入框类型，支持0（文本）默认1（密码）2（多行文本）
                    value: '',
                    title: _title,
                    anim: 6
                    // area: ['800px', '350px'] //自定义文本域宽高
                }, function (value, index) {
                    callBack(value, true);
                });
            },
            msg_warning: function (_msg) {
                layer.msg(_msg, {icon: 0, time: 2000, shade: 0.5, shadeClose: true, anim: 6});
            },
            msg_success: function (_msg) {
                layer.msg(_msg, {icon: 6, time: 2000, anim: 0});
                // layer.open({
                //     skin: 'layui-layer-molv', type: 1, offset: 'rb', //具体配置参考：offset参数项
                //     content: '<div style="padding:40px 90px;">' + _msg + '</div>',
                //     shade: 0, time: 2000, anim: 2
                // });
            },
            msg_fail: function (_msg) {
                // layer.msg(_msg, {icon: 2, time: 2000, shade: 0.5, shadeClose: true, anim: 6});
                layer.alert(_msg, {title: "系统提示", icon: 2, anim: 6});
            },
            msg_error: function (_msg) {
                layer.msg(_msg, {icon: 2, time: 2000, shade: 0.5, shadeClose: true, anim: 6});
            },
            blockUI: function (_msg) { //统一遮罩样式
                if (_msg !== undefined && _msg !== '') {
                    $('#loadingText').html(_msg);
                }
                $.blockUI({message: $('#loading')});
            },
            ajaxBlockUI: function (_msg) {//声明ajax遮罩
                $(document).ajaxStart($.modal.blockUI(_msg)).ajaxStop($.unblockUI);
            },
            pageBlockUI: function (_msg) {//页面载入遮罩
                NProgress.start();
                $.modal.blockUI();
                window.onload = function () {
                    $.unblockUI();
                    NProgress.done();
                 /*   setTimeout(function () {
                    }, 50)*/

                };
            },
            blockUICallBack: function (_msg, callBack) {//支持回调函数
                $.modal.blockUI(_msg);
                setTimeout(function () {
                    $.unblockUI({
                        onUnblock: callBack(true)
                    })
                }, 800);
            },
            NProgres: function () {//加载进度条
                NProgress.start();
                window.onload = function () {
                    NProgress.done();
                };
            }
        }
    });
})(jQuery);


//序号格式化
function indexFormatter(value, row, index) {
    //获取每页显示的数量
    var pageSize = $('#bootstrap-table').bootstrapTable('getOptions').pageSize;
    //获取当前是第几页
    var pageNumber = $('#bootstrap-table').bootstrapTable('getOptions').pageNumber;
    //返回序号，注意index是从0开始的，所以要加上1
    return pageSize * (pageNumber - 1) + index + 1;
}

$(function () {

    //表单的显示隐藏
    $('.queryFormToggle').click(function () {
        $('.queryForm').slideToggle(300);
    });


    //用户列表树的显示隐藏
    $('.toggleTree').click(function () {
        var $rightList = $('.rightList');
        var $leftTree = $('.leftTree');
        if ($rightList.hasClass('col-sm-12')) {
            $rightList.attr("class", 'col-sm-10 rightList');
            $leftTree.show(0, function () {
                $('.toggleI').removeClass("glyphicon-chevron-right").addClass("glyphicon-chevron-left");
            });
        } else if ($rightList.hasClass('col-sm-10')) {
            $leftTree.hide(0, function () {
                $rightList.attr("class", 'col-sm-12 rightList');
                $('.toggleI').removeClass("glyphicon-chevron-left").addClass("glyphicon-chevron-right");
            });
        }
    });
    //icheck样式
    $('.i-checks').iCheck({
        checkboxClass: 'icheckbox_flat-green',
        radioClass: 'iradio_flat-green',
        increaseArea: '20%' // optional
    });
});
