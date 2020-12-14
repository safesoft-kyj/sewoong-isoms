<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
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


    <!--Bootbox Modals [ OPTIONAL ]-->
    <script src="/static/plugins/bootbox/bootbox.min.js"></script>
    <script>
        $(document).ready(function() {
            $("#reportForm").find('input, select, textarea').attr("disabled", "disabled");
        });
    </script>
</head>
<body>

<div class="row">
    <div class="col-lg-12">
<#--        <form class="" method="post" id="approvalForm">-->
            <div class="row">
                <div class="col-xs-12">

                    <div  class="panel form-horizontal form-padding">
                        <div class="panel-heading">
                            <h3 class="panel-title">${approvalView.type.label}</h3>
                        </div>
                        <div class="panel-body">
                            <div class="row">
                                <div class="col-xs-8 bord-rgt" id="reportForm">
                                    <#include "form/" + approvalView.type.viewName + "_view.ftlh"/>
                                </div>
                                <div class="col-xs-4">
                                    <form name="f" id="approvedForm" method="post">
                                    <table class="table table-bordered">
                                        <thead>
                                        <tr class="bg-gray-light">
                                            <th class="text-center" colspan="${approvalView.approvalLines?size}">결재</th>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <tr class="bg-gray-light">
                                            <#list approvalView.approvalLines as line>
                                            <td class="text-center text-xs text-semibold">${line.lineType.label}</td>
                                            </#list>
                                        </tr>
                                        <tr>
                                            <#list approvalView.approvalLines as line>
                                                <td class="text-center text-info">${line.displayName}</td>
                                            </#list>
                                        </tr>
                                        <tr style="height:50px;">
                                            <#assign idx = -1/>
                                            <#list approvalView.approvalLines as line>
                                                <td class="text-center" style="vertical-align:middle;">
                                                    <#if line.lineType == 'requester'>
                                                        <#if line.base64signature?has_content>
                                                        <img src="${line.base64signature}" style="width:50px"/>
                                                        </#if>
                                                    <#else>
                                                        <#if line.status?has_content>
                                                            <#if line.status == 'request' && user.username == line.username && approvalView.status != 'temp'>
                                                                <#assign idx = line?index/>
                                                                    <input type="hidden" name="_method" value="put"/>
                                                                    <input type="hidden" name="id" value="${line.id}"/>
                                                                    <input type="hidden" name="status" id="status" />
                                                                    <input type="hidden" name="password" id="password" />
                                                                    <textarea name="comments" id="comments" style="display: none"></textarea>
                                                                <#if approvalView.deleted == false>
                                                                    <button class="btn btn-xs btn-danger" type="button" id="rejectBtn" data-status="rejected"><i class="fa fa-rotate-left"></i> 반려</button>
                                                                    <button class="btn btn-xs btn-success" type="button" id="approvalBtn" data-status="approved"><i class="fa fa-check"></i> 승인</button>
                                                                </#if>
                                                            <#else>
                                                                <#if line.status == 'rejected'>
                                                                    <span class="label label-danger">${line.status.label}</span>
                                                                <#else>
                                                                    <#if line.base64signature?has_content>
                                                                        <img src="${line.base64signature}" style="width:50px"/>
                                                                    </#if>
                                                                </#if>
                                                                <#if line.comments?has_content>
                                                                    <br/>
                                                                <small class="add-popover" data-toggle="popover" data-container="body" data-placement="left" data-original-title="Comments" data-content="${line.comments}"><i class="fa fa-commenting-o"></i></small>
                                                                </#if>
                                                            </#if>
                                                        <#else>
                                                            <#if idx + 1 == line?index>
                                                                <input type="hidden" name="nextId" value="${line.id}"/>
                                                            </#if>
                                                            미결
                                                        </#if>
                                                    </#if>
                                                </td>
                                            </#list>
                                        </tr>
                                        <tr>
                                            <#list approvalView.approvalLines as line>
                                                <td class="text-center text-xs text-muted">
                                                    <#if line.lineType == 'requester'>
                                                        ${line.createdDate?string('dd/MMM/yyyy')}
                                                    <#elseif line.status?has_content && line.status != 'request'>
                                                        ${line.lastModifiedDate?has_content?then(line.lastModifiedDate?string('dd/MMM/yyyy'), '-')}
                                                    </#if>
                                                </td>
                                            </#list>
                                        </tr>
                                        </tbody>
                                    </table>
                                        <script>
                                            $(document).ready(function() {
                                                $("#rejectBtn,#approvalBtn").click(function () {
                                                    var statusText = $(this).data("status");
                                                    $("#status").val(statusText);

                                                    // bootbox.prompt({
                                                    //     size:"small",
                                                    //     title:"비밀번호를 입력해 주세요.",
                                                    //     inputType:"password",
                                                    //     callback:function(result) {
                                                    //         if(result) {
                                                    //             $("#password").val(result);
                                                    //             $("#approvedForm").submit();
                                                    //         } else {
                                                    //             $.niftyNoty({
                                                    //                 type: 'danger',
                                                    //                 icon : 'pli-cross icon-2x',
                                                    //                 message : '비밀번호가 입력 되지 않았습니다.',
                                                    //                 container : 'floating',
                                                    //                 timer : 5000
                                                    //             });
                                                    //         }
                                                    //     }
                                                    // });

                                                    bootbox.dialog({
                                                        title: (statusText == 'approved' ? "승인" : "반려"),
                                                        message: '<div class="row"> ' + '<div class="col-md-12"> ' +
                                                            '<div class="form-horizontal"> ' + '<div class="form-group"> ' +
                                                            '<label class="col-md-4 control-label" for="name">Password</label> ' +
                                                            '<div class="col-md-4"> ' +
                                                            '<input id="txt_password" type="password" placeholder="Your password" class="form-control input-md"> ' +
                                                            '</div> ' +
                                                            '</div> ' + '<div class="form-group"> ' +
                                                            '<label class="col-md-4 control-label" for="awesomeness">Comments</label> ' +
                                                            '<div class="col-md-8"> <div class="form-block"> ' +
                                                            '<textarea class="form-control" id="txt_comments"></textarea>' +
                                                            '</div>' +
                                                            '</div> </div>' + '</div> </div> </div>',
                                                        buttons: {
                                                            success: {
                                                                label: (statusText == 'approved' ? "승인" : "반려"),
                                                                className: "btn-purple",
                                                                callback: function (e) {
                                                                    var password = $('#txt_password').val();
                                                                    if(!password) {
                                                                        alert('비밀번호를 입력해 주세요.');
                                                                        $('#txt_password').focus();
                                                                        e.preventDefault();
                                                                        return false;
                                                                    }
                                                                    var comments = $("#txt_comments").val();

                                                                    $("#password").val(password);
                                                                    $("#comments").val(comments);
                                                                    $("#approvedForm").submit();
                                                                }
                                                            }
                                                        }
                                                    });
                                                });
                                            });
                                        </script>
                                    </form>
<#--                                    <div class="panel panel-bordered-default panel-default">-->
<#--                                        <div class="panel-heading">-->
<#--                                            <h5 class="panel-title">-->
<#--&lt;#&ndash;                                                <div class="pull-right"><a class="btn btn-default btn-sm" href="#org-modal" data-toggle="modal"> <i class="fa fa-sitemap"></i> 조직도</a></div>&ndash;&gt;-->
<#--                                                결재선</h5>-->
<#--                                        </div>-->
<#--                                        <div class="panel-body">-->
<#--                                            <div id="nestable-handler" class="dd">-->
<#--                                                <ol class="dd-list">-->
<#--                                                    <#if form.approvalLines?has_content>-->
<#--                                                        <#list form.approvalLines as line>-->
<#--                                                            <#if line?is_first == false>-->
<#--                                                                <li class="dd-item" data-username="${line.username}" data-name="${line.displayName}">-->
<#--                                                                    <div class="dd-content bord-all pad-all">-->
<#--&lt;#&ndash;                                                                        <div class="pull-right">&ndash;&gt;-->
<#--&lt;#&ndash;                                                                            <button type="button" class="btn btn-success"><i class="fa fa-check"></i> 승인</button>&ndash;&gt;-->
<#--&lt;#&ndash;                                                                            <button type="button" class="btn btn-danger"><i class="fa fa-times"></i> 반려</button>&ndash;&gt;-->
<#--&lt;#&ndash;                                                                        </div>&ndash;&gt;-->
<#--                                                                        <span class="label label-${(line.lineType == 'review')?then('info', 'success')}">${line.lineType.name()}</span> ${line.displayName}<#if line.status?has_content>${line.status.name()}</#if>-->
<#--                                                                    </div>-->
<#--                                                                </li>-->
<#--                                                            </#if>-->
<#--                                                        </#list>-->
<#--                                                    </#if>-->
<#--                                                </ol>-->
<#--                                            </div>-->
<#--                                            <@spring.bind "form.approvalLines"/>-->
<#--                                            <@spring.showErrors "<br/>" "color:red;"/>-->
<#--&lt;#&ndash;                                            <script>&ndash;&gt;-->
<#--&lt;#&ndash;                                                $('#nestable-handler').nestable({group: 1});&ndash;&gt;-->
<#--&lt;#&ndash;                                            </script>&ndash;&gt;-->
<#--                                        </div>-->
<#--                                    </div>-->
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer text-right">
                            <#--                <div class="col-xs-12 text-right">-->
                            <a class="btn btn-link" href="/approval/box/${lineType.name()}${reportStatus?has_content?then('/'+reportStatus.name(), '')}"><i class="fa fa-times"></i> 뒤로</a>
                            <#if (approvalView.deleted == true || (reportStatus?has_content && reportStatus == 'rejected')) && approvalView.username == user.username>
                            <a class="btn btn-info" href="/approval/box/requester/${approvalView.type.name()}/new?renewId=${approvalView.id}"><i class="fa fa-pencil"></i> 수정기안</a>
                            </#if>
                            <#--                            <#if approval.id?has_content == false || approval.status == 'temp'>-->
<#--                                <button type="submit" class="btn btn-info" name="_temp"> 임시저장</button>-->
<#--                                <button class="btn btn-primary" type="submit" name="_finish"><i class="fa fa-check"></i> 결재요청</button>-->
<#--                            </#if>-->

                            <#--                </div>-->
                        </div>

                        <!--===================================================-->
                        <!--End Horizontal Form-->

                    </div>
                </div>
            </div>
<#--        </form>-->
    </div>
    <#--    <div class="col-lg-4">-->
    <#--        -->
    <#--    </div>-->
</div>
</body>
</html>