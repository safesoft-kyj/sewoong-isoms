<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/common/pagination.ftl" as pagination />
<#setting locale="en_US">
<html>
<head>
    <title>Admin > Training > Off-line Training</title>
</head>
<body>
<div class="panel">
    <div class="panel-heading">
        <#--        <h3 class="panel-title">Off-line Training</h3>-->
    </div>

    <!--Data Table-->
    <!--===================================================-->
    <@pagination.bind isoOfflineTraining/>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center">No.</th>
                    <th class="text-center">Completion Date<br/><span class="text-muted"></span> </th>
                    <th>Description of Training Course / Hour</th>
                    <#--                    <th>Training Hr.</th>-->
                    <th>Organization
                        <#--                        <br/><span class="text-muted">(Academy, Self-training, etc.)</span>-->
                    </th>
                    <th>Created On</th>
                    <th>Created By</th>
                    <th class="text-center">Status</th>
                    <th>Modified By</th>
                    <th>Modified Date</th>
                    <#--                    <th>#Actions</th>-->
                </tr>
                </thead>
                <tbody>
                <#if isoOfflineTraining.content?has_content>
                    <#list isoOfflineTraining.content as t>
                        <tr>
                            <td class="text-center">${(isoOfflineTraining.totalElements - (isoOfflineTraining.size * isoOfflineTraining.number)) - t?index}</td>
                            <td class="text-center">${t.trainingDate?string('dd-MMM-yyyy')?upper_case}</td>
                            <td><a href="offline-training/${t.id}"> <#list t.isoOfflineTrainingDocuments as doc>
                                    <div<#if t.status == 'DELETED'> style="text-decoration: line-through;color:indianred !important;" </#if>><i class="fa fa-file-pdf-o"></i> [${doc.iso.isoType.label}] ${doc.iso.title} / <span class="label label-default"><i class="fa fa-clock-o"></i> ${doc.hour}</span></div>
                                </#list></a></td>
                            <#--                        <td>${t.hour}</td>-->
                            <td>${t.organization}</td>
                            <td>${t.createdDate?string('yyyy-MM-dd')}</td>
                            <td>${t.createdBy}</td>
                            <td class="text-center"><span class="label label-${t.status.className}">${t.status.label}</span></td>
                            <td>${t.lastModifiedBy!'-'}</td>
                            <td>${(t.lastModifiedDate?has_content)?then(t.lastModifiedDate?string('yyyy-MM-dd'), '-')}</td>
                            <#--                        <td>${(i % 3 == 0)?then('<span class="label label-info">승인대기</span>', '<span class="label label-success">승인완료</span><span class="label label-warning">반려</span>')}</td>-->
                            <#--                        <td><#if i % 3 == 0>-->
                            <#--                            <button class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 삭제</button>-->
                            <#--                        </#if>-->
                            <#--                        </td>-->
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="9" class="text-center">Off-line Training 데이터가 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>
        </div>
        <@pagination.default/>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
</body>
</html>