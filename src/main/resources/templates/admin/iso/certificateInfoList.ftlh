<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#import "/spring.ftl" as spring />
<html>
<head>
    <title>Admin > User Management > ROLE 관리</title>
</head>
<body>

<div class="panel">
    <div class="panel-heading">
        <h3 class="panel-title">ISO Certificate Setting</h3>
        <div class="panel-control" style="float:left;">
            <ul class="nav nav-tabs">
                <li>
                    <a href="/admin/training/iso/training-certification">
                        ISO Cetificate</a>
                </li>
                <li class='active'>
                    <a href="/admin/training/iso/training-certification/info">
                        ISO Cetificate Setting</a>
                </li>
            </ul>
        </div>
    </div>
    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body" style="margin-top: 45px;">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">
                    <a class="btn btn-primary"
                       href="/admin/training/iso/training-certification/info?action=new#edit0">
                        <i class="pli-pencil"></i> ISO Cetificate Add</a>
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
            <form name="f" method="post" action="/admin/training/iso/training-certification/info">
                <input type="hidden" name="action" value="${action}">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th class="text-center" style="width:70px;">No.</th>
                        <#--                        <th>Prefix</th>-->
                        <#--                        <th>Suffix</th>-->
                        <th class="text-center">Manager</th>
                        <th class="text-center">Signature</th>
                        <th class="text-center">Active</th>
                        <th style="width:180px">#actions</th>
                    </tr>
                    </thead>
                    <tbody>

                    <#if infoList?has_content>
                        <#list infoList as info>
                            <tr>
                                <td class="text-center">${info?index + 1}
                                    <a name="edit${info?index}"></a>
                                    <#if action == "edit" && id == info.id>
                                        <@spring.formHiddenInput "info.id"/>
                                    </#if>
                                </td>
                                <#--                                <td>-->
                                <#--                                    <#if action == "edit" && id == info.id>-->
                                <#--                                        <@spring.formInput "info.prefix" "class='form-control'"/>-->
                                <#--                                        <@spring.showErrors "" "color:red;"/>-->
                                <#--                                    <#else>-->
                                <#--                                        ${info.prefix}-->
                                <#--                                    </#if>-->
                                <#--                                </td>-->
                                <#--                                <td>-->
                                <#--                                    <#if action == "edit" && id == info.id>-->
                                <#--                                        <@spring.formInput "info.suffix" "class='form-control'"/>-->
                                <#--                                        <@spring.showErrors "" "color:red;"/>-->
                                <#--                                    <#else>-->
                                <#--                                        ${info.suffix}-->
                                <#--                                    </#if>-->
                                <#--                                </td>-->
                                <td class="text-center">
                                    <#if action == "edit" && id == info.id>
                                        <@spring.formSingleSelect "info.userId" userMap "class='form-control'"/>
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        ${info.managerName}
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#if action == "edit" && id == info.id>
                                        -
                                    <#else>
                                        <img src="${info.base64signature}" style="width:50px; border: solid 1px"/>
                                    </#if>
                                </td>
                                <td class="text-center">
                                    <#if action == "edit" && id == info.id>
                                        <@spring.formCheckbox "info.active" "class='form-checkbox'"/><br />
                                        <@spring.showErrors "" "color:red;"/>
                                    <#else>
                                        <span class="label label-${info.active?then('success', 'warning')}">${info.active?then('Y', 'N')}</span>
                                    </#if>
                                </td>
                                <td>
                                    <#if action == "edit" && id == info.id>
                                        <button class="btn btn-sm btn-primary" type="submit"><i
                                                    class="fa fa-check"></i> 저장
                                        </button>
                                        <a class="btn btn-sm btn-warning" href="?action=list"><i
                                                    class="fa fa-times"></i> 취소</a>
                                    <#else>
                                        <a class="btn btn-sm btn-mint"
                                           href="/admin/training/iso/training-certification/info?action=edit&id=${info.id}#edit${info?index}"><i
                                                    class="fa fa-pencil"> 수정</i></a>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                    <#else>
                        <tr>
                            <td colspan="7" class="text-center">등록된 Certificate Info가 없습니다.</td>
                        </tr>
                    </#if>
                    <#if action == "new">
                        <tr>
                            <td class="text-center">#<a name="edit0"></a></td>
                            <#--                            <td>-->
                            <#--                                <@spring.formInput "info.prefix" "class='form-control'"/>-->
                            <#--                                <@spring.showErrors "" "color:red;"/></td>-->
                            <#--                            <td>-->
                            <#--                                <@spring.formInput "info.suffix" "class='form-control'"/>-->
                            <#--                                <@spring.showErrors "" "color:red;"/></td>-->
                            <td>
                                <@spring.formSingleSelect "info.userId" userMap "class='form-control'"/>
                                <@spring.showErrors "" "color:red;"/>
                            </td>
                            <td class="text-center">
                                -
                            </td>
                            <td class="text-center">
                                <@spring.formCheckbox "info.active" "checked"/><br />
                                <@spring.showErrors "" "color:red;"/>
                            </td>
                            <td>
                                <button class="btn btn-sm btn-primary" type="submit"><i class="fa fa-check"></i>
                                    저장
                                </button>
                                <a class="btn btn-sm btn-warning" href="?action=list"><i
                                            class="fa fa-times"></i> 취소</a>
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
</div><!-- end panel-->
</body>
</html>