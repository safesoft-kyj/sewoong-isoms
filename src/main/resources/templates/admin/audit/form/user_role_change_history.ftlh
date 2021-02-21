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
            <th class="text-center">Requester Id</th>
            <th class="text-center">Manager Id</th>
            <th class="text-center">Prev Role</th>
            <th class="text-center">Next Role</th>
            <th class="text-center">Reason</th>
            <th class="text-center">Request Date</th>
            <th class="text-center">Assign Date</th>
            <th class="text-center">status</th>
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

                    <td class="text-center ${new}">${audit.revisionEntity.rev!''}</td>
                    <td class="text-center ${new}">${audit.revisionEntity.revisionDate?string('yyyy-MM-dd HH:mm:ss')}</td>
                    <td class="text-center ${new}"><span class="label label-${revType}">${audit.revisionType}</span></td>
                    <td class="text-center ${new}">${audit.entity.id}</td>
                    <td class="text-center ${new}" style="${audit.contains('requester')?then('color:red;', '')}">${audit.entity.requester.username!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('manager')?then('color:red;', '')}">${audit.entity.manager?has_content?then(audit.entity.manager.username, 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('prevJobDescription')?then('color:red;', '')}">${audit.entity.prevJobDescription!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('nextJobDescription')?then('color:red;', '')}">${audit.entity.nextJobDescription!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('reason')?then('color:red;', '')}">${audit.entity.reason!'N/A'}</td>
                    <td class="text-center ${new}" style="${audit.contains('requestDate')?then('color:red;', '')}">${audit.entity.requestDate?has_content?then(audit.entity.requestDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}" style="${audit.contains('assignDate')?then('color:red;', '')}">${audit.entity.assignDate?has_content?then(audit.entity.assignDate?string('yyyy-MM-dd'), 'N/A')}</td>
                    <td class="text-center ${new}"><span class="label ${new?has_content?then('label-info', audit.contains('roleStatus')?then('label-danger', 'bg-gray-dark'))}">${audit.entity.roleStatus}</span></td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="12" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>
</div>
<@pagination.default />