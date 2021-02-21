<#setting locale="en_US">
<#if currentRFs?has_content>
    <table class="table table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center">Document ID</th>
            <th class="text-center">SOP Title</th>
            <th class="text-center">Version</th>
            <th class="text-center">Effective Date</th>
        </tr>
        </thead>
        <tbody>
        <#list currentRFs as rf>
            <tr>
                <td class="text-center">${rf.document.docId}</td>
                <td>${rf.document.title}</td>
                <td class="text-center">${rf.version}</td>
                <td class="text-center">${rf.effectiveDate?string('dd-MMM-yyyy')}</td>
            </tr>
        </#list>
        </tbody>
    </table>
<#--    <div>-->
<#--        <small>* 해당 메일은 Dt&SanoMedics e-SOP 시스템에서 발송 되었습니다.</small>-->
<#--    </div>-->
</#if>