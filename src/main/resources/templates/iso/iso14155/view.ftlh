<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<html>
    <head>
        <title>ISO-MS :: ${iso.title}</title>
        <!--Bootbox Modals [ OPTIONAL ]-->
        <script src="/static/plugins/bootbox/bootbox.min.js"></script>

        <style>
            .modal-fullsize {
                width: 80%;
                height: 800px;
                margin: 3px;
                padding: 0;
            }

            #iso-html {
                height: 700px;
            }
        </style>

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
                var isoId = $(this).data("id");
                $.ajax({
                    url:'/ajax/iso-14155/' + isoId + '/send',
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
                        ${iso.title}
                    </h1>
                </div>

                <div class="row">
                    <div class="col-sm-7 toolbar-left">

                        <!--Sender Information-->
                        <div class="media">
                            <div class="media-body text-left">
                                <div class="text-bold"><i class="pli-user"></i> ${iso.createdBy?default('System')}</div>
                                <small class="text-muted"><i class="pli-pencil"></i> ${iso.createdDate}</small>
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
                        <button class="btn btn-default" id="sendEmailBtn" data-id="${iso.id}"><i class="fa fa-send-o"></i> 메일 전송</button>
                        </@security.authorize>
<#--                        </div>-->
                    </div>
                    <div class="col-sm-5 toolbar-right">
                        <!--Reply & forward buttons-->
                        <@security.authorize access="hasAnyAuthority('ADMIN')">
                        <a class="btn btn-default" href="javascript:goToURL('/iso-14155/${iso.id}/edit');"><i class="pli-pencil"></i> 수정</a>
                        <button class="btn btn-default" id="removeBtn"><i class="pli-trash icon-lg"></i> 삭제</button>
                        </@security.authorize>
                        <div class="btn-group btn-group">
                            <a class="btn btn-default" href="javascript:goToURL('/iso-14155');">
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
                    ${iso.content?no_esc}
                </div>
                <!--===================================================-->
                <!--End Message-->

                <!-- Attach Files-->
                <!--===================================================-->
                <#if iso.attachFiles?has_content>
                <div class="pad-ver">
                    <p class="text-main text-bold box-inline"><i class="psi-paperclip icon-fw"></i> Attachments <span>(${iso.attachFiles?size})</span></p>
<#--                    <a href="#" class="btn-link">Download all</a> | <a href="#" class="btn-link">View all images</a>-->

                    <ul class="mail-attach-list list-ov">
                        <#list iso.attachFiles as attachFile>
                        <li>
                            <a href="#iso-modal" data-toggle="modal" data-id="${iso.id}" data-file="${attachFile.id}" data-name="${attachFile.originalFileName}" class="thumbnail">
                                <#if attachFile.fileType?index_of('image') == 0>
                                <div class="mail-file-img">
                                    <img class="image-responsive" src="/iso-14155/${iso.id}/downloadFile/${attachFile.id}" alt="image">
                                </div>
                                <#else>
                                <div class="mail-file-icon">
                                    <i class="pli-file-download"></i>
                                </div>
                                </#if>
                                <div class="caption">
                                    <p class="text-main mar-no" style="overflow-x: hidden;">${attachFile.originalFileName}</p>
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

            <!-- ISO Viewer -->
            <div class="modal fade" id="iso-modal" role="dialog" tabindex="-1" aria-labelledby="iso-modal"
                 aria-hidden="true">
                <div class="modal-dialog modal-fullsize">
                    <div class="modal-content">

                        <!--Modal header-->
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                            <h4 class="modal-title">ISO 14155</h4>
                        </div>

                        <!--Modal body-->
                        <div class="modal-body">
                            <div class="table-responsive">
                                <div id="iso-html" style="" >내용을 불러오는 중입니다...</div>
                            </div>
                        </div>

                        <!--Modal footer-->
                        <div class="modal-footer">
                            <button data-dismiss="modal" class="btn btn-default" type="button"><i class="fa fa-times"></i> Close
                            </button>
                        </div>
                    </div>
                </div>
            </div>

            <#-- PDF Object -->
            <script src='/static/js/pdfjs/control/pdfobject.js'></script>
            <!-- ISO viewer end -->
            <script>
                $('#iso-modal').on('show.bs.modal', function (event) {
                    $("#iso-html").html('<string>내용을 불러오는 중입니다...</string>');
                    var button = $(event.relatedTarget); // Button that triggered the modal
                    var isoId = button.data('id');
                    var fileId = button.data('file');
                    var isoName = button.data('name');

                    isoName = isoName.substr(0, isoName.indexOf(".pdf"));
                    $(".modal-title").html(isoName);

                    var options = {
                        height: "800",
                        page: '1',
                        pdfOpenParams: {
                            view: 'FitV',
                            pagemode: 'thumbs',
                            search: 'lorem ipsum'
                        },
                        forcePDFJS: true, // 강제로 PDF Viewer를 실행시키는 옵션
                        PDFJS_URL: "/static/js/pdfjs/web/viewer.html" //PDF.js의 PDF viewer를 viewer로 지정.
                    };

                    //제일 처음 열리는 pdf파일 경로 설정.
                    PDFObject.embed("/iso-14155/" + isoId + "/downloadFile/" + fileId , "#iso-html", options);
                });
            </script>

        </div>
    </div>
</div>
</body>
</html>