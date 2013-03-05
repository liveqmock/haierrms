package haier.rms.sbs.statistics;

import haier.repository.model.sbsreport.ActbalRankBean;
import haier.repository.model.sbsreport.CorpBalanceBean;
import haier.service.rms.sbsbatch.ActbalService;
import org.apache.commons.lang.StringUtils;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pub.tools.MessageUtil;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: zhanrui
 * Date: 11-3-18
 * Time: ????11:20
 * To change this template use File | Settings | File Templates.
 */

@ManagedBean
public class ActbalStatisticsAction implements Serializable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @ManagedProperty(value = "#{actbalService}")
    private ActbalService actbalService;

    private StreamedContent piechart;
    private StreamedContent barchart;
    private StreamedContent linechart1;
    private StreamedContent linechart2;

    private List<ActbalRankBean> actbalList;
    private String startdate;

    private String path;

    public List<ActbalRankBean> getActbalList() {
        return actbalList;
    }

    public void setActbalList(List<ActbalRankBean> actbalList) {
        this.actbalList = actbalList;
    }

    public StreamedContent getPiechart() {
        return piechart;
    }

    public void setPiechart(StreamedContent piechart) {
        this.piechart = piechart;
    }

    public StreamedContent getBarchart() {
        return barchart;
    }

    public void setBarchart(StreamedContent barchart) {
        this.barchart = barchart;
    }

    public void setActbalService(ActbalService actbalService) {
        this.actbalService = actbalService;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public StreamedContent getLinechart1() {
        return linechart1;
    }

    public void setLinechart1(StreamedContent linechart1) {
        this.linechart1 = linechart1;
    }

    public StreamedContent getLinechart2() {
        return linechart2;
    }

    public void setLinechart2(StreamedContent linechart2) {
        this.linechart2 = linechart2;
    }
//====================================================

    @PostConstruct
    public void init() {
        //DateTime dt = new DateTime();
        //this.startdate = dt.minusMonths(1).toString("yyyy��MM��");


        FacesContext context = FacesContext.getCurrentInstance();
        Map params = context.getExternalContext().getSessionMap();
        this.startdate = (String) params.get("date");
        //params.remove("path");
        if (StringUtils.isEmpty(startdate)) {
            logger.error("���ڲ�������");
            MessageUtil.addError("���ڲ�������");
            return;
        }
        this.path = (String) params.get("path");
        //params.remove("path");
        if (StringUtils.isEmpty(path)) {
            logger.error("����ģʽ��������");
            MessageUtil.addError("����ģʽ��������");
            return;
        }
        start();
    }

    public void start() {

        int chartflag = 0;
        String title = "";
        if ("increase".equals(this.path)) {
            chartflag = 0;
            title = startdate + "�����������";
        }
        if ("decrease".equals(this.path)) {
            chartflag = 1;
            title = startdate + "����������";
        }

        CategoryDataset categoryDataset = createBalanceRankCategoryDataset(chartflag);
        if (categoryDataset == null) {
            MessageUtil.addError("δ������������ݡ�");
            return;
        }
        createHorizontalBarChart(categoryDataset, null, null, title, "barchart");

        CategoryDataset lineCategoryDataset = createBalanceCategoryDatasetForLineChart(
                this.actbalList.get(0).getActname(),this.startdate);
        if (lineCategoryDataset == null) {
            MessageUtil.addError("δ������������ݡ�");
            return;
        }
        this.linechart1 = createTimeXYChar(this.actbalList.get(0).getActname(), "","", lineCategoryDataset, "linechart1");

        lineCategoryDataset = createBalanceCategoryDatasetForLineChart(
                this.actbalList.get(1).getActname(),this.startdate);
        if (lineCategoryDataset == null) {
            MessageUtil.addError("δ������������ݡ�");
            return;
        }
        this.linechart2 = createTimeXYChar(this.actbalList.get(1).getActname(), "","", lineCategoryDataset, "linechart2");
    }


    /**
     * ��ҵ���仯����
     *
     * @return
     */
    private CategoryDataset createBalanceRankCategoryDataset(int chartflag) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        this.actbalList = new ArrayList();
        if (chartflag == 0) {
            this.actbalList = actbalService.selectCustomerBalanceIncreaseTop10(this.startdate);
        }
        if (chartflag == 1) {
            this.actbalList = actbalService.selectCustomerBalanceDecreaseTop10(this.startdate);
        }
        if (actbalList == null || actbalList.size() == 0) {
            return null;
        }
        for (ActbalRankBean record : actbalList) {
//            dataset.addValue(record.getBalamt(), record.getActname(), record.getActname());
//            dataset.addValue(record.getBalamt(), record.getActname(), record.getSeqno());
            dataset.addValue(record.getBalamt(), record.getSeqno(), record.getActname());
        }

        return dataset;
    }

    /**
     * ��ҵ������ڱ仯����
     *
     * @return
     */
    private CategoryDataset createBalanceCategoryDatasetForLineChart(String actname, String yearmonth) {
        try {
            Date date = new SimpleDateFormat("yyyy��MM��").parse(yearmonth);
            yearmonth = new SimpleDateFormat("yyyy-MM").format(date);
        } catch (ParseException e) {
            logger.info("�����������");
            throw new RuntimeException("�����������");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CorpBalanceBean> records = actbalService.selectCustomerBalanceOneMonth(actname,yearmonth);
        for (CorpBalanceBean record : records) {
            dataset.addValue(record.getBalamt(), record.getCorpname(), record.getTxndate().substring(8,10));
        }

        return dataset;
    }


    /**
     * ����ͼ
     *
     * @param xName      x���˵���������࣬ʱ��ȣ�
     * @param yName      y���˵�������ٶȣ�ʱ��ȣ�
     * @param chartTitle ͼ����
     * @param charName   ����ͼƬ������
     * @return
     */
    public String createHorizontalBarChart(CategoryDataset dataset, String xName, String yName, String chartTitle, String charName) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // ͼ�����
                xName, // Ŀ¼�����ʾ��ǩ
                yName, // ��ֵ�����ʾ��ǩ
                dataset, // ���ݼ�
                PlotOrientation.VERTICAL, // ͼ����ˮƽ����ֱ
                false, // �Ƿ���ʾͼ��(���ڼ򵥵���״ͼ������false)
                false, // �Ƿ����ɹ���
                false // �Ƿ�����URL����
        );

        chart.getTitle().setFont(new Font("����", Font.BOLD, 18));

        CategoryPlot plot = chart.getCategoryPlot();
        // �����ᾫ��
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        //���ÿ̶ȱ����0��ʼ
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0");
        vn.setNumberFormatOverride(df); // ���������ݱ�ǩ����ʾ��ʽ

        CategoryAxis domainAxis = plot.getDomainAxis();

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�
        // Lable
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 14);

        domainAxis.setLabelFont(labelFont);// �����
        domainAxis.setTickLabelFont(labelFont);// ����ֵ

        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);// �����ϵ� Lable �Ƿ�������ʾ
        // domainAxis.setVerticalCategoryLabels(false);
        plot.setDomainAxis(domainAxis);

        ValueAxis rangeAxis = plot.getRangeAxis();
        // ������ߵ�һ�� Item ��ͼƬ���˵ľ���
        rangeAxis.setUpperMargin(0.15);
        // ������͵�һ�� Item ��ͼƬ�׶˵ľ���
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);
        BarRenderer renderer = new BarRenderer();
        // �������ӿ��
        renderer.setMaximumBarWidth(30);
        // �������Ӹ߶�
        renderer.setMinimumBarLength(20);

        renderer.setBaseOutlinePaint(Color.BLACK);

        /*
        // ����������ɫ
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesPaint(1, new Color(0, 0, 255));
        // ����ÿ��������������ƽ������֮�����
        renderer.setItemMargin(0.1);
        // ��ʾÿ��������ֵ�����޸ĸ���ֵ����������
        */
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // ����������ֵ�ɼ�
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // ��������͸����
        //plot.setForegroundAlpha(0.6f);

        try {
            File chartFile = new File("vbarchart.png");
            ChartUtilities.saveChartAsPNG(chartFile, chart, 850, 500);
            this.barchart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("ͳ��ͼ�����ɴ���", e);
            throw new RuntimeException("ͳ��ͼ�����ɴ���", e);
        }

        return null;
    }

    /**
     * ����ͼ
     *
     * @param chartTitle
     * @param x
     * @param y
     * @param xyDataset
     * @param chartName
     * @return
     */

    public StreamedContent createTimeXYChar(String chartTitle, String x, String y,
                                   CategoryDataset xyDataset, String chartName) {

        JFreeChart chart = ChartFactory.createLineChart(chartTitle, x, y,
                xyDataset, PlotOrientation.VERTICAL, false, true, false);

        chart.setTextAntiAlias(false);
        chart.setBackgroundPaint(Color.WHITE);
        // ����ͼ�����������������title
        Font font = new Font("����", Font.BOLD, 18);
        TextTitle title = new TextTitle(chartTitle);
        title.setFont(font);
        chart.setTitle(title);
        // �����������
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        // x�� // �����������Ƿ�ɼ�
        categoryplot.setDomainGridlinesVisible(false);
        // y�� //�����������Ƿ�ɼ�
        categoryplot.setRangeGridlinesVisible(true);

        categoryplot.setRangeGridlinePaint(Color.WHITE);// ����ɫ��

        categoryplot.setDomainGridlinePaint(Color.WHITE);// ����ɫ��

        categoryplot.setBackgroundPaint(Color.lightGray);

        // ����������֮��ľ���
        // categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

        CategoryAxis domainAxis = categoryplot.getDomainAxis();

        domainAxis.setLabelFont(labelFont);// �����
        domainAxis.setTickLabelFont(labelFont);// ����ֵ

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // �����ϵ�
        // Lable
        // 45����б
        // ���þ���ͼƬ��˾���
        domainAxis.setLowerMargin(0.0);
        // ���þ���ͼƬ�Ҷ˾���
        domainAxis.setUpperMargin(0.0);

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setAutoRangeIncludesZero(true);

        // ���renderer ע���������������͵�lineandshaperenderer����
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();

        lineandshaperenderer.setBaseShapesVisible(true); // series �㣨�����ݵ㣩�ɼ�
        lineandshaperenderer.setBaseLinesVisible(true); // series �㣨�����ݵ㣩�������߿ɼ�

        // ��ʾ�۵�����
        // lineandshaperenderer.setBaseItemLabelGenerator(new
        // StandardCategoryItemLabelGenerator());
        // lineandshaperenderer.setBaseItemLabelsVisible(true);

        try {
            File chartFile = new File(chartName);
            ChartUtilities.saveChartAsPNG(chartFile, chart, 850, 300);
            return new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("ͳ��ͼ�����ɴ���", e);
            throw new RuntimeException("ͳ��ͼ�����ɴ���", e);
        }
    }
}

