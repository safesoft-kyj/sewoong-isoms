<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<@security.authentication property="principal" var="user"/>
<#import "/spring.ftl" as spring />
<#setting locale="en_US">
<html>
<head>
    <title>전자결재 > SOP Deviation Report</title>
<#--    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">-->
<#--    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>-->
    <#--        <link href="/static/plugins/chosen/chosen.min.css" rel="stylesheet">-->
    <#--        <script src="/static/plugins/chosen/chosen.jquery.min.js"></script>-->
<#--    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">-->
<#--    <script src="/static/plugins/select2/js/select2.min.js"></script>-->

<#--    <script src="/static/plugins/nestable-list/jquery.nestable.js"></script>-->
<#--    <link href="/static/plugins/nestable-list/nestable-list.min.css" rel="stylesheet">-->

<#--    <script src="/static/plugins/jstree/jstree.min.js"></script>-->
<#--    <link href="/static/plugins/jstree/themes/default/style.min.css" rel="stylesheet">-->


    <!--Bootbox Modals [ OPTIONAL ]-->
<#--    <script src="/static/plugins/bootbox/bootbox.min.js"></script>-->
<#--    <script>-->
<#--        $(document).ready(function() {-->
<#--            $("#reportForm").find('input, select, textarea').attr("disabled", "disabled");-->
<#--        });-->
<#--//     </script>-->
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
                                <#include "../../approval/form/" + approvalView.type.viewName + "_view.ftlh"/>
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
                                                <td class="text-center text-info">${line.displayName?replace("(", "<br/>(")?no_esc}</td>
                                            </#list>
                                        </tr>
                                        <tr style="height:50px;">
                                            <#assign idx = -1/>
                                            <#list approvalView.approvalLines as line>
                                                <td class="text-center">
                                                    <#if line.lineType == 'requester'>
                                                        <#if line.base64signature?has_content>
                                                            <img src="${line.base64signature}" style="width:50px"/>
                                                        </#if>
                                                    <#else>
                                                        <#if line.status?has_content>
                                                            <#if line.base64signature?has_content>
                                                                <img src="${line.base64signature}" style="width:50px"/>
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
                                </form>
                            </div>
                        </div>
                    </div>
                    <div class="panel-footer text-right">
                        <a class="btn btn-primary" href="/admin/approval${status?has_content?then('/'+status.name(), '')}"><i class="fa fa-chevron-left"></i> 뒤로</a>
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