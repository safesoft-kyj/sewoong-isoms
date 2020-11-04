<h5>Requester Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Name of Requester</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.nameOfRequester!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Team/Department</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.teamDept!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Protocol Title or No.</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.protocolTitleOrNo!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Project Manager or Team Manager</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.projectOrTeamManager!''}
        </td>
    </tr>
    </tbody>
</table>
<h5>External Customer Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Company Name or Institute Name</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.companyNameOrInstituteName!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">External Customer Name, Email, Job Title</td>
        <td>
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>No.</th>
                    <th>Name</th>
                    <th>Email</th>
                    <th>Job Title</th>
                </tr>
                </thead>
                <tbody id="externalCustomerBody">
                <#list approvalView.sopDisclosureRequestForm.externalCustomers as c>
                    <tr>
                        <td>${c?index + 1}</td>
                        <td>${c.name}</td>
                        <td>${c.email}</td>
                        <td>${c.jobTitle!''}</td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Request Date<br/><small>(Date or Period to access e-SOP system)</small></td>
        <td>
            ${approvalView.sopDisclosureRequestForm.requestStartDate?string('dd-MMM-yyyy')} ~ ${approvalView.sopDisclosureRequestForm.requestEndDate?string('dd-MMM-yyyy')}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Requested Documents</td>
        <td>
            <h6>[SOP/RD]</h6>
            <#list approvalView.sopDisclosureRequestForm.requestedDocumentSOPs as sop>
                <div>
                    <small><span class="label label-purple">SOP</span> ${sop.documentVersion.document.docId}/${sop.documentVersion.document.title}/${sop.documentVersion.version}</small>
                </div>
            </#list>
            <#if approvalView.sopDisclosureRequestForm.requestedDocumentRDs?has_content>
                <#list approvalView.sopDisclosureRequestForm.requestedDocumentRDs as rd>
                    <div>
                        <small><span class="label label-important label-info">RD</span> ${rd.documentVersion.document.docId}/${rd.documentVersion.document.title}/${rd.documentVersion.version}</small>
                    </div>
                </#list>
            </#if>
            <hr/>
            <h6>[Digital Binder]</h6>
            <#if approvalView.sopDisclosureRequestForm.disclosureDigitalBinders?has_content>
                <#list approvalView.sopDisclosureRequestForm.disclosureDigitalBinders as db>
                    <span class="label label-default">${db.user.engName?default(db.user.name)}</span>
                </#list>
            </#if>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Type of Document Access</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.documentAccess.label}
            <#if approvalView.sopDisclosureRequestForm.documentAccess == 'OTHER'>
                :<u>${approvalView.sopDisclosureRequestForm.documentAccessOther}</u>
            </#if>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Purpose of Disclosure</td>
        <td>
            ${approvalView.sopDisclosureRequestForm.purposeOfDisclosure.label}
            <#if approvalView.sopDisclosureRequestForm.purposeOfDisclosure == 'OTHER'>
                :<u>${approvalView.sopDisclosureRequestForm.purposeOfDisclosureOther}</u>
            </#if>
        </td>
    </tr>

    </tbody>
</table>
