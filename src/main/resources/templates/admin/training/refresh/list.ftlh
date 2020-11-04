<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Training Management > Refresh Training</title>
    <script>
        $(document).ready(function() {
            $("button[name='removeBtn']").click(function() {
               if(confirm('삭제 하시겠습니까?')) {
                   var id = $(this).data("id");
                   $("#refId").val(id);
                   $("#removeForm").submit();
               }
            });
        })
    </script>
</head>
<body>
<form name="f" method="post" id="removeForm">
    <input type="hidden" name="_method" value="delete"/>
    <input type="hidden" name="id" id="refId">
</form>

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">Refresh Training</h3>
    </div>
    <div class="pad-btm form-inline">
        <div class="row">
            <div class="col-sm-6 table-toolbar-left">
            </div>
            <div class="col-sm-6 table-toolbar-right">
                <div class="btn-group">
                    <a class="btn btn-primary" href="/admin/training/refresh-training/new"><i class="fa fa-plus"></i> Add Refresh Training</a>
                </div>
            </div>
        </div>
    </div>
    <!--Data Table-->
    <!--===================================================-->
    <@pagination.bind refreshTraining/>
    <div class="panel-body">
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr class="bg-gray-light">
                    <th class="text-center" style="width:60px;">No.</th>
                    <th>SOP Document ID</th>
                    <th>Title</th>
                    <th class="text-center">Version</th>
                    <th class="text-center">Effective Date</th>
                    <th class="text-center">Training Period</th>
                    <th>#actions</th>
                </tr>
                </thead>
                <tbody>
                <#if refreshTraining.content?has_content>
                    <#list refreshTraining.content as t>
                        <tr>
                            <td class="text-center">${(refreshTraining.totalElements - (refreshTraining.size * refreshTraining.number)) - t?index}</td>
                            <td>${t.documentVersion.document.docId}</td>
                            <td>${t.documentVersion.document.title}</td>
                            <td class="text-center">${t.documentVersion.version}</td>
                            <td class="text-center">${t.documentVersion.effectiveDate?string('yyyy-MM-dd')}</td>
                            <td class="text-center"><span class="text-info">${t.startDate?string('yyyy-MM-dd')} ~ ${t.endDate?string('yyyy-MM-dd')}</span></td>
                            <td><a class="btn btn-info btn-xs" href="refresh-training/${t.id}"><i class="fa fa-pencil"></i> Edit</a>
                                <button class="btn btn-danger btn-xs add-popover" type="button" <#if t.trainingLogs?has_content> data-toggle="popover" data-container="body" data-placement="top" data-original-title="삭제 할수 없습니다." data-content="Training 이력이 ${t.trainingLogs?size}건 존재 합니다."<#else> name="removeBtn" data-id="${t.id}"</#if>><i class="fa fa-trash-o"></i> Remove</button></td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="7" class="text-center">등록된 데이터가 없습니다.</td>
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