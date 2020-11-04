<style>
    .modal-dialog.modal-fullsize {
        width: 1024px;
        height: 550px;
        margin: 3px;
        padding: 0;
    }
    .modal-content.modal-fullsize {
        height: auto;
        min-height: 550px;
        border-radius: 0;
    }
</style>
<div class="panel">
    <div class="panel-body">
        <#if users?has_content>
            <#list users as user>
<#--                <div class="panel panel-default panel-bordered col-xs-2">-->
<#--                    <div class="panel-body">-->
                        <button class="btn btn-sm btn-info" data-target="#binder-modal" data-toggle="modal" data-id="${user.username}" data-title="${user.engName?default(user.name)}"><i class="fa fa-file-pdf-o"></i> ${user.engName?default(user.name)}</button>
<#--                    </div>-->
<#--                </div>-->

            </#list>
        <#else>
            <div class="text-center"><h6><i class="fa fa-exclamation-circle"></i> 열람 가능한 Digital Binder 가 없습니다.</h6></div>
        </#if>
    </div>
</div>
    <script>
        $(document).ready(function() {
            $('#binder-modal').on('show.bs.modal', function (event) {
                var button = $(event.relatedTarget); // Button that triggered the modal
                var title = button.data('title'); // Extract info from data-* attributes
                var username = button.data('id'); // Extract info from data-* attributes
                var modal = $(this);
                modal.find('.modal-title').text(title);
                // var options = {
                //     pdfOpenParams: { toolbar: '0', statusbar: '0', messages: '0', scrollbar: '1', view:'FitH'}
                // };

                // var $container = $("#pdfViewer");
                $("#db_body").html("Digital Binder 정보를 로드 중입니다...");
                $.ajax({
                    url: "/ajax/digital-binder/" + username,
                    method: "GET",
                    timeout: 3600000,//1시간
                    data: { r : Math.random() },
                    dataType: "html"
                }).done(function(response) {
                    $("#db_body").html(response);
                });
                // PDFObject.embed("/common/download/trainingMatrix", $container, options);
            });
        });
    </script>
    <div class="modal fade" id="binder-modal" role="dialog" tabindex="-1" aria-labelledby="matrix-modal" aria-hidden="true">
        <div class="modal-dialog modal-fullsize">
            <div class="modal-content modal-fullsize">

                <!--Modal header-->
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                    <h4 class="modal-title"></h4>
                </div>

                <!--Modal body-->
                <div class="modal-body">
                    <div id="db_body" style="width:100% !important;min-height:550px !important;">
                    </div>

                    <!--Modal footer-->
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
<#--</div>-->
