<#import "/spring.ftl" as spring />
<script>
    $(document).ready(function() {
        $("#sopWaiverApprovalForm\\.waiverStartDate,#sopWaiverApprovalForm\\.waiverEndDate").datepicker({format:'yyyy-mm-dd', todayBtn: "linked",
            autoclose: true,
            todayHighlight: true});
        $("#sopWaiverApprovalForm\\.deviatedSOPDocument\\.id").select2({width:"100%"});
    });
</script>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Project No.</td>
        <td><@spring.formInput "approval.sopWaiverApprovalForm.projectNo" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Protocol Title/No.</td>
        <td><@spring.formInput "approval.sopWaiverApprovalForm.protocolNo" "class='form-control' placeholder='Please enter ‘N/A’ if this section is not applicable.'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Waiver Start Date<br/><small>(DD-MMM-YYYY)</small></td>
        <td><@spring.formInput "approval.sopWaiverApprovalForm.waiverStartDate" "class='form-control' placeholder='This should be on or after request/approval date'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Waiver End Date<br/><small>(DD-MMM-YYYY)</small></td>
        <td><@spring.formInput "approval.sopWaiverApprovalForm.waiverEndDate" "class='form-control' placeholder='If an exact date is not confirmed, please specify an expected end point. e.g.) until the completion of 00-000 project.'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Waived SOP Doc. ID & Title/Version/Effective Date:<br/>
        <small>(DD-MMM-YYYY)</small></td>
        <td><@spring.formSingleSelect "approval.sopWaiverApprovalForm.deviatedSOPDocument.id" sopMap "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    </tbody>
</table>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Waiver Details</td>
        <td colspan="3"><@spring.formTextarea "approval.sopWaiverApprovalForm.deviationDetails" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    </tbody>
</table>
