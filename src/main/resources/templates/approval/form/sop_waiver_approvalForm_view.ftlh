<#setting locale="en_US">
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-4">Project No.</td>
        <td>
            ${approvalView.sopWaiverApprovalForm.projectNo!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Protocol Title/No.</td>
        <td>
            ${approvalView.sopWaiverApprovalForm.protocolNo!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Waiver Start Date<br/><small class="text-gray-light text-xs">(DD-MMM-YYYY)</small></td>
        <td>
            ${approvalView.sopWaiverApprovalForm.waiverStartDate?string('dd-MMM-yyyy')?upper_case}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Waiver End Date<br/><small class="text-gray-light text-xs">(DD-MMM-YYYY)</small></td>
        <td>
            ${approvalView.sopWaiverApprovalForm.waiverEndDate?string('dd-MMM-yyyy')?upper_case}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Waived SOP Doc. ID & Title/Version/Effective Date:<br/><small class="text-gray-light text-xs">(DD-MMM-YYYY)</small></td>
        <td>
            <#--            <@spring.formSingleSelect "approvalView.sopWaiverApprovalForm.deviatedSOPDocument.id" sopMap "class='form-control'"/>-->
            <#--            <@spring.showErrors "<br/>" "color:red;"/>-->
            <div><span class="text-semibold">SOP Document Id & Title:</span> ${approvalView.sopWaiverApprovalForm.deviatedSOPDocument.document.docId} & ${approvalView.sopWaiverApprovalForm.deviatedSOPDocument.document.title}</div>
            <div><span class="text-semibold">Version:</span> ${approvalView.sopWaiverApprovalForm.deviatedSOPDocument.version}</div>
            <div><span class="text-semibold">Effective Date:</span> ${approvalView.sopWaiverApprovalForm.deviatedSOPDocument.effectiveDate?string('dd-MMM-yyyy')?upper_case}</div>
        </td>
    </tr>
    </tbody>
</table>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-4">Waiver Details</td>
        <td colspan="3">
            ${approvalView.sopWaiverApprovalForm.deviationDetails?default('')?replace('\n', '<br/>')?no_esc}
        </td>
    </tr>
    </tbody>
</table>
