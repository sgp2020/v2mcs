package net.muratec.mcs.service.forupgrade;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.tibco.tibrv.TibrvMsg;



/**
 * メッセージの受信イベントを捕捉するリスナークラスです。
 *
 * @version $Revision: 1.2 $
 * @author M.Takashima(NISP)
 */
public class CommunicationListener extends javax.swing.Timer implements ActionListener
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /** タイムアウトか否かの状態を保持します。 */
    private boolean isTimeout;

    /** 
     * 受信メッセージを選別するためのフィルタを保持します。<br> 
     * 具体的には、サブジェクトの4つ目の部分(メッセージの種類)を表します。
     */
    private String strFilter;

    private int intMaxSendCount;
    private int intRcvCount;

//********************************************************************************
// Constructors
//********************************************************************************

    /**
     * 初期化を行います。
     */
    public CommunicationListener()
    {
        super(Keyword.K_DELAY, null );
        addActionListener( this );
        setInitialDelay( getDelay() );
        setRepeats( false );
        strFilter = "*";
    }


//********************************************************************************
// Public Methods
//********************************************************************************

    /**
     * 受信すべきメッセージを選別するフィルタを設定します。
     *
     * @param strFilter フィルタ
     */
    public void setFilter( String strFilter )
    {
        this.strFilter = strFilter;
        intMaxSendCount = 0;
    }

    /**
     * 連続送信する際の初期化を行います。
     *
     * @param intMaxSendCount 最大送信数
     */
    public void initialize( int intMaxSendCount )
    {
        this.intRcvCount = 0;
        this.intMaxSendCount = intMaxSendCount;
    }

    /**
     * タイマーを開始します。
     * 主にメッセージ送信時にommunicationクラスから呼び出されます。
     */
    public void start()
    {
        isTimeout = false;
        super.start();
    }

    /**
     * メッセージ受信時のイベント処理を行います。
     * メッセージ受信時にCommunicationクラスから呼び出されます。
     *
     * @param commMsg 通信メッセージ
     */
    public synchronized final void onRecive( TibrvMsg tibmsg )
    {
        if( isTimeout )
        {
            // 既にタイムアウトならリターンする
            return;
        }

        try
        {
            //==================================================
            // サブジェクトを分解して、受信したメッセージ
            // タイプを取得
            // ('.'区切りのトークンの四つ目にあたる)
            //==================================================
            String subject = tibmsg.getSendSubject();
/*
            StringTokenizer token = new StringTokenizer( subject, "." );
            token.nextToken();
            token.nextToken();
            token.nextToken();
            String msgType = token.nextToken();
*/
            String[] strArray = GuiUtilities.splitByDelim( subject, "." );
            String msgType = strArray[3];

            //==================================================
            // サブジェクトを解析し、受信すべきメッセージ
            // であればタイマーを止め、メッセージ受信時の
            // 処理を行う。
            //==================================================
//          if( strFilter.equals( msgType ) )
//          {
//              stop();
//
//System.out.println( "Recive Subject = " + subject );
//
//              // バイトデータを取得
//              byte[] data = (byte[])tibmsg.getFieldByIndex(0).data;
//              onReciveMsg( new CommunicationMessage( subject, msgType, data ) );
//          }
            if( strFilter.equals( msgType ) )
            {
//System.out.println( "Recive Subject = " + subject );
                if( intMaxSendCount == 0 )
                {
                    stop();
                }
                else
                {
                    intRcvCount++;
                    if( intRcvCount >= intMaxSendCount )
                    {
                        stop();
                    }
                }
                
                byte[] data = (byte[])tibmsg.getFieldByIndex(0).data;
                onReciveMsg( new CommunicationMessage( subject, msgType, data ) );
                
                if( intMaxSendCount != 0 && intRcvCount >= intMaxSendCount )
                {
                    onReciveMsgLast();
                }
            }
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * メッセージ受信時に行う処理を記述します。
     * <p>
     * <strong>注:</strong>
     * このメソッドの実装は空です。<br>
     * 実際の使用ではサブクラスで処理を実装します。
     *
     * @param commMsg 通信メッセージ
     */
    public void onReciveMsg( CommunicationMessage commMsg )
    {
    }

    /**
     * 連続送信時の最後のメッセージを受信したことを知らせます。
     * <p>
     * <strong>注:</strong>
     * このメソッドの実装は空です。<br>
     * 実際の使用ではサブクラスで処理を実装します。
     */
    public void onReciveMsgLast()
    {
    }

    /**
     * タイムアウト時に行う処理を記述します。
     * <p>
     * <strong>注:</strong>
     * このメソッドの実装は空です。<br>
     * 実際の使用ではサブクラスで処理を実装します。
     */
    public void onTimeout()
    {
    }

    /**
     * タイムアウト発生時のイベント処理を行います。
     */
    public void actionPerformed( ActionEvent evt )
    {
        synchronized( this )
        {
            // タイマーを止める
            stop();

            // タイムアウト発生時の処理を行う
            onTimeout();

            // 状態をタイムアウトに設定する
            isTimeout = true;
        }
    }

    /**
     * タイムアウトか否かの状態を取得します。
     *
     * @retrun true:タイムアウト時<br>
     *         false:未タイムアウト時
     */
    public boolean isTimeout()
    {
        return isTimeout;
    }

    /**
     * フィルタを取得します。
     * 文字列として保持しているフィルタを数値に変換します。
     *
     * @retrun フィルタを表すint値。
     */
    public String getFilter()
    {
        return strFilter;
    }

    /**
     * リスナーをストップします。
     */
    public void stopListener()
    {
        stop();
    }
}
