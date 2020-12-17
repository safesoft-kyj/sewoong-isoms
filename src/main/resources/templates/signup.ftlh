<#import "/spring.ftl" as spring />
<html>
<head>
    <meta charset="UTF-8">
    <title>CAUH ISO Management System > Sign Up</title>

    <!--Premium Icons [ OPTIONAL ]-->
    <link href="/static/premium/icon-sets/icons/line-icons/premium-line-icons.min.css" rel="stylesheet">
    <!-- Font Icon -->
    <link rel="stylesheet" href="/static/material-icon/css/material-design-iconic-font.min.css" type="text/css">

    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="/static/css/bootstrap.min.css" rel="stylesheet">
    <!-- Bootstrap DatePicker -->
    <link href="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.css" rel="stylesheet">
    <!-- select2 -->
    <link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">

    <!-- Main css -->
    <#--    <link rel="stylesheet" href="http://lms.dtnsm.com/default/login/css/style.css">-->
    <!-- JS -->
    <script src="/static/js/jquery.min.js"></script>
    <!--BootstrapJS [ RECOMMENDED ]-->
    <script src="/static/js/bootstrap.min.js"></script>
    <!--Bootstrap Validator -->
    <script src="/static/plugins/bootstrap-validator/bootstrapValidator.js"></script>
    <!-- Bootstrap DatePicker -->
    <script src="/static/plugins/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
    <!-- select2 -->
    <script src="/static/plugins/select2/js/select2.min.js"></script>

    <style>
        .form-group {
            margin-bottom: 10px;
        }

    </style>

    <script>
        $(document).ready(function () {

            //SignUp Validation Setting
            signUpValidation();

            $("#birthDate").datepicker({
                format: "yyyy-mm-dd",
                todayBtn: "linked",
                endDate: new Date(),
                clearBtn: true,
                autoclose: true,
                todayHighlight: true,
            }).on('hide.bs.modal', function (e) {
                e.stopPropagation(); //datepicker가 close될 때, sign-up-modal까지의 hide.bs.modal Event의 전파를 막음.
            });


            $('#jdIds').select2({width : '100%'});
        })

        function signUpValidation() {
            //Sign Up Setup
            $("#signup-form").bootstrapValidator({
                message: 'This value is not valid',
                feedbackIcons: {
                    valid: 'pli-yes text-success',
                    invalid: 'pli-close',
                    validating: 'pli-refresh'
                },
                fields: {
                    username: {
                        validators: {
                            remote: {
                                message: '> The id is already used',
                                url: '/signUp/ajax/validation',
                                type: 'POST',
                                data: {
                                    type: "username",
                                },
                                name: 'keyword' // 입력된 데이터 name을 keyword로 한다.
                            },
                            regexp: {
                                regexp: /^[a-zA-Z\d*]{5,20}$/,
                                message: '> The id length must 5 - 20 (alphabetical, number)'
                            },
                            notEmpty: {
                                message: '> The id is required'
                            }
                        }
                    },
                    name: {
                        validators: {
                            notEmpty: {
                                message: '> The name is required'
                            }
                        }
                    },
                    // engName: {
                    //     message: '> The engName is not valid',
                    //     validators: {
                    //         notEmpty: {
                    //             message: '> The engName is required'
                    //         }
                    //     }
                    // },
                    password: {
                        validators: {
                            notEmpty: {
                                message: '> The password is required and can\'t be empty'
                            },
                            identical: {
                                field: 'confirmPassword',
                                message: '> The password and its P/W Confirm are not the same'
                            },
                            regexp: {
                                //최소 8자, 대문자 하나 이상, 소문자 하나, 숫자 하나 및 특수문자 하나 이상
                                regexp: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/,
                                message: '> The password can over than 8 length consist of upper/lower alphabetical, number, dot and special character'
                            }
                        }
                    },
                    confirmPassword: {
                        validators: {
                            notEmpty: {
                                message: '> The P/W Confirm is required and can\'t be empty'
                            },
                            identical: {
                                field: 'password',
                                message: '> The password and its P/W Confirm are not the same'
                            },
                            regexp: {
                                //최소 8자, 대문자 하나 이상, 소문자 하나, 숫자 하나 및 특수문자 하나 이상
                                regexp: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[$@$!%*?&])[A-Za-z\d$@$!%*?&]{8,}/,
                                message: '> The password can over than 8 length consist of upper/lower alphabetical, number, dot and special character'
                            }
                        }
                    },
                    email: {
                        validators: {
                            remote: {
                                message: '> The email is already used',
                                url: '/signUp/ajax/validation',
                                type: 'POST',
                                data: {
                                    type: "email",
                                },
                                name: 'keyword' // 입력된 데이터 name을 keyword로 한다.
                            },
                            notEmpty: {
                                message: '> The email is required and can\'t be empty'
                            },
                            emailAddress: {
                                message: '> Please enter a valid email address'
                            }
                        }
                    },
                    phone: {
                        validators: {
                            regexp: {
                                regexp: /^[0-9]{3}[-]+[0-9]{4}[-]+[0-9]{4}$/,
                                message: '> The phone number is not valid (000-0000-0000)'
                            }
                        }
                    },
                    jdIds: {
                        validators: {
                            notEmpty: {
                                message: '> The job descriptions are required and can\'t be empty'
                            }
                        }
                    }
                }
            }).on('success.field.bv', function (e, data) {
                // $(e.target)  --> The field element
                // data.bv      --> The BootstrapValidator instance
                // data.field   --> The field name
                // data.element --> The field element
                var $parent = data.element.parents('.form-group');

                // Remove the has-success class
                $parent.removeClass('has-success');
            });
        }
    </script>
</head>
<body class="main" style="background-color: white">
<div class="container">
    <!-- Sign Up Page -->
    <div class="panel panel-success">
        <!--Modal header-->
        <div class="panel-heading">
            <h4 class="panel-title">ISO Management System > Sign Up</h4>
        </div>
        <form action="/signUp" method="POST" class="register-form" id="signup-form">
            <!--Modal body-->
            <div class="panel-body">
                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="username">ID* : </label>
                            <@spring.formInput "account.username" "class='form-control'" />
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="email">E-mail* : </label>
                            <@spring.formInput "account.email" "class='form-control'" "email"/>
                            <@spring.showErrors "" "color:red;"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="password">Password* : </label>
                            <@spring.formInput "account.password" "class='form-control'" "password" />
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="confirmPassword">P/W Confirm* : </label>
                            <input class="form-control" type="password" name="confirmPassword" id="confirmPassword">
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="name">Name(KOR)* : </label>
                            <@spring.formInput "account.name" "class='form-control'" />
                            <@spring.showErrors "" "color:red;"/>
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="engName">Name(ENG) : </label>
                            <@spring.formInput "account.engName" "class='form-control'" />
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="email">Birth Date : </label>
                            <@spring.formInput "account.birthDate" "class='form-control'" />
                            <@spring.showErrors "" "color:red;"/>
                        </div>
                    </div>
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label" for="email">phone : </label>
                            <@spring.formInput "account.phone" "class='form-control'" />
                            <@spring.showErrors "" "color:red;"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label">Department* : </label>
                            <@spring.formSingleSelect "account.department" departmentMap "class='form-control'"/>
                            <@spring.showErrors "" "color:red;"/>
                        </div>
                    </div>

                    <div class="col-sm-6">
                        <div class="form-group">
                            <label class="control-label">Job Descriptions* : </label>
                            <@spring.formMultiSelect "account.jdIds" jobDescriptionMap "class='form-control'"/>
                            <@spring.showErrors "" "color:red;"/>
                        </div>
                    </div>
                </div>
            </div>
            <!--Modal footer-->
            <div class="panel-footer">
                <button class="btn btn-default" id="requestBtn" type="submit"><i class="pli-yes"></i>Request</button>
                <a data-dismiss="modal" class="btn btn-default" href="/login"><i class="pli-arrow-left-2"></i> Back</a>
            </div>
        </form>
    </div>
    <!-- Sign Up Page -->
</div>
</body>
</html>