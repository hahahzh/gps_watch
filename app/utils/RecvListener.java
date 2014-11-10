package utils;

import java.math.BigInteger;

import com.wondertek.esmp.esms.empp.EMPPAnswer;
import com.wondertek.esmp.esms.empp.EMPPChangePassResp;
import com.wondertek.esmp.esms.empp.EMPPDeliver;
import com.wondertek.esmp.esms.empp.EMPPDeliverReport;
import com.wondertek.esmp.esms.empp.EMPPObject;
import com.wondertek.esmp.esms.empp.EMPPRecvListener;
import com.wondertek.esmp.esms.empp.EMPPReqNoticeResp;
import com.wondertek.esmp.esms.empp.EMPPSubmitSM;
import com.wondertek.esmp.esms.empp.EMPPSubmitSMResp;
import com.wondertek.esmp.esms.empp.EMPPSyncAddrBookResp;
import com.wondertek.esmp.esms.empp.EMPPTerminate;
import com.wondertek.esmp.esms.empp.EMPPUnAuthorization;
import com.wondertek.esmp.esms.empp.EmppApi;

public class RecvListener implements EMPPRecvListener {

	private static final long RECONNECT_TIME = 10 * 1000;
    
    private EmppApi emppApi = null;
    
    private int closedCount = 0;
    
    private static RecvListener recvListener = null;
    
    private RecvListener(){
        
    }
    
    public static RecvListener getInstance(EmppApi emppApi) {
    	if (recvListener == null) {
        	recvListener = new RecvListener(emppApi);
        }
        return recvListener;
    }
    
    private RecvListener(EmppApi emppApi){
        this.emppApi = emppApi;
    }
  
    public void onMessage(EMPPObject message) {
        if(message instanceof EMPPUnAuthorization){
            EMPPUnAuthorization unAuth=(EMPPUnAuthorization)message;
            play.Logger.info("客户端无权执行此操作 commandId="+unAuth.getUnAuthCommandId());
            return;
         }
        if(message instanceof EMPPSubmitSMResp){
        	EMPPSubmitSMResp resp=(EMPPSubmitSMResp)message;
        	play.Logger.info("收到sumbitResp:");
        	byte[] msgId=fiterBinaryZero(resp.getMsgId());
        	
        	play.Logger.info("msgId="+new BigInteger(msgId));
        	play.Logger.info("result="+resp.getResult());
        	return;
        }
  		if(message instanceof EMPPDeliver){
  			EMPPDeliver deliver = (EMPPDeliver)message;
  			if(deliver.getRegister()==EMPPSubmitSM.EMPP_STATUSREPORT_TRUE){
  				EMPPDeliverReport report=deliver.getDeliverReport();
  				play.Logger.info("收到状态报告:");
  				byte[] msgId=fiterBinaryZero(report.getMsgId());
  			    
  				play.Logger.info("msgId="+new BigInteger(msgId));
  				play.Logger.info("status="+report.getStat());
  			    
  			}else{
  				play.Logger.info("收到"+deliver.getSrcTermId()+"发送的短信");
  				play.Logger.info("短信内容为："+deliver.getMsgContent());
  			}
  		    return;
  		}
  		if(message instanceof EMPPSyncAddrBookResp){
		      EMPPSyncAddrBookResp resp=(EMPPSyncAddrBookResp)message;
		      if(resp.getResult()!=EMPPSyncAddrBookResp.RESULT_OK)
		          play.Logger.info("同步通讯录失败");
		      else{
		          play.Logger.info("收到服务器发送的通讯录信息");
		          play.Logger.info("通讯录类型为："+resp.getAddrBookType());
		          play.Logger.info(resp.getAddrBook());
		      }
        }
		 if(message instanceof EMPPChangePassResp){
              EMPPChangePassResp resp=(EMPPChangePassResp)message;
              if(resp.getResult()==EMPPChangePassResp.RESULT_VALIDATE_ERROR)
                  play.Logger.info("更改密码：验证失败");
              if(resp.getResult()==EMPPChangePassResp.RESULT_OK)
              {
                  play.Logger.info("更改密码成功,新密码为："+resp.getPassword());
                  emppApi.setPassword(resp.getPassword());
              }
              return;
              
          } 
		 if(message instanceof EMPPReqNoticeResp){
              EMPPReqNoticeResp response=(EMPPReqNoticeResp)message;
              if(response.getResult()!=EMPPReqNoticeResp.RESULT_OK)
                 play.Logger.info("查询运营商发布信息失败");
              else{
                 play.Logger.info("收到运营商发布的信息");
                 play.Logger.info(response.getNotice());
              }
              return;
         }
		if(message instanceof EMPPAnswer){
		    play.Logger.info("收到企业疑问解答");
           EMPPAnswer  answer=(EMPPAnswer)message;
           play.Logger.info(answer.getAnswer());
           
       }
	    
     }
	 //处理连接断掉事件
   public void OnClosed(Object object) {
      // 该连接是被服务器主动断掉，不需要重连
      if(object instanceof EMPPTerminate){
          play.Logger.info("收到服务器发送的Terminate消息，连接终止");
          return;
      }
      //这里注意要将emppApi做为参数传入构造函数
      RecvListener listener = new RecvListener(emppApi)
		;
      play.Logger.info("连接断掉次数："+(++closedCount));
      for(int i = 1;!emppApi.isConnected();i++){
          try {
              play.Logger.info("重连次数:"+i);
              Thread.sleep(RECONNECT_TIME);
              emppApi.reConnect(listener);
          }catch (Exception e) {
              e.printStackTrace();
          }
      }
      play.Logger.info("重连成功");
  }

	//处理错误事件
  public void OnError(Exception e) {
      e.printStackTrace();
  }
  
  private static byte[] fiterBinaryZero(byte[] bytes) {
      byte[] returnBytes = new byte[8];
      for (int i = 0; i < 8; i++) {
          returnBytes[i] = bytes[i];
      }
      return returnBytes;
  }
}
