<#setting locale="en_US">
<!-- 1 SOP Deviation Report -->
<#if approval.sopDeviationReport?has_content>
    <table class="invoice-items" cellpadding="0" cellspacing="0">
        <tr>
            <td>Project No.</td>
            <td class="alignleft"><#if approval.sopDeviationReport.projectNo?has_content>${approval.sopDeviationReport.projectNo}</#if></td>
        </tr>
        <tr>
            <td>Deviated SOP</td>
            <td class="alignleft">
                <#if deviatedSOPDocument?has_content && deviatedSOPDocument.document?has_content>
                    ${deviatedSOPDocument.document.docId} & ${deviatedSOPDocument.document.title} v${deviatedSOPDocument.version}
                </#if>
            </td>
        </tr>
        <tr>
            <td>Date of Occurrence</td>
            <td class="alignleft"><#if approval.sopDeviationReport.dateOfOccurrence?has_content>${approval.sopDeviationReport.dateOfOccurrence?string('dd-MMM-yyyy')?upper_case}</#if></td>
        </tr>
        <tr>
            <td>Date of Discovery</td>
            <td class="alignleft"><#if approval.sopDeviationReport.dateOfDiscovery?has_content>${approval.sopDeviationReport.dateOfDiscovery?string('dd-MMM-yyyy')?upper_case}</#if></td>
        </tr>
    </table>
    <#elseif approval.sopRdRequestForm?has_content>
        <!-- 2 SOP/RD Request Form -->
        <table class="invoice-items" cellpadding="0" cellspacing="0">
            <tr>
                <td>Request for</td>
                <td class="alignleft">
                    <#if approval.sopRdRequestForm.newSOPDevelopment == true && approval.sopRdRequestForm.sopDevelopmentDocs?has_content>
                        <strong>[New SOP Development]</strong><br/>
                        <#list approval.sopRdRequestForm.sopDevelopmentDocs as sop>
                            - ${sop.docId}/${sop.title}/v${sop.version}<br/>
                        </#list>
                        <br/>
                    </#if>
                    <#if approval.sopRdRequestForm.newRDDevelopment == true && approval.sopRdRequestForm.rdDevelopmentDocs?has_content>
                        <strong>[New RD Development]</strong><br/>
                        <#list approval.sopRdRequestForm.rdDevelopmentDocs as rd>
                            - ${rd.docId}/${rd.title}/v${rd.version}<br/>
                        </#list>
                        <br/>
                    </#if>
                    <#if sopRevisionDocs?has_content>
                        <strong>[SOP Revision]</strong><br/>
                        <#list sopRevisionDocs as sop>
                            - ${sop.document.docId}/${sop.document.title}/v${sop.version}<br/>
                        </#list>
                        <br/>
                    </#if>
                    <#if rdRevisionDocs?has_content>
                        <strong>[RD Revision]</strong><br/>
                        <#list rdRevisionDocs as rd>
                            - ${rd.document.docId}/${rd.document.title}/v${rd.version}<br/>
                        </#list>
                    </#if>
                </td>
            </tr>
            <tr>
                <td>Reason for the request</td>
                <td class="alignleft">${approval.sopRdRequestForm.reasonForTheRequest}</td>
            </tr>
        </table>
    <#elseif approval.sopWaiverApprovalForm?has_content>
    <!-- 3 SOP Waiver Request and Approval Form -->
    <table class="invoice-items" cellpadding="0" cellspacing="0">
        <tr>
            <td>Project No.</td>
            <td class="alignleft"><#if approval.sopWaiverApprovalForm.projectNo?has_content>${approval.sopWaiverApprovalForm.projectNo}</#if></td>
        </tr>
        <tr>
            <td>Protocol Title/No.</td>
            <td class="alignleft"><#if approval.sopWaiverApprovalForm.protocolNo?has_content>${approval.sopWaiverApprovalForm.protocolNo}</#if></td>
        </tr>
        <tr>
            <td>Waiver Start Date</td>
            <td class="alignleft">${approval.sopWaiverApprovalForm.waiverStartDate?string('dd-MMM-yyyy')?upper_case}</td>
        </tr>
        <tr>
            <td>Waiver End Date</td>
            <td class="alignleft">${approval.sopWaiverApprovalForm.waiverEndDate?string('dd-MMM-yyyy')?upper_case}</td>
        </tr>
        <tr>
            <td>Waived SOP</td>
            <td class="alignleft">${deviatedSOP.document.docId}/${deviatedSOP.document.title}/v${deviatedSOP.version}</td>
        </tr>
    </table>
    <#elseif approval.sopDisclosureRequestForm?has_content>
    <table class="invoice-items" cellpadding="0" cellspacing="0">
        <tr>
            <td>Protocol Title/No.</td>
            <td class="alignleft">${approval.sopDisclosureRequestForm.protocolTitleOrNo?default('')}</td>
        </tr>
        <tr>
            <td>Company Name or Institute Name</td>
            <td class="alignleft">${approval.sopDisclosureRequestForm.companyNameOrInstituteName?default('')}</td>
        </tr>
        <tr>
            <td>Request Date</td>
            <td class="alignleft">${approval.sopDisclosureRequestForm.requestStartDate?string('dd-MMM-yyyy')?upper_case} ~ ${approval.sopDisclosureRequestForm.requestEndDate?string('dd-MMM-yyyy')?upper_case}</td>
        </tr>
        <tr>
            <td>Purpose of Disclosure</td>
            <td class="alignleft">${approval.sopDisclosureRequestForm.purposeOfDisclosure.label}
                <#if approval.sopDisclosureRequestForm.purposeOfDisclosure == 'OTHER'>
                    : <u>${approval.sopDisclosureRequestForm.purposeOfDisclosureOther}</u>
                </#if></td>
        </tr>
    </table>
    <#elseif approval.retirementApprovalForm?has_content>
    <!-- 5 SOP/RD Retirement Form -->
    <h5>[Request for Retirement Documents]</h5>
    <table class="invoice-items" cellpadding="0" cellspacing="0">
        <tr>
            <th class="aligncenter">Document Type</th>
            <th class="aligncenter">SOP/RD<br/>Document Id</th>
            <th class="aligncenter">SOP/RD<br/>Title</th>
            <th class="aligncenter">Ver.</th>
            <th class="aligncenter">Effective Date</th>
        </tr>
        <#if retirementSOPs?has_content>
            <#list retirementSOPs as sop>
        <tr>
            <td class="aligncenter">SOP</td>
            <td class="aligncenter">${sop.document.docId}</td>
            <td class="aligncenter">${sop.document.title}</td>
            <td class="aligncenter">${sop.version}</td>
            <td class="aligncenter">${sop.effectiveDate?string('dd-MMM-yyyy')?upper_case}</td>
        </tr>
            </#list>
        </#if>
        <#if retirementRDs?has_content>
            <#list retirementRDs as rd>
                <tr>
                    <td class="aligncenter">RD</td>
                    <td class="aligncenter">${rd.document.docId}</td>
                    <td class="aligncenter">${rd.document.title}</td>
                    <td class="aligncenter">${rd.version}</td>
                    <td class="aligncenter">${rd.effectiveDate?string('dd-MMM-yyyy')?upper_case}</td>
                </tr>
            </#list>
        </#if>
    </table>
</#if>


