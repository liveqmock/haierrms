package haier.service.fundmonitor;

import haier.activemq.service.sbs.core.SBSRequest;
import haier.activemq.service.sbs.core.SBSResponse4SingleRecord;
import haier.activemq.service.sbs.txn.Tn062.Tn062Handler;
import haier.activemq.service.sbs.txn.Tn062.Tn062SOFDataDetail;
import haier.repository.dao.MtActbalMapper;
import haier.repository.model.MtActbal;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-7-1
 * Time: 上午6:50
 * To change this template use File | Settings | File Templates.
 */
public class CcbActBalQryThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private int index;
    private String actno;
    private String strdate;
    private String strtime;

    private MtActbalMapper mtActbalMapper;

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
//            Runnable r = new CcbActBalQryThread(i, "12333", "20110101", "10:10:10");
//            new Thread(r, "ThreadName" + i).start();
        }
    }

    public CcbActBalQryThread(int index, String actno, String strdate, String strtime, MtActbalMapper mtActbalMapper) {
        this.index = index;
        this.actno = actno;
        this.strdate = strdate;
        this.strtime = strtime;
        this.mtActbalMapper = mtActbalMapper;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "INDEX:" + index);
        try {
            queryAndInsertOneActBalFromCCB();
        } catch (Exception e) {
            //TODO
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public void queryAndInsertOneActBalFromCCB() {
        SBSResponse4SingleRecord response = processSbsTxn_n062(actno);

        Tn062SOFDataDetail sofDataDetail = (Tn062SOFDataDetail) response.getSofDataDetail();

        MtActbal mtActbal = new MtActbal();

        mtActbal.setBankcd("105");
        mtActbal.setActno(actno);

        if (!response.getFormcode().substring(0, 1).equals("T")) {
            logger.error("交易异常！SBS返回FORM号：" + response.getFormcode() + response.getForminfo() + " 帐号：" + actno);
            mtActbal.setBalamt(new BigDecimal(0));
            mtActbal.setRmbamt(new BigDecimal(0));
            String forminfo = response.getForminfo();
            if (forminfo.length() > 200) {
                forminfo = forminfo.substring(0, 200);
            }
            mtActbal.setRemark(forminfo);
        } else {
            String strActbal = sofDataDetail.getActbal();
            BigDecimal balamt = new BigDecimal(strActbal);
            mtActbal.setBalamt(balamt);
            mtActbal.setRmbamt(balamt);

        }

        mtActbal.setExrate(new BigDecimal(100));
        mtActbal.setQrydate(strdate);
        mtActbal.setQrytime(strtime);

        this.mtActbalMapper.insert(mtActbal);
//        this.ccbActBalDBService.insertOneRecord(mtActbal);
    }

    private SBSResponse4SingleRecord processSbsTxn_n062(String actno) {
        Tn062Handler handler = new Tn062Handler();
        List<String> paramList = new ArrayList<String>();
        paramList.add(StringUtils.rightPad(actno, 22, ' '));//账号	X(22)
        paramList.add(StringUtils.rightPad("105", 12, ' '));//行号	X(12)
        paramList.add("CNY");//3位币别

        SBSRequest request = new SBSRequest("n062", paramList);
        SBSResponse4SingleRecord response = new SBSResponse4SingleRecord();
        response.setSofDataDetail(new Tn062SOFDataDetail());

        handler.run(request, response);
        return response;
    }
}
