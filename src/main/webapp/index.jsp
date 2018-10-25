<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
    <title>OCR银行卡识别演示</title>
</head>
<style>
    body{ text-align:center}
</style>
<script type="text/javascript" src="js/jquery-1.11.3.js" ></script>
<script type="text/javascript">
    $(function () {
        $("#selectOCR").change(function () {
            $("#recognize_result_textarea").val("");
        });

        $("#file_input").change(function () {
            $("#recognize_result_textarea").val("");
        });

        $("#recognize_btn").click(function () {
            var filePath = $('#file_input').val();
            if (filePath === '') {
                alert('请选择图片！');
                return false;
            }
            var fileType = (filePath.substring(filePath
                    .lastIndexOf(".") + 1, filePath.length))
                .toLowerCase();
            if (fileType != 'jpg' && fileType != 'png' && fileType != 'gif' && fileType != 'jpeg') {
                alert('文件格式不正确！');
                return false;
            }
            var selectOCR = $('#selectOCR').val();
            $.ajax({
                url: "http://localhost:8888/orcselection/OCRRecognize",
                async: "true",
                type: "post",
                dataType: "json",
                cache: false,
                data: {"selectOCR": selectOCR, "filePath": filePath},
                success: function (data) {
                    $("#recognize_result_textarea").val(data['result']);
                }
            });
            return false;
        });
    });
</script>
<body>
    <div id="ocr_form">
        <br/><br/><br/><br/>
        <select name="selectOCR" id="selectOCR">
            <option value="1">腾讯云</option>
            <option value="2">百度云</option>
            <option value="3">合和</option>
        </select>
        &nbsp;&nbsp;&nbsp;<input type="file" style="width:250px;" name="file" id="file_input" /><br/><br/>
        <textarea style="width:330px;height:200px;font-size:15px;" name="result" id="recognize_result_textarea"></textarea><br/><br/>
        <input type="submit" value="进行识别" id='recognize_btn'/>
    </div>
</body>
</html>
