<#setting locale="en_US">
<table class="table table-bordered">
    <tbody>
<#--    <tr>-->
<#--        <td class="bg-gray-light col-xs-4">Project No.</td>-->
<#--        <td>-->
<#--            ${approvalView.sopDeviationReport.projectNo!''}-->
<#--        </td>-->
<#--    </tr>-->
<#--    <tr>-->
<#--        <td class="bg-gray-light">Protocol Title/No.</td>-->
<#--        <td>-->
<#--            ${approvalView.sopDeviationReport.protocolNo!''}-->
<#--        </td>-->
<#--    </tr>-->
<#--    <tr>-->
<#--        <td class="bg-gray-light">Date of Occurrence</td>-->
<#--        <td>-->
<#--            <#if approvalView.sopDeviationReport.dateOfOccurrence?has_content>-->
<#--                ${approvalView.sopDeviationReport.dateOfOccurrence?string('dd-MMM-yyyy')}-->
<#--            </#if>-->
<#--        </td>-->
<#--    </tr>-->
<#--    <tr>-->
<#--        <td class="bg-gray-light">Date of Discovery</td>-->
<#--        <td>-->
<#--            <#if approvalView.sopDeviationReport.dateOfDiscovery?has_content>-->
<#--                ${approvalView.sopDeviationReport.dateOfDiscovery?string('dd-MMM-yyyy')}-->
<#--            </#if>-->
<#--        </td>-->
<#--    </tr>-->
    <tr>
        <td class="bg-gray-light">Date of Discovery</td>
        <td>
            <#if approvalView.sopDeviationReport.confirmationDate?has_content>
                ${approvalView.sopDeviationReport.confirmationDate?string('dd-MMM-yyyy')}
            </#if>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">SOP Information:</td>
        <td>
<#--            <@spring.formSingleSelect "approvalView.sopDeviationReport.deviatedSOPDocument.id" sopMap "class='form-control'"/>-->
<#--            <@spring.showErrors "<br/>" "color:red;"/>-->
            <div><span class="text-semibold">SOP Document Id & Title:</span> ${approvalView.sopDeviationReport.deviatedSOPDocument.document.docId} & ${approvalView.sopDeviationReport.deviatedSOPDocument.document.title}</div>
            <div><span class="text-semibold">Version:</span> ${approvalView.sopDeviationReport.deviatedSOPDocument.version}</div>
            <div><span class="text-semibold">Effective Date:</span> ${approvalView.sopDeviationReport.deviatedSOPDocument.effectiveDate?string('dd-MMM-yyyy')}</div>
        </td>
    </tr>
    </tbody>
</table>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light">Deviation Details</td>
        <td colspan="3">
            ${approvalView.sopDeviationReport.deviationDetails?default('')?replace('\n', '<br/>')?no_esc}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">(Expected) Completion Date</td>
        <td>
            ${approvalView.sopDeviationReport.correctiveCompletionDate?string('dd-MMM-yyyy')}
        </td>
        <td class="bg-gray-light">(Expected) Completion Date</td>
        <td>
            ${approvalView.sopDeviationReport.preventiveCompletionDate?string('dd-MMM-yyyy')}
        </td>
    </tr>
    <tr>
        <td colspan="2" class="bg-gray-light">Corrective Action</td><td colspan="2" class="bg-gray-light">Preventive Action</td>
    </tr>

    <tr>
        <td colspan="2">
            ${approvalView.sopDeviationReport.correctiveAction?default('')?replace('\n', '<br/>')?no_esc}
        </td>
        <td colspan="2">
            ${approvalView.sopDeviationReport.preventiveAction?default('')?replace('\n', '<br/>')?no_esc}
        </td>
    </tr>
    </tbody>
</table>
