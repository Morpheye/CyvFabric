package mcpk.utils;

public class MathHelper {
	private static final int SIGNIFICANT_ANGLES = 65536;
	
    /** A table of sin values computed from 0 (inclusive) to 2*pi (exclusive), with steps of 2*PI / 65536. */
    private static final float[] SIN_TABLE = new float[65536];

    static {
    	for (int i = 0; i < 65536; ++i) {
            SIN_TABLE[i] = (float)Math.sin((double)i * Math.PI * 2.0D / 65536.0D);
    	}
    }

    /**
     * sin looked up in a table
     */
    public static float mcSin(float angle) {
        return SIN_TABLE[(int) (angle * 10430.378F) & 65535];
    }

    /**
     * cos looked up in the sin table with the appropriate offset
     */
    public static float mcCos(float angle) {
        return SIN_TABLE[(int) (angle * 10430.378F + 16384.0F) & 65535];
    }
}