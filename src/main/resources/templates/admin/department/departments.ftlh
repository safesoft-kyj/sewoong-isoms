<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > 부서 관리</title>
    <script>
        $(document).ready(function(){

            $(document).on("click", "#deleteDept", function(e){
                e.preventDefault(); //기존 submit 방지
                let id = $(this).data("value");
                let ischilds = $(this).data("ischilds");
                console.log(ischilds);

                if(confirm("정말로 삭제하시겠습니까? ")){
                    $("#removeInput").val(id);
                    if(ischilds) {
                        if(confirm("하위 부서로 등록한 모든 부서를 삭제합니다.계속하시겠습니까?")){
                            $("#removeForm").submit();
                        }
                    }else {
                        $("#removeForm").submit();
                    }
                }
            })
        });
    </script>
</head>
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
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th class="text-center" style="width:60px;">No.</th>
                        <th>부서명</th>
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
                                        <@spring.formHiddenInput "department.id"/>
                                    </#if>
                                </td>
                                <td>
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
                                    <#assign ischilds = 'false' />
                                    <#if d.childDepartments?has_content>
                                        <#assign ischilds='true' />
                                    </#if>
                                    <#if action == "edit" && id == d.id>
                                        <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
                                        <a class="btn btn-sm btn-warning" href="?action=list"><i class="fa fa-times"></i> 취소</a>
                                    <#else>
                                        <a class="btn btn-sm btn-mint" href="/admin/department?action=edit&id=${d.id}#edit${d?index}"><i class="fa fa-pencil"> 수정</i></a>
                                        <button class="btn btn-sm btn-danger" id="deleteDept" data-value="${d.id}" data-ischilds="${ischilds}"><i class="fa fa-trash"> 삭제</i></button>
                                        <a class="btn btn-sm btn-info" href="/admin/department?action=subNew&pid=${d.id}#edit${d?index}"><i class="fa fa-plus-circle"> 부서 추가</i></a>
                                    </#if>
                                </td>
                            </tr>
                            <!-- parentID -->
                            <#if action == "subNew" && pid == d.id >
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
                                <tr class="bg-trans-dark">
                                    <td class="text-center"><i class="pli-turn-up-2"> </i></td>
                                    <td>
                                        <#if action == "subEdit" && id == cd.id && pid?has_content && pid == d.id>
                                            <@spring.formHiddenInput "department.parentDepartment.id" />
                                            <@spring.formHiddenInput "department.id" />
                                            <@spring.formInput "department.name" "class='form-control' placeholder='부서 이름'"/>
                                            <@spring.showErrors "" "color:red;"/>
                                        <#else>
                                            ${cd.name}
                                        </#if>
                                    </td>
                                    <td>
                                        <#if action == "subEdit" && id == cd.id && pid?has_content && pid == d.id>
                                            -
                                        <#elseif cd.createdDate?has_content>
                                            ${cd.createdDate?string("YYYY-MM-dd HH:mm:ss")}
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td>
                                        <#if action == "subEdit" && id == cd.id && pid?has_content && pid == d.id>
                                            -
                                        <#elseif cd.lastModifiedDate?has_content>
                                            ${cd.lastModifiedDate?string("YYYY-MM-dd HH:mm:ss")}
                                        <#else>
                                            -
                                        </#if>
                                    </td>
                                    <td class="text-center">
                                        <#assign checked = ''/>
                                        <#if (cd.isYn?has_content && cd.isYn == 'Y')>
                                            <#assign checked = " checked='checked'"/>
                                        </#if>

                                        <#if action == "subEdit" && id == cd.id && pid?has_content && pid == d.id>
                                            <@spring.formCheckbox "department.isYn" "value='Y' ${checked}" />
                                            <@spring.showErrors "" "color:red;"/>
                                        <#else>
                                            <span class="label label-${(cd.isYn?contains("Y"))?then('success', 'warning')}">${(cd.isYn?contains("Y"))?then('Use', 'Not Use')}</span>
                                        </#if>
                                    </td>
                                    <td style="color:#ffffff!important;">
                                        <#if action == "subEdit" && id == cd.id && pid?has_content && pid == d.id>
                                            <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
                                            <a class="btn btn-sm btn-warning" href="?action=list"><i class="fa fa-times"></i> 취소</a>
                                        <#else>
                                            <a class="btn btn-sm btn-mint" href="/admin/department?action=subEdit&id=${cd.id}&pid=${d.id}#edit${cd?index}"><i class="fa fa-pencil"> 수정</i></a>
                                            <button class="btn btn-sm btn-danger" id="deleteDept" data-value="${cd.id}"><i class="fa fa-trash"> 삭제</i></button>
                                        </#if>
                                    </td>
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
                            <td>
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
    <form name="f" id="removeForm" method="post">
        <input type="hidden" name="_method" value="delete"/>
        <input type="hidden" id="removeInput" name="id" />
    </form>

</div>
</body>
</html>