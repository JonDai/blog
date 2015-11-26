package com.jondai.blog.util;

/**
 * Created by dongxg.
 * User: dongxg
 * Date: 2004-12-6
 * Time: 16:41:17
 */

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

public final class Base64 {
	private static final byte[] DECODE_TABLE = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 62, 0, 0, 0, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, 0, 0, 0, 0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 6,
			7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 0, 0, 0, 0, 0, 0, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36,
			37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 0, 0, 0, 0, 0 };

	// "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/"
	private static final byte[] ENCODE_TABLE = new byte[] { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86,
			87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121,
			122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };

	static {
		// create encode table
		// ENCODE_TABLE = new byte[64];
		int index = 0;
		for (char c = 'A'; c <= 'Z'; c++)
			ENCODE_TABLE[index++] = (byte) c;
		for (char c = 'a'; c <= 'z'; c++)
			ENCODE_TABLE[index++] = (byte) c;
		for (char c = '0'; c <= '9'; c++)
			ENCODE_TABLE[index++] = (byte) c;
		ENCODE_TABLE[index++] = (byte) '+';
		ENCODE_TABLE[index++] = (byte) '/';

		// create decode table
		for (int i = 0; i < 64; i++)
			DECODE_TABLE[(int) ENCODE_TABLE[i]] = (byte) i;
	}

	public final static byte[] base64Encode(byte[] byteData) {
		if (byteData == null)
			return null;
		int iSrcIdx; // index into source (byteData)
		int iDestIdx; // index into destination (byteDest)
		byte byteDest[] = new byte[((byteData.length + 2) / 3) * 4];

		for (iSrcIdx = 0, iDestIdx = 0; iSrcIdx < byteData.length - 2; iSrcIdx += 3) {
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 1] >>> 4) & 017 | (byteData[iSrcIdx] << 4) & 077);
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 2] >>> 6) & 003 | (byteData[iSrcIdx + 1] << 2) & 077);
			byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 2] & 077);
		}

		if (iSrcIdx < byteData.length) {
			byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] >>> 2) & 077);
			if (iSrcIdx < byteData.length - 1) {
				byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 1] >>> 4) & 017 | (byteData[iSrcIdx] << 4) & 077);
				byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx + 1] << 2) & 077);
			} else
				byteDest[iDestIdx++] = (byte) ((byteData[iSrcIdx] << 4) & 077);
		}

		for (iSrcIdx = 0; iSrcIdx < iDestIdx; iSrcIdx++) {
			if (byteDest[iSrcIdx] < 26)
				byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 'A');
			else if (byteDest[iSrcIdx] < 52)
				byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 'a' - 26);
			else if (byteDest[iSrcIdx] < 62)
				byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + '0' - 52);
			else if (byteDest[iSrcIdx] < 63)
				byteDest[iSrcIdx] = (byte) '+';
			else
				byteDest[iSrcIdx] = (byte) '/';
		}

		for (; iSrcIdx < byteDest.length; iSrcIdx++)
			byteDest[iSrcIdx] = (byte) '=';

		return byteDest;
	}

	public final static String base64Encode(String strInput) {
		if (strInput == null)
			return null;
		return base64Encode(strInput, "GB2312");
	}

	public final static String base64Encode(String strInput, String charSet) {
		if (strInput == null)
			return null;
		String strOutput = null;
		byte byteData[] = new byte[strInput.length()];
		try {
			byteData = strInput.getBytes(charSet);
			strOutput = new String(base64Encode(byteData), charSet);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return strOutput;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2000-11-4 18:27:35)
	 *
	 * @param in
	 *            java.io.InputStream
	 * @param charSet
	 *            java.lang.String
	 */
	public final static String base64Encode(InputStream in, String charSet) {
		try {
			int c;
			byte[] buff = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			while ((c = in.read(buff, 0, 1024)) != -1) {
				out.write(buff, 0, c);
				// index+=1024;
				// out.write(c);
				// attachContent+=ss;
			}
			in.close();
			out.flush();
			byte[] tmp2 = Base64.base64Encode(out.toByteArray());
			out.close();
			return new String(tmp2, charSet);
		} catch (IOException e) {
			return "";
		}
	}

	public static byte[] base64Decode(byte[] base64Data) {
		if (base64Data == null)
			return null;
		if (base64Data.length == 0)
			return new byte[0];
		if (base64Data.length % 4 != 0)
			throw new IllegalArgumentException("���ݲ�����������Ϊ��" + base64Data.length);

		byte[] result = null;
		int groupCount = base64Data.length / 4;

		int lastData = base64Data.length;
		while (base64Data[lastData - 1] == 0x3D) {
			if (--lastData == 0)
				return new byte[0];
		}
		result = new byte[lastData - groupCount];

		int temp = 0;
		int resultIndex = 0;
		int dataIndex = 0;
		for (; dataIndex + 4 < base64Data.length;) {
			temp = DECODE_TABLE[base64Data[dataIndex++]];
			temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex++]];
			temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex++]];
			temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex++]];

			result[resultIndex++] = (byte) ((temp >> 16) & 0xff);
			result[resultIndex++] = (byte) ((temp >> 8) & 0xff);
			result[resultIndex++] = (byte) (temp & 0xff);
		}

		temp = 0;
		int j = 0;
		for (; dataIndex < base64Data.length; dataIndex++, j++)
			temp = (temp << 6) + DECODE_TABLE[base64Data[dataIndex]];
		for (; j < 4; j++)
			temp <<= 6;

		result[resultIndex++] = (byte) ((temp >> 16) & 0xff);
		if (base64Data[dataIndex - 2] != '=')
			result[resultIndex++] = (byte) ((temp >> 8) & 0xff);
		if (base64Data[dataIndex - 1] != '=')
			result[resultIndex++] = (byte) (temp & 0xff);

		return result;
	}

	public final static String base64Decode(String strInput) {
		if (strInput == null)
			return null;

		return base64Decode(strInput, "GB2312");
	}

	public final static String base64Decode(String strInput, String charSet) {
		if (strInput == null)
			return null;
		String strOutput = null;
		byte byteData[] = new byte[strInput.length()];
		try {
			byteData = strInput.getBytes(charSet);
			strOutput = new String(base64Decode(byteData), charSet);
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		return strOutput;
	}

	/**
	 * �˴����뷽��˵���� �������ڣ�(2000-11-4 18:27:35)
	 *
	 * @param in
	 *            java.io.InputStream
	 * @param charSet
	 *            java.lang.String
	 */
	public final static String base64Decode(InputStream in, String charSet) {
		try {
			int c;
			byte[] buff = new byte[1024];
			ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
			while ((c = in.read(buff, 0, 1024)) != -1) {
				out.write(buff, 0, c);
				// index+=1024;
				// out.write(c);
				// attachContent+=ss;
			}
			in.close();
			out.flush();
			byte[] tmp2 = Base64.base64Decode(out.toByteArray());
			out.close();
			return new String(tmp2, charSet);
		} catch (IOException e) {
			return "";
		}
	}
}
