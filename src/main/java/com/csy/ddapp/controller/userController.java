package com.csy.ddapp.controller;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiCallBackGetCallBackRequest;
import com.dingtalk.api.request.OapiCallBackRegisterCallBackRequest;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiMessageCorpconversationAsyncsendV2Request;
import com.dingtalk.api.request.OapiMessageSendToConversationRequest;
import com.dingtalk.api.request.OapiProcessCopyRequest;
import com.dingtalk.api.request.OapiProcessSyncRequest;
import com.dingtalk.api.request.OapiProcessinstanceCreateRequest;
import com.dingtalk.api.request.OapiUserGetDeptMemberRequest;
import com.dingtalk.api.request.OapiUserGetRequest;
import com.dingtalk.api.request.OapiUserGetuserinfoRequest;
import com.dingtalk.api.request.OapiUserSimplelistRequest;
import com.dingtalk.api.response.OapiCallBackGetCallBackResponse;
import com.dingtalk.api.response.OapiCallBackRegisterCallBackResponse;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.dingtalk.api.response.OapiMessageSendToConversationResponse;
import com.dingtalk.api.response.OapiProcessCopyResponse;
import com.dingtalk.api.response.OapiProcessSyncResponse;
import com.dingtalk.api.response.OapiProcessinstanceCreateResponse;
import com.dingtalk.api.response.OapiUserGetDeptMemberResponse;
import com.dingtalk.api.response.OapiUserGetResponse;
import com.dingtalk.api.response.OapiUserGetuserinfoResponse;
import com.dingtalk.api.response.OapiUserSimplelistResponse;
import com.taobao.api.ApiException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("users")
public class userController {

    /**
     *
     * @param requestAuthCode 免登授权码
     * @return
     * @throws ApiException
     */
    @GetMapping
    public Map getUser(String requestAuthCode) throws ApiException {
        Map map = new HashMap();
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/gettoken");
        OapiGettokenRequest request = new OapiGettokenRequest();
        request.setAppkey("dingqgtq2bnkbco0w9x6");
        request.setAppsecret("y4vrDKrQBQUuRH_OGuqql76TV7hQLm9-ZZWYfBPVXvU8CRolJKsBFnNDja1d7_0j");
        request.setHttpMethod("GET");
        OapiGettokenResponse response = client.execute(request);
        map.put("response",response);
        getUserId(response.getAccessToken(),requestAuthCode);
        return map;
    }

    @GetMapping(value = "getUserId")
    public String getUserId(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getuserinfo");
        OapiUserGetuserinfoRequest request = new OapiUserGetuserinfoRequest();
        request.setCode(requestAuthCode);
        request.setHttpMethod("GET");
        OapiUserGetuserinfoResponse response = client.execute(request, accessToken);
        String userId = response.getUserid();
        return userId;
    }
    @GetMapping(value = "getUserPhone")
    public String getUserPhone(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/get");
        OapiUserGetRequest request = new OapiUserGetRequest();
        request.setUserid("zhangsan");
        request.setHttpMethod("GET");
        OapiUserGetResponse response = client.execute(request, accessToken);
        return response.getUnionid();
    }
    @GetMapping(value = "getUserIds")
    public String getUserIds(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/getDeptMember");
        OapiUserGetDeptMemberRequest req = new OapiUserGetDeptMemberRequest();
        req.setHttpMethod("GET");
        req.setDeptId("a");
        OapiUserGetDeptMemberResponse rsp = client.execute(req, accessToken);
        return null;
    }
    @GetMapping(value = "getUserIds")
    public String getDeptUsers(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/user/simplelist");
        OapiUserSimplelistRequest request = new OapiUserSimplelistRequest();
        request.setDepartmentId(100L);
        request.setOffset(0L);
        request.setSize(10L);
        request.setHttpMethod("GET");
        OapiUserSimplelistResponse response = client.execute(request, accessToken);

        return null;
    }

    @GetMapping(value = "getUserIds")
    public String sendMessage(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/message/corpconversation/asyncsend_v2");
        OapiMessageCorpconversationAsyncsendV2Request request = new OapiMessageCorpconversationAsyncsendV2Request();
        request.setUseridList("01376814877479");
        request.setAgentId(153858650L);
        request.setToAllUser(false);
        OapiMessageCorpconversationAsyncsendV2Request.Msg msg = new OapiMessageCorpconversationAsyncsendV2Request.Msg();
        msg.setMsgtype("text");
        msg.setText(new OapiMessageCorpconversationAsyncsendV2Request.Text());
        msg.getText().setContent("test123");
        request.setMsg(msg);
        msg.setMsgtype("image");
        msg.setImage(new OapiMessageCorpconversationAsyncsendV2Request.Image());
        msg.getImage().setMediaId("@lADOdvRYes0CbM0CbA");
        request.setMsg(msg);

        msg.setMsgtype("link");
        msg.setLink(new OapiMessageCorpconversationAsyncsendV2Request.Link());
        msg.getLink().setTitle("test");
        msg.getLink().setText("test");
        msg.getLink().setMessageUrl("test");
        msg.getLink().setPicUrl("test");
        request.setMsg(msg);

        msg.setMsgtype("markdown");
        msg.setMarkdown(new OapiMessageCorpconversationAsyncsendV2Request.Markdown());
        msg.getMarkdown().setText("##### text");
        msg.getMarkdown().setTitle("### Title");
        request.setMsg(msg);

        msg.setOa(new OapiMessageCorpconversationAsyncsendV2Request.OA());
        msg.getOa().setHead(new OapiMessageCorpconversationAsyncsendV2Request.Head());
        msg.getOa().getHead().setText("head");
        msg.getOa().setBody(new OapiMessageCorpconversationAsyncsendV2Request.Body());
        msg.getOa().getBody().setContent("xxx");
        msg.setMsgtype("oa");
        request.setMsg(msg);

        msg.setActionCard(new OapiMessageCorpconversationAsyncsendV2Request.ActionCard());
        msg.getActionCard().setTitle("xxx123411111");
        msg.getActionCard().setMarkdown("### 测试123111");
        msg.getActionCard().setSingleTitle("测试测试");
        msg.getActionCard().setSingleUrl("https://www.baidu.com");
        msg.setMsgtype("action_card");
        request.setMsg(msg);

        OapiMessageCorpconversationAsyncsendV2Response response = client.execute(request,accessToken);


        return null;
    }

    @GetMapping(value = "getUserIds")
    public String chat(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/message/send_to_conversation");
        OapiMessageSendToConversationRequest req = new OapiMessageSendToConversationRequest();
        req.setSender("01376814877479");
        req.setCid("14ac70d94e79377b88aa5fc75759fe84");
        OapiMessageSendToConversationRequest.Msg msg = new OapiMessageSendToConversationRequest.Msg();
        // 文本消息
        OapiMessageSendToConversationRequest.Text text = new OapiMessageSendToConversationRequest.Text();
        text.setContent("测试测试");
        msg.setText(text);
        msg.setMsgtype("text");
        req.setMsg(msg);
        OapiMessageSendToConversationRequest.Image image = new OapiMessageSendToConversationRequest.Image();
        image.setMediaId("@lADOdvRYes0CbM0CbA");
        msg.setImage(image);
        msg.setMsgtype("image");
        req.setMsg(msg);

        //文件
        OapiMessageSendToConversationRequest.File file = new OapiMessageSendToConversationRequest.File();
        file.setMediaId("@lADOdvRYes0CbM0CbA");
        msg.setFile(file);
        msg.setMsgtype("file");
        req.setMsg(msg);
        OapiMessageSendToConversationRequest.Markdown markdown = new OapiMessageSendToConversationRequest.Markdown();
        markdown.setText("# 这是支持markdown的文本 \\n## 标题2  \\n* 列表1 \\n![alt 啊](https://gw.alipayobjects.com/zos/skylark-tools/public/files/b424a1af2f0766f39d4a7df52ebe0083.png)");
        markdown.setTitle("首屏会话透出的展示内容");
        msg.setMarkdown(markdown);
        msg.setMsgtype("markdown");
        req.setMsg(msg);


        OapiMessageSendToConversationRequest.ActionCard actionCard = new OapiMessageSendToConversationRequest.ActionCard();
        actionCard.setTitle("是透出到会话列表和通知的文案");
        actionCard.setMarkdown("持markdown格式的正文内");
        actionCard.setSingleTitle("查看详情");
        actionCard.setSingleUrl("https://open.dingtalk.com");
        msg.setActionCard(actionCard);
        msg.setMsgtype("action_card");
        req.setMsg(msg);

        // link消息
        OapiMessageSendToConversationRequest.Link link = new OapiMessageSendToConversationRequest.Link();
        link.setMessageUrl("https://www.baidu.com/");
        link.setPicUrl("@lADOdvRYes0CbM0CbA");
        link.setText("步扬测试");
        link.setTitle("oapi");
        msg.setLink(link);
        msg.setMsgtype("link");
        req.setMsg(msg);
        OapiMessageSendToConversationResponse response = client.execute(req, accessToken);
        return null;
    }
    @GetMapping(value = "process")
    public String process(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/copy");
        OapiProcessCopyRequest request = new OapiProcessCopyRequest();
        request.setAgentId(5493L);
        request.setProcessCode("PROC-AP3L3TFV-A3WU5CVUQQ3TQMP3NU3P1-GCO4WRGJ-1");
        request.setProcessName("审批流复制请假");
        request.setBizCategoryId("fab.purchase");
        request.setDescription("适用于企业员工请假");
        OapiProcessCopyResponse response = client.execute(request,accessToken);
        return null;
    }
    @GetMapping(value = "updateProcess")
    public String updateProcess(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/process/sync");
        OapiProcessSyncRequest request = new OapiProcessSyncRequest();
        request.setAgentId(5493L);
        request.setSrcProcessCode("PROC-BY6LI83V-4R8T1CHNUX58O0Z6C55M3-DTQJJGEJ-1");
        request.setTargetProcessCode("PROC-BY6LI83V-4R8T1CHNUX58O0Z6C55M3-DTQJJGEJ-2");
        request.setProcessName("审批流复制");
        request.setBizCategoryId("fab.purchase");
        OapiProcessSyncResponse response = client.execute(request,accessToken);
        return null;
    }
    @GetMapping(value = "processinstance")
    public String processinstance(String accessToken,String requestAuthCode) throws ApiException {
        DefaultDingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/processinstance/create");
        OapiProcessinstanceCreateRequest request = new OapiProcessinstanceCreateRequest();
        request.setAgentId(41605932L);
        request.setProcessCode("PROC-BY6LI83V-4R8T1CHNUX58O0Z6C55M3-DTQJJGEJ-1");
        List<OapiProcessinstanceCreateRequest.FormComponentValueVo> formComponentValues = new ArrayList<OapiProcessinstanceCreateRequest.FormComponentValueVo>();
        OapiProcessinstanceCreateRequest.FormComponentValueVo vo = new OapiProcessinstanceCreateRequest.FormComponentValueVo();
        vo.setName("企业名称");
        vo.setValue("test");
        formComponentValues.add(vo);
        request.setFormComponentValues(formComponentValues);
        request.setApprovers("userid1,userid2");
        request.setOriginatorUserId("userid1");
        request.setDeptId(-1L);
        request.setCcList("userid1,userid2");
        request.setCcPosition("START_FINISH");
        OapiProcessinstanceCreateResponse response = client.execute(request,accessToken);
        return null;
    }

    @GetMapping(value = "register_call_back")
    public String register_call_back(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/register_call_back");
        OapiCallBackRegisterCallBackRequest request = new OapiCallBackRegisterCallBackRequest();
        request.setUrl("http://test001.vaiwan.com/eventreceive");
        request.setAesKey("1234567890123456789012345678901234567890123");
        request.setToken("123456");
        request.setCallBackTag(Arrays.asList("user_add_org", "user_modify_org", "user_leave_org"));
        OapiCallBackRegisterCallBackResponse response = client.execute(request,accessToken);

        return null;
    }


    @GetMapping(value = "get_call_back")
    public String get_call_back(String accessToken,String requestAuthCode) throws ApiException {
        DingTalkClient  client = new DefaultDingTalkClient("https://oapi.dingtalk.com/call_back/get_call_back");
        OapiCallBackGetCallBackRequest request = new OapiCallBackGetCallBackRequest();
        request.setHttpMethod("GET");
        OapiCallBackGetCallBackResponse response = client.execute(request,accessToken);
        return null;
    }


}
