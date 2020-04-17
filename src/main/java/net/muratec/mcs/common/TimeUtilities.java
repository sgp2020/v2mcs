/*
 * $Header: /home/CVS/CVS_DB/macs-infi2/gui-source/src/macs/tools/TimeUtilities.java,v 1.1.1.1 2017/12/28 06:09:48 kuwahara Exp $
 * $Revision: 1.1.1.1 $
 * $Date: 2017/12/28 06:09:48 $
 *
 * ====================================================================
 *
 * TimeUtilities.java
 *
 * Copyright 2000 by Murata Machinary,LTD
 *
 * ====================================================================
 *
 * $Log: TimeUtilities.java,v $
 * Revision 1.1.1.1  2017/12/28 06:09:48  kuwahara
 * First Release
 *
 * Revision 1.1.1.1  2009/09/11 02:03:18  okumura
 * MACS 10 anniversary model.
 *
 * Revision 1.2  2009/02/14 01:11:39  okumura
 * Added function.
 *
 * Revision 1.2  2008/09/26 10:16:48  ishigaki
 * For move macs-340
 *
 *
 */


package net.muratec.mcs.common;

import java.util.*;

import net.muratec.mcs.common.defines.GuiEnvironment;

import java.text.SimpleDateFormat;
//import macs.common.GuiEnvironment;
/**
 * 時刻関係のユーティリティクラスです。
 *
 * @version 1.0 01/10/01
 * @author M.Takashima(NISP)
 */
public class TimeUtilities
{
	/** カレンダークラスオブジェクトを保持します。 */
	private static Calendar calender = Calendar.getInstance();

	/** 'YYYYMMDDHHMMSSSS'の日付時刻フォーマットを保持します。 */
	private static SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyyMMddHHmmssSS" );


//********************************************************************************
// Public Methods
//********************************************************************************

	/**
	 * 'YYYYMMDDHHMMSSSS'フォーマットの文字列をlong型に変換します。
	 *
	 * @param strTime 'YYYYMMDDHHMMSSSS'フォーマットの文字列
	 * @return 時刻を表すlong値
	 */
	public static long timeConvStrToMillis( String strTime )
	{
		String tmp = "";

		switch( strTime.length() )
		{
		case 14:
			tmp += "0";
		case 15:
			tmp += "0";
		case 16:
			tmp = strTime + tmp;
			calender.clear();
			calender.set( Integer.parseInt( tmp.substring(  0,  4 ) ),
						  Integer.parseInt( tmp.substring(  4,  6 ) ) - 1,
						  Integer.parseInt( tmp.substring(  6,  8 ) ),
						  Integer.parseInt( tmp.substring(  8, 10 ) ),
						  Integer.parseInt( tmp.substring( 10, 12 ) ),
						  Integer.parseInt( tmp.substring( 12, 14 ) )
			);
			return calender.getTime().getTime() + ( Integer.parseInt( tmp.substring( 14 ) ) * 10 );
		default:
			return 0;
		}
	}

	/**
	 * 時刻(long型)を'YYYYMMDDHHMMSSSS'フォーマットの文字列に変換します。
	 *
	 * @param millis 時刻を表すlong値
	 * @return 'YYYYMMDDHHMMSSSS'フォーマットの文字列
	 */
	public static String timeConvMillsToStrDate( long millis )
	{
//		return dateFormat.format( new Date( millis ) );
		String strDate = dateFormat.format( new Date( millis ) );
		return strDate.substring( 0, 16 );
	}

	/**
	 * 時刻(long型)を指定された単位で更新します。
	 *
	 * @param nMillisec 時刻を表すlong値
	 * @param nFlag 更新単位(時刻・日付・月)
	 * @return 更新後の時刻
	 */
	public static long dateUpdate( long nMillisec, int nFlag )
	{
		String str = timeConvMillsToStrDate( nMillisec );
		calender.clear();
		calender.setTime( new Date( nMillisec ) );
		switch( nFlag )
		{
		case Calendar.HOUR_OF_DAY:
			calender.set( Calendar.HOUR_OF_DAY, Integer.parseInt( str.substring( 8, 10 ) ) + 1 );
			break;
		case Calendar.DATE:
			calender.set( Calendar.DATE, Integer.parseInt( str.substring( 6, 8 ) ) + 1 );
			break;
		case Calendar.WEEK_OF_MONTH:
			calender.add( Calendar.DATE, 7 );
			break;
		case Calendar.MONTH:
			calender.set( Calendar.MONTH, Integer.parseInt( str.substring( 4, 6 ) ) );
			break;
		case Calendar.YEAR:
			calender.add( Calendar.YEAR, 1 );
			break;
		}

		return calender.getTime().getTime();
	}
	/**
	 * WEEKボタン用
	 * 2005.05.09 ADD YAMANAN
	 */
	public static String getLastDayOfWeek( String start_date )
	{
		calender.clear();
		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(5,7)) - 1 );
		calender.set( Calendar.DATE , Integer.parseInt(start_date.substring(8,10)) );
		calender.set( Calendar.HOUR_OF_DAY, 0 );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );

		//まずは、今週の月曜日まで戻る。
		while( calender.get(Calendar.DAY_OF_WEEK) != GuiEnvironment.getDayOfWeek() )
		{
			calender.add( Calendar.DATE, -1 );
		}

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}
	/**
	 * MONTHボタン用
	 * 2005.05.09 ADD YAMANAN
	 */
	public static String getLastMonth( long millis )
	{
		calender.clear();
		calender.setTime( new Date( millis ) );
		calender.set( Calendar.DATE, 1 );
		calender.set( Calendar.HOUR_OF_DAY, 0 );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );

		calender.add( Calendar.MONTH, -1 );


		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}
	/**
	 * MONTHボタン用
	 * 2005.05.09 ADD YAMANAN
	 */
	public static String getLastMonth( String start_date )
	{
		calender.clear();

		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(5,7)) - 1 );
		calender.set( Calendar.DATE, 1 );
		calender.set( Calendar.HOUR_OF_DAY, 0 );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}
	public static String addMonth(String start_date) {
		calender.clear();
		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(4,6)) - 1 );
		calender.set( Calendar.DATE,  Integer.parseInt(start_date.substring(6,8)) );
		calender.set( Calendar.HOUR_OF_DAY, Integer.parseInt(start_date.substring(8,10)) );
		calender.set( Calendar.MINUTE, Integer.parseInt(start_date.substring(10,12)) );
		calender.set( Calendar.SECOND, Integer.parseInt(start_date.substring(12,14)) );
		calender.set( Calendar.MILLISECOND, 0 );

		calender.add( Calendar.MONTH, 1 );

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}

	public static String addWeek(String start_date) {
		calender.clear();
		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(4,6)) - 1 );
		calender.set( Calendar.DATE,  Integer.parseInt(start_date.substring(6,8)) );
		calender.set( Calendar.HOUR_OF_DAY, Integer.parseInt(start_date.substring(8,10)) );
		calender.set( Calendar.MINUTE, Integer.parseInt(start_date.substring(10,12)) );
		calender.set( Calendar.SECOND, Integer.parseInt(start_date.substring(12,14)) );
		calender.set( Calendar.MILLISECOND, 0 );

		calender.add( Calendar.DATE, 7 );

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}
	public static String getDate( Object oDateTime )
	{
		String tmp = (String)oDateTime;
		if( tmp == null)
		{
			// 文字列がヌルもしくは14文字以下ならばパラメータをそのまま返す
			return tmp;
		}

		return tmp.substring(  0,  4 ) + "/" + tmp.substring(  4,  6 ) + "/" + tmp.substring(  6,  8 );
	}
	/**
	 * ドリルダウンWEEKボタン用
	 * 2005.08.22 ADD YAMANAN
	 */
	public static String getDrillWeek( String start_date )
	{
		calender.clear();
		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(5,7)) - 1 );
		calender.set( Calendar.DATE,  Integer.parseInt(start_date.substring(8,10)) );
		calender.set( Calendar.HOUR_OF_DAY, 0 );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );

		calender.add( Calendar.DATE, 7 );

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}
	/**
	 * ドリルダウンMONTHボタン用
	 * 2005.08.22 ADD YAMANAN
	 */
	public static String getDrillMonth( String start_date )
	{
		calender.clear();
		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(5,7)) - 1 );
		calender.set( Calendar.DATE,  Integer.parseInt(start_date.substring(8,10)) );
		calender.set( Calendar.HOUR_OF_DAY, 0 );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );

		calender.add( Calendar.MONTH, 1 );

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}

	/**
	 * ドリルダウンHOURボタン用
	 * 2005.10.12 ADD YAMANAN
	 */
	public static String getDrillHour( String start_date )
	{
		calender.clear();
		calender.set( Calendar.YEAR , Integer.parseInt(start_date.substring(0,4))  );
		calender.set( Calendar.MONTH, Integer.parseInt(start_date.substring(4,6)) - 1 );
		calender.set( Calendar.DATE,  Integer.parseInt(start_date.substring(6,8)) );
		calender.set( Calendar.HOUR_OF_DAY, Integer.parseInt(start_date.substring(8,10)) );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );

		calender.add( Calendar.DATE, 1 );

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}
	/**
	 * 時刻(String型)を指定された単位で更新します。
	 *
	 * @param nMillisec 時刻を表すlong値
	 * @param nFlag 更新単位(時刻・日付・月)
	 * @return 更新後の時刻を表す文字列
	 */
	public static String dateUpdate( String strDate, int nFlag )
	{
		calender.clear();
		calender.setTime( new Date( timeConvStrToMillis( strDate ) ) );
		switch( nFlag )
		{
		case Calendar.HOUR_OF_DAY:
			calender.set( Calendar.HOUR_OF_DAY, Integer.parseInt( strDate.substring( 8, 10 ) ) + 1 );
			break;
		case Calendar.DATE:
			calender.set( Calendar.DATE, Integer.parseInt( strDate.substring( 6, 8 ) ) + 1 );
			break;
		case Calendar.WEEK_OF_MONTH:
			calender.add( Calendar.DATE, 7 );
			break;
		case Calendar.MONTH:
			calender.set( Calendar.MONTH, Integer.parseInt( strDate.substring( 4, 6 ) ) );
			break;
		case Calendar.YEAR:
			calender.add( Calendar.YEAR, 1 );
			break;
		}

		return timeConvMillsToStrDate( calender.getTime().getTime() );
	}

	/**
	 * 'YYYYMMDDHHMMSSSS'フォーマットの文字列を'YYYY/MM/DD HH:MM:SS'フォーマットの
	 * 文字列に変換します。
	 *
	 * @param oDateTime 'YYYYMMDDHHMMSSSS'フォーマットの文字列
	 * @return 'YYYY/MM/DD HH:MM:SS'フォーマットの文字列
	 */
	public static String datetimeFormat( Object oDateTime )
	{
		String tmp = (String)oDateTime;
		if( tmp == null || tmp.length() < 14 )
		{
			// 文字列がヌルもしくは14文字以下ならばパラメータをそのまま返す
			return tmp;
		}

		return tmp.substring(  0,  4 ) + "/" + tmp.substring(  4,  6 ) + "/" + tmp.substring(  6,  8 ) + " " +
			   tmp.substring(  8, 10 ) + ":" + tmp.substring( 10, 12 ) + ":" + tmp.substring( 12, 14 );
	}
	public static long getInterval( String setTime, String clearTime ) {
		try {
			return ( dateFormat.parse( clearTime ).getTime() 
					- dateFormat.parse( setTime ).getTime() ) / 1000;
		} catch ( Exception e ) {
			return 0;
		}
	}
public static long getThisYear() {
		calender.clear();
		calender.setTime( new Date( System.currentTimeMillis()) );
		calender.set( Calendar.MONTH, 0 );
		calender.set( Calendar.DATE, 1 );
		calender.set( Calendar.HOUR_OF_DAY, 0 );
		calender.set( Calendar.MINUTE, 0 );
		calender.set( Calendar.SECOND, 0 );
		calender.set( Calendar.MILLISECOND, 0 );
		return calender.getTime().getTime();
	}
	/**
	 * 'YYYYMMDDHHMMSSSS'フォーマットの文字列を'YYYY/MM/DD HH:MM:SS mm'フォーマットの
	 * 文字列に変換します。
	 *
	 * @param oDateTime 'YYYYMMDDHHMMSSSS'フォーマットの文字列
	 * @return 'YYYY/MM/DD HH:MM:SS mm'フォーマットの文字列
	 */
	public static String datetimeMillisFormat( Object oDateTime )
	{
		String tmp = (String)oDateTime;
		if( tmp == null || tmp.length() < 16 )
		{
			// 文字列がヌルもしくは16文字以下ならばパラメータをそのまま返す
			return tmp;
		}

		return tmp.substring(  0,  4 ) + "/" + tmp.substring(  4,  6 ) + "/" + tmp.substring(  6,  8 ) + " " +
			   tmp.substring(  8, 10 ) + ":" + tmp.substring( 10, 12 ) + ":" + tmp.substring( 12, 14 ) + "." +
			   tmp.substring( 14, 16 );
	}

	/**
	 * 'YYYYMMDDHHMMSSSS'フォーマットの文字列を'YYYY-MM-DD HH:MM:SS'フォーマットの
	 * 文字列に変換します。
	 *
	 * @param oDateTime 'YYYYMMDDHHMMSSSS'フォーマットの文字列
	 * @return 'YYYY/MM/DD HH:MM:SS'フォーマットの文字列
	 */
	public static String datetimeFormat2( Object oDateTime )
	{
		String tmp = (String)oDateTime;
		if( tmp == null || tmp.length() < 14 )
		{
			// 文字列がヌルもしくは14文字以下ならばパラメータをそのまま返す
			return tmp;
		}

		return tmp.substring(  0,  4 ) + "-" + tmp.substring(  4,  6 ) + "-" + tmp.substring(  6,  8 ) + " " +
			   tmp.substring(  8, 10 ) + ":" + tmp.substring( 10, 12 ) + ":" + tmp.substring( 12, 14 );
	}

	/**
	 * 時間を'HH:MM:SS'フォーマットの文字列に変換します。
	 *
	 * @param millis 時間を表すlong値
	 * @return 'HH:MM:SS'フォーマットの文字列
	 */
	public static String timeFormat( long millis )
	{
		long abs = java.lang.Math.abs( millis );
		long hh  = abs / 3600;
		long mm  = ( abs / 60 ) % 60;
		long ss  = abs % 60;
		return ( ( millis < 0 )?  "-" : "" ) +
			   ( ( hh     < 10 )?  "0" : "" ) + String.valueOf( hh ) + ":" +
			   ( ( mm     < 10 )?  "0" : "" ) + String.valueOf( mm ) + ":" +
			   ( ( ss     < 10 )?  "0" : "" ) + String.valueOf( ss );
	}


	/**
	 * 時間を'HH:MM:SS'フォーマットの文字列に変換します。
	 *
	 * @param millis 時間を表すlong値
	 * @return 'HH:MM:SS'フォーマットの文字列
	 */
	public static String hostTimeFormat( long millis )
	{
		long abs = java.lang.Math.abs( millis );
		long dd  = abs / 86400000;
		long hh  = ( abs / 3600000 ) % 24;
		long mm  = ( abs / 60000 ) % 60;
		return ( ( millis < 0 )?  "-" : "" ) + String.valueOf( dd ) + " day " +
			   ( ( hh     < 10 )?  "0" : "" ) + String.valueOf( hh ) + " h " +
			   ( ( mm     < 10 )?  "0" : "" ) + String.valueOf( mm ) + " m ";
	}


	/**
	 * 時間を表すオブジェクトを'HH:MM:SS'フォーマットの文字列に変換します。
	 *
	 * @param oTime 時間を表すオブジェクト
	 * @return 'HH:MM:SS'フォーマットの文字列
	 */
	public static String timeFormat( Object oTime )
	{
		if( oTime != null ) 
		{
//			return timeFormat( ((BigDecimal)oTime).longValue() );
			return timeFormat( Long.parseLong( oTime.toString() ) );
		}
		return null;
	}

	/**
	 * 指定された日時の、月の末日を返す
	 * 
	 * @param millis 末日を求めたい日時
	 * @return 指定されたフィールドの末日
	 */
	public static int getActualMaximumDay( long millis )
	{
		calender.clear();
		calender.setTime( new Date( millis ) );
		return calender.getActualMaximum( Calendar.DATE );
	}

	/**
	 * 指定された日時の、月の末日を返す
	 * 
	 * @param strDate 末日を求めたい日時
	 * @return 指定されたフィールドの末日
	 */
	public static int getActualMaximumDay( String strDate )
	{
		return TimeUtilities.getActualMaximumDay( TimeUtilities.timeConvStrToMillis( strDate ) );
	}

	/**
	 * 指定された日時を表す文字列から月を返す。<br>
	 * 引数となる文字列のフォーマットは'YYYYMMDDHH24MISS'。
	 * 
	 * @param strDate 日時を表す文字列
	 * @return 月を表すint値
	 */
	public static int getMonth( String strDate )
	{
		return Integer.parseInt( strDate.substring( 4, 6 ) );
	}

	/**
	 * 指定された日時を表す文字列から日を返す。<br>
	 * 引数となる文字列のフォーマットは'YYYYMMDDHH24MISS'。
	 * 
	 * @param strDate 日時を表す文字列
	 * @return 日を表すint値
	 */
	public static int getDay( String strDate )
	{
		return Integer.parseInt( strDate.substring( 6, 8 ) );
	}

	/**
	 * 指定された日時を表す文字列から時間を返す。<br>
	 * 引数となる文字列のフォーマットは'YYYYMMDDHH24MISS'。
	 * 
	 * @param strDate 日時を表す文字列
	 * @return 時間を表すint値
	 */
	public static int getHour( String strDate )
	{
		return Integer.parseInt( strDate.substring( 8, 10 ) );
	}
}
