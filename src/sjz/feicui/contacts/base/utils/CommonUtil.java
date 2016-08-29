package sjz.feicui.contacts.base.utils;

import java.math.BigDecimal;

public class CommonUtil {
	// ½«bate ×ª»»³ÉMB
	public static double byteCastMB(long number) {
		double n = number / 1024.0 / 1024.0;
		BigDecimal bd = new BigDecimal(n);
		bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bd.doubleValue();
	}
}
