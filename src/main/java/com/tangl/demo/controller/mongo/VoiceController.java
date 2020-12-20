package com.tangl.demo.controller.mongo;

import cn.hutool.core.util.IdUtil;
import com.mongodb.BasicDBObject;
import com.tangl.demo.Document.mongo.MgVoice;
import com.tangl.demo.Document.mongo.MgVoiceLibrary;
import com.tangl.demo.annotation.LogAnno;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * mongodb嵌套文档
 *
 * @author: TangLiang
 * @date: 2020/12/20 12:53
 * @since: 1.0
 */
@RestController
@Api(tags = "mongodb嵌套文档")
@RequestMapping("/mongodb/voice")
public class VoiceController {

    @Autowired
    private MongoTemplate mongoTemplate;

    //https://blog.csdn.net/walle167/article/details/51281199
    //http://docs.mongodb.org/manual/reference/operator/update/pull/

    @ApiOperation("新增语音")
    @RequestMapping(value = "/insertVoice", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "voiceType", value = "男女声", dataType = "String", paramType = "query", example = "M 男声  F 女声"),
            @ApiImplicitParam(name = "voiceSpeed", value = "速度", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "mgVoiceLibrary", value = "语音库", dataType = "MgVoiceLibrary")
    })
    @LogAnno(operateType = "新增语音")
    public Object insertVoice(String voiceType, Integer voiceSpeed, MgVoiceLibrary mgVoiceLibrary) {
        MgVoice mgVoice = new MgVoice();
        mgVoice.setCid(IdUtil.objectId());
        mgVoice.setVoiceType(voiceType);
        mgVoice.setVoiceSpeed(voiceSpeed);
        List<MgVoiceLibrary> mgVoiceLibraries = new ArrayList<>();
        mgVoiceLibraries.add(mgVoiceLibrary);
        mgVoiceLibraries.add(mgVoiceLibrary);
        mgVoiceLibraries.add(mgVoiceLibrary);
        mgVoice.setVoiceLibrarys(mgVoiceLibraries);
        return mongoTemplate.save(mgVoice);
    }

    @ApiOperation("修改语音")
    @RequestMapping(value = "/updateVoice", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "id", dataType = "String", paramType = "query", example = "5fdee06891226ff02d33f144"),
            @ApiImplicitParam(name = "voiceType", value = "语音类型", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "voiceSpeed", value = "速度", dataType = "int", paramType = "query")
    })
    @LogAnno(operateType = "修改语音")
    public Object updateVoice(String cid, String voiceType, Integer voiceSpeed) {
        Criteria criteria = Criteria.where("_id").is(cid);
        Query query = new Query(criteria);
        Update update = Update.update("voiceType", voiceType).set("voiceSpeed", voiceSpeed);
        return mongoTemplate.updateFirst(query, update, MgVoice.class);
    }

    @ApiOperation("修改语音嵌套文档内容")
    @RequestMapping(value = "/updateVoiceLibrary", method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "cid", value = "cid", dataType = "String", paramType = "query", example = "5fdee06891226ff02d33f144"),
            @ApiImplicitParam(name = "lid", value = "lid", dataType = "String", paramType = "query", example = "p2_b9dc7616-b476-40ee-8fd7-982711cece98"),
            @ApiImplicitParam(name = "title", value = "标题", dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "content", value = "内容", dataType = "String", paramType = "query")
    })
    @LogAnno(operateType = "修改语音嵌套文档内容")
    public Object updateVoiceLibrary(String cid, String lid, String title, String content) {
        Query query = new Query(Criteria.where("_id").is(cid).and("voiceLibrarys.lid").is(lid));
        Update update = Update.update("voiceLibrarys.$.title", title).set("voiceLibrarys.$.content", content);
        return mongoTemplate.updateFirst(query, update, MgVoice.class);
    }

    @ApiOperation("语音新增嵌套文档内容")
    @RequestMapping(value = "/updateVoiceInsertLibrary", method = RequestMethod.POST)
    @ResponseBody
    @LogAnno(operateType = "语音新增嵌套文档内容")
    public Object updateVoiceInsertLibrary(String cid, Integer type, String title, String content) {
        String lid = IdUtil.fastUUID();
        MgVoiceLibrary voice = new MgVoiceLibrary();
        voice.setLid(lid);
        voice.setType(type);
        voice.setTitle(title);
        voice.setContent(content);
        voice.setIsTemp(0);
        voice.setUpdateDate(new Date());

        Query query = Query.query(Criteria.where("_id").is(cid));
        Update update = new Update();
        update.addToSet("voiceLibrarys", voice);
        return mongoTemplate.upsert(query, update, MgVoice.class);
    }

    @ApiOperation("删除语音嵌套文档内容对象")
    @RequestMapping(value = "/deleteVoiceLibrary", method = RequestMethod.POST)
    @ResponseBody
    @LogAnno(operateType = "删除语音嵌套文档内容对象")
    public Object deleteVoiceLibrary(String cid, String plid) {
        Query query = Query.query(Criteria.where("_id").is(cid));
        BasicDBObject s = new BasicDBObject();
        s.put("lid", plid);
        Update update = new Update();
        update.pull("voiceLibrarys", s);
        return mongoTemplate.updateFirst(query, update, MgVoice.class);
    }
}
