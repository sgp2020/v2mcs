package net.muratec.mcs.service.forupgrade;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.StringTokenizer;
import java.util.Vector;



/**
 * 各種ユーティリティメソッドを提供するクラス
 *
 * @version $Revision: 1.4 $
 * @author M.Takashima(NISP)
 */
public class GuiUtilities
{
    /**
     * int型変数をLittle-Endianバイトオーダーに従いバイト配列に変換する
     */
    public static byte[] convertIntToByteArrayByLittleEndian( int value )
    {
        byte[] bArray = new byte[4];
        int index = 0;
        int startOffset = 0;

        for( int i=24; i>=0; i-=8 )
        {
            // 以下の変換を施す
            // ・int型変数を右シフトする
            // ・0xffとの論理輪を取り、符号無しint型変数に変換する
            // ・byte型にキャストする
            bArray[index] = (byte)((value>>>i)&0xff);
            index++;
        }

        return bArray;
    }

    /**
     * int型変数をBig-Endianバイトオーダーに従いバイト配列に変換する
     */
    public static byte[] convertIntToByteArrayByBigEndian( int value )
    {
        byte[] bArray = new byte[4];
        int index = 0;
        int startOffset = 0;

        for( int i=0; i<=24; i+=8 )
        {
            // 以下の変換を施す
            // ・int型変数を右シフトする
            // ・0xffとの論理輪を取り、符号無しint型変数に変換する
            // ・byte型にキャストする
            bArray[index] = (byte)((value>>>i)&0xff);
            index++;
        }

        return bArray;
    }

    /**
     * バイト配列をint型変数に変換します。
     * <p>
     * <strong>注:</strong>
     * このメソッドはバイト配列内のバイトオーダーがBig-Endianであることを想定しています｡
     */
    public static int convertByteArrayToInt( byte[] bArray )
    {
        BigInteger bIntValue = new BigInteger( bArray );
        return bIntValue.intValue();
    }

    /**
     * 指定された文字列が数値を表す文字列か否かを判断します｡
     *
     * @param value 判断対象となる文字列
     * @return 数値を表す文字列ならばture、それ以外はfalse。
     */
    public static boolean isNumber( String value )
    {
        try
        {
            Integer.parseInt( value );
            
            // このチェックをすることによって、"0"で始まるStringの場合でも
            // trueとならずにfalseで返すことが出来る
            // 
            // before: 01 -> true after 01 -> false
            if( "0".equals( value.substring( 0, 1 ) ) )
            {
                return false;
            }
            return true;
        }
        catch( NumberFormatException e ) {
            return false;
        }
    }
    
    public static boolean isNumberofTime( String value )
    {
        try
        {
            Integer.parseInt( value );
            return true;
        }
        catch( NumberFormatException e ) {
            return false;
        }
    }
    

    public static String[] splitByDelim( String str, String delim )
    {
        try
        {
            StringTokenizer token = new StringTokenizer( str, delim );
            int length = token.countTokens();
            String[] strArray = new String[length];
            for( int i=0; i<length; i++ )
            {
                strArray[i] = token.nextToken();
            }

            return strArray;
        }
        catch( Exception e )
        {
            return null;
        }
    }


    /**
     * 指定された範囲(from〜to)で昇順のソートを行う。<br>
     * ソートアルゴリズムにはクイックソートを使用｡
     *
     * @param data ソート対照のテーブルデータ
     * @param from ソート開始
     * @param to ソート終了
     * @param index ソート対象カラムのインデックス
     */
    public static void sort( Vector data, int from, int to, int index )
    {
        Object center = ((Object[])data.get((from+to)/2))[index];
        Object tmp = null;
        int i = from;
        int j = to;
        int n = 0;

        do
        {
            for( ; i < to; i++ )
            {
                if( center == null ) 
                    break;

                tmp = ((Object[])data.get( i ))[index];

                if( tmp instanceof String ) 
                {
//                  n = ((String)tmp).compareTo( (String)center );
                    if( center instanceof String ){
                        n = ((String)tmp).compareTo( (String)center );
                    } else if ( center instanceof BigDecimal ){
                        n = ((String)tmp).compareTo( ((BigDecimal) center).toString() );
                    } else if ( center instanceof Integer ){
                        n = ((String)tmp).compareTo( ((Integer) center).toString());
                    }
                }
                else if( tmp instanceof BigDecimal ) 
                {
//                  n = ((BigDecimal)tmp).compareTo( (BigDecimal)center );
                    if( center instanceof String){
                        n = ((BigDecimal)tmp).toString().compareTo((String) center);
                    } else if ( center instanceof BigDecimal){
                        n = ((BigDecimal)tmp).compareTo( (BigDecimal)center );
                    } else if ( center instanceof Integer){
                        n = ((BigDecimal) tmp).toString().compareTo( ((Integer) center).toString() );
                    }
                }
                else if( tmp instanceof Integer ) 
                {
//                  n = ((Integer)tmp).compareTo( (Integer)center );
                    if( center instanceof String){
                        n = ((Integer) tmp).toString().compareTo((String) center );
                    } else if (center instanceof BigDecimal){
                        n = ((Integer) tmp).toString().compareTo(((BigDecimal)center).toString());
                    } else if ( center instanceof Integer){
                        n = ((Integer)tmp).compareTo( (Integer)center );
                    }
                }
                else 
                {
                    n = 1;
                }

                if( !( 0 > n ) ) 
                    break;
            }

            for( ; j > from; j-- )
            {
                if( center != null )
                {
                    tmp = ((Object[])data.get( j ))[index];

                    if( tmp instanceof String ) 
                    {
//                      n = ((String)tmp).compareTo( (String)center );
                        if ( center instanceof String ){
                            n = ((String)tmp).compareTo( (String)center );
                        } else if ( center instanceof BigDecimal){
                            n = ((String)tmp).compareTo( ((BigDecimal) center).toString() );
                        } else if ( center instanceof Integer){
                            n = ((String)tmp).compareTo( ((Integer) center).toString() );
                        } 
                    }
                    else if( tmp instanceof BigDecimal ) 
                    {
//                      n = ((BigDecimal)tmp).compareTo( (BigDecimal)center );
                        if( center instanceof String ){
                            n = ((BigDecimal)tmp).toString().compareTo( (String) center );
                        } else if ( center instanceof BigDecimal){
                            n = ((BigDecimal)tmp).compareTo( (BigDecimal)center );
                        } else if ( center instanceof Integer ){
                            n = ((BigDecimal)tmp).toString().compareTo( ((Integer) center).toString() );
                        }
                    }
                    else if( tmp instanceof Integer ) 
                    {
//                      n = ((Integer)tmp).compareTo( (Integer)center );
                        if( center instanceof String ){
                            n = ((Integer)tmp).toString().compareTo((String) center );
                        } else if ( center instanceof BigDecimal){
                            n = ((Integer)tmp).toString().compareTo(((BigDecimal)center).toString());
                        } else if ( center instanceof Integer ){
                            n = ((Integer)tmp).compareTo( (Integer)center );
                        }
                    }
                    else 
                    {
                        n = 1;
                    }

                    if( !( 0 < n ) ) 
                        break;
                }
            }

            if( i < j )
            {
                tmp = data.get( i );
                data.set( i, data.get( j ) );
                data.set( j, tmp );
            }

            if( i <= j )
            {
                i++;
                j--;
            }
        }
        while( i <= j );

        if( from < j ) 
        {
            sort( data, from, j, index );
        }
        if( i < to ) 
        {
            sort( data, i, to, index );
        }
    }

    /**
     * 指定の文字列内にある全てのbeforeをreplacementに置換した結果
     * 生成される新しい文字列を返します｡
     *
     * @param base 置換対象となる文字列
     * @param before 置換したい文字列
     * @param replacement 置換後の文字列
     * @return 置換後の文字列
     */
    public static String replaceAll( String base, String before, String replacement )
    {
        StringBuffer regex = new StringBuffer();
        char[] upperChars = before.toUpperCase().toCharArray();
        char[] lowerChars = before.toLowerCase().toCharArray();
        for( int i=0; i<upperChars.length; i++ )
        {
            regex.append( "[" );
            regex.append( upperChars[i] );
            regex.append( lowerChars[i] );
            regex.append( "]" );
        }

        return base.replaceAll( regex.toString(), replacement );
    }
}
