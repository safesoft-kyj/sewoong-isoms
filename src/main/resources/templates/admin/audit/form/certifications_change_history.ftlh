<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">

<@pagination.bind auditList/>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center" style="width:80px">Rev No.</th>
            <th class="text-center">Revision Date</th>
            <th class="text-center">Revision Type</th>
            <th class="text-center">ID</th>
            <th >Title</th>
            <th class="text-center">Attach Files</th>
            <th class="text-center">postStatus</th>
            <th class="text-center">Deleted</th>
        </tr>
        </thead>
        <tbody>
        <#if auditList.content?has_content>
            <#list auditList.content as audit>
                <tr>
                    <#assign new = ''>
                    <#if audit.revisionType == 'ADD'>
                        <#assign revType = 'info' />
                        <#assign new = 'text-info text-bold bg-trans-dark'>
                    <#elseif audit.revisionType == 'MOD'>
                        <#assign revType = 'warning' />
                    <#elseif audit.revisionType == 'DEL'>
                        <#assign revType = 'danger' />
                    </#if>

                    <td class="text-center ${new}" style="vertical-align: middle;">${audit.revisionEntity.rev!''}</td>
                    <td class="text-center ${new}" style="vertical-align: middle;">${audit.revisionEntity.revisionDate?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td class="text-center ${new}" style="vertical-align: middle;"><span class="label label-${revType}">${audit.revisionType}</span></td>
                    <td class="text-center ${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">${audit.entity.id}</td>
                    <td class="${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">${audit.entity.title!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('attachFiles')?then('color:red;', '')}">
                        <#if audit.entity.attachFiles?has_content>
                            <#list audit.entity.attachFiles as file>
                                <i class="fa fa-file"></i>&nbsp;${file.originalFileName!'N/A'}<br />
                            </#list>
                        <#else>
                            No File
                        </#if>
                    </td>
                    <td class="text-center ${new}"style="${audit.contains('postStatus')?then('color:red;', '')}">${audit.entity.postStatus!'N/A'}</td>
                    <td class="text-center ${new}"style="${audit.contains('deleted')?then('color:red;', '')}">${audit.entity.deleted?then('Y', 'N')}</td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="16" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>
</div>
<@pagination.default />