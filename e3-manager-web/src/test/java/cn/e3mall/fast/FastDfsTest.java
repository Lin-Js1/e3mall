package cn.e3mall.fast;

import cn.e3mall.common.utils.FastDFSClient;
import org.csource.fastdfs.*;
import org.junit.Test;

import java.net.URLDecoder;

public class FastDfsTest {

    @Test
    public void testUpload()throws Exception{
        //创建一个配置文件，文件名任意，内容就是tracker服务器的地址
        //使用全局对象加载配置文件
        ClientGlobal.init("E:\\Program Files\\IDEA\\e3-mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        //创建一个TrackerClient对象
        TrackerClient client = new TrackerClient();
        //通过TrackClient获得一个TrackerServer对象
        TrackerServer trackerServer = client.getConnection();
        //创建一个StorageServer的引用 可以是null
        StorageServer storageServer = null;
        //创建一个StorageClient，参数需要TrackerServer和StorageServer
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //使用StorageClient上传文件
        String[] jpgs = storageClient.upload_file("C:\\Users\\Lin\\iphone\\1.jpg", "jpg", null);
        for (String jsp :
                jpgs) {
            System.out.println(jsp);
        }
    }

    @Test
    public void testFastDfsClient()throws Exception{
        FastDFSClient fastDFSClient = new FastDFSClient("E:\\Program Files\\IDEA\\e3-mall\\e3-manager-web\\src\\main\\resources\\conf\\client.conf");
        String string = fastDFSClient.uploadFile("C:\\Users\\Lin\\iphone\\2.jpg");
        System.out.println(string);
    }

    @Test
    public void testaa()throws Exception{
        String conf = "classpath:conf/client.conf";
        if (conf.contains("classpath:")) {
            conf = conf.replace("classpath:", this.getClass().getResource("/").getPath());
            System.out.println(conf);
            conf = URLDecoder.decode(conf,"UTF-8");
            System.out.println(conf);
        }
    }

    @Test
    public void testa2a()throws Exception{
        String ids ="11,55,65";
        String[] split = ids.split(",");
        for (String s :
                split) {

           // System.out.println(s);
        }
        String i = "11";
        String[] split1 = i.split(",");
        for (String s1 :
                split1) {

             System.out.println(s1);
        }
    }

}
