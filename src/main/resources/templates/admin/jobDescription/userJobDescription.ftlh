<#import "/common/pagination.ftl" as pagination />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > 사용자별 ROLE 관리</title>
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.assignDate').datepicker({format:'yyyy-mm-dd', autoclose:true, clearBtn:true, todayBtn: "linked", todayHighlight: true});
        });
    </script>
</head>
<body>

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">사용자별 ROLE 관리</h3>
    </div>
    <!--===================================================-->
    <div class="panel-body">
        <form method="post">
            <div class="table-responsive">
                <table class="table table-bordered">
                    <tbody>
                    <tr>
                        <td class="bg-gray-light">이름</td><td>${user.name}</td>
                    </tr>
                    <tr>
                        <td class="bg-gray-light">직무</td><td>
                            <table class="table table-bordered">
                                <thead>
                                <tr class="bg-gray-light">
                                    <th>No.</th>
                                    <th>직무</th>
                                    <th>직무 배정일</th>
                                    <th>#actions</th>
                                </tr>
                                </thead>
                                <tbody>
                                <#if user.userJobDescriptions?has_content>
                                    <#list user.userJobDescriptions as jd>
                                        <tr<#if jd.delete == true> style="text-decoration: line-through;color:red"</#if>>
                                            <td>${jd?index + 1}</td>
                                            <td><@spring.formSingleSelect "user.userJobDescriptions[" + jd?index +"].jobDescription.id" jdMap "class='form-control'"/>
                                                <@spring.showErrors "" "color:red;"/></td>
                                            <td><@spring.formInput "user.userJobDescriptions[" + jd?index +"].assignDate" "class='form-control assignDate'"/>
                                                <@spring.showErrors "" "color:red;"/></td>
                                            <td><#if jd.delete == false><button name="remove" type="submit" value="${jd?index}" class="btn btn-xs btn-danger"><i class="fa fa-trash-o"></i></button></#if> </td>
                                        </tr>
                                    </#list>
                                <#else>
                                    <tr>
                                        <td>1</td>
                                        <td><@spring.formSingleSelect "user.userJobDescriptions[0].jobDescription.id" jdMap "class='form-control'"/>
                                            <@spring.showErrors "" "color:red;"/></td>
                                        <td><@spring.formInput "user.userJobDescriptions[0].assignDate" "class='form-control assignDate'"/>
                                            <@spring.showErrors "" "color:red;"/></td>
                                        <td><button name="remove" disabled="disabled" type="submit" class="btn btn-xs btn-danger"><i class="fa fa-trash-o"></i></button> </td>
                                    </tr>
                                </#if>
                                </tbody>
                            </table>
                            <div>
                                <button class="btn btn-primary btn-sm" type="submit" name="add"><i class="fa fa-plus"></i> 추가</button>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="text-right">
                    <a class="btn btn-default" href="/admin/role/user"><i class="fa fa-chevron-left"></i> 뒤로</a>
                    <button class="btn btn-success"><i class="fa fa-check"></i> 저장</button>
                </div>
            </div>
        </form>
    </div>
    <!--===================================================-->
    <!--End Data Table-->
</div>
</body>
</html>