<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Admin > Change Control</title>

</head>
<body>

<div class="panel">
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th>Test</th>
<#--                    <th class="text-center">Document Type</th>-->
<#--                    <th>Document Id</th>-->
<#--                    <th>Title</th>-->
<#--                    <th class="text-center">Version</th>-->
<#--                    <th class="text-center">Access Type</th>-->
<#--                    <th class="text-center">Access Date</th>-->
<#--                    <th class="text-center">User</th>-->
                </tr>
                </thead>
                <tbody>
                <#if changeLog?has_content>
                    <#list changeLog as access>
                        <#include "form/" + viewName + ".ftlh"/>
                        <tr>
                            <td class="text-center">${(accessLog.totalElements - (accessLog.size * accessLog.number)) - access?index}
                                <td>Test</td>
<#--                            <td class="text-center"><span class="label label-${(access.documentVersion.document.type == 'SOP')?then('purple', 'mint')} label-table">${access.documentVersion.document.type.name()}</span></td>-->
<#--                            <td>${access.documentVersion.document.docId}</td>-->
<#--                            <td>${access.documentVersion.document.title}</td>-->
<#--                            <td class="text-center">${access.documentVersion.version}</td>-->
<#--                            <td class="text-center"><span class="label label-table label-${access.accessType.className}"> ${access.accessType.name()}</span></td>-->
<#--                            <td class="text-center"><span class="text-muted">${access.createdDate?string('yyyy-MM-dd HH:mm:ss')}</span></td>-->
<#--                            <td class="text-center">${access.createdBy?default('System')}</td>-->
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="8" class="text-center">Change History가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>

        </div>

        <#--        <nav style="float:left;">-->
        <#---->
        <#--        </nav>-->
        <#--        <@pagination.counter />-->
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
</body>
</html>