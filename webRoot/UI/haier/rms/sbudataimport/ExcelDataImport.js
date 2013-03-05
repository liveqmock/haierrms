
/* 补 0
* */
function pad(num,n){
    var len = num.toString().length;
    while (len < n){
        num = "0" + num;
        len++;
    }
    return num;
}
/*
* 页面初期化，取得当前时间
* */
function getNowDate(){
    var dt = new Date();
    dt.setDate(dt.getDate()-1);
    var dateStr = dt.getYear() + "-" + pad((dt.getMonth()+1),2) + "-" + pad(dt.getDate(),2);
    document.getElementById("txtDataDate").value = dateStr;
}
var idTmr = "";
 /*
 * 关闭Excel进程 垃圾回收
 * */
  function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
  }
 //Excel格式及数据正确性前台验证
function ExcelFomatCheck(){
    //日期判断
    if (document.getElementById("txtDataDate").value == "") {
        alert("数据日期不能为空！");
        return false;
    }
    var excelfile = document.getElementById("himport").value;
    if (excelfile == ""){
        alert("请选择数据文件。");
        return false;
    }
    if (excelfile.substring(excelfile.length-3,excelfile.length).toLowerCase() != "xls") {
        alert("文件类型不正确，请输入Excel格式文件!");
        return false;
    }
    var oXL = new ActiveXObject("Excel.Application");
    oXL.Visible=false;
    var oWB;
    var oSheet1;
    var oSheet2;
    var oSheet3;
    var isRight = true;
    var errPeridLst = "";      //错误身份证号码list
    var FormatTitle = "文件表头不正确，请检查文件是否正确。";
    try{
        oWB = oXL.Workbooks.Open(excelfile,0,true);
        oSheet1 = oWB.Sheets(1);      //投资银行业务收入明细sheet
        oSheet2 = oWB.Sheets(2);      //国际业务收入明细sheet
        oSheet3 = oWB.Sheets(3);      //其它收入明细sheet
        var Count1 = oSheet1.UsedRange.rows.Count + 2;
        var Count2 = oSheet2.UsedRange.rows.Count + 2;
        var Count3 = oSheet3.UsedRange.rows.Count + 2;
        var i,j,k;
        var startDay,endDay,basicRate,dayAvail;
        var s1money,s2money,s3money;
        var sequence;  //序号
        var productName; //产品代码 or 单位名称
        //sheet1的判断
        var sheet1Name = "    sheet【投资银行业务】：\r\n";
        for (i = 5;i < Count1;i++){
            //序号列  空值，长度判断
            sequence = oSheet1.cells(i,1).value;
            if (sequence == "" || sequence == undefined){
                alert(sheet1Name + "第"+i+"行(序号)列不能为空。");
                return false;
            } else if (sequence.toString().length > 50){
                alert(sheet1Name + "第"+i+"行(序号)列字符长度不能超过50。");
                return false;
            }
            //产品代码 空值，长度判断
            productName = oSheet1.cells(i,2).value;
            if (productName == "" || productName == undefined){
                alert(sheet1Name + "第"+i+"行(产品代码)列不能为空。");
                return false;
            } else if (productName.toString().length > 100){
                alert(sheet1Name + "第"+i+"行(产品代码)列字符长度不能超过50。");
                return false;
            }
            //业务种类 空值
            if (oSheet1.cells(i,3).value =="" || oSheet1.cells(i,3).value == undefined){
                alert(sheet1Name + "第"+i+"行(业务种类)列不能为空。");
                return false;
            }
            //币种 空值
            if (oSheet1.cells(i,5).value =="" || oSheet1.cells(i,5).value == undefined){
                alert(sheet1Name + "第"+i+"行(币种)列不能为空。");
                return false;
            }
            //投资金额列判断
            s1money = oSheet1.cells(i,4).value;
            if (s1money == "" || s1money == undefined){
                alert(sheet1Name+"第"+i+"行(投资金额)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(s1money)){
                alert(sheet1Name+"第"+i+"行(投资金额)必须是数字，请重新输入。");
                return false;
            }
            //起始日期 结束日期格式判断
            var objDate = new Date(oSheet1.cells(i,6).value);
            startDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if (startDay == "" || startDay == undefined){
                alert(sheet1Name+"第"+i+"行(起始日)不能为空，请重新输入。");
                return false;
            }
            if (!checkDate(startDay)){
                alert(sheet1Name+"第"+i+"行(起始日)格式不正确，日期输入格式如下 yyyy-mm-dd。");
                return false;
            }
            objDate = new Date(oSheet1.cells(i,7).value);
            endDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if(endDay == "" || endDay == undefined){
                 alert(sheet1Name+"第"+i+"行(到期日)不能为空，请重新输入。");
                return false;
            }
            if (!checkDate(endDay)){
                alert(sheet1Name+"第"+i+"行(到期日)格式不正确，日期输入格式如下 yyyy-mm-dd。");
                return false;
            }
            //基准利率
            basicRate = oSheet1.cells(i,8).value;
            if (basicRate == "" || basicRate == undefined){
                alert(sheet1Name+"第"+i+"行(基准利率)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(basicRate)){
                alert(sheet1Name+"第"+i+"行(基准利率)必须是数字，请重新输入。");
                return false;
            }
            //日收益
            dayAvail = oSheet1.cells(i,9).value;
            if (dayAvail == "" || dayAvail == undefined){
                alert(sheet1Name+"第"+i+"行(日收益)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(dayAvail)){
                alert(sheet1Name+"第"+i+"行(日收益)必须是数字，请重新输入。");
                return false;
            }
        }
//                alert("sheet1 is success!!");
        //sheet2的判断
         var sheet2Name = "    sheet【国际业务】：\r\n";
        for (i = 5;i < Count2;i++){
            //序号列  空值，长度判断
            sequence = oSheet2.cells(i,1).value;
            if (sequence == "" || sequence == undefined){
                alert(sheet2Name + "第"+i+"行(序号)列不能为空。");
                return false;
            } else if (sequence.toString().length > 50){
                alert(sheet2Name + "第"+i+"行(序号)列字符长度不能超过50。");
                return false;
            }
            //单位名称 空值，长度判断
            productName = oSheet2.cells(i,2).value;
            if (productName == "" || productName == undefined){
                alert(sheet2Name + "第"+i+"行(单位名称)列不能为空。");
                return false;
            } else if (productName.toString().length > 100){
                alert(sheet2Name + "第"+i+"行(单位名称)列字符长度不能超过50。");
                return false;
            }
            //贷款形式 空值
            if (oSheet2.cells(i,3).value == "" || oSheet2.cells(i,3).value == undefined){
                alert(sheet2Name + "第"+i+"行(贷款形式)列不能为空。");
                return false;
            }
            //是否境外收入 空值
            if (oSheet2.cells(i,4).value == "" || oSheet2.cells(i,4).value == undefined){
                alert(sheet2Name + "第"+i+"行(是否境外收入)列不能为空。");
                return false;
            }
            //币种 空值
            if (oSheet2.cells(i,6).value == "" || oSheet2.cells(i,6).value == undefined){
                alert(sheet2Name + "第"+i+"行(币种)列不能为空。");
                return false;
            }
            //贷款金额列判断
            s2money = oSheet2.cells(i,5).value;
            if (s2money == "" || s2money == undefined){
                alert(sheet2Name+"第"+i+"行(贷款金额)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(s1money)){
                alert(sheet2Name+"第"+i+"行(贷款金额)必须是数字，请重新输入。");
                return false;
            }
            //起始日期 结束日期格式判断
            var objDate = new Date(oSheet2.cells(i,7).value);
            startDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if (startDay == "" || startDay == undefined){
                alert(sheet2Name+"第"+i+"行(发放日期)不能为空，请重新输入。");
                return false;
            }
            if (!checkDate(startDay)){
                alert(sheet2Name+"第"+i+"行(发放日期)格式不正确，日期输入格式如下 yyyy-mm-dd。");
                return false;
            }
            objDate = new Date(oSheet2.cells(i,8).value);
            endDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if(endDay == "" || endDay == undefined){
                 alert(sheet2Name+"第"+i+"行(到期日)不能为空，请重新输入。");
                return false;
            }
            if (!checkDate(endDay)){
                alert(sheet2Name+"第"+i+"行(到期日)格式不正确，日期输入格式如下 yyyy-mm-dd。");
                return false;
            }
            //基准利率
            basicRate = oSheet2.cells(i,9).value;
            if (basicRate == "" || basicRate == undefined){
                alert(sheet2Name+"第"+i+"行(基准利率)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(basicRate)){
                alert(sheet2Name+"第"+i+"行(基准利率)必须是数字，请重新输入。");
                return false;
            }
            //日收益
            dayAvail = oSheet2.cells(i,10).value;
            if (dayAvail == "" || dayAvail == undefined){
                alert(sheet2Name+"第"+i+"行(日收益)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(dayAvail)){
                alert(sheet2Name+"第"+i+"行(日收益)必须是数字，请重新输入。");
                return false;
            }
        }
//                alert("sheet2 is success!!");
        //sheet3的判断
        var sheet3Nmae = "    sheet【其它收入】：\r\n";
        for (i = 5;i < Count3;i++){
            //序号列  空值，长度判断
            sequence = oSheet3.cells(i,1).value;
            if (sequence == "" || sequence == undefined){
                alert(sheet3Nmae + "第"+i+"行(序号)列不能为空。");
                return false;
            } else if (sequence.toString().length > 50){
                alert(sheet3Nmae + "第"+i+"行(序号)列字符长度不能超过50。");
                return false;
            }
            //业务类型 空值
            if (oSheet3.cells(i,2).value == "" || oSheet3.cells(i,2).value == undefined){
                alert(sheet3Nmae + "第"+i+"行(业务类型)列不能为空。");
                return false;
            }
            //币种 空值
//            alert(i);
//            alert(oSheet3.cells(i,4).value);
            
            if (oSheet3.cells(i,4).value == "" || oSheet3.cells(i,4).value == undefined){
                alert(sheet3Nmae + "第"+i+"行(币种)列不能为空。");
                return false;
            }
            //金额列数字判断
            s3money = oSheet3.cells(i,3).value;
            if (s3money == "" || s3money == undefined){
                alert(sheet3Nmae+"第"+i+"行(金额)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(s3money)){
                alert(sheet3Nmae+"第"+i+"行(金额)必须是数字，请重新输入。");
                return false;
            }
            //起始日期 结束日期格式判断
            var objDate = new Date(oSheet3.cells(i,5).value);
            startDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if (startDay == "" || startDay == undefined){
                alert(sheet3Nmae+"第"+i+"行(起始日期)不能为空，请重新输入。");
                return false;
            }
            if (!checkDate(startDay)){
                alert(sheet3Nmae+"第"+i+"行(起始日期)格式不正确，日期输入格式如下 yyyy-mm-dd。");
                return false;
            }
            objDate = new Date(oSheet3.cells(i,6).value);
            endDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if(endDay == "" || endDay == undefined){
                 alert(sheet3Nmae+"第"+i+"行(结束日期)不能为空，请重新输入。");
                return false;
            }
            if (!checkDate(endDay)){
                alert(sheet3Nmae+"第"+i+"行(结束日期)格式不正确，日期输入格式如下 yyyy-mm-dd。");
                return false;
            }
            //基准利率
            basicRate = oSheet3.cells(i,7).value;
            if (basicRate == "" || basicRate == undefined){
                alert(sheet3Nmae+"第"+i+"行(基准利率)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(basicRate)){
                alert(sheet3Nmae+"第"+i+"行(基准利率)必须是数字，请重新输入。");
                return false;
            }
            //日收益
            dayAvail = oSheet3.cells(i,7).value;
            if (dayAvail == "" || dayAvail == undefined){
                alert(sheet3Nmae+"第"+i+"行(日收益)不能为空，请重新输入。");
                return false;
            }
            if (isNaN(dayAvail)){
                alert(sheet3Nmae+"第"+i+"行(日收益)必须是数字，请重新输入。");
                return false;
            }

        }

        document.forms[0].submit();

    }catch(ex){
        alert(ex.getMessage);
    } finally{
        //关闭Excel进程
        oXL.Quit();
        oXL = null;
        idTmr = window.setInterval("Cleanup();",1);
    }
}
/////去掉左边控格
function LTrim(str){
	if ((typeof(str) != "string") || !str) return "";
	for(var i=0; i<str.length; i++){if (str.charCodeAt(i, 10)!=32) break;}
	return str.substring(i, str.length);
}

//去掉右边控格
function RTrim(str){
	if ((typeof(str) != "string") || !str) return "";
	for(var i=str.length-1; i>=0; i--){if (str.charCodeAt(i, 10)!=32) break;}
	return str.substr(0, i+1);
}
/// /去掉控格
function trimStr(str){
	if ((typeof(str) != "string") || !str) return "";
	return LTrim(RTrim(str));
}

/*
* 日期格式判断
* */
/*
* 判断日期，格式：yyyy-mm-dd
* */
function checkDate(fld) {
    var mo, day, yr;
    var entry;
    //haiyu huang 2010-6-24 添加判断
    if (typeof(fld) == "object")
        entry = fld.value;
    else entry = fld;
	//alert(entry);
    var re = /\d{4}\b[-]\b\d{1,2}[-]\d{1,2}/;

    if (re.test(entry)) {
		//alert('12345');
        var delimChar = "-";
        var delim1 = entry.indexOf(delimChar);
        var delim2 = entry.lastIndexOf(delimChar);
        yr = parseInt(entry.substring(0, delim1), 10);
        mo = parseInt(entry.substring(delim1+1, delim2), 10);
        day= parseInt(entry.substring(delim2+1), 10);
        var testDate = new Date(yr, mo-1, day);
        //alert(testDate);
        if (testDate.getDate( ) == day) {
            if (testDate.getMonth( ) + 1 == mo) {
                if (testDate.getFullYear( ) == yr) {
                    return true;
                } else {
//                    alert("输入年份有误！");
                    return false;
                }
            } else {
//                alert("输入月份有误！");
                return false;
            }
        } else {
//            alert("输入日份有误!");
            return false;
        }
    } else {
//        alert("不正确日期格式. 日期输入格式如下 yyyy-mm-dd.");
        return false;
    }
    return true;
}
