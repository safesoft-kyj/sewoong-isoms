<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > 부서 관리</title>
</head>
<script>
    $(document).ready(function(){

        //처음 값 Y로 초기화.
        $("#isYn").val("Y")

        $(document).on("#bIsYn", "change", function(){
            if($(this).is(":checked")){
                $("#isYn").val("Y")
            }else {
                $("#isYn").val("N")
            }
        })

    });
</script>
<body>
<div class="panel">
    <#--    <div class="panel-heading">-->
    <#--        <h3 class="panel-title">사용자별 직무 관리</h3>-->
    <#--    </div>-->
    <#--    <div class="pad-btm form-inline">-->
    <#--        <div class="row">-->
    <#--            <div class="col-sm-6 table-toolbar-left">-->
    <#--            </div>-->
    <#--            <div class="col-sm-6 table-toolbar-right">-->
    <#--                <div class="btn-group">-->
    <#--                    <a class="btn btn-primary" href="/admin/training/refresh-training/new"><i class="fa fa-plus"></i> Add Refresh Training</a>-->
    <#--                </div>-->
    <#--            </div>-->
    <#--        </div>-->
    <#--    </div>-->
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <a class="btn btn-primary" href="/admin/department?action=new#edit0"><i class="pli-pencil"></i>부서 추가</a>
                </div>
                <div class="col-sm-6 table-toolbar-right">
                    <#--                    <div class="form-group">-->
                    <#--                        <input id="demo-input-search2" type="text" placeholder="Search" class="form-control" autocomplete="off">-->
                    <#--                    </div>-->
                    <#--                    <div class="btn-group">-->
                    <#--                        <button class="btn btn-default"><i class="fa fa-search fa-lg"></i></button>-->
                    <#--                    </div>-->
                </div>
            </div>
        </div>
        <div class="table-responsive">
            <form name="f" method="post" action="/admin/department">
                <input type="hidden" name="action" value="${action}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th class="text-center" style="width:60px;">No.</th>
                        <th class="text-center">부서명</th>
                        <th>생성 날짜</th>
                        <th>수정 날짜</th>
                        <th class="text-center">사용 유무</th>
                        <th>#actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#if departments?has_content>
                        <#list departments as d>
                            <tr>
                                <td class="text-center">${d?index + 1}
                                    <a name="edit${d?index}"></a>
                                    <#if action == "edit" && id == d.id>
                                        <@spring.formHiddenInput "jobDescription.id"/>
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#if action == "edit" && id == d.id>
                                        <@spring.formInput "department.name" "class='form-control'"/>
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        ${d.name}
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == d.id>
                                        -
                                    <#elseif d.createdDate?has_content>
                                        ${d.createdDate?string("YYYY-MM-dd HH:mm:ss")}
                                    <#else>
                                        -
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == d.id>
                                        -
                                    <#elseif d.lastModifiedDate?has_content>
                                        ${d.lastModifiedDate?string("YYYY-MM-dd HH:mm:ss")}
                                    <#else>
                                        -
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#assign checked = ''/>
                                    <#if (d.isYn?has_content && d.isYn == 'Y')>
                                        <#assign checked = " checked='checked'"/>
                                    </#if>

                                    <#if action == "edit" && id == d.id>
                                        <@spring.formCheckbox "department.isYn" "value='Y' ${checked}" />
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        <span class="label label-${(d.isYn?contains("Y"))?then('success', 'warning')}">${(d.isYn?contains("Y"))?then('Use', 'Not Use')}</span>
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == d.id>
                                        <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
                                        <a class="btn btn-sm btn-warning" href="?action=list"><i class="fa fa-times"></i> 취소</a>
                                    <#else>
                                        <a class="btn btn-sm btn-mint" href="/admin/department?action=edit&id=${d.id}#edit${d?index}"><i class="fa fa-pencil"> 수정</i></a>
                                        <a class="btn btn-sm btn-danger" href="/admin/department?action=new&pid=${d.id}#edit${d?index}"><i class="pli-add"> 부서 추가</i></a>
                                    </#if>
                                </td>
                            </tr>
                            <!-- parentID -->
                            <#if action == "new" && pid == d.id >
                                <tr>
                                    <td class="text-center">#<a name="edit0"></a></td>
                                    <td>
                                    <@spring.formHiddenInput "department.parentDepartment.id" />
                                    <@spring.formInput "department.name" "class='form-control' placeholder='부서 이름'"/>
                                    <@spring.showErrors "" "color:red;"/>
                                    </td>
                                    <td>-</td>
                                    <td>-</td>
                                    <td class="text-center">
                                        <@spring.formCheckbox "department.isYn" "value='Y' checked"/>
                                        <@spring.showErrors "" "color:red;"/>
                                    </td>
                                    <td>
                                        <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
                                        <a class="btn btn-sm btn-warning" href="?action=list"><i class="fa fa-times"></i> 취소</a>
                                    </td>
                                </tr>
                            </#if>

                            <#if d.childDepartments?has_content>
                                <#list d.childDepartments as cd>
                                <tr>
                                    <td><i class="pli-turn-up-2"> </i></td>
                                    <td></td>
                                </tr>
                                </#list>
                            </#if>
                        </#list>
                    <#else>
                        <tr>
                            <td colspan="5" class="text-center">등록된 부서가 없습니다.</td>
                        </tr>
                    </#if>
                    <#if action == "new" && !pid?has_content >
                        <tr>
                            <td class="text-center">#<a name="edit0"></a></td>
                            <td>
                                <@spring.formInput "department.name" "class='form-control' placeholder='부서 이름'"/>
                                <@spring.showErrors "" "color:red;"/></td>
                            <td>-</td>
                            <td>-</td>
                            <td class="text-center">
                                <@spring.formCheckbox "department.isYn" "value='Y' checked"/>
                                <@spring.showErrors "" "color:red;"/>
                            </td>
                            <td class="text-center">
                                <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
                                <a class="btn btn-sm btn-warning" href="?action=list"><i class="fa fa-times"></i> 취소</a>
                            </td>
                        </tr>
                    </#if>
                    </tbody>
                </table>
            </form>
        </div>
    </div>
    <!--===================================================-->
    <!--End Data Table-->

</div>
</body>
</html>