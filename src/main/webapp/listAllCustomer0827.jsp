<%@ page language="java" contentType="text/html; charset=utf8"
    pageEncoding="utf8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<title>Welcome-凯捷</title>
		<link href="bootstrap/bootstrap.min.css" rel="stylesheet">
		<style type="text/css">
		</style>	
		<script type="text/javascript" src="jquery/jquery-1.11.3.min.js"></script>
		<script type="text/javascript" src="vue/vue.min.js"></script>
		<script type="text/javascript">
			$(document).ready(function(){
				initData();
				getDataFromServer();
			});
			function getDataFromServer(){
				//到服务器端去加载所有的数据
				var url = "listCustomer.do" ;
				var data = {
						start:(v1.$data.pageInfo.nowPage-1)*v1.$data.pageInfo.pageRows,
						end:v1.$data.pageInfo.pageRows
				}
				$.get(url,data,function(data){
					console.log(data) ;
					v1.$data.custLists = data.allRowData ;
					v1.$data.pageInfo.maxPage = data.allRows;
					console.log(data) ;
				},"json") ;				
			}
			var v1 ;
			function initData(){
				v1 = new Vue({
					el: '#app1',
					data: {
						title:"产品信息维护20180828-cap "  ,
						custNow:{cstNo:'testDatacstNo',
							cstName:'testDatacstName',
							cstSex:'testDatacstSex',
							cstBdt:null,
							cstAds:'cstAds-data',
							cstLevel:'testDatacstLevel',
							logName:'testDatalogName',
							po:'',
							poIndex:-1
						},
						pageInfo:{
							nowPage:1,
							maxPage:100,
							pageRows:10
						},
						custLists:[],
						isAdd:false,
						flag1:false,
					},
					methods:{
						refreshData:function(){
							console.log(this.pageInfo.nowPage);
							getDataFromServer();
						},
						firstPage:function(){
							this.pageInfo.nowPage = 1;
							this.refreshData();
						},
						nextPage:function(){
							this.pageInfo.nowPage = this.pageInfo.nowPage + 1;
							if (this.pageInfo.nowPage > this.pageInfo.maxPage ){
								this.pageInfo.nowPage = this.pageInfo.maxPage ;
							}
							this.refreshData();
						},
						prevPage:function(){
							this.pageInfo.nowPage = this.pageInfo.nowPage - 1;
							if (this.pageInfo.nowPage <1 ){
								this.pageInfo.nowPage  = 1;
							}
							this.refreshData();
						},
						lastPage:function(){
							this.pageInfo.nowPage = this.pageInfo.maxPage;
							this.refreshData();
						}
					}
				})				
			}
			
		</script>
		
</head>
<body>
	<div class="container">
	 	<div style="float:right">
	 	
	 	<!-- el表达式 requestScope/sessionScope/applicationScope/ -->
	 	<!-- 使用jstl的语法来判断是否存在登录信息，如果存在 -->

	 	<!-- 使用jstl的语法来判断是否存在登录信息，如果不存在 -->
	 		
	 	</div>
	 	<table id="app1" class="table table-striped table-bordered table-condensed" >
	 		<tr>
	 			<td>序号</td><td>客户编号</td><td>客户名称</td>
	 			<td>客户性别</td><td>客户日期</td><td>登录名</td>
	 		</tr>
	 		
	 		<tr v-for="(cusInfo,index) in custLists">
	 			<td>{{index +1 }}</td><td>{{cusInfo.cstNo }}</td><td>{{cusInfo.cstName }}</td>
	 			<td>{{cusInfo.cstSex }}</td><td>{{cusInfo.cstBdt }}</td><td>{{cusInfo.logName }}</td>
	 		</tr>
	 		<tr>
	 			<td colspan=10>
	 			总共{{pageInfo.maxPage}}页 
	 			<input type="button"  v-on:click="firstPage()" value= "第一页">
	 			<input type="button"  v-on:click="prevPage()"  value= "上一页">
	 			<a  v-on:click="nextPage()">下一页</a>
	 			<a  v-on:click="lastPage()">最后一页</a>
	 			当前是:{{pageInfo.nowPage}}页 
	 			</td>
	 		</tr>	 		

	 	</table>
	</div>
</body>
</html>