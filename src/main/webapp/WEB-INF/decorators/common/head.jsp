<%--
  Created by IntelliJ IDEA.
  User: JHSEO
  Date: 2019-02-21
  Time: 오전 11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication property="principal" var="user"/>
<%--<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>--%>
<!--STYLESHEET-->
<!--=================================================-->

<!--Open Sans Font [ OPTIONAL ] -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin" rel="stylesheet">


<!--Bootstrap Stylesheet [ REQUIRED ]-->
<link href="/static/css/bootstrap.min.css" rel="stylesheet">


<!--Nifty Stylesheet [ REQUIRED ]-->
<link href="/static/css/nifty.min.css" rel="stylesheet">


<!--Premium Icons [ OPTIONAL ]-->
<link href="/static/premium/icon-sets/icons/line-icons/premium-line-icons.min.css" rel="stylesheet">
<link href="/static/premium/icon-sets/icons/solid-icons/premium-solid-icons.min.css" rel="stylesheet">

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


<link href="/static/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">

<c:choose>
    <c:when test="${not empty param['admin']}">
    <link href="/static/css/themes/type-c/theme-ocean.min.css" rel="stylesheet">
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
This category must be included but you may modify which plugins or components which should be included in your
project.


OPTIONAL
Optional plugins. You may choose whether to include it in your project or not.


Detailed information and more samples can be found in the document.

=================================================-->
<script>
    $(function() {
        var pathname = location.pathname;
        activeLink(pathname);

        $.ajaxSetup({
            error:function(x, status, error) {
                if (x.status == 401 || x.status == 403) {
                    if(unloadMessage) {
                        $(window).off("beforeunload", unloadMessage);
                    }

                    alert("Sorry, your session has expired. Please login again to continue");
                    window.location.href ="/login?invalidSession";
                } else {
                    alert("An error occurred: " + status + "nError: " + error);
                }
            }
        });
    });

    var recursionCount = 0;
    function activeLink(pathname) {
        recursionCount ++;
        if(recursionCount == 5) {
            return;
        }
        var $a = $("#mainnav-menu").find("a[href='"+pathname+"']");
        var li = $a.parents("li");

        if(li.length == 0) {
            pathname = pathname.substring(0, pathname.lastIndexOf("/"));
            return activeLink(pathname);
        } else {
            li.find("ul.collapse").addClass('in');
        }

        recursionCount = 0;
        li.addClass('active-sub');
        var $a = li.find("a");
        setPageTitle($a.html());
        setNav(pathname, $a.find("span.menu-title").text());

    }

    function setPageTitle(pageTitle) {
        $("#page-title").html("<h1 class=\"page-header text-overflow\">"+pageTitle+"</h1>");

    }

    function setNav(pathname, pageTitle) {
        $("#breadcrumb").append("<li><a href=\"/\"><i class=\"pli-home\"></i></a></li>");
        if(pageTitle) {
            $("#breadcrumb").append("<li>"+pageTitle+"</li>");
        }
        var $a = $("#mainnav-menu").find("a[href='"+pathname+"']");
            $("#breadcrumb").append("<li>"+$a.text()+"</li>");
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
<sec:authorize var="isAdmin" access="hasAnyAuthority('QAA','QAM','QMO', 'QAD')"/>
<c:if test="${empty param['admin'] or isAdmin eq false}">
    var pathname = location.pathname;
    // var paths = pathname.split("/");
    if(pathname.indexOf("/notice/") == -1 || ${isAdmin} == false) {
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
        $(document).ready(function() {
            $.niftyNoty({
                type: 'info',
                container: 'floating',
                // html: alert_content[alert_layout].type,
                message:'${message}',
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