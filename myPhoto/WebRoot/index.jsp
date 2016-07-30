<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  	<title>chx云相册</title>
  	<script type="text/javascript">
  		function delFile(input){
  			table = input.parentElement.parentElement.parentElement;//table.nodeName  TBODY
  			
  			table.removeChild(input.parentElement.parentElement);
  		}
  		var t=1;
  		function addFile(input){
			tr = input.parentElement.parentElement;
			//alert(tr.nodeName);
			var str = "<td>选择文件：</td>"+
				"<td> <input type='file' name='file"+t+"'> </td> "+
				"<td>文件说明：</td>"+
				"<td> <input type='text' name='text"+t+"'> </td> "+
				"<td> <input type='button' value='删除文件' onclick='delFile(this)'> </td>";
			tr.insertAdjacentHTML("beforeBegin",str);
  		}
  		
  		function move(){
  			window.location.href="/myPhoto/cloudPhoto";
  		}
  	</script>
  	
  	<style type="text/css">
  		#font{
  			color:red;
  		}
  	
  	</style>
  </head>
  
  <body>
  	<h1><font id="font">相册上传：</font></h1>
  	<form action="/myPhoto/upload" method="post" enctype="multipart/form-data">
  		<table border="1px" bordercolor="red">
			<tr>
				<td>选择文件：</td>
				<td> <input type="file" name="file1"> </td> 
				<td>文件说明：</td>
				<td> <input type="text" name="text1"> </td> 
				<td> <input type="button" value="删 除 文 件" onclick="delFile(this)"> </td>
			</tr>
			<tr>
				<td colspan=2> <input type="submit" value="上 传 文 件"> </td>
				<td colspan=3> <input type="button" value="添 加 文 件" onclick="addFile(this)"> </td>
			</tr>
  		</table>
  	</form>
  	<form action="/myPhoto/cloudPhoto" method="post" enctype="multipart/form-data">
  		<table border="1px;double;#ff0000">
			<tr>
				<td colspan=5><input type="submit" value="进 入 云 相 册" onclick="move()"></td>
			</tr>
  		</table>
  	</form>
  </body>
</html>
