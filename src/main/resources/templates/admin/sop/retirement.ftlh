<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#--<#import "/spring.ftl" as spring />-->
<#import "/common/pagination.ftl" as pagination />
<html>
<head>
    <title>ISO Management System > 관리자 > SOP Management</title>
    <script src="/static/plugins/bootbox/bootbox.min.js"></script>
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <script>
        $(document).ready(function() {
            $('input[name="retirementDate"]').datepicker({format:'yyyy-mm-dd', autoclose:true, clearBtn:true, todayBtn: "linked", todayHighlight: true})
                .change(function() {
                    var id = $(this).data("id");
                    $("#rdateBtn" + id).removeAttr("disabled");
                });

            $("button[name='rdateBtn']").click(function() {
                var id = $(this).data("id");
               if(!$("#rdate" + id).val()) {
                   alert('Retirement Date를 입력해 주세요.');
                   $("#rdate" + id).focus();
               } else {
                   var url = '/ajax' + location.pathname + '/setRetirementDate';
                   $.ajax({
                       url:url,
                       method:'post',
                       data:{'id':id,'retirementDate':$("#rdate" + id).val()},
                       success:function(res) {
                           if(res.result == 'success') {
                               alert('Retirement Date 가 적용 되었습니다.');
                               $("#rdateBtn" + res.id).attr("disabled", "disabled");
                           } else {
                               alert('Retirement Date 적용 중 오류가 발생 하였습니다.');
                           }
                       }
                   });
               }
            });
        });
    </script>
</head>
<body>
<form id="statusChangeForm" method="post">
    <input type="hidden" name="_method" value="PUT"/>
</form>

<div class="panel">
    <#--    <div class="panel-heading">-->
    <#--        <h3 class="panel-title">${status.label} ${type.name()}</h3>-->
    <#--    </div>-->

    <!--Data Table-->
    <!--===================================================-->
    <div class="panel-body">
        <div class="pad-btm form-inline">
            <div class="row">
                <div class="col-sm-6 table-toolbar-left">

                </div>
                <div class="col-sm-6 table-toolbar-right">
                </div>
            </div>
        </div>
        <@pagination.bind retirement/>
        <div class="table-responsive">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th class="text-center" style="width:70px;">No.</th>
<#--                    <th class="text-center">Document<br/>Type</th>-->
                    <th class="text-center">Doc Id.</th>
                    <th>Title</th>
                    <th class="text-center">Version</th>
<#--                    <th class="text-center">승인</th>-->
<#--                    <th class="text-center">승인일</th>-->
                    <th class="text-center">Retirement Date</th>
                </tr>
                </thead>
                <tbody>
                <#if retirement.content?has_content>
                    <#list retirement.content as r>
                        <tr>
                            <td class="text-center">${(retirement.totalElements - (retirement.size * retirement.number)) - r?index}</td>
<#--                            <td class="text-center">${r.documentType.name()}</td>-->
                            <td class="text-center">${r.document.docId}</td>
                            <td>${r.document.title}</td>
                            <td class="text-center">${r.version}</td>
<#--                            <td class="text-center">${r.retirementApprovalForm.approval.lastModifiedBy?default('')}</td>-->
<#--                            <td class="text-center">${r.retirementApprovalForm.approval.lastModifiedDate?string('yyyy-MM-dd')}</td>-->
                            <td class="text-center" style="width:240px;">
                                ${r.retirementDate?has_content?then(r.retirementDate?string('yyyy-MM-dd'), '-')}
<#--                                <div class="input-group">-->
<#--                                <input type="text" class="form-control" id="rdate${r.id}" name="retirementDate" data-id="${r.id}" value="${r.retirementDate?has_content?then(r.retirementDate?string('yyyy-MM-dd'), '')}"/>-->
<#--                                    <span class="input-group-btn">-->
<#--                                        <button class="btn btn-success" id="rdateBtn${r.id}" name="rdateBtn" data-id="${r.id}" type="button" disabled><i class="fa fa-check"></i></button>-->
<#--                                    </span>-->
<#--                                </div>-->
                            </td>
                        </tr>
                    </#list>
                <#else>
                    <tr>
                        <td colspan="7" class="text-center">No Data.</td>
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