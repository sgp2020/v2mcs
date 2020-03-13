package net.muratec.mcs.service.forupgrade;

import java.nio.ByteOrder;


public class CommunicationMessage
{
    private int guiId;
    /** イベント名を保持します。 */
    private String eventName;

    /** サブジェクトを保持します｡ */
    private String subject;

    /** メッセージのキーと値のリストである通信データを保持します。 */
    private Object[][] fields;

    /** データ型がIntegerであることを表します｡ */
    private static final String TYPE_INTEGER = "Integer";

    /** データ型がStringであることを表します｡ */
    private static final String TYPE_STRING = "String";

    /** バイト配列の長さ */
    private static final int BYTE_ARRAY_LENGTH = 16384;


//********************************************************************************
// Constructors
//********************************************************************************

    /**
     * 指定されたパラメータで初期化を行います｡<br>
     * このコンストラクタは、送信メッセージ作成時に使用します｡
     * 
     * @param eventKeyName イベントキー
     */
    public CommunicationMessage( String eventName, int i)
    {
        this.guiId = i;
        this.eventName = eventName;
        init();
    }
    
    public CommunicationMessage( String eventName)
    {
        this.eventName = eventName;
        init();
    }

    /**
     * 指定されたパラメータで初期化を行います。<br>
     * このコンストラクタは、受信メッセージ作成時に使用します｡
     *
     * @param subject サブジェクト
     * @param eventKeyName イベントキー
     * @param data バイト配列のデータ
     */
    public CommunicationMessage( String subject, String eventKeyName, byte[] data )
    {

        this( eventKeyName );
        this.subject = subject;

//System.out.println(" subject = " + subject);
//System.out.println(" eventKeyName = " + eventKeyName);
//System.out.println(" data.length = " + data.length);

        convertByteArrayToCommMsg( data );
    }


//********************************************************************************
// Public Methods
//********************************************************************************

    /**
     * サブジェクトを設定します｡
     */
    public final void setSubject( String subject )
    {
        this.subject = subject;
    }

    /**
     * サブジェクトを返します｡<br>
     * このメソッドは、受信メッセージの場合のみ有効な値を返します｡
     */
    public final String getSubject()
    {
        return subject;
    }

    /**
     * 指定されたキーに値をセットします。
     */
    public final void set( String keyName, Object data )
    {
        for( int i=0; i<fields.length; i++ )
        {
            if( keyName.equals( fields[i][0] ) )
            {
                // 指定されたキーと一致するデータを更新する
                fields[i][3] = data;
                break;
            }
        }
    }

    /**
     * 指定されたインデックスにデータをセットします。
     *
     * @param n データを挿入するインデックス
     * @param o 挿入対象データ
     */
    public void set( int n, Object o )
    {
        fields[n][3] = o;
    }

    /**
     * データから指定されたキーに対応した値を取得します。
     *
     * @param strField キー
     * @return 指定のキーに対応した値。<br>
     *         値がない場合はnull。
     */
    public Object get( String strField )
    {
        for( int i=0; i<fields.length; i++ )
        {
            if( strField.equals( fields[i][0] ) )
            {
                Object value = fields[i][3];
                if( value instanceof String )
                    value = ((String)value).trim();
                return value;
            }
        }

        return null;
    }

    /**
     * 指定されたインデックスのデータを取得します。
     *
     * @param index インデックス
     * @return 指定のインデックスに対応した値。<br>
     *         値がない場合はnull。
    */
    public Object get( int index )
    {
        if( index >= 0 && index < fields.length )
        {
            Object value = fields[index][3];
            if( value instanceof String )
                value = ((String)value).trim();
            return value;
        }

        return null;
    }

    /**
     * 指定されたインデックスのフィールド名(キー)を取得します。
     *
     * @param index インデックス
     * @return 指定のインデックスのキー。<br>
     *         キーがない場合はnull。
     */
    public Object getFieldName( int index )
    {
        if( index >= 0 && index < fields.length )
        {
            return fields[index][0];
        }

        return null;
    }

    /**
     * メッセージの全データを取得します。
     *
     * @return データ
     */
    public Object getAll()
    {
        return fields;
    }

    /**
     * 通信メッセージのフィールド数を取得します。<br>
     * ここでのデータとは、キーと値のペアを意味します。
     *
     * @return データ数
     */
    public final int getFieldCount()
    {
        return fields.length;
    }



    public void convertByteArrayToCommMsg( byte[] data )
    {
        try
        {
            int offset = 0;
            for( int i=0; i<fields.length; i++ )
            {
                String type = (String)fields[i][1];
                int length = Integer.parseInt( (String)fields[i][2] );
                byte[] buff = new byte[length];
                
                if( Keyword.K_SPLIT.equals( (String)fields[i][0] ) )
                {
                    // 入れ子の最大バイト数が１バイト(構造体の中身が全てchar)だったら、アライメントは必要なし
                    if( type.equals( TYPE_INTEGER ) )
                    {
                        offset = updateOffset( offset );
                    }
                }
                else if( type.equals( TYPE_INTEGER ) )
                {
                    // 型がintならオフセットを更新する
                    offset = updateOffset( offset );
                    System.arraycopy( data, offset, buff, 0, length );
                    fields[i][3] = new Integer( GuiUtilities.convertByteArrayToInt( buff ) );
//System.out.println("Integer : i["+i+"] key["+fields[i][0]+"] length["+length+"] offset["+offset+"] type["+type+"] data["+fields[i][3]+"]");
                }
                else
                {
                    System.arraycopy( data, offset, buff, 0, length );
                    fields[i][3] = new String( buff );
//System.out.println("String : i["+i+"] key["+fields[i][0]+"] length["+length+"] offset["+offset+"] type["+type+"] data["+fields[i][3]+"]");

                }
                offset += length;
            }
        }
        catch( Exception ex )
        {
            ex.printStackTrace();
//            Common.infoMsg( ((Object)new String("The structure definition of this message is wrong1")), "", JOptionPane.WARNING_MESSAGE );
        }
        finally
        {
        }
    }

    /**
     * イベントキーを返します
     */
    public String getEventName()
    {
        return eventName;
    }

    /**
     * メッセージを表すバイト配列を返します。
     */
    public byte[] getByteArray()
    {

        byte[] bArray = new byte[BYTE_ARRAY_LENGTH];
        int offset = 0;

        try
        {
            for( int i=0; i<fields.length; i++ )
            {
                String type = (String)fields[i][1];
                int length = Integer.parseInt( (String)fields[i][2] );

                if( Keyword.K_SPLIT.equals( (String)fields[i][0] ) )
                {
                    // 入れ子の最大バイト数が１バイト(構造体の中身が全てchar)だったら、アライメントは必要なし
                    if( type.equals( TYPE_INTEGER ) )
                    {
                        offset = updateOffset( offset );
                    }
                }
                else if( type.equals( TYPE_INTEGER ) )
                {
                    int intValue;
                    // 型がintの場合はオフセットを更新する
                    offset = updateOffset( offset );
    
                //  int intValue = ((Integer)fields[i][3]).intValue();
                    if( fields[i][3] instanceof Integer )
                    {
                         intValue = ((Integer)fields[i][3]).intValue();
                    }
                    else
                    { 
                        intValue = (Integer.valueOf((String)fields[i][3])).intValue();
                    }

                    System.arraycopy( convertIntToByteArray( intValue ), 0, bArray, offset, length );
//System.out.println("Integer : i["+i+"] key["+fields[i][0]+"] length["+length+"] offset["+offset+"] type["+type+"] data["+fields[i][3]+"]");

                }
                else if(type.equals( TYPE_STRING ))
                { 
//System.out.println("String  : i["+i+"] key["+fields[i][0]+"] length["+length+"] offset["+offset+"] type["+type+"] data["+fields[i][3]+"]");

                    String strValue = (String)fields[i][3];
                    System.arraycopy( strValue.getBytes(), 0, bArray, offset, strValue.length() );
                }
                offset += length;
            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
//            Common.infoMsg( ((Object)new String("The structure definition of this message is wrong2")), "", JOptionPane.WARNING_MESSAGE );

        }
/*
        finally
        {
            return bArray;
        }
*/
        return bArray;
    }


//********************************************************************************
// Private Methods
//********************************************************************************
    /**
     * 初期化を行う
     */
    private void init()
    {
        //==================================================
        // メッセージフォーマットをリソースから読込んで作成
        //==================================================
        StringBuilder strBuffTemp = new StringBuilder();
        if ("HostTransferCmdReq".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("MSGID,Integer,4;");
            strBuffTemp.append("HOSTCOMMANDID,String,65;");
            strBuffTemp.append("ORIGINATOR,String,65;");
            strBuffTemp.append("BATCHFLG,Integer,4;");
            strBuffTemp.append("TOTALCOUNT,Integer,4;");
            strBuffTemp.append("SPLIT,Integer,0;");
            strBuffTemp.append("COMMANDID,String,65;");
            strBuffTemp.append("CARRIERID,String,65;");
            strBuffTemp.append("CARRIERTYPEID,String,65;");
            strBuffTemp.append("PRIORITY,Integer,4;");
            strBuffTemp.append("BATCHSEQ,Integer,4;");
            strBuffTemp.append("MANDATORY,Integer,4;");
            strBuffTemp.append("DEPARTTIME,String,17;");
            strBuffTemp.append("ARRIVETIME,String,17;");
            strBuffTemp.append("CARRIERSIZE,String,65;");
            strBuffTemp.append("CARRIERSHAPE,String,65;");
            strBuffTemp.append("LOTID,String,65;");
            strBuffTemp.append("NEXTDST,String,65;");
            strBuffTemp.append("CARRIERPRIORITY,Integer,4;");
            strBuffTemp.append("RCVSRCEQP,String,65;");
            strBuffTemp.append("RCVSRCLOC,String,65;");
            strBuffTemp.append("RCVDSTEQP1,String,65;");
            strBuffTemp.append("RCVDSTLOC1,String,65;");
            strBuffTemp.append("RCVDSTEQP2,String,65;");
            strBuffTemp.append("RCVDSTLOC2,String,65;");
            strBuffTemp.append("RCVDSTEQP3,String,65;");
            strBuffTemp.append("RCVDSTLOC3,String,65;");
            strBuffTemp.append("RCVDSTEQP4,String,65;");
            strBuffTemp.append("RCVDSTLOC4,String,65;");
            strBuffTemp.append("RCVDSTEQP5,String,65;");
            strBuffTemp.append("RCVDSTLOC5,String,65");
        } else if ("HostTransferCmdAns".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("MSGID,Integer,4;");
            strBuffTemp.append("HOSTCOMMANDID,String,65;");
            strBuffTemp.append("HCACK,Integer,4;");
            strBuffTemp.append("REASON,String,257;");
            strBuffTemp.append("TOTALCOUNT,Integer,4;");
            strBuffTemp.append("SPLIT,Integer,0;");
            strBuffTemp.append("CMDACK,Integer,4;");
            strBuffTemp.append("COMMANDID,String,65;");
            strBuffTemp.append("CARRIERID,String,65;");
            strBuffTemp.append("RCVSRCEQP,String,65;");
            strBuffTemp.append("RCVSRCLOC,String,65;");
            strBuffTemp.append("RCVDSTEQP1,String,65;");
            strBuffTemp.append("RCVDSTLOC1,String,65;");
            strBuffTemp.append("RCVDSTEQP2,String,65;");
            strBuffTemp.append("RCVDSTLOC2,String,65;");
            strBuffTemp.append("RCVDSTEQP3,String,65;");
            strBuffTemp.append("RCVDSTLOC3,String,65;");
            strBuffTemp.append("RCVDSTEQP4,String,65;");
            strBuffTemp.append("RCVDSTLOC4,String,65;");
            strBuffTemp.append("RCVDSTEQP5,String,65;");
            strBuffTemp.append("RCVDSTLOC5,String,65;");
            strBuffTemp.append("DEPARTTIME,String,17;");
            strBuffTemp.append("ARRIVETIME,String,17;");
            strBuffTemp.append("TEXT,String,257");
        } else if ("InstallCommandReq".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("TSCID,Integer,4;");
            strBuffTemp.append("CARRIERID,String,65;");
            strBuffTemp.append("CARRIERLOC,String,65;");
            strBuffTemp.append("CARRIERTYPEID,String,65;");
            strBuffTemp.append("ZONENAME,String,65");
        } else if ("InstallCommandAns".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("HCACK,Integer,4;");
            strBuffTemp.append("TSCID,Integer,4;");
            strBuffTemp.append("CARRIERID,String,65;");
            strBuffTemp.append("CARRIERLOC,String,65;");
            strBuffTemp.append("ZONENAME,String,65;");
            strBuffTemp.append("REASON,String,257");
        } else if ("RemoveCommandReq".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("CARRIERID,String,65");
        } else if ("RemoveCommandAns".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("HCACK,Integer,4;");
            strBuffTemp.append("TSCID,Integer,4;");
            strBuffTemp.append("CARRIERID,String,65;");
            strBuffTemp.append("CARRIERLOC,String,65;");
            strBuffTemp.append("REASON,String,257");
        } else if ("JobDeleteReq".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("CARRIERID,String,65");
        } else if ("JobDeleteAns".equals(eventName)) {
            strBuffTemp.append("SID,Integer,4;");
            strBuffTemp.append("TIME,String,17;");
            strBuffTemp.append("REPLYFLG,Integer,4;");
            strBuffTemp.append("LAPTIME,Integer,4;");
            strBuffTemp.append("WAITTIME,Integer,4;");
            strBuffTemp.append("SENDTIME,Double,8;");
            strBuffTemp.append("HCACK,Integer,4;");
            strBuffTemp.append("CARRIERID,String,65;");
            strBuffTemp.append("REASON,String,257");
        }
        String strMsgStruct = strBuffTemp.toString();
        String[] strArray = GuiUtilities.splitByDelim( strMsgStruct, ";" );
        fields = new Object[strArray.length][4];
        for( int i=0; i<fields.length; i++ )
        {
            String[] params = GuiUtilities.splitByDelim( strArray[i], "," );

            //--------------------------------------------------
            // フィールド値をセットする
            //--------------------------------------------------
            fields[i][0] = params[0];   // キー
            fields[i][1] = params[1];   // 型
            fields[i][2] = params[2];   // データ長
            // 初期値
            if( TYPE_STRING.equals( fields[i][1] ) )
                fields[i][3] = "";
            else
                fields[i][3] = new Integer( 0 );
        }

        // メッセージ共通の固定値をセットする
//        fields[0][3] = new Integer( GuiEnvironment.getGuiId() );    // SID
        fields[0][3] = new Integer( guiId );    // SID
    }

    /**
     * int型変数をクライアントマシンのバイトオーダーに従いバイト配列に変換する
     */
    private byte[] convertIntToByteArray( int value )
    {
        if( ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN )
        {
            // クライアントマシンのバイトオーダーがBig-Endianの場合
            return GuiUtilities.convertIntToByteArrayByBigEndian( value );
        }
        else
        {
            // クライアントマシンのバイトオーダーがLittle-Endianの場合
            return GuiUtilities.convertIntToByteArrayByLittleEndian( value );
        }
    }

    /**
     * 指定されたオフセットを4の倍数に更新する
     */
    private int updateOffset( int offset )
    {
        int divisor = offset/4;     // 除算結果
        int remaindar = offset%4;   // 余り
        if( remaindar == 0 )
            return offset;
        else
            return 4*(divisor+1);
    }
}

