package com.adanac.tool.j2se.encrypt;

import java.util.BitSet;

import com.adanac.framework.utils.StringUtils;

public class BitsUtil {
	public static BitSet convert(Long value) {
		BitSet bits = new BitSet();
		int index = 0;
		while (value != 0L) {
			if (value % 2L != 0) {
				bits.set(index);
			}
			++index;
			value = value >>> 1;
		}
		return bits;
	}

	public static Long convert(BitSet bits) {
		long value = 0L;
		for (int i = 0; i < bits.length(); ++i) {
			value += bits.get(i) ? (1L << i) : 0L;
		}
		return value;
	}

	public static BitSet stb(String fspuProperty) {
		BitSet bitSet = new BitSet();
		if (!StringUtils.isEmpty(fspuProperty)) {
			String[] property = fspuProperty.split(",");
			for (int i = 0; i < property.length; i++) {
				Long a = Long.valueOf(property[i]);
				if (a == 0) {
					bitSet.set(i, false);
				}
				if (a == 1) {
					bitSet.set(i, true);
				}
			}
		}
		return bitSet;
	}

	public static String bts(Long spuProperty) {
		BitSet bitSet = convert(spuProperty);
		String property = "";
		if (bitSet.get(0)) {
			property += "1,";
		} else {
			property += "0,";
		}
		if (bitSet.get(1)) {
			property += "1,";
		} else {
			property += "0,";
		}
		return property.substring(0, property.length() - 1);
	}

	public static void main(String[] args) {
		System.out.println(convert(02l));
		System.out.println(stb("2,1,3"));
		System.out.println(bts(1l));
	}
}
