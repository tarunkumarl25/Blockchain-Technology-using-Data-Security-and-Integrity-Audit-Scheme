<!DOCTYPE html>
<%@page import="deduplicatable.data.auditing.mechanismbean.Bean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="deduplicatable.data.auditing.mechanism.dao.ViewDAO"%>
<html lang="en">

  <head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="TemplateMo">
    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

    <title>Deduplication</title>

    <!-- Bootstrap core CSS -->
    <link href="vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="assets/css/fontawesome.css">
    <link rel="stylesheet" href="assets/css/templatemo-finance-business.css">
    <link rel="stylesheet" href="assets/css/owl.css">
<!--

Finance Business TemplateMo

https://templatemo.com/tm-545-finance-business

-->
  </head>

  <body>
	<%
	int uid =(Integer)session.getAttribute("uid");
	ArrayList<Bean> al = new ViewDAO().userViewProfile(uid); %>
    <!-- ***** Preloader Start ***** -->
    <div id="preloader">
        <div class="jumper">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>  
    <!-- ***** Preloader End ***** -->
	<jsp:include page="userMenu.jsp"></jsp:include>

    <!-- Page Content -->
    <!-- Banner Starts Here -->
    <div class="main-banner header-text" id="top">
        <div class="Modern-Slider">
          <!-- Item -->
          <div class="item item-1">
          </div>
          <!-- // Item -->
          <!-- Item -->
        </div>
    </div>
    <!-- Banner Ends Here -->

    <div class="callback-form">
      <div class="container">
        <div class="row">
          <div class="col-md-12">
            <div class="section-heading">
              <h2><em>Profile</em></h2>
              <%String status = request.getParameter("status");
              if(status!=null)
              {%>
            	  <h2 style="color: red;"><%out.print(status); %></h2>
              <%}
              %>
            </div>
          </div>
          <%if(!al.isEmpty()){ %>
          <div class="col-md-6" align="center" style="margin-left: 25%;">
            <div class="contact-form">
              <table style="text-align: left;">
              <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">UID </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
               <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getUid() %></td>
               <%} %>
               </tr>
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">TpaID </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getTpaid() %></td>
               <%} %>
               </tr>
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">TpaName </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getTpaName() %></td>
               <%} %>
               </tr>
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">User Name </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getName()%></td>
               <%} %>
               </tr>
               
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">Email </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getEmail()%></td>
               <%} %>
               </tr>
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">Dob </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getDob()%></td>
               <%} %>
               </tr>
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">Mobile </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getMobile()%></td>
               <%} %>
               </tr>
               <tr>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">Address </th>
              <th style="color: black; font-family: Times New Roman; padding: 20px;">: </th>
              <%for(Bean b: al){ %>
               	<td style="color: black; font-family: Times New Roman; padding: 20px;"><%=b.getAddress()%></td>
               <%} %>
               </tr>
               <tr>
              </tr>
              </table>
            </div>
          </div>
          <%}else
          {%>
        	  <h2 style="margin-left: 25%;"><em>Some Internal Error</em></h2>
          <%} %>
        </div>
      </div>
    </div>

    <!-- Bootstrap core JavaScript -->
    <script src="vendor/jquery/jquery.min.js"></script>
    <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

    <!-- Additional Scripts -->
    <script src="assets/js/custom.js"></script>
    <script src="assets/js/owl.js"></script>
    <script src="assets/js/slick.js"></script>
    <script src="assets/js/accordions.js"></script>

    <script language = "text/Javascript"> 
      cleared[0] = cleared[1] = cleared[2] = 0; //set a cleared flag for each field
      function clearField(t){                   //declaring the array outside of the
      if(! cleared[t.id]){                      // function makes it static and global
          cleared[t.id] = 1;  // you could use true and false, but that's more typing
          t.value='';         // with more chance of typos
          t.style.color='#fff';
          }
      }
    </script>

  </body>
</html>