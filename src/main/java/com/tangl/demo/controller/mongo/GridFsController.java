package com.tangl.demo.controller.mongo;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.tangl.demo.annotation.LogAnno;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.Map;

/**
 * @author: TangLiang
 * @date: 2020/8/3 20:23
 * @since: 1.0
 */
@Controller
@Api(tags = "文件存储管理")
@RequestMapping("/manageGridFs")
public class GridFsController {
    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket gridFSBucket;

    /**
     * GridFs存储文件的测试
     * 将文件按大小256k分割存到mongodb数据库
     * 将分割后的文件分记录存到fs.files表
     * 将完整文件信息存到fs.chunks表
     *
     * @throws FileNotFoundException
     */
    @LogAnno(operateType = "上传文件至mongodb")
    @ApiOperation("上传文件")
    @RequestMapping(value = "/uploadFS", method = RequestMethod.GET)
    @ResponseBody
    public Map listLogs() throws FileNotFoundException {
        //获取要存储的文件
        File file = new File("E:\\xfmovie\\adnadd065un.rmvb");
        //将要存储的文件写入输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        //文件开始存储
        ObjectId objectId = gridFsTemplate.store(fileInputStream, "轮播图测试文件01", "");
        //获取存储的文件id
        String fileId = objectId.toString();
        System.out.println(fileId);
        return null;
    }

    /**
     * GridFs读取文件的测试
     * 读取数据库中的文件，以字符形式展示
     *
     * @throws IOException
     */
    @LogAnno(operateType = "读取mongo文件")
    @ApiOperation("读取mongo文件")
    @RequestMapping(value = "/readFs", method = RequestMethod.GET)
    @ResponseBody
    public Map listone() throws IOException {
        //根据文件id查询文件
        GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is("5f77376c58df9371f0d66149")));

        //使用GridFsBucket打开一个下载流对象
        GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建GridFsResource对象，获取流
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //从流中取数据
        String s = IOUtils.toString(gridFsResource.getInputStream(), "UTF-8");
        System.out.println(s);
        return null;
    }

    @LogAnno(operateType = "下载mongo文件")
    @ApiOperation("下载mongo文件")
    @RequestMapping(value = "/downFs", method = RequestMethod.GET)
    @ResponseBody
    public Map download() throws IOException {
        String fileId = "5f77376c58df9371f0d66149";
        //根据id查询文件
        GridFSFile gridFSFile =
                gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        //打开下载流对象
        GridFSDownloadStream gridFSDownloadStream =
                gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
        //创建gridFsResource，用于获取流对象
        GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
        //获取流中的数据
        InputStream inputStream = gridFsResource.getInputStream();
        File f1 = new File("D:\\get.rmvb");
        if (!f1.exists()) {
            f1.getParentFile().mkdirs();
        }
        byte[] bytes = new byte[1024];
        // 创建基于文件的输出流
        FileOutputStream fos = new FileOutputStream(f1);
        int len = 0;
        while ((len = inputStream.read(bytes)) != -1) {
            fos.write(bytes, 0, len);
        }
        inputStream.close();
        fos.close();
        return null;
    }

    @LogAnno(operateType = "删除mongo文件")
    @ApiOperation("删除mongo文件")
    @RequestMapping(value = "/delFs", method = RequestMethod.GET)
    @ResponseBody
    public Map delFs() {
        //根据文件id删除fs.files表和fs.chunks表中的记录
        gridFsTemplate.delete(Query.query(Criteria.where("_id").is("5f77376c58df9371f0d66149")));
        return null;
    }
}
