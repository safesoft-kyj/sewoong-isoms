<div class="modal fade" id="approval-modal" role="dialog" tabindex="-1" aria-labelledby="approval-modal" aria-hidden="true">
    <div class="modal-dialog modal-fullsize">
        <div class="modal-content">

            <!--Modal header-->
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
                <h4 class="modal-title">SOP/ISO & Training Log Disclosure Request Form</h4>
            </div>

            <!--Modal body-->
            <div class="modal-body">
            </div>

            <!--Modal footer-->
            <div class="modal-footer">
                <div class="row">
                    <div class="col-xs-6 text-left">
                    </div>
                    <div class="col-xs-6">
                        <button data-dismiss="modal" class="btn btn-default" type="button"><i class="fa fa-times"></i> Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<style>
    .modal-dialog.modal-fullsize {
        width: 1200px;
        height: 700px;
        margin: 3px;
        padding: 0;
    }
    .modal-content.modal-fullsize {
        height: auto;
        min-height: 700px;
        border-radius: 0;
    }
</style>
<script>
    $(document).ready(function() {
        $('#approval-modal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget); // Button that triggered the modal
            var approvalId = button.data('id'); // Extract info from data-* attributes
            var title = button.data('title'); // Extract info from data-* attributes
            var modal = $(this);
            modal.find('.modal-title').html(title);
            modal.find('.modal-body').html("Loading...");
            $.ajax({
                url:'/ajax/admin/approval/approved/'+approvalId+'/v',
                data:{r:Math.random()},
                success:function(responseText) {
                    // console.log(res);
                    modal.find('.modal-body').html(responseText);

                    setTimeout(function() {
                        modal.find('.panel-heading').hide();
                        modal.find('.panel-footer').hide();
                    }, 5);
                }
            });
        });
    });
</script>