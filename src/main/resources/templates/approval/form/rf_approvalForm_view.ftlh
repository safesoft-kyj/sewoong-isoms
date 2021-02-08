<#import "/spring.ftl" as spring />
<h5>RD Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light">Superseded version</td>
        <td>
            ${approvalView.rdApprovalForm.supersededVersion.document.docId} / ${approvalView.rdApprovalForm.supersededVersion.version} / ${approvalView.rdApprovalForm.supersededVersion.document.title} / ${approvalView.rdApprovalForm.supersededVersion.effectiveDate?string('dd-MMM-yyyy')}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">New version</td>
        <td>
            ${approvalView.rdApprovalForm.supersededVersion.document.docId} / ${approvalView.rdApprovalForm.version} / ${approvalView.rdApprovalForm.supersededVersion.document.title} / ${approvalView.rdApprovalForm.effectiveDate?string('dd-MMM-yyyy')}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Description of the revised contents</td>
        <td>
            ${approvalView.rdApprovalForm.description!''?replace('\n', '<br/>')?no_esc}
        </td>
    </tr>

    </tbody>
</table>