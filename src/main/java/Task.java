import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

/**
 * 发邮件任务
 * 日期：2018-03-02
 */
public class Task extends TimerTask {
    public void run() {
        try {
            List<Employee> employeeList= BirthdayGreetings.readFile();

            Calendar cal = Calendar.getInstance();
            cal.setTime(new Date());

            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DATE);
            int birthOfMonth;
            int birthOfDay;

            for(int i = 0;i < employeeList.size();i++) {
                Calendar birthday = Calendar.getInstance();
                birthday.setTime(employeeList.get(i).getBirthday());
                birthOfMonth = birthday.get(Calendar.MONTH);
                birthOfDay = birthday.get(Calendar.DATE);

                if(month == birthOfMonth && day == birthOfDay ) {
                    BirthdayGreetings.sendQQMail(employeeList.get(i).getName(),
                            employeeList.get(i).getEmail());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }
}