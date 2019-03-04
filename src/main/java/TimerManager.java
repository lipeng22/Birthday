import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

/**
 * 每天检查是否有邮件需要发送
 * 日期：2018-03-02
 */
public class TimerManager {

    //时间间隔(一天)
    private static final long PERIOD_DAY = 24 * 60 * 60 * 1000;

    public TimerManager() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,9);
        calendar.set(Calendar.MINUTE, 53);
        calendar.set(Calendar.SECOND, 0);
        Date date=calendar.getTime(); //第一次执行定时任务的时间

        if (date.before(new Date())) {
            date = this.addDay(date, 1);
        }
        Timer timer = new Timer();

        Task task = new Task();
        //安排指定的任务在指定的时间开始进行重复的固定延迟执行。
        timer.schedule(task, date, PERIOD_DAY);
    }

    /**
     * 增加或减少天数
     * @param date
     * @param num
     * @return
     */
    public Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }
}
