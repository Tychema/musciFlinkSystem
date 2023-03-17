package hdfs;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.yarn.webapp.hamlet2.Hamlet;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 客户端模板
 * 1、获取客户端对象
 * 2、执行操作命令
 * 3、关闭资源
 */
public class HdfsClient {

    private FileSystem fs;
    @Before
    public void init() throws URISyntaxException, IOException, InterruptedException {
        //连接集群NN地址
        URI uri = new URI("hdfs://hadoop101:8020");
        //配置文件
        Configuration configuration = new Configuration();
        //用户
        String user = "tychema";
        //获取客户端对象
        fs = FileSystem.get(uri, configuration,user);
    }
    @After
    public void close() throws IOException {
        fs.close();
    }

    //创建文件夹
    @Test
    public void testMkdir() throws IOException{
        fs.mkdirs(new Path("/xiyou/shuiliandong"));
    }
    //上传
    @Test
    public void testPut() throws IOException {
        // 参数：1、是否删除原数据 2、是否允许覆盖 3、原数据路径 4、 目的路径
        fs.copyFromLocalFile(false,false,new Path("E:\\tychema\\program\\testTXT\\sunwukong.txt"),new Path("hdfs://hadoop101:8082/"));
    }
    @Test
    public void testGet() throws IOException {
        // 参数：1、是否删除原数据 2、源文件路径 3、目的文件路径 4、是否开启本地校验
        fs.copyToLocalFile(false,new Path("hdfs://hadoop101:8020/"),new Path("E:\\tychema\\program\\testTXT"),false);
    }
    @Test
    public void testRM() throws IOException {
        //参数：1、路径 2、是否递归删除(空目录false非空目录true)
        fs.delete(new Path("hdfs://hadoop101:8020/"),true);
    }
    @Test
    public void testVmOrRename() throws IOException {
        //参数：1、原路径 2、目的路径
        //既可以改名rename也可以移动mv
        fs.rename(new Path("hdfs://hadoop101:8020/"),new Path("hdfs://hadoop101:8020/"));
    }
    @Test
    public void fileDetail() throws IOException {
        //获取到的是一个迭代器
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path("/"), true);
        while(listFiles.hasNext()){
            LocatedFileStatus fileStatus = listFiles.next();
            System.out.println("========="+fileStatus.getPath()+"==========");
            System.out.println("权限"+"    "+"所有者"+"    "+"分组"+"    "+"大小"+"    "+"上次修改时间"+"    "+"备份"+"    "+"块大小"+"    "+"文件名");
            System.out.println(fileStatus.getPermission()+" "+fileStatus.getOwner()+" "+fileStatus.getGroup()+" "+fileStatus.getLen()+" "+fileStatus.getModificationTime()+" "+fileStatus.getReplication()+" "+fileStatus.getBlockSize()+" "+fileStatus.getPath().getName());
            //System.out.println("权限"+fileStatus.getPermission()+"==========");
            //System.out.println("所有者"+fileStatus.getOwner()+"==========");
            //System.out.println("分组"+fileStatus.getGroup()+"==========");
            //System.out.println("大小"+fileStatus.getLen()+"==========");
            //System.out.println("上次修改时间"+fileStatus.getModificationTime()+"==========");
            //System.out.println("备份"+fileStatus.getReplication()+"==========");
            //System.out.println("块大小"+fileStatus.getBlockSize()+"==========");
            //System.out.println("文件名"+fileStatus.getPath().getName()+"==========");
        }
    }
    @Test
    public void testJudgeFileOrFolder() throws IOException {
        FileStatus[] listStatus = fs.listStatus(new Path("/"));
        for(FileStatus status : listStatus){
            if(status.isFile()){
                System.out.println("文件："+status.getPath().getName());
            }else{
                System.out.println("目录"+status.getPath().getName());
            }
        }
    }
}
