<%@ taglib prefix="t" uri="http://tiles.apache.org/tags-tiles" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<!DOCTYPE html>
<html lang="en">

<head>
    <t:insertAttribute name="header" />

    <script type="text/javascript" src="/resources/js/emotracker-auth.js"></script>
</head>
<body>
<div class="container">


    <div id="loginbox" style="margin-top:50px;" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info" >
            <div class="panel-heading">
                <div class="panel-title">Emotracker server sign in</div>
                <div style="float:right; font-size: 80%; position: relative; top:-10px"><a href="#">Forgot password?</a></div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger col-sm-12">${error}</div>
            </c:if>

            <div style="padding-top:30px" class="panel-body" >

                <div style="display:none" id="login-alert" class="alert alert-danger col-sm-12"></div>

                <form id="loginform" class="form-horizontal" role="form" action="<c:url value="/j_spring_security_check" />" method='POST'>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                        <input id="login-username" type="text" class="form-control"
                               name="username" value="" placeholder="username or email" />
                    </div>

                    <div style="margin-bottom: 25px" class="input-group">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                        <input id="login-password" type="password" class="form-control"
                               name="password" placeholder="password" />
                    </div>



                    <div class="input-group" style="display:none">
                        <div class="checkbox">
                            <label>
                                <input id="login-remember" type="checkbox" name="remember" value="1"> Remember me
                            </label>
                        </div>
                    </div>


                    <div style="margin-top:10px" class="form-group">
                        <!-- Button -->

                        <div class="col-sm-12 controls">
                            <input id="btn-login" class="btn btn-primary" name="submit" type="submit" value="Sign in" />

                        </div>
                    </div>


                    <div class="form-group" style="display: none;">
                        <div class="col-md-12 control">
                            <div style="border-top: 1px solid#888; padding-top:15px; font-size:85%" >
                                Don't have an account?
                                <a href="#" onClick="$('#loginbox').hide(); $('#signupbox').show()">
                                    Sign up here
                                </a>
                            </div>
                        </div>
                    </div>
                </form>



            </div>
        </div>
    </div>
    <div id="signupbox" style="display:none; margin-top:50px" class="mainbox col-md-6 col-md-offset-3 col-sm-8 col-sm-offset-2">
        <div class="panel panel-info">
            <div class="panel-heading">
                <div class="panel-title">Sign Up</div>
                <div style="float:right; font-size: 85%; position: relative; top:-10px"><a id="signinlink" href="#" onclick="$('#signupbox').hide(); $('#loginbox').show()">Sign in</a></div>
            </div>
            <div class="panel-body" >
                <form id="signupform" class="form-horizontal" role="form">

                    <div id="signupalert" style="display:none" class="alert alert-danger">
                        <p>Error:</p>
                        <span></span>
                    </div>



                    <div class="form-group">
                        <label for="email" class="col-md-3 control-label">Email</label>
                        <div class="col-md-9">
                            <input id="email" type="text" class="form-control" name="email" placeholder="Email Address">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="firstname" class="col-md-3 control-label">Full Name</label>
                        <div class="col-md-9">
                            <input id="firstname" type="text" class="form-control" name="fullName" placeholder="First Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-md-3 control-label">Username</label>
                        <div class="col-md-9">
                            <input id="lastname" type="text" class="form-control" name="userName" placeholder="Last Name">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="password" class="col-md-3 control-label">Password</label>
                        <div class="col-md-9">
                            <input id="password" type="password" class="form-control" name="password" placeholder="Password">
                        </div>
                    </div>

                    <%--<div class="form-group">--%>
                        <%--<label for="icode" class="col-md-3 control-label">Invitation Code</label>--%>
                        <%--<div class="col-md-9">--%>
                            <%--<input type="text" class="form-control" name="icode" placeholder="">--%>
                        <%--</div>--%>
                    <%--</div>--%>

                    <div class="form-group">
                        <!-- Button -->
                        <div class="col-md-offset-3 col-md-9">
                            <button id="btn-signup" type="submit" class="btn btn-primary"><i class="icon-hand-right"></i>Sign up</button>
                            <%--<span style="margin-left:8px;">or</span>--%>
                        </div>
                    </div>

                    <%--<div style="border-top: 1px solid #999; padding-top:20px"  class="form-group">--%>

                        <%--<div class="col-md-offset-3 col-md-9">--%>
                            <%--<button id="btn-fbsignup" type="button" class="btn btn-primary"><i class="icon-facebook"></i>   Sign Up with Facebook</button>--%>
                        <%--</div>--%>

                    <%--</div>--%>



                </form>
            </div>
        </div>




    </div>
</div>

</body>
</html>
