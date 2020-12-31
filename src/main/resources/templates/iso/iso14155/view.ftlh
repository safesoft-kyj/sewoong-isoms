<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
    <head>
        <title>e-SOP :: ${notice.title}</title>
        <!--Bootbox Modals [ OPTIONAL ]-->
        <script src="/static/plugins/bootbox/bootbox.min.js"></script>
    </head>
<body>


<script>
    // function preview_print(){
    //     var OLECMDID = 7;
    //     var PROMPT = 1;
    //     var WebBrowser = '<OBJECT ID="WebBrowser1" WIDTH=0 HEIGHT=0 CLASSID="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2"></OBJECT>';
    //
    //
    //     document.body.insertAdjacentHTML('beforeEnd', WebBrowser);
    //     WebBrowser1.ExecWB( OLECMDID, PROMPT);
    // }

    function fnPrint() {
        // var agent = navigator.userAgent.toLowerCase();
        // if(agent.indexOf("trident") > 0 || agent.indexOf("msie") > 0 ){
        //     preview_print();
        // } else {
            window.print();
        // }
    }

    $(document).ready(function() {
        $('#removeBtn').on('click', function() {
            bootbox.confirm("정말로 삭제 하시겠습니까?", function (result) {
                if (result) {
                    $("#removeForm").submit();
                } else {
                    // $.niftyNoty({
                    //     type: 'danger',
                    //     icon: 'pli-cross icon-2x',
                    //     message: 'User declined dialog.',
                    //     container: 'floating',
                    //     timer: 5000
                    // });
                }

            });
        });

        $("#sendEmailBtn").click(function() {
            if(confirm('알림 메일을 전송 하시겠습니까?')) {
                var noticeId = $(this).data("id");
                $.ajax({
                    url:'/ajax/notice/' + noticeId + '/send',
                    data:{r:Math.random()},
                    success:function(r) {
                        // alert(r);
                        // $("#sendEmailBtn").hide();
                        alert('이메일 전송 요청이 완료 되었습니다.');
                    }
                })
            }
        });
    });


</script>
<form id="removeForm" method="post">
    <input type="hidden" name="_method" value="delete">
</form>
<div class="panel">
    <div class="panel-body">
        <div class="fixed-fluid">

            <div class="fluid">

                <!-- VIEW MESSAGE -->
                <!--===================================================-->

                <div class="mar-btm pad-btm bord-btm">
                    <h1 class="page-header text-overflow">
                        ${notice.title}
                    </h1>
                </div>

                <div class="row">
                    <div class="col-sm-7 toolbar-left">

                        <!--Sender Information-->
                        <div class="media">
                            <div class="media-body text-left">
                                <div class="text-bold"><i class="pli-user"></i> ${notice.createdBy?default('System')}</div>
                                <small class="text-muted"><i class="pli-pencil"></i> ${notice.createdDate}</small>
                            </div>
                        </div>
                    </div>
                    <div class="col-sm-5 toolbar-right">

                        <!--Details Information-->
<#--                        <p class="mar-no"><small class="text-muted">${notice.createdDate}</small></p>-->
<#--                        <a href="#">-->
<#--                            <strong>Holiday.zip</strong>-->
<#--                            <i class="psi-paperclip icon-lg icon-fw"></i>-->
<#--                        </a>-->
                    </div>
                </div>
                <div class="row pad-top hidden-print">
                    <div class="col-sm-7 toolbar-left">

                        <!--Mail toolbar-->
                        <button class="btn btn-default" type="button" onclick="fnPrint();"><i class="pli-printer icon-lg"></i></button>
<#--                        <div class="btn-group btn-group">-->
                        <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <button class="btn btn-default" id="sendEmailBtn" data-id="${notice.id}"><i class="fa fa-send-o"></i> 메일 전송</button>
                        </@security.authorize>
<#--                        </div>-->
                    </div>
                    <div class="col-sm-5 toolbar-right">
                        <!--Reply & forward buttons-->
                        <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <a class="btn btn-default" href="javascript:goToURL('/notice/${notice.id}/edit');"><i class="pli-pencil"></i> 수정</a>
                        <button class="btn btn-default" id="removeBtn"><i class="pli-trash icon-lg"></i> 삭제</button>
                        </@security.authorize>
                        <div class="btn-group btn-group">
                            <a class="btn btn-default" href="javascript:goToURL('/notice');">
                                <i class="psi-list-view"></i> 목록
                            </a>
<#--                            <a class="btn btn-default" href="#">-->
<#--                                <i class="psi-right-4"></i>-->
<#--                            </a>-->
                        </div>
                    </div>
                </div>

                <!--Message-->
                <!--===================================================-->
                <div class="mail-message">
                    ${notice.content?no_esc}
                </div>
                <!--===================================================-->
                <!--End Message-->

                <!-- Attach Files-->
                <!--===================================================-->
                <#if notice.attachFiles?has_content>
                <div class="pad-ver">
                    <p class="text-main text-bold box-inline"><i class="psi-paperclip icon-fw"></i> Attachments <span>(${notice.attachFiles?size})</span></p>
<#--                    <a href="#" class="btn-link">Download all</a> | <a href="#" class="btn-link">View all images</a>-->

                    <ul class="mail-attach-list list-ov">
                        <#list notice.attachFiles as attachFile>
                        <li>
                            <a href="/notice/${notice.id}/downloadFile/${attachFile.id}" class="thumbnail">
                                <#if attachFile.fileType?index_of('image') == 0>
                                <div class="mail-file-img">
                                    <img class="image-responsive" src="/notice/${notice.id}/downloadFile/${attachFile.id}" alt="image">
                                </div>
                                <#else>
                                <div class="mail-file-icon">
                                    <i class="pli-file-download"></i>
                                </div>
                                </#if>
                                <div class="caption">
                                    <p class="text-main mar-no">${attachFile.originalFileName}</p>
                                    <small class="text-muted">Added: ${attachFile.createdDate}</small>
                                </div>
                            </a>
                        </li>
                        </#list>
                    </ul>
                </div>
                </#if>
                <!--===================================================-->
                <!-- End Attach Files-->

            </div>

        </div>
    </div>
</div>
</body>
</html>