<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>e-SOP > SOP > Category 관리</title>
    <script>
        $(document).ready(function(){

            //삭제 버튼 동적 이벤트 할당.
            $(document).on("click", "#deleteBtn", function(){
                let categoryName = $(this).data("value");
                let res = confirm("정말로 [" + categoryName + "]을(를) 삭제하시겠습니까?");
                if(res) {
                    console.log("삭제");
                }

            });
        })
    </script>

</head>
<body>


<div class="panel">
<#--    <div class="panel-heading">-->
<#--        <h3 class="panel-title">Category List</h3>-->
<#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <a class="btn btn-primary" href="/admin/sop/category?action=new#edit0"><i class="pli-pencil"></i> Category 추가</a>
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
            <form name="f" method="post" action="/admin/sop/category">
                <input type="hidden" name="action" value="${action}">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
                    <th>Category ID.</th>
                    <th>Category Name</th>
                    <th class="text-center">#actions</th>
                </tr>
                </thead>
                <tbody>

                <#if CategoryList?has_content>
                    <#list CategoryList as Category>
                        <tr>
                            <td class="text-center">${category?index + 1}
                                <a name="edit${category?index}"></a>
                                <#if action == "edit" && id == category.id>
                                    <@spring.formHiddenInput "category.id"/>
                                </#if>
                            </td>
                            <td>
                                <#if action == "edit" && id == category.id>
                                    <#assign readonly = category.readonly?then(" readonly='readonly'", "")/>
                                <@spring.formInput "category.shortName" "class='form-control' ${readonly}"/>
                                <#else>
                                ${category.shortName}
                                </#if>
                            </td>
                            <td>
                                <#if action == "edit" && id == category.id>
                                    <@spring.formInput "category.name" "class='form-control'"/>
                                <#else>
                                    ${category.name}
                                </#if>
                            </td>
                            <td class="text-center">
                                <#if action == "edit" && id == category.id>
                                    <button class="btn btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
                                <#else>
                                    <a class="btn btn-sm btn-mint" href="/admin/sop/category?action=edit&id=${category.id}#edit${category?index}"><i class="fa fa-pencil"> 수정</i></a>
                                    <button class="btn btn-sm btn-danger" data-value="${category.name}" type="button" id="deleteBtn"><i class="fa fa-trash"> 삭제</i></button>
                                </#if>
                            </td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="4" class="text-center">등록된 카테고리가 존재하지 않습니다.</td>
                    </tr>
                </#if>
                    <#if action == "new">
                    <tr>
                        <td class="text-center">#<a name="edit0"></a></td>
                        <td>
                            <@spring.formInput "category.shortName" "class='form-control' placeholder='Short Name'"/>
                            <@spring.showErrors "" "color:red;"/></td>
                        <td>
                            <@spring.formInput "category.name" "class='form-control' placeholder='Name'"/>
                            <@spring.showErrors "" "color:red;"/></td>
                        <td class="text-center">
                            <button class="btn btn-primary" type="submit"><i class="fa fa-check"></i> 저장</button>
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