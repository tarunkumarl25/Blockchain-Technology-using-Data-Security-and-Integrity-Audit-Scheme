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
	<%ArrayList<Bean> al = new ViewDAO().spViewDeDuplicate(); %>
    <!-- ***** Preloader Start ***** -->
    <div id="preloader">
        <div class="jumper">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>  
    <!-- ***** Preloader End ***** -->
	<jsp:include page="serviceProviderMenu.jsp"></jsp:include>

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
              <h2><em>Deduplication Data</em></h2>
              <%String status = request.getParameter("status");
              if(status!=null)
              {%>
            	  <h2 style="color: red;"><%out.print(status); %></h2>
              <%}
              %>
            </div>
          </div>
          <%if(!al.isEmpty()){ %>
          <div class="col-md-12" align="center" style="margin-left: 2%;">
            <div class="contact-form">
              <table border="1" style="border-color: black;">
              <tr>
              <th style="color: black; font-family: Times New Roman; padding: 5px;">FileId</th>
              <th style="color: black; font-family: Times New Roman; padding: 5px;">Uname</th>
              <th style="color: black; font-family: Times New Roman; padding: 5px;">TpaID</th>
              <th style="color: black; font-family: Times New Roman; padding: 5px;">Fname</th>
              <th style="color: black; font-family: Times New Roman; padding: 5px;">Data Index</th>
              <th style="color: black; font-family: Times New Roman; padding: 5px;">Deduplication</th>
              </tr>
              <%for(Bean b: al){ %>
              <tr>
              	<td style="color: black; font-family: Times New Roman; padding: 5px;"><%=b.getFid() %></td>
              	<td style="color: black; font-family: Times New Roman; padding: 5px;"><%=b.getName() %></td>
              	<td style="color: black; font-family: Times New Roman; padding: 5px;"><%=b.getTpaid()%></td>
              	<td style="color: black; font-family: Times New Roman; padding: 5px;"><%=b.getFname()%></td>
              	<td style="color: black; font-family: Times New Roman; padding: 5px;"><textarea rows="5" cols="60"><%=b.getData() %></textarea></td>
              	<td style="color: black; font-family: Times New Roman; padding: 5px;"><a href="./DeduplicationIntegrityProofServlet_sp?fid=<%=b.getFid()%>&uname=<%=b.getName()%>&fname=<%=b.getFname()%>&uid=<%=b.getUid()%>">Send Integrity Proof</a></td>
              </tr>
              <%} %>
              </table>
            </div>
          </div>
          <%}else
          {%>
        	  <h2 style="margin-left: 30%;"><em>Deduplication Files are Available</em></h2>
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