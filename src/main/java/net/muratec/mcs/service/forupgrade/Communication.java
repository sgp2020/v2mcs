package net.muratec.mcs.service.forupgrade;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.tibco.tibrv.Tibrv;
import com.tibco.tibrv.TibrvListener;
import com.tibco.tibrv.TibrvMsg;
import com.tibco.tibrv.TibrvMsgCallback;
import com.tibco.tibrv.TibrvQueue;
import com.tibco.tibrv.TibrvQueueGroup;
import com.tibco.tibrv.TibrvRvaTransport;

/**
 * GUIと制御との通信を司るクラスです。
 *
 * @version $Revision: 1.1.1.1 $
 * @author M.Takashima(NISP)
 */
public class Communication
{
    private int guiId;
    public Communication(int i) {
        this.guiId = i;
    }
    /** ディスパッチ用スレッド。 */
    private Dispatch dispatch;

    /** 受信リスナー */
    private ReciveListener[] reviveListeners;

    /** メッセージキュー。 */
    private TibrvQueue[] queues;

    /** キューグループ。 */
    private TibrvQueueGroup queueGroup;

    /** RVAと通信を行うオブジェクト。 */
    private TibrvRvaTransport transport;

    public void connect( String strHostname, String[] astrReciveSubject ) throws Exception
    {
        try
        {
            // ディスパッチスレッドの作成
            dispatch  = new Dispatch();

            // 受信リスナーの取得
            reviveListeners = new ReciveListener[] { new ReciveListener(), new ReciveListener(), new ReciveListener(), new ReciveListener() };

            Tibrv.open( Tibrv.IMPL_JAVA );
            // RVAと通信を行うオブジェクトを作成する
            transport = new TibrvRvaTransport( strHostname,TibrvRvaTransport.DEFAULT_RVA_PORT );
            queueGroup = new TibrvQueueGroup();
            queues = new TibrvQueue[reviveListeners.length];
            for( int i=0; i<queues.length; i++ )
            {
                // キューをキューグループに登録
                queues[i] = new TibrvQueue();
                queues[i].setPriority( i );
                queueGroup.add( queues[i] );
                new TibrvListener( queues[i], reviveListeners[i], transport, astrReciveSubject[i], null );
            }

            // ディスパッチスレッドの開始
            dispatch.start();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            throw e;
        }
    }
    
    public boolean disconnect()
    {
        try
        {
            // ディスパッチスレッドを停止する
            dispatch.interrupt();
            dispatch = null;

            // 通信関連オブジェクトを破壊する
            for( int i=0; i<queues.length; i++ )
            {
                queues[i].destroy();
            }
            queueGroup.destroy();
            transport.destroy();

            // TIBのコネクションを閉じる
            Tibrv.close();
        }
        catch( Exception e )
        {
            e.printStackTrace();
            // クローズ失敗
            return false;
        }

        return true;
    }

    /**
     * 指定された通信メッセージを送ります。
     *
     * @param commMsg 通信メッセージ
     * @param commListener 応答リスナー
     * @return 送信成功時はtrue、失敗時はfalse
     * @exception ProcessDownException MACSのステータスが'Shutdown'または'Down'時
     */
    public boolean send( CommunicationMessage commMsg, CommunicationListener commListener, String strUserName )
    {
        if( commListener != null )
        {
            // 応答フラグをセットする
            commMsg.set( Keyword.K_REPLYFLG, new Integer( 1 ) );

            // 応答リスナーをセットする
            reviveListeners[1].setCommunicationListener( commListener );

            // 受信タイムアウトのタイマーをスタートする
            commListener.start();
   
        }

        // メッセージ送信
        return send( commMsg, commMsg.getSubject(), strUserName);
    }


    /**
     * 指定された通信メッセージを送ります。
     * 受信メッセージはありません。
     *
     * @param commMsg 通信メッセージ
     * @param subject サブジェクト
     * @return 送信成功時はtrue、失敗時はfalse
     * @exception ProcessDownException MACSのステータスが'Shutdown'または'Down'時
     */
    public boolean send( CommunicationMessage commMsg, String subject, String strUserName )
    {
        try
        {
            // CommunicationMessage -> TibrvMsgに変換
            TibrvMsg tibmsg = new TibrvMsg();
            tibmsg.setSendSubject( subject );

            // 送信時間をセット
            String currentTime = timeConvMillsToStrDate( System.currentTimeMillis() );
            commMsg.set( Keyword.K_TIME, currentTime );

            // オリジネータをセット
            commMsg.set( Keyword.K_ORIGINATOR, strUserName );

            // 送信用フォーマット(バイト配列)に変換して送信
            tibmsg.add( "EVENT", commMsg.getByteArray());
//System.out.println("send message : "+commMsg.getSubject());
            // メッセージを送信
            transport.send( tibmsg );
//System.out.println(String.valueOf(guiId) + " Send Message : " +commMsg.getSubject());  
        }
        catch( Exception e )
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String timeConvMillsToStrDate( long millis )
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMddHHmmssSS" );
        String strDate = dateFormat.format( new Date( millis ) );
        return strDate.substring( 0, 16 );
    }
    
    /**
     * 通信リスナーをセットします。
     *
     * @param commListener 通信リスナー
     */
    public void setCommunicationListener( CommunicationListener commListener )
    {
        reviveListeners[0].setCommunicationListener( commListener );
    }

    /**
     * 通信リスナーをセットします。
     *
     * @param commListener 通信リスナー
     */
    public void setCommunicationListener( CommunicationListener[] commListeners )
    {
        for( int i=0; i<commListeners.length; i++ )
        {
            reviveListeners[i+2].setCommunicationListener( commListeners[i] );
        }
    }
    
    /**
     * RVAとの接続状態を判定します。
     *
     * @return 例外発生時はfalse、それ以外はtrueを返します。
     */
    public boolean isConnected()
    {
        try
        {
            // ダミーのメッセージを送信
            TibrvMsg tibmsg = new TibrvMsg();
            tibmsg.setSendSubject( "DUMMY" );
            transport.send( tibmsg );
        }
        catch( Exception e )
        {
            // 通信エラー
            return false;
        }
        return true;
    }
    

//********************************************************************************
// Inner Classes
//********************************************************************************

    /**
     * 送信メッセージに対応した応答を捕捉する受信リスナークラス。
     */
    private class ReciveListener implements TibrvMsgCallback
    {
        private CommunicationListener m_commListener;

        /**
         * 通信リスナーをセットする
         *
         * @param commListener 通信リスナー
         */
        public void setCommunicationListener( CommunicationListener commListener )
        {
//Thread current = Thread.currentThread();   
//System.out.println( current.toString() +" Callback1: "+ commListener.toString());
            m_commListener = commListener;
        }

        /** 
         * 応答メッセージ受信時の処理を行う
         *
         * @param listener リスナー
         * @param tivmsg 受信メッセージ
         */
        public void onMsg( TibrvListener listener, TibrvMsg tivmsg )
        {
//Thread current = Thread.currentThread();   
//System.out.println( current.toString() +" Callback2: "+ this.toString());  
            try
            {
                if( m_commListener != null )
                {
                    // 登録した受信リスナーの受信メソッドをコールする
//System.out.println( current.toString() +" Callback3: "+ tivmsg.getSendSubject() +" " + this.toString());  
                    m_commListener.onRecive( tivmsg );
                }
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

    /**
     * ディスパッチ(送信)クラス
     */
    private class Dispatch extends Thread
    {
        /**
         * キューにあるメッセージを送信する。
         */
        public void run()
        {
            try
            {
                while( true )
                {
                    // キューにあるメッセージを送信する
                    // なければ待機する
                    queueGroup.dispatch();
                }
            }
            catch( InterruptedException e ) {
 //               e.printStackTrace();
System.out.println(String.valueOf(guiId) + " Listener stop.");
            }
            catch( com.tibco.tibrv.TibrvException e ) {
                e.printStackTrace();
            }
        }
    }
}
