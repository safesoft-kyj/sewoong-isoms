<#import "/spring.ftl" as spring />
<script>
    $(document).ready(function() {
        $("#sopDeviationReport\\.dateOfOccurrence,#sopDeviationReport\\.dateOfDiscovery,#sopDeviationReport\\.correctiveCompletionDate,#sopDeviationReport\\.preventiveCompletionDate").datepicker({format:'yyyy-mm-dd', todayBtn: "linked",
            autoclose: true,
            todayHighlight: true});
        $("#sopDeviationReport\\.deviatedSOPDocument\\.id").select2({width:"100%"});
    });
</script>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light">Project No.</td>
        <td><@spring.formInput "approval.sopDeviationReport.projectNo" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Protocol Title/No.</td>
        <td><@spring.formInput "approval.sopDeviationReport.protocolNo" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Date of Occurrence</td>
        <td><@spring.formInput "approval.sopDeviationReport.dateOfOccurrence" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Date of Discovery</td>
        <td><@spring.formInput "approval.sopDeviationReport.dateOfDiscovery" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">Deviated SOP Doc.</td>
        <td><@spring.formSingleSelect "approval.sopDeviationReport.deviatedSOPDocument.id" sopMap "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    </tbody>
</table>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light">Deviation Details</td>
        <td colspan="3"><@spring.formTextarea "approval.sopDeviationReport.deviationDetails" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td class="bg-gray-light">(Expected) Completion Date</td>
        <td><@spring.formInput "approval.sopDeviationReport.correctiveCompletionDate" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
        <td class="bg-gray-light">(Expected) Completion Date</td>
        <td><@spring.formInput "approval.sopDeviationReport.preventiveCompletionDate" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    <tr>
        <td colspan="2" class="bg-gray-light">Corrective Action</td><td colspan="2" class="bg-gray-light">Preventive Action</td>
    </tr>
    <tr>
        <td colspan="2"><@spring.formTextarea "approval.sopDeviationReport.correctiveAction" "class='form-control'"/><@spring.showErrors "<br/>" "color:red;"/></td>
        <td colspan="2"><@spring.formTextarea "approval.sopDeviationReport.preventiveAction" "class='form-control'"/><@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    </tbody>
</table>
