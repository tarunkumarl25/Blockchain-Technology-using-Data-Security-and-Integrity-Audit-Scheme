<!DOCTYPE html>
<%@page import="deduplicatable.data.auditing.mechanismbean.Bean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
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
<script src="https://code.jquery.com/jquery-1.10.2.min.js"></script>
    <script src="assets/js/hover.zoom.js"></script>
    <script src="assets/js/hover.zoom.conf.js"></script>





<script src="assets/js/jquery.min.js" type="text/javascript"></script>
<script src="assets/js/highcharts.js" type="text/javascript"></script>

<script type="text/javascript">
		
		
			Highcharts.visualize = function(table, options) {
				// the categories
				options.xAxis.categories = [];
				$('tbody th', table).each( function(i) {
					options.xAxis.categories.push(this.innerHTML);
				});
				
				
				options.series = [];
				$('tr', table).each( function(i) {
					var tr = this;
					$('th, td', tr).each( function(j) {
						if (j > 0) { // skip first column
							if (i == 0) { // get the name and init the series
								options.series[j - 1] = { 
									name: this.innerHTML,
									data: []
								};
							} else { // add values
								options.series[j - 1].data.push(parseFloat(this.innerHTML));
							}
						}
					});
				});
				
				var chart = new Highcharts.Chart(options);
			}
				
			
			$(document).ready(function() {			
				var table = document.getElementById('datatable'),
				options = {
					   chart: {
					      renderTo: 'container',
					      defaultSeriesType: 'column'
					   },
					   title: {
					      text: 'Number of Data Blocks'
					   },
					   xAxis: {
					   },
					   yAxis: {
					      title: {
					         text: 'Average Number of Audits'
					      }
					   },
					   tooltip: {
					      formatter: function() {
					         return '<b>'+ this.series.name +'</b><br/>'+
					            this.y +' '+ this.x.toLowerCase();
					      }
					   }
					};
				
			      					
				Highcharts.visualize(table, options);
				$('#datatable').hide();
			});
				
		</script>
  </head>

  <body>
	<%ArrayList<Bean> al = new ViewDAO().smViewResult(); %>
    <!-- ***** Preloader Start ***** -->
    <div id="preloader">
        <div class="jumper">
            <div></div>
            <div></div>
            <div></div>
        </div>
    </div>  
    <!-- ***** Preloader End ***** -->
	<jsp:include page="systemmanagerMenu.jsp"></jsp:include>

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
              <h2><em>Result</em></h2>
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
              <center>
<div id="container" style="width: 30%; height: 400px">

</div>
<table id="datatable" border="1" width="134" cellspacing="0" cellpadding="0" height="110">
		
			<thead>
				<tr>
					<th></th>
					
					<%
					
					Iterator iterator=al.iterator();
					while(iterator.hasNext())
					{
					Bean prcc=(Bean)iterator.next();
					
					 %>
					
					<th><font color=green><%=prcc.getBlocksize() %></font></th>
				
					<%
					}
					 %>
				</tr>
			</thead>
			<tbody>
			
		
			
				<tr>
				<th></th>
					<%
					int i=0;
					Iterator iterator1=al.iterator();
					while(iterator1.hasNext())
					{
					i++;
					Bean prcc=(Bean)iterator1.next();
					int val=(int)prcc.getUid();
					
					
					 %>
					<td><%=val%></td>
					
					<%
					}
					 %>
					
				</tr>
				
			</tbody>
			
			
		</table>
		
		
</center>
          </div>
          <%}else
          {%>
        	  <h2 style="margin-left: 35%;"><em>Graph Not Available</em></h2>
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