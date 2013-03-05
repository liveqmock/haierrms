package haier.rms.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class RMSCORPPAYFEE extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new RMSCORPPAYFEE().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new RMSCORPPAYFEE().findAndLockByWhere(sSqlWhere);      }       public static RMSCORPPAYFEE findFirst(String sSqlWhere) {           return (RMSCORPPAYFEE)new RMSCORPPAYFEE().findFirstByWhere(sSqlWhere);      }       public static RMSCORPPAYFEE findFirstAndLock(String sSqlWhere) {           return (RMSCORPPAYFEE)new RMSCORPPAYFEE().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new RMSCORPPAYFEE().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           RMSCORPPAYFEE bean = new RMSCORPPAYFEE();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           RMSCORPPAYFEE bean = new RMSCORPPAYFEE();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static RMSCORPPAYFEE findFirst(String sSqlWhere,boolean isAutoRelease) {           RMSCORPPAYFEE bean = new RMSCORPPAYFEE();           bean.setAutoRelease(isAutoRelease);           return (RMSCORPPAYFEE)bean.findFirstByWhere(sSqlWhere);      }       public static RMSCORPPAYFEE findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           RMSCORPPAYFEE bean = new RMSCORPPAYFEE();           bean.setAutoRelease(isAutoRelease);           return (RMSCORPPAYFEE)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           RMSCORPPAYFEE bean = new RMSCORPPAYFEE();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new RMSCORPPAYFEE().findByWhereByRow(sSqlWhere,row);      } int id;
String corpacct;
String corpname;
String bankname;
int countnum;
double fee;
public static final String TABLENAME ="rms_corppayfee";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
RMSCORPPAYFEE abb = new RMSCORPPAYFEE();
abb.id=rs.getInt("id");abb.setKeyValue("ID",""+abb.getId());
abb.corpacct=rs.getString("corpacct");abb.setKeyValue("CORPACCT",""+abb.getCorpacct());
abb.corpname=rs.getString("corpname");abb.setKeyValue("CORPNAME",""+abb.getCorpname());
abb.bankname=rs.getString("bankname");abb.setKeyValue("BANKNAME",""+abb.getBankname());
abb.countnum=rs.getInt("countnum");abb.setKeyValue("COUNTNUM",""+abb.getCountnum());
abb.fee=rs.getDouble("fee");abb.setKeyValue("FEE",""+abb.getFee());
list.add(abb);
abb.operate_mode = "edit";
}public int getId() { return this.id;}
public String getCorpacct() { if ( this.corpacct == null ) return ""; return this.corpacct;}
public String getCorpname() { if ( this.corpname == null ) return ""; return this.corpname;}
public String getBankname() { if ( this.bankname == null ) return ""; return this.bankname;}
public int getCountnum() { return this.countnum;}
public double getFee() { return this.fee;}
public void setId(int id) { sqlMaker.setField("id",""+id,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getId()!=id) cf.add("id",this.id+"",id+""); } this.id=id;}
public void setCorpacct(String corpacct) { sqlMaker.setField("corpacct",corpacct,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCorpacct().equals(corpacct)) cf.add("corpacct",this.corpacct,corpacct); } this.corpacct=corpacct;}
public void setCorpname(String corpname) { sqlMaker.setField("corpname",corpname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getCorpname().equals(corpname)) cf.add("corpname",this.corpname,corpname); } this.corpname=corpname;}
public void setBankname(String bankname) { sqlMaker.setField("bankname",bankname,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getBankname().equals(bankname)) cf.add("bankname",this.bankname,bankname); } this.bankname=bankname;}
public void setCountnum(int countnum) { sqlMaker.setField("countnum",""+countnum,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getCountnum()!=countnum) cf.add("countnum",this.countnum+"",countnum+""); } this.countnum=countnum;}
public void setFee(double fee) { sqlMaker.setField("fee",""+fee,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getFee()!=fee) cf.add("fee",this.fee+"",fee+""); } this.fee=fee;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"id") !=null && actionRequest.getFieldValue(i,"id").trim().length() > 0 ) {this.setId(Integer.parseInt(actionRequest.getFieldValue(i,"id")));}
if ( actionRequest.getFieldValue(i,"corpacct") !=null ) {this.setCorpacct(actionRequest.getFieldValue(i,"corpacct"));}
if ( actionRequest.getFieldValue(i,"corpname") !=null ) {this.setCorpname(actionRequest.getFieldValue(i,"corpname"));}
if ( actionRequest.getFieldValue(i,"bankname") !=null ) {this.setBankname(actionRequest.getFieldValue(i,"bankname"));}
if ( actionRequest.getFieldValue(i,"countnum") !=null && actionRequest.getFieldValue(i,"countnum").trim().length() > 0 ) {this.setCountnum(Integer.parseInt(actionRequest.getFieldValue(i,"countnum")));}
if ( actionRequest.getFieldValue(i,"fee") !=null && actionRequest.getFieldValue(i,"fee").trim().length() > 0 ) {this.setFee(Double.parseDouble(actionRequest.getFieldValue(i,"fee")));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { RMSCORPPAYFEE obj = (RMSCORPPAYFEE)super.clone();obj.setId(obj.id);
obj.setCorpacct(obj.corpacct);
obj.setCorpname(obj.corpname);
obj.setBankname(obj.bankname);
obj.setCountnum(obj.countnum);
obj.setFee(obj.fee);
return obj;}}