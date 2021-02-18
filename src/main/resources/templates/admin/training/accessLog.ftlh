<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Admin > ISO Access Log</title>

</head>
<body>


<div class="panel">
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <@pagination.bind trainingAccessLog/>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th class="text-center">Training Type</th>
                    <th class="text-center">Access Type</th>
                    <th class="text-center">Access Date</th>
                    <th class="text-center">User</th>
                    <#--                    <th class="text-center" style="width:120px">#actions</th>-->
                </tr>
                </thead>
                <tbody>
                <#if trainingAccessLog.content?has_content>
                    <#list trainingAccessLog.content as access>
                        <tr>

                            <td class="text-center">${(trainingAccessLog.totalElements - (trainingAccessLog.size * trainingAccessLog.number)) - access?index}
                            <td class="text-center"><span
                                        class="label label-${access.trainingLogType.className} label-table add-popover"
                                        data-toggle="popover" data-container="body" data-placement="top"
                                        data-trigger="hover" data-html="true"
                                        data-content="${access.trainingLogType.detail}"
                                        style="max-width: 160px;">
                                    ${access.trainingLogType.label}
                                </span>
                            </td>
                            <td class="text-center"><span
                                        class="label label-table label-${access.accessType.className}"> ${access.accessType.name()}</span>
                            </td>
                            <td class="text-center"><span
                                        class="text-muted">${access.createdDate?string('yyyy-MM-dd HH:mm:ss')}</span>
                            </td>
                            <td class="text-center">${access.user.name?default('System')}</td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="8" class="text-center">Training Access Log가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>

        </div>
        <@pagination.default/>

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