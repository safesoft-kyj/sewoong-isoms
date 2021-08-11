<%--
  Created by IntelliJ IDEA.
  User: JHSEO
  Date: 2019-02-21
  Time: 오전 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="user"/>
<%--<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>--%>

<spring:eval expression="@environment.getProperty('site.session-timer')" var="timer" />

<!--STYLESHEET-->
<!--=================================================-->

<!--Pace - Page Load Progress Par [OPTIONAL]-->
<link href="/static/plugins/pace/themes/pace-theme-corner-indicator.css" rel="stylesheet">
<script src="/static/plugins/pace/pace.min.js"></script>

<!--Open Sans Font [ OPTIONAL ] -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin" rel="stylesheet">

<!--Bootstrap Stylesheet [ REQUIRED ]-->
<link href="/static/css/bootstrap.min.css" rel="stylesheet">

<!--Nifty Stylesheet [ REQUIRED ]-->
<link href="/static/css/nifty.min.css" rel="stylesheet">

<!--Premium Icons [ OPTIONAL ]-->
<link href="/static/premium/icon-sets/icons/line-icons/premium-line-icons.min.css" rel="stylesheet">
<link href="/static/premium/icon-sets/icons/solid-icons/premium-solid-icons.min.css" rel="stylesheet">

<!--Spinkit [ OPTIONAL ]-->
<link href="/static/plugins/spinkit/css/spinkit.min.css" rel="stylesheet">

<!-- No Print -->
<%--<link href="/static/css/no_print.css" rel="stylesheet">--%>

<!--JAVASCRIPT-->
<!--=================================================-->

<!--jQuery [ REQUIRED ]-->
<script src="/static/js/jquery.min.js"></script>

<script src="/static/plugins/jquery-ui/jquery-ui.min.js"></script>


<!--BootstrapJS [ RECOMMENDED ]-->
<script src="/static/js/bootstrap.min.js"></script>


<!--Nifty Admin [ RECOMMENDED ]-->
<script src="/static/js/nifty.min.js"></script>


<!--=================================================-->

<!--Plugin===========================================-->
<!-- select2 -->
<link href="/static/plugins/select2/css/select2.min.css" rel="stylesheet">
<!-- select2 -->
<script src="/static/plugins/select2/js/select2.min.js"></script>
<!--=================================================-->

<link href="/static/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<c:choose>
    <c:when test="${not empty param['admin']}">
        <link href="/static/css/themes/type-c/theme-ocean.min.css" rel="stylesheet">
<%--        <link href="/static/css/themes/type-c/theme-lime.min.css" rel="stylesheet">--%>
    </c:when>
    <c:otherwise>
        <link href="/static/css/themes/type-d/theme-dark.min.css" rel="stylesheet">
        <%--        <link href="/static/css/themes/type-c/theme-navy.min.css" rel="stylesheet">--%>
    </c:otherwise>
</c:choose>
<%--<link href="/static/css/themes/type-c/theme-navy.min.css" rel="stylesheet">--%>
<!--=================================================

REQUIRED
You must include this in your project.


RECOMMENDED
This Category must be included but you may modify which plugins or components which should be included in your
project.


OPTIONAL
Optional plugins. You may choose whether to include it in your project or not.


Detailed information and more samples can be found in the document.

=================================================-->
<script>
    var intervalId;
    var duration = 1;

    function dailyMissionTimer() {

        var timer = duration * ${timer};
        var hours, minutes, seconds;

        intervalId = setInterval(function () {
            hours = parseInt(timer / 3600, 10);
            minutes = parseInt(timer / 60 % 60, 10);
            seconds = parseInt(timer % 60, 10);

            if (minutes == 1 && seconds == 0) {
                // $("#timer-modal").modal('show');
                // $(".modal-backdrop:eq(0)").css("z-index", "9996");
                // $(".modal").css("z-index", "9997");
                // $(".modal-backdrop:eq(1)").css("z-index", "9998");
                // $("#timer-modal").css("z-index", "9999");

                // bootbox.dialog({
                //     title: "Your session is about to expired!",
                //     message: '<div class="media">' +
                //         '<div class="media-body">' +
                //         '<p class="text-semibold text-main">You will be logged out in <span id="m-timer-sec" class="text-semibold text-warning">60</span> seconds.</p>' +
                //         '<p class="text-info">Do you want to stay signed in?</p>' +
                //         '</div></div>',
                //     buttons: {
                //         confirm: {
                //             label: "Continue Session",
                //             className: "btn-success",
                //             callback: function() {
                //                 resetTimer();
                //             }
                //         }
                //     }
                // });

                $.niftyNoty({
                    type: 'info',
                    container: 'floating',
                    html: '<h4 class="alert-title">Your session is about to expired!</h4>' +
                        '<p class="alert-message">You will be logged out in <span id="m-timer-sec" class="text-semibold text-danger">60</span> seconds.<br/' +
                        '<br/>Do you want to stay signed in?</p>' +
                        '<div class="mar-top"><button name="continue-session-btn" class="btn btn-primary" type="button">Continue Session</button></div>',
                    closeBtn: true,
                    floating: {
                        position: 'top-right',
                        animationIn: 'jelly',
                        animationOut: 'fadeOut'
                    },
                    focus: true,
                    timer: 0
                });
            }

            if (minutes > 0 && seconds == 0) {
                $.ajax({
                    url: '/ajax/keep-session',
                    method: 'get',
                    data: {r: Math.random()},
                    success: function (res) {
                    }
                });
            }

            hours = hours < 10 ? "0" + hours : hours;
            minutes = minutes < 10 ? "0" + minutes : minutes;
            seconds = seconds < 10 ? "0" + seconds : seconds;

            // $('#time-hour').text(hours);
            $('#time-min').text(minutes);
            $('#time-sec,#m-timer-sec').text(seconds);

            if (--timer < 0) {
                timer = 0;
                clearInterval(intervalId);
                sessionTimeout();
            }
        }, 1000);
    }

    function sessionTimeout() {
        alert('Your session has expired.');
        document.location.replace('/logout');
    }

    function resetTimer() {
        $("div.alert-wrap button.close").trigger('click');
        clearInterval(intervalId);
        dailyMissionTimer();
    }

    $(document).ready(function () {
        $(document).on("click", "button[name='continue-session-btn']", function (e) {
            resetTimer();
            // $("#timer-modal").modal('hide');
        });
    });

    $(function () {
        var pathname = location.pathname;
        activeLink(pathname);

        $.ajaxSetup({
            error: function (x, status, error) {
                if (x.status == 401 || x.status == 403) {
                    if (unloadMessage) {
                        $(window).off("beforeunload", unloadMessage);
                    }

                    alert("Sorry, your session has expired. Please login again to continue");
                    window.location.href = "/login?invalidSession";
                } else {
                    alert("An error occurred: " + status + "nError: " + error);
                }
            }
        });
    });
    var recursionCount = 0;

    function activeLink(pathname) {
        recursionCount++;
        if (recursionCount == 5) {
            return;
        }
        var $a = $("#mainnav-menu").find("a[href='" + pathname + "']");
        var li = $a.parents("li");

        if (li.length == 0) {
            pathname = pathname.substring(0, pathname.lastIndexOf("/"));
            return activeLink(pathname);
        } else {
            let upperMenus = li.find("ul.collapse");
            $.each(upperMenus, function (idx, menu) {
                if ($(menu).find("a[href='" + pathname + "']").length > 0) {
                    $(menu).addClass('in');
                }
            });
        }
        // } else {
        //     li.find("ul.collapse").addClass('in');
        // }

        recursionCount = 0;
        li.addClass('active-sub');


        var $a = li.find("a");
        var subMenus = $a.find("span.menu-title");

        setPageTitle($a.html());
        setNav(pathname, subMenus);
    }

    function setPageTitle(pageTitle) {
        $("#page-title").html("<h1 class=\"page-header text-overflow\">" + pageTitle + "</h1>");
    }

    function setNav(pathname, pageTitle) {
        $("#breadcrumb").append("<li><a href=\"/\"><i class=\"pli-home\"></i></a></li>");
        if (pageTitle) {
            $.each(pageTitle, function (idx, title) {
                let check = $(title).parent().next().find("a[href='" + pathname + "']");
                if (check.length > 0) {
                    $("#breadcrumb").append("<li>" + $(title).text() + "</li>");
                }
            })
        }
        var $a = $("#mainnav-menu").find("a[href='" + pathname + "']");
        $("#breadcrumb").append("<li>" + $a.text() + "</li>");
        // }
    }

    function goToURL(url) {
        window.location.href = url + location.search;
    }

    // var camelize = function camelize(str) {
    //     return str.replace(/\W+(.)/g, function(match, chr) {
    //         return chr.toUpperCase();
    //     });
    // }

    <sec:authorize var="isAdmin" access="hasAnyAuthority('ADMIN')"/>
    <c:if test="${empty param['admin'] or isAdmin eq false}">
    var pathname = location.pathname;
    // var paths = pathname.split("/");

    console.log("Path : ", pathname);
    console.log("isAdmin : ", "${isAdmin}");


    if(!((pathname.indexOf("/notice/") != -1 || pathname.indexOf("/iso-14155/") != -1) && ${isAdmin})) {
        $(document).ready(function () {
            $(document).bind("contextmenu", function (e) {
                return false;
            });
        });
        $(document).bind('selectstart', function () {
            return false;
        });
        $(document).bind('dragstart', function () {
            return false;
        });
    }
    </c:if>
</script>
<c:if test="${not empty message}">
    <script>
        $(document).ready(function () {
            let type = 'info'
            <c:if test="${not empty messageType}">
            type = '${messageType}';
            </c:if>

            $.niftyNoty({
                type: type,
                container: 'floating',
                // html: alert_content[alert_layout].type,
                message: '${message}',
                closeBtn: true,
                floating: {
                    position: 'top-right',
                    animationIn: 'fadeInUp',
                    animationOut: 'fadeOut'
                },
                focus: true,
                timer: 5000
            });
        });

    </script>
</c:if>