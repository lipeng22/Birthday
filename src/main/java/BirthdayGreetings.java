import com.sun.mail.util.MailSSLSocketFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 生日祝福
 * 日期：2018-03-02
 */
public class BirthdayGreetings {
    // 姓
    public static final int SURNAME = 0;

    // 名
    public static final int NAME = 1;

    // 生日
    public static final int BIRTHDAY = 2;

    // 邮箱
    public static final int EMAIL = 3;

    // 文件路径
    public static final String PATHNAME = "F:\\employee_records.txt";

    // 发件人电子邮箱
    public static final String SENDER = "1733144213@qq.com";

    // 发件人邮箱密码
    public static final String PASSWORD = "mscyvqvagshnciie";

    // 主题
    public static final String SUBJECT = "Happy birthday!";

    // 发送内容
    public static final String CONTENT= "Happy birthday,dear ";

    /**
     * 从文件中读取内容保存为List
     * @return
     * @throws IOException
     * @throws ParseException
     */
    public static List<Employee> readFile() throws IOException, ParseException {
        File f = new File(PATHNAME);
        BufferedReader bf = null;
        try {
            FileInputStream fis = new FileInputStream(f);
            InputStreamReader isr = new InputStreamReader(fis,"gb2312"); //指定以UTF-8编码读入
            bf = new BufferedReader(isr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String str;
        List<String> array = new ArrayList();

        while((str=bf.readLine())!=null)
        {
            array.add(str);
        }

        List<Employee> list = new ArrayList<>();
        for(int i = 0;i < array.size();i++) {
            String[] s =array.get(i).split("，");
            Employee employee = new Employee();
            employee.setSurname(s[SURNAME]);
            employee.setName(s[NAME]);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
            employee.setBirthday(simpleDateFormat.parse(s[BIRTHDAY]));
            employee.setEmail(s[EMAIL]);

            list.add(employee);
        }
        return list;
    }

    /**
     * QQ邮箱发送邮件
     * @param name
     * @param email
     * @throws GeneralSecurityException
     */
    public static void sendQQMail(String name, String email) throws GeneralSecurityException {
        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(SENDER, PASSWORD); //发件人邮件用户名、密码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(SENDER));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

            // 主题
            message.setSubject(SUBJECT);

            // 消息内容
            message.setText(CONTENT + name);

            // 发送消息
            Transport.send(message);
            System.out.println("发送短信成功");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
}
