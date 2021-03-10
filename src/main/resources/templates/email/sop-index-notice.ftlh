<#setting locale="en_US">
<#if currentSOPs?has_content>
    <table class="table table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center">SOP Category</th>
            <th class="text-center">SOP No.</th>
            <th class="text-center">SOP Title</th>
            <th class="text-center">Document ID</th>
            <th class="text-center">Version</th>
            <th class="text-center">Effective Date</th>
        </tr>
        </thead>
        <tbody>
        <#list currentSOPs as sop>
            <tr>
                <td class="text-center">${sop.document.category.shortName}</td>
                <td class="text-center">${sop.document.documentNo}</td>
                <td>${sop.document.title}</td>
                <td class="text-center">${sop.document.docId}</td>
                <td class="text-center">${sop.version}</td>
                <td class="text-center">${sop.effectiveDate?string('dd-MMM-yyyy')}</td>
            </tr>
        </#list>
        </tbody>
    </table>
<#--    <div>-->
<#--        <small>* 해당 메일은 Dt&SanoMedics e-SOP 시스템에서 발송 되었습니다.</small>-->
<#--    </div>-->
</#if>