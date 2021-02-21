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
            <th class="text-center">Approval ID</th>
            <th class="text-center">Confirmation Date of SOP<br />Training Deviation</th>
            <th class="text-center">SOP Information</th>
            <th class="text-center">Deviation Details</th>
            <th class="text-center">Corrective<br />Completion Date</th>
            <th class="text-center">Corrective Action</th>
            <th class="text-center">Preventive<br />Completion Date</th>
            <th class="text-center">Preventive Action</th>
            <th class="text-center">Training Period ID</th>
            <th class="text-center">Training Deviated<br />SOP ID</th>
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
                <td class="text-center ${new}" style="${audit.contains('approval')?then('color:red;', '')}">${audit.entity.approval.id}</td>
                <td class="text-center ${new}" style="${audit.contains('confirmationDate')?then('color:red;', '')}">${audit.entity.confirmationDate?has_content?then(audit.entity.confirmationDate?string('yyyy-MM-dd'), 'N/A')}</td>
                <td class="text-center ${new}" style="${audit.contains('deviatedSOPDocument')?then('color:red;', '')}">
                    <#if audit.entity.deviatedSOPDocument?has_content>
                        <span class="label ${new?has_content?then('label-info', audit.contains('approvalLines')?then('label-danger', 'bg-gray-dark'))} add-popover"
                              data-toggle="popover" data-container="body" data-placement="top"
                              data-trigger="hover" data-html="true"
                              data-content='<div><span class="text-semibold">SOP Document Id & Title:</span> ${audit.entity.deviatedSOPDocument.document.docId} & ${audit.entity.deviatedSOPDocument.document.title}</div><div><span class="text-semibold">Version:</span> ${audit.entity.deviatedSOPDocument.version}</div><div><span class="text-semibold">Effective Date:</span>${audit.entity.deviatedSOPDocument.effectiveDate?string('dd-MMM-yyyy')}</div>'
                              style="cursor: pointer;">
                            <i class="fa fa-file-text-o"></i>
                        </span>
                    <#else>
                        N/A
                    </#if>
                </td>
                <td class="text-center ${new}" style="${audit.contains('deviationDetails')?then('color:red;', '')}">${audit.entity.deviationDetails!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('correctiveCompletionDate')?then('color:red;', '')}">${audit.entity.correctiveCompletionDate?has_content?then(audit.entity.correctiveCompletionDate?string('yyyy-MM-dd'), 'N/A')}</td>
                <td class="text-center ${new}" style="${audit.contains('correctiveAction')?then('color:red;', '')}">${audit.entity.correctiveAction!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('preventiveCompletionDate')?then('color:red;', '')}">${audit.entity.preventiveCompletionDate?has_content?then(audit.entity.preventiveCompletionDate?string('yyyy-MM-dd'), 'N/A')}</td>
                <td class="text-center ${new}" style="${audit.contains('preventiveAction')?then('color:red;', '')}">${audit.entity.preventiveAction!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('trainingLogId')?then('color:red;', '')}">${audit.entity.trainingLogId!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('trDeviatedSOPDocumentId')?then('color:red;', '')}">${audit.entity.trDeviatedSOPDocumentId!'N/A'}</td>
            </#list>
        <#else>
            <tr>
                <td colspan="14" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>

    <#include "../../authority/approval-modal.ftlh"/>
</div>
<@pagination.default />