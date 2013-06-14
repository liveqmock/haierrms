package pub.platform.advance.utils;

import com.ccb.dao.SYSSCHEDULER;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SchedulerManager extends HttpServlet {

	static{
		System.out.println("////////////////////////////////////////");
		System.out.println("loading SchedulerManager.class");
		System.out.println("////////////////////////////////////////");
	}

    private static final long serialVersionUID = 1527023683681574743L;

    public static Scheduler scheduler;
    public static Map jobInfoMap = new HashMap();

	// 初始化
    public void init() throws ServletException {
		//System.out.println();
		printLine();
		System.out.println("正在加载调度作业任务信息......");
		try{
			// 加载作业信息			
			SchedulerManager.loadSchedulerInfo();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("加载调度作业任务信息结束！");
		printLine();
	}

	// 销毁
    public void destroy() {
		System.out.println();
		printLine();
		System.out.println("正在关闭所有调度作业任务......");

		try{
			// 关闭调度作业
			SchedulerManager.shutdownScheduler();
		}catch(Exception e){
			e.printStackTrace();
		}

		System.out.println("关闭调度作业任务信息结束！");
		printLine();
	}

	// 重新加载作业信息
	public static void reload(){
		System.out.println();
		printLine();
		System.out.println("开始重新加载作业信息......");
		try {
			SchedulerManager.loadSchedulerInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("重新加载作业信息结束！");
		printLine();
	}

	public static void shutdown(){
		System.out.println();
		printLine();
		System.out.println("开始停止所有作业......");
		try {
			SchedulerManager.shutdownScheduler();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("停止所有作业结束！");
		printLine();
	}

	// 加载作业信息
	private static synchronized void loadSchedulerInfo()throws Exception{

		SchedulerManager.shutdownScheduler();

		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		SchedulerManager.scheduler = schedulerFactory.getScheduler();

		List list = SYSSCHEDULER.find("", true);

		for(int i=0; i<list.size(); i++){
			SYSSCHEDULER scheuler = (SYSSCHEDULER)list.get(i);
			JobInfo job = new JobInfo(scheuler);
		}

		SchedulerManager.scheduler.start();
	}

	// 关闭调度作业
	private static synchronized void shutdownScheduler()throws Exception{
		if(SchedulerManager.scheduler != null){
			SchedulerManager.scheduler.shutdown(false);
			SchedulerManager.jobInfoMap.clear();
		}
	}

    public void doPost(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {
    	doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    	throws ServletException, IOException {
        response.setContentType("text/html;charset=GBK");
        PrintWriter out = response.getWriter();
    	if("reload".equals(request.getParameter("action"))){
    		System.out.println("********  reload  *********");
    		SchedulerManager.reload();
            out.print("schedule config reloaded...");
    	}else if("shutdown".equals(request.getParameter("action"))){
    		System.out.println("********  shutdown  **********");
    		SchedulerManager.shutdown();
            out.print("schedule config shutdown...");
        }else{
            out.print("使用说明：</br>");
            out.print("1、重新装载调度计划：http://...../../..?action=reload</br>");
            out.print("2、关闭调度任务：http://...../../..?action=shutdown</br>");
        }
    }

	private static void printLine(){
		System.out.println("//////////////////////////" + new Date() + "//////////////////////////");
	}

}


