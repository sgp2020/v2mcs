package net.muratec.mcs.service.forupgrade;

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.model.DeleteCarrier;


public class DeleteCarrierAction
{
    public DeleteCarrierAction(int i) {
        this.guiId = i;
    }
    
    private boolean rtnflg = false;
    private int guiId;
    private List<CommunicationMessage> rtnCommMsgs = new ArrayList<CommunicationMessage>();
    private String ans;

    /**
     * 入力条件に従って搬送指示を作成・送信します。
     */
    public List<DeleteCarrier> sendRemoveCmdReq(List<DeleteCarrier> sendRows, String strUserName, String hostName, int eventType)
    {   
        List<DeleteCarrier> rcvRows = new ArrayList<DeleteCarrier>();
        
        if (eventType==1) { 
            this.ans = "INFI.Gui."+String.valueOf(guiId)+".RemoveCommandAns.ON";
        } else {
            this.ans = "INFI.Gui."+String.valueOf(guiId)+".JobDeleteAns.ON";
        }
        
        Communication comm = new Communication(guiId);      
        // 通信メッセージの取得
        CommunicationMessage[] commMessages = new CommunicationMessage[] {
                new CommunicationMessage( "InstallCommandReq",guiId),       // キャリアインストール要求
                new CommunicationMessage( "RemoveCommandReq",guiId),     // キャリア削除要求
                new CommunicationMessage( "JobDeleteReq",guiId),         // Job削除要求
        };
        
        // 通信メッセージの受信リスナーの取得
        CommunicationListener[] commListeners = new CommunicationListener[] { 
                new ReplyListener("InstallCommandAns"),     // キャリアインストール応答
                new ReplyListener("RemoveCommandAns"),       // キャリア削除応答
                new ReplyListener("JobDeleteAns"),           // JobDelete応答
        };        
        
        // 最大送信するをセットする.
        commListeners[eventType].initialize(sendRows.size());
        
        try
        {
            comm.connect(hostName, new String[] { "INFI.*.*.*.*",                                       // For Alarm
                                                  "INFI.Gui."+String.valueOf(guiId)+".*.ON",            // For Send Message
                                                  "INFI.Gui."+String.valueOf(guiId)+".*.ON",            // For Send Message
                                                  "INFI.Gui."+String.valueOf(guiId)+".*.ON" });         // For Send Message
System.out.println(String.valueOf(guiId) + " Listener Start.");
        } catch (Exception e) {
            return rcvRows;   //"-231"
        }
        
        for (DeleteCarrier dc : sendRows) {
            // パラメータ設定
            commMessages[eventType].set( Keyword.K_CARRIERID, dc.getCarrierId());
            
            // サブジェクト
            commMessages[eventType].setSubject( createSubject( String.valueOf(dc.getExecSid()), commMessages[eventType] ) );    
            
          //Thread current = Thread.currentThread();
          //System.out.println(current.toString() + " Ta start: "+commMessage.getSubject()+" " + String.valueOf(guiId) +" " +this.toString()); 
            boolean rtnSend = comm.send( commMessages[eventType], commListeners[eventType], strUserName);
          //System.out.println(current.toString() + " Ta stop: "+commMessage.getSubject()+" " + String.valueOf(guiId));
            
            if (!rtnSend) {
                //发送失败
                //rtn ="-299";
                DeleteCarrier dCtemp = new DeleteCarrier();
                dCtemp.setCarrierId(dc.getCarrierId());
                dCtemp.setAns("-299");
                rcvRows.add(dCtemp);
            }
        }
        
        try {           
 
                //发送成功   每0.1秒获取回调(执行140次：0.1x150=14秒  循环次数与阻塞时间的乘积小于页面ajax请求超时15秒设定) 返回code
                for (int i = 0; i < 140; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//System.out.print(current.toString() + " Recive Message: ");
//System.out.println(rtnCommMsg.getSubject());

                    if (rtnflg) {
                        //接收成功
                        for (CommunicationMessage rtnCommMsg : rtnCommMsgs) {
                            
                            DeleteCarrier dCtemp = new DeleteCarrier();
                            dCtemp.setCarrierId(rtnCommMsg.get(Keyword.K_CARRIERID).toString());
                            dCtemp.setAns(rtnCommMsg.get(Keyword.K_HCACK).toString());
                            rcvRows.add(dCtemp);
                        }
                        return rcvRows;
                    }
                }
                //接收失败
                //rtn ="-298";            

            return rcvRows;
        } finally {
            comm.disconnect();
        }

    } 
    
    private String createSubject( String destExecSid, CommunicationMessage commMsg )
    {
        int target = 0;
        String subject = "";
        if("JobDeleteReq".equals( commMsg.getEventName())) {
            target = 1000 + Integer.parseInt( destExecSid );
            subject = "INFI.JobDispatcher." + target + "." + commMsg.getEventName() + ".ON";
        } else {
            target = 2000 + Integer.parseInt( destExecSid );
            subject = "INFI.CoreControl." + target + "." + commMsg.getEventName() + ".ON";
        }
        
        return subject;
    }
    

    
  //********************************************************************************
 // Inner Classes
 //********************************************************************************

     /**
      * 通信メッセージ受信リスナクラス
      */
     private class ReplyListener extends CommunicationListener
     {

         private static final long serialVersionUID = 1L;
         
         /** 受信したAckデータを保持します */
//         private Vector rcvData;

         /**
          * 受信するメッセージのサブジェクトを登録します。
          */
         public ReplyListener(String nFilter)
         {
             setFilter( nFilter );
             setInitialDelay( Keyword.K_DELAY );
         }

         /**
          * メッセージ受信時の処理を行います。
          */
         @Override
         public void onReciveMsg( CommunicationMessage commMsg )
         {
 //Thread current = Thread.currentThread();
 //System.out.println(current.toString() + " rtnCommMsg1: " + ans+" 比较 "+commMsg.getSubject() +" " +this.toString());
             if (ans != null && ans.equals(commMsg.getSubject())) {
 //System.out.println(current.toString() + " rtnCommMsg2: " + commMsg.getSubject()+" " +this.toString());                
                 rtnCommMsgs.add(commMsg);
             }
         }
         
         @Override
         public void onReciveMsgLast()
         {
             rtnflg = true;
         }

         /**
          * タイムアウト時の処理を行います。
          */
         @Override
         public void onTimeout()
         {
             // メッセージの表示
//             Common.infoMsg( messages[2], "", JOptionPane.WARNING_MESSAGE );
System.out.println("onTimeout");
         }
         
         public void initialize( int intMaxSendCount )
         {
             super.initialize( intMaxSendCount );
//             rcvData = new Vector();
         }
     }

 }
