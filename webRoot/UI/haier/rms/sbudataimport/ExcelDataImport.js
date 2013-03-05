
/* �� 0
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
* ҳ����ڻ���ȡ�õ�ǰʱ��
* */
function getNowDate(){
    var dt = new Date();
    dt.setDate(dt.getDate()-1);
    var dateStr = dt.getYear() + "-" + pad((dt.getMonth()+1),2) + "-" + pad(dt.getDate(),2);
    document.getElementById("txtDataDate").value = dateStr;
}
var idTmr = "";
 /*
 * �ر�Excel���� ��������
 * */
  function Cleanup() {
    window.clearInterval(idTmr);
    CollectGarbage();
  }
 //Excel��ʽ��������ȷ��ǰ̨��֤
function ExcelFomatCheck(){
    //�����ж�
    if (document.getElementById("txtDataDate").value == "") {
        alert("�������ڲ���Ϊ�գ�");
        return false;
    }
    var excelfile = document.getElementById("himport").value;
    if (excelfile == ""){
        alert("��ѡ�������ļ���");
        return false;
    }
    if (excelfile.substring(excelfile.length-3,excelfile.length).toLowerCase() != "xls") {
        alert("�ļ����Ͳ���ȷ��������Excel��ʽ�ļ�!");
        return false;
    }
    var oXL = new ActiveXObject("Excel.Application");
    oXL.Visible=false;
    var oWB;
    var oSheet1;
    var oSheet2;
    var oSheet3;
    var isRight = true;
    var errPeridLst = "";      //�������֤����list
    var FormatTitle = "�ļ���ͷ����ȷ�������ļ��Ƿ���ȷ��";
    try{
        oWB = oXL.Workbooks.Open(excelfile,0,true);
        oSheet1 = oWB.Sheets(1);      //Ͷ������ҵ��������ϸsheet
        oSheet2 = oWB.Sheets(2);      //����ҵ��������ϸsheet
        oSheet3 = oWB.Sheets(3);      //����������ϸsheet
        var Count1 = oSheet1.UsedRange.rows.Count + 2;
        var Count2 = oSheet2.UsedRange.rows.Count + 2;
        var Count3 = oSheet3.UsedRange.rows.Count + 2;
        var i,j,k;
        var startDay,endDay,basicRate,dayAvail;
        var s1money,s2money,s3money;
        var sequence;  //���
        var productName; //��Ʒ���� or ��λ����
        //sheet1���ж�
        var sheet1Name = "    sheet��Ͷ������ҵ�񡿣�\r\n";
        for (i = 5;i < Count1;i++){
            //�����  ��ֵ�������ж�
            sequence = oSheet1.cells(i,1).value;
            if (sequence == "" || sequence == undefined){
                alert(sheet1Name + "��"+i+"��(���)�в���Ϊ�ա�");
                return false;
            } else if (sequence.toString().length > 50){
                alert(sheet1Name + "��"+i+"��(���)���ַ����Ȳ��ܳ���50��");
                return false;
            }
            //��Ʒ���� ��ֵ�������ж�
            productName = oSheet1.cells(i,2).value;
            if (productName == "" || productName == undefined){
                alert(sheet1Name + "��"+i+"��(��Ʒ����)�в���Ϊ�ա�");
                return false;
            } else if (productName.toString().length > 100){
                alert(sheet1Name + "��"+i+"��(��Ʒ����)���ַ����Ȳ��ܳ���50��");
                return false;
            }
            //ҵ������ ��ֵ
            if (oSheet1.cells(i,3).value =="" || oSheet1.cells(i,3).value == undefined){
                alert(sheet1Name + "��"+i+"��(ҵ������)�в���Ϊ�ա�");
                return false;
            }
            //���� ��ֵ
            if (oSheet1.cells(i,5).value =="" || oSheet1.cells(i,5).value == undefined){
                alert(sheet1Name + "��"+i+"��(����)�в���Ϊ�ա�");
                return false;
            }
            //Ͷ�ʽ�����ж�
            s1money = oSheet1.cells(i,4).value;
            if (s1money == "" || s1money == undefined){
                alert(sheet1Name+"��"+i+"��(Ͷ�ʽ��)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(s1money)){
                alert(sheet1Name+"��"+i+"��(Ͷ�ʽ��)���������֣����������롣");
                return false;
            }
            //��ʼ���� �������ڸ�ʽ�ж�
            var objDate = new Date(oSheet1.cells(i,6).value);
            startDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if (startDay == "" || startDay == undefined){
                alert(sheet1Name+"��"+i+"��(��ʼ��)����Ϊ�գ����������롣");
                return false;
            }
            if (!checkDate(startDay)){
                alert(sheet1Name+"��"+i+"��(��ʼ��)��ʽ����ȷ�����������ʽ���� yyyy-mm-dd��");
                return false;
            }
            objDate = new Date(oSheet1.cells(i,7).value);
            endDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if(endDay == "" || endDay == undefined){
                 alert(sheet1Name+"��"+i+"��(������)����Ϊ�գ����������롣");
                return false;
            }
            if (!checkDate(endDay)){
                alert(sheet1Name+"��"+i+"��(������)��ʽ����ȷ�����������ʽ���� yyyy-mm-dd��");
                return false;
            }
            //��׼����
            basicRate = oSheet1.cells(i,8).value;
            if (basicRate == "" || basicRate == undefined){
                alert(sheet1Name+"��"+i+"��(��׼����)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(basicRate)){
                alert(sheet1Name+"��"+i+"��(��׼����)���������֣����������롣");
                return false;
            }
            //������
            dayAvail = oSheet1.cells(i,9).value;
            if (dayAvail == "" || dayAvail == undefined){
                alert(sheet1Name+"��"+i+"��(������)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(dayAvail)){
                alert(sheet1Name+"��"+i+"��(������)���������֣����������롣");
                return false;
            }
        }
//                alert("sheet1 is success!!");
        //sheet2���ж�
         var sheet2Name = "    sheet������ҵ�񡿣�\r\n";
        for (i = 5;i < Count2;i++){
            //�����  ��ֵ�������ж�
            sequence = oSheet2.cells(i,1).value;
            if (sequence == "" || sequence == undefined){
                alert(sheet2Name + "��"+i+"��(���)�в���Ϊ�ա�");
                return false;
            } else if (sequence.toString().length > 50){
                alert(sheet2Name + "��"+i+"��(���)���ַ����Ȳ��ܳ���50��");
                return false;
            }
            //��λ���� ��ֵ�������ж�
            productName = oSheet2.cells(i,2).value;
            if (productName == "" || productName == undefined){
                alert(sheet2Name + "��"+i+"��(��λ����)�в���Ϊ�ա�");
                return false;
            } else if (productName.toString().length > 100){
                alert(sheet2Name + "��"+i+"��(��λ����)���ַ����Ȳ��ܳ���50��");
                return false;
            }
            //������ʽ ��ֵ
            if (oSheet2.cells(i,3).value == "" || oSheet2.cells(i,3).value == undefined){
                alert(sheet2Name + "��"+i+"��(������ʽ)�в���Ϊ�ա�");
                return false;
            }
            //�Ƿ������� ��ֵ
            if (oSheet2.cells(i,4).value == "" || oSheet2.cells(i,4).value == undefined){
                alert(sheet2Name + "��"+i+"��(�Ƿ�������)�в���Ϊ�ա�");
                return false;
            }
            //���� ��ֵ
            if (oSheet2.cells(i,6).value == "" || oSheet2.cells(i,6).value == undefined){
                alert(sheet2Name + "��"+i+"��(����)�в���Ϊ�ա�");
                return false;
            }
            //���������ж�
            s2money = oSheet2.cells(i,5).value;
            if (s2money == "" || s2money == undefined){
                alert(sheet2Name+"��"+i+"��(������)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(s1money)){
                alert(sheet2Name+"��"+i+"��(������)���������֣����������롣");
                return false;
            }
            //��ʼ���� �������ڸ�ʽ�ж�
            var objDate = new Date(oSheet2.cells(i,7).value);
            startDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if (startDay == "" || startDay == undefined){
                alert(sheet2Name+"��"+i+"��(��������)����Ϊ�գ����������롣");
                return false;
            }
            if (!checkDate(startDay)){
                alert(sheet2Name+"��"+i+"��(��������)��ʽ����ȷ�����������ʽ���� yyyy-mm-dd��");
                return false;
            }
            objDate = new Date(oSheet2.cells(i,8).value);
            endDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if(endDay == "" || endDay == undefined){
                 alert(sheet2Name+"��"+i+"��(������)����Ϊ�գ����������롣");
                return false;
            }
            if (!checkDate(endDay)){
                alert(sheet2Name+"��"+i+"��(������)��ʽ����ȷ�����������ʽ���� yyyy-mm-dd��");
                return false;
            }
            //��׼����
            basicRate = oSheet2.cells(i,9).value;
            if (basicRate == "" || basicRate == undefined){
                alert(sheet2Name+"��"+i+"��(��׼����)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(basicRate)){
                alert(sheet2Name+"��"+i+"��(��׼����)���������֣����������롣");
                return false;
            }
            //������
            dayAvail = oSheet2.cells(i,10).value;
            if (dayAvail == "" || dayAvail == undefined){
                alert(sheet2Name+"��"+i+"��(������)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(dayAvail)){
                alert(sheet2Name+"��"+i+"��(������)���������֣����������롣");
                return false;
            }
        }
//                alert("sheet2 is success!!");
        //sheet3���ж�
        var sheet3Nmae = "    sheet���������롿��\r\n";
        for (i = 5;i < Count3;i++){
            //�����  ��ֵ�������ж�
            sequence = oSheet3.cells(i,1).value;
            if (sequence == "" || sequence == undefined){
                alert(sheet3Nmae + "��"+i+"��(���)�в���Ϊ�ա�");
                return false;
            } else if (sequence.toString().length > 50){
                alert(sheet3Nmae + "��"+i+"��(���)���ַ����Ȳ��ܳ���50��");
                return false;
            }
            //ҵ������ ��ֵ
            if (oSheet3.cells(i,2).value == "" || oSheet3.cells(i,2).value == undefined){
                alert(sheet3Nmae + "��"+i+"��(ҵ������)�в���Ϊ�ա�");
                return false;
            }
            //���� ��ֵ
//            alert(i);
//            alert(oSheet3.cells(i,4).value);
            
            if (oSheet3.cells(i,4).value == "" || oSheet3.cells(i,4).value == undefined){
                alert(sheet3Nmae + "��"+i+"��(����)�в���Ϊ�ա�");
                return false;
            }
            //����������ж�
            s3money = oSheet3.cells(i,3).value;
            if (s3money == "" || s3money == undefined){
                alert(sheet3Nmae+"��"+i+"��(���)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(s3money)){
                alert(sheet3Nmae+"��"+i+"��(���)���������֣����������롣");
                return false;
            }
            //��ʼ���� �������ڸ�ʽ�ж�
            var objDate = new Date(oSheet3.cells(i,5).value);
            startDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if (startDay == "" || startDay == undefined){
                alert(sheet3Nmae+"��"+i+"��(��ʼ����)����Ϊ�գ����������롣");
                return false;
            }
            if (!checkDate(startDay)){
                alert(sheet3Nmae+"��"+i+"��(��ʼ����)��ʽ����ȷ�����������ʽ���� yyyy-mm-dd��");
                return false;
            }
            objDate = new Date(oSheet3.cells(i,6).value);
            endDay = objDate.getYear() + "-" + (objDate.getMonth()+1) + "-" + objDate.getDate();
            if(endDay == "" || endDay == undefined){
                 alert(sheet3Nmae+"��"+i+"��(��������)����Ϊ�գ����������롣");
                return false;
            }
            if (!checkDate(endDay)){
                alert(sheet3Nmae+"��"+i+"��(��������)��ʽ����ȷ�����������ʽ���� yyyy-mm-dd��");
                return false;
            }
            //��׼����
            basicRate = oSheet3.cells(i,7).value;
            if (basicRate == "" || basicRate == undefined){
                alert(sheet3Nmae+"��"+i+"��(��׼����)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(basicRate)){
                alert(sheet3Nmae+"��"+i+"��(��׼����)���������֣����������롣");
                return false;
            }
            //������
            dayAvail = oSheet3.cells(i,7).value;
            if (dayAvail == "" || dayAvail == undefined){
                alert(sheet3Nmae+"��"+i+"��(������)����Ϊ�գ����������롣");
                return false;
            }
            if (isNaN(dayAvail)){
                alert(sheet3Nmae+"��"+i+"��(������)���������֣����������롣");
                return false;
            }

        }

        document.forms[0].submit();

    }catch(ex){
        alert(ex.getMessage);
    } finally{
        //�ر�Excel����
        oXL.Quit();
        oXL = null;
        idTmr = window.setInterval("Cleanup();",1);
    }
}
/////ȥ����߿ظ�
function LTrim(str){
	if ((typeof(str) != "string") || !str) return "";
	for(var i=0; i<str.length; i++){if (str.charCodeAt(i, 10)!=32) break;}
	return str.substring(i, str.length);
}

//ȥ���ұ߿ظ�
function RTrim(str){
	if ((typeof(str) != "string") || !str) return "";
	for(var i=str.length-1; i>=0; i--){if (str.charCodeAt(i, 10)!=32) break;}
	return str.substr(0, i+1);
}
/// /ȥ���ظ�
function trimStr(str){
	if ((typeof(str) != "string") || !str) return "";
	return LTrim(RTrim(str));
}

/*
* ���ڸ�ʽ�ж�
* */
/*
* �ж����ڣ���ʽ��yyyy-mm-dd
* */
function checkDate(fld) {
    var mo, day, yr;
    var entry;
    //haiyu huang 2010-6-24 ����ж�
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
//                    alert("�����������");
                    return false;
                }
            } else {
//                alert("�����·�����");
                return false;
            }
        } else {
//            alert("�����շ�����!");
            return false;
        }
    } else {
//        alert("����ȷ���ڸ�ʽ. ���������ʽ���� yyyy-mm-dd.");
        return false;
    }
    return true;
}
