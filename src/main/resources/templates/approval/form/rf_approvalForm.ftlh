<#import "/spring.ftl" as spring />
<script>
    $(document).ready(function() {
        $("#rdApprovalForm\\.supersededVersion\\.id").chosen({width:'100%'});

        $("#rdApprovalForm\\.supersededVersion\\.id").change(function() {
            var rd = $(this).find("option:selected").text();
            var docName = rd.split("/");

            $('#rdApprovalForm\\.rdNo').val(docName[0]);
            $('#rdApprovalForm\\.title').val(docName[2]);
        });

        $("#rdApprovalForm\\.supersededVersion\\.id").trigger("change");

        $('#rdApprovalForm\\.effectiveDate').datepicker({format:'yyyy-mm-dd', todayBtn: "linked",
            autoclose: true,
            todayHighlight: true});
    });
</script>
<h5>RD Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light">Superseded version</td>
        <td>
            <@spring.formSingleSelect "approval.rdApprovalForm.supersededVersion.id" rdMap "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">New version</td>
        <td>
            <table class="table">
                <thead>
                <tr>
                    <th class="col-xs-3">No.</th>
                    <th class="col-xs-1">Version</th>
                    <th>Title</th>
                    <th class="col-xs-3">Effective Date</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>
                        <@spring.formInput "approval.rdApprovalForm.rdNo", "class='form-control' readonly='readonly'"/>
                    </td>
                    <td>
                        <@spring.formInput "approval.rdApprovalForm.version", "class='form-control'"/>
                        <@spring.showErrors "<br/>" "color:red;"/>
                    </td>
                    <td>
                        <@spring.formInput "approval.rdApprovalForm.title", "class='form-control' readonly='readonly'"/>
                    </td>
                    <td>
                        <@spring.formInput "approval.rdApprovalForm.effectiveDate", "class='form-control'"/>
                        <@spring.showErrors "<br/>" "color:red;"/>
                    </td>
                </tr>
                </tbody>
            </table>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Description of the revised contents</td>
        <td>
            <@spring.formTextarea "approval.rdApprovalForm.description" "rows='4' class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/>
        </td>
    </tr>

    </tbody>
</table>