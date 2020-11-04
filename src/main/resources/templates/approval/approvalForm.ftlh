<#import "/spring.ftl" as spring />
<#setting locale="en_US">
<html>
<head>
    <title>전자결재 > SOP Deviation Report</title>
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <#--        <link href="/static/plugins/chosen/chosen.min.css" rel="stylesheet">-->
    <#--        <script src="/static/plugins/chosen/chosen.jquery.min.js"></script>-->
    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">
    <script src="/static/plugins/select2/js/select2.min.js"></script>

    <script src="/static/plugins/nestable-list/jquery.nestable.js"></script>
    <link href="/static/plugins/nestable-list/nestable-list.min.css" rel="stylesheet">

    <script src="/static/plugins/jstree/jstree.min.js"></script>
    <link href="/static/plugins/jstree/themes/default/style.min.css" rel="stylesheet">

    <script src="/static/plugins/chosen/chosen.jquery.min.js"></script>
    <link href="/static/plugins/chosen/chosen.min.css" rel="stylesheet">
    <script>
        $(document).ready(function() {
            $("#removeBtn").click(function() {
               if(confirm('삭제 하시겠습니까?')) {
                   $("#removeForm").submit();
               }
            });
            var isTemp = false;
            var isSubmit = false;
            $("button[name='_temp']").click(function() {
                isTemp = true;
                isSubmit = false;
            });
            $("button[name='_finish']").click(function() {
                isTemp = false;
                isSubmit = true;
            });
            $("#appForm").submit(function(e) {
                if(isSubmit) {
                    if(!confirm('결재 상신 하시겠습니까?')) {
                        isSubmit = false;
                        e.preventDefault();
                        return false;
                    }
                }
                if(isTemp) {
                    if(!confirm('임시저장 하시겠습니까?')) {
                        isTemp = false;
                        e.preventDefault();
                        return false;
                    }
                }

            });
        });
    </script>
</head>
<body>
<form name="f" id="removeForm" method="post">
    <input type="hidden" name="_method" value="delete"/>
</form>

<div class="row">
    <div class="col-lg-12">
        <form class="form-horizontal form-padding" method="post" id="appForm">
            <div class="row">
                <div class="col-xs-12">


                    <div class="panel">
                        <div class="panel-heading">
                            <h3 class="panel-title">${approval.type.label}</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-9 bord-rgt">
                                    <#include "form/" + approval.type.viewName + ".ftlh"/>
                                </div>
                                <div class="col-xs-3">
                                    <div class="panel panel-bordered-default panel-default">
                                        <div class="panel-heading">
                                            <h5 class="panel-title"><div class="pull-right"><a class="btn btn-default btn-sm" href="#org-modal" data-toggle="modal"> <i class="fa fa-sitemap"></i> 조직도</a></div>결재</h5>
                                        </div>
                                        <div class="panel-body">
                                            <div id="nestable-handler" class="dd">
                                                <ol class="dd-list">
                                                    <#if approval.approvalLines?has_content>
                                                        <#assign idx = 0/>
                                                        <#list approval.approvalLines as line>
                                                            <#if line.lineType?has_content && line.lineType != 'requester'>
                                                                <li class="dd-item" data-username="${line.username}" data-name="${line.displayName}">
                                                                    <div style="padding:5px !important;" class="dd-content bord-all pad-all"><span class="text-uppercase label label-${(line.lineType == 'reviewer')?then('info', 'success')}">${(line.lineType == 'reviewer')?then('검토', '승인')}</span> ${line.displayName}

                                                                    </div>
                                                                </li>
                                                                <#assign idx = idx + 1/>
                                                            </#if>
                                                        </#list>
                                                    </#if>
                                                </ol>
                                            </div>
                                            <@spring.bind "approval.approvalLines"/>
                                            <@spring.showErrors "<br/>" "color:red;"/>
                                            <script>
                                                $('#nestable-handler').nestable({group: 1});
                                            </script>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <#--                <div class="col-xs-12 text-right">-->
<#--                            <a class="btn btn-link" href="/approval/box/${lineType.name()}${status?has_content?then('/'+status.name(), '')}"><i class="fa fa-times"></i> 취소</a>-->
                            <a class="btn btn-link" href="javascript:history.back();"><i class="fa fa-times"></i> 취소</a>
                            <#if approval.id?has_content == false || (approval.id?has_content && (approval.status == 'temp' || approval.status == 'request'))>
                                <#if approval.id?has_content == false || approval.status == 'temp'>
                                <button type="submit" class="btn btn-info" name="_temp"> 임시저장</button>
                                </#if>
                                <#if approval.id?has_content && (approval.status == 'temp' || approval.status == 'request')>
                                    <button class="btn btn-danger" type="button" id="removeBtn"><i class="fa fa-trash-o"></i> 삭제</button>
                                </#if>
                                <button class="btn btn-primary" type="submit" name="_finish"><i class="fa fa-check"></i> 결재요청</button>
                            </#if>

                            <#--                </div>-->
                        </div>

                        <!--===================================================-->
                        <!--End Horizontal Form-->

                    </div>
                </div>
            </div>
        </form>
    </div>
    <#--    <div class="col-lg-4">-->
    <#--        -->
    <#--    </div>-->
</div>

<div class="modal fade" id="org-modal" role="dialog" tabindex="-1" aria-labelledby="matrix-modal" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title">결재선</h4>
            </div>

            <!--Modal body-->
            <div class="modal-body">
                <script>
                    $(document).ready(function() {
                        $.ajax({
                            url: "/common/organization/chart",
                            method: "GET",
                            data: { r : Math.random() },
                            dataType: "json"
                        }).done(function(response) {
                            // $('#org-jstree-json').jstree('destroy');
                            $('#org-jstree-json').jstree({
                                'core': {
                                    // 'check_callback' : true,
                                    // 'dblclick_toggle' : false,
                                    'data': response
                                },
                                'plugins': ['types'],
                                'types' : {
                                    'company' : {
                                        'icon' : 'pli-building icon-lg'
                                    },
                                    'dept' : {
                                        'icon' : 'pli-folders icon-lg text-warning'
                                    },
                                    'team' : {
                                        'icon' : 'pli-folder text-warning'
                                    },
                                    'user_mng_male' : {
                                        'icon' : 'pli-business-man icon-lg'
                                    },
                                    'user_mng_female' : {
                                        'icon' : 'pli-business-woman icon-lg'
                                    },
                                    'user_male' : {
                                        'icon' : 'pli-male icon-lg'
                                    },
                                    'user_female' : {
                                        'icon' : 'pli-female icon-lg'
                                    }
                                }
                            }).bind('select_node.jstree', function (event, data) {
                                var jsTreeData = {};
                                for (var i = 0; i < data.selected.length; i++) {
                                    var dataJson = data.instance.get_node(data.selected[i]).data;
                                    var code = dataJson.code;    //data 에서 a 가져오기
                                }
                            }).bind('dblclick.jstree', function(e) {
                                var instance = $.jstree.reference(this);
                                var node = instance.get_node(e.target);
                                var jsonData = node.data;

                                if(jsonData.type == 'user') {
                                    // alert('empNo:' + jsonData.empNo + ',korName:' + jsonData.empName);
                                    if(lineUsers.indexOf(jsonData.username) == -1) {
                                        var htmlText = createLi(jsonData.username, jsonData.empName);
                                        // console.log(htmlText);
                                        $("#modal-nestable-handler > ol.dd-list").append(htmlText);
                                    } else {
                                        alert('이미 결재선에 지정되어 있습니다.');
                                    }
                                }
                            });
                        });

                        $('#modal-nestable-handler').nestable({group: 2});

                        $(document).on('click', '#applyBtn', function() {
                            var $lines = $("#modal-nestable-handler > ol.dd-list > li.dd-item");
                            var lineSize = $lines.length;
                            if(lineSize < 1) {
                                alert('결재선을 지정해 주세요.');
                                return false;
                            }
                            var htmlText = '';
                            $lines.each(function(i, $el) {
                                var $li = $($el);
                                var idx = (i + 1);
                                // $li.find('input[type=hidden]').remove();
                                var lineType = $li.find('select option:selected').val();
                                $li.append("<input type='hidden' name='approvalLines[" + idx + "].id' value=''/>");
                                $li.append("<input type='hidden' name='approvalLines[" + idx + "].username' value='"+$li.data("username")+"'/>");
                                $li.append("<input type='hidden' name='approvalLines[" + idx + "].displayName' value='"+$li.data("name")+"'/>");
                                $li.append("<input type='hidden' name='approvalLines[" + idx + "].lineType' value='"+lineType+"'/>");

                                htmlText += "<li class=\"dd-item\">\n" +
                                    "<div style=\"padding:5px !important;\" class=\"dd-content bord-all pad-all\"><span class='text-uppercase label label-"+(lineType == 'reviewer' ? 'info' : 'success')+"'>"+(lineType == 'reviewer' ? '검토' : '승인')+"</span> "+$li.data("name")+"</div>\n" +
                                    "<input type='hidden' name='approvalLines[" + idx + "].id' value=''/>" +
                                    "<input type='hidden' name='approvalLines[" + idx + "].username' value='"+$li.data("username")+"'/>" +
                                    "<input type='hidden' name='approvalLines[" + idx + "].displayName' value='"+$li.data("name")+"'/>" +
                                    "<input type='hidden' name='approvalLines[" + idx + "].lineType' value='"+lineType+"'/>" +
                                    "</li>";

                                if(idx < lineSize && lineType == 'approver') {
                                    alert('결재 순서를 변경해 주세요.');
                                    return false;
                                }

                                if(idx == lineSize && lineType != 'approver') {
                                    alert('결재 순서를 변경해 주세요.');
                                    return false;
                                } else if(idx == lineSize && lineType == 'approver') {
                                    $("#nestable-handler").html(htmlText);
                                    $("#org-modal").modal('hide');
                                }
                            });
                        });

                        $("#modal-nestable-handler").on('click', 'i.fa-times', function() {
                            $(this).parents("li").remove();
                            lineUsers.splice(lineUsers.indexOf($(this).data("id")), 1);
                        });
                    });

                    var lineUsers = new Array();
                    function createLi(username, korName) {
                        lineUsers.push(username);
                        var htmlText = "<li class=\"dd-item\" data-username=\""+username+"\" data-name=\""+korName+"\">\n" +
                            "<div class=\"dd-handle dd-handle-btn\"></div>\n" +
                            "<div class=\"dd-content bord-all\"><div class='pull-right pad-rgt'><i class='fa fa-times' data-id=\""+username+"\"></i></div><select style='border:1px solid #eee;padding:1px;'>" +
                            "<option value='reviewer'>검토</option>" +
                            "<option value='approver'>승인</option>" +
                            "</select> "+korName+"</div>\n" +
                            "</li>";

                        return htmlText
                    }
                </script>
                <div class="row">
                    <div class="col-xs-6 bord-rgt">
                        <div class="bord-all pad-all">
                            <div id="org-jstree-json"></div>
                        </div>
                    </div>
                    <div class="col-xs-6 ">
                        <div class="bord-all pad-all">
                            <div id="modal-nestable-handler" class="dd">
                                <ol class="dd-list dd-list-handle-btn">

                                </ol>
                            </div>
                            <script>

                            </script>
                        </div>
                    </div>
                </div>
            </div>

            <!--Modal footer-->
            <div class="modal-footer">
                <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
                <button class="btn btn-success" type="button" id="applyBtn"><i class="fa fa-check"></i> 적용</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>