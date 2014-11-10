package utils;

import java.util.ArrayList;
import java.util.List;

import play.Play;

import com.wondertek.esmp.esms.empp.EMPPConnectResp;
import com.wondertek.esmp.esms.empp.EMPPData;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPShortMsg;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EmppApi;

public class SendSMS {

	static String host = Play.configuration.getProperty("sms.host");
	static String accountId = Play.configuration.getProperty("sms.accountId");
	static String password = Play.configuration.getProperty("sms.password");
	static String serviceId = Play.configuration.getProperty("sms.serviceId");

	public static boolean sendMsg(String[] phoneNos, String content) throws Exception {

		EmppApi emppApi = new EmppApi();
		RecvListener listener = RecvListener.getInstance(emppApi);

		EMPPConnectResp response = emppApi.connect(host, 9981, accountId, password, listener);
		if (response == null || !emppApi.isConnected() || !emppApi.isSubmitable()) {
			return false;
		}
		
        EMPPSubmitSM msg = (EMPPSubmitSM)EMPPObject.createEMPP(EMPPData.EMPP_SUBMIT);
        List dstId = new ArrayList();
        for(String m : phoneNos){
        	dstId.add(m.trim()); 
        }

        msg.setDstTermId(dstId);
        msg.setSrcTermId(accountId);
        msg.setServiceId(serviceId);

        EMPPShortMsg msgContent = new EMPPShortMsg(EMPPShortMsg.EMPP_MSG_CONTENT_MAXLEN);

         try {
                 msgContent.setMessage(content.getBytes("GBK"));
                 msg.setShortMessage(msgContent);
                 msg.assignSequenceNumber();
                 emppApi.submitMsgAsync(msg);
         } catch (Exception e) {
                 e.printStackTrace();
                 play.Logger.error("Send Message error. msg:"+msg);
                 return false;
         }
         return true;
	}
	
	public static void main(String[] args) {
		try {
			// 结论：字数多的情况下可以正常显示，字数少的情况下出现乱码
			String str = "否功看是否能否功看是否能否功看是否能否功看是否能否功看是否能否功看是否能否功看是否功看是否能否功看是否能否功看是否能能是否能否功看是否能能";

			String s0 = UTF2GBK.GBK2Unicode("测试");
            String s1 = UTF2GBK.GBK2Unicode(str);
            String s2 = UTF2GBK.unicodeToUtf8(str);

			SendSMS.sendMsg(new String[]{"15000993473"}, str);
			
//			String tt = IOUtils.toString(IOUtils.toInputStream(utf8, "GBK"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    
	}
}