package com.ccb.dao;
import java.util.*;
import pub.platform.db.*;
import pub.platform.utils.*;
import pub.platform.db.AbstractBasicBean;
public class EVTMAININFO extends AbstractBasicBean implements Cloneable {
     public static List find(String sSqlWhere) {           return new EVTMAININFO().findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere) {           return new EVTMAININFO().findAndLockByWhere(sSqlWhere);      }       public static EVTMAININFO findFirst(String sSqlWhere) {           return (EVTMAININFO)new EVTMAININFO().findFirstByWhere(sSqlWhere);      }       public static EVTMAININFO findFirstAndLock(String sSqlWhere) {           return (EVTMAININFO)new EVTMAININFO().findFirstAndLockByWhere(sSqlWhere);      }            public static RecordSet findRecordSet(String sSqlWhere) {           return new EVTMAININFO().findRecordSetByWhere(sSqlWhere);      }       public static List find(String sSqlWhere,boolean isAutoRelease) {           EVTMAININFO bean = new EVTMAININFO();           bean.setAutoRelease(isAutoRelease);           return bean.findByWhere(sSqlWhere);      }       public static List findAndLock(String sSqlWhere,boolean isAutoRelease) {           EVTMAININFO bean = new EVTMAININFO();           bean.setAutoRelease(isAutoRelease);           return bean.findAndLockByWhere(sSqlWhere);      }       public static EVTMAININFO findFirst(String sSqlWhere,boolean isAutoRelease) {           EVTMAININFO bean = new EVTMAININFO();           bean.setAutoRelease(isAutoRelease);           return (EVTMAININFO)bean.findFirstByWhere(sSqlWhere);      }       public static EVTMAININFO findFirstAndLock(String sSqlWhere,boolean isAutoRelease) {           EVTMAININFO bean = new EVTMAININFO();           bean.setAutoRelease(isAutoRelease);           return (EVTMAININFO)bean.findFirstAndLockByWhere(sSqlWhere);      }       public static RecordSet findRecordSet(String sSqlWhere,boolean isAutoRelease) {           EVTMAININFO bean = new EVTMAININFO();           bean.setAutoRelease(isAutoRelease);           return bean.findRecordSetByWhere(sSqlWhere);      }      public static List findByRow(String sSqlWhere,int row) {           return new EVTMAININFO().findByWhereByRow(sSqlWhere,row);      } String evt_id;
String evt_name;
String evt_type;
String evt_type_name;
String evt_date;
String evt_time;
String txn_date;
String txn_time;
String succ_flag;
String notify_type;
int succ_count;
int fail_count;
String sms_flag;
String sms_list;
String sms_start_time;
String sms_sent_status;
String sms_err_msg;
String sms_info;
String mail_flag;
String mail_list;
String mail_start_time;
String mail_sent_status;
String mail_err_msg;
String mail_info;
int time_elapsed;
String evt_msg_code;
public static final String TABLENAME ="evt_maininfo";
private String operate_mode = "add";
public ChangeFileds cf = new ChangeFileds();
public String getTableName() {return TABLENAME;}
public void addObject(List list,RecordSet rs) {
EVTMAININFO abb = new EVTMAININFO();
abb.evt_id=rs.getString("evt_id");abb.setKeyValue("EVT_ID",""+abb.getEvt_id());
abb.evt_name=rs.getString("evt_name");abb.setKeyValue("EVT_NAME",""+abb.getEvt_name());
abb.evt_type=rs.getString("evt_type");abb.setKeyValue("EVT_TYPE",""+abb.getEvt_type());
abb.evt_type_name=rs.getString("evt_type_name");abb.setKeyValue("EVT_TYPE_NAME",""+abb.getEvt_type_name());
abb.evt_date=rs.getString("evt_date");abb.setKeyValue("EVT_DATE",""+abb.getEvt_date());
abb.evt_time=rs.getString("evt_time");abb.setKeyValue("EVT_TIME",""+abb.getEvt_time());
abb.txn_date=rs.getString("txn_date");abb.setKeyValue("TXN_DATE",""+abb.getTxn_date());
abb.txn_time=rs.getString("txn_time");abb.setKeyValue("TXN_TIME",""+abb.getTxn_time());
abb.succ_flag=rs.getString("succ_flag");abb.setKeyValue("SUCC_FLAG",""+abb.getSucc_flag());
abb.notify_type=rs.getString("notify_type");abb.setKeyValue("NOTIFY_TYPE",""+abb.getNotify_type());
abb.succ_count=rs.getInt("succ_count");abb.setKeyValue("SUCC_COUNT",""+abb.getSucc_count());
abb.fail_count=rs.getInt("fail_count");abb.setKeyValue("FAIL_COUNT",""+abb.getFail_count());
abb.sms_flag=rs.getString("sms_flag");abb.setKeyValue("SMS_FLAG",""+abb.getSms_flag());
abb.sms_list=rs.getString("sms_list");abb.setKeyValue("SMS_LIST",""+abb.getSms_list());
abb.sms_start_time=rs.getString("sms_start_time");abb.setKeyValue("SMS_START_TIME",""+abb.getSms_start_time());
abb.sms_sent_status=rs.getString("sms_sent_status");abb.setKeyValue("SMS_SENT_STATUS",""+abb.getSms_sent_status());
abb.sms_err_msg=rs.getString("sms_err_msg");abb.setKeyValue("SMS_ERR_MSG",""+abb.getSms_err_msg());
abb.sms_info=rs.getString("sms_info");abb.setKeyValue("SMS_INFO",""+abb.getSms_info());
abb.mail_flag=rs.getString("mail_flag");abb.setKeyValue("MAIL_FLAG",""+abb.getMail_flag());
abb.mail_list=rs.getString("mail_list");abb.setKeyValue("MAIL_LIST",""+abb.getMail_list());
abb.mail_start_time=rs.getString("mail_start_time");abb.setKeyValue("MAIL_START_TIME",""+abb.getMail_start_time());
abb.mail_sent_status=rs.getString("mail_sent_status");abb.setKeyValue("MAIL_SENT_STATUS",""+abb.getMail_sent_status());
abb.mail_err_msg=rs.getString("mail_err_msg");abb.setKeyValue("MAIL_ERR_MSG",""+abb.getMail_err_msg());
abb.mail_info=rs.getString("mail_info");abb.setKeyValue("MAIL_INFO",""+abb.getMail_info());
abb.time_elapsed=rs.getInt("time_elapsed");abb.setKeyValue("TIME_ELAPSED",""+abb.getTime_elapsed());
abb.evt_msg_code=rs.getString("evt_msg_code");abb.setKeyValue("EVT_MSG_CODE",""+abb.getEvt_msg_code());
list.add(abb);
abb.operate_mode = "edit";
}public String getEvt_id() { if ( this.evt_id == null ) return ""; return this.evt_id;}
public String getEvt_name() { if ( this.evt_name == null ) return ""; return this.evt_name;}
public String getEvt_type() { if ( this.evt_type == null ) return ""; return this.evt_type;}
public String getEvt_type_name() { if ( this.evt_type_name == null ) return ""; return this.evt_type_name;}
public String getEvt_date() { if ( this.evt_date == null ) return ""; return this.evt_date;}
public String getEvt_time() { if ( this.evt_time == null ) return ""; return this.evt_time;}
public String getTxn_date() { if ( this.txn_date == null ) return ""; return this.txn_date;}
public String getTxn_time() { if ( this.txn_time == null ) return ""; return this.txn_time;}
public String getSucc_flag() { if ( this.succ_flag == null ) return ""; return this.succ_flag;}
public String getNotify_type() { if ( this.notify_type == null ) return ""; return this.notify_type;}
public int getSucc_count() { return this.succ_count;}
public int getFail_count() { return this.fail_count;}
public String getSms_flag() { if ( this.sms_flag == null ) return ""; return this.sms_flag;}
public String getSms_list() { if ( this.sms_list == null ) return ""; return this.sms_list;}
public String getSms_start_time() { if ( this.sms_start_time == null ) return ""; return this.sms_start_time;}
public String getSms_sent_status() { if ( this.sms_sent_status == null ) return ""; return this.sms_sent_status;}
public String getSms_err_msg() { if ( this.sms_err_msg == null ) return ""; return this.sms_err_msg;}
public String getSms_info() { if ( this.sms_info == null ) return ""; return this.sms_info;}
public String getMail_flag() { if ( this.mail_flag == null ) return ""; return this.mail_flag;}
public String getMail_list() { if ( this.mail_list == null ) return ""; return this.mail_list;}
public String getMail_start_time() { if ( this.mail_start_time == null ) return ""; return this.mail_start_time;}
public String getMail_sent_status() { if ( this.mail_sent_status == null ) return ""; return this.mail_sent_status;}
public String getMail_err_msg() { if ( this.mail_err_msg == null ) return ""; return this.mail_err_msg;}
public String getMail_info() { if ( this.mail_info == null ) return ""; return this.mail_info;}
public int getTime_elapsed() { return this.time_elapsed;}
public String getEvt_msg_code() { if ( this.evt_msg_code == null ) return ""; return this.evt_msg_code;}
public void setEvt_id(String evt_id) { sqlMaker.setField("evt_id",evt_id,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_id().equals(evt_id)) cf.add("evt_id",this.evt_id,evt_id); } this.evt_id=evt_id;}
public void setEvt_name(String evt_name) { sqlMaker.setField("evt_name",evt_name,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_name().equals(evt_name)) cf.add("evt_name",this.evt_name,evt_name); } this.evt_name=evt_name;}
public void setEvt_type(String evt_type) { sqlMaker.setField("evt_type",evt_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_type().equals(evt_type)) cf.add("evt_type",this.evt_type,evt_type); } this.evt_type=evt_type;}
public void setEvt_type_name(String evt_type_name) { sqlMaker.setField("evt_type_name",evt_type_name,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_type_name().equals(evt_type_name)) cf.add("evt_type_name",this.evt_type_name,evt_type_name); } this.evt_type_name=evt_type_name;}
public void setEvt_date(String evt_date) { sqlMaker.setField("evt_date",evt_date,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_date().equals(evt_date)) cf.add("evt_date",this.evt_date,evt_date); } this.evt_date=evt_date;}
public void setEvt_time(String evt_time) { sqlMaker.setField("evt_time",evt_time,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_time().equals(evt_time)) cf.add("evt_time",this.evt_time,evt_time); } this.evt_time=evt_time;}
public void setTxn_date(String txn_date) { sqlMaker.setField("txn_date",txn_date,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTxn_date().equals(txn_date)) cf.add("txn_date",this.txn_date,txn_date); } this.txn_date=txn_date;}
public void setTxn_time(String txn_time) { sqlMaker.setField("txn_time",txn_time,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getTxn_time().equals(txn_time)) cf.add("txn_time",this.txn_time,txn_time); } this.txn_time=txn_time;}
public void setSucc_flag(String succ_flag) { sqlMaker.setField("succ_flag",succ_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSucc_flag().equals(succ_flag)) cf.add("succ_flag",this.succ_flag,succ_flag); } this.succ_flag=succ_flag;}
public void setNotify_type(String notify_type) { sqlMaker.setField("notify_type",notify_type,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getNotify_type().equals(notify_type)) cf.add("notify_type",this.notify_type,notify_type); } this.notify_type=notify_type;}
public void setSucc_count(int succ_count) { sqlMaker.setField("succ_count",""+succ_count,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getSucc_count()!=succ_count) cf.add("succ_count",this.succ_count+"",succ_count+""); } this.succ_count=succ_count;}
public void setFail_count(int fail_count) { sqlMaker.setField("fail_count",""+fail_count,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getFail_count()!=fail_count) cf.add("fail_count",this.fail_count+"",fail_count+""); } this.fail_count=fail_count;}
public void setSms_flag(String sms_flag) { sqlMaker.setField("sms_flag",sms_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_flag().equals(sms_flag)) cf.add("sms_flag",this.sms_flag,sms_flag); } this.sms_flag=sms_flag;}
public void setSms_list(String sms_list) { sqlMaker.setField("sms_list",sms_list,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_list().equals(sms_list)) cf.add("sms_list",this.sms_list,sms_list); } this.sms_list=sms_list;}
public void setSms_start_time(String sms_start_time) { sqlMaker.setField("sms_start_time",sms_start_time,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_start_time().equals(sms_start_time)) cf.add("sms_start_time",this.sms_start_time,sms_start_time); } this.sms_start_time=sms_start_time;}
public void setSms_sent_status(String sms_sent_status) { sqlMaker.setField("sms_sent_status",sms_sent_status,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_sent_status().equals(sms_sent_status)) cf.add("sms_sent_status",this.sms_sent_status,sms_sent_status); } this.sms_sent_status=sms_sent_status;}
public void setSms_err_msg(String sms_err_msg) { sqlMaker.setField("sms_err_msg",sms_err_msg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_err_msg().equals(sms_err_msg)) cf.add("sms_err_msg",this.sms_err_msg,sms_err_msg); } this.sms_err_msg=sms_err_msg;}
public void setSms_info(String sms_info) { sqlMaker.setField("sms_info",sms_info,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getSms_info().equals(sms_info)) cf.add("sms_info",this.sms_info,sms_info); } this.sms_info=sms_info;}
public void setMail_flag(String mail_flag) { sqlMaker.setField("mail_flag",mail_flag,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_flag().equals(mail_flag)) cf.add("mail_flag",this.mail_flag,mail_flag); } this.mail_flag=mail_flag;}
public void setMail_list(String mail_list) { sqlMaker.setField("mail_list",mail_list,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_list().equals(mail_list)) cf.add("mail_list",this.mail_list,mail_list); } this.mail_list=mail_list;}
public void setMail_start_time(String mail_start_time) { sqlMaker.setField("mail_start_time",mail_start_time,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_start_time().equals(mail_start_time)) cf.add("mail_start_time",this.mail_start_time,mail_start_time); } this.mail_start_time=mail_start_time;}
public void setMail_sent_status(String mail_sent_status) { sqlMaker.setField("mail_sent_status",mail_sent_status,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_sent_status().equals(mail_sent_status)) cf.add("mail_sent_status",this.mail_sent_status,mail_sent_status); } this.mail_sent_status=mail_sent_status;}
public void setMail_err_msg(String mail_err_msg) { sqlMaker.setField("mail_err_msg",mail_err_msg,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_err_msg().equals(mail_err_msg)) cf.add("mail_err_msg",this.mail_err_msg,mail_err_msg); } this.mail_err_msg=mail_err_msg;}
public void setMail_info(String mail_info) { sqlMaker.setField("mail_info",mail_info,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getMail_info().equals(mail_info)) cf.add("mail_info",this.mail_info,mail_info); } this.mail_info=mail_info;}
public void setTime_elapsed(int time_elapsed) { sqlMaker.setField("time_elapsed",""+time_elapsed,Field.NUMBER); if (this.operate_mode.equals("edit")) { if (this.getTime_elapsed()!=time_elapsed) cf.add("time_elapsed",this.time_elapsed+"",time_elapsed+""); } this.time_elapsed=time_elapsed;}
public void setEvt_msg_code(String evt_msg_code) { sqlMaker.setField("evt_msg_code",evt_msg_code,Field.TEXT); if (this.operate_mode.equals("edit")) { if (!this.getEvt_msg_code().equals(evt_msg_code)) cf.add("evt_msg_code",this.evt_msg_code,evt_msg_code); } this.evt_msg_code=evt_msg_code;}
public void init(int i,ActionRequest actionRequest) throws Exception { if ( actionRequest.getFieldValue(i,"evt_id") !=null ) {this.setEvt_id(actionRequest.getFieldValue(i,"evt_id"));}
if ( actionRequest.getFieldValue(i,"evt_name") !=null ) {this.setEvt_name(actionRequest.getFieldValue(i,"evt_name"));}
if ( actionRequest.getFieldValue(i,"evt_type") !=null ) {this.setEvt_type(actionRequest.getFieldValue(i,"evt_type"));}
if ( actionRequest.getFieldValue(i,"evt_type_name") !=null ) {this.setEvt_type_name(actionRequest.getFieldValue(i,"evt_type_name"));}
if ( actionRequest.getFieldValue(i,"evt_date") !=null ) {this.setEvt_date(actionRequest.getFieldValue(i,"evt_date"));}
if ( actionRequest.getFieldValue(i,"evt_time") !=null ) {this.setEvt_time(actionRequest.getFieldValue(i,"evt_time"));}
if ( actionRequest.getFieldValue(i,"txn_date") !=null ) {this.setTxn_date(actionRequest.getFieldValue(i,"txn_date"));}
if ( actionRequest.getFieldValue(i,"txn_time") !=null ) {this.setTxn_time(actionRequest.getFieldValue(i,"txn_time"));}
if ( actionRequest.getFieldValue(i,"succ_flag") !=null ) {this.setSucc_flag(actionRequest.getFieldValue(i,"succ_flag"));}
if ( actionRequest.getFieldValue(i,"notify_type") !=null ) {this.setNotify_type(actionRequest.getFieldValue(i,"notify_type"));}
if ( actionRequest.getFieldValue(i,"succ_count") !=null && actionRequest.getFieldValue(i,"succ_count").trim().length() > 0 ) {this.setSucc_count(Integer.parseInt(actionRequest.getFieldValue(i,"succ_count")));}
if ( actionRequest.getFieldValue(i,"fail_count") !=null && actionRequest.getFieldValue(i,"fail_count").trim().length() > 0 ) {this.setFail_count(Integer.parseInt(actionRequest.getFieldValue(i,"fail_count")));}
if ( actionRequest.getFieldValue(i,"sms_flag") !=null ) {this.setSms_flag(actionRequest.getFieldValue(i,"sms_flag"));}
if ( actionRequest.getFieldValue(i,"sms_list") !=null ) {this.setSms_list(actionRequest.getFieldValue(i,"sms_list"));}
if ( actionRequest.getFieldValue(i,"sms_start_time") !=null ) {this.setSms_start_time(actionRequest.getFieldValue(i,"sms_start_time"));}
if ( actionRequest.getFieldValue(i,"sms_sent_status") !=null ) {this.setSms_sent_status(actionRequest.getFieldValue(i,"sms_sent_status"));}
if ( actionRequest.getFieldValue(i,"sms_err_msg") !=null ) {this.setSms_err_msg(actionRequest.getFieldValue(i,"sms_err_msg"));}
if ( actionRequest.getFieldValue(i,"sms_info") !=null ) {this.setSms_info(actionRequest.getFieldValue(i,"sms_info"));}
if ( actionRequest.getFieldValue(i,"mail_flag") !=null ) {this.setMail_flag(actionRequest.getFieldValue(i,"mail_flag"));}
if ( actionRequest.getFieldValue(i,"mail_list") !=null ) {this.setMail_list(actionRequest.getFieldValue(i,"mail_list"));}
if ( actionRequest.getFieldValue(i,"mail_start_time") !=null ) {this.setMail_start_time(actionRequest.getFieldValue(i,"mail_start_time"));}
if ( actionRequest.getFieldValue(i,"mail_sent_status") !=null ) {this.setMail_sent_status(actionRequest.getFieldValue(i,"mail_sent_status"));}
if ( actionRequest.getFieldValue(i,"mail_err_msg") !=null ) {this.setMail_err_msg(actionRequest.getFieldValue(i,"mail_err_msg"));}
if ( actionRequest.getFieldValue(i,"mail_info") !=null ) {this.setMail_info(actionRequest.getFieldValue(i,"mail_info"));}
if ( actionRequest.getFieldValue(i,"time_elapsed") !=null && actionRequest.getFieldValue(i,"time_elapsed").trim().length() > 0 ) {this.setTime_elapsed(Integer.parseInt(actionRequest.getFieldValue(i,"time_elapsed")));}
if ( actionRequest.getFieldValue(i,"evt_msg_code") !=null ) {this.setEvt_msg_code(actionRequest.getFieldValue(i,"evt_msg_code"));}
}public void init(ActionRequest actionRequest) throws Exception { this.init(0,actionRequest);}public void initAll(int i,ActionRequest actionRequest) throws Exception { this.init(i,actionRequest);}public void initAll(ActionRequest actionRequest) throws Exception { this.initAll(0,actionRequest);}public Object clone() throws CloneNotSupportedException { EVTMAININFO obj = (EVTMAININFO)super.clone();obj.setEvt_id(obj.evt_id);
obj.setEvt_name(obj.evt_name);
obj.setEvt_type(obj.evt_type);
obj.setEvt_type_name(obj.evt_type_name);
obj.setEvt_date(obj.evt_date);
obj.setEvt_time(obj.evt_time);
obj.setTxn_date(obj.txn_date);
obj.setTxn_time(obj.txn_time);
obj.setSucc_flag(obj.succ_flag);
obj.setNotify_type(obj.notify_type);
obj.setSucc_count(obj.succ_count);
obj.setFail_count(obj.fail_count);
obj.setSms_flag(obj.sms_flag);
obj.setSms_list(obj.sms_list);
obj.setSms_start_time(obj.sms_start_time);
obj.setSms_sent_status(obj.sms_sent_status);
obj.setSms_err_msg(obj.sms_err_msg);
obj.setSms_info(obj.sms_info);
obj.setMail_flag(obj.mail_flag);
obj.setMail_list(obj.mail_list);
obj.setMail_start_time(obj.mail_start_time);
obj.setMail_sent_status(obj.mail_sent_status);
obj.setMail_err_msg(obj.mail_err_msg);
obj.setMail_info(obj.mail_info);
obj.setTime_elapsed(obj.time_elapsed);
obj.setEvt_msg_code(obj.evt_msg_code);
return obj;}}