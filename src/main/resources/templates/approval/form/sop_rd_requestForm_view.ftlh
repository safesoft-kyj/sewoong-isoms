<h5>Requester Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Name of Requester</td>
        <td>
            ${approvalView.sopRdRequestForm.nameOfRequester!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Name of Team/Department</td>
        <td>
            ${approvalView.sopRdRequestForm.nameOfTeamDept!''}
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light" rowspan="4">Request for</td>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRdRequestForm.newSOPDevelopment" "disabled='disabled'"/> New SOP Development
            </label>
            <div id="sopDevDiv">
                <#list approvalView.sopRdRequestForm.sopDevelopmentDocs as doc>
                    <div>${doc.docId}/${doc.title}/${doc.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRdRequestForm.newRDDevelopment" "disabled='disabled'"/> New RD Development
            </label>
            <div id="rdDevDiv">
                <#list approvalView.sopRdRequestForm.rdDevelopmentDocs as doc>
                    <div>${doc.docId}/${doc.title}/${doc.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRdRequestForm.sopRevision" "data-id='sop' disabled='disabled'"/> SOP Revision
            </label>
            <div id="sopDiv">
<#--                <@spring.formMultiSelect "approvalView.sopRdRequestForm.sopRevisionIds" sopMap "class='form-control'"/>-->
                <#list approvalView.sopRdRequestForm.sopRevisionDocs as doc>
                    <div>${doc.documentVersion.document.docId}/${doc.documentVersion.document.title}/${doc.documentVersion.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
                <@spring.formCheckbox "approvalView.sopRdRequestForm.rdRevision" "data-id='rd' disabled='disabled'"/> RD Revision
            </label>
            <div id="rdDiv">
                <#list approvalView.sopRdRequestForm.rdRevisionDocs as doc>
                    <div>${doc.documentVersion.document.docId}/${doc.documentVersion.document.title}/${doc.documentVersion.version}</div>
                </#list>
            </div>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Reason for the request</td>
        <td>
            ${approvalView.sopRdRequestForm.reasonForTheRequest!''}
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
            ${approvalView.sopRdRequestForm.requestedByComments!''}
        </td>
    </tr>
    </tbody>
</table>