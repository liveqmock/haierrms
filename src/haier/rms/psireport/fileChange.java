package haier.rms.psireport;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-9-27
 * Time: 16:54:45
 * To change this template use File | Settings | File Templates.
 */
public class fileChange {
    private static HSSFWorkbook wb = new HSSFWorkbook(); 
    private static HSSFSheet sheet = wb.createSheet();
    public static void main(String[] args) {
/*
        ExportExcel ext = new ExportExcel(wb,sheet);
        ext.createHead(true);
        String strPath = "D:\\าตฮ๑\\xing\\2010-09-26_nsm-old.lst";
        readData rd=new readData();
        List al = new ArrayList();
        al = rd.getDataList(strPath,"|");
        Iterator<nsmbean> it = al.iterator();
        try {
            while (it.hasNext()) {
                nsmbean n = it.next();
                ext.createBody(true,n);
    //            System.out.println(n.getBorrowamt());
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ext.outputExcel("d:\\tt.xls");
*/
    }
}
