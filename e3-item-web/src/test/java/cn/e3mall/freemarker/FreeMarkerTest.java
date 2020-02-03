package cn.e3mall.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.*;

import org.junit.Test;
public class FreeMarkerTest {

    @Test
    public void testFreeMarker()throws Exception{
        //1、创建一个模板文件
        //2、创建一个Configuration对象
        Configuration configuration = new Configuration(Configuration.getVersion());
        //3、设置模板文件保存的目录
        configuration.setDirectoryForTemplateLoading(new File("E:\\Program Files\\IDEA\\e3-mall\\e3-item-web\\src\\main\\webapp\\WEB-INF\\ftl"));
        //4、模板文件的编码格式，一般是utf-8
        configuration.setDefaultEncoding("utf-8");
        //5、加载一个模板文件，创建一个模板对象
//        Template template = configuration.getTemplate("hello.ftl");
        Template template = configuration.getTemplate("student.ftl");
        //6、创建一个数据集，可以是pojo也可以是map，推荐使用map
        Map data = new HashMap();
        data.put("hello","hello freemarker!");
        //创建一个pojo对象
        Student student = new Student(1,"林允儿",18,"韩国");
        data.put("student",student);
        //添加一个List
        List<Student> stuList = new ArrayList<>();
        stuList.add(new Student(1,"林允儿1",18,"韩国"));
        stuList.add(new Student(2,"林允儿2",28,"韩国"));
        stuList.add(new Student(3,"林允儿3",38,"韩国"));
        stuList.add(new Student(4,"林允儿4",48,"韩国"));
        stuList.add(new Student(5,"林允儿5",58,"韩国"));
        stuList.add(new Student(6,"林允儿6",68,"韩国"));
        stuList.add(new Student(7,"林允儿7",78,"韩国"));
        stuList.add(new Student(8,"林允儿8",88,"韩国"));
        data.put("stuList",stuList);
        //添加日期类型
        data.put("date",new Date());
        //null值测试
        data.put("val",12);
        //7、创建一个Writer对象，指定输出文件的路径及文件名
        Writer writer = new FileWriter(new File("E:\\upload\\freemarker\\student.html"));
        //8、生成静态页面
        template.process(data,writer);
        //9、关闭流
        writer.close();
    }
}
