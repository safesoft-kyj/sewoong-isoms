<h5>Requester Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Name of Requester</td>
        <td>
            ${approvalView.sopRfRequestForm.nameOfRequester!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Name of Team/Department</td>
        <td>
            ${approvalView.sopRfRequestForm.nameOfTeamDept!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light" rowspan="4">Request for</td>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRfRequestForm.newSOPDevelopment" "disabled='disabled'"/> New SOP Development
            </label>
            <div id="sopDevDiv">
                <#list approvalView.sopRfRequestForm.sopDevelopmentDocs as doc>
                    <div>${doc.docId}/${doc.title}/${doc.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRfRequestForm.newRDDevelopment" "disabled='disabled'"/> New RD Development
            </label>
            <div id="rdDevDiv">
                <#list approvalView.sopRfRequestForm.rdDevelopmentDocs as doc>
                    <div>${doc.docId}/${doc.title}/${doc.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRfRequestForm.sopRevision" "data-id='sop' disabled='disabled'"/> SOP Revision
            </label>
            <div id="sopDiv">
<#--                <@spring.formMultiSelect "approvalView.sopRfRequestForm.sopRevisionIds" sopMap "class='form-control'"/>-->
                <#list approvalView.sopRfRequestForm.sopRevisionDocs as doc>
                    <div>${doc.documentVersion.document.docId}/${doc.documentVersion.document.title}/${doc.documentVersion.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRfRequestForm.rdRevision" "data-id='rd' disabled='disabled'"/> RD Revision
            </label>
            <div id="rdDiv">
                <#list approvalView.sopRfRequestForm.rdRevisionDocs as doc>
                    <div>${doc.documentVersion.document.docId}/${doc.documentVersion.document.title}/${doc.documentVersion.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Reason for the request</td>
        <td>
            ${approvalView.sopRfRequestForm.reasonForTheRequest!''}
        </td>
    </tr>

    </tbody>
</table>
<h5>Requested by</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Comments, if required</td>
        <td>
            ${approvalView.sopRfRequestForm.requestedByComments!''}
        </td>
    </tr>
    </tbody>
</table>