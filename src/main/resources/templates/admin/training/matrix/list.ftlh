<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>Admin > Training Management > SOP Training Matrix</title>
    <script>
        $(document).ready(function() {
            $("#uploadForm").submit(function() {
               if(!$("#title").val()) {
                   alert('Title을 입력해 주세요.');
                   $("#title").focus();
                   return false;
               } else if(!$("#uploadFile").val()) {
                   alert('파일을 선택해 주세요.');
                   $("#uploadFile").focus();
                   return false;
               }

               return true;
            });
        });
    </script>
</head>
<body>



<div class="panel">
    <#--    <div class="panel-heading">-->
    <#--        <h3 class="panel-title">공지사항</h3>-->
    <#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <form method="post" class="form-horizontal" enctype="multipart/form-data" id="uploadForm">
                        <div class="panel panel-bordered panel-default">
<#--                            <div class="panel-heading">-->
<#--                                <h3 class="panel-title">-->
<#--                                    Training Matrix Upload-->
<#--                                </h3>-->
<#--                            </div>-->
                            <div class="panel-body">
                                <div class="form-group">
                                    <label for="title" class="col-sm-3">Title</label>
                                    <div class="col-sm-9">
                                        <input type="text" placeholder="Title" name="title" id="title" class="form-control">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label for="trainingMatrix" class="col-sm-3">Training Matrix</label>
                                    <div class="col-sm-9">
                                        <input type="file" placeholder="Training Matrix" name="uploadFile" id="uploadFile" class="form-control" accept=".xls,.xlsx">
                                    </div>
                                </div>
                            </div>
                            <div class="panel-footer text-right">
                                <button class="btn btn-primary" type="submit"><i class="fa fa-upload"></i> Upload</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-6 table-toolbar-right">

                </div>
            </div>
        </div>
        <@pagination.bind trainingMatrix/>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th>Title</th>
                    <th>파일명</th>
                    <th><i class="fa fa-download"></i> </th>
                    <th class="text-center" style="width:140px">작성자</th>
                    <th class="text-center" style="width:160px;">등록일</th>
                </tr>
                </thead>
                <tbody>
                <#if trainingMatrix.content?has_content>
                    <#list trainingMatrix.content as matrix>
                        <tr>
                            <td class="text-center">${(trainingMatrix.totalElements - (trainingMatrix.size * trainingMatrix.number)) - matrix?index}</td>
                            <td>${matrix.title}</td>
                            <td>${matrix.originalFileName}</td>
                            <td><a href="/common/download/trainingMatrix/${matrix.id}" class="btn-link"> <i class="fa fa-download"></i> </a></td>
                            <td class="text-center">${matrix.createdBy}</td>
                            <td class="text-center"><span class="text-muted">${matrix.createdDate?string('yyyy-MM-dd')}</span></td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="6" class="text-center">업로드된 Training Matrix 파일이 없습니다.</td>
                    </tr>
                </#if>
                </tbody>
            </table>

        </div>
        <@pagination.default/>

        <#--        <nav style="float:left;">-->
        <#---->
        <#--        </nav>-->
        <#--        <@pagination.counter />-->
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
</body>
</html>