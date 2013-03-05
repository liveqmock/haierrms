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
        //this.startdate = dt.minusMonths(1).toString("yyyy年MM月");


        FacesContext context = FacesContext.getCurrentInstance();
        Map params = context.getExternalContext().getSessionMap();
        this.startdate = (String) params.get("date");
        //params.remove("path");
        if (StringUtils.isEmpty(startdate)) {
            logger.error("日期参数错误！");
            MessageUtil.addError("日期参数错误！");
            return;
        }
        this.path = (String) params.get("path");
        //params.remove("path");
        if (StringUtils.isEmpty(path)) {
            logger.error("输入模式参数错误！");
            MessageUtil.addError("输入模式参数错误！");
            return;
        }
        start();
    }

    public void start() {

        int chartflag = 0;
        String title = "";
        if ("increase".equals(this.path)) {
            chartflag = 0;
            title = startdate + "余额增加排名";
        }
        if ("decrease".equals(this.path)) {
            chartflag = 1;
            title = startdate + "余额减少排名";
        }

        CategoryDataset categoryDataset = createBalanceRankCategoryDataset(chartflag);
        if (categoryDataset == null) {
            MessageUtil.addError("未检索到余额数据。");
            return;
        }
        createHorizontalBarChart(categoryDataset, null, null, title, "barchart");

        CategoryDataset lineCategoryDataset = createBalanceCategoryDatasetForLineChart(
                this.actbalList.get(0).getActname(),this.startdate);
        if (lineCategoryDataset == null) {
            MessageUtil.addError("未检索到余额数据。");
            return;
        }
        this.linechart1 = createTimeXYChar(this.actbalList.get(0).getActname(), "","", lineCategoryDataset, "linechart1");

        lineCategoryDataset = createBalanceCategoryDatasetForLineChart(
                this.actbalList.get(1).getActname(),this.startdate);
        if (lineCategoryDataset == null) {
            MessageUtil.addError("未检索到余额数据。");
            return;
        }
        this.linechart2 = createTimeXYChar(this.actbalList.get(1).getActname(), "","", lineCategoryDataset, "linechart2");
    }


    /**
     * 企业余额变化排名
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
     * 企业余额月内变化数据
     *
     * @return
     */
    private CategoryDataset createBalanceCategoryDatasetForLineChart(String actname, String yearmonth) {
        try {
            Date date = new SimpleDateFormat("yyyy年MM月").parse(yearmonth);
            yearmonth = new SimpleDateFormat("yyyy-MM").format(date);
        } catch (ParseException e) {
            logger.info("日期输入错误");
            throw new RuntimeException("日期输入错误");
        }
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        List<CorpBalanceBean> records = actbalService.selectCustomerBalanceOneMonth(actname,yearmonth);
        for (CorpBalanceBean record : records) {
            dataset.addValue(record.getBalamt(), record.getCorpname(), record.getTxndate().substring(8,10));
        }

        return dataset;
    }


    /**
     * 横向图
     *
     * @param xName      x轴的说明（如种类，时间等）
     * @param yName      y轴的说明（如速度，时间等）
     * @param chartTitle 图标题
     * @param charName   生成图片的名字
     * @return
     */
    public String createHorizontalBarChart(CategoryDataset dataset, String xName, String yName, String chartTitle, String charName) {
        JFreeChart chart = ChartFactory.createBarChart(chartTitle, // 图表标题
                xName, // 目录轴的显示标签
                yName, // 数值轴的显示标签
                dataset, // 数据集
                PlotOrientation.VERTICAL, // 图表方向：水平、垂直
                false, // 是否显示图例(对于简单的柱状图必须是false)
                false, // 是否生成工具
                false // 是否生成URL链接
        );

        chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));

        CategoryPlot plot = chart.getCategoryPlot();
        // 数据轴精度
        NumberAxis vn = (NumberAxis) plot.getRangeAxis();
        //设置刻度必须从0开始
        // vn.setAutoRangeIncludesZero(true);
        DecimalFormat df = new DecimalFormat("#0");
        vn.setNumberFormatOverride(df); // 数据轴数据标签的显示格式

        CategoryAxis domainAxis = plot.getDomainAxis();

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的
        // Lable
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 14);

        domainAxis.setLabelFont(labelFont);// 轴标题
        domainAxis.setTickLabelFont(labelFont);// 轴数值

        domainAxis.setMaximumCategoryLabelWidthRatio(0.8f);// 横轴上的 Lable 是否完整显示
        // domainAxis.setVerticalCategoryLabels(false);
        plot.setDomainAxis(domainAxis);

        ValueAxis rangeAxis = plot.getRangeAxis();
        // 设置最高的一个 Item 与图片顶端的距离
        rangeAxis.setUpperMargin(0.15);
        // 设置最低的一个 Item 与图片底端的距离
        rangeAxis.setLowerMargin(0.15);
        plot.setRangeAxis(rangeAxis);
        BarRenderer renderer = new BarRenderer();
        // 设置柱子宽度
        renderer.setMaximumBarWidth(30);
        // 设置柱子高度
        renderer.setMinimumBarLength(20);

        renderer.setBaseOutlinePaint(Color.BLACK);

        /*
        // 设置柱的颜色
        renderer.setSeriesPaint(0, Color.GREEN);
        renderer.setSeriesPaint(1, new Color(0, 0, 255));
        // 设置每个地区所包含的平行柱的之间距离
        renderer.setItemMargin(0.1);
        // 显示每个柱的数值，并修改该数值的字体属性
        */
        renderer.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        // 设置柱的数值可见
        renderer.setBaseItemLabelsVisible(true);

        plot.setRenderer(renderer);
        // 设置柱的透明度
        //plot.setForegroundAlpha(0.6f);

        try {
            File chartFile = new File("vbarchart.png");
            ChartUtilities.saveChartAsPNG(chartFile, chart, 850, 500);
            this.barchart = new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("统计图表生成错误", e);
            throw new RuntimeException("统计图表生成错误", e);
        }

        return null;
    }

    /**
     * 折线图
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
        // 设置图标题的字体重新设置title
        Font font = new Font("宋体", Font.BOLD, 18);
        TextTitle title = new TextTitle(chartTitle);
        title.setFont(font);
        chart.setTitle(title);
        // 设置面板字体
        Font labelFont = new Font("SansSerif", Font.TRUETYPE_FONT, 12);

        chart.setBackgroundPaint(Color.WHITE);

        CategoryPlot categoryplot = (CategoryPlot) chart.getPlot();
        // x轴 // 分类轴网格是否可见
        categoryplot.setDomainGridlinesVisible(false);
        // y轴 //数据轴网格是否可见
        categoryplot.setRangeGridlinesVisible(true);

        categoryplot.setRangeGridlinePaint(Color.WHITE);// 虚线色彩

        categoryplot.setDomainGridlinePaint(Color.WHITE);// 虚线色彩

        categoryplot.setBackgroundPaint(Color.lightGray);

        // 设置轴和面板之间的距离
        // categoryplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));

        CategoryAxis domainAxis = categoryplot.getDomainAxis();

        domainAxis.setLabelFont(labelFont);// 轴标题
        domainAxis.setTickLabelFont(labelFont);// 轴数值

        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45); // 横轴上的
        // Lable
        // 45度倾斜
        // 设置距离图片左端距离
        domainAxis.setLowerMargin(0.0);
        // 设置距离图片右端距离
        domainAxis.setUpperMargin(0.0);

        NumberAxis numberaxis = (NumberAxis) categoryplot.getRangeAxis();
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis.setAutoRangeIncludesZero(true);

        // 获得renderer 注意这里是下嗍造型到lineandshaperenderer！！
        LineAndShapeRenderer lineandshaperenderer = (LineAndShapeRenderer) categoryplot.getRenderer();

        lineandshaperenderer.setBaseShapesVisible(true); // series 点（即数据点）可见
        lineandshaperenderer.setBaseLinesVisible(true); // series 点（即数据点）间有连线可见

        // 显示折点数据
        // lineandshaperenderer.setBaseItemLabelGenerator(new
        // StandardCategoryItemLabelGenerator());
        // lineandshaperenderer.setBaseItemLabelsVisible(true);

        try {
            File chartFile = new File(chartName);
            ChartUtilities.saveChartAsPNG(chartFile, chart, 850, 300);
            return new DefaultStreamedContent(new FileInputStream(chartFile), "image/png");
        } catch (Exception e) {
            logger.error("统计图表生成错误", e);
            throw new RuntimeException("统计图表生成错误", e);
        }
    }
}

