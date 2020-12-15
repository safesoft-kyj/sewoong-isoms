<!DOCTYPE html>
<html lang="en">
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

    <style>
        .panel{
            position:absolute;
            top:10%;
            left:30%;
            border: solid 1px gray;
        }

        .form-group{
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
                clearBtn: true,
                autoclose: true,
                todayHighlight: true,
            }).on('hide.bs.modal', function (e) {
                e.stopPropagation(); //datepicker가 close될 때, sign-up-modal까지의 hide.bs.modal Event의 전파를 막음.
            });


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
<!-- Sign Up Page -->
<div class="panel">
    <!--Modal header-->
    <div class="panel-heading">
        <button type="button" class="close" data-dismiss="modal"><i class="pci-cross pci-circle"></i></button>
        <h4 class="modal-title">Sign Up</h4>
    </div>
    <form action="/signUp" method="POST" class="register-form" id="signup-form">
        <!--Modal body-->
        <div class="panel-body">
            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="username">ID* : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="text" name="username" id="username">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="password">Password* : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="password" name="password" id="password"
                           data-bv-field="password">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="confirmPassword">P/W Confirm*
                    : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="password" name="confirmPassword"
                           id="confirmPassword" data-bv-field="confirmPassword">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="name">Name(KOR)* : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="text" name="name" id="name">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="engName">Name(ENG) : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="text" name="engName" id="engName">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="email">E-mail* : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="email" name="email" id="email">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="email">phone : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="text" name="phone" id="phone">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-3 signup-label control-label" for="email">Birth Date : </label>
                <div class="col-sm-9">
                    <input class="form-control" type="text" name="birthDate" id="birthDate">
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
</body>
</html>