package net.muratec.mcs.service.forupgrade;

import net.muratec.mcs.entity.info.ReqGetCarrierJobAddTransferEntity;


public class TransportAction
{
    public TransportAction(int i) {
        this.guiId = i;
        this.ans = "INFI.Gui."+String.valueOf(guiId)+".HostTransferCmdAns.ON";
    }
    
    private int guiId;
    private CommunicationMessage rtnCommMsg;
    private String ans;

    /**
     * 入力条件に従って搬送指示を作成・送信します。
     */
    public String sendTransferCmdReq(ReqGetCarrierJobAddTransferEntity reqEntity, String strUserName, Integer execSid, String hostName)
    {   
        Communication comm = new Communication(guiId);      
        // 通信メッセージの取得
        CommunicationMessage commMessage =  new CommunicationMessage("HostTransferCmdReq",guiId);     // 搬送指示要求
        // 通信メッセージの受信リスナーの取得
        CommunicationListener commListener = new ReplyListener( "HostTransferCmdAns" );               // 搬送指示応答

        // サブジェクト
        commMessage.setSubject(createSubject(String.valueOf(execSid),commMessage));        
        
        //==================================================
        // パラメータの設定
        //==================================================
        // Total Count
        commMessage.set(Keyword.TOTALCOUNT, new Integer(1));
        // CarrierID
        commMessage.set(Keyword.K_CARRIERID, reqEntity.getCarrierId());
        // Carrier Type ID
        commMessage.set(Keyword.K_CARRIERTYPEID, "");
        // Priority
        commMessage.set(Keyword.K_PRIORITY, new Integer( reqEntity.getJobPriority()));
        // MsgID
        commMessage.set(Keyword.K_MSGID, new Integer(0));
        
        commMessage.set(Keyword.K_RCVSRCEQP,     reqEntity.getFromCzone() );
        commMessage.set(Keyword.K_RCVSRCLOC,     reqEntity.getFromPort() );
        commMessage.set(Keyword.K_RCVDSTEQP1,    reqEntity.getController() );
        commMessage.set(Keyword.K_RCVDSTLOC1,    reqEntity.getPort() );
        
        //==================================================
        // メッセージ送信
        //==================================================
        try
        {
            comm.connect(hostName, new String[] { "INFI.*.*.*.*",                                       // For Alarm
                                                  "INFI.Gui."+String.valueOf(guiId)+".*.ON",            // For Send Message
                                                  "INFI.Gui."+String.valueOf(guiId)+".*.ON",            // For Send Message
                                                  "INFI.Gui."+String.valueOf(guiId)+".*.ON" });         // For Send Message
System.out.println(String.valueOf(guiId) + " Listener Start.");
        } catch (Exception e) {
            return "-231";
        }
//Thread current = Thread.currentThread();
//System.out.println(current.toString() + " Ta start: "+commMessage.getSubject()+" " + String.valueOf(guiId) +" " +this.toString()); 
        boolean rtnSend = comm.send( commMessage, commListener, strUserName);
//System.out.println(current.toString() + " Ta stop: "+commMessage.getSubject()+" " + String.valueOf(guiId));
        try {
            if (rtnSend) {
                //发送成功   每0.1秒获取回调(执行140次：0.1x150=14秒  循环次数与阻塞时间的乘积小于页面ajax请求超时15秒设定) 返回code
                for (int i = 0; i < 140; i++) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//System.out.print(current.toString() + " Recive Message: ");
                    if (rtnCommMsg != null) {
//System.out.println(rtnCommMsg.getSubject());
                        //接收成功
                        return rtnCommMsg.get(Keyword.K_CMDACK).toString();
                    }
                }
                //接收失败
                return "-298";            
            }
            //发送失败
            return "-299";
        } finally {
            comm.disconnect();
        }
    } 
    
    private String createSubject( String execSid, CommunicationMessage commMsg )
    {
        int target;
        target = Integer.parseInt( execSid );
        if( target < 1000 ) target += 1000;
        return "INFI.JobDispatcher." + target + "."+commMsg.getEventName()+".ON";
    }
    
  //********************************************************************************
 // Inner Classes
 //********************************************************************************

     /**
      * 通信メッセージ受信リスナクラス
      */
     private class ReplyListener extends CommunicationListener
     {
         /**
          * 
          */
         private static final long serialVersionUID = 1L;

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
             if (ans.equals(commMsg.getSubject())) {
 //System.out.println(current.toString() + " rtnCommMsg2: " + commMsg.getSubject()+" " +this.toString());                
                 rtnCommMsg = commMsg;
             }
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
     }

 }
