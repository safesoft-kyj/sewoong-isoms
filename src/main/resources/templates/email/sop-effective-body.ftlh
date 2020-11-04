<#setting locale="en_US">
<#if effectiveSOPs?has_content>
    <h5>Effective SOP Information</h5>
    <table class="table table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th>Document ID</th>
            <th>Version</th>
            <th>Title</th>
            <th>Effective Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <#list effectiveSOPs as sop>
            <tr>
                <td>${sop.document.docId}</td>
                <td>${sop.version}</td>
                <td>${sop.document.title}</td>
                <td>${sop.effectiveDate?string('dd-MMM-yyyy')}</td>
                <td>Effective</td>
            </tr>
        </#list>
        </tbody>
    </table>
</#if>
<#if effectiveRDs?has_content>
    <h5>Effective RD Information</h5>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Document ID</th>
            <th>Version</th>
            <th>Title</th>
            <th>Effective Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <#list effectiveRDs as rd>
            <tr>
                <td>${rd.document.docId}</td>
                <td>${rd.version}</td>
                <td>${rd.document.title}</td>
                <td>${rd.effectiveDate?string('dd-MMM-yyyy')}</td>
                <td>Effective</td>
            </tr>
        </#list>
        </tbody>
    </table>
</#if>
<#if retirementSOPs?has_content>
    <h5>Retirement SOP Information</h5>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Document ID</th>
            <th>Version</th>
            <th>Title</th>
            <th>Retirement Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <#list retirementSOPs as sop>
            <tr>
                <td>${sop.documentVersion.document.docId}</td>
                <td>${sop.documentVersion.version}</td>
                <td>${sop.documentVersion.document.title}</td>
                <td>${sop.retirementDate?string('dd-MMM-yyyy')}</td>
                <td>Retired</td>
            </tr>
        </#list>
        </tbody>
    </table>
</#if>
<#if retirementRDs?has_content>
    <h5>Retirement RD Information</h5>
    <table class="table table-bordered">
        <thead>
        <tr>
            <th>Document ID</th>
            <th>Version</th>
            <th>Title</th>
            <th>Retirement Date</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <#list retirementRDs as rd>
            <tr>
                <td>${rd.documentVersion.document.docId}</td>
                <td>${rd.documentVersion.version}</td>
                <td>${rd.documentVersion.document.title}</td>
                <td>${rd.retirementDate?string('dd-MMM-yyyy')}</td>
                <td>Retired</td>
            </tr>
        </#list>
        </tbody>
    </table>
</#if>