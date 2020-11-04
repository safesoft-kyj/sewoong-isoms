<%--
  Created by IntelliJ IDEA.
  User: JHSEO
  Date: 2019-02-21
  Time: 오후 2:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" import="java.util.Random" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="Content-type" content="text/html;charset=UTF-8">
<%--    <meta name="viewport" content="width=device-width, initial-scale=1.0">--%>
    <meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1, minimum-scale=1,maximum-scale=1"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<%--    <meta http-equiv="X-UA-Compatible" content="IE=edge">--%>
    <title><sitemesh:write property="title"/></title>
    <!--STYLESHEET-->
    <!--=================================================-->
    <!--jQuery [ REQUIRED ]-->
    <script src="/static/js/jquery.min.js"></script>
    <!--Open Sans Font [ OPTIONAL ] -->
    <link href="http://fonts.googleapis.com/css?family=Open+Sans:300,400,600,700&amp;subset=latin" rel="stylesheet">


    <!--Bootstrap Stylesheet [ REQUIRED ]-->
    <link href="/static/css/bootstrap.css" rel="stylesheet">


    <!--Nifty Stylesheet [ REQUIRED ]-->
    <link href="/static/css/nifty.min.css" rel="stylesheet">


    <!--Premium Icons [ OPTIONAL ]-->
    <link href="/static/premium/icon-sets/icons/line-icons/premium-line-icons.min.css" rel="stylesheet">
    <link href="/static/premium/icon-sets/icons/solid-icons/premium-solid-icons.min.css" rel="stylesheet">
    <link href="/static/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet">
    <sitemesh:write property='head'/>
    <%
//        Random random = new Random();
//        int imageNumber = random.nextInt(13) + 1;
    %>
    <style>
        .bg-img{
            <%--background-image : url("/static/img/bg-img-<%=imageNumber%>.jpg");--%>
            background-image : url("/static/img/bg-img-9.jpg");
        }
    </style>
</head>
<!--TIPS-->

<body>
<div id="container" class="cls-container">

    <!-- BACKGROUND IMAGE -->
    <!--===================================================-->
    <div id="bg-overlay" class="bg-img"></div>
    <sitemesh:write property='body'/>

</div>
<!--===================================================-->
<!-- END OF CONTAINER -->
</body>
</html>
