package haier.rms.sbs.balance;

import org.apache.commons.lang.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import pub.platform.advance.utils.PropertyManager;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-2-16
 * Time: 下午1:25
 * To change this template use File | Settings | File Templates.
 */
@ManagedBean
public class ExcelDownloadAction {
        private String reportFileName;
    private StreamedContent reportFile;

    public StreamedContent getReportFile() {
        return reportFile;
    }

    public void setReportFile(StreamedContent reportFile) {
        this.reportFile = reportFile;
    }

    public String getReportFileName() {
        return reportFileName;
    }

    public void setReportFileName(String reportFileName) {
        this.reportFileName = reportFileName;
    }

    public ExcelDownloadAction() {
        this.reportFileName = (String)FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("ExcelFileName");
        //FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("ExcelFileName");
        if (StringUtils.isEmpty(this.reportFileName)) {
            return;
        }
        FacesContext context = FacesContext.getCurrentInstance();

        //文件绝对路径
        String excelName = PropertyManager.getProperty("REPORT_ROOTPATH") + "temp/" + this.reportFileName;
        File exportFile = new File(excelName);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(exportFile);
        } catch (FileNotFoundException e) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "EXCEL文件不存在。", null));
            return;
        }
        this.reportFile = new DefaultStreamedContent(fis, "application/excel",this.reportFileName);

    }
}
