<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">

<@pagination.bind auditList/>
<div class="table-responsive">
    <table class="table table-hover table-bordered">
        <thead>
        <tr class="bg-gray-light">
            <th class="text-center" style="width:80px">Rev No.</th>
            <th class="text-center">Revision Date</th>
            <th class="text-center">Revision Type</th>
            <th class="text-center">ID</th>
            <th class="text-center">Approval ID</th>
<#--            <th class="text-center">Approval Lines</th>-->
            <th class="text-center">Requester<br />Name</th>
            <th class="text-center">Requester<br />Team/Department</th>
            <th class="text-center">Company Name<br/>or InstituteName</th>
            <th class="text-center">Request Start Date</th>
            <th class="text-center">Request End Date</th>
            <th class="text-center">Requested Documents</th>
            <th class="text-center">Document Access Type</th>
            <th class="text-center">Purpose of Disclosure</th>
        </tr>
        </thead>
        <tbody>
        <#if auditList.content?has_content>
            <#list auditList.content as audit>
                <tr>
                    <#assign new = ''>
                    <#if audit.revisionType == 'ADD'>
                        <#assign revType = 'info' />
                        <#assign new = 'text-info text-bold bg-trans-dark'>
                    <#elseif audit.revisionType == 'MOD'>
                        <#assign revType = 'warning' />
                    <#elseif audit.revisionType == 'DEL'>
                        <#assign revType = 'danger' />
                    </#if>
                <td class="text-center ${new}">${audit.revisionEntity.rev!''}</td>
                <td class="text-center ${new}">${audit.revisionEntity.revisionDate?string('yyyy-MM-dd HH:mm:ss')}</td>
                <td class="text-center ${new}"><span class="label label-${revType}">${audit.revisionType}</span></td>
                <td class="text-center ${new}">${audit.entity.id}</td>
                <td class="text-center ${new}" style="${audit.contains('approval')?then('color:red;', '')}">${audit.entity.approval.id}</td>
<#--                <td class="text-center ${new}">-->
<#--                    <span class="label ${new?has_content?then('label-info', audit.contains('approval')?then('label-danger', 'bg-gray-dark'))} add-popover"-->
<#--                          data-toggle="popover" data-container="body" data-placement="top"-->
<#--                          data-trigger="hover" data-html="true"-->
<#--                          data-content="<div><#list audit.entity.approval.approvalLines as approvalLine><p><i class='fa fa-check-square'></i>&nbsp;${approvalLine.lineType.label!''}&nbsp;${approvalLine.displayName}</p></#list></div>"-->
<#--                          style="max-width: 160px; cursor: pointer;">-->
<#--                        <i class="fa fa-search"></i>-->
<#--                    </span>-->
<#--                </td>-->
                <td class="text-center ${new}" style="${audit.contains('nameOfRequester')?then('color:red;', '')}">${audit.entity.nameOfRequester!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('teamDept')?then('color:red;', '')}">${audit.entity.teamDept!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('companyNameOrInstituteName')?then('color:red;', '')}">${audit.entity.companyNameOrInstituteName!'N/A'}</td>
                <td class="text-center ${new}" style="${audit.contains('requestStartDate')?then('color:red;', '')}">${audit.entity.requestStartDate?has_content?then(audit.entity.requestStartDate?string('yyyy-MM-dd'), 'N/A')}</td>
                <td class="text-center ${new}" style="${audit.contains('requestEndDate')?then('color:red;', '')}">${audit.entity.requestEndDate?has_content?then(audit.entity.requestEndDate?string('yyyy-MM-dd'), 'N/A')}</td>
                <td class="text-center ${new}">
                    <button type="button" data-target="#request-modal" data-toggle="modal"
                            class="btn btn-xs ${new?has_content?then('btn-info', (audit.contains('requestedDocumentSOPs')||audit.contains('requestedDocumentRFs')||audit.contains('disclosureSOPTrainingLog')||audit.contains('disclosureISOTrainingLog'))?then('btn-danger', 'bg-gray-dark'))}"
                            data-sop="<#if audit.entity.requestedDocumentSOPs?has_content><h6>[SOP/RF]</h6><#list audit.entity.requestedDocumentSOPs as sop><div><small><span class='label label-purple'>SOP</span>${sop.documentVersion.document.docId}/${sop.documentVersion.document.title}/${sop.documentVersion.version}</small></div></#list></#if>"
                            data-rf="<#if audit.entity.requestedDocumentRFs?has_content><#list audit.entity.requestedDocumentRFs as rf><div><small><span class='label label-important label-info'>RF</span>${rf.documentVersion.document.docId}/${rf.documentVersion.document.title}/${rf.documentVersion.version}</small></div></#list></#if>"
                            data-soptraining="<#if audit.entity.disclosureSOPTrainingLog?has_content><hr/><h6>[Training Log(SOP)]</h6><#list audit.entity.disclosureSOPTrainingLog as sop><span class='label label-default'>${sop.user.engName?default(sop.user.name)}</span>&nbsp;</#list></#if>"
                            data-isotraining="<#if audit.entity.disclosureISOTrainingLog?has_content><h6>[Training Log(ISO)]</h6><#list audit.entity.disclosureISOTrainingLog as iso><span class='label label-default'>${iso.user.engName?default(iso.user.name)}</span>&nbsp;</#list></#if>">
                        <i class="fa fa-search"></i>
                    </button>
                </td>
                <td class="text-center ${new}" style="${audit.contains('documentAccess')?then('color:red;', '')}">
                    <#if audit.entity.documentAccess?has_content>
                        <#if audit.entity.documentAccess == 'OTHER'>
                            ${audit.entity.documentAccess}(${audit.entity.documentAccessOther!'N/A'})
                        <#else>
                            ${audit.entity.documentAccess}
                        </#if>
                    <#else>
                        N/A
                    </#if>
                </td>
                <td class="text-center ${new}" style="${audit.contains('purposeOfDisclosure')?then('color:red;', '')}">
                    <#if audit.entity.purposeOfDisclosure?has_content>
                        <#if audit.entity.purposeOfDisclosure == 'OTHER'>
                            ${audit.entity.purposeOfDisclosure}(${audit.entity.purposeOfDisclosureOther!'N/A'})
                        <#else>
                            ${audit.entity.purposeOfDisclosure}
                        </#if>
                    <#else>
                        N/A
                    </#if>
                </td>
            </#list>
        <#else>
            <tr>
                <td colspan="13" class="text-center">Change History가 없습니다.</td>
            </tr>
        </#if>
        </tbody>
    </table>

    <div class="modal fade" id="request-modal" role="dialog" tabindex="-1" aria-labelledby="training-modal" aria-hidden="true">
        <div class="modal-dialog modal-md">
            <div class="modal-content">

                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title">Requested Document</h4>
                </div>
                <!--Modal body-->
                <div class="modal-body">
                    <div id="request-html" style="max-height: 700px;"></div>
                </div>

                <!--Modal footer-->
                <div class="modal-footer">
                    <div class="row">
                        <div class="col-xs-8 text-left">
                        </div>
                        <div class="col-xs-4">
                            <button class="btn btn-info" data-dismiss="modal" type="button"><i class="fa fa-close"></i>Close</button>
                        </div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <script>
        $("#request-modal").on('show.bs.modal', function (event) {
            let button = $(event.relatedTarget); // Button that triggered the modal
            let sop = button.data('sop');
            let rf = button.data('rf');
            let soptraining = button.data('soptraining');
            let isotraining = button.data('isotraining');

            let content = sop + rf + soptraining + isotraining

            console.log("sop : ", sop);
            console.log("rf : ", rf);
            console.log("training : ", soptraining);
            console.log("iso training : ", isotraining);

            $("#request-html").html(content);
        });
    </script>

</div>
<@pagination.default />