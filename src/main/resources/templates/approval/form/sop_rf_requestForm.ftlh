<#import "/spring.ftl" as spring />
<script>
    $(document).ready(function() {
       $("#sopRfRequestForm\\.sopRevisionIds,#sopRfRequestForm\\.rdRevisionIds").chosen({width:'100%'});

       $("#sopRfRequestForm\\.sopRevision,#sopRfRequestForm\\.rdRevision").change(function() {
          var chkbox = $(this);

          if(chkbox.is(":checked")) {
              $("#"+chkbox.data("id")+"Div").show();
          } else {
              $("#"+chkbox.data("id")+"Div").hide();
          }
       });
       $("#sopRfRequestForm\\.newSOPDevelopment,#sopRfRequestForm\\.newRDDevelopment").change(function() {
          var chkbox = $(this);

          if(chkbox.is(":checked")) {
              $("#"+chkbox.data("id")+"DevelopmentDiv").show();
          } else {
              $("#"+chkbox.data("id")+"DevelopmentDiv").hide();
          }
       });

        $("#sopRfRequestForm\\.sopRevision,#sopRfRequestForm\\.rdRevision,#sopRfRequestForm\\.newSOPDevelopment,#sopRfRequestForm\\.newRDDevelopment").trigger("change");

        $(document).on("click", "button[name='addDevelopment']", function() {
            var data = $("#appForm").serialize();
            var type = $(this).data("type");
            data += "&_method=put&act=add&r=" + Math.random()+"&t=" + type;
            $.ajax({
                url:'/ajax/approval/box/requester/SOP_RD_Request_Form/development',
                method:'post',
                data:data,
                success:function(responseHtml) {
                    // console.log(res);
                    $("#" + type +"DevelopmentBody").html(responseHtml);
                }
            });
        });
        $(document).on("click", "button[name='removeDevelopment']", function() {
            var data = $("#appForm").serialize();
            var type = $(this).data("type");
            data += "&_method=put&act=remove&r=" + Math.random()+"&idx="+$(this).data("id")+"&t=" + type;;
            $.ajax({
                url:'/ajax/approval/box/requester/SOP_RD_Request_Form/development',
                method:'post',
                data:data,
                success:function(responseHtml) {
                    // console.log(res);
                    $("#" + type +"DevelopmentBody").html(responseHtml);
                }
            });
        });
    });


</script>
<style type="text/css">
    body .chosen-container-multi .chosen-choices li.search-choice, body .chosen-container .chosen-results li.highlighted {
        width:100% !important;
    }
</style>
<h5>Requester Information</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Name of Requester</td>
        <td><@spring.formInput "approval.sopRfRequestForm.nameOfRequester" "class='form-control' readonly='readonly'"/>
            <@spring.showErrors "<br/>" "color:red;"/>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Name of Team/Department</td>
        <td><@spring.formInput "approval.sopRfRequestForm.nameOfTeamDept" "class='form-control' readonly='readonly'"/>
            <@spring.showErrors "<br/>" "color:red;"/>
        </td>
    </tr>
    <tr>
        <td class="bg-gray-light" rowspan="4">Request for</td>
        <td>
            <label>
            <@spring.formCheckbox "approval.sopRfRequestForm.newSOPDevelopment" "data-id='sop'"/> New SOP Development
            </label>
            <div id="sopDevelopmentDiv">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>Document Id</th>
                    <th>Title</th>
                    <th style="width:80px">Version</th>
                    <th>#</th>
                </tr>
                </thead>
                <tbody id="sopDevelopmentBody">
                <#include "inc_development_sop.ftlh"/>
                </tbody>
            </table>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
            <@spring.formCheckbox "approval.sopRfRequestForm.newRDDevelopment" "data-id='rd'"/> New RD Development
            </label>
            <div id="rdDevelopmentDiv">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th>Document Id</th>
                        <th>Title</th>
                        <th style="width:80px">Version</th>
                        <th>#</th>
                    </tr>
                    </thead>
                    <tbody id="rdDevelopmentBody">
                    <#include "inc_development_rd.ftlh"/>
                    </tbody>
                </table>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
            <@spring.formCheckbox "approval.sopRfRequestForm.sopRevision" "data-id='sop'"/> SOP Revision
            </label>
            <div id="sopDiv">
                <@spring.formMultiSelect "approval.sopRfRequestForm.sopRevisionIds" sopMap "class='form-control'"/>
                <@spring.showErrors "<br/>" "color:red;"/></td>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            <label>
            <@spring.formCheckbox "approval.sopRfRequestForm.rdRevision" "data-id='rd'"/> RD Revision
            </label>
            <div><@spring.showErrors "<br/>" "color:red;"/></div>
            <div id="rdDiv">
                <div id="sopRevisionDiv">
                    <@spring.formMultiSelect "approval.sopRfRequestForm.rdRevisionIds" rdMap "class='form-control'"/>
                    <@spring.showErrors "<br/>" "color:red;"/></td>
        </div>
            </div>

        </td>
    </tr>
    <tr>
        <td class="bg-gray-light">Reason for the request</td>
        <td><@spring.formTextarea "approval.sopRfRequestForm.reasonForTheRequest" "rows='4' class='form-control' placeholder='If you select new SOP/RD development, please specify new SOP/RD title, purpose and the target date (expected effective date).
If you select SOP/RD revision, please specify the reason of revision and the target date (expected effective date).
For new RD development, please specify the SOP information (number and title) which is associated with the new RD.
'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>

    </tbody>
</table>
<h5>Requested by</h5>
<table class="table table-bordered">
    <tbody>
    <tr>
        <td class="bg-gray-light col-xs-3">Comments, if required</td>
        <td><@spring.formTextarea "approval.sopRfRequestForm.requestedByComments" "class='form-control'"/>
            <@spring.showErrors "<br/>" "color:red;"/></td>
    </tr>
    </tbody>
</table>